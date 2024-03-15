package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class OrderTraceMSResponseDto {
    private String id;
    private Long idOrder;
    private Long idClient;
    private String clientMail;
    private String lastState;
    private String currentState;
    private Long idEmployee;
    private String employeeMail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
