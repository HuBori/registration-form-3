package com.example.registration_form;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private static String url = "https://demoqa.com/automation-practice-form";
    private WebDriver driver;

    // Elements
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

    public MainPage(WebDriver driver) {
        this.driver = driver;
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
                radioButton = driver.findElement(By.xpath(formPath + "/input[value='Male']"));
                break;
            case "2":
            case "f":
            case "female":
                radioButton = driver.findElement(By.xpath(formPath + "/input[value='Female']"));
                break;
            default:
                radioButton = driver.findElement(By.xpath(formPath + "/input[value='Other']"));
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
}
