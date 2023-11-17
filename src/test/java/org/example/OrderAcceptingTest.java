package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class OrderAcceptingTest {

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
    private final CourierCreationSteps courierSteps = new CourierCreationSteps();
    private final CourierLoginSteps courierLoginSteps = new CourierLoginSteps();

    private static final String MESSAGE_FIELD = "message";
    private static final String OK_FIELD = "ok";
    private static final String DATA_MISSING_MESSAGE = "Недостаточно данных для поиска";
    private static final String NOT_FOUND_MESSAGE = "Not Found.";
    private static final String ORDER_ID_MISSING_MESSAGE = "Заказа с таким id не существует";
    private static final String COURIER_ID_MISSING_MESSAGE = "Курьера с таким id не существует";

    private Integer trackId;
    private Integer courierId;
    private int orderId;

    private void createOrder() {
        order = orderMethods.getOrder(firstName, lastName, address, metroStation,
                phoneNumber, deliveryDate, rentalTime, colour, comment);
    }

    @Test
    @DisplayName("Existing order acceptance")
    @Description("Possibility to accept an order sending trackId and courierId in parameters")
    public void acceptingAnOrder(){
        createOrder();
        trackId = orderSteps.createOrder(order).body().path("track");
        orderId = orderSteps.getTrack(String.valueOf(trackId)).extract().path("order.id");

        courierSteps.sendPostWithAllParameters();
        courierLoginSteps.sendPostWithAllParameters();
        courierId = courierLoginSteps.sendPostWithAllParameters().body().path("id");
        Response response = orderSteps.acceptAnOrder(courierId, orderId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(OK_FIELD, equalTo(true));
    }

    @Test
    @DisplayName("Sending order acceptance request without courierId")
    @Description("Proving impossibility to accept an order without courierId")
    public void acceptingAnOrderWithoutCourierId(){
        createOrder();
        trackId = orderSteps.createOrder(order).body().path("track");
        Response response = orderSteps.acceptAnOrderWithoutCourierId(trackId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(DATA_MISSING_MESSAGE));
    }

    @Test
    @DisplayName("Sending acceptance request with wrong courierId")
    @Description("Proving impossibility to accept an order with wrong courierId")
    public void acceptingAnOrderWithWrongCourierId(){
        createOrder();
        trackId = orderSteps.createOrder(order).body().path("track");
        orderId = orderSteps.getTrack(String.valueOf(trackId)).extract().path("order.id");

        courierId = -2;
        Response response = orderSteps.acceptAnOrder(courierId, orderId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(COURIER_ID_MISSING_MESSAGE));
    }

    @Test
    @DisplayName("Sending acceptance request without trackId")
    @Description("Proving impossibility to accept an order without trackId")
    public void acceptingAnOrderWithoutOrderId(){
        courierSteps.sendPostWithAllParameters();
        courierLoginSteps.sendPostWithAllParameters();
        courierId = courierLoginSteps.sendPostWithAllParameters().body().path("id");

        Response response = orderSteps.acceptAnOrderWithoutOrderId(courierId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(NOT_FOUND_MESSAGE));
    }

    @Test
    @DisplayName("Sending acceptance request with wrong trackId")
    @Description("Proving impossibility to accept an order with wrong trackId")
    public void acceptingAnOrderWithWrongOrderId(){
        courierSteps.sendPostWithAllParameters();
        courierLoginSteps.sendPostWithAllParameters();
        courierId = courierLoginSteps.sendPostWithAllParameters().body().path("id");
        orderId = -5;
        Response response = orderSteps.acceptAnOrder(courierId, orderId);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(MESSAGE_FIELD, equalTo(ORDER_ID_MISSING_MESSAGE));
    }
}