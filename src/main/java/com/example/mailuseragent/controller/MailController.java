package com.example.mailuseragent.controller;

import com.example.mailuseragent.dto.FromUserDto;
import com.example.mailuseragent.dto.MailDto;
import com.example.mailuseragent.dto.UserDto;
import com.example.mailuseragent.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @PostMapping("/send")
    public void sendMail(@RequestBody MailDto mailDto) {
        mailService.sendMail(mailDto.getToEmail(), mailDto.getSubject(), mailDto.getMessage());
    }

    @GetMapping("/")
    public ResponseEntity<List<FromUserDto>> getMail() throws MessagingException {
        UserDto userDto = new UserDto();
        userDto.setHost("pop-mail.outlook.com");
        userDto.setMailStoreType("pop3");
        userDto.setUsername("your email");
        userDto.setPassword("your email password");

        return ResponseEntity.ok().body(mailService.getMail(userDto.getHost(), userDto.getMailStoreType(), userDto.getUsername(), userDto.getPassword()));

    }
}
