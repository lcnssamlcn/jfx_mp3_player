package com.practice.lcn.jfx_mp3_player.controller;

import com.practice.lcn.jfx_mp3_player.util.FxColorUtil;
import com.practice.lcn.jfx_mp3_player.util.FxNodeUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * "File -> Settings -> Styling -> Color" page controller.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class FileSettingsStylingColorController implements Initializable, MyFxmlController {
    private static Logger logger = LogManager.getLogger(FileSettingsStylingColorController.class.getName());

    @FXML
    private GridPane rootStylingColor;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsFgColor}.
     */
    @FXML
    private ColorPicker cpAllWindowsFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsBgColor}.
     */
    @FXML
    private ColorPicker cpAllWindowsBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleFgColor}.
     */
    @FXML
    private ColorPicker cpSongTitleFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleBgColor}.
     */
    @FXML
    private ColorPicker cpSongTitleBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistFgColor}.
     */
    @FXML
    private ColorPicker cpSongArtistFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistBgColor}.
     */
    @FXML
    private ColorPicker cpSongArtistBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumFgColor}.
     */
    @FXML
    private ColorPicker cpSongAlbumFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumBgColor}.
     */
    @FXML
    private ColorPicker cpSongAlbumBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadataBgColor}.
     */
    @FXML
    private ColorPicker cpSongMetadataBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeFgColor}.
     */
    @FXML
    private ColorPicker cpSongCurrentTimeFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeBgColor}.
     */
    @FXML
    private ColorPicker cpSongCurrentTimeBg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeFgColor}.
     */
    @FXML
    private ColorPicker cpSongTotalTimeFg;
    /**
     * color picker of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeBgColor}.
     */
    @FXML
    private ColorPicker cpSongTotalTimeBg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * update all labels' foreground color in "File -> Settings -> Styling -> Color" page.
     *
     * @param color new color to apply
     */
    public void setRootFgColor(String color) {
        for (Node n : this.rootStylingColor.getChildren()) {
            if (n instanceof Label) {
                Label lbl = (Label) n;
                logger.debug(String.format("setRootColor(): n.text: \"%s\"", lbl.getText()));
                String style = lbl.getStyle();
                lbl.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(style, "-fx-text-fill", color));
            }
        }
    }

    /**
     * update background color of "File -> Settings -> Styling -> Color" page.
     *
     * @param color new color to apply
     */
    public void setRootBgColor(String color) {
        String style = this.rootStylingColor.getStyle();
        this.rootStylingColor.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(style, "-fx-background-color", color));
    }

    public void setAllWindowsFgColor(Color color) {
        this.cpAllWindowsFg.setValue(color);
    }

    public void setAllWindowsBgColor(Color color) {
        this.cpAllWindowsBg.setValue(color);
    }

    public void setSongTitleFgColor(Color color) {
        this.cpSongTitleFg.setValue(color);
    }

    public void setSongTitleBgColor(Color color) {
        this.cpSongTitleBg.setValue(color);
    }

    public void setSongArtistFgColor(Color color) {
        this.cpSongArtistFg.setValue(color);
    }

    public void setSongArtistBgColor(Color color) {
        this.cpSongArtistBg.setValue(color);
    }

    public void setSongAlbumFgColor(Color color) {
        this.cpSongAlbumFg.setValue(color);
    }

    public void setSongAlbumBgColor(Color color) {
        this.cpSongAlbumBg.setValue(color);
    }

    public void setSongMetadataBgColor(Color color) {
        this.cpSongMetadataBg.setValue(color);
    }

    public void setSongCurrentTimeFgColor(Color color) {
        this.cpSongCurrentTimeFg.setValue(color);
    }

    public void setSongCurrentTimeBgColor(Color color) {
        this.cpSongCurrentTimeBg.setValue(color);
    }

    public void setSongTotalTimeFgColor(Color color) {
        this.cpSongTotalTimeFg.setValue(color);
    }

    public void setSongTotalTimeBgColor(Color color) {
        this.cpSongTotalTimeBg.setValue(color);
    }

    public String getAllWindowsFgColor() {
        return FxColorUtil.toRgba(this.cpAllWindowsFg.getValue());
    }

    public String getAllWindowsBgColor() {
        return FxColorUtil.toRgba(this.cpAllWindowsBg.getValue());
    }

    public String getSongTitleFgColor() {
        return FxColorUtil.toRgba(this.cpSongTitleFg.getValue());
    }

    public String getSongTitleBgColor() {
        return FxColorUtil.toRgba(this.cpSongTitleBg.getValue());
    }

    public String getSongArtistFgColor() {
        return FxColorUtil.toRgba(this.cpSongArtistFg.getValue());
    }

    public String getSongArtistBgColor() {
        return FxColorUtil.toRgba(this.cpSongArtistBg.getValue());
    }

    public String getSongAlbumFgColor() {
        return FxColorUtil.toRgba(this.cpSongAlbumFg.getValue());
    }

    public String getSongAlbumBgColor() {
        return FxColorUtil.toRgba(this.cpSongAlbumBg.getValue());
    }

    public String getSongMetadataBgColor() {
        return FxColorUtil.toRgba(this.cpSongMetadataBg.getValue());
    }

    public String getSongCurrentTimeFgColor() {
        return FxColorUtil.toRgba(this.cpSongCurrentTimeFg.getValue());
    }

    public String getSongCurrentTimeBgColor() {
        return FxColorUtil.toRgba(this.cpSongCurrentTimeBg.getValue());
    }

    public String getSongTotalTimeFgColor() {
        return FxColorUtil.toRgba(this.cpSongTotalTimeFg.getValue());
    }

    public String getSongTotalTimeBgColor() {
        return FxColorUtil.toRgba(this.cpSongTotalTimeBg.getValue());
    }
}
