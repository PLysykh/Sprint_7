package org.example;

import org.junit.After;

public class BaseTest {
    private final CourierLoginSteps courierLoginSteps = new CourierLoginSteps();
    private final CourierCreationSteps courierCreationSteps = new CourierCreationSteps();

    @After
    public void deleteCourier() {
        if (courierLoginSteps.sendPostWithAllParameters().body().path("id") != null) {
            int courierId = courierLoginSteps.sendPostWithAllParameters().body().path("id");
            courierCreationSteps.deleteCourier(courierId);
        }
    }
}