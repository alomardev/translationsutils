package com.alomardev.translationutils;

import com.alomardev.translationutils.model.KeyValue;
import com.alomardev.translationutils.ui.MainFrame;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranslationUtils implements MainFrame.MainFrameInterface {

    private static int MAXIMUM_PADDING = 2;

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        new TranslationUtils();
    }

    private MainFrame mainFrame;
    private JFileChooser fileChooser;
    private List<KeyValue> keyValueList;

    private String jsonStrAr;
    private String jsonStrEn;

    public TranslationUtils() {
        mainFrame = new MainFrame();
        mainFrame.setup(this);
        mainFrame.setVisible(true);

        fileChooser = new JFileChooser();

        keyValueList = new ArrayList<>();

        String arFilePath = Prefs.get(Prefs.KEY_AR_FILE_PATH);
        String enFilePath = Prefs.get(Prefs.KEY_EN_FILE_PATH);
        if (arFilePath != null && enFilePath != null) {
            File arFile = new File(arFilePath);
            File enFile = new File(enFilePath);
            if (arFile.exists() && enFile.exists()) {
                int result = JOptionPane.showConfirmDialog(mainFrame, "Do you want to load and parse previous files?\n" +
                        arFile.getAbsolutePath() + "\n" +
                        enFile.getAbsolutePath(), "Confirm", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    loadFile(arFile, "ar");
                    loadFile(enFile, "en");
                    parse();
                }
            }
        }
    }

    private void loadFile(File file, String lang) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String content = new String(data, "UTF-8");

            switch (lang) {
                case "ar":
                    Prefs.set(Prefs.KEY_AR_FILE_PATH, file.getAbsolutePath());
                    mainFrame.setArabicFilePath(file.getPath());
                    jsonStrAr = content;
                    break;
                case "en":
                    Prefs.set(Prefs.KEY_EN_FILE_PATH, file.getAbsolutePath());
                    mainFrame.setEnglishFilePath(file.getPath());
                    jsonStrEn = content;
                    break;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void parse() {
        try {
            Map<String, String> translationsAr = new HashMap<>();
            Map<String, String> translationsEn = new HashMap<>();

            JSONObject json = new JSONObject(orderMagic(jsonStrAr));
            for (String key : json.keySet()) {
                parser(json, key, "", translationsAr);
            }
            json = new JSONObject(orderMagic(jsonStrEn));
            for (String key : json.keySet()) {
                parser(json, key, "", translationsEn);
            }

            keyValueList.clear();
            for (String key : translationsAr.keySet()) {
                KeyValue kv = new KeyValue();
                kv.setKey(key);
                kv.setValueAr(translationsAr.get(key));

                boolean hasIt = false;
                for (String keyInner : translationsEn.keySet()) {
                    if (orderDemagic(keyInner).equals(orderDemagic(key))) {
                        kv.setValueEn(translationsEn.get(keyInner));
                        hasIt = true;
                        break;
                    }
                }
                if (!hasIt) {
                    kv.setValueEn("");
                }
                keyValueList.add(kv);
            }

            a:
            for (String key : translationsEn.keySet()) {
                for (String keyInner : translationsAr.keySet()) {
                    if (orderDemagic(keyInner).equals(orderDemagic(key))) {
                        continue a;
                    }
                }

                KeyValue kv = new KeyValue();
                kv.setKey(key);
                kv.setValueEn(translationsEn.get(key));
                kv.setValueAr("");
                keyValueList.add(kv);
            }

            keyValueList.sort((o1, o2) -> {
                String obj1 = o1.getKey();
                String obj2 = o2.getKey();
                return obj1.compareTo(obj2);
            });

            keyValueList.forEach(kv -> kv.setKey(orderDemagic(kv.getKey())));
            mainFrame.setTranslations(keyValueList);

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void parser(JSONObject json, String key, String combined, Map<String, String> trans) {
        Object o = json.get(key);
        combined += key;
        if (o instanceof JSONObject) {
            for (String k : ((JSONObject) o).keySet()) {
                parser((JSONObject) o, k, combined + ".", trans);
            }
        } else {
            trans.put(combined, json.get(key).toString());
        }
    }

    private String orderMagic(String plainJson) {
        int index = 0;
        String regex = "\"([\\w -_]+)\"\\s*:";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(plainJson);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String iwp = String.format("%0" + MAXIMUM_PADDING + "d", index);
            matcher.appendReplacement(buffer, "\"" + iwp + ".$1\":");
            index++;
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private String orderDemagic(String magified) {
        return magified.replaceAll("\\d{" + MAXIMUM_PADDING + "}\\.", "");
    }

    @Override
    public void onBrowseClicked(String lang) {
        String location = Prefs.get(lang.equals("ar") ? Prefs.KEY_AR_FILE_PATH : Prefs.KEY_EN_FILE_PATH);
        if (location != null)
            fileChooser.setCurrentDirectory(new File(location));
        int result = fileChooser.showOpenDialog(mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadFile(file, lang);
        }
    }

    @Override
    public void onParseClicked() {
        parse();
    }

    @Override
    public void onSaveClicked() {

    }

    @Override
    public void onAddKeyClicked(String key) {
        /* boolean exists = false;
        key = key.trim();
        String[] groups = key.split("\\.");
        for(KeyValue kv : keyValueList) {
            if (kv.getKey().equals(key)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            mainFrame.setStatusText(String.format("The key %s is already exists", key));
        } else {
            // TODO add it to the
        } */
    }

    @Override
    public void onRemoveKeyClicked(String key) {

    }

    @Override
    public void onApplyClicked(String lang, String value) {

    }
}
