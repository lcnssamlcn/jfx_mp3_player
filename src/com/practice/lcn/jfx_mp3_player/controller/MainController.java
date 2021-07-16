package com.practice.lcn.jfx_mp3_player.controller;

import com.practice.lcn.jfx_mp3_player.MainApp;
import com.practice.lcn.jfx_mp3_player.media.MyPlayer;
import com.practice.lcn.jfx_mp3_player.util.OsUtil;
import com.practice.lcn.jfx_mp3_player.util.Mp3Util;
import com.practice.lcn.jfx_mp3_player.util.FxNodeUtil;
import com.practice.lcn.jfx_mp3_player.util.FxColorUtil;
import com.practice.lcn.jfx_mp3_player.util.AudioFormatConverter;
import com.practice.lcn.jfx_mp3_player.util.TimeConverter;
import com.practice.lcn.jfx_mp3_player.model.Config;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import java.io.File;

import java.nio.file.Files;

import org.apache.tika.metadata.Metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Application controller.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class MainController implements Initializable, MyFxmlController {
    private static Logger logger = LogManager.getLogger(MainController.class.getName());

    @FXML
    private BorderPane root;
    /**
     * "File -> Import" button
     */
    @FXML
    private MenuItem miFileImport;
    /**
     * "Effects -> Change Tempo" button
     */
    @FXML
    private MenuItem miEffectsChangeTempo;
    @FXML
    private VBox content;
    /**
     * current play time of the song
     */
    @FXML
    private Label labelSongCurrentTime;
    /**
     * allowing users to seek which part they want to listen the song.
     */
    @FXML
    private Slider seekbar;
    /**
     * total time of the song
     */
    @FXML
    private Label labelSongTotalTime;
    /**
     * press this button to play/resume the song.
     */
    @FXML
    private Button btnPlay;
    /**
     * press this button to pause the song temporarily. Users can resume the song later.
     */
    @FXML
    private Button btnPause;
    /**
     * press this button to stop the song and place the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} to the start.
     */
    @FXML
    private Button btnStop;
    /**
     * volume bar to allow users to adjust song volume.
     */
    @FXML
    private Slider sliderVol;
    /**
     * press this button to mute the song.
     */
    @FXML
    private Button btnVolMin;
    /**
     * press this button to maximize the song volume.
     */
    @FXML
    private Button btnVolMax;
    /**
     * button to enable/disable song repeat.
     */
    @FXML
    private ToggleButton btnRepeat;

    /**
     * song metadata container storing
     * <ul>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}</li>
     * </ul>
     */
    private VBox rootMetadata;
    /**
     * song title label
     */
    private Label labelSongTitle;
    /**
     * song artist label
     */
    private Label labelSongArtist;
    /**
     * song album label
     */
    private Label labelSongAlbum;

    /**
     * application window
     */
    private Stage mainWindow;
    /**
     * "File -> Settings" window
     */
    private Stage fileSettingsWindow;
    /**
     * "Effects -> Change Tempo" window
     */
    private Stage effectsCtWindow;
    /**
     * "Help -> About" window
     */
    private Stage helpAboutWindow;
    /**
     * internal music player
     */
    private MyPlayer mp;
    /**
     * mp3 path selected by users.
     */
    private String mp3Path;

    /**
     * default foreground color for all windows' labels
     */
    private String allWindowsFgColor;
    /**
     * default background color for all windows' labels
     */
    private String allWindowsBgColor;
    /**
     * foreground color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     */
    private String labelSongTitleFgColor;
    /**
     * background color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     */
    private String labelSongTitleBgColor;
    /**
     * foreground color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     */
    private String labelSongArtistFgColor;
    /**
     * background color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     */
    private String labelSongArtistBgColor;
    /**
     * foreground color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     */
    private String labelSongAlbumFgColor;
    /**
     * background color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     */
    private String labelSongAlbumBgColor;
    /**
     * background color for the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadata}.
     */
    private String rootMetadataBgColor;
    /**
     * foreground color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     */
    private String labelSongCurrentTimeFgColor;
    /**
     * background color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     */
    private String labelSongCurrentTimeBgColor;
    /**
     * foreground color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     */
    private String labelSongTotalTimeFgColor;
    /**
     * background color for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     */
    private String labelSongTotalTimeBgColor;

    /**
     * application config
     */
    private Config conf;

    /**
     * controller for "File -> Settings" window.
     */
    private FileSettingsController fsc;
    /**
     * controller for "Effects -> Change Tempo" window.
     */
    private EffectsChangeTempoController ectc;
    /**
     * controller for "Help -> About" window.
     */
    private HelpAboutController hac;

    /**
     * thread to update {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} periodically. When the song is playing, this thread will be executed and started updating {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} based on music player's current play time.
     */
    private Thread tSeekbarUpdate;
    /**
     * task for updating {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar}. It is used by {@link com.practice.lcn.jfx_mp3_player.controller.MainController#tSeekbarUpdate} to restart the thread easily.
     */
    private Runnable taskSeekbarUpdate = new Runnable() {
        @Override
        public void run() {
            while (mp.getState() == MyPlayer.STATE.RUNNING) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        int songCurrentTimeSec = Long.valueOf(mp.getSongCurrentTime()).intValue();
                        updateSeekbar(songCurrentTimeSec);
                    }
                });

                try {
                    Thread.sleep(50);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * initialize
     * <ul>
     *     <li>skin color</li>
     *     <li>layout ratio</li>
     *     <li>state of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnRepeat}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.controller.MainController#sliderVol}</li>
     * </ul>
     * and load them from {@link com.practice.lcn.jfx_mp3_player.controller.MainController#conf}.
     *
     * @param location  N/A
     * @param resources N/A
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mp = null;
        this.mp3Path = null;
        this.rootMetadata = null;
        this.labelSongTitle = null;
        this.labelSongArtist = null;
        this.labelSongAlbum = null;

        this.conf = null;
        try {
            this.conf = this.loadSettings();
        }
        catch (Exception e) {
            logger.error("Unable to load settings.", e);
        }

        if (this.conf == null) {
            this.allWindowsFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.allWindowsBgColor = FxColorUtil.toRgba(Color.WHITE);
            this.labelSongTitleFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.labelSongTitleBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);
            this.labelSongArtistFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.labelSongArtistBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);
            this.labelSongAlbumFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.labelSongAlbumBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);
            this.rootMetadataBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);
            this.labelSongCurrentTimeFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.labelSongCurrentTimeBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);
            this.labelSongTotalTimeFgColor = FxColorUtil.toRgba(Color.BLACK);
            this.labelSongTotalTimeBgColor = FxColorUtil.toRgba(Color.TRANSPARENT);

            this.btnRepeat.setSelected(false);
            this.sliderVol.setValue(1);
        }
        else {
            this.allWindowsFgColor = this.conf.getAllWindowsFgColor();
            this.allWindowsBgColor = this.conf.getAllWindowsBgColor();
            this.labelSongTitleFgColor = this.conf.getLabelSongTitleFgColor();
            this.labelSongTitleBgColor = this.conf.getLabelSongTitleBgColor();
            this.labelSongArtistFgColor = this.conf.getLabelSongArtistFgColor();
            this.labelSongArtistBgColor = this.conf.getLabelSongArtistBgColor();
            this.labelSongAlbumFgColor = this.conf.getLabelSongAlbumFgColor();
            this.labelSongAlbumBgColor = this.conf.getLabelSongAlbumBgColor();
            this.rootMetadataBgColor = this.conf.getRootMetadataBgColor();
            this.labelSongCurrentTimeFgColor = this.conf.getLabelSongCurrentTimeFgColor();
            this.labelSongCurrentTimeBgColor = this.conf.getLabelSongCurrentTimeBgColor();
            this.labelSongTotalTimeFgColor = this.conf.getLabelSongTotalTimeFgColor();
            this.labelSongTotalTimeBgColor = this.conf.getLabelSongTotalTimeBgColor();

            this.btnRepeat.setSelected(this.conf.getEnableRepeat());
            this.sliderVol.setValue(this.conf.getVolume());
        }

        this.updateAllWindowsFg();
        this.updateAllWindowsBg();

        this.seekbar.prefWidthProperty().bind(this.mainWindow.widthProperty().multiply(0.7));
        this.labelSongCurrentTime.prefWidthProperty().bind(this.mainWindow.widthProperty().multiply(0.15));
        this.labelSongTotalTime.prefWidthProperty().bind(this.mainWindow.widthProperty().multiply(0.15));

        this.sliderVol.prefWidthProperty().bind(this.mainWindow.widthProperty().multiply(0.7));
    }

    /**
     * describe what happens when "File -> Open" button is clicked.
     * <ol>
     *     <li>Ask users to choose a song file to play. Only ".mp3" files are accepted.</li>
     *     <li>Copy the selected mp3 file to {@link com.practice.lcn.jfx_mp3_player.MainApp#PROCESSING_DIR} and rename the new file as {@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#BASE_MP3}.</li>
     *     <li>Convert the selected mp3 file to wav.</li>
     *     <li>Load the WAV file in {@link com.practice.lcn.jfx_mp3_player.controller.MainController#mp}.</li>
     *     <li>Set tempo from {@link com.practice.lcn.jfx_mp3_player.controller.MainController#conf} and apply it (if exists).</li>
     *     <li>Display song metadata.</li>
     *     <li>Initialize {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar}.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleMiFileOpenClick(ActionEvent event) {
        if (this.mp != null) {
            this.mp.pause();
        }

        FileChooser fc = new FileChooser();
        fc.setTitle("Select a song");
        File fInitialDir = new File(OsUtil.getUserHome());
        if (this.conf != null) {
            if (this.conf.getLastMp3Dir() != null) {
                File fLastMp3Dir = new File(this.conf.getLastMp3Dir());
                if (fLastMp3Dir.exists()) {
                    fInitialDir = fLastMp3Dir;
                }
            }
        }
        fc.setInitialDirectory(fInitialDir);
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*.mp3"));
        File f = fc.showOpenDialog(this.mainWindow);
        try {
            if (f != null) {
                if (this.mp != null) {
                    this.mp.close();
                }
                this.mkProcessingDir();
                File fBaseMp3 = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR + File.separatorChar + MyPlayer.BASE_MP3);
                Files.copy(f.toPath(), fBaseMp3.toPath());
                File fWav = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR + File.separatorChar + MyPlayer.BASE_WAV);
                logger.debug(String.format("handleMiFileOpenClick(): f.getAbsolutePath(): \"%s\"", f.getAbsolutePath()));
                AudioFormatConverter.mp3ToWav(fBaseMp3.getAbsolutePath(), fWav.getAbsolutePath());
                this.mp = new MyPlayer(fWav.getAbsolutePath());
                this.mp.setMfc(this);
                this.miEffectsChangeTempo.setDisable(false);
                if (this.conf != null) {
                    if (this.conf.getTempo() != null) {
                        this.mp.setTempo(this.conf.getTempo());
                    }
                }
                this.mp3Path = f.getAbsolutePath();

                this.displaySongInfo();

                this.initSeekbar();
                this.seekbar.setDisable(false);

                this.restartTSeekBarUpdate();

                this.mp.setVolume(Double.valueOf(this.sliderVol.getValue()).floatValue());

                this.mp.setEnableRepeat(this.btnRepeat.isSelected());
            }
        }
        catch (Exception e) {
            logger.error("Unable to initialize mp3 player.", e);
        }
    }

    /**
     * describing what happens when "File -> Settings" button is clicked.
     * <ol>
     *     <li>Initialize {@link com.practice.lcn.jfx_mp3_player.controller.MainController#fileSettingsWindow}</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleMiFileSettingsClick(ActionEvent event) {
        try {
            this.fileSettingsWindow = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(MainApp.FILE_SETTINGS_LAYOUT_PATH));
            this.fsc = new FileSettingsController();
            this.fsc.setParentController(this);
            loader.setController(this.fsc);
            VBox rootFileSettings = loader.<VBox>load();

            this.fileSettingsWindow.setTitle(MainApp.TITLE_FILE_SETTINGS);
            this.fileSettingsWindow.setScene(new Scene(rootFileSettings, MainApp.FILE_SETTINGS_WINDOW_WIDTH, MainApp.FILE_SETTINGS_WINDOW_HEIGHT));
            this.updateAllWindowsBg();

            this.fileSettingsWindow.show();
        }
        catch (Exception e) {
            logger.error("Unable to initialize \"File -> Settings\" window.", e);
        }
    }

    /**
     * describing what happens when "Effects -> Change Tempo" button is clicked.
     * <ol>
     *     <li>Initialize {@link com.practice.lcn.jfx_mp3_player.controller.MainController#effectsCtWindow}</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleMiEffectsChangeTempoClick(ActionEvent event) {
        try {
            this.mp.pause();

            this.effectsCtWindow = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(MainApp.EFFECTS_CT_LAYOUT_PATH));
            this.ectc = new EffectsChangeTempoController();
            this.ectc.setParentController(this);
            loader.setController(this.ectc);
            AnchorPane rootEffectsCt = loader.<AnchorPane>load();

            this.ectc.setTempo(this.mp.getTempo());

            this.effectsCtWindow.setTitle(MainApp.TITLE_EFFECTS_CT);
            this.effectsCtWindow.setScene(new Scene(rootEffectsCt, MainApp.EFFECTS_CT_WINDOW_WIDTH, MainApp.EFFECTS_CT_WINDOW_HEIGHT));
            this.updateEffectsCtWindowFg();
            this.updateEffectsCtWindowBg();

            this.effectsCtWindow.show();
        }
        catch (Exception e) {
            logger.error("Unable to initialize \"Effects -> Change Tempo\" window.", e);
        }
    }

    /**
     * describing what happens when "Help -> About" button is clicked.
     * <ol>
     *     <li>Initialize {@link com.practice.lcn.jfx_mp3_player.controller.MainController#helpAboutWindow}</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleMiHelpAboutClick(ActionEvent event) {
        try {
            this.helpAboutWindow = new Stage();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(MainApp.HELP_ABOUT_LAYOUT_PATH));
            this.hac = new HelpAboutController();
            this.hac.setParentController(this);
            loader.setController(this.hac);
            AnchorPane rootHelpAbout = loader.<AnchorPane>load();

            this.helpAboutWindow.setTitle(MainApp.TITLE_HELP_ABOUT);
            this.helpAboutWindow.setScene(new Scene(rootHelpAbout, MainApp.HELP_ABOUT_WINDOW_WIDTH, MainApp.HELP_ABOUT_WINDOW_HEIGHT));
            this.updateAllWindowsFg();
            this.updateAllWindowsBg();

            this.helpAboutWindow.show();
        }
        catch (Exception e) {
            logger.error("Unable to initialize \"Help -> About\" window.", e);
        }
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnPlay} is clicked.
     * <ol>
     *     <li>Play/Resume the song.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnPlayClick(ActionEvent event) {
        if (this.mp == null) {
            return;
        }
        if (this.mp.getState() == MyPlayer.STATE.RUNNING) {
            return;
        }

        if (this.mp.getState() == MyPlayer.STATE.STOP) {
            this.mp.start();
        }
        else if (this.mp.getState() == MyPlayer.STATE.PAUSE) {
            this.mp.resume();
        }
        this.restartTSeekBarUpdate();
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnPause} is clicked.
     * <ol>
     *     <li>Pause the song.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnPauseClick(ActionEvent event) {
        if (this.mp == null) {
            return;
        }

        this.mp.pause();
        this.closeTSeekbarUpdate();
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnStop} is clicked.
     * <ol>
     *     <li>Stop the song.</li>
     *     <li>Move the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} to the start.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnStopClick(ActionEvent event) {
        if (this.mp == null) {
            return;
        }

        this.mp.stop();
        this.closeTSeekbarUpdate();
        this.updateSeekbar(0);
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnRepeat} is clicked.
     * <ol>
     *     <li>Repeat the song if {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnRepeat} is enabled. Otherwise, stop the song once the song ends.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnRepeatClick(ActionEvent event) {
        if (this.mp == null) {
            return;
        }

        this.mp.setEnableRepeat(this.btnRepeat.isSelected());
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnVolMin} is clicked.
     * <ol>
     *     <li>Mute the song.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnVolMinClick(ActionEvent event) {
        this.sliderVol.setValue(0);
        if (this.mp != null) {
            this.mp.setVolume(0f);
        }
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnVolMax} is clicked.
     * <ol>
     *     <li>Maximum the song volume.</li>
     * </ol>
     *
     * @param event click event
     */
    @FXML
    public void handleBtnVolMaxClick(ActionEvent event) {
        this.sliderVol.setValue(1);
        if (this.mp != null) {
            this.mp.setVolume(1f);
        }
    }

    /**
     * describing what happens when {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} knot is dragged or the users click the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} slider.
     * <ol>
     *     <li>Play the song where the users seek.</li>
     * </ol>
     * <p>
     * Note that this method does not support seeking when the song is playing. Users have to pause or stop the song to seek.
     * </p>
     *
     * @param event mouse released event
     */
    @FXML
    public void handleSeekbarMouseReleased(MouseEvent event) {
        if (mp == null) {
            return;
        }

        double seekbarVal = this.seekbar.getValue();
        int seekbarValInt = Double.valueOf(seekbarVal).intValue();
        if (mp.getState() == MyPlayer.STATE.STOP) {
            mp.seek(seekbarValInt);
            mp.setState(MyPlayer.STATE.PAUSE);
            this.updateSeekbar(seekbarValInt);
        }
        else if (mp.getState() == MyPlayer.STATE.PAUSE) {
            mp.seek(seekbarValInt);
            this.updateSeekbar(seekbarValInt);
        }
    }

    /**
     * describing what happens when {@link com.practice.lcn.jfx_mp3_player.controller.MainController#sliderVol} knot is dragged or the users click the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#sliderVol} slider.
     * <ol>
     *     <li>Adjust song volume.</li>
     * </ol>
     *
     * @param event mouse released event
     */
    @FXML
    public void handleVolMouseReleased(MouseEvent event) {
        double sliderVolVal = this.sliderVol.getValue();
        float sliderVolValFloat = Double.valueOf(sliderVolVal).floatValue();
        if (this.mp != null) {
            this.mp.setVolume(sliderVolValFloat);
        }
    }

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * change tempo of the current song
     *
     * @param tempo tempo set by users
     */
    public void changeTempo(double tempo) {
        try {
            this.mp.setTempo(tempo);
        }
        catch (Exception e) {
            logger.error("Unable to change tempo.", e);
        }
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsFgColor} and update the default foreground color of all windows.
     *
     * @param allWindowsFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsFgColor}
     */
    public void setAllWindowsFgColor(String allWindowsFgColor) {
        if (this.allWindowsFgColor == allWindowsFgColor) {
            return;
        }

        this.allWindowsFgColor = allWindowsFgColor;
        this.updateAllWindowsFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsBgColor} and update the default background color of all windows.
     *
     * @param allWindowsFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsBgColor}
     */
    public void setAllWindowsBgColor(String allWindowsBgColor) {
        if (this.allWindowsBgColor == allWindowsBgColor) {
            return;
        }

        this.allWindowsBgColor = allWindowsBgColor;
        this.updateAllWindowsBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleFgColor} and update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     *
     * @param labelSongTitleFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleFgColor}
     */
    public void setLabelSongTitleFgColor(String labelSongTitleFgColor) {
        if (this.labelSongTitleFgColor == labelSongTitleFgColor) {
            return;
        }

        this.labelSongTitleFgColor = labelSongTitleFgColor;
        this.updateLabelSongTitleFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     *
     * @param labelSongTitleBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleBgColor}
     */
    public void setLabelSongTitleBgColor(String labelSongTitleBgColor) {
        if (this.labelSongTitleBgColor == labelSongTitleBgColor) {
            return;
        }

        this.labelSongTitleBgColor = labelSongTitleBgColor;
        this.updateLabelSongTitleBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistFgColor} and update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     *
     * @param labelSongArtistFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistFgColor}
     */
    public void setLabelSongArtistFgColor(String labelSongArtistFgColor) {
        if (this.labelSongArtistFgColor == labelSongArtistFgColor) {
            return;
        }

        this.labelSongArtistFgColor = labelSongArtistFgColor;
        this.updateLabelSongArtistFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     *
     * @param labelSongArtistBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistBgColor}
     */
    public void setLabelSongArtistBgColor(String labelSongArtistBgColor) {
        if (this.labelSongArtistBgColor == labelSongArtistBgColor) {
            return;
        }

        this.labelSongArtistBgColor = labelSongArtistBgColor;
        this.updateLabelSongArtistBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumFgColor} and update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     *
     * @param labelSongAlbumFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumFgColor}
     */
    public void setLabelSongAlbumFgColor(String labelSongAlbumFgColor) {
        if (this.labelSongAlbumFgColor == labelSongAlbumFgColor) {
            return;
        }

        this.labelSongAlbumFgColor = labelSongAlbumFgColor;
        this.updateLabelSongAlbumFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     *
     * @param labelSongAlbumBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumBgColor}
     */
    public void setLabelSongAlbumBgColor(String labelSongAlbumBgColor) {
        if (this.labelSongAlbumBgColor == labelSongAlbumBgColor) {
            return;
        }

        this.labelSongAlbumBgColor = labelSongAlbumBgColor;
        this.updateLabelSongAlbumBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadataBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadata}.
     *
     * @param rootMetadataBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadataBgColor}
     */
    public void setRootMetadataBgColor(String rootMetadataBgColor) {
        if (this.rootMetadataBgColor == rootMetadataBgColor) {
            return;
        }

        this.rootMetadataBgColor = rootMetadataBgColor;
        this.updateRootMetadataBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeFgColor} and update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     *
     * @param labelSongCurrentTimeFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeFgColor}
     */
    public void setLabelSongCurrentTimeFgColor(String labelSongCurrentTimeFgColor) {
        if (this.labelSongCurrentTimeFgColor == labelSongCurrentTimeFgColor) {
            return;
        }

        this.labelSongCurrentTimeFgColor = labelSongCurrentTimeFgColor;
        this.updateLabelSongCurrentTimeFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     *
     * @param labelSongCurrentTimeBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeBgColor}
     */
    public void setLabelSongCurrentTimeBgColor(String labelSongCurrentTimeBgColor) {
        if (this.labelSongCurrentTimeBgColor == labelSongCurrentTimeBgColor) {
            return;
        }

        this.labelSongCurrentTimeBgColor = labelSongCurrentTimeBgColor;
        this.updateLabelSongCurrentTimeBg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeFgColor} and update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     *
     * @param labelSongTotalTimeFgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeFgColor}
     */
    public void setLabelSongTotalTimeFgColor(String labelSongTotalTimeFgColor) {
        if (this.labelSongTotalTimeFgColor == labelSongTotalTimeFgColor) {
            return;
        }

        this.labelSongTotalTimeFgColor = labelSongTotalTimeFgColor;
        this.updateLabelSongTotalTimeFg();
    }

    /**
     * set {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeBgColor} and update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     *
     * @param labelSongTotalTimeBgColor see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeBgColor}
     */
    public void setLabelSongTotalTimeBgColor(String labelSongTotalTimeBgColor) {
        if (this.labelSongTotalTimeBgColor == labelSongTotalTimeBgColor) {
            return;
        }

        this.labelSongTotalTimeBgColor = labelSongTotalTimeBgColor;
        this.updateLabelSongTotalTimeBg();
    }

    public String getAllWindowsFgColor() {
        return this.allWindowsFgColor;
    }

    public String getAllWindowsBgColor() {
        return this.allWindowsBgColor;
    }

    public String getLabelSongTitleFgColor() {
        return this.labelSongTitleFgColor;
    }

    public String getLabelSongTitleBgColor() {
        return this.labelSongTitleBgColor;
    }

    public String getLabelSongArtistFgColor() {
        return this.labelSongArtistFgColor;
    }

    public String getLabelSongArtistBgColor() {
        return this.labelSongArtistBgColor;
    }

    public String getLabelSongAlbumFgColor() {
        return this.labelSongAlbumFgColor;
    }

    public String getLabelSongAlbumBgColor() {
        return this.labelSongAlbumBgColor;
    }

    public String getRootMetadataBgColor() {
        return this.rootMetadataBgColor;
    }

    public String getLabelSongCurrentTimeFgColor() {
        return this.labelSongCurrentTimeFgColor;
    }

    public String getLabelSongCurrentTimeBgColor() {
        return this.labelSongCurrentTimeBgColor;
    }

    public String getLabelSongTotalTimeFgColor() {
        return this.labelSongTotalTimeFgColor;
    }

    public String getLabelSongTotalTimeBgColor() {
        return this.labelSongTotalTimeBgColor;
    }

    /**
     * restart the thread {@link com.practice.lcn.jfx_mp3_player.controller.MainController#tSeekbarUpdate}.
     */
    public void restartTSeekBarUpdate() {
        this.closeTSeekbarUpdate();
        this.tSeekbarUpdate = new Thread(this.taskSeekbarUpdate);
        this.tSeekbarUpdate.start();
    }

    /**
     * close and deallocate the "File -> Settings" window.
     */
    public void closeFileSettingsWindow() {
        if (this.fileSettingsWindow != null) {
            this.fileSettingsWindow.close();
            this.fileSettingsWindow = null;
            this.fsc = null;
        }
    }

    /**
     * close and deallocate the "Effects -> Change Tempo" window.
     */
    public void closeEffectsCtWindow() {
        if (this.effectsCtWindow != null) {
            this.effectsCtWindow.close();
            this.effectsCtWindow = null;
            this.ectc = null;
        }
    }

    /**
     * close and deallocate the "Help -> About" window.
     */
    public void closeHelpAboutWindow() {
        if (this.helpAboutWindow != null) {
            this.helpAboutWindow.close();
            this.helpAboutWindow = null;
            this.hac = null;
        }
    }

    /**
     * kill the thread {@link com.practice.lcn.jfx_mp3_player.controller.MainController#tSeekbarUpdate}.
     */
    public void closeTSeekbarUpdate() {
        try {
            if (this.tSeekbarUpdate != null) {
                this.tSeekbarUpdate.join();
                this.tSeekbarUpdate = null;
            }
        }
        catch (InterruptedException e) {

        }
    }

    /**
     * before terminating this application,
     * <ul>
     *     <li>close and deallocate all resources</li>
     *     <li>save current application settings, e.g. skin color, to {@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON}</li>
     * </ul>
     */
    public void destroy() {
        this.closeFileSettingsWindow();
        this.closeEffectsCtWindow();
        this.closeHelpAboutWindow();
        if (this.mp != null) {
            this.mp.close();
        }
        try {
            this.saveSettings();
        }
        catch (Exception e) {
            logger.error("Unable to save settings.", e);
        }
    }

    /**
     * save current application settings, e.g. skin color, to {@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON}.
     *
     * @throws Exception throws when {@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON} cannot be generated.
     */
    private void saveSettings() throws Exception {
        Config c = new Config();
        c.setAllWindowsFgColor(this.allWindowsFgColor);
        c.setAllWindowsBgColor(this.allWindowsBgColor);
        c.setLabelSongTitleFgColor(this.labelSongTitleFgColor);
        c.setLabelSongTitleBgColor(this.labelSongTitleBgColor);
        c.setLabelSongArtistFgColor(this.labelSongArtistFgColor);
        c.setLabelSongArtistBgColor(this.labelSongArtistBgColor);
        c.setLabelSongAlbumFgColor(this.labelSongAlbumFgColor);
        c.setLabelSongAlbumBgColor(this.labelSongAlbumBgColor);
        c.setRootMetadataBgColor(this.rootMetadataBgColor);
        c.setLabelSongCurrentTimeFgColor(this.labelSongCurrentTimeFgColor);
        c.setLabelSongCurrentTimeBgColor(this.labelSongCurrentTimeBgColor);
        c.setLabelSongTotalTimeFgColor(this.labelSongTotalTimeFgColor);
        c.setLabelSongTotalTimeBgColor(this.labelSongTotalTimeBgColor);
        c.setEnableRepeat(this.btnRepeat.isSelected());
        c.setVolume(this.sliderVol.getValue());
        if (this.mp != null) {
            c.setTempo(this.mp.getTempo());
        }
        else {
            c.setTempo(null);
        }
        if (this.mp3Path != null) {
            File fMp3Path = new File(this.mp3Path);
            c.setLastMp3Dir(fMp3Path.getParent());
        }
        else {
            c.setLastMp3Dir(null);
        }

        ObjectMapper om = new ObjectMapper();
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        File fApplDataDir = new File(MainApp.APPL_DATA_DIR);
        if (!fApplDataDir.exists()) {
            fApplDataDir.mkdir();
        }
        File output = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.CONFIG_JSON);
        om.writer(printer).writeValue(output, c);
    }

    /**
     * load application settings from {@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON}.
     *
     * @return current settings
     * @throws Exception throws when {@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON} cannot be loaded even if it exists.
     */
    private Config loadSettings() throws Exception {
        File input = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.CONFIG_JSON);
        if (!input.exists()) {
            return null;
        }

        ObjectMapper om = new ObjectMapper();
        Config c = om.readValue(input, Config.class);

        return c;
    }

    /**
     * obtain song metadata and display it.
     *
     * @throws Exception throws when the song metadata cannot be obtained.
     */
    private void displaySongInfo() throws Exception {
        File fSong = new File(this.mp3Path);
        this.mainWindow.setTitle(fSong.getName());

        Metadata md = Mp3Util.getMetadata(this.mp3Path);
        List<String> mdn = Arrays.asList(md.names());
        logger.debug(String.format("mdn: \"%s\"", mdn));
        String songTitle = md.get("title");
        String artist = md.get("xmpDM:artist");
        String album = md.get("xmpDM:album");
        if (this.rootMetadata == null) {
            this.labelSongTitle = new Label();
            this.labelSongTitle.setStyle("-fx-font-size: 20px;");
            this.updateLabelSongTitleFg();
            this.updateLabelSongTitleBg();
            this.labelSongArtist = new Label();
            this.updateLabelSongArtistFg();
            this.updateLabelSongArtistBg();
            this.labelSongAlbum = new Label();
            this.updateLabelSongAlbumFg();
            this.updateLabelSongAlbumBg();
            this.rootMetadata = new VBox();
            this.rootMetadata.setSpacing(10);
            this.updateRootMetadataBg();
            this.rootMetadata.getChildren().addAll(this.labelSongTitle, this.labelSongArtist, this.labelSongAlbum);
            this.content.getChildren().add(0, this.rootMetadata);
        }
        this.labelSongTitle.setText(songTitle == null ? MainApp.TRACK_UNKNOWN_CATEGORY : songTitle);
        this.labelSongArtist.setText(artist == null ? MainApp.TRACK_UNKNOWN_CATEGORY : artist);
        this.labelSongAlbum.setText(album == null ? MainApp.TRACK_UNKNOWN_CATEGORY : album);
    }

    /**
     * initialize seekbar when the mp3 is loaded.
     */
    public void initSeekbar() {
        int songTotalTime = Long.valueOf(this.mp.getSongTotalTime()).intValue();
        logger.debug(String.format("songTotalTime: %d", songTotalTime));
        Map<String, Integer> mmss = TimeConverter.ss2mmss(songTotalTime);
        this.labelSongTotalTime.setText(String.format(MainApp.TRACK_TIME_FORMAT, mmss.get(TimeConverter.MINUTE), mmss.get(TimeConverter.SECOND)));
        this.seekbar.setMax(songTotalTime);

        this.updateSeekbar(0);
    }

    /**
     * create {@link com.practice.lcn.jfx_mp3_player.MainApp#PROCESSING_DIR} if not exists.
     */
    public void mkProcessingDir() {
        File processingDir = new File(MainApp.APPL_DATA_DIR + File.separatorChar + MainApp.PROCESSING_DIR);
        if (!processingDir.exists()) {
            processingDir.mkdirs();
        }
    }

    /**
     * update {@link com.practice.lcn.jfx_mp3_player.controller.MainController#seekbar} knot's position. Also, update the display {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     *
     * @param songCurrentTimeSec song current play time in seconds
     */
    public void updateSeekbar(int songCurrentTimeSec) {
        this.seekbar.setValue(songCurrentTimeSec);
        Map<String, Integer> mmss = TimeConverter.ss2mmss(songCurrentTimeSec);
        this.labelSongCurrentTime.setText(String.format(MainApp.TRACK_TIME_FORMAT, mmss.get(TimeConverter.MINUTE), mmss.get(TimeConverter.SECOND)));
    }

    /**
     * update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     */
    public void updateLabelSongTitleFg() {
        if (this.labelSongTitle != null) {
            String styleLabelSongTitle = this.labelSongTitle.getStyle();
            logger.debug(String.format("setLabelSongTitleFgColor(): styleLabelSongTitle: \"%s\"", styleLabelSongTitle));
            this.labelSongTitle.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongTitle, "-fx-text-fill", this.labelSongTitleFgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitle}.
     */
    public void updateLabelSongTitleBg() {
        if (this.labelSongTitle != null) {
            String styleLabelSongTitle = this.labelSongTitle.getStyle();
            logger.debug(String.format("setLabelSongTitleBgColor(): styleLabelSongTitle: \"%s\"", styleLabelSongTitle));
            this.labelSongTitle.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongTitle, "-fx-background-color", this.labelSongTitleBgColor));
        }
    }

    /**
     * update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     */
    public void updateLabelSongArtistFg() {
        if (this.labelSongArtist != null) {
            String styleLabelSongArtist = this.labelSongArtist.getStyle();
            logger.debug(String.format("setLabelSongArtistFgColor(): styleLabelSongArtist: \"%s\"", styleLabelSongArtist));
            this.labelSongArtist.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongArtist, "-fx-text-fill", this.labelSongArtistFgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtist}.
     */
    public void updateLabelSongArtistBg() {
        if (this.labelSongArtist != null) {
            String styleLabelSongArtist = this.labelSongArtist.getStyle();
            logger.debug(String.format("setLabelSongArtistBgColor(): styleLabelSongArtist: \"%s\"", styleLabelSongArtist));
            this.labelSongArtist.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongArtist, "-fx-background-color", this.labelSongArtistBgColor));
        }
    }

    /**
     * update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     */
    public void updateLabelSongAlbumFg() {
        if (this.labelSongAlbum != null) {
            String styleLabelSongAlbum = this.labelSongAlbum.getStyle();
            logger.debug(String.format("setLabelSongAlbumFgColor(): styleLabelSongAlbum: \"%s\"", styleLabelSongAlbum));
            this.labelSongAlbum.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongAlbum, "-fx-text-fill", this.labelSongAlbumFgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbum}.
     */
    public void updateLabelSongAlbumBg() {
        if (this.labelSongAlbum != null) {
            String styleLabelSongAlbum = this.labelSongAlbum.getStyle();
            logger.debug(String.format("setLabelSongAlbumBgColor(): styleLabelSongAlbum: \"%s\"", styleLabelSongAlbum));
            this.labelSongAlbum.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongAlbum, "-fx-background-color", this.labelSongAlbumBgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadata}.
     */
    public void updateRootMetadataBg() {
        if (this.rootMetadata != null) {
            String stylerootMetadata = this.rootMetadata.getStyle();
            logger.debug(String.format("setrootMetadataBgColor(): stylerootMetadata: \"%s\"", stylerootMetadata));
            this.rootMetadata.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(stylerootMetadata, "-fx-background-color", this.rootMetadataBgColor));
        }
    }

    /**
     * update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     */
    public void updateLabelSongCurrentTimeFg() {
        if (this.labelSongCurrentTime != null) {
            String styleLabelSongCurrentTime = this.labelSongCurrentTime.getStyle();
            logger.debug(String.format("setLabelSongCurrentTimeFgColor(): styleLabelSongCurrentTime: \"%s\"", styleLabelSongCurrentTime));
            this.labelSongCurrentTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongCurrentTime, "-fx-text-fill", this.labelSongCurrentTimeFgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime}.
     */
    public void updateLabelSongCurrentTimeBg() {
        if (this.labelSongCurrentTime != null) {
            String styleLabelSongCurrentTime = this.labelSongCurrentTime.getStyle();
            logger.debug(String.format("setLabelSongCurrentTimeBgColor(): styleLabelSongCurrentTime: \"%s\"", styleLabelSongCurrentTime));
            this.labelSongCurrentTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongCurrentTime, "-fx-background-color", this.labelSongCurrentTimeBgColor));
        }
    }

    /**
     * update the foreground color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     */
    public void updateLabelSongTotalTimeFg() {
        if (this.labelSongTotalTime != null) {
            String styleLabelSongTotalTime = this.labelSongTotalTime.getStyle();
            logger.debug(String.format("setLabelSongTotalTimeFgColor(): styleLabelSongTotalTime: \"%s\"", styleLabelSongTotalTime));
            this.labelSongTotalTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongTotalTime, "-fx-text-fill", this.labelSongTotalTimeFgColor));
        }
    }

    /**
     * update the background color of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     */
    public void updateLabelSongTotalTimeBg() {
        if (this.labelSongTotalTime != null) {
            String styleLabelSongTotalTime = this.labelSongTotalTime.getStyle();
            logger.debug(String.format("setLabelSongTotalTimeBgColor(): styleLabelSongTotalTime: \"%s\"", styleLabelSongTotalTime));
            this.labelSongTotalTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(styleLabelSongTotalTime, "-fx-background-color", this.labelSongTotalTimeBgColor));
        }
    }

    /**
     * update the default foreground color of "File -> Settings" window.
     */
    public void updateFileSettingsWindowFg() {
        if (this.fileSettingsWindow != null) {
            this.fsc.setRootFgColor(this.allWindowsFgColor);
        }
    }

    /**
     * update the default background color of "File -> Settings" window.
     */
    public void updateFileSettingsWindowBg() {
        if (this.fileSettingsWindow != null) {
            this.fsc.setRootBgColor(this.allWindowsBgColor);
        }
    }

    /**
     * update the default foreground color of "Help -> About" window.
     */
    public void updateHelpAboutWindowFg() {
        if (this.helpAboutWindow != null) {
            this.hac.setRootFgColor(this.allWindowsFgColor);
        }
    }

    /**
     * update the default background color of "Help -> About" window.
     */
    public void updateHelpAboutWindowBg() {
        if (this.helpAboutWindow != null) {
            this.hac.setRootBgColor(this.allWindowsBgColor);
        }
    }

    /**
     * update the default foreground color of "Effects -> Change Tempo" window.
     */
    public void updateEffectsCtWindowFg() {
        if (this.effectsCtWindow != null) {
            this.ectc.setRootFgColor(this.allWindowsFgColor);
        }
    }

    /**
     * update the default background color of "Effects -> Change Tempo" window.
     */
    public void updateEffectsCtWindowBg() {
        if (this.effectsCtWindow != null) {
            this.ectc.setRootBgColor(this.allWindowsBgColor);
        }
    }

    /**
     * update the default foreground color of all windows.
     */
    public void updateAllWindowsFg() {
        this.labelSongCurrentTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelSongCurrentTime.getStyle(), "-fx-text-fill", this.labelSongCurrentTimeFgColor));
        this.labelSongTotalTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelSongTotalTime.getStyle(), "-fx-text-fill", this.labelSongTotalTimeFgColor));
        this.updateFileSettingsWindowFg();
        this.updateEffectsCtWindowFg();
        this.updateHelpAboutWindowFg();
    }

    /**
     * update the default background color of all windows.
     */
    public void updateAllWindowsBg() {
        this.root.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.root.getStyle(), "-fx-background-color", this.allWindowsBgColor));
        this.labelSongCurrentTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelSongCurrentTime.getStyle(), "-fx-background-color", this.labelSongCurrentTimeBgColor));
        this.labelSongTotalTime.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelSongTotalTime.getStyle(), "-fx-background-color", this.labelSongTotalTimeBgColor));
        this.updateFileSettingsWindowBg();
        this.updateEffectsCtWindowBg();
        this.updateHelpAboutWindowBg();
    }
}
