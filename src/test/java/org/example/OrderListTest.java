package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    OrderSteps orderSteps = new OrderSteps();

    private final String ORDERS_LIST_FIELD = "orders";

    @Test
    @DisplayName("Receiving the list of orders")
    @Description("Getting the list of orders by sending get request")
    public void receiveAllOrders(){
        Response response = orderSteps.receiveAll();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(ORDERS_LIST_FIELD, notNullValue());
    }
}