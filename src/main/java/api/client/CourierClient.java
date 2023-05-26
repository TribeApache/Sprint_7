package api.client;

import api.model.Courier;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String COURIER_ENDPOINT = "/api/v1/courier";

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendPostRequestApiV1CourierLogin(Courier courier) {
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_ENDPOINT + "/login");
    }

    @Step("Send POST request to /api/v1/courier")
    public Response sendPostRequestApiV1Courier(Courier courier) {
        return given().log().all()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER_ENDPOINT);
    }

    @Step("Send DELETE request to /api/v1/courier/{id}")
    public Response deleteCourier(Courier courier) {
        String deleteEndpoint = COURIER_ENDPOINT + "/" + courier.getId();
        return given().log().all()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .when()
                .delete(deleteEndpoint);
    }
}