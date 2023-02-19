package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.data.Courier;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TryCreateWrongCourierTest {

    public static final String API_V_1_COURIER = "/api/v1/courier";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание курьера без логина: " + API_V_1_COURIER)
    @Description("Проверка создания курьера без логина. Ожидается ошибка 400.")
    public void createCourierNoLoginTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Courier(null, "5687", "Marusya"))
                .when()
                .post(API_V_1_COURIER)
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }

    @Test
    @DisplayName("Создание курьера без пароля: " + API_V_1_COURIER)
    @Description("Проверка создания курьера без пароля. Ожидается ошибка 400.")
    public void createCourierNoPasswordTest() {
        given()
                .header("Content-type", "application/json")
                .body(new Courier("Marta", null, "Marusya"))
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }
}
