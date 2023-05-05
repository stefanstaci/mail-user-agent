package com.example.mailuseragent.dto;

import lombok.Data;

@Data
public class UserDto {
    String host;
    String mailStoreType;
    String username;
    String password;
}
