package com.geese.as.Commands;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public abstract class Command {

    private String commandName;
    private String commandInput;
    private String content;


    public final String run() {
        validate();
        beforeExecute();
        String result = execute();
        setContent(result);
        afterExecute(result);
        return result;
    }

    protected abstract String execute();

    protected void validate() {
        if (commandName == null) {
            throw new IllegalStateException("commandName is null");
        }
    }

    protected void beforeExecute() {}
    protected void afterExecute(String result) {}
    public void clearTemporaryOutput() {
        this.content = null;
    }
}
