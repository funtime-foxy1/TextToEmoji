package com.funfoxrr;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RoundBorder implements Border
{
    private int r;
    RoundBorder(int r) {
        this.r = r;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,
                            int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, r, r);
    }
}

public class Main {

    public Main()
    {
        var res = Files.getResPath(0);
        var fontsp = Files.getResPath(2);
        var buttonsp = Files.getResPath(1);

        System.out.println("jframe lol");
        Image imageN = Files.getIcon(IconSize.NORMAL);
        Image imageS = Files.getIcon(IconSize.SMALL);
        final boolean[] outputvalid = {false};

        JFrame frame = setJFrame("Text > Emoji", 800, 600, imageN);
        frame.getContentPane().setBackground(new Color(88, 101, 242));

        JTextField text = createTextField("Your text!", 275, 230, 250, 50); // TEXT FIELD

        text.setBorder(new RoundBorder(15));
        text.setOpaque(false);

        ImageIcon icon = new ImageIcon(imageS);

        JLabel iconDisplay = new JLabel();
        iconDisplay.setIcon(icon);
        iconDisplay.setBounds(335, 40, 128, 128);

        JLabel titleText = createLabel("Text to emoji", 316, 160, 200, 50);
        titleText.setFont(FontHelper.loadFont(fontsp + "GothamBold.ttf", 24));

        JTextField output = createTextField("Waiting for generation...", 265, 350, 250, 50); // OUTPUT
        output.setEditable(false);
        output.setBorder(new RoundBorder(15));
        output.setOpaque(false);

        //JButton copybutton = createButton("Copy", Color.LIGHT_GRAY, 490, 350, 90, 50); // BUTTON
        JButton copybutton = new JButton(new ImageIcon(buttonsp + "copyinactive.png")); // BUTTON
        copybutton.setBorder(BorderFactory.createEmptyBorder());
        copybutton.setContentAreaFilled(false);
        copybutton.setBounds(490, 350, 90, 50);

        //JButton convertbutton = createButton("Emojify!", new Color(0, 208, 255), 325, 295, 150, 45, 35); // BUTTON
        JButton convertbutton = new JButton(new ImageIcon(buttonsp + "convertbutton.png")); // BUTTON
        convertbutton.setBorder(BorderFactory.createEmptyBorder());
        convertbutton.setContentAreaFilled(false);
        convertbutton.setBounds(325, 295, 150, 45);

        convertbutton.setFocusable(false);
        convertbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("generate button");

                String base = ":regional_indicator_";
                java.util.List<String> discordEmojis = new ArrayList<String>();
                char[] doNotContain = {' ', '!', '?', ':', ';'};

                String moddifiedText = text.getText();

                moddifiedText.replace("!", ":exclamation:");

                for (char c : doNotContain)
                {
                    moddifiedText = moddifiedText.replace(Character.toString(c), "");
                }

                for (char c : moddifiedText.toCharArray())
                {
                    if (c != ' ') {
                        discordEmojis.add(base + c + ":");
                    } else {
                        discordEmojis.add(" ");
                    }
                }

                System.out.println(discordEmojis);

                String message = "";

                for (String s : discordEmojis)
                {
                    if (s.contains(" "))
                    {
                        message += " - ";
                        continue;
                    }

                    message += s + " ";
                }

                message.replace(" ", "   ");

                for (char c : doNotContain)
                {
                    if (message.contains(Character.toString(c)))
                    {
                        message.replace(base + c + ":", "");
                    }
                }

                outputvalid[0] = true;
                output.setText(message.toLowerCase());
            }
        }); // to emoji ! MAIN FUNCTION :O !

        copybutton.setFocusable(false);
        copybutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (outputvalid[0]) {
                    System.out.println("copy button");
                    copy(output.getText());
                } else
                {
                    System.out.println("not vaild copy");
                }
            }
        });

        JLabel about = createLabel("Made by FunFox with Java 17", 0, 527, 250, 50);
        about.setForeground(Color.black);
        about.setFont(FontHelper.loadFont("res/fonts/GothamBold.ttf", 16));

        frame.add(copybutton);
        frame.add(convertbutton);
        frame.add(text);
        frame.add(output);
        frame.add(titleText);
        frame.add(iconDisplay);
        frame.add(about);

        while (true) {
            frame.repaint();

            if (outputvalid[0])
            {
                copybutton.setIcon(new ImageIcon( buttonsp + "copyactive.png"));
            }
        }
    }

    private void copy(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String text = value;
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }

    public static JLabel createLabel(String text, int x, int y, int width, int height)
    {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);

        return label;
    }

    public static JTextField createTextField(String text, int x, int y, int width, int height)
    {
        JTextField textfield = new JTextField(text);
        textfield.setBounds(x, y, width, height);

        return textfield;
    }

    public static JButton createButton(String name, Color bg, int x, int y, int width, int height)
    {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        button.setBackground(bg);

        return button;
    }

    public static JFrame setJFrame(String title, int width, int height, Image icon)
    {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setIconImage(icon);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        return frame;
    }

    public static void main(String[] args) {
        new Main();
    }
}