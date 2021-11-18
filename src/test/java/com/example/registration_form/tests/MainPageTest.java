package com.example.registration_form.tests;

import com.example.registration_form.helpers.MainPage;
import com.example.registration_form.helpers.PopupWindow;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @NullAndEmptySource
    @CsvFileSource(resources = "com/example/registration_form/resources/missingmandatory.csv", numLinesToSkip = 1)
    public void missingMandatory(String fName, String lName, String gender, String mobile) {
        mainPage.fillName(fName, lName);
        mainPage.pickGender(gender);
        mainPage.fillMobile(mobile);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertFalse(popup.validatePresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/invalidname.csv", numLinesToSkip = 1)
    public void invalidName(String fName, String lName) {
        mainPage.fillMandatoryFields();
        mainPage.fillName(fName, lName);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertFalse(popup.validatePresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/invalidmobile.csv", numLinesToSkip = 1)
    public void invalidMobile(String mobile) {
        mainPage.fillMandatoryFields();
        mainPage.fillMobile(mobile);
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertFalse(popup.validatePresent());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/genders.csv", numLinesToSkip = 1)
    public void genders(String gender) {
        mainPage.fillMandatoryFields();
        mainPage.pickGender(gender);
        assertEquals(gender, mainPage.getGender());
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePopup("", "", gender, "", "", "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/hobbies.csv", numLinesToSkip = 1)
    public void hobbies(String hobbies) {
        mainPage.fillMandatoryFields();
        mainPage.pickHobbies(hobbies.split(", "));
        assertTrue(mainPage.compareHobbies(hobbies));
        mainPage.submit();
        popup = new PopupWindow(mainPage.getWebDriver());
        assertTrue(popup.validatePopup("", "", "", "", hobbies, "", ""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/statecity.csv", numLinesToSkip = 1)
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

    @Test
    public void testCsvFiles() {
        List<String> pathes = new ArrayList<String>() {{
            add("src/test/java/com/example/registration_form/resources/mandatory.csv");
            add("/src/test/java/com/example/registration_form/resources/mandatory.csv");
            add("com/example/registration_form/resources/mandatory.csv");
            add("/com/example/registration_form/resources/mandatory.csv");
            add("/mandatory.csv");
            add("mandatory.csv");
            add("src\\test\\java\\com\\example\\registration_form\\resources\\mandatory.csv");
            add("\\src\\test\\java\\com\\example\\registration_form\\resources\\mandatory.csv");
            add("com\\example\\registration_form\\resources\\mandatory.csv");
            add("\\com\\example\\registration_form\\resources\\mandatory.csv");
            add("\\mandatory.csv");
        }};
        for (String path : pathes) {
            File f = new File(path);
            if (f.exists() && !f.isDirectory()) {
                System.out.println("Valid path:\n\t" + path);
                List<List<String>> records = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        records.add(Arrays.asList(values));
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
