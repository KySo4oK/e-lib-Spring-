package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.service.UserService;
import extclasses.final_project_spring.util.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public void getNewUser(@RequestBody UserDTO userDTO) {
        log.info("{}", userDTO);
        Validator.checkRegistration(userDTO);
        userService.setNewUser(userDTO);
    }
}