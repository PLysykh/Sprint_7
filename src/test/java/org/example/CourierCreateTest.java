package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest extends BaseTest{
    private final CourierCreationSteps courierCreationSteps = new CourierCreationSteps();
    private static final String OK_FIELD = "ok";
    private static final String MESSAGE_FIELD = "message";
    private static final String ALREADY_EXISTING_LOGIN_MESSAGE = "Этот логин уже используется. Попробуйте другой.";
    private static final String NOT_ENOUGH_DATA_MESSAGE = "Недостаточно данных для создания учетной записи";


    @Test
    @DisplayName("Create courier with all three parameters")
    @Description("Possibility of creation a courier using login, password and firstName")
    public void createCourierTest(){
        Response response = courierCreationSteps.sendPostWithAllParameters();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat()
                .body(OK_FIELD, equalTo(true));
    }

    @Test
    @DisplayName("Create courier with already existing login")
    @Description("Absence of possibility to create a courier with already existing login")
    public void createCourierSameLoginTest(){
        courierCreationSteps.sendPostWithAllParameters();
        Response response = courierCreationSteps.sendPostWithAllParameters();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(ALREADY_EXISTING_LOGIN_MESSAGE));
    }

    @Test
    @DisplayName("Create courier without login")
    @Description("Absence of possibility to create a courier without login")
    public void courierCreationWithoutLoginTest(){
        Response response = courierCreationSteps.sendPostWithoutLogin();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(NOT_ENOUGH_DATA_MESSAGE));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Absence of possibility to create a courier without password")
    public void courierCreationWithoutPasswordTest(){
        Response response = courierCreationSteps.sendPostWithoutPassword();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(NOT_ENOUGH_DATA_MESSAGE));
    }

    @Test
    @DisplayName("Create courier without firstName")
    @Description("Ability to create a courier without firstName")
    public void courierCreationWithoutFirstNameIsPossibleTest(){
        Response response = courierCreationSteps.sendPostWithoutFirstName();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat()
                .body(OK_FIELD, equalTo(true));
    }
}