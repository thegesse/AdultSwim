package com.geese.as.Commands.DefaultCommands;

import com.geese.as.Commands.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Component
@Data
@EqualsAndHashCode(callSuper = true)
public class ClearCommand extends Command {

    public enum ClearTarget {
        CONSOLE,
        OUTPUT,
        ALL
    }
    private ClearTarget target = ClearTarget.ALL;

    public ClearCommand() {
        setCommandName("clear");
    }

    @Override
    protected void validate() {
        if(getCommandInput() != null && !getCommandInput().isBlank()) {
            try {
                this.target = ClearTarget.valueOf(getCommandInput().toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException("invalid target");
            }
        }
    }

    //return JS to execute on front
    @Override
    protected String execute() {
        return switch (target) {
            case CONSOLE -> "console.clear();";
            case OUTPUT -> "document.getElementById('output').innerHTML = '';\";";
            case ALL-> """
                console.clear();
                document.getElementById('output').innerHTML = '';
                document.getElementById('input').value = '';
                """;
        };
    }

    @Override
    protected void afterExecute(String result) {
        clearTemporaryOutput();
    }
}
