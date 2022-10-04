package ru.zagirnur.restguzel.model;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;


@Data
public class User {
    private String login;
    private String email;
    private String password;
}
