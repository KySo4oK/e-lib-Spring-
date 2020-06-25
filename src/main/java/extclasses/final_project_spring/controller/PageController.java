package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

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
    public String getRegPage(WebRequest request, Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "reg.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveNewUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
        log.info("{}", userDTO);
        if (result.hasErrors()) {
            return "reg.html";
        }
        userService.setNewUser(userDTO);
        return "redirect:/login";
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
}