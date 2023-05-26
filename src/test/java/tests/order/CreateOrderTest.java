package tests.order;

import api.client.OrderClient;
import api.model.Order;
import api.util.Colors;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;
    private OrderClient orderClient;

    public CreateOrderTest(Order order){
        this.order = order;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters(name="Тестовые данные: {0}")
    public static Object[][] getOrderParameters(){
        return new Object[][] {
                {new Order("Tony", "Stark", "LA, StarkTown", "2",
                        "8-800-555-35-35", "2020-12-14", "Millioner, filantrop, playboy",
                        List.of(Colors.GRAY.name()), 6)},
                {new Order("Naruto", "Uzumaki", "Konoha, 142 apt.", "4",
                        "+7 800 355 35 35", "2020-06-06",
                        "Saske, come back to Konoha", List.of(Colors.BLACK.name()), 5)},
                {new Order("Constantine","Artemyev","Kazan","10",
                        "+7-999-999-99-99", "2022-07-25", "I want to make a lot of money",
                        List.of(Colors.GRAY.name(), Colors.BLACK.name()), 2)},
                {new Order("Vladimir", "Vladimirovich", "Sochi", "1",
                        "+7-123-456-78-90", "2014-02-28",
                        "RU-SSI-A!", Collections.emptyList(), 8)}
        };
    }

    @Test
    @DisplayName("Создайте тест заказа.")
    @Description("Успешное создание заказа с различными параметрами.")
    public void createOrderTest(){
        Response response = orderClient.sendPostRequestApiV1Orders(order);
        response.then().log().all()
                .assertThat().body("track", Matchers.notNullValue()).and().statusCode(201);
    }

}
