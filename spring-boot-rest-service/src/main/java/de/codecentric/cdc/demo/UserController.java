package de.codecentric.cdc.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
//    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/user")
    public User user() {
        User user = new User();
        user.setName("David Server");
        return user;
    }


}
