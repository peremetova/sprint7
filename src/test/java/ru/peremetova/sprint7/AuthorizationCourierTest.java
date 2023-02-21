package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.api.client.CourierClient;
import ru.peremetova.sprint7.api.data.Authorization;
import ru.peremetova.sprint7.api.data.Courier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthorizationCourierTest {

    private static final String LOGIN = "marusya1";
    private static final String PASSWORD = "5687";

    private final Authorization authorization = new Authorization(LOGIN, PASSWORD);
    private final Courier courier = new Courier(LOGIN, PASSWORD, "Marusya");
    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        courierClient.createCourier(courier);
    }

    @After
    public void setDown() {
        int id = courierClient.getClientId(courier);
        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Базовая проверка авторизации курьера")
    public void authorizationCourierTest() {
        courierClient
                .loginCourier(authorization)
                .statusCode(200)
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка авторизации курьера без логина. Ожидается ошибка 400.")
    public void authorizationCourierNoLoginTest() {
        courierClient
                .loginCourier(new Authorization(null, PASSWORD))
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка авторизации курьера без пароля. Ожидается ошибка 400.")
    public void authorizationCourierNoPasswordTest() {
        courierClient
                .loginCourier(new Authorization(LOGIN, null))
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с неверным логином")
    @Description("Проверка авторизации курьера с неверным логином. Ожидается ошибка 404.")
    public void authorizationCourierWrongLoginTest() {
        courierClient
                .loginCourier(new Authorization("marusya", "5687"))
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неверным паролем")
    @Description("Проверка авторизации курьера с неверным паролем. Ожидается ошибка 404.")
    public void authorizationCourierWrongPasswordTest() {
        courierClient
                .loginCourier(new Authorization("marusya1", "1234"))
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }
}