package com.example.registration_form.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class PopupWindow {
    private static WebDriver driver;
    private static WebDriverWait wait;

    // Elements
    private final String popupPath = "//div[@class='modal-dialog modal-lg']";

    private final String nameCellPath = "//td[text()='Student Name']//following-sibling::td";
    private final String emailCellPath = "//td[text()='Student Email']//following-sibling::td";
    private final String genderCellPath = "//td[text()='Gender']//following-sibling::td";
    private final String mobileCellPath = "//td[text()='Mobile']//following-sibling::td";
    private final String hobbiesCellPath = "//td[text()='Hobbies']//following-sibling::td";
    private final String addressCellPath = "//td[text()='Address']//following-sibling::td";
    private final String stateAndCityCellPath = "//td[text()='State and City']//following-sibling::td";

    private final String btn = "//button[@id='closeLargeModal']";

    public PopupWindow(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 2);
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(btn)));
    }

    public boolean validatePopup(String name, String email, String gender, String mobile, String hobbies, String address, String stateAndCity) {
        String differences = "";
        if (validatePresent(true)) {
            HashMap<String, String> toCompare = new HashMap<String, String>() {{
                put(nameCellPath, name);
                put(emailCellPath, email);
                put(genderCellPath, gender);
                put(mobileCellPath, mobile);
                put(hobbiesCellPath, hobbies);
                put(addressCellPath, address);
                put(stateAndCityCellPath, stateAndCity);
            }};
            for (String path : toCompare.keySet()) {
                if (toCompare.get(path) != null) {
                    if (!toCompare.get(path).equals("")) {
                        String result = driver.findElement(By.xpath(path)).getText();
                        if (!result.equals(toCompare.get(path))) {
                            differences += "\nexpected: " + toCompare.get(path) + "\t<->\tresult: " + result;
                        }
                    }
                }
            }
            System.out.println(differences);
            return differences.length() == 0;
        }
        return false;
    }

    public void close() {
        driver.findElement(By.xpath(btn)).click();
    }

    public boolean validatePresent(boolean shouldBe) {
        try {
            if (shouldBe) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popupPath)));
            }
            return driver.findElement(By.xpath(popupPath)).isDisplayed();
        } catch (NoSuchElementException e) {
            return !shouldBe;
        }
    }
}
