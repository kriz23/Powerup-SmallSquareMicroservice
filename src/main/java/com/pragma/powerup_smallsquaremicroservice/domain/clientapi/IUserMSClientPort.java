package com.pragma.powerup_smallsquaremicroservice.domain.clientapi;

import com.pragma.powerup_smallsquaremicroservice.domain.model.User;

public interface IUserMSClientPort {
    User getOwnerById(String authHeader, Long id);
    User getUserByMail(String authHeader, String mail);
}
