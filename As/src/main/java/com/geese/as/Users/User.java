package com.geese.as.Users;

import com.geese.as.Commands.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class User {
    private String username;
    private String password;

}
