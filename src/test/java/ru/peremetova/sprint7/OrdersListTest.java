package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.api.client.OrdersClient;

import static org.hamcrest.CoreMatchers.hasItems;

public class OrdersListTest {

    private final OrdersClient ordersClient = new OrdersClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Базовая проверка получения списка заказов без прараметров.")
    public void getOrdersTest() {
        ordersClient.getOrders()
                .statusCode(200)
                .assertThat()
                .body("orders", hasItems());
    }
}
