package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("ru"));

    public static String generateDate(long addDays) {
        String date = LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        String[] cities = new String[]{"Москва", "Санкт-Петербург", "Воронеж", "Великий Новгород", "Тверь",
                "Екатеринбург", "Калининград", "Йошкар-Ола", "Пенза", "Южно-Сахалинск", "Челябинск", };
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String locale) {
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            var user = new UserInfo(generateCity("ru"), generateName("ru"), generatePhone("ru"));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}