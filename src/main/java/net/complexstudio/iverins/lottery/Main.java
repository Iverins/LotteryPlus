package net.complexstudio.iverins.lottery;

import net.complexstudio.iverins.lottery.ui.ProgramUI;
import net.complexstudio.iverins.lottery.utils.ConfigReader;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class Main {

    private static String path = null;

    static {
        try {
            path = java.net.URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static File dataFolder = new File(path);
    private static ConfigReader reader = null;

    public static final URL lotteryWAV = Main.class.getClassLoader().getResource("lottery.wav");
    public static final AudioClip player = Applet.newAudioClip(lotteryWAV);

    public static void main(String[] args) {

        // 图标文件
        URL iconURL = Main.class.getClassLoader().getResource("icon.png");
        assert iconURL != null;
        Image icon = new ImageIcon(iconURL).getImage();

        try {
            getReader().loadDefaultConfig();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        ProgramUI ui = new ProgramUI();
        ui.setIconImage(icon);
        ui.init();
    }

    /**
     * 返回软件所在的文件夹
     *
     * @return 软件所在的文件夹
     */
    public static File getDataFolder() {
        return dataFolder;
    }

    /**
     * 返回配置文件读取
     *
     * @return 配置文件
     */
    public static ConfigReader getReader() {
        if (reader != null) {
            return reader;
        }
        if (!dataFolder.isDirectory()) {
            // 不是目录
            if (dataFolder.getParentFile().isDirectory()) {
                dataFolder = dataFolder.getParentFile();
            }
        }
        // 读取配置文件
        reader = new ConfigReader("config.json");
        return reader;
    }

    /**
     * 字符串转unicode
     *
     * @param str 传入的字符串
     * @return Unicode
     */
    public static String stringToUnicode(String str) {
        StringBuilder sb = new StringBuilder();
        char[] c = str.toCharArray();
        for (char value : c) {
            sb.append("\\u").append(Integer.toHexString(value));
        }
        return sb.toString();
    }

    /**
     * unicode转字符串
     *
     * @param unicode 传入的Unicode
     * @return 字符串
     */
    public static String unicodeToString(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

    /**
     * Java 是用 Unicode 编码，汉字的长度为 1
     * @param s 传入的字符串
     * @return 字符串的长度
     */
    public static int getWordCount(String s) {
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;
    }

}
