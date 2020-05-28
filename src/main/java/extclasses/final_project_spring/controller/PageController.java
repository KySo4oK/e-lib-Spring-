package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.entity.Role;
import extclasses.final_project_spring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
public class PageController {
    final
    UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getMainPage() {
        return "main.html";
    }

    @GetMapping("/reg")
    public String getRegPage() {
        return "reg.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public void getNewUser(@Valid @RequestBody UserDTO userDTO) {
        log.info("{}", userDTO);
        userService.setNewUser(userDTO);
    }

    @GetMapping("/prospectus")
    public String getProspectusPage() {
        return "prospectus.html";
    }

    @GetMapping("/orders")
    public String getOrdersPage() {
        return "orders.html";
    }

    @GetMapping("/user")
    public String getUserPage() {
        return "user.html";
    }

    @GetMapping("/book")
    public String getAddBookPage() {
        return "bookManagePage.html";
    }

    @GetMapping("/authorities")
    public @ResponseBody
    int getAuthorities(Authentication authentication) {//todo remove
        if (authentication == null) return 3;
        log.info("{}", authentication.getAuthorities());
        return authentication.getAuthorities().contains(Role.ADMIN) ? 1 : 2;
    }
}