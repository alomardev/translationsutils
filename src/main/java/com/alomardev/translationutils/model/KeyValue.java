package com.alomardev.translationutils.model;

import java.util.Arrays;

public class KeyValue {

    public static String[] extractGroups(String key) {
        String[] sigs = key.split("\\.");
        return Arrays.copyOfRange(sigs, 0, sigs.length - 1);
    }

    String key, valueAr, valueEn, groups[];

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        this.groups = extractGroups(key);
    }

    public String getValueAr() {
        return valueAr;
    }

    public void setValueAr(String valueAr) {
        this.valueAr = valueAr;
    }

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    public String[] getGroups() {
        return groups;
    }

    public boolean childOf(String key) {
        // TODO change the way they are compared
        String concatenatedGroups = String.join(".", getGroups());
        return concatenatedGroups.startsWith(key);
    }

    @Override
    public String toString() {
        boolean star = valueAr.isEmpty() || valueEn.isEmpty();
        return (star ? "*" : "") + key;
    }
}
