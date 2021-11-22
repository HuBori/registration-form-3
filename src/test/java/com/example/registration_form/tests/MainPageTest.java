package com.example.registration_form.tests;

import com.example.registration_form.helpers.MainPage;
import com.example.registration_form.helpers.PopupWindow;
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
    }

    @BeforeEach
    public void setUp() {
        mainPage.openPage();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/missingmandatory.csv", numLinesToSkip = 1, delimiter = ';')
    public void missingMandatory(String fName, String lName, String gender, String mobile) {
        mainPage.fillName(fName, lName);
        mainPage.pickGender(gender);
        mainPage.fillMobile(mobile);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePresent(false));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidname.csv", numLinesToSkip = 1)
    public void invalidName(String fName, String lName) {
        mainPage.fillMandatoryFields();
        mainPage.fillName(fName, lName);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePresent(false));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/invalidmobile.csv", numLinesToSkip = 1)
    public void invalidMobile(String mobile) {
        mainPage.fillMandatoryFields();
        mainPage.fillMobile(mobile);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePresent(false));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/genders.csv", numLinesToSkip = 1)
    public void genders(String gender) {
        mainPage.fillMandatoryFields();
        mainPage.pickGender(gender);
        assertEquals(gender, mainPage.getGender());
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePopup("", "", gender, "", "", "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/hobbies.csv", numLinesToSkip = 1)
    public void hobbies(String hobbies) {
        mainPage.fillMandatoryFields();
        mainPage.pickHobbies(hobbies.split(", "));
        assertTrue(mainPage.compareHobbies(hobbies));
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePopup("", "", "", "", hobbies, "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/statecity.csv", numLinesToSkip = 1)
    public void stateAndCity(String state, String city) {
        mainPage.fillMandatoryFields();
        assertEquals(state, mainPage.getState());
        mainPage.pickStateAndCity(state, city);
        assertEquals(city, mainPage.getCity());

        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        String stateCity = state + " " + city;
        assertTrue(popup.validatePopup("", "", "", "", "", "", stateCity));
    }
}
