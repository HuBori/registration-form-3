package com.example.registration_form;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPage {
    private static String url = "https://demoqa.com/automation-practice-form";
    private WebDriver driver;

    // Elements on form
    private WebElement formPath = driver.findElement(By.xpath("//div[class='row']/div[class='col-12 mt-4 col-md-6']/div[class='practice-form-wrapper']/form[id='userForm']"));
    private WebElement nameFieldsPath = formPath.findElement(By.xpath("//div[id='userName-wrapper']/div[class='col-md-4 col-sm-6']"));
    private WebElement stateCityPath = formPath.findElement(By.xpath("//div[id='stateCity-wrapper']/div[class='col-md-4 col-sm-12']"));

    private WebElement firstNameField = nameFieldsPath.findElement(By.xpath("/input[id='firstName']"));
    private WebElement lastNameField = nameFieldsPath.findElement(By.xpath("/input[id='lastName']"));
    private WebElement emailField = formPath.findElement(By.xpath("//div[id='userEmail-wrapper']/div[class='col-md-9 col-sm-12']/input[id='userEmail']"));
    private WebElement genderFieldPath = formPath.findElement(By.xpath("//div[id='genterWrapper']/div[class='col-md-9 col-sm-12']/div[class='custom-control custom-radio custom-control-inline']"));
    private WebElement mobileField = formPath.findElement(By.xpath("//div[id='userNumber-wrapper']/div[class='col-md-9 col-sm-12']/input[id='userNumber']"));
    private WebElement hobbiesBoxes = formPath.findElement(By.xpath("//div[id='hobbiesWrapper']//div[class='col-md-9 col-sm-12']/div"));
    private WebElement addressField = formPath.findElement(By.xpath("//div[id='currentAddress-wrapper']/div[class='col-md-9 col-sm-12']/textarea[id='currentAddress']"));
    private WebElement stateField = stateCityPath.findElement(By.xpath("/div[id='state']/div[class=' css-yk16xz-control']/div[class=' css-1hwfws3']"));
    private WebElement cityField = stateCityPath.findElement(By.xpath(stateCityPath + "/div[id='city']/div[class=' css-1fhf3k1-control']/div[class=' css-1hwfws3']"));
    private WebElement submitBtn = formPath.findElement(By.xpath("//div[class='mt-4 justify-content-end row']/div[class='text-right col-md-2 col-sm-12']/button[id='submit']"));

    // Elements on popup window
    private String popupPath = "//div[class='modal-dialog modal-lg']";
    private WebElement table = driver.findElement(By.xpath("//div[class='modal-content']/div[class='modal-body']/div[class='table-responsive']"));

    private WebElement nameCell = table.findElement(By.xpath("//td[text()='Student Name']/following-sibling::td"));
    private WebElement emailCell = table.findElement(By.xpath("//td[text()='Student Email\t']/following-sibling::td"));
    private WebElement genderCell = table.findElement(By.xpath("//td[text()='Gender']/following-sibling::td"));
    private WebElement mobileCell = table.findElement(By.xpath("//td[text()='Mobile']/following-sibling::td"));
    private WebElement hobbiesCell = table.findElement(By.xpath("//td[text()='Hobbies']/following-sibling::td"));
    private WebElement addressCell = table.findElement(By.xpath("//td[text()='Address']/following-sibling::td"));
    private WebElement stateAndCityCell = table.findElement(By.xpath("//td[text()='State and City']/following-sibling::td"));

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean validatePopup(String name, String email, String gender, String mobile, String hobbies, String address, String stateAndCity) {
        submit();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popupPath)));
        if(!driver.findElement(By.xpath(popupPath)).isDisplayed()) { return false; }
        Alert alert = driver.switchTo().alert();
        HashMap<WebElement, String> toCompare = new HashMap<WebElement, String>(){{
           put(nameCell, name);
           put(emailCell, email);
           put(genderCell, gender);
           put(mobileCell, mobile);
           put(hobbiesCell, hobbies);
           put(addressCell, address);
           put(stateAndCityCell, stateAndCity);
        }};
        for (WebElement cell : toCompare.keySet()) {
            if (toCompare.get(cell) != null) {
                if (toCompare.get(cell) != "") {
                    if (cell.getText() != toCompare.get(cell)) { return false; }
                }
            }
        }
        alert.accept();
        return true;
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
        switch (gender.toLowerCase()) {
            case "1":
            case "m":
            case "male":
                radioButton = genderFieldPath.findElement(By.xpath("/input[value='Male']"));
                break;
            case "2":
            case "f":
            case "female":
                radioButton = genderFieldPath.findElement(By.xpath("/input[value='Female']"));
                break;
            default:
                radioButton = genderFieldPath.findElement(By.xpath("/input[value='Other']"));
        }
        radioButton.findElement(By.xpath("parent::div")).click();
    }

    public void fillMobile(String mobileNumber) {
        mobileField.sendKeys(mobileNumber);
    }

    public void pickHobbies(String[] hobbies) {
        boolean str = false;
        if (hobbies.length > 0) {
            str = hobbies[0].toLowerCase().matches("[a-z]+");
        }
        for (String hobby : hobbies) {
            WebElement hobbyBox = hobbiesBoxes.findElement(By.xpath((str ? "/input[text()='" : "/input[value='") + hobby +"']"));
            hobbyBox.click();
        }
    }

    public void fillAddress(String address) {
        addressField.sendKeys(address);
    }

    public void pickStateAndCity(String state, String city) {
        stateField.sendKeys(state);
        stateField.findElement(By.xpath("/div[text()='" + state + "']")).click();
        cityField.sendKeys(city);
        cityField.findElement(By.xpath("/div[text()='" + city + "']")).click();
    }

    public void pickStateAndCity(int state, int city) {
        pickOnlyState(state);
        cityField.click();
        List<WebElement> cities = cityField.findElements(By.xpath("div[class=' css-1uccc91-singleValue']"));
        cities.get(city).click();
    }

    public void pickOnlyState(int state) {
        stateField.click();
        List<WebElement> states = stateField.findElements(By.xpath("div[class=' css-1uccc91-singleValue']"));
        states.get(state).click();
    }

    public static String getUrl() {
        return url;
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
        for (WebElement gender : genderFieldPath.findElements(By.xpath("/input[name='gender']"))) {
            if (gender.isSelected()) {
                return gender.findElement(By.xpath("/parent::div/label[class='custom-control-label']")).getText();
            }
        }
        return null;
    }

    public String getMobile() {
        return mobileField.getText();
    }

    public List<String> getHobbies() {
        List<String> hobbies = new ArrayList<>();
        for (WebElement hobby : hobbiesBoxes.findElements(By.xpath("/div[class='custom-control custom-checkbox custom-control-inline']"))) {
            if (hobby.findElement(By.xpath("/child::input")).isSelected()) {
                hobbies.add(hobby.findElement(By.xpath("/parent::div/label[class='custom-control-label']")).getText());
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
