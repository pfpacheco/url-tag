package br.com.lojadomecanico.urltag.service.handler;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

@SuppressWarnings("unused")
public class UrlShrinkHandler {

    private final HashMap<String, String> keyMap;
    private final HashMap<String, String> valMap;

    private final char[] charArray;

    private final Random randomInfo;

    private final static String prefixUrl = "ldm.click/";

    public UrlShrinkHandler() {
        keyMap = new HashMap<>();
        valMap = new HashMap<>();
        randomInfo = new Random();
        int keySize = 6;
        charArray = new char[62];

        for (int i = 0; i < charArray.length; i++) {
            int j;
            if (i < 10) {
                j = i + 48;
            } else if (i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            charArray[i] = (char) j;
        }
    }

    public String urlShrunk(String url) {
        String urlShrunk = "";
        if (validateUrl(url)) {
            url = this.normalizeUrl(url);

            urlShrunk += valMap.containsKey(url) ? valMap.get(url) : this.getKey(url);
        }
        return prefixUrl.concat(urlShrunk.substring(0, 6));
    }

    private boolean validateUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    private String normalizeUrl(String url) {
        String urlFaded = "";
        if (url.startsWith("http://")) {
            urlFaded += url.substring(7);
        }
        if (url.startsWith("https://")) {
            urlFaded += url.substring(8);
        }
        if (url.charAt(url.length() - 1) == '/') {
            urlFaded += url.substring(0, url.length() - 1);
        }
        return urlFaded;
    }

    private String getKey(String url) {
        String key;
        key = generateKey();
        keyMap.put(key, url);
        valMap.put(url, key);
        return key;
    }
    private String generateKey() {
        StringBuilder key = new StringBuilder();
        boolean flag = true;
        while (flag) {
            key = new StringBuilder();
            for (int i = 0; i <= charArray.length; i++) {
                key.append(charArray[randomInfo.nextInt(62)]);
            }
            if (!keyMap.containsKey(key.toString())) {
                flag = false;
            }
        }
        return key.toString();
    }
}

