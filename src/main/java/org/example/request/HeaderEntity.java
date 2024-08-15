package org.example.request;

import org.example.utilities.ConfigurationReader;
import org.example.utilities.CookieFinder;
import org.example.utilities.Driver;

import java.util.Map;
import java.util.HashMap;

public class HeaderEntity {

    public static String requestUrl = "";
    public static final String connectionNumber = CookieFinder.urlParser();
    public static final String accept = "application/json, text/javascript, */*; q=0.01";
    public static final String acceptEncoding = "gzip, deflate, br, zstd";
    public static final String acceptLanguage = "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7";
    public static final String connection = "keep-alive";
    public static final String cookie = CookieFinder.seleniumCookies(Driver.getDriver());
    public static final String host = "ais.usvisa-info.com";
    public static final String referer = "https://ais.usvisa-info.com/tr-tr/niv/schedule/" + connectionNumber + "/appointment";
    public static final String sec_ch_ua = "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"";
    public static final String sec_ch_ua_mobile = "?0";
    public static final String sec_ch_ua_platform = ConfigurationReader.getProperty("OS");
    public static final String sec_fetch_dest = "empty";
    public static final String sec_fetch_mode = "cors";
    public static final String sec_fetch_site = "same-origin";
    public static final String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36";
    public static final String x_csrf_token = "Sh7X4wBjEvNlYMD2jvbE1JkVuE5rWoV532tymfhT21D+UFvOcWgD6a/Ug+YDHjV0dlmTE2sGDE1k1zdqEDQQNA==";
    public static final String x_requested_with = "XMLHttpRequest";



    //Headers for Windows and Google Chrome
    public static final Map<String, String> headers = new HashMap<String, String>(){{
        put("Accept", accept);
        put("Accept-Encoding", acceptEncoding);
        put("Accept-Language", acceptLanguage);
        put("Connection", connection);
        put("Cookie", cookie);
        put("Host", host);
        put("Referer", referer);
        put("sec_ch_ua", sec_ch_ua);
        put("sec_ch_ua_mobile", sec_ch_ua_mobile);
        put("sec_ch_ua_platform", sec_ch_ua_platform);
        put("Sec-Fetch-Dest", sec_fetch_dest);
        put("Sec-Fetch-Mode", sec_fetch_mode);
        put("Sec-Fetch-Site", sec_fetch_site);
        put("User-Agent", user_agent);
        put("x_csrf_token", x_csrf_token);
        put("X-Requested-With", x_requested_with);
    }};
}
