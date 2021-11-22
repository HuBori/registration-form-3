package com.example.registration_form.tests;

import com.example.registration_form.helpers.MainPage;
import com.example.registration_form.helpers.PopupWindow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmokeTests {
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
    @CsvFileSource(resources = "/mandatory.csv", delimiter = ';', numLinesToSkip = 1)
    public void happyPath(String fName, String lName, String gender, String mobile) {
        mainPage.fillName(fName, lName);
        String fNameResult = mainPage.getFirstName();
        String lNameResult = mainPage.getLastName();
        assertEquals(fName, fNameResult);
        System.out.println("INFO: Successfully filled firstName");
        assertEquals(lName, lNameResult);
        System.out.println("INFO: Successfully filled lastName");

        mainPage.pickGender(gender);
        String genderResult = mainPage.getGender();
        assertEquals(gender, genderResult);
        System.out.println("INFO: Successfully picked gender");

        mainPage.fillMobile(mobile);
        String mobileResult = mainPage.getMobile();
        assertEquals(mobile, mobileResult);
        System.out.println("INFO: Successfully filled mobile");

        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());

        String name = fName + " " + lName;
        assertTrue(popup.validatePopup(name,"",gender,mobile,"","",""));
        popup.close();
        assertTrue(popup.validatePresent(true));
        System.out.println("INFO: All data is valid");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/valid.csv", numLinesToSkip = 1)
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
        assertTrue(mainPage.compareHobbies(hobbies));
        mainPage.fillAddress(address);
        assertEquals(address, mainPage.getAddress());
        mainPage.pickStateAndCity(state, city);
        assertEquals(state, mainPage.getState());
        assertEquals(city, mainPage.getCity());

        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        String name = fName + " " + lName;
        String stateAndCity = state + " " + city;
        assertTrue(popup.validatePopup(name, email, gender, mobile, hobbies, address, stateAndCity));
    }
}
