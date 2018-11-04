package com.alomardev.translationutils.ui;

import com.alomardev.translationutils.model.KeyValue;
import com.alomardev.translationutils.ui.utils.UIUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame extends JFrame {

    public interface MainFrameInterface {
        void onBrowseClicked(String lang);
        void onParseClicked();
        void onSaveClicked();
        void onAddKeyClicked(String key);
        void onRemoveKeyClicked(String key);
        void onApplyClicked(String lang, String value);
    }

    private List<KeyValue> keyValueList;

    private static Font FONT_INPUT = UIUtils.FONT_MONO.deriveFont(18.0f);

    private JTextField searchTf, arValueTf, enValueTf, keyTf, arFilePathLbl, enFilePathLbl;
    private JList<KeyValue> keysList;
    private JButton browseArBtn, browseEnBtn, saveAllBtn, parseBtn, saveKeyValueBtn, removeKeyValueBtn;
    private JLabel statusLbl;

    public void setup(MainFrameInterface callback) {
        setTitle("Translation Utils");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel keysLbl = new JLabel("Key"),
               arLbl = new JLabel("Arabic"),
               enLbl = new JLabel("English");

        final Dimension dfix = new Dimension(1, 1);

        searchTf = new JTextField();
        arValueTf = new JTextField();
        enValueTf = new JTextField();
        keyTf = new JTextField();
        arFilePathLbl = new JTextField("(ar.json)");
        enFilePathLbl = new JTextField("(en.json)");
        keysList = new JList<>();
        browseArBtn = new JButton("Browse");
        browseEnBtn = new JButton("Browse");
        saveAllBtn = new JButton("Save Changes");
        parseBtn = new JButton("Parse");
        saveKeyValueBtn = new JButton("Save");
        removeKeyValueBtn = new JButton("Remove");
        statusLbl = new JLabel("...");

        JScrollPane keysListSp = new JScrollPane(keysList,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        arFilePathLbl.setEditable(false);
        arFilePathLbl.setPreferredSize(dfix);
        enFilePathLbl.setEditable(false);
        enFilePathLbl.setPreferredSize(dfix);
        keysListSp.setPreferredSize(dfix);

        searchTf.setFont(FONT_INPUT);
        keyTf.setFont(FONT_INPUT);
        arValueTf.setFont(FONT_INPUT);
        enValueTf.setFont(FONT_INPUT);
        keysList.setFont(FONT_INPUT);

        keysList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        GridBagConstraints c = new GridBagConstraints();

        JPanel top = new JPanel();
        top.setLayout(new GridBagLayout());

        JPanel middle = new JPanel();
        middle.setLayout(new GridBagLayout());

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridBagLayout());

        setLayout(new GridBagLayout());

        UIUtils.gbc(c, 0, 0, 1, 1, 1, 1, -1, true, true, 2, 2, 2, 0);
        add(top, c);

        UIUtils.gbc(c, 0, 1, 1, 1, 1, 0, -1, true, true, 2, 1, 2, 0);
        add(new JSeparator(), c);

        UIUtils.gbc(c, 0, 2, 1, 1, 1, 0, -1, true, true, 2, 2, 2, 0);
        add(middle, c);

        UIUtils.gbc(c, 0, 3, 1, 1, 1, 0, -1, true, true, 2, 2, 2, 0);
        add(new JSeparator(), c);

        UIUtils.gbc(c, 0, 4, 1, 1, 1, 0, -1, true, true, 2, 2, 2, 2);
        add(bottom, c);

        UIUtils.gbc(c, 0, 0, 4, 1, 1, 0, -1, true, true, 0, 0, 0, 0);
        top.add(searchTf, c);

        UIUtils.gbc(c, 0, 1, 4, 1, 1, 1, -1, true, true, 0, 1, 0, 1);
        top.add(keysListSp, c);

        UIUtils.gbc(c, 0, 2, 1, 1, 0, 0, -1, true, true, 0, 1, 1, 1);
        top.add(keysLbl, c);

        UIUtils.gbc(c, 1, 2, 2, 1, 1, 0, -1, true, true, 0, 1, 0, 1);
        top.add(keyTf, c);

        UIUtils.gbc(c, 0, 3, 1, 1, 0, 0, -1, true, true, 0, 1, 1, 1);
        top.add(arLbl, c);

        UIUtils.gbc(c, 1, 3, 2, 1, 1, 0, -1, true, true, 0, 1, 0, 1);
        top.add(arValueTf, c);

        UIUtils.gbc(c, 0, 4, 1, 1, 0, 0, -1, true, true, 0, 1, 1, 1);
        top.add(enLbl, c);

        UIUtils.gbc(c, 1, 4, 2, 1, 1, 0, -1, true, true, 0, 1, 0, 1);
        top.add(enValueTf, c);

        UIUtils.gbc(c, 1, 5, 1, 1, 1, 0, GridBagConstraints.EAST, false, false, 0, 1, 1, 1);
        top.add(removeKeyValueBtn, c);

        UIUtils.gbc(c, 2, 5, 1, 1, 0, 0, -1, false, false, 0, 1, 0, 1);
        top.add(saveKeyValueBtn, c);

        UIUtils.gbc(c, 0, 0, 1, 1, 1, 0, -1, true, true, 0, 0, 1, 1);
        middle.add(arFilePathLbl, c);

        UIUtils.gbc(c, 1, 0, 1, 1, 0, 0, -1, true, true, 0, 0, 1, 1);
        middle.add(browseArBtn, c);

        UIUtils.gbc(c, 2, 0, 1, 1, 1, 0, -1, true, true, 0, 0, 1, 1);
        middle.add(enFilePathLbl, c);

        UIUtils.gbc(c, 3, 0, 1, 1, 0, 0, -1, true, true, 0, 0, 3, 1);
        middle.add(browseEnBtn, c);

        UIUtils.gbc(c, 4, 0, 1, 1, 0, 0, -1, true, true, 0, 0, 1, 1);
        middle.add(parseBtn, c);

        UIUtils.gbc(c, 5, 0, 1, 1, 0, 0, -1, true, true, 0, 0, 0, 1);
        middle.add(saveAllBtn, c);

        UIUtils.gbc(c, 0, 0, 1, 1, 1, 0, -1, true, true, 0, 0, 1, 0);
        bottom.add(statusLbl, c);

        setMinimumSize(new Dimension(640, 480));

        browseArBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onBrowseClicked("ar");
        });
        browseEnBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onBrowseClicked("en");
        });
        saveAllBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onSaveClicked();
        });
        parseBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onParseClicked();
        });
        saveKeyValueBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onAddKeyClicked(getSearchText());
        });
        removeKeyValueBtn.addActionListener((ActionEvent e) -> {
            if (callback != null) callback.onRemoveKeyClicked(getSearchText());
        });

        keysList.addListSelectionListener((ListSelectionEvent e) -> {
            KeyValue kv = keysList.getSelectedValue();
            if (kv == null) {
                setArabicValue("");
                setEnglishValue("");
                keyTf.setText("");
                return;
            }
            setArabicValue(kv.getValueAr());
            setEnglishValue(kv.getValueEn());
            keyTf.setText(kv.getKey());
        });

        searchTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });

        keyTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkExistence();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkExistence();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkExistence();
            }
        });

    }

    private void checkExistence() {
        if (keyTf.getText().trim().isEmpty()) {
            highlight(keyTf, "");
            return;
        }
        for (KeyValue kv : keyValueList) {
            if (kv.childOf(keyTf.getText())) {
                highlight(keyTf, "danger");
                return;
            }
        }
        highlight(keyTf, "");
    }

    private void highlight(JTextField comp, String style) {
        Color color;
        switch (style) {
            case "danger":
                color = Color.red;
                break;
            case "success":
                color = Color.green;
                break;
            default:
                color = UIManager.getColor("TextField.background");
        }
        comp.setBackground(color);
    }

    private void filter() {
        if (keyValueList == null) {
            return;
        }

        String filter = searchTf.getText();
        boolean searchForMissing = filter.equals("*");

        DefaultListModel<KeyValue> model = (DefaultListModel<KeyValue>) keysList.getModel();
        model.clear();

        boolean emptyFilter = filter.trim().isEmpty();
        for (KeyValue kv : keyValueList) {
            if (emptyFilter) {
                model.addElement(kv);
            } else {
                if (searchForMissing) {
                    if (kv.getValueEn().isEmpty() || kv.getValueAr().isEmpty()) {
                        model.addElement(kv);
                    }
                } else if (kv.getKey().toLowerCase().contains(filter.toLowerCase())) {
                    model.addElement(kv);
                } else if (kv.getValueAr().toLowerCase().contains(filter.toLowerCase())) {
                    model.addElement(kv);
                } else if (kv.getValueEn().toLowerCase().contains(filter.toLowerCase())) {
                    model.addElement(kv);
                }
            }
        }

        keysList.clearSelection();
    }

    public String getSearchText() {
        return searchTf.getText();
    }

    public void setTranslations(List<KeyValue> translations) {
        keyValueList = translations;
        final DefaultListModel<KeyValue> model = new DefaultListModel<>();
        keyValueList.forEach(model::addElement);
        keysList.setModel(model);
    }

    public String getArabicValue() {
        return arValueTf.getText();
    }

    public String getEnglishValue() {
        return enValueTf.getText();
    }

    public void setArabicValue(String value) {
        arValueTf.setText(value);
    }

    public void setEnglishValue(String value) {
        enValueTf.setText(value);
    }

    public void setStatusText(String text) {
        statusLbl.setText(text);
    }

    public void setArabicFilePath(String path) {
        arFilePathLbl.setText(path);
    }

    public void setEnglishFilePath(String path) {
        enFilePathLbl.setText(path);
    }

}
