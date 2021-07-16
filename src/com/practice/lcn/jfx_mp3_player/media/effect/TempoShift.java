package com.practice.lcn.jfx_mp3_player.media.effect;

import com.practice.lcn.jfx_mp3_player.media.MyPlayer;
import com.practice.lcn.jfx_mp3_player.MainApp;

import java.io.File;

/**
 * Tempo shift effect on a song. "Tempo" means to change the playback rate without affecting the original pitch.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class TempoShift implements Effect {
    /**
     * location of the tempo shift tool (in Windows).
     */
    private static final String SS_EXE_WIN_PATH = MainApp.LIB_DIR + "\\soundstretch-win-2.1.1\\soundstretch.exe";
    /**
     * location of the tempo shift tool (in Mac OS X).
     */
    private static final String SS_EXE_MAC_PATH = MainApp.LIB_DIR + "/soundstretch-mac-2.1.2/SoundStretch";

    /**
     * tempo that the users selected
     */
    private double tempo;

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    /**
     * apply tempo shift effect on a song.
     *
     * @param  wavPath        WAV song location
     * @param  transformCount current number of transforms
     * @return                transformed song location
     * @throws Exception      throws when the effect cannot be applied on the song.
     */
    @Override
    public String apply(String wavPath, int transformCount) throws Exception {
        File transformedWav = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR + File.separatorChar + String.format(MyPlayer.TRANSFORM_WAV, transformCount));
        int tempoSs = Double.valueOf((this.tempo - 1) * 100).intValue();
        String ssExePath = null;
        String os = System.getProperty("os.name");

        if (os.toLowerCase().startsWith("windows")) {
            ssExePath = TempoShift.SS_EXE_WIN_PATH;
        }
        else if (os.toLowerCase().startsWith("mac os")) {
            ssExePath = TempoShift.SS_EXE_MAC_PATH;
        }

        ProcessBuilder pb = new ProcessBuilder(ssExePath, wavPath, transformedWav.getAbsolutePath(), String.format("-tempo=%d", tempoSs));
        Process proc = pb.start();
        proc.waitFor();

        return transformedWav.getAbsolutePath();
    }
}
