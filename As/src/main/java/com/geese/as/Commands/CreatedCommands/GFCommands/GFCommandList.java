package com.geese.as.Commands.CreatedCommands.GFCommands;

import com.geese.as.Commands.Command;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GFCommandList {
    private final List<GFCommandTemplate> commands = new ArrayList<>();


    public GFCommandList() {
        commands.add(new GFCommandTemplate("Gravity-Falls", "A small town in Roadkill County Oregon, there isn't much to do here, but rumor has it that strange things happen here sometimes. Also if you have the time (and money) comme visit the mystery shack the owner has given me a large sum of encouragement to say that"));
        commands.add(new GFCommandTemplate("Mystery-Shack", "Truly a staple of this community it is the biggest tourist attraction around"));
        commands.add(new GFCommandTemplate("Journals", "A collection of 2...no wait 3 I believe journals written about the folklore of this town. I personally believe its just fiction but who am I to judge?"));
        commands.add(new GFCommandTemplate("Bill", "V hbkwhuhrxv hqwlwb wkhw vwuxfn d ghdo zlwk d ihz ri xv. Kh lv kljkob gdqjhurxv gr qrw eholhyh klv...Zdlw zkb fdq'w L zulwh, ru wdon surshuob dqbpru-"));
    }

    public List<GFCommandTemplate> getCommands() {
        return commands;
    }

    public GFCommandTemplate getCommandByName(String commandName) {
        for (GFCommandTemplate command : commands) {
            if (command.getCommandName().equalsIgnoreCase(commandName)) {
                return command;
            }
        }
        return null;
    }
}
