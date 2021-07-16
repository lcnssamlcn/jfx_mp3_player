package com.practice.lcn.jfx_mp3_player.util;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;

import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Utility to manipulate mp3 files.
 *
 * @author Sam Leung
 * @version 1.0
 */
public class Mp3Util {
    /**
     * read mp3 metadata.
     *
     * @param  mp3Path   mp3 location
     * @return           mp3 metadata
     * @throws Exception throws when mp3 metadata is failed to obtain.
     */
    public static Metadata getMetadata(String mp3Path) throws Exception {
        InputStream is = null;
        try {
            is = new FileInputStream(new File(mp3Path));
            ContentHandler ch = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext pc = new ParseContext();
            parser.parse(is, ch, metadata, pc);

            return metadata;
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException e) {

            }
        }
    }
}
