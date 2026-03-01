package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import org.springframework.stereotype.Component;

@Component
public class AboutCommand extends Command {
    public AboutCommand() {
        setCommandName("about");
    }


    @Override
    protected String execute() {
        return "A small project made to explore the creation of a terminal and the usage of sound and animation in coding";
    }

    @Override
    protected void validate(){
        super.validate();

        if(getCommandInput() == null || getCommandInput().isBlank()) {
            throw new IllegalArgumentException("Input required");
        }
    }
}
