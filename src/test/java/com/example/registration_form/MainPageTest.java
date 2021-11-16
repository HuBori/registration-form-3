package com.example.registration_form;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;


public class MainPageTest {
    private static MainPage mainPage;
    private static PopupWindow popup;

    @BeforeAll
    public static void setUpAll() {
        mainPage = new MainPage();
        popup = new PopupWindow(mainPage.getWebDriver());
    }

    @BeforeEach
    public void setUp() {
        mainPage.openPage();
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

        mainPage.submit();

        String name = fName + " " + lName;
        assertTrue(popup.validatePopup(name,"",gender,mobile,"","",""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/missingmandatory.csv", numLinesToSkip = 1)
    public void missingMandatory(String fName, String lName, String gender, String mobile) {
        mainPage.fillName(fName, lName);
        mainPage.pickGender(gender);
        mainPage.fillMobile(mobile);
        mainPage.submit();
        assertFalse(popup.validatePresent());
    }


}
