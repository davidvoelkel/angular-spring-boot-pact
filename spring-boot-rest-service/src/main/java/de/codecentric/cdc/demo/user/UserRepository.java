package de.codecentric.cdc.demo.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @NotNull
    public User findUserByAlias(String name) {

        // TODO implement real data access logic here ...

        return null;
    }
}
