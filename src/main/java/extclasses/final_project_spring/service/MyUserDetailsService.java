package extclasses.final_project_spring.service;


import extclasses.final_project_spring.entity.MyUserDetails;
import extclasses.final_project_spring.entity.User;
import extclasses.final_project_spring.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Component
@Service
public class MyUserDetailsService implements UserDetailsService {

    final
    UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostConstruct
//    public void newUser() {
//        User user = new User();
//        user.setRoles("ROLE_USER");
//        user.setPassword("user");
//        user.setUsername("user");
//        user.setActive(true);
//        userRepository.save(user);
//        user = new User();
//        user.setRoles("ROLE_ADMIN");
//        user.setPassword("admin");
//        user.setUsername("admin");
//        user.setActive(true);
//        userRepository.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        log.info("{}", user.toString());

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return user.map(MyUserDetails::new).get();
    }
}
