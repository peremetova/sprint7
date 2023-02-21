package ru.peremetova.sprint7;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.sprint7.api.client.CourierClient;
import ru.peremetova.sprint7.api.data.Courier;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {

    private final Courier courier = new Courier("marusya1", "5687", "Marusya");
    private final CourierClient courierClient = new CourierClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void setDown() {
        int id = courierClient.getClientId(courier);
        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка создания курьера.")
    public void createCourierTest() {
        courierClient
                .createCourier(courier)
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    @Description("Проверка создания двух одинаковых курьеров. Ожидается ошибка 409.")
    public void createCourierDoubleTest() {
        courierClient
                .createCourier(courier)
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
        courierClient
                .createCourier(courier)
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}
