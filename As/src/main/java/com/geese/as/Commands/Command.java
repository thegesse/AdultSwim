package com.geese.as.Commands;

public interface Command {
    String getCommandName();
    void SetCommandName(String commandName);
    String getCommandInput();
    void SetCommandInput(String commandInput);
    String execute();
}
