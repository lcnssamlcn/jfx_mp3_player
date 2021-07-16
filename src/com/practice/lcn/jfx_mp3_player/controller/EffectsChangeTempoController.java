package com.practice.lcn.jfx_mp3_player.controller;

import com.practice.lcn.jfx_mp3_player.util.FxNodeUtil;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.net.URL;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * "Effects -> Change Tempo" window controller.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class EffectsChangeTempoController implements Initializable, MyFxmlController {
    private static Logger logger = LogManager.getLogger(EffectsChangeTempoController.class.getName());

    @FXML
    private AnchorPane root;
    @FXML
    private Label labelTempo;
    /**
     * tempo slider. It allows users to adjust song tempo as they need.
     */
    @FXML
    private Slider sliderTempo;
    /**
     * It always displays the current value of {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#sliderTempo}.
     */
    @FXML
    private TextField sliderTempoText;
    /**
     * press this button to apply tempo shift based on the current value of {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#sliderTempo}.
     */
    @FXML
    private Button btnOk;
    /**
     * press this button to cancel tempo shift on the song.
     */
    @FXML
    private Button btnCancel;

    /**
     * controller that controls this controller.
     */
    private MyFxmlController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Bindings.<Number>bindBidirectional(this.sliderTempoText.textProperty(), this.sliderTempo.valueProperty(), new NumberStringConverter());
    }

    /**
     * set tempo value programmatically.
     *
     * @param tempo new tempo value
     */
    public void setTempo(double tempo) {
        this.sliderTempo.setValue(tempo);
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#btnOk} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnOkClick(ActionEvent event) {
        double sliderTempoVal = this.sliderTempo.getValue();
        logger.debug(String.format("sliderTempoVal: %f", sliderTempoVal));
        ((MainController) parentController).changeTempo(sliderTempoVal);
        ((MainController) parentController).closeEffectsCtWindow();
        ((MainController) parentController).initSeekbar();
    }

    /**
     * describing what happens when button {@link com.practice.lcn.jfx_mp3_player.controller.EffectsChangeTempoController#btnCancel} is clicked.
     *
     * @param event click event
     */
    @FXML
    public void handleBtnCancelClick(ActionEvent event) {
        ((MainController) parentController).closeEffectsCtWindow();
    }

    public void setParentController(MyFxmlController parentController) {
        this.parentController = parentController;
    }

    /**
     * update all labels' foreground color in "Effects -> Change Tempo" window.
     *
     * @param color new color to apply
     */
    public void setRootFgColor(String color) {
        this.labelTempo.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(this.labelTempo.getStyle(), "-fx-text-fill", color));
    }

    /**
     * update the background color of "Effects -> Change Tempo" window.
     * 
     * @param color new color to apply
     */
    public void setRootBgColor(String color) {
        String style = this.root.getStyle();
        this.root.setStyle(FxNodeUtil.getAddedOrUpdatedCssStyleStr(style, "-fx-background-color", color));
    }
}
