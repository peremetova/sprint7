package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;

public class OrdersListTest {

    public static final String API_V_1_ORDERS = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Получение списка заказов:" + API_V_1_ORDERS)
    @Description("Базовая проверка получения списка заказов без прараметров.")
    public void getOrdersTest() {
        given()
                .get(API_V_1_ORDERS)
                .then()
                .assertThat()
                .body("orders", hasItems())
                .statusCode(200);
    }
}
