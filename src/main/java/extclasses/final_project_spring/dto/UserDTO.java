package extclasses.final_project_spring.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Pattern;

@Data
@ToString
public class UserDTO {
    @Pattern(regexp = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z0-9._]+(?<![_.])$",
            message = "{valid.user.username}")
    private String username;
    @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", message = "{valid.user.email}")
    private String email;
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "{valid.user.phone}")
    private String phone;
    @Pattern(regexp = "^.{8,}$", message = "{valid.user.password}")
    private String password;
}