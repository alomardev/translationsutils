package com.alomardev.translationutils.ui.utils;

import java.net.URL;

public class R {

    public static URL get(String resName) {
        return R.class.getResource(resName);
    }

}
