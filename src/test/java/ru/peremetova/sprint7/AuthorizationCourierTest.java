package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.data.Authorization;
import ru.peremetova.sprint7.data.Courier;
import ru.peremetova.sprint7.data.SimpleId;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthorizationCourierTest {

    private static final String LOGIN = "marusya1";
    private static final String PASSWORD = "5687";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";

    private final Authorization authorization = new Authorization(LOGIN, PASSWORD);
    private final Courier courier = new Courier(LOGIN, PASSWORD, "Marusya");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @After
    public void setDown() {
        int id = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .body()
                .as(SimpleId.class) // --> SimpleId
                .getId();
        given()
                .delete("/api/v1/courier/" + id);
    }

    @Test
    @DisplayName("Авторизация курьера: " + API_V_1_COURIER_LOGIN)
    @Description("Базовая проверка авторизации курьера.")
    public void authorizationCourierTest() {
        given()
                .header("Content-type", "application/json")
                .body(authorization)
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200);
    }

    @Test
    @DisplayName("Авторизация курьера без логина: " + API_V_1_COURIER_LOGIN)
    @Description("Проверка авторизации курьера без логина. Ожидается ошибка 400.")
    public void authorizationCourierNoLoginTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Authorization(null, PASSWORD))
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля: " + API_V_1_COURIER_LOGIN)
    @Description("Проверка авторизации курьера без пароля. Ожидается ошибка 400.")
    public void authorizationCourierNoPasswordTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Authorization(LOGIN, null))
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Авторизация курьера с неверным логином: " + API_V_1_COURIER_LOGIN)
    @Description("Проверка авторизации курьера с неверным логином. Ожидается ошибка 404.")
    public void authorizationCourierWrongLoginTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Authorization("marusya", "5687"))
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @Test
    @DisplayName("Авторизация курьера с неверным паролем: " + API_V_1_COURIER_LOGIN)
    @Description("Проверка авторизации курьера с неверным паролем. Ожидается ошибка 404.")
    public void authorizationCourierWrongPasswordTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Authorization("marusya1", "1234"))
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }
}