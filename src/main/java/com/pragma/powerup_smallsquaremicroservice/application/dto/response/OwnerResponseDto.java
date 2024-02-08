package com.pragma.powerup_smallsquaremicroservice.application.dto.response;

import com.pragma.powerup_smallsquaremicroservice.domain.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class OwnerResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String docNumber;
    private String phone;
    private LocalDate birthdate;
    private String mail;
    private String password;
    private Role role;
}
