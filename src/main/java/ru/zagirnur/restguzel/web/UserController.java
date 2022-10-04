package ru.zagirnur.restguzel.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.zagirnur.restguzel.model.User;
import ru.zagirnur.restguzel.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class UserController {

    String apiKey = "abcabc";

    private final UserService userService;

    @PostMapping("/user")
    @Operation(summary = "Создание пользователя")
    public User createUser(@RequestBody User user, @RequestParam("apiKey") String apiKey) {
        validateApiKey(apiKey);

        return userService.createUserIfNotExists(user);
    }

    @GetMapping("/user")
    @Operation(summary = "Получение всех пользователей")
    public Iterable<User> getAllUsers(HttpServletRequest request) {
        String key = request.getHeader("apiKey");
        System.out.println(key);
        validateApiKey(key);

        return userService.getAllUsers();
    }

    @GetMapping("/user/{login}")
    @Operation(summary = "Получение пользователя по логину")
    public User getUserByLogin(String login, @RequestParam("apiKey") String apiKey) {
        validateApiKey(apiKey);
        return userService.getUserByLoginOrThrow(login);
    }

    @PutMapping("/user/{login}")
    @Operation(summary = "Обновление пользователя по логину")
    public User updateUser(String login, @RequestBody User user, @RequestParam("apiKey") String apiKey) {
        validateApiKey(apiKey);
        if (!login.equals(user.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login in path must be equal to login in body");
        }
        return userService.updateUserIfExists(user);
    }

    private void validateApiKey(String apiKey) {
        if (!Objects.equals(this.apiKey, apiKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid api key");
        }
    }
}
