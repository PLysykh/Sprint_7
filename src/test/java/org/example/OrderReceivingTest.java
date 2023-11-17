package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class OrderReceivingTest {

    private final String firstName = "Елена";
    private final String lastName = "Старобогатова";
    private final String address = "Камышинский проезд, дом 14";
    private final String metroStation = "Автово";
    private final String phoneNumber = "+79212346576";
    private final String deliveryDate = "2023-11-15";
    private final Integer rentalTime = 6;
    private final String[] colour = new String[] {"black"};
    private final String comment = "Первый комментарий заказчика";

    Order order;

    private final OrderSteps orderSteps = new OrderSteps();
    private final OrderMethods orderMethods = new OrderMethods();

    private static final String MESSAGE_FIELD = "message";
    private static final String ORDER_FIELD = "order";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Заказ не найден";
    private static final String ORDER_ID_MISSING_MESSAGE = "Заказа с таким id не существует";
    private static final String ALREADY_EXISTING_ORDER_MESSAGE = "Этот заказ уже в работе";
    private static final String NOT_ENOUGH_DATA_FOR_SEARCH = "Недостаточно данных для поиска";
    private Integer trackId;

    private void createOrder() {
        order = orderMethods.getOrder(firstName, lastName, address, metroStation,
                phoneNumber, deliveryDate, rentalTime, colour, comment);
    }

    @Test
    @DisplayName("Accepting existing order")
    @Description("Acceptance of order from database")
    public void receivingAnOrder(){
        createOrder();
        trackId = orderSteps.createOrder(order).body().path("track");
        Response response = orderSteps.receiveAnOrder(trackId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(ORDER_FIELD, notNullValue());
    }

    @Test
    @DisplayName("Sending get request without trackId")
    @Description("Proving acceptance of order is impossible without sending trackId")
    public void receivingAnOrderWithoutNumber(){
        Response response = orderSteps.receiveAnOrderWithoutNumber();
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(NOT_ENOUGH_DATA_FOR_SEARCH));
    }

    @Test
    @DisplayName("Sending get request without a nonexisting trackId")
    @Description("Proving acceptance of order is impossible while sending nonexisting trackId")
    public void receivingANonExistingOrder(){
        trackId = 000000;
        Response response = orderSteps.receiveAnOrder(trackId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(ORDER_NOT_FOUND_MESSAGE));
    }
}