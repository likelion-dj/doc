package com.ll.dj.doc.util;


import java.util.regex.Pattern;

public class Ut {
    public static class url {
        public static boolean isUrl(String url) {
            String regex = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$";
            Pattern p = Pattern.compile(regex);
            return p.matcher(url).matches();
        }
    }
}
