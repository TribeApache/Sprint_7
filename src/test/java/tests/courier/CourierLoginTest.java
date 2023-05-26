package tests.courier;

import api.client.CourierClient;
import api.model.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CourierLoginTest {
    private Courier createdCourier;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
        if (createdCourier != null) {
            // Удаление созданного курьера
            CourierClient clientStep = new CourierClient();
            clientStep.deleteCourier(createdCourier);
        }
    }

    @Test
    @DisplayName("Курьер входит в систему.")
    @Description("Проверьте код состояния, когда курьер входит в систему (успешный запрос).")
    public void authorizationTest() {
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("1234");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("id", Matchers.notNullValue()).and().statusCode(HttpStatus.SC_OK);

        // Сохранение созданного курьера
        createdCourier = courier;
    }

    @Test
    @DisplayName("Курьер входит в систему без авторизации.")
    @Description("Проверьте код состояния, когда курьер входит в систему без логина (плохой запрос).")
    public void authorizationWithoutLoginTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setPassword("1234");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Недостаточно данных для входа")).
                and().statusCode(HttpStatus.SC_BAD_REQUEST);

    }


     //Тест падает по причине дефекта в сервисе Авторизации

    @Test
    @DisplayName("Курьер входит в систему без пароля.")
    @Description("Проверьте код состояния, когда курьер входит в систему без пароля (плохой запрос).")
    public void authorizationWithoutPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST).and().body("message", Matchers.is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Курьер входит в систему с неправильным паролем.")
    @Description("Проверьте код состояния, когда курьер входит в систему с неправильным паролем.")
    public void authorizationWithWrongPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453");
        courier.setPassword("1234000");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Курьер входит в систему с неправильным логином.")
    @Description("Проверьте код состояния, когда курьер входит в систему с неправильным логином.")
    public void authorizationWithWrongLoginTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("ninja1453NarutoILoveYou");
        courier.setPassword("1234");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(HttpStatus.SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Курьер входит в систему с неправильным логином и паролем.")
    @Description("Проверьте код состояния, когда курьер входит в систему с неправильным логином и паролем.")
    public void authorizationWithWrongLoginAndPasswordTest(){
        CourierClient clientStep = new CourierClient();
        Courier courier = new Courier();
        courier.setLogin("TakogoLoginaNeSushestvuet");
        courier.setPassword("TakogoParolaNet");
        Response response = clientStep.sendPostRequestApiV1CourierLogin(courier);
        response.then().log().all()
                .assertThat().body("message", Matchers.is("Учетная запись не найдена")).and().statusCode(HttpStatus.SC_NOT_FOUND);
    }


}
