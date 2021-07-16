package com.practice.lcn.jfx_mp3_player.controller;

import com.practice.lcn.jfx_mp3_player.MainApp;
import com.practice.lcn.jfx_mp3_player.util.FxNodeUtil;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import java.net.URL;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * "Help -> About" window controller.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class HelpAboutController implements Initializable, MyFxmlController {
    private static Logger logger = LogManager.getLogger(HelpAboutController.class.getName());

    @FXML
    private AnchorPane root;
    /**
     * application title label
     */
    @FXML
    private Label labelTitle;
    /**
     * application version label
     */
    @FXML
    private Label labelVersion;
    /**
     * application author label
     */
    @FXML
    private Label labelAuthor;
    /**
     * press this button to close the "Help -> About" window.
     */
    @FXML
    private Button btnClose;

    /**
     * controller that controls this controller.
     */
    private MyFxmlController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.labelTitle.setText(String.format("%s", MainApp.TITLE));
        this.labelVersion.setText(String.format("Version: %s", MainApp.VERSION));
        this.labelAuthor.setText(String.format("Author: %s", MainApp.AUTHOR));
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.HelpAboutController#btnClose} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnCloseClick(ActionEvent event) {
        ((MainController) parentController).closeHelpAboutWindow();
    }

    public void setParentController(MyFxmlController parentController) {
        this.parentController = parentController;
    }

    /**
     * update all labels' foreground color in "Help -> About" window.
     *
     * @param color new color to apply
     */
    public void setRootFgColor(String color) {
        this.labelTitle.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelTitle.getStyle(), "-fx-text-fill", color));
        this.labelVersion.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelVersion.getStyle(), "-fx-text-fill", color));
        this.labelAuthor.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelAuthor.getStyle(), "-fx-text-fill", color));
    }

    /**
     * update background color of "Help -> About" window.
     *
     * @param color new color to apply
     */
    public void setRootBgColor(String color) {
        String style = this.root.getStyle();
        this.root.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(style, "-fx-background-color", color));
    }
}
