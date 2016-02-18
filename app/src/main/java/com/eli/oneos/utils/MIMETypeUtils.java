package com.eli.oneos.utils;

/**
 * Created by gaoyun@eli-tech.com on 2016/2/18.
 */
public class MIMETypeUtils {

    private final static String[][] MIME_MAP_TABLE = {{".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"}, {".c", "text/plain"},
            {".class", "application/octet-stream"}, {".conf", "text/plain"},
            {".cpp", "text/plain"}, {".doc", "application/msword"},
            {".exe", "application/octet-stream"}, {".gif", "image/gif"},
            {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"},
            {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"},
            {".jar", "application/java-archive"}, {".java", "text/plain"},
            {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"}, {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"}, {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"}, {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"}, {".rc", "text/plain"},
            {".rmvb", "video/mp4"}, {".rtf", "application/rtf"}, {".sh", "text/plain"},
            {".tar", "application/x-tar"}, {".tgz", "application/x-compressed"},
            {".txt", "text/plain"}, {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "video/x-ms-wmv"}, {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "*/*"},
            {"", "*/*"}};

    public static String getMIMEType(String fName) {
        String type = "*/*";
        int doIndex = fName.lastIndexOf(".");
        if (doIndex < 0) {
            return type;
        }

        String suffix = fName.substring(doIndex, fName.length()).toLowerCase();
        if (EmptyUtils.isEmpty(suffix)) {
            return type;
        }

        for (int i = 0; i < MIME_MAP_TABLE.length; i++) {
            if (suffix.equals(MIME_MAP_TABLE[i][0])) {
                type = MIME_MAP_TABLE[i][1];
            }
        }
        return type;
    }
}
