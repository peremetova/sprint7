package ru.peremetova.sprint7.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.peremetova.sprint7.api.data.SimpleId;

import static io.restassured.RestAssured.given;

public class CourierClient {

    public static final String API_V_1_COURIER = "/api/v1/courier";
    public static final String API_V_1_COURIER_LOGIN = "/api/v1/courier/login";

    @Step("Create courier. Send POST request to " + API_V_1_COURIER)
    public ValidatableResponse createCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(API_V_1_COURIER)
                .then();
    }

    @Step("Login Courier. Send POST request to" + API_V_1_COURIER_LOGIN)
    public ValidatableResponse loginCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(API_V_1_COURIER_LOGIN)
                .then();
    }

    @Step("Delete courier by id. Send DELETE request to " + API_V_1_COURIER + "/{id}")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .delete(API_V_1_COURIER + "/" + id)
                .then();
    }

    @Step("Get courier id via login.")
    public int getClientId(Object body) {
        return loginCourier(body)
                .extract()
                .body()
                .as(SimpleId.class)
                .getId();
    }
}
