package org.example.utilties;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.example.request.HeaderEntity;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class CookieFinder {

    public static String cookies = "";


    //Set current cookie to Header Entity's cookie
    public static Set<Cookie> seleniumCookies(WebDriver driver) {

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
        HeaderEntity.cookie = cookieParser(seleniumCookies);
        System.out.println(HeaderEntity.cookie);

        //Get current URL to find special connection number from URL
        HeaderEntity.requestUrl = Driver.getDriver().getCurrentUrl();
        System.out.println(HeaderEntity.requestUrl);
        HeaderEntity.connectionNumber = HeaderEntity.findConnectionNumberByURL(HeaderEntity.requestUrl);
        System.out.println(HeaderEntity.connectionNumber);

        //Set HeaderEntity's header based on parsed cookie
        HeaderEntity.headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        HeaderEntity.headers.put("Accept-Encoding", "gzip, deflate, br, zstd");
        HeaderEntity.headers.put("Accept-Language", "en-US,en;q=0.9,tr-TR;q=0.8,tr;q=0.7");
        HeaderEntity.headers.put("Connection", "keep-alive");
        HeaderEntity.headers.put("Cookie", HeaderEntity.cookie);
        HeaderEntity.headers.put("Host", "ais.usvisa-info.com");
        HeaderEntity.headers.put("Referer", "https://ais.usvisa-info.com/tr-tr/niv/schedule/" + HeaderEntity.connectionNumber + "/appointment");
        HeaderEntity.headers.put("Sec-Ch-Ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
        HeaderEntity.headers.put("Sec-Ch-Ua-Mobile", "?0");
        HeaderEntity.headers.put("Sec-Ch-Ua-Platform", "\"Windows\"");
        HeaderEntity.headers.put("Sec-Fetch-Dest", "empty");
        HeaderEntity.headers.put("Sec-Fetch-Mode", "cors");
        HeaderEntity.headers.put("Sec-Fetch-Site", "same-origin");
        HeaderEntity.headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
        HeaderEntity.headers.put("X-Csrf-Token", "Sh7X4wBjEvNlYMD2jvbE1JkVuE5rWoV532tymfhT21D+UFvOcWgD6a/Ug+YDHjV0dlmTE2sGDE1k1zdqEDQQNA==");
        HeaderEntity.headers.put("X-Requested-With", "XMLHttpRequest");


        return seleniumCookies;
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
