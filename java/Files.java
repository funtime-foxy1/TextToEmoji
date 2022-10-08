package com.funfoxrr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Files {

    public static final Image getIcon(IconSize size)
    {
        try {
            File pathToFile = null;
            if (size == IconSize.NORMAL) {
                pathToFile = new File(getResPath(0) + "icon.png");
            } else if (size == IconSize.SMALL)
            {
                pathToFile = new File( getResPath(0) + "iconsmall.png");
            }

            Image image = ImageIO.read(pathToFile);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String getResPath(int index)
    {
        java.util.List<String> lines = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader("paths.txt"))) {
            String line = br.readLine();
            while (line != null) {
                //System.out.println(line);
                lines.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines.get(index);
    }

}
