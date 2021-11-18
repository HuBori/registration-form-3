package com.example.registration_form;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

    //@ParameterizedTest
    //@CsvFileSource(resources = "src/test/java/com/example/registration_form/resources/mandatory.csv", delimiter = ';', numLinesToSkip = 1)
    //public void happyPath(String fName, String lName, String gender, String mobile) {
    @Test
    public void happyPath() {
        String fName = "Valid";
        String lName = "Name";
        String gender = "Male";
        String mobile = "0123456789";

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
        popup = new PopupWindow(mainPage.getWebDriver());

        String name = fName + " " + lName;
        assertTrue(popup.validatePopup(name,"",gender,mobile,"","",""));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "com/example/registration_form/resources/valid.csv", numLinesToSkip = 1)
    //@CsvSource(value = {"Valid:Name:valid@email.com:Male:0123456789:Sports:Dummy str. 1.:Rajasthan:Agra"}, delimiter = ':')
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
        popup = new PopupWindow(mainPage.getWebDriver());
        String name = fName + " " + lName;
        String stateAndCity = state + " " + city;
        assertTrue(popup.validatePopup(name, email, gender, mobile, hobbies, address, stateAndCity));
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
        Stream<String> sortedHobbies = Arrays.stream(hobbies.split(", ")).sorted();
        Stream<String> sortedResult = mainPage.getHobbies();
        assertEquals(sortedHobbies, sortedResult);
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
