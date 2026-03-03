package com.geese.as.Commands.CreatedCommands;

import com.geese.as.Commands.Command;
import com.geese.as.JavaFx.GFTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GravitFallsTerminalCommand extends Command {

    @Autowired
    private GFTerminalService gfTerminalService;

    public GravitFallsTerminalCommand() {
        setCommandName("sixer");
        System.out.println("[GravitFallsTerminalCommand] Created");
    }

    @Override
    protected String execute() {
        System.out.println("[GravitFallsTerminalCommand] Opening GF terminal...");

        gfTerminalService.openWindow();
        return "GF Terminal opened! Type 'help' for commands.";
    }
}