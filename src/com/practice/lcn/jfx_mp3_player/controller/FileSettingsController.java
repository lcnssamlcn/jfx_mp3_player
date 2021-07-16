package com.practice.lcn.jfx_mp3_player.controller;

import com.practice.lcn.jfx_mp3_player.MainApp;
import com.practice.lcn.jfx_mp3_player.util.FxNodeUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URL;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * "File -> Settings" window controller.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class FileSettingsController implements Initializable, MyFxmlController {
    private static Logger logger = LogManager.getLogger(FileSettingsController.class.getName());

    @FXML
    private VBox root;
    /**
     * page displayed on the right pane of {@link com.practice.lcn.jfx_mp3_player.controller.FileSettingsController#sp1}.
     */
    @FXML
    private AnchorPane page;
    @FXML
    private SplitPane sp1;
    /**
     * table of content displayed on the left pane of {@link com.practice.lcn.jfx_mp3_player.controller.FileSettingsController#sp1}.
     */
    @FXML
    private TreeView<String> toc;
    /**
     * press this button to apply changes and close the "File -> Settings" window.
     */
    @FXML
    private Button btnOk;
    /**
     * press this button to discard any changes made and close the "File -> Settings" window.
     */
    @FXML
    private Button btnCancel;
    /**
     * press this button to apply changes without closing the "File -> Settings" window.
     */
    @FXML
    private Button btnApply;

    /**
     * controller that controls this controller.
     */
    private MyFxmlController parentController;

    /**
     * original number of children in {@link com.practice.lcn.jfx_mp3_player.controller.FileSettingsController#page}.
     */
    private int numChildrenInPage;
    /**
     * code of the page that is currently displaying in "File -> Settings" window, e.g. {@link com.practice.lcn.jfx_mp3_player.MainApp#FILE_SETTINGS_STYLING_PAGE} and {@link com.practice.lcn.jfx_mp3_player.MainApp#FILE_SETTINGS_STYLING_COLOR_PAGE}.
     */
    private String activePage;

    /**
     * controller that control the {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootPage}.
     */
    private MyFxmlController pageController;
    /**
     * root of the new page selected by users. It will first be selected by users from the {@link com.practice.lcn.jfx_mp3_player.controller.FileSettingsController#toc}. The page will be generated accordingly and its root will be stored here.
     */
    private Parent rootPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.activePage = null;
        this.numChildrenInPage = this.page.getChildren().size();
        this.pageController = null;
        this.rootPage = null;

        this.sp1.setDividerPosition(0, 0.3);
        this.toc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent> () {
            @Override
            public void handle(MouseEvent event) {
                Node node = event.getPickResult().getIntersectedNode();
                // Accept clicks only on node cells, and not on empty spaces of the TreeView
                if (node instanceof Text || (node instanceof TreeCell<?> && ((TreeCell<String>) node).getText() != null)) {
                    String name = (String) ((TreeItem<String>) toc.getSelectionModel().getSelectedItem()).getValue();
                    logger.debug(String.format("\"%s\" is clicked.", name));

                    try {
                        if (name.equals("Styling") || name.equals("Color")) {
                            handleTiStylingColorClick();
                        }
                    }
                    catch (Exception e) {
                        logger.error("Unable to initialize page.", e);
                    }
                }
            }
        });
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.FileSettingsController#btnOk} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnOkClick(ActionEvent event) {
        this.applySettings();
        ((MainController) parentController).closeFileSettingsWindow();
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#btnCancel} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnCancelClick(ActionEvent event) {
        ((MainController) parentController).closeFileSettingsWindow();
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#btnApply} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnApplyClick(ActionEvent event) {
        this.applySettings();
    }

    /**
     * apply the changes made in "File -> Settings" window, e.g. skin color.
     */
    private void applySettings() {
        if (this.pageController == null) {
            return;
        }

        if (this.pageController instanceof FileSettingsStylingColorController) {
            FileSettingsStylingColorController c = (FileSettingsStylingColorController) this.pageController;
            ((MainController) parentController).setAllWindowsFgColor(c.getAllWindowsFgColor());
            ((MainController) parentController).setAllWindowsBgColor(c.getAllWindowsBgColor());
            ((MainController) parentController).setLabelSongTitleFgColor(c.getSongTitleFgColor());
            ((MainController) parentController).setLabelSongTitleBgColor(c.getSongTitleBgColor());
            ((MainController) parentController).setLabelSongArtistFgColor(c.getSongArtistFgColor());
            ((MainController) parentController).setLabelSongArtistBgColor(c.getSongArtistBgColor());
            ((MainController) parentController).setLabelSongAlbumFgColor(c.getSongAlbumFgColor());
            ((MainController) parentController).setLabelSongAlbumBgColor(c.getSongAlbumBgColor());
            ((MainController) parentController).setRootMetadataBgColor(c.getSongMetadataBgColor());
            ((MainController) parentController).setLabelSongCurrentTimeFgColor(c.getSongCurrentTimeFgColor());
            ((MainController) parentController).setLabelSongCurrentTimeBgColor(c.getSongCurrentTimeBgColor());
            ((MainController) parentController).setLabelSongTotalTimeFgColor(c.getSongTotalTimeFgColor());
            ((MainController) parentController).setLabelSongTotalTimeBgColor(c.getSongTotalTimeBgColor());
        }
    }

    /**
     * describing what happens when button "File -> Settings -> Styling -> Color" is clicked.
     *
     * @throws Exception throws when {@link com.practice.lcn.jfx_mp3_player.MainApp#FILE_SETTINGS_STYLING_COLOR_LAYOUT_PATH} cannot be loaded.
     */
    private void handleTiStylingColorClick() throws Exception {
        if (this.activePage != null && this.activePage.equals(MainApp.FILE_SETTINGS_STYLING_PAGE)) {
            return;
        }

        if (this.pageController == null && this.rootPage == null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainController.class.getResource(MainApp.FILE_SETTINGS_STYLING_COLOR_LAYOUT_PATH));
            this.pageController = new FileSettingsStylingColorController();
            loader.setController(this.pageController);
            this.rootPage = loader.<VBox>load();
        }
        if (this.page.getChildren().size() != this.numChildrenInPage) {
            this.page.getChildren().remove(0);
        }
        this.activePage = MainApp.FILE_SETTINGS_STYLING_PAGE;
        this.loadPreset();
        this.page.getChildren().add(0, this.rootPage);
    }

    public void setParentController(MyFxmlController parentController) {
        this.parentController = parentController;
    }

    /**
     * update all labels' foreground color in "File -> Settings" window.
     *
     * @param color new color to apply
     */
    public void setRootFgColor(String color) {
        if (this.pageController != null && this.pageController instanceof FileSettingsStylingColorController) {
            FileSettingsStylingColorController c = (FileSettingsStylingColorController) this.pageController;
            c.setRootFgColor(color);
        }
    }

    /**
     * update background color of "File -> Settings" window.
     *
     * @param color new color to apply
     */
    public void setRootBgColor(String color) {
        String style = this.root.getStyle();
        this.sp1.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(style, "-fx-background-color", color));

        if (this.pageController != null && this.pageController instanceof FileSettingsStylingColorController) {
            FileSettingsStylingColorController c = (FileSettingsStylingColorController) this.pageController;
            c.setRootBgColor(color);
        }
    }

    /**
     * load application settings set by users.
     */
    private void loadPreset() {
        if (this.activePage == null) {
            return;
        }

        if (this.activePage.equals(MainApp.FILE_SETTINGS_STYLING_PAGE)) {
            FileSettingsStylingColorController c = (FileSettingsStylingColorController) this.pageController;
            MainController pc = (MainController) parentController;
            c.setAllWindowsFgColor(Color.web(pc.getAllWindowsFgColor()));
            c.setAllWindowsBgColor(Color.web(pc.getAllWindowsBgColor()));
            c.setSongTitleFgColor(Color.web(pc.getLabelSongTitleFgColor()));
            c.setSongTitleBgColor(Color.web(pc.getLabelSongTitleBgColor()));
            c.setSongArtistFgColor(Color.web(pc.getLabelSongArtistFgColor()));
            c.setSongArtistBgColor(Color.web(pc.getLabelSongArtistBgColor()));
            c.setSongAlbumFgColor(Color.web(pc.getLabelSongAlbumFgColor()));
            c.setSongAlbumBgColor(Color.web(pc.getLabelSongAlbumBgColor()));
            c.setSongMetadataBgColor(Color.web(pc.getRootMetadataBgColor()));
            c.setSongCurrentTimeFgColor(Color.web(pc.getLabelSongCurrentTimeFgColor()));
            c.setSongCurrentTimeBgColor(Color.web(pc.getLabelSongCurrentTimeBgColor()));
            c.setSongTotalTimeFgColor(Color.web(pc.getLabelSongTotalTimeFgColor()));
            c.setSongTotalTimeBgColor(Color.web(pc.getLabelSongTotalTimeBgColor()));
            pc.updateAllWindowsFg();
            pc.updateAllWindowsBg();
        }
    }
}
