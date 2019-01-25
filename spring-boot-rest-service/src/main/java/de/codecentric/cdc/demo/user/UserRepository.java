package de.codecentric.cdc.demo.user;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public User findUserByUserName(String userName) {

        User user = new User();
        user.withName("The Real Service's David");
        user.withEmail("the.real.services.david@gmail.com");
        return user;
    }
}
