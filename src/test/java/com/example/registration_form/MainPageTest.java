package com.example.registration_form;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.stream.Stream;

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
    @CsvFileSource(resources = "csv/valid.csv", numLinesToSkip = 1)
    public void allValidData(String fName, String lName, String email, String gender, String mobile, String hobbies, String address, String state, String city) {
        mainPage.fillName(fName, lName);
        assertEquals(fName, mainPage.getFirstName());
        assertEquals(lName, mainPage.getLastName());
        mainPage.fillEmail(email);
        assertEquals(email, mainPage.getEmailField());
        mainPage.pickGender(gender);
        assertEquals(gender, mainPage.getGender());
        mainPage.fillMobile(mobile);
        assertEquals(mobile, mainPage.getMobile());
        mainPage.pickHobbies(hobbies.split(", "));
        assertEquals(Arrays.stream(hobbies.split(", ")).sorted(), mainPage.getHobbies());
        mainPage.fillAddress(address);
        assertEquals(address, mainPage.getAddress());
        mainPage.pickStateAndCity(state, city);
        assertEquals(state, mainPage.getState());
        assertEquals(city, mainPage.getCity());

        mainPage.submit();
        String name = fName + " " + lName;
        String stateAndCity = state + " " + city;
        assertTrue(popup.validatePopup(name, email, gender, mobile, hobbies, address, stateAndCity));
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

    @ParameterizedTest
    @CsvFileSource(resources = "csv/invalidname.csv", numLinesToSkip = 1)
    public void invalidName(String fName, String lName) {
        mainPage.fillMandatoryFields();
        mainPage.fillName(fName, lName);
        mainPage.submit();
        assertFalse(popup.validatePresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/invalidmobile.csv", numLinesToSkip = 1)
    public void invalidMobile(String mobile) {
        mainPage.fillMandatoryFields();
        mainPage.fillMobile(mobile);
        mainPage.submit();
        assertFalse(popup.validatePresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/genders.csv", numLinesToSkip = 1)
    public void genders(String gender) {
        mainPage.fillMandatoryFields();
        mainPage.pickGender(gender);
        assertEquals(gender, mainPage.getGender());
        mainPage.submit();
        assertTrue(popup.validatePopup("", "", gender, "", "", "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/hobbies.csv", numLinesToSkip = 1)
    public void hobbies(String hobbies) {
        mainPage.fillMandatoryFields();
        mainPage.pickHobbies(hobbies.split(", "));
        Stream<String> sortedHobbies = Arrays.stream(hobbies.split(", ")).sorted();
        Stream<String> sortedResult = mainPage.getHobbies();
        assertEquals(sortedHobbies, sortedResult);
        mainPage.submit();
        assertTrue(popup.validatePopup("", "", "", "", hobbies, "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "csv/statecity.csv", numLinesToSkip = 1)
    public void stateAndCity(String state, String city) {
        mainPage.fillMandatoryFields();
        assertEquals(state, mainPage.getState());
        mainPage.pickStateAndCity(state, city);
        assertEquals(city, mainPage.getCity());

        mainPage.submit();
        String stateCity = state + " " + city;
        assertTrue(popup.validatePopup("", "", "", "", "", "", stateCity));
    }
}
