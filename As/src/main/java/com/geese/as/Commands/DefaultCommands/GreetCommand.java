package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import org.springframework.stereotype.Component;

@Component
public class GreetCommand extends Command {
    public GreetCommand() {
        setCommandName("greet");
    }

    @Override
    protected String execute() {
        return "Welcome to the terminal, this is a small project, see the help command for a list of available commands! Also there are hiddden commands here Have fun.";
    }

    @Override
    protected void validate() {
        super.validate();
    }
}
