package com.geese.as.Commands;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandRegistry {

    @Autowired
    private ApplicationContext context;

    @Getter
    private final Map<String, Command> commands = new HashMap<>();

    @PostConstruct
    public void init() {
        Map<String, Command> beans = context.getBeansOfType(Command.class);

        beans.forEach((beanName, command) -> {
            String name = command.getCommandName().toLowerCase();
            commands.put(name, command);
        });

        System.out.println("loaded" + commands.size() + "commands");
    }

    public boolean hasCommand(String name) {
        return name != null && commands.containsKey(name.toLowerCase());
    }

    public Command getCommand(String name) {
        if(!hasCommand(name)) {
            System.out.println("Command " + name + " not found");
        }
        return commands.get(name.toLowerCase());
    }

    public String execute (String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : null;

        Command command = getCommand(commandName);
        command.setCommandInput(args);
        return command.run();
    }

    public String getHelpText() {
        StringBuilder help = new StringBuilder();
        help.append("Commands\n");
        for (String name : commands.keySet()) {
            help.append("  - ").append(name).append("\n");
        }
        return help.toString();
    }
    public List<String> getCommandNames() {
        return new ArrayList<>(commands.keySet());
    }
}
