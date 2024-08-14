package org.example.pages;

import org.example.utilties.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UsLoginPage {

    //{Login page} URL: https://ais.usvisa-info.com/tr-tr/niv/users/sign_in
    public UsLoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //{Web-element} email input box
    @FindBy(xpath = "//*[@id=\"user_email\"]")
    public WebElement usernameInputBox;

    //{Web-element} password input box
    @FindBy(xpath = "//*[@id=\"user_password\"]")
    public WebElement passwordInputBox;

    //{Web-element} check box
    @FindBy(xpath = "//*[@id=\"sign_in_form\"]/div[3]/label/div")
    public WebElement LoginCheckBox;

    //{Web-element} Log in button
    @FindBy(xpath = "//*[@id=\"sign_in_form\"]/p[1]/input")
    public WebElement loginButton;

    public void login(String username, String password) {

        //Wait 5ms
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //Send username into username input box
        usernameInputBox.sendKeys(username);

        //Send password into password inputbox
        passwordInputBox.sendKeys(password);

        //Click on login checkbox
        LoginCheckBox.click();

        //Click on login button
        loginButton.click();

    }
}
