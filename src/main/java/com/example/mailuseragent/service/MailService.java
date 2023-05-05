package com.example.mailuseragent.service;

import com.example.mailuseragent.dto.FromUserDto;
import org.springframework.stereotype.Service;

import jakarta.mail.*;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subject, String message) {
        var email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setText(message);
        email.setSubject(subject);

        javaMailSender.send(email);
    }


    public List<FromUserDto> getMail(String host, String storeType, String user, String password) throws MessagingException {
        List<FromUserDto> fromUserDto = new ArrayList<>();

            Properties properties = new Properties();
            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");

            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);

        Arrays.stream(emailFolder.getMessages()).skip(Math.max(0, emailFolder.getMessages().length - 3)).forEach(mes -> {
            try {
                fromUserDto.add(new FromUserDto(Arrays.toString(mes.getFrom()), mes.getSubject()));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        emailFolder.close(true);
        store.close();

        return fromUserDto;
    }
}