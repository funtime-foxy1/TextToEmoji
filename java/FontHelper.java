package com.funfoxrr;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontHelper {

    public static java.awt.Font loadFont(String filePath, float size)
    {
        java.awt.Font font = null;
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(filePath)).deriveFont(size);
            ge.registerFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        return font;
    }

}
