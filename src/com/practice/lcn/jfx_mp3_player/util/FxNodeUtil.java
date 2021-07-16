package com.practice.lcn.jfx_mp3_player.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility to support {@link javafx.scene.Node}.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class FxNodeUtil {
    private static Logger logger = LogManager.getLogger(FxNodeUtil.class.getName());

    /**
     * add or update css style for a particular node.
     *
     * @param  cssStyles current css style of node
     * @param  cssProp   css property to add or update
     * @param  val       value of the css property
     * @return           added or updated css style
     */
    public static String getAddedOrUpdatedCssStyleStr(String cssStyles, String cssProp, String val) {
        logger.debug(String.format("getUpdatedCssStyleStr(): original cssStyles: \"%s\"", cssStyles));

        boolean updated = false;
        List<String> splitCssStyles = new ArrayList<String>(Arrays.asList(cssStyles.split(";")));
        for (int i = 0; i < splitCssStyles.size(); i++) {
            String s = splitCssStyles.get(i).trim();
            String[] splitS = s.split(":");
            if (splitS[0].trim().equals(cssProp)) {
                updated = true;
                splitCssStyles.set(i, String.format("%s: %s", cssProp, val));
                break;
            }
        }
        if (!updated) {
            splitCssStyles.add(String.format("%s: %s;", cssProp, val));
        }

        String result = StringUtils.join(splitCssStyles, "; ");
        logger.debug(String.format("getUpdatedCssStyleStr(): updated cssStyles: \"%s\"", result));

        return result;
    }

    /**
     * check whether a css property is set on a particular node.
     *
     * @param  cssStyles current css style of node
     * @param  cssProp   css property to check
     * @return           true if set; otherwise false.
     */
    public static boolean isCssPropSet(String cssStyles, String cssProp) {
        return cssStyles.indexOf(cssProp) != -1;
    }
}
