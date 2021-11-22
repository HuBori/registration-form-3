package com.example.registration_form.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MandatoryFields {
    private static final String DELIMITER = ";";
    private static String firstName;
    private static String lastName;
    private static String gender;
    private static String mobile;

    public MandatoryFields() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/mandatory.csv"))) {

            List<List<String>> result = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER);
                result.add(Arrays.asList(values));
            }

            firstName = result.get(1).get(0);
            lastName = result.get(1).get(1);
            gender = result.get(1).get(2);
            mobile = result.get(1).get(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getMobile() {
        return mobile;
    }
}
