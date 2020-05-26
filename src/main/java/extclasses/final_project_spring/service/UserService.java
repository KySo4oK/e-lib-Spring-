package extclasses.final_project_spring.service;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.entity.User;
import extclasses.final_project_spring.exception.UserAlreadyExistException;
import extclasses.final_project_spring.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostConstruct
//    public void newUser() {
//        User user = new User();
//        user.setRoles("ROLE_ADMIN");
//        user.setPassword("admin");
//        user.setUsername("admin");
//        user.setActive(true);
//        user.setEmail("admin@ukr.net");
//        user.setPhone("+380966190691");
//        userRepository.save(user);
//    }

    @Transactional(rollbackFor = UserAlreadyExistException.class)
    public void setNewUser(UserDTO userDTO) {
        log.info("save new user {}", userDTO.toString());
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("user - " + userDTO.getUsername() + " already exist");
        }
        User user = new User(userDTO);
        userRepository.save(user);
    }
}
