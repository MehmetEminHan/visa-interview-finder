package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.pages.UsAppointmentPage;
import org.example.pages.UsLoginPage;
import org.example.request.HeaderEntity;
import org.example.utilties.ConfigurationReader;
import org.example.utilties.CookieFinder;
import org.example.utilties.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //Pages
    public static UsLoginPage usLoginPage = new UsLoginPage();
    public static UsAppointmentPage usAppointmentPage = new UsAppointmentPage();


    //Create Javascript Executor object to run js commands
    public static JavascriptExecutor javascriptExecutor = (JavascriptExecutor) Driver.getDriver();

    public static void main(String[] args) {

        //Open requested link via Selenium
        Driver.getDriver().get(ConfigurationReader.getProperty("page_link"));

        //Wait 10 sec
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

        //Scroll down
        javascriptExecutor.executeScript("window.scrollBy(0, 1000);");

        //Login with given username and password
        usLoginPage.login(ConfigurationReader.getProperty("username"), ConfigurationReader.getProperty("password"));

        //Click on devam et button
        usAppointmentPage.devamEt.click();

        wait.until(ExpectedConditions.elementToBeClickable(usAppointmentPage.randevuyuYenidenZamanlaDropdown));
        usAppointmentPage.randevuyuYenidenZamanlaDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(usAppointmentPage.randevuyuYenidenZamanlaButton));
        usAppointmentPage.randevuyuYenidenZamanlaButton.click();

        CookieFinder.seleniumCookies(Driver.getDriver());

        for (int i = 0; i<50; i++){
            //Send GET request with headers
            Response response = RestAssured.given()
                    .headers(HeaderEntity.headers)
                    .when()
                    .get("https://ais.usvisa-info.com/tr-tr/niv/schedule/"+HeaderEntity.connectionNumber+"/appointment/days/124.json?appointments[expedite]=false");

            //print response body
            String responseBody = response.getBody().asString();
            String printToLogFile = prettyprintJson(responseBody);
        }
    }

    public static String prettyprintJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object jsonObj = mapper.readValue(json, Object.class);
            String prettyJson = mapper.writeValueAsString(jsonObj);
            System.out.println("Pretty-Printed Response Body: \n" + prettyJson);
            return prettyJson;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}