package com.example.registration_form;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class MainPageTest {
    private static MainPage mainPage;
    private static ChromeDriver driver;

    @BeforeAll
    public static void setUpAll() {
        Configuration.startMaximized = true;
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @BeforeEach
    public void setUp() {
        driver.get(mainPage.getUrl());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/mandatory.csv", numLinesToSkip = 1)
    public void happyPath(String fName, String lName, String gender, String mobile) {
        mainPage.fillName(fName, lName);
        String fNameResult = mainPage.getFirstName();
        String lNameResult = mainPage.getLastName();
        assertEquals(fName, fNameResult);
        assertEquals(lName, lNameResult);
        mainPage.pickGender(gender);
        String genderResult = mainPage.getGender();
        assertEquals(gender, genderResult);
        mainPage.fillMobile(mobile);
        String mobileResult = mainPage.getMobile();
        assertEquals(mobile, mobileResult);
        String name = fName + " " + lName;
        assertTrue(mainPage.validatePopup(name,"",gender,mobile,"","",""));
    }
}
