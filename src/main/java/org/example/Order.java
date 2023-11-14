package org.example;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phoneNumber;
    private String deliveryDate;
    private Integer rentalTime;
    private String[] colour;
    private String comment;

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getRentalTime() {
        return rentalTime;
    }

    public void setRentalTime(Integer rentalTime) {
        this.rentalTime = rentalTime;
    }

    public String[] getColour() {
        return colour;
    }

    public void setColour(String[] colour) {
        this.colour = colour;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}