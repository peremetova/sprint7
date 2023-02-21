package ru.peremetova.sprint7.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    public static final String API_V_1_ORDERS = "/api/v1/orders";

    @Step("Create Order. Send POST request to" + API_V_1_ORDERS)
    public ValidatableResponse createOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .body(body)
                .when()
                .post(API_V_1_ORDERS)
                .then();
    }

    @Step("Get Orders. Send GET request to" + API_V_1_ORDERS)
    public ValidatableResponse getOrders() {
        return given()
                .get(API_V_1_ORDERS)
                .then();
    }
}
