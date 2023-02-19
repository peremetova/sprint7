package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.data.Courier;
import ru.peremetova.sprint7.data.SimpleId;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    public static final String API_V_1_COURIER = "/api/v1/courier";
    private final Courier courier = new Courier("marusya1", "5687", "Marusya");


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
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
    @DisplayName("Создание курьера: " + API_V_1_COURIER)
    @Description("Проверка создания курьера.")
    public void createCourierTest() {
        createCourierApi()
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров: " + API_V_1_COURIER)
    @Description("Проверка создания двух одинаковых курьеров. Ожидается ошибка 409.")
    public void createCourierDoubleTest() {
        createCourierApi()
                .then()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
        createCourierApi()
                .then()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @Step("Send POST request to /api/v1/courier")
    private Response createCourierApi() {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_V_1_COURIER);
    }
}
