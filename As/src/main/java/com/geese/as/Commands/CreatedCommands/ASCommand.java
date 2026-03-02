package com.geese.as.Commands.CreatedCommands;

import com.geese.as.ArtPackage.AS.ASAscii;
import com.geese.as.ArtPackage.AS.ASText;
import com.geese.as.Commands.Command;
import com.geese.as.JavaFx.AsciiAnimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ASCommand extends Command {

    @Autowired
    private AsciiAnimationService animationService;

    @Autowired
    private ASAscii asAscii;

    @Autowired
    private ASText asText;

    public ASCommand() {
        setCommandName("leavingishard");
    }

    @Override
    protected String execute() {
        String type = getCommandInput();

        if (type == null || type.isBlank()) {
            type = "as";
        }

        if (type.equalsIgnoreCase("as")) {
            String ascii = asAscii.getArt();
            String text = asText.getText().replace('$', '\n');

            String musicPath = "static/sounds/as-theme.mp3";

            animationService.play(ascii, text, musicPath, 5000);
            return "Secret command found";
        }

        return "Unknown animation. Try: animate as";
    }
}
