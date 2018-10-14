package de.codecentric.cdc.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/user/{userName}")
    public User user(@PathVariable String userName) throws IOException {
        return userRepository.findUserByUserName(userName);
    }
}
