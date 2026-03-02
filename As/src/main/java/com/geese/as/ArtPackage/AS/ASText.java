package com.geese.as.ArtPackage.AS;

import com.geese.as.ArtPackage.Text;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ASText implements Text {

    private String text = "Use Javascript for Backend development $ Or don't $ We don't really care";
    private char pauseChar = '$';
    private int timeBetweenPauses = 300;
}
