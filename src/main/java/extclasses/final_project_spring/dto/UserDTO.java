package extclasses.final_project_spring.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
public class UserDTO {
    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^(?=.{5,20}$)(?![_.])(?!.*[_.]{2})[а-щА-ЩЬьЮюЯяЇїІіЄєҐґa-zA-Z0-9._]+(?<![_.])$", message = "bad username")
    private String username;
    @NotNull
    @NotEmpty
    @Email
    @NotBlank
    private String email;
    @NotNull
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "incorrect phone")
    private String phone;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 30, message = "incorrect password")
    private String password;
}
