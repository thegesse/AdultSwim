package com.geese.as.ArtPackage.AS;

import com.geese.as.ArtPackage.Ascii;
import org.springframework.stereotype.Component;

@Component
public class ASAscii implements Ascii {

    @Override
    public String getName() {
        return "Adult swim";
    }

    @Override
    public String getArt() {
        return " /$$$$  /$$$$$$   /$$$$$$  /$$$$\n" +
                "| $$_/ /$$__  $$ /$$__  $$|_  $$\n" +
                "| $$  | $$  \\ $$| $$  \\__/  | $$\n" +
                "| $$  | $$$$$$$$|  $$$$$$   | $$\n" +
                "| $$  | $$__  $$ \\____  $$  | $$\n" +
                "| $$  | $$  | $$ /$$  \\ $$  | $$\n" +
                "| $$$$| $$  | $$|  $$$$$$/ /$$$$\n" +
                "|____/|__/  |__/ \\______/ |____/\n" +
                "                                \n" +
                "                                \n" +
                "                                \n" +
                "WE REALLY DONT'T CARE";
    }
}
