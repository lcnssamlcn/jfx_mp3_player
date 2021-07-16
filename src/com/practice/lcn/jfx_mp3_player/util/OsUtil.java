package com.practice.lcn.jfx_mp3_player.util;

/**
 * Utility about user's operating system.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class OsUtil {
    /**
     * @return local AppData directory depending on which operating system the user is.
     */
    public static String getLocalAppDataDir() {
        String appDataLocalDir = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("windows")) {
            appDataLocalDir = System.getenv("LOCALAPPDATA");
        }
        else if (os.toLowerCase().startsWith("mac os")) {
            appDataLocalDir = System.getProperty("user.home");
            appDataLocalDir += "/Library/Application Support";
        }

        return appDataLocalDir;
    }

    /**
     * @return user home directory depending on which operating system the user is.
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }
}
