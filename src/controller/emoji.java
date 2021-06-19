/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.nio.charset.Charset;

/**
 *
 * @author Xuyen
 */
public class emoji {

    byte[] emojiByteshappy = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x83};
    byte[] emojiBytessmile = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8A};

    byte[] emojiByteskiss = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};

    byte[] emojiBytessad = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x9E};

    byte[] emojiBytesneutral = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x94};

    String emojihappy;
    String emojismile;

    String emojisad;

    String emojikiss;

    String emojineutral;

    public emoji() {
        this.emojihappy = new String(emojiByteshappy, Charset.forName("UTF-8"));
        this.emojismile = new String(emojiBytessmile, Charset.forName("UTF-8"));
        this.emojisad = new String(emojiBytessad, Charset.forName("UTF-8"));
        this.emojikiss = new String(emojiByteskiss, Charset.forName("UTF-8"));
        this.emojineutral = new String(emojiBytesneutral, Charset.forName("UTF-8"));
    }

    public String getEmojihappy() {
        return emojihappy;
    }

    public String getEmojikiss() {
        return emojikiss;
    }

    public String getEmojisad() {
        return emojisad;
    }

    public String getEmojineutral() {
        return emojineutral;
    }

    public String getEmojismile() {
        return emojismile;
    }

}
