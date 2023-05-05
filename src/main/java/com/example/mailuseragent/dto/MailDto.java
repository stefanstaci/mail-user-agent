package com.example.mailuseragent.dto;

import lombok.Data;

@Data
public class MailDto {
    String toEmail;
    String subject;
    String message;
}
