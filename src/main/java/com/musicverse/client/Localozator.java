package com.musicverse.client;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Localozator {
//    private static final Locale locale = Locale.getDefault();
    private static final Locale locale = new Locale("sk", "SK");

    private static final File file = new File("src/main/resources/bundle");

    private static URL[] urls = new URL[0];

    static {
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
    }

    public static ResourceBundle getResourceBundle(){
        ClassLoader loader = new URLClassLoader(urls);
        return ResourceBundle.getBundle("Language", locale, loader);
    }

}
