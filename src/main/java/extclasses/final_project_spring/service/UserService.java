package extclasses.final_project_spring.service;

import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.entity.User;
import extclasses.final_project_spring.exception.CustomException;
import extclasses.final_project_spring.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void saveNewUser(UserDTO userDTO) {
        log.info("save new user {}", userDTO.toString());
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("user.already.exist");
        }
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("user.not.found"));
    }
}