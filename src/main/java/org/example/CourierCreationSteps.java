package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierCreationSteps extends RestData {

    private final CourierMethods courierMethods = new CourierMethods();
    private final String POST_COURIER_CREATION_ENDPOINT = "/api/v1/courier";
    private final String DELETE_COURIER_DELETION_ENDPOINT = "/api/v1/courier";

    @Step("Send POST request to /api/v1/courier with all parameters")
    public Response sendPostWithAllParameters(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourier())
                .when()
                .post(POST_COURIER_CREATION_ENDPOINT);
    }

    @Step("Compare response with three parameters")
    public void compareResponseResult(Response response, boolean OK_FIELD){
        response
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Step("Send POST request to /api/v1/courier without login")
    public Response sendPostWithoutLogin(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierWithoutLogin())
                .when()
                .post(POST_COURIER_CREATION_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier without login")
    public Response sendPostWithoutPassword(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierWithoutPassword())
                .when()
                .post(POST_COURIER_CREATION_ENDPOINT);
    }

    @Step("Send POST request to /api/v1/courier without firstName")
    public Response sendPostWithoutFirstName(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourierWithoutFirstName())
                .when()
                .post(POST_COURIER_CREATION_ENDPOINT);
    }

    @Step("Compare response without firstName")
    public void compareResponseResultWithoutFirstName(Response response, boolean OK_ANSWER){
        response
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Step("Courier deletion")
    public Response deleteCourier(Integer courierId) {
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_COURIER_DELETION_ENDPOINT + "/" + courierId);
    }

    @Step("Courier deletion without sending id")
    public Response deleteCourierWithoutSendingId() {
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(courierMethods.getCourier())
                .when()
                .delete(DELETE_COURIER_DELETION_ENDPOINT + "/");
    }
}