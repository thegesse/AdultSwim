package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import org.springframework.stereotype.Component;

@Component
public class CreatorCommand extends Command {
    public CreatorCommand() {
        setCommandName("creator");
    }

    @Override
    protected String execute(){
        return "Creator of this project is thegesse who you can find on github here: https://github.com/thegesse";
    }

    @Override
    protected void validate() {
        super.validate();


    }
}
