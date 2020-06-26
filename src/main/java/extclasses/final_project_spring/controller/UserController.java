package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Log4j2
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reg")
    public String getRegPage(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "reg";
    }

    @PostMapping("/reg")
    public String saveNewUser(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {
        log.info("{}", userDTO);
        if (result.hasErrors()) {
            return "reg";
        }
        userService.saveNewUser(userDTO);
        return "redirect:/login";
    }
}