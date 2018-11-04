package com.alomardev.translationutils;

import java.io.*;
import java.util.Properties;

public class Prefs {

    public static String KEY_AR_FILE_PATH = "arFilePath";
    public static String KEY_EN_FILE_PATH = "enFilePath";
    private static String FILE_NAME = "TranslationUtils.properties";

    private static Properties props;
    private static Properties getInstance() {
        if (props == null) {
            props = new Properties();
            try {
                File file = new File(FILE_NAME);
                System.out.println(file.getAbsolutePath());
                if (file.exists()) {
                    InputStream in = new FileInputStream(file);
                    props.load(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props;
    }

    public static String get(String key) {
        return getInstance().getProperty(key, null);
    }

    public static void set(String key, String value) {
        getInstance().setProperty(key, value);
        try {
            getInstance().store(new FileOutputStream(FILE_NAME), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
