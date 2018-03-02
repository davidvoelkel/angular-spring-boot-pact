package de.codecentric.cdc.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user")
    public User user() {
        String name = userService.userName();

        User user = new User();
        user.setName(name);
        return user;
    }

}
