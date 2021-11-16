package com.example.registration_form;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    private static MainPage mainPage;

    @BeforeAll
    public static void setUpAll() {
        Configuration.startMaximized = true;
        WebDriver driver = new ChromeDriver();
        mainPage = new MainPage(driver);
    }

    @BeforeEach
    public void setUp() {
        open(mainPage.getUrl());
    }

    @Test
    public void search() {
        
    }
}
