package com.solvd.notix.utils;

import com.github.javafaker.Faker;

public class DataFaker {

    private static Faker faker = new Faker();

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getAddress() {
        return faker.address().fullAddress();
    }

    public static String getName() {
        return faker.name().fullName();
    }

    public static String getPhoneNumber() {
        return faker.numerify("#########");
    }
}
