package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierDeleteTest {

    private final CourierMethods courierMethods = new CourierMethods();
    private final CourierCreationSteps courierCreationSteps = new CourierCreationSteps();
    private final CourierLoginSteps courierLoginSteps = new CourierLoginSteps();
    private static final String MESSAGE_FIELD = "message";
    private static final String OK_FIELD = "ok";
    private static final String MISSING_COURIER_ID_MESSAGE = "Курьера с таким id нет.";
    private static final String NOT_FOUND_MESSAGE = "Not Found.";
    private Integer id = 0;


    @Test
    @DisplayName("Courier deletion sending its id in parameters")
    @Description("Checking possibility to delete a courier sending actual id")
    public void deleteCourier() {
        Courier courier = courierMethods.getCourier();
        courierCreationSteps.sendPostWithAllParameters();
        id = courierLoginSteps.sendPostWithAllParameters().body().path("id");
        Response response = courierCreationSteps.deleteCourier(id);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(OK_FIELD, equalTo(true));
    }

    @Test
    @DisplayName("Sending delete request for courier deletion without id")
    @Description("Impossible to delete a courier without sending id")
    public void deleteCourierWithoutId() {
        Courier courier = courierMethods.getCourier();
        courierCreationSteps.sendPostWithAllParameters();
        id = courierLoginSteps.sendPostWithAllParameters().body().path("id");
        Response response = courierCreationSteps.deleteCourierWithoutSendingId();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body(MESSAGE_FIELD, equalTo(NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Sending delete request for courier deletion using nonexisting id")
    @Description("Impossible to delete a courier while using nonexisting id")
    public void deleteNonExistentCourier() {
        id = -7;
        Response response = courierCreationSteps.deleteCourier(id);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body(MESSAGE_FIELD, equalTo(MISSING_COURIER_ID_MESSAGE));
    }
}