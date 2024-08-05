package cn.lunadeer.colorfulmap.utils;

import cn.lunadeer.colorfulmap.ColorfulMap;

public class UrlTools {

    public static boolean isInWhiteList(String url) {
        if (ColorfulMap.config.getAddressWhiteList().isEmpty()) {
            return true;
        }
        for (String whiteUrl : ColorfulMap.config.getAddressWhiteList()) {
            if (url.startsWith(whiteUrl)) {
                return true;
            }
            if (url.startsWith("http://" + whiteUrl)) {
                return true;
            }
            if (url.startsWith("https://" + whiteUrl)) {
                return true;
            }
        }
        return false;
    }

}
