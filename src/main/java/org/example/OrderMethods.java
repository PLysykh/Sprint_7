package org.example;

public class OrderMethods {
    public Order getOrder(String firstName, String lastName, String address, String metroStation, String phoneNumber,
                          String deliveryDate, Integer rentalTime, String[] colour, String comment) {
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhoneNumber(phoneNumber);
        order.setDeliveryDate(deliveryDate);
        order.setRentalTime(rentalTime);
        order.setColour(colour);
        order.setComment(comment);
        return order;
    }
}