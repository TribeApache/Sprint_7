package api.client;

import api.model.Order;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDER_ENDPOINT = "/api/v1/orders";

    @Step("Send POST request to /api/v1/orders")
    public Response sendPostRequestApiV1Orders(Order order) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_ENDPOINT);
    }

}
