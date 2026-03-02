package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import com.geese.as.Commands.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {
    @Autowired
    private CommandRegistry registry;

    public HelpCommand() {
        setCommandName("help");
    }

    @Override
    protected String execute() {
        return registry.getHelpText();
    }

}
