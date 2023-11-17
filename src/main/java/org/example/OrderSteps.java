package org.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps extends RestData {
    private final static String ORDER_CREATION_ENDPOINT = "/api/v1/orders";
    private final static String ORDER_ACCEPTANCE_ENDPOINT = "/api/v1/orders/accept";
    private final static String ORDER_RECEIVING_ENDPOINT = "/api/v1/orders/track";
    private final String GET_LIST_OF_ORDERS_ENDPOINT = "/api/v1/orders";

    @Step("Create an order")
    public Response createOrder(Order order) {
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDER_CREATION_ENDPOINT);
    }

    @Step("Accept an order")
    public Response acceptAnOrder(Integer courierId, Integer orderId){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_ACCEPTANCE_ENDPOINT + "/" + orderId);
    }

    @Step("Accept an order without courierId")
    public Response acceptAnOrderWithoutCourierId(Integer trackId){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .put(ORDER_ACCEPTANCE_ENDPOINT + "/" + trackId);
    }

    @Step("Accept an order without trackId")
    public Response acceptAnOrderWithoutOrderId(Integer courierId){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .when()
                .put(ORDER_ACCEPTANCE_ENDPOINT);
    }

    @Step("Receive an order")
    public Response receiveAnOrder(Integer trackId){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .queryParam("t", trackId)
                .when()
                .get(ORDER_RECEIVING_ENDPOINT);
    }

    @Step("Receive an order without number")
    public Response receiveAnOrderWithoutNumber(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .get(ORDER_RECEIVING_ENDPOINT);
    }

    @Step("Send get request to get the track")
    public ValidatableResponse getTrack(String courierId) {
        return given()
                .spec(requestSpecification())
                .queryParam("t", courierId)
                .when()
                .get(ORDER_RECEIVING_ENDPOINT)
                .then();
    }

    @Step("Retrieve the full list of orders")
    public Response receiveAll(){
        return given()
                .spec(requestSpecification())
                .header("Content-type", "application/json")
                .when()
                .get(GET_LIST_OF_ORDERS_ENDPOINT);
    }
}