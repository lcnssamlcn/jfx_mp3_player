package com.practice.lcn.jfx_mp3_player.model;

/**
 * Application settings.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class Config {
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsFgColor}.
     */
    private String allWindowsFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#allWindowsBgColor}.
     */
    private String allWindowsBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleFgColor}.
     */
    private String labelSongTitleFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTitleBgColor}.
     */
    private String labelSongTitleBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistFgColor}.
     */
    private String labelSongArtistFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongArtistBgColor}.
     */
    private String labelSongArtistBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumFgColor}.
     */
    private String labelSongAlbumFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongAlbumBgColor}.
     */
    private String labelSongAlbumBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#rootMetadataBgColor}.
     */
    private String rootMetadataBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeFgColor}.
     */
    private String labelSongCurrentTimeFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongCurrentTimeBgColor}.
     */
    private String labelSongCurrentTimeBgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeFgColor}.
     */
    private String labelSongTotalTimeFgColor;
    /**
     * see {@link com.practice.lcn.jfx_mp3_player.controller.MainController#labelSongTotalTimeBgColor}.
     */
    private String labelSongTotalTimeBgColor;

    /**
     * record the state of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#btnRepeat}.
     */
    private boolean enableRepeat;
    /**
     * record the value of {@link com.practice.lcn.jfx_mp3_player.controller.MainController#sliderVol}.
     */
    private double volume;
    /**
     * record the value of {@link com.practice.lcn.jfx_mp3_player.media.MyPlayer#tempo}. If the users did not apply tempo shift on a song, it will be recorded as "null".
     */
    private Double tempo;

    /**
     * parent directory of the mp3 location that the users selected to play.
     */
    private String lastMp3Dir;

    public void setAllWindowsFgColor(String allWindowsFgColor) {
        this.allWindowsFgColor = allWindowsFgColor;
    }

    public String getAllWindowsFgColor() {
        return this.allWindowsFgColor;
    }

    public void setAllWindowsBgColor(String allWindowsBgColor) {
        this.allWindowsBgColor = allWindowsBgColor;
    }

    public String getAllWindowsBgColor() {
        return this.allWindowsBgColor;
    }

    public void setLabelSongTitleFgColor(String labelSongTitleFgColor) {
        this.labelSongTitleFgColor = labelSongTitleFgColor;
    }

    public String getLabelSongTitleFgColor() {
        return this.labelSongTitleFgColor;
    }

    public void setLabelSongTitleBgColor(String labelSongTitleBgColor) {
        this.labelSongTitleBgColor = labelSongTitleBgColor;
    }

    public String getLabelSongTitleBgColor() {
        return this.labelSongTitleBgColor;
    }

    public void setLabelSongArtistFgColor(String labelSongArtistFgColor) {
        this.labelSongArtistFgColor = labelSongArtistFgColor;
    }

    public String getLabelSongArtistFgColor() {
        return this.labelSongArtistFgColor;
    }

    public void setLabelSongArtistBgColor(String labelSongArtistBgColor) {
        this.labelSongArtistBgColor = labelSongArtistBgColor;
    }

    public String getLabelSongArtistBgColor() {
        return this.labelSongArtistBgColor;
    }

    public void setLabelSongAlbumFgColor(String labelSongAlbumFgColor) {
        this.labelSongAlbumFgColor = labelSongAlbumFgColor;
    }

    public String getLabelSongAlbumFgColor() {
        return this.labelSongAlbumFgColor;
    }

    public void setLabelSongAlbumBgColor(String labelSongAlbumBgColor) {
        this.labelSongAlbumBgColor = labelSongAlbumBgColor;
    }

    public String getLabelSongAlbumBgColor() {
        return this.labelSongAlbumBgColor;
    }

    public void setRootMetadataBgColor(String rootMetadataBgColor) {
        this.rootMetadataBgColor = rootMetadataBgColor;
    }

    public String getRootMetadataBgColor() {
        return this.rootMetadataBgColor;
    }

    public void setLabelSongCurrentTimeFgColor(String labelSongCurrentTimeFgColor) {
        this.labelSongCurrentTimeFgColor = labelSongCurrentTimeFgColor;
    }

    public String getLabelSongCurrentTimeFgColor() {
        return this.labelSongCurrentTimeFgColor;
    }

    public void setLabelSongCurrentTimeBgColor(String labelSongCurrentTimeBgColor) {
        this.labelSongCurrentTimeBgColor = labelSongCurrentTimeBgColor;
    }

    public String getLabelSongCurrentTimeBgColor() {
        return this.labelSongCurrentTimeBgColor;
    }

    public void setLabelSongTotalTimeFgColor(String labelSongTotalTimeFgColor) {
        this.labelSongTotalTimeFgColor = labelSongTotalTimeFgColor;
    }

    public String getLabelSongTotalTimeFgColor() {
        return this.labelSongTotalTimeFgColor;
    }

    public void setLabelSongTotalTimeBgColor(String labelSongTotalTimeBgColor) {
        this.labelSongTotalTimeBgColor = labelSongTotalTimeBgColor;
    }

    public String getLabelSongTotalTimeBgColor() {
        return this.labelSongTotalTimeBgColor;
    }

    public void setEnableRepeat(boolean enableRepeat) {
        this.enableRepeat = enableRepeat;
    }

    public boolean getEnableRepeat() {
        return this.enableRepeat;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    public Double getTempo() {
        return this.tempo;
    }

    public void setLastMp3Dir(String lastMp3Dir) {
        this.lastMp3Dir = lastMp3Dir;
    }

    public String getLastMp3Dir() {
        return this.lastMp3Dir;
    }
}
