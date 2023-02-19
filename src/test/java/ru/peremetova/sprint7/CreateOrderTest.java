package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.peremetova.sprint7.data.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    public static final String API_V_1_ORDERS = "/api/v1/orders";
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
    @DisplayName("Создание заказа: " + API_V_1_ORDERS)
    @Description("Проверка создания заказа.")
    public void createOrderTest() {
        order.setColor(color);
        given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(API_V_1_ORDERS)
                .then()
                .assertThat()
                .body("track", notNullValue())
                .statusCode(201);
    }
}