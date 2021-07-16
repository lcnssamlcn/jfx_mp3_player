package com.practice.lcn.jfx_mp3_player.util;

import com.practice.lcn.jfx_mp3_player.MainApp;

import java.io.File;
import java.io.IOException;

/**
 * Audio converter.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class AudioFormatConverter {
    /**
     * Convert mp3 file to wav.
     *
     * @param  inputPath            mp3 location
     * @param  outputPath           new wav location
     * @throws IOException          throws when conversion fails
     * @throws InterruptedException throws when conversion fails
     */
    public static void mp3ToWav(String inputPath, String outputPath) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", MainApp.LIB_DIR + File.separatorChar + "jlayer-1.0.1.jar", "javazoom.jl.converter.jlc", "-v", "-p", outputPath, inputPath);
        Process proc = pb.start();
        proc.waitFor();
    }
}
