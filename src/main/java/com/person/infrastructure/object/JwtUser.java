package com.person.infrastructure.object;

import lombok.Data;

@Data
public class JwtUser {
    private Long id;
    private String role = "USER";

    public JwtUser(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public JwtUser(long id) {
        this.id = id;
        this.role = "USER";
    }
}
