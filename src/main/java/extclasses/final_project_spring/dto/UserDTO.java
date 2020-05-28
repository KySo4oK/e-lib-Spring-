package extclasses.final_project_spring.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String password;
}
