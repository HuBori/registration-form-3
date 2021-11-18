package com.example.registration_form;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    private static String url = "https:/demoqa.com/automation-practice-form";
    private WebDriver driver;
    private WebDriverWait wait;

    // Elements on form
    private String formPath = "//form[@id='userForm']";
    private String nameFieldsPath, stateCityPath;
    private String firstNameFieldPath, lastNameFieldPath, emailFieldPath, genderFieldPath, mobileFieldPath, hobbiesBoxesPath, addressFieldPath, stateFieldPath, cityFieldPath;
    private String submitBtnPath;

    public MainPage() {
        open(url);
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, 100);
        closeAd();
//        WebElement form = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(formPath)));

        nameFieldsPath = "//div[@id='userName-wrapper']//div[@class='col-md-4 col-sm-6']";
        stateCityPath = "//div[@id='stateCity-wrapper']//div[@class='col-md-4 col-sm-12']";

        firstNameFieldPath = "//input[@id='firstName']";
        lastNameFieldPath = "//input[@id='lastName']";
        emailFieldPath = "//input[@id='userEmail']";
        genderFieldPath = "//div[@id='genterWrapper']//div[@class='col-md-9 col-sm-12']//div[@class='custom-control custom-radio custom-control-inline']";
        mobileFieldPath = "//input[@id='userNumber']";
        hobbiesBoxesPath = "//div[@id='hobbiesWrapper']//div[@class='col-md-9 col-sm-12']";
        addressFieldPath = "//textarea[@id='currentAddress']";
        stateFieldPath = "//div[@id='state']//div[@class=' css-yk16xz-control']//div[@class=' css-1hwfws3']";
        cityFieldPath = "//div[@id='city']//div[@class=' css-1fhf3k1-control']//div[@class=' css-1hwfws3']";
        submitBtnPath = "//button[@id='submit']";
    }

    public void openPage() {
        open(url);
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(formPath)));
        closeAd();
    }

    private void closeAd() {
        WebElement adCloseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='close-fixedban']")));
        adCloseBtn.click();
    }

    public void fillMandatoryFields() {
        fillName(MandatoryFields.getFirstName(), MandatoryFields.getLastName());
        pickGender(MandatoryFields.getGender());
        fillMobile(MandatoryFields.getMobile());
    }

    public void submit() {
        WebElement submitBtn = driver.findElement(By.xpath(submitBtnPath));
        submitBtn.click();
    }

    public void fillName(String firstName, String lastName) {
        WebElement firstNameField = driver.findElement(By.xpath(firstNameFieldPath));
        fillOneName(firstNameField, firstName);
        WebElement lastNameField = driver.findElement(By.xpath(lastNameFieldPath));
        fillOneName(lastNameField, lastName);
    }

    private void fillOneName(WebElement field, String value) {
        while (field.getText().length() > 0) {
            field.sendKeys(Keys.BACK_SPACE);
        }
        field.sendKeys(value);
    }

    public void fillEmail(String email) {
        driver.findElement(By.xpath(emailFieldPath)).sendKeys(email);
    }

    public void pickGender(String gender) {
        WebElement radioButton;
        switch (gender) {
            case "Male": radioButton = driver.findElement(By.xpath(genderFieldPath + "//input[@value='Male']"));break;
            case "Female": radioButton = driver.findElement(By.xpath(genderFieldPath + "//input[@value='Female']"));break;
            case "Other": radioButton = driver.findElement(By.xpath(genderFieldPath + "//input[@value='Other']"));break;
            default: System.out.println("Invalid gender!");return;
        }
        radioButton.findElement(By.xpath("//parent::div")).click();
    }

    public void fillMobile(String mobileNumber) {
        WebElement mobileField = driver.findElement(By.xpath(mobileFieldPath));
        while (mobileField.getText().length() > 0) { mobileField.sendKeys(Keys.BACK_SPACE); }
        mobileField.sendKeys(mobileNumber);
    }

    public void pickHobbies(String[] hobbies) {
        for (String hobby : hobbies) {
            WebElement hobbyBox = driver.findElement(By.xpath(hobbiesBoxesPath + "//div[@class='custom-control custom-checkbox custom-control-inline']//input[text()='" + hobby +"']"));
            hobbyBox.click();
        }
    }

    public void fillAddress(String address) {
        driver.findElement(By.xpath(addressFieldPath)).sendKeys(address);
    }

    public void pickStateAndCity(String state, String city) {
        WebElement stateField = driver.findElement(By.xpath(stateFieldPath));
        stateField.sendKeys(state);
        stateField.findElement(By.xpath("//div[text()='" + state + "']")).click();

        WebElement cityField = driver.findElement(By.xpath(cityFieldPath));
        cityField.sendKeys(city);
        cityField.findElement(By.xpath("//div[text()='" + city + "']")).click();
    }

    public void pickOnlyState(int state) { // TODO: write test
        WebElement stateField = driver.findElement(By.xpath(stateFieldPath));
        stateField.click();
        List<WebElement> states = stateField.findElements(By.xpath("//div[@class=' css-1uccc91-singleValue']"));
        states.get(state).click();
    }

    public void pickOnlyCity(int city) { // TODO: write test
        WebElement cityField = driver.findElement(By.xpath(cityFieldPath));
        cityField.click();
        List<WebElement> states = cityField.findElements(By.xpath("//div[@class=' css-1uccc91-singleValue']"));
        states.get(city).click();
    }

    public WebDriver getWebDriver() { return driver; }

    public String getFirstName() {
        return driver.findElement(By.xpath(firstNameFieldPath)).getText();
    }

    public String getLastName() {
        return driver.findElement(By.xpath(lastNameFieldPath)).getText();
    }

    public String getEmailField() {
        return driver.findElement(By.xpath(emailFieldPath)).getText();
    }

    public String getGender() {
        for (WebElement gender : driver.findElements(By.xpath(genderFieldPath + "//input[@name='gender']"))) {
            if (gender.isSelected()) {
                return gender.findElement(By.xpath("//parent::div//label[@class='custom-control-label']")).getText();
            }
        }
        return null;
    }

    public String getMobile() {
        return driver.findElement(By.xpath(mobileFieldPath)).getText();
    }

    public Stream<String> getHobbies() {
        List<String> hobbies = new ArrayList<>();
        for (WebElement hobby : driver.findElements(By.xpath(hobbiesBoxesPath + "//div[@class='custom-control custom-checkbox custom-control-inline']"))) {
            if (hobby.findElement(By.xpath("//child::input")).isSelected()) {
                hobbies.add(hobby.findElement(By.xpath("//parent::div//label[@class='custom-control-label']")).getText());
            }
        }
        return hobbies.stream().sorted();
    }

    public String getAddress() {
        return driver.findElement(By.xpath(addressFieldPath)).getText();
    }

    public String getState() {
        return driver.findElement(By.xpath(stateFieldPath)).getText();
    }

    public String getCity() {
        return driver.findElement(By.xpath(cityFieldPath)).getText();
    }
}
