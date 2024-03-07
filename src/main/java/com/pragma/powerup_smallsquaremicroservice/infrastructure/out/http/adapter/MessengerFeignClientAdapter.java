package com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.adapter;

import com.pragma.powerup_smallsquaremicroservice.application.dto.request.MessageOrderReadyRequestDto;
import com.pragma.powerup_smallsquaremicroservice.domain.clientapi.IMessengerMSClientPort;
import com.pragma.powerup_smallsquaremicroservice.infrastructure.out.http.feignclient.IMessengerFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessengerFeignClientAdapter implements IMessengerMSClientPort {
    
    private final IMessengerFeignClient messengerFeignClient;
    
    @Override
    public boolean sendOrderReadyMessage(String clientPhone, String restaurantPhone, String restaurantName,
                                         String orderPIN) {
        return messengerFeignClient.sendOrderReadyMessage(new MessageOrderReadyRequestDto(clientPhone, restaurantPhone,
                                                                                          restaurantName, orderPIN));
    }
}
