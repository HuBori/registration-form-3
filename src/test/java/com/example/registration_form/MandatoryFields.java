package com.example.registration_form;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MandatoryFields {
    private static final String COMMA_DELIMITER = ",";
    private static String firstName;
    private static String lastName;
    private static String gender;
    private static String mobile;

    public static void readCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/mandatory.csv"))) {

            List<List<String>> result = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                result.add(Arrays.asList(values));
            }

            firstName = result.get(0).get(0);
            lastName = result.get(0).get(1);
            gender = result.get(0).get(2);
            mobile = result.get(0).get(3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getGender() {
        return gender;
    }

    public static String getMobile() {
        return mobile;
    }
}
