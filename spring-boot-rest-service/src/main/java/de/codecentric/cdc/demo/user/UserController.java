package de.codecentric.cdc.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired UserRepository userRepository;

    @RequestMapping("/user/{alias}")
    public User user(@PathVariable String alias) {
        return userRepository.findUserByAlias(alias);
    }
}
