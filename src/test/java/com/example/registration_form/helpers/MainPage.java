package com.example.registration_form.helpers;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class MainPage {
    private static final String url = "https:/demoqa.com/automation-practice-form";
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final MandatoryFields mandatory;

    // Elements on form
    private static final String formPath = "//form[@id='userForm']";
    private final String firstNameFieldPath, lastNameFieldPath, emailFieldPath, genderFieldPath, mobileFieldPath, addressFieldPath, stateFieldPath, cityFieldPath;

    public MainPage() {
        open(url);
        driver = WebDriverRunner.getWebDriver();
        wait = new WebDriverWait(driver, 3);
        closeAd();

        firstNameFieldPath = "//input[@id='firstName']";
        lastNameFieldPath = "//input[@id='lastName']";
        emailFieldPath = "//input[@id='userEmail']";
        genderFieldPath = "//div[@id='genterWrapper']//div[@class='col-md-9 col-sm-12']//div[@class='custom-control custom-radio custom-control-inline']";
        mobileFieldPath = "//input[@id='userNumber']";
        addressFieldPath = "//textarea[@id='currentAddress']";
        stateFieldPath = "//div[@id='state']//div[@class=' css-yk16xz-control']//div[@class=' css-1hwfws3']";
        cityFieldPath = "//div[@id='city']//div[@class=' css-yk16xz-control']//div[@class=' css-1hwfws3']";

        mandatory = new MandatoryFields();
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
        fillName(mandatory.getFirstName(), mandatory.getLastName());
        pickGender(mandatory.getGender());
        fillMobile(mandatory.getMobile());
    }

    public void submit() {
        WebElement mobileField = driver.findElement(By.xpath(mobileFieldPath));
        mobileField.submit();
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
        WebElement emailField = driver.findElement(By.xpath(emailFieldPath));
        emailField.sendKeys(email);
    }

    public void pickGender(String gender) {
        if (gender.length() == 0) { return; } // handles empty source

        String radioButtonPath = genderFieldPath;
        WebElement radioButton;
        switch (gender) {
            case "Male":
                radioButtonPath += "//input[@value='Male']";
                break;
            case "Female":
                radioButtonPath += "//input[@value='Female']";
                break;
            case "Other":
                radioButtonPath += "//input[@value='Other']";
                break;
            default:
                System.out.println("Invalid gender!");
                return;
        }
        radioButton = driver.findElement(By.xpath(radioButtonPath + "/.."));
        radioButton.click();
    }

    public void fillMobile(String mobileNumber) {
        WebElement mobileField = driver.findElement(By.xpath(mobileFieldPath));
        while (mobileField.getAttribute("value").length() > 0) {
            mobileField.sendKeys(Keys.BACK_SPACE);
        }
        mobileField.sendKeys(mobileNumber);
    }

    public void pickHobbies(String[] hobbies) {
        for (String hobby : hobbies) {
            WebElement hobbyBox = driver.findElement(By.xpath("//label[text()='" + hobby + "']"));
            hobbyBox.click();
        }
    }

    public void fillAddress(String address) {
        WebElement addressField = driver.findElement(By.xpath(addressFieldPath));
        addressField.sendKeys(address);
    }

    public void pickStateAndCity(String state, String city) {
        WebElement stateField = driver.findElement(By.xpath(stateFieldPath));
        pickFromDropDown(stateField, state);
        WebElement cityField = driver.findElement(By.xpath(cityFieldPath));
        pickFromDropDown(cityField, city);
        driver.findElement(By.xpath(formPath)).click();
    }

    public void pickFromDropDown(WebElement field, String value) { // TODO: write test
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", field);
        field.click();
        WebElement option = driver.findElement(By.xpath("//div[text()='" + value + "']"));
        js.executeScript("arguments[0].scrollIntoView();", option);
        option.click();
    }

    public WebDriver getWebDriver() { return driver; }

    public String getFirstName() {
        return driver.findElement(By.xpath(firstNameFieldPath)).getAttribute("value");
    }

    public String getLastName() {
        return driver.findElement(By.xpath(lastNameFieldPath)).getAttribute("value");
    }

    public String getEmailField() {
        return driver.findElement(By.xpath(emailFieldPath)).getAttribute("value");
    }

    public String getGender() {
        for (WebElement gender : driver.findElements(By.xpath(genderFieldPath + "//input[@name='gender']"))) {
            if (gender.isSelected()) {
                return gender.findElement(By.xpath("//parent::div//label[@class='custom-control-label']")).getText(); // TODO: should fix it here
            }
        }
        return null;
    }

    public String getMobile() {
        return driver.findElement(By.xpath(mobileFieldPath)).getAttribute("value");
    }

    private List<String> getHobbies() {
        List<String> hobbies = new ArrayList<>();
        for (WebElement hobby : driver.findElements(By.xpath("//input[@type='checkbox']"))) {
            if (hobby.isSelected()) {
                hobbies.add(hobby.findElement(By.xpath("./..//label[@class='custom-control-label']")).getText());
            }
        }
        return hobbies;
    }

    public String getAddress() {
        return driver.findElement(By.xpath(addressFieldPath)).getAttribute("value");
    }

    public String getState() {
        return driver.findElement(By.xpath(stateFieldPath + "//div[@class=' css-1uccc91-singleValue']")).getText();
    }

    public String getCity() {
        return driver.findElement(By.xpath(cityFieldPath + "//div")).getText();
    }

    public boolean compareHobbies(String expectedHobbies) {
        String[] expected = expectedHobbies.split(", ");
        List<String> result = getHobbies();
        for (String hobby : expected) {
            if (result.contains(hobby)) {
                result.remove(hobby);
            } else {
                return false;
            }
        }
        return result.size() == 0;
    }
}
