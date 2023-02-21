package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.peremetova.sprint7.api.client.OrdersClient;
import ru.peremetova.sprint7.api.data.Order;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final OrdersClient ordersClient = new OrdersClient();

    private Order order = new Order(
            "Naruto",
            "Uchiha",
            "Konoha, 142 apt.",
            4,
            "+7 800 355 35 35",
            5,
            "2020-06-06",
            "Saske, come back to Konoha",
            new String[]{}
    );

    private String[] color;

    public CreateOrderTest(String[] color, String name) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Параметры цвета: {1}")
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{Order.COLOR_BLACK}, "Black"},
                {new String[]{Order.COLOR_GREY}, "Grey"},
                {new String[]{Order.COLOR_BLACK, Order.COLOR_GREY}, "Black, Grey"},
                {new String[]{}, "Empty"},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа.")
    public void createOrderTest() {
        order.setColor(color);
        ordersClient
                .createOrder(order)
                .statusCode(201)
                .assertThat()
                .body("track", notNullValue());
    }
}