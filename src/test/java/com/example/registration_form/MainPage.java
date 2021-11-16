package com.example.registration_form;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    private static String url = "https://demoqa.com//automation-practice-form";
    private static String title = "ToolsQA";
    private WebDriver driver;
    private WebDriverWait wait;

    // Elements on form
    private String formPath = "//form[@id='userForm']"; // TODO: problem to fix: element not found <- page does not load completely <- why?
    private WebElement form, nameFieldsPath, stateCityPath;
    private WebElement firstNameField, lastNameField, emailField, genderFieldPath, mobileField, hobbiesBoxes, addressField, stateField, cityField;
    private WebElement submitBtn;

    public MainPage() {
        open(url);
        driver = WebDriverRunner.getWebDriver();
        System.out.println("Driver initialised");
        //driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("implicit wait set to 10 sec");
        wait = new WebDriverWait(driver, 10);
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(formPath)));
        form = driver.findElement(By.xpath(formPath));
        System.out.println("Form element assigned"); // TODO: does not reach
        closeAd();
        System.out.println("Ad is closed");

        nameFieldsPath = form.findElement(By.xpath("//div[@id='userName-wrapper']//div[@class='col-md-4 col-sm-6']"));
        stateCityPath = form.findElement(By.xpath("//div[@id='stateCity-wrapper']//div[@class='col-md-4 col-sm-12']"));

        firstNameField = nameFieldsPath.findElement(By.xpath("//input[@id='firstName']"));
        lastNameField = nameFieldsPath.findElement(By.xpath("//input[@id='lastName']"));
        emailField = form.findElement(By.xpath("//input[@id='userEmail']"));
        genderFieldPath = form.findElement(By.xpath("//div[@id='genterWrapper']//div[@class='col-md-9 col-sm-12']//div[@class='custom-control custom-radio custom-control-inline']"));
        mobileField = form.findElement(By.xpath("//input[@id='userNumber']"));
        hobbiesBoxes = form.findElement(By.xpath("//div[@id='hobbiesWrapper']//div[@class='col-md-9 col-sm-12']"));
        addressField = form.findElement(By.xpath("//textarea[@id='currentAddress']"));
        stateField = stateCityPath.findElement(By.xpath("//div[@id='state']//div[@class=' css-yk16xz-control']//div[@class=' css-1hwfws3']"));
        cityField = stateCityPath.findElement(By.xpath("//div[@id='city']//div[@class=' css-1fhf3k1-control']//div[@class=' css-1hwfws3']"));
        submitBtn = form.findElement(By.xpath("//button[@id='submit']"));
    }

    public void openPage() {
        open(url);
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(formPath)));
        closeAd();
    }

    private void closeAd() {
        driver.findElement(By.xpath("//a[@id='close-fixedban']")).click();
    }

    public void fillMandatoryFields() {
        fillName(MandatoryFields.getFirstName(), MandatoryFields.getLastName());
        pickGender(MandatoryFields.getGender());
        fillMobile(MandatoryFields.getMobile());
    }

    public void submit() {
        submitBtn.click();
    }

    public void fillName(String firstName, String lastName) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
    }

    public void fillEmail(String email) {
        emailField.sendKeys(email);
    }

    public void pickGender(String gender) {
        WebElement radioButton;
        switch (gender) {
            case "Male":
                radioButton = genderFieldPath.findElement(By.xpath("//input[@value='Male']"));
                break;
            case "Female":
                radioButton = genderFieldPath.findElement(By.xpath("//input[@value='Female']"));
                break;
            case "Other":
                radioButton = genderFieldPath.findElement(By.xpath("//input[@value='Other']"));
                break;
            default:
                System.out.println("Invalid gender!");
                return;
        }
        radioButton.findElement(By.xpath("//parent::div")).click();
    }

    public void fillMobile(String mobileNumber) {
        mobileField.sendKeys(mobileNumber);
    }

    public void pickHobbies(String[] hobbies) {
        for (String hobby : hobbies) {
            WebElement hobbyBox = hobbiesBoxes.findElement(By.xpath("//div[@class='custom-control custom-checkbox custom-control-inline']//input[text()='" + hobby +"']"));
            hobbyBox.click();
        }
    }

    public void fillAddress(String address) {
        addressField.sendKeys(address);
    }

    public void pickStateAndCity(String state, String city) {
        stateField.sendKeys(state);
        stateField.findElement(By.xpath("//div[text()='" + state + "']")).click();
        cityField.sendKeys(city);
        cityField.findElement(By.xpath("//div[text()='" + city + "']")).click();
    }

    public void pickStateAndCity(int state, int city) {
        pickOnlyState(state);
        cityField.click();
        List<WebElement> cities = cityField.findElements(By.xpath("//div[@class=' css-1uccc91-singleValue']"));
        cities.get(city).click();
    }

    public void pickOnlyState(int state) {
        stateField.click();
        List<WebElement> states = stateField.findElements(By.xpath("//div[@class=' css-1uccc91-singleValue']"));
        states.get(state).click();
    }

    public static String getUrl() {
        return url;
    }

    public WebDriver getWebDriver() { return driver; }

    public String getForm() {
        return formPath;
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmailField() {
        return emailField.getText();
    }

    public String getGender() {
        for (WebElement gender : genderFieldPath.findElements(By.xpath("//input[@name='gender']"))) {
            if (gender.isSelected()) {
                return gender.findElement(By.xpath("//parent::div//label[@class='custom-control-label']")).getText();
            }
        }
        return null;
    }

    public String getMobile() {
        return mobileField.getText();
    }

    public List<String> getHobbies() {
        List<String> hobbies = new ArrayList<>();
        for (WebElement hobby : hobbiesBoxes.findElements(By.xpath("//div[@class='custom-control custom-checkbox custom-control-inline']"))) {
            if (hobby.findElement(By.xpath("//child::input")).isSelected()) {
                hobbies.add(hobby.findElement(By.xpath("//parent::div//label[@class='custom-control-label']")).getText());
            }
        }
        return hobbies;
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getState() {
        return stateField.getText();
    }

    public String getCity() {
        return cityField.getText();
    }
}
