package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.pages.UsAppointmentPage;
import org.example.pages.UsLoginPage;
import org.example.entities.ResponseEntity;
import org.example.utilities.Driver;
import org.example.utilities.PageUtils;
import org.openqa.selenium.JavascriptExecutor;

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

    public static PageUtils pageUtils = new PageUtils();

    public static void main(String[] args) throws InterruptedException {

        pageUtils.runLogin();
    }



}