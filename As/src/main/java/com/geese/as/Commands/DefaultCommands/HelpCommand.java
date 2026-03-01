package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {

    public HelpCommand() {
        setCommandName("help");
    }

    @Override
    protected String execute() {
        return "List of available commands\n" +
                "about\t\t- A small about section for this project\n" +
                "creator\t\t- A link thowards my github profile\n" +
                "clear\t\t- Clearing the terminal";
    }

    @Override
    protected void validate() {
        super.validate();

        if(getCommandInput() == null || getCommandInput().isEmpty()) {
            throw new IllegalArgumentException("Invalid command input");
        }
    }
}
