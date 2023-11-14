package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierLoginSteps extends RestData {

    private final CourierMethods courierMethods = new CourierMethods();
    private final String POST_COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";

    @Step("Send POST request to /api/v1/courier/login with all two parameters")
    public Response sendPostWithAllParameters(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierLogin())
                .when()
                .post(POST_COURIER_LOGIN_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier/login without login")
    public Response sendPostWithoutLogin(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierLoginWithoutLogin())
                .when()
                .post(POST_COURIER_LOGIN_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier/login without password")
    public Response sendPostWithoutPassword(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierLoginWithoutPassword())
                .when()
                .post(POST_COURIER_LOGIN_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier/login with wrong login")
    public Response sendPostWithWrongLogin(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierLoginWithWrongLogin())
                .when()
                .post(POST_COURIER_LOGIN_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier/login with wrong password")
    public Response sendPostWithWrongPassword(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierLoginWithWrongPassword())
                .when()
                .post(POST_COURIER_LOGIN_ENDPOINT);
    }
}