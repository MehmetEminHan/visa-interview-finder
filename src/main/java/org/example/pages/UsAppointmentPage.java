package org.example.pages;

import org.example.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UsAppointmentPage {

    //{Appointment Page} URL: https://ais.usvisa-info.com/tr-tr/niv/groups/{SPECIFIC USERID}
    public UsAppointmentPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public String dataYear = " ";
    public String dataMonth = " ";

    //{Web-element} devam et button
    @FindBy(xpath = "//*[@id=\"main\"]/div[2]/div[2]/div[1]/div/div/div[1]/div[2]/ul/li/a")
    public WebElement devamEt;

    //{Web-element} randevuyu yeniden zamanla dropdown
    @FindBy(xpath = "//*[@id=\"forms\"]/ul/li[4]")
    public WebElement randevuyuYenidenZamanlaDropdown;

    //{Web-element} randevuyu yeniden zamanla button
    @FindBy(xpath = "/html/body/div[4]/main/div[2]/div[2]/div/section/ul/li[4]/div/div/div[2]/p[2]/a")
    public WebElement randevuyuYenidenZamanlaButton;

    @FindBy(xpath = "//*[@id=\"appointments_consulate_appointment_date_input\"]")
    public WebElement randevuyuYenidenZamanlaDateInput;

    @FindBy(xpath = "//*[@id=\"ui-datepicker-div\"]/div[2]/div/a")
    public WebElement calendarNextButton;

    @FindBy(xpath = "//*[@id=\"ui-datepicker-div\"]/div[1]/div/a")
    public WebElement calendarPrevButton;

    @FindBy(xpath = "//*[@id=\"ui-datepicker-div\"]/div[1]")
    public WebElement calendarDatePickerFirst;

    @FindBy(xpath = "//*[@id=\"ui-datepicker-div\"]/div[2]")
    public WebElement calendarDatePickerLast;



}
