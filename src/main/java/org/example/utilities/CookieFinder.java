package org.example.utilities;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.example.request.HeaderEntity;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class CookieFinder {

    // This variables assigned to HeaderEntity field
    public static String cookies = "";
    public static String requestUrl = "";
    public static String connectionNumber = "";


    //Set current cookie to Header Entity's cookie
    public static String seleniumCookies(WebDriver driver) {

        // Get cookies from the browser
        Set<Cookie> seleniumCookies = driver.manage().getCookies();

        // Create a CookieStore to hold the cookies for HttpClient
        BasicCookieStore cookieStore = new BasicCookieStore();
        for (Cookie seleniumCookie : seleniumCookies) {
            BasicClientCookie httpClientCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            httpClientCookie.setDomain(seleniumCookie.getDomain());
            httpClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(httpClientCookie);
        }

        // Print each cookie
        for (Cookie cookie : seleniumCookies) {
            System.out.println("--------------------------------------START FINDING COOKIES------------------------------------");
            System.out.println("Cookie Name: " + cookie.getName());
            System.out.println("Cookie Value: " + cookie.getValue());
            System.out.println("Domain: " + cookie.getDomain());
            System.out.println("Path: " + cookie.getPath());
            System.out.println("Expiry: " + cookie.getExpiry());
            System.out.println("Is Secure: " + cookie.isSecure());
            System.out.println("Is HttpOnly: " + cookie.isHttpOnly());
            System.out.println("--------------------------------------FINISHED FINDING COOKIES------------------------------------");
        }

        //Set cookies to Header Entity's cookie
        cookies = cookieParser(seleniumCookies);
        System.out.println(HeaderEntity.cookie);


        /*//Set HeaderEntity's header based on parsed cookie
        HeaderEntity.headers.put("Accept", HeaderEntity.accept);
        HeaderEntity.headers.put("Accept-Encoding", HeaderEntity.acceptEncoding);
        HeaderEntity.headers.put("Accept-Language", HeaderEntity.acceptLanguage);
        HeaderEntity.headers.put("Connection", HeaderEntity.connection);
        HeaderEntity.headers.put("Cookie", HeaderEntity.cookie);
        HeaderEntity.headers.put("Host", HeaderEntity.host);
        HeaderEntity.headers.put("Referer", "https://ais.usvisa-info.com/tr-tr/niv/schedule/" + HeaderEntity.connectionNumber + "/appointment");
        HeaderEntity.headers.put("Sec-Ch-Ua", HeaderEntity.sec_ch_ua);
        HeaderEntity.headers.put("Sec-Ch-Ua-Mobile", HeaderEntity.sec_ch_ua_mobile);
        HeaderEntity.headers.put("Sec-Ch-Ua-Platform", HeaderEntity.sec_ch_ua_platform);
        HeaderEntity.headers.put("Sec-Fetch-Dest", HeaderEntity.sec_fetch_dest);
        HeaderEntity.headers.put("Sec-Fetch-Mode", HeaderEntity.sec_fetch_mode);
        HeaderEntity.headers.put("Sec-Fetch-Site", HeaderEntity.sec_fetch_site);
        HeaderEntity.headers.put("User-Agent", HeaderEntity.user_agent);
        HeaderEntity.headers.put("X-Csrf-Token", HeaderEntity.x_csrf_token);
        HeaderEntity.headers.put("X-Requested-With", HeaderEntity.x_requested_with);*/


        return cookies;
    }

    //Parse current URL to find connection number
    public static String urlParser(){
        //Get current URL to find special connection number from URL and assign to variable
        requestUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(requestUrl);

        //Find special connection number and assign to variable
        connectionNumber = findConnectionNumberByURL(requestUrl);
        System.out.println(connectionNumber);

        return connectionNumber;
    }

    //This method finds special connection number to parsing the URL
    public static String findConnectionNumberByURL(String requestUrl) {
        int startIndex = requestUrl.indexOf("/schedule/") + "/schedule/".length();
        int endIndex = requestUrl.indexOf("/appointment");
        return requestUrl.substring(startIndex, endIndex);
    }

    //Parse cookie for POST request headers
    public static String cookieParser(Set<Cookie> seleniumCookies) {
        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_gid")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_gat")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_ga_CSLL4ZEK4L")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_ga")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_ga+W1JNKHTW0Y")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        for (Cookie cookie : seleniumCookies) {
            if (cookie.getName().equals("_yatri_session")) {
                cookies += cookie.getName() + "=" + cookie.getValue() + ";";
                break;
            }
        }

        return cookies;
    }
}
