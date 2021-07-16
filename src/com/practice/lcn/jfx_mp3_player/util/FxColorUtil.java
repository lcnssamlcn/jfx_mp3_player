package com.practice.lcn.jfx_mp3_player.util;

import javafx.scene.paint.Color;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility to support {@link javafx.scene.paint.Color}.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class FxColorUtil {
    private static Logger logger = LogManager.getLogger(FxColorUtil.class.getName());

    /**
     * convert {@link javafx.scene.paint.Color} color to "rgba(%d, %d, %d, %f)" format.
     *
     * @param  color color
     * @return color in "rgba(%d, %d, %d, %f)" format.
     */
    public static String toRgba(Color color) {
        int r = Long.valueOf(Math.round(color.getRed() * 255)).intValue();
        int g = Long.valueOf(Math.round(color.getGreen() * 255)).intValue();
        int b = Long.valueOf(Math.round(color.getBlue() * 255)).intValue();
        double a = color.getOpacity();
        logger.debug(String.format("toRgba(): (%d, %d, %d, %f)", r, g, b, a));

        return String.format("rgba(%d, %d, %d, %f)", r, g, b, a);
    }
}
