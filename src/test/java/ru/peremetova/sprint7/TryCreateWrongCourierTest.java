package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.api.client.CourierClient;
import ru.peremetova.sprint7.api.data.Courier;

import static org.hamcrest.CoreMatchers.equalTo;

public class TryCreateWrongCourierTest {

    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка создания курьера без логина. Ожидается ошибка 400.")
    public void createCourierNoLoginTest() {
        courierClient
                .createCourier(new Courier(null, "5687", "Marusya"))
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка создания курьера без пароля. Ожидается ошибка 400.")
    public void createCourierNoPasswordTest() {
        courierClient
                .createCourier(new Courier("Marta", null, "Marusya"))
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
