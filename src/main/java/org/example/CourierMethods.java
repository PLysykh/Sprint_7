package org.example;

public class CourierMethods {

    Courier courier = new Courier();

    private final String login = "oppenheimer";
    private final String password = "QAtest123";
    private final String firstName = "Robert";


    public Courier getCourier() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    public Courier getCourierWithoutLogin(){
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }
    public Courier getCourierWithoutPassword(){
        courier.setLogin(login);
        courier.setFirstName(firstName);
        return courier;
    }

    public Courier getCourierWithoutFirstName(){
        courier.setLogin(login);
        courier.setPassword(password);
        return courier;
    }

    public Courier getCourierLogin() {
        courier.setLogin(login);
        courier.setPassword(password);
        return courier;
    }

    public Courier getCourierLoginWithoutLogin() {
        courier.setPassword(password);
        return courier;
    }

    public Courier getCourierLoginWithoutPassword() {
        courier.setLogin(login);
        return courier;
    }

    public Courier getCourierLoginWithWrongLogin() {
        String wrongLogin = "heimer";
        courier.setLogin(wrongLogin);
        courier.setPassword(password);
        return courier;
    }

    public Courier getCourierLoginWithWrongPassword() {
        courier.setLogin(login);
        String wrongPassword = "QA";
        courier.setPassword(wrongPassword);
        return courier;
    }
}