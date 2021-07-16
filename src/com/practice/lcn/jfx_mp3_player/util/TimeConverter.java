package com.practice.lcn.jfx_mp3_player.util;

import java.util.Map;
import java.util.HashMap;

/**
 * Utility to manipulate time.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class TimeConverter {
    /**
     * map key representing minute
     */
    public static final String MINUTE = "minute";
    /**
     * map key representing second
     */
    public static final String SECOND = "second";

    /**
     * Convert second to "mm:ss" format.
     *
     * @param  ss number of seconds
     * @return    second in "mm:ss" format.
     */
    public static Map<String, Integer> ss2mmss(int ss) {
        int min = ss / 60;
        int sec = ss % 60;

        Map<String, Integer> result = new HashMap<String, Integer>();
        result.put(TimeConverter.MINUTE, min);
        result.put(TimeConverter.SECOND, sec);

        return result;
    }
}
