package com.pragma.powerup_smallsquaremicroservice.domain.clientapi;

public interface IMessengerMSClientPort {
    boolean sendOrderReadyMessage(String clientPhone, String restaurantPhone, String restaurantName, String orderPIN);
}
