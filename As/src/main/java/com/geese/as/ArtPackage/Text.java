package com.geese.as.ArtPackage;


public interface Text {
    String getText();
    char getPauseChar();
    int getTimeBetweenPauses();

    default String displayWithPauses() {
        StringBuilder result = new StringBuilder();

        for (char c : getText().toCharArray()) {
            result.append(c);

            if(c == getPauseChar()){
                sleep(getTimeBetweenPauses());
            }
        }
        return result.toString();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    default String ToJS() {
        return """
            typewriter("%s", "%c", %d);
            """.formatted(
                getText().replace("\"", "\\\""),
                getPauseChar(),
                getTimeBetweenPauses()
        );
    }
}
