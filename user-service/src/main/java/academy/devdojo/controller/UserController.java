package academy.devdojo.controller;

import academy.devdojo.mapper.UserMapper;
import academy.devdojo.response.UserGetResponse;
import academy.devdojo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<List<UserGetResponse>> listAllUsers(@RequestParam(required = false) String firstName) {
        log.debug("Request received to list all users, param firstName  '{}'", firstName);

        var users = service.findAll(firstName);
        var usersGetResponse = mapper.toUserGetResponse(users);

        return ResponseEntity.ok().body(usersGetResponse);
    }
}