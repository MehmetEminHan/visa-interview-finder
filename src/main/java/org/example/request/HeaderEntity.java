package org.example.request;

import java.util.Map;
import java.util.HashMap;

public class HeaderEntity {

    public static String cookie = "";
    public static String requestUrl = "";
    public static String connectionNumber = "";
    //Headers for windows and google chrome
    public static final Map<String, String> headers = new HashMap<String, String>();


    //This method finds special connection number to parsing the URL
    public static String findConnectionNumberByURL(String requestUrl) {
        int startIndex = requestUrl.indexOf("/schedule/") + "/schedule/".length();
        int endIndex = requestUrl.indexOf("/appointment");
        return requestUrl.substring(startIndex, endIndex);
    }
}
