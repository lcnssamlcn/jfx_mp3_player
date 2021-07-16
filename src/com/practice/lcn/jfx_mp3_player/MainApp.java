package com.practice.lcn.jfx_mp3_player;

import com.practice.lcn.jfx_mp3_player.controller.MainController;
import com.practice.lcn.jfx_mp3_player.util.OsUtil;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;

import java.io.File;

import java.awt.Taskbar;
import java.awt.Toolkit;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * JavaFX MP3 Player application. Note that this application only supports
 * <ul>
 *     <li>Windows x64</li>
 *     <li>Mac OS X x64</li>
 * </ul>
 *
 * @author Sam Leung
 * @version 1.0
 */
public class MainApp extends Application {
    private static Logger logger = LogManager.getLogger(MainApp.class.getName());

    /**
     * application title
     */
    public static final String TITLE = "JavaFX MP3 Player";
    /**
     * application version
     */
    public static final String VERSION = "1.0";
    /**
     * author of this JavaFX application
     */
    public static final String AUTHOR = "Sam Leung";
    /**
     * application icon path
     */
    private static final String APPL_ICON_PATH = "/img/ico_mp3_player.png";
    /**
     * width of the application
     */
    private static final double WINDOW_WIDTH = 400;
    /**
     * height of the application
     */
    private static final double WINDOW_HEIGHT = 300;

    /**
     * "File -> Settings" window's title
     */
    public static final String TITLE_FILE_SETTINGS = "Settings";
    /**
     * width of the "File -> Settings" window
     */
    public static final double FILE_SETTINGS_WINDOW_WIDTH = 800;
    /**
     * height of the "File -> Settings" window
     */
    public static final double FILE_SETTINGS_WINDOW_HEIGHT = 500;
    /**
     * "File -> Settings -> Styling" internal page name
     */
    public static final String FILE_SETTINGS_STYLING_PAGE = "styling";
    /**
     * "File -> Settings -> Styling -> Color" internal page name
     */
    public static final String FILE_SETTINGS_STYLING_COLOR_PAGE = "styling_color";

    /**
     * "Effect -> Change Tempo" window's title
     */
    public static final String TITLE_EFFECTS_CT = "Change Tempo";
    /**
     * width of the "Effect -> Change Tempo" window
     */
    public static final double EFFECTS_CT_WINDOW_WIDTH = 500;
    /**
     * height of the "Effect -> Change Tempo" window
     */
    public static final double EFFECTS_CT_WINDOW_HEIGHT = 300;

    /**
     * "Help -> About" window's title
     */
    public static final String TITLE_HELP_ABOUT = "About";
    /**
     * width of the "Help -> About" window
     */
    public static final double HELP_ABOUT_WINDOW_WIDTH = 300;
    /**
     * height of the "Help -> About" window
     */
    public static final double HELP_ABOUT_WINDOW_HEIGHT = 200;

    /**
     * application FXML layout path
     */
    private static final String MAIN_LAYOUT_PATH = "/layout/main.fxml";
    /**
     * "File -> Settings" window's FXML layout path
     */
    public static final String FILE_SETTINGS_LAYOUT_PATH = "/layout/file/settings.fxml";
    /**
     * "File -> Settings -> Styling -> Color" window's FXML layout path
     */
    public static final String FILE_SETTINGS_STYLING_COLOR_LAYOUT_PATH = "/layout/file/settings_styling_color.fxml";
    /**
     * "Effects -> Change Tempo" window's FXML layout path
     */
    public static final String EFFECTS_CT_LAYOUT_PATH = "/layout/effect/change_tempo.fxml";
    /**
     * "Help -> About" window's FXML layout path
     */
    public static final String HELP_ABOUT_LAYOUT_PATH = "/layout/help/about.fxml";

    /**
     * application css path
     */
    public static final String CSS_MAIN_PATH = "/css/main.css";

    /**
     * if one of the fields
     * <ul>
     *     <li>song title</li>
     *     <li>song artist</li>
     *     <li>song album</li>
     * </ul>
     * is unknown, fill them with {@link com.practice.lcn.jfx_mp3_player.MainApp#TRACK_UNKNOWN_CATEGORY}.
     */
    public static final String TRACK_UNKNOWN_CATEGORY = "???";
    /**
     * if mp3 is not loaded yet, fill the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime} and {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime} with {@link com.practice.lcn.jfx_mp3_player.MainApp#TRACK_TIME_DEFAULT}.
     */
    public static final String TRACK_TIME_DEFAULT = "--:--";
    /**
     * time format for {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTime} and {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTime}.
     */
    public static final String TRACK_TIME_FORMAT = "%02d:%02d";

    /**
     * application settings filename
     */
    public static final String CONFIG_JSON = "config.json";

    private static final String ERR_UNSUPPORTED_OS = "Your OS is not supported to use this application. Sorry.";

    public static final String LIB_DIR = "lib";
    /**
     * application data directory. We use this directory to store
     * <ul>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.MainApp#CONFIG_JSON}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#BASE_WAV}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#TRANSFORM_WAV}</li>
     * </ul>
     */
    public static final String APPL_DATA_DIR = OsUtil.getLocalAppDataDir() + File.separatorChar + MainApp.TITLE;
    /**
     * <p>
     * directory to store
     * </p>
     * <ul>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#BASE_WAV}</li>
     *     <li>{@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#TRANSFORM_WAV}</li>
     * </ul>
     * <p>
     * This directory will be located at {@link com.practice.lcn.jfx_mp3_player.MainApp#APPL_DATA_DIR}/{@link com.practice.lcn.jfx_mp3_player.MainApp#PROCESSING_DIR}.
     * </p>
     */
    public static final String PROCESSING_DIR = "processing";

    /**
     * application controller
     */
    private MainController mc;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(MainApp.MAIN_LAYOUT_PATH));
        this.mc = new MainController();
        this.mc.setMainWindow(stage);
        loader.setController(this.mc);
        BorderPane root = loader.<BorderPane>load();

        stage.setTitle(MainApp.TITLE);
        stage.setResizable(false);
        stage.setScene(new Scene(root, MainApp.WINDOW_WIDTH, MainApp.WINDOW_HEIGHT));
        stage.getIcons().add(new Image(MainApp.class.getResourceAsStream(MainApp.APPL_ICON_PATH)));

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.mc.destroy();
    }

    /**
     * initialize dock application icon in Mac OS X
     */
    private static void initOsxDockIcon() {
        final Toolkit dt = Toolkit.getDefaultToolkit();
        final URL urlApplIcon = MainApp.class.getResource(MainApp.APPL_ICON_PATH);
        final java.awt.Image imgApplIcon = dt.getImage(urlApplIcon);

        final Taskbar taskbar = Taskbar.getTaskbar();

        try {
            taskbar.setIconImage(imgApplIcon);
        }
        catch (UnsupportedOperationException|SecurityException e) {
            logger.error("Unable to initialize dock icon for Mac OS X.", e);
        }
    }

    public static void main(String args[]) {
        String os = System.getProperty("os.name");
        if (!os.toLowerCase().startsWith("windows") && !os.toLowerCase().startsWith("mac os")) {
            System.err.println(MainApp.ERR_UNSUPPORTED_OS);
            System.exit(1);
        }

        if (os.toLowerCase().startsWith("mac os")) {
            MainApp.initOsxDockIcon();
        }

        Application.launch(args);
    }
}
