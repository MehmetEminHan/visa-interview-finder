package org.example.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.pages.UsAppointmentPage;
import org.example.pages.UsLoginPage;
import org.example.entities.ResponseEntity;
import org.example.entities.YearMonth;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    //Log4j
    private static final Logger logger = LogManager.getLogger(PageUtils.class);

    //Create Javascript Executor object to run js commands
    public static JavascriptExecutor javascriptExecutor = (JavascriptExecutor) Driver.getDriver();

    public void runLogin(String desiredDateMonth, String desiredDateYear) throws InterruptedException {
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

        findEmptyAppointment(desiredDateMonth, desiredDateYear);
    }

    //Find Empty Appointments and return
    public void findEmptyAppointment(String desiredDateMonth, String desiredDateYear) throws InterruptedException {

        int refreshCounter = 0;
        int counter = 0;

        for (; ; ) {

            // Execute JavaScript to find all td elements with data-event='click'
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            List<WebElement> availableAppointmentsElements = (List<WebElement>) js.executeScript(
                    "return Array.from(document.querySelectorAll('td[data-event=\"click\"]'));"
            );
            //Find all available appointment slots
            //List<WebElement> availableAppointmentsElements = usAppointmentPage.calendarDatePickerFirst.findElements(By.cssSelector("td[data-event='click']"));

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

                //<====================== LOGS ======================>
                logger.info("Most early appointment: " + yearMonthList.get(0));
                logger.info("Avaliable all early appointments " + yearMonthList);

                //Select appointment if desired appointment date founded
                if (Integer.parseInt(yearMonthList.get(0).getYear()) <= Integer.parseInt(desiredDateYear) && Integer.parseInt(desiredDateMonth) >= Integer.parseInt(yearMonthList.get(0).getMonth())) {

                    //Print desired and founded appointment dates into console
                    System.out.println("Desired Month: " + desiredDateMonth + " Desired Year: " + desiredDateYear);
                    System.out.println("Founded Month: " + yearMonthList.get(0).getMonth() + " Founded Year: " + yearMonthList.get(0).getYear());

                    //<====================== LOGS ======================>
                    logger.info("Desired Month: " + desiredDateMonth + " Desired Year: " + desiredDateYear + "\n" + "Founded Month: " + yearMonthList.get(0).getMonth() + " Founded Year: " + yearMonthList.get(0).getYear());


                    //Select most early appointment
                    availableAppointmentsElements.get(0).click();

                    //Select appointment time
                    Select select = new Select(usAppointmentPage.calendarScheduleTime);

                    //Collect all appointment time web elements in list
                    List<WebElement> options = select.getOptions();
                    for (WebElement option : options) {
                        System.out.println("Option value: " + option.getAttribute("value"));
                        System.out.println("Option text: " + option.getText());
                    }

                    if (!options.isEmpty()){
                        //Click on appointment time drop down
                        usAppointmentPage.calendarScheduleTime.click();

                        //Wait until appointment times visible
                        Thread.sleep(5000);

                        //Click on first appointment time
                        options.get(1).click();

                        //Click on appointment submit button
                        usAppointmentPage.calendarScheduleTime.click();

                        Thread.sleep(5000);

                        //Click on appointment approve button
                        usAppointmentPage.approveAppointmentButton.click();
                    }else{
                        continue;
                    }

                    //Shut down application if required appointment found!
                    System.exit(0);

                    //break the loop
                    break;
                } else {
                    Thread.sleep(30000);
                    Driver.getDriver().navigate().refresh();
                    refreshCounter++;

                    logger.info("====================REFRESH COUNTER=======================\n" + refreshCounter);
                    Thread.sleep(30000);
                    usAppointmentPage.randevuyuYenidenZamanlaDateInput.click();
                    continue;
                }
            }else {
                //
                logger.info("Calendar page number: " + counter + " --> No matching appointment found.");

                counter++;

                usAppointmentPage.calendarNextButton.click();
            }
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
