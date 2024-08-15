package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.pages.UsAppointmentPage;
import org.example.pages.UsLoginPage;
import org.example.request.HeaderEntity;
import org.example.request.ResponseEntity;
import org.example.utilities.ConfigurationReader;
import org.example.utilities.CookieFinder;
import org.example.utilities.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //Pages
    public static UsLoginPage usLoginPage = new UsLoginPage();
    public static UsAppointmentPage usAppointmentPage = new UsAppointmentPage();

    //Others
    static ObjectMapper mapper = new ObjectMapper();
    public static ResponseEntity responseEntity;

    //Create Javascript Executor object to run js commands
    public static JavascriptExecutor javascriptExecutor = (JavascriptExecutor) Driver.getDriver();

    public static void main(String[] args) throws InterruptedException {


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

    public static void runLogin() throws InterruptedException {
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

        //Wait until randevuyu yeniden zamanla dropdown is visible and click on it
        wait.until(ExpectedConditions.elementToBeClickable(usAppointmentPage.randevuyuYenidenZamanlaDropdown));
        usAppointmentPage.randevuyuYenidenZamanlaDropdown.click();

        //Wait until randevuyu yeniden zamanla button is visible and click on it
        wait.until(ExpectedConditions.elementToBeClickable(usAppointmentPage.randevuyuYenidenZamanlaButton));
        usAppointmentPage.randevuyuYenidenZamanlaButton.click();

        //Wait until calendar is visible and click on it
        wait.until(ExpectedConditions.elementToBeClickable(usAppointmentPage.randevuyuYenidenZamanlaDateInput));
        usAppointmentPage.randevuyuYenidenZamanlaDateInput.click();


        for (int i = 0; i < 50; i++) {
            //Send GET request with headers
            Response response = RestAssured.given()
                    .headers(HeaderEntity.headers)
                    .when()
                    .get("https://ais.usvisa-info.com/tr-tr/niv/schedule/" + HeaderEntity.connectionNumber + "/appointment/days/124.json?appointments[expedite]=false");

            //print response body
            String responseBody = response.getBody().asString();

            //Map response body to Response Entity java object
            List<ResponseEntity> appointments = new ArrayList<ResponseEntity>();
            try {
                appointments = mapper.readValue(responseBody, new TypeReference<List<ResponseEntity>>() {
                });
            } catch (JsonProcessingException e) {
                System.out.println("Error parsing response: " + responseBody);
                e.printStackTrace();
                break;
            }

            //Print all response entities
            appointments.stream().forEach(System.out::println);

            //Wait 1 minute
            Thread.sleep(60000);
        }
    }
}