package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private final CourierCreationSteps courierCreationSteps = new CourierCreationSteps();
    private final CourierLoginSteps courierLoginSteps = new CourierLoginSteps();

    private static final String MESSAGE_FIELD = "message";
    private static final String ID_FIELD = "id";
    private static final String NOT_ENOUGH_DATA_MESSAGE = "Недостаточно данных для входа";
    private static final String SERVICE_UNAVAILABLE_MESSAGE = "Service unavailable";
    private static final String ACCOUNT_NOT_FOUND = "Учетная запись не найдена";


    @Test
    @DisplayName("Courier logging in with its login and password")
    @Description("Logging in with actual login and password is possible")
    public void courierLoginPossibilityTest(){
        courierCreationSteps.sendPostWithAllParameters();
        Response response = courierLoginSteps.sendPostWithAllParameters();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(ID_FIELD, notNullValue());
    }

    @Test
    @DisplayName("Trying to log in without sending login in post request")
    @Description("Proving the impossibility to log in without login")
    public void courierLoggingInIsImpossibleWithoutLoginTest(){
        Response response = courierLoginSteps.sendPostWithoutLogin();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body(MESSAGE_FIELD, equalTo(NOT_ENOUGH_DATA_MESSAGE));
    }

    @Test
    @DisplayName("Trying to log in without sending password in post request")
    @Description("Proving the impossibility to log in without password")
    public void courierLoggingInIsImpossibleWithoutPasswordTest(){
        Response response = courierLoginSteps.sendPostWithoutPassword();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_GATEWAY_TIMEOUT)
                .time(lessThan(2000L), TimeUnit.SECONDS)
                .and()
                .body(equalToObject(SERVICE_UNAVAILABLE_MESSAGE));
    }


    @Test
    @DisplayName("Trying to log in with wrong login")
    @Description("Proving the impossibility to log in with wrong login")
    public void courierLoggingWithWrongLoginTest() {
        Response response = courierLoginSteps.sendPostWithWrongLogin();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body(MESSAGE_FIELD, equalTo(ACCOUNT_NOT_FOUND));
    }

    @Test
    @DisplayName("Trying to log in with wrong password")
    @Description("Proving the impossibility to log in with wrong password")
    public void courierLoggingWithWrongPasswordTest() {
        Response response = courierLoginSteps.sendPostWithWrongPassword();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body(MESSAGE_FIELD, equalTo(ACCOUNT_NOT_FOUND));
    }
}