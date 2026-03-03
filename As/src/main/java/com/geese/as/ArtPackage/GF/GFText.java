package com.geese.as.ArtPackage.GF;

import com.geese.as.ArtPackage.Text;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GFText implements Text {

    private String text = "Time is dead $ Meaning has no meaning $ Existence is upside down and HE reigns supreme $ Welcome one and all $ to WEIRDMAGEDDON";
    private char pauseChar = '$';
    private int timeBetweenPauses = 300;
}
