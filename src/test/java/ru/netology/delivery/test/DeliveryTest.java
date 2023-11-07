package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;
import com.github.javafaker.Faker;
import java.time.Duration;
import java.util.Locale;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Faker faker = new Faker(new Locale("en"));
        String password = faker.internet().domainName();
        System.out.println(password);
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__title").shouldHave(Condition.text("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + firstMeetingDate))
                .shouldBe(Condition.visible);
        $("[data-test-id=success-notification]").shouldBe(hidden, Duration.ofSeconds(15));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(visible);
        $("[data-test-id=replan-notification] .notification__title").shouldHave(Condition.text("Необходимо подтверждение"))
                .shouldBe(Condition.visible);
        $("[data-test-id=replan-notification] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=replan-notification]").shouldBe(hidden);
        $("[data-test-id=success-notification] .notification__title").shouldHave(Condition.text("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + secondMeetingDate))
                .shouldBe(Condition.visible);
    }
}
