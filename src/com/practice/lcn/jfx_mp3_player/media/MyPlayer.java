package com.practice.lcn.jfx_mp3_player.media;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.application.Platform;

import com.practice.lcn.jfx_mp3_player.MainApp;
import com.practice.lcn.jfx_mp3_player.controller.MyFxmlController;
import com.practice.lcn.jfx_mp3_player.controller.MainController;
import com.practice.lcn.jfx_mp3_player.media.effect.Effect;
import com.practice.lcn.jfx_mp3_player.media.effect.TempoShift;

import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.net.URL;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * WAV music player. This player is powered by <code>javax.sound</code> technology.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class MyPlayer {
    private static Logger logger = LogManager.getLogger(MyPlayer.class.getName());

    /**
     * original song without any effects applied
     */
    public static final String BASE_WAV = "base.wav";
    /**
     * original song without any effects applied
     */
    public static final String BASE_MP3 = "base.mp3";
    /**
     * filename format for all songs transformed by effects
     */
    public static final String TRANSFORM_WAV = "transform-%d.wav";

    /**
     * song stream
     */
    private AudioInputStream ais;
    /**
     * song
     */
    private Clip clip;
    /**
     * frame position when the song is paused (in microsecond).
     */
    private long pausedMicrosecPos;
    /**
     * enable/disable song repeat.
     */
    private volatile boolean enableRepeat;
    /**
     * song tempo
     */
    private double tempo;
    /**
     * song's current play state.
     */
    private volatile SimpleObjectProperty<STATE> state;
    /**
     * indicating whether this music player is ready to replay a song.
     */
    private volatile boolean isReady;
    /**
     * number of effect transforms that the song currently applies.
     */
    private int transformCount;
    /**
     * current effect transforms of the song.
     */
    private List<Effect> effects;

    /**
     * controller controlling this music player.
     */
    private MyFxmlController mfc;

    /**
     * music player's state.
     */
    public enum STATE {
        /**
         * indicating that music player is current playing a song.
         */
        RUNNING,
        /**
         * indicating that the song is currently paused by users. It can be resumed later.
         */
        PAUSE,
        /**
         * indicating that the song is currently stopped by users. Users have to play the song from the start.
         */
        STOP
    };

    /**
     * initialize the music player with WAV song file.
     *
     * @param  wavPath   WAV song file location.
     * @throws Exception throws when the song cannot be loaded.
     */
    public MyPlayer(String wavPath, MyFxmlController mfc) throws Exception {
        this.pausedMicrosecPos = 0L;
        this.transformCount = 0;
        this.effects = new ArrayList<Effect>();
        this.tempo = 1;
        this.enableRepeat = false;
        this.mfc = mfc;
        this.state = new SimpleObjectProperty<STATE>();
        this.isReady = false;

        this.state.addListener(new ChangeListener<STATE>(){
            @Override
            public void changed(ObservableValue<? extends STATE> observable, STATE oldValue, STATE newValue) {
                logger.debug(String.format("state listener: newValue: \"%s\"", newValue.name()));
                if (newValue == STATE.RUNNING) {
                    ((MainController) mfc).initBtnPauseHotkey();
                }
                else if (newValue == STATE.PAUSE || newValue == STATE.STOP) {
                    ((MainController) mfc).initBtnPlayHotkey();
                }
            }
        });
        this.loadSong(wavPath);
    }

    /**
     * start playing the song from the start.
     */
    public void start() {
        if (this.state.get() == STATE.STOP) {
            this.clip.setMicrosecondPosition(0L);
            this.clip.start();
            this.state.set(STATE.RUNNING);
        }
    }

    /**
     * stop the song from playing and move the seek position to the start.
     */
    public void stop() {
        if (this.state.get() == STATE.RUNNING || this.state.get() == STATE.PAUSE) {
            this.clip.stop();
            this.pausedMicrosecPos = 0L;
            this.state.set(STATE.STOP);
        }
    }

    /**
     * pause the song temporarily. The song can be resumed later.
     */
    public void pause() {
        if (this.state.get() == STATE.RUNNING) {
            this.pausedMicrosecPos = this.clip.getMicrosecondPosition();
            this.clip.stop();
            this.state.set(STATE.PAUSE);
        }
    }

    /**
     * resume the song from the paused frame position.
     */
    public void resume() {
        if (this.state.get() == STATE.PAUSE) {
            if (this.pausedMicrosecPos < this.clip.getMicrosecondLength()) {
                this.clip.setMicrosecondPosition(this.pausedMicrosecPos);
            }
            else {
                this.clip.setMicrosecondPosition(0L);
            }
            this.clip.start();
            this.state.set(STATE.RUNNING);
        }
    }

    /**
     * seek the song at certain frame position.
     *
     * @param secPos frame position that the users want to seek the song at (in seconds).
     */
    public void seek(int secPos) {
        long microsecPos = Math.round(secPos * Math.pow(10, 6));
        if (this.state.get() == STATE.STOP || this.state.get() == STATE.PAUSE) {
            this.pausedMicrosecPos = microsecPos;
        }
    }

    /**
     * adjust song volume
     *
     * @param volume new song volume
     */
    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }

        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * adjust song tempo.
     *
     * @param  tempo     new song tempo
     * @throws Exception throws when tempo shift algorithm cannot be applied or the transformed song cannot be played.
     */
    public void setTempo(double tempo) throws Exception {
        if (this.tempo == tempo) {
            return;
        }

        this.tempo = tempo;

        TempoShift ts1 = new TempoShift();
        ts1.setTempo(this.tempo);
        Iterator<Effect> it = this.effects.iterator();
        while (it.hasNext()) {
            Effect e = it.next();
            if (e instanceof TempoShift) {
                it.remove();
                break;
            }
        }
        this.effects.add(ts1);
        String transformedWavPath = this.applyEffects();

        this.loadSong(transformedWavPath);
    }

    public void setState(STATE state) {
        this.state.set(state);
    }

    public void setEnableRepeat(boolean enableRepeat) {
        this.enableRepeat = enableRepeat;
    }

    public void setMfc(MyFxmlController mfc) {
        this.mfc = mfc;
    }

    /**
     * @return current song volume
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public double getTempo() {
        return this.tempo;
    }

    public STATE getState() {
        return this.state.get();
    }

    /**
     * @return song total time in second
     */
    public long getSongTotalTime() {
        return Math.round(this.clip.getMicrosecondLength() / Math.pow(10, 6));
    }

    /**
     * @return song current time in second
     */
    public long getSongCurrentTime() {
        return Math.round(this.clip.getMicrosecondPosition() / Math.pow(10, 6));
    }

    /**
     * deallocate song and song stream.
     */
    public void freeClip() {
        if (this.clip != null) {
            this.clip.close();
        }
        try {
            if (this.ais != null) {
                this.ais.close();
            }
        }
        catch (IOException e) {

        }
    }

    /**
     * remove all effect-transformed song files.
     */
    public static void freeEffects() {
        File processingDir = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR);
        File[] processingDirFiles = processingDir.listFiles();
        for (File f : processingDirFiles) {
            if (f.getName().matches("transform-\\d+.wav")) {
                f.delete();
            }
        }
    }

    /**
     * remove the original songs {@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#BASE_WAV} and {@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#BASE_MP3}.
     */
    public static void freeSong() {
        File processingDir = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR);
        File[] processingDirFiles = processingDir.listFiles();
        for (File f : processingDirFiles) {
            if (f.getName().equals(MyPlayer.BASE_WAV) || f.getName().equals(MyPlayer.BASE_MP3)) {
                logger.debug(String.format("freeSong(): f.getName(): \"%s\"", f.getName()));
                f.delete();
            }
        }
    }

    /**
     * deallocate this music player and close all its resources.
     */
    public void close() {
        this.freeClip();
        MyPlayer.freeEffects();
        MyPlayer.freeSong();
        this.state.set(STATE.STOP);
    }

    /**
     * load WAV song.
     *
     * @param  wavPath                       WAV song location.
     * @throws LineUnavailableException      throws when the song cannot be loaded.
     * @throws UnsupportedAudioFileException N/A
     * @throws InterruptedException          N/A
     * @throws IOException                   throws when the song cannot be loaded.
     */
    private void loadSong(String wavPath) throws LineUnavailableException, UnsupportedAudioFileException, InterruptedException, IOException {
        this.freeClip();

        URL wavUrl = new File(wavPath).toURI().toURL();
        this.ais = AudioSystem.getAudioInputStream(wavUrl);
        DataLine.Info dlInfo = new DataLine.Info(Clip.class, this.ais.getFormat());
        this.clip = (Clip) AudioSystem.getLine(dlInfo);
        this.clip.open(this.ais);

        this.clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                if (event.getType().equals(LineEvent.Type.STOP)) {
                    if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength()) {
                        logger.debug("media has finished playing.");
                        state.set(STATE.STOP);
                        clip.stop();
                        logger.debug(String.format("loadSong(): linelistener: enableRepeat: %b", enableRepeat));
                        if (enableRepeat) {
                            isReady = false;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainController) mfc).fireBtnStopClick();
                                    isReady = true;
                                }
                            });
                            while (!isReady);
                            ((MainController) mfc).fireBtnPlayClick();
                        }
                    }
                }
            }
        });

        this.state.set(STATE.STOP);
    }

    /**
     * apply effects on the original song {@link com.practice.lcn.jfx_mp3_player.controller.MainController#BASE_WAV}.
     * @return transformed song location
     * @throws Exception throws when an effect cannot be applied on the song.
     */
    private String applyEffects() throws Exception {
        MyPlayer.freeEffects();
        this.transformCount = 0;

        File fBaseWav = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR + File.separatorChar + MyPlayer.BASE_WAV);
        String transformedWavPath = fBaseWav.getAbsolutePath();
        try {
            for (Effect e : this.effects) {
                this.transformCount++;
                transformedWavPath = e.apply(transformedWavPath, this.transformCount);
            }

            return transformedWavPath;
        }
        catch (Exception e) {
            this.transformCount = 0;
            throw e;
        }
    }
}
