package com.practice.lcn.jfx_mp3_player.media.effect;

/**
 * Song transform.
 *
 * @author Sam Leung
 * @version 1.0
 */
public interface Effect {
    /**
     * apply effect on a song.
     *
     * @param  wavPath        WAV song location
     * @param  transformCount current number of transforms
     * @return                transformed song location
     * @throws Exception      throws when the effect cannot be applied on the song.
     */
    String apply(String wavPath, int transformCount) throws Exception;
}
