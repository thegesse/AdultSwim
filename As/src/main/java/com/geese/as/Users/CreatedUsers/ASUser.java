package com.geese.as.Users.CreatedUsers;

import com.geese.as.Users.User;
import org.springframework.stereotype.Component;

@Component
public class ASUser extends User {

    public ASUser() {
        super("Geese", "1234");
    }


}
