package org.example.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pages.UsAppointmentPage;
import org.example.pages.UsLoginPage;
import org.example.entities.ResponseEntity;
import org.example.entities.YearMonth;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {

    //Pages
    public static UsLoginPage usLoginPage = new UsLoginPage();
    public static UsAppointmentPage usAppointmentPage = new UsAppointmentPage();

    //Others
    static ObjectMapper mapper = new ObjectMapper();
    public static ResponseEntity responseEntity;

    //Create Javascript Executor object to run js commands
    public static JavascriptExecutor javascriptExecutor = (JavascriptExecutor) Driver.getDriver();

    public void runLogin() throws InterruptedException {
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


        /*for (int i = 0; i < 50; i++) {
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
        }*/

        findEmptyAppointment("8", "2025");
    }

    //Find Empty Appointments and return
    public void findEmptyAppointment(String desiredDateMonth, String desiredDateYear) {

        int counter = 0;

        for (; ; ) {
            //Find all available appointment slots
            List<WebElement> availableAppointmentsElements = usAppointmentPage.calendarDatePickerFirst.findElements(By.cssSelector("td[data-event='click']"));

            // Check if the list is not empty before processing
            if (!availableAppointmentsElements.isEmpty()) {
                /*// Print out the text of each td element
                availableAppointmentsElements.stream()
                        .map(WebElement::getText)
                        .forEach(System.out::println);*/

                // Extract data-year, data-month, and day text as YearMonth objects
                List<YearMonth> yearMonthList = availableAppointmentsElements.stream()
                        .map(td -> {
                            String year = td.getAttribute("data-year");
                            String month = td.getAttribute("data-month");
                            String day = td.getText(); // Get the text of the td element as day
                            return new YearMonth(year, month, day);
                        })
                        .collect(Collectors.toList());

                // Print out each YearMonth object
                yearMonthList.forEach(System.out::println);

                System.out.println("Most early appointment: " + availableAppointmentsElements.get(0).getText());

                if (Integer.parseInt(yearMonthList.get(0).getYear()) <= Integer.parseInt(desiredDateYear) && Integer.parseInt(desiredDateMonth) >= Integer.parseInt(yearMonthList.get(0).getMonth())) {

                    System.out.println("Desired Month: " + desiredDateMonth + " Desired Year: " + desiredDateYear);
                    System.out.println("Founded Month: " + yearMonthList.get(0).getMonth() + " Founded Year: " + yearMonthList.get(0).getYear());
                    //Select most early appointment
                    availableAppointmentsElements.get(0).click();

                    break;
                } else {
                    Driver.getDriver().navigate().refresh();
                    usAppointmentPage.randevuyuYenidenZamanlaDateInput.click();
                    continue;
                }
            } else {
                //
                System.out.println(counter + " No matching elements found.");
                counter++;
                usAppointmentPage.calendarNextButton.click();
                continue;
            }
        }
    }
}
