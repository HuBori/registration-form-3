package com.example.registration_form;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class PopupWindow {
    private static WebDriver driver;

    // Elements
    private String popupPath = "//div[@class='modal-dialog modal-lg']";
    private WebElement table;

    private WebElement nameCell = table.findElement(By.xpath("//td[text()='Student Name']//following-sibling::td"));
    private WebElement emailCell = table.findElement(By.xpath("//td[text()='Student Email\t']//following-sibling::td"));
    private WebElement genderCell = table.findElement(By.xpath("//td[text()='Gender']//following-sibling::td"));
    private WebElement mobileCell = table.findElement(By.xpath("//td[text()='Mobile']//following-sibling::td"));
    private WebElement hobbiesCell = table.findElement(By.xpath("//td[text()='Hobbies']//following-sibling::td"));
    private WebElement addressCell = table.findElement(By.xpath("//td[text()='Address']//following-sibling::td"));
    private WebElement stateAndCityCell = table.findElement(By.xpath("//td[text()='State and City']//following-sibling::td"));

    public PopupWindow(WebDriver driver) {
        this.driver = driver;
        table = this.driver.findElement(By.xpath("//div[@class='modal-content']//div[@class='modal-body']//div[@class='table-responsive']"));
    }

    public boolean validatePopup(String name, String email, String gender, String mobile, String hobbies, String address, String stateAndCity) {
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
}
