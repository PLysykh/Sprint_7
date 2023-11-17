package org.example;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static constant.Colours.BLACK_COLOUR;
import static constant.Colours.GREY_COLOUR;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final Integer rentalTime;
    private final String[] colour;
    private final String comment;
    private final OrderSteps orderSteps = new OrderSteps();
    private final OrderMethods orderMethods = new OrderMethods();

    private Integer trackId;
    private static final String TRACK_FIELD = "track";

    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phoneNumber,
                           String deliveryDate, Integer rentalTime, String[] colour, String comment){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalTime = rentalTime;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{index} : color{7}")
    public static Object[][] getData(){
        return new Object[][]{
                {"Иван", "Павлов", "Краснознамённая ул., д. 53", "ВДНХ", "+79009002112", "2023-11-15", 3, new String[] {GREY_COLOUR}, "qaTest01"},
                {"Илона", "Павлова", "Петровская ул., д. 70", "Сокол", "+78125673469", "2023-11-15", 4, new String[] {GREY_COLOUR, BLACK_COLOUR}, "qaTest02"},
                {"Ирина", "Давыдова", "Прибалтийская ул., д. 5", "Автозаводская", "+79326664355", "2023-11-15", 1, new String[] {BLACK_COLOUR}, "qaTest03"}
        };
    }

    @Test
    @DisplayName("Making an order")
    @Description("Parameterized test for making an order")
    public void makingOrder(){
        Order order = orderMethods.getOrder(firstName, lastName, address, metroStation, phoneNumber,
                deliveryDate, rentalTime, colour, comment);
        Response response = orderSteps.createOrder(order);
        trackId = response
                .body()
                .path(TRACK_FIELD);
        response
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat()
                .body(TRACK_FIELD, notNullValue());
    }
}