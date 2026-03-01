package com.geese.as.Validation;

import com.geese.as.Users.CreatedUsers.ASUser;
import com.geese.as.Users.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserValidation {
    @Getter
    private ArrayList<User> whiteList = new ArrayList<User>();

    private final ASUser asUser;
    @Autowired
    public UserValidation(ASUser asUser) {
        this.asUser = asUser;
        whiteList.add(asUser);
    }

    public Optional<User> getUserByName(String username) {
        return whiteList.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public boolean isPasswordCorrect(String username, String password) {
        return whiteList.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .map(User::getPassword)
                .anyMatch(password::equals);
    }

}
