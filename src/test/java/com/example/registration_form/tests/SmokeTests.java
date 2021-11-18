package com.example.registration_form.tests;

import com.example.registration_form.helpers.MainPage;
import com.example.registration_form.helpers.PopupWindow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    //@ParameterizedTest
    //@CsvFileSource(resources = "src/test/java/com/example/registration_form/resources/mandatory.csv", delimiter = ';', numLinesToSkip = 1)
    //public void happyPath(String fName, String lName, String gender, String mobile) {
    @Test
    //@CsvSource({"Valid,Name,Male,0123456789"})
    public void happyPath() {
        String fName = "Valid";
        String lName = "Name";
        String gender = "Male";
        String mobile = "0123456789";

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
        assertTrue(popup.validatePresent());
        System.out.println("INFO: All data is valid");
    }

    //@ParameterizedTest
    //@CsvFileSource(resources = "com/example/registration_form/resources/valid.csv", numLinesToSkip = 1)
    //@CsvSource(value = {"Valid:Name:valid@email.com:Male:0123456789:Sports:Dummy str. 1.:Rajasthan:Agra"}, delimiter = ':')
    //public void allValidData(String fName, String lName, String email, String gender, String mobile, String hobbies, String address, String state, String city) {
    @Test
    public void allValidData() {
        String fName = "Valid";
        String lName = "Name";
        String email = "valid@email.com";
        String gender = "Male";
        String mobile = "0123456789";
        String hobbies = "Sports";
        String address = "Dummy str. 1.";
        String state = "Uttar Pradesh";
        String city = "Agra";

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

    //@Test
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
