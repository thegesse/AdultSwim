package com.geese.as.JavaFx;

import com.geese.as.Commands.CreatedCommands.GFCommands.GFCommandList;
import com.geese.as.Commands.CreatedCommands.GFCommands.GFCommandTemplate;
import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class GFTerminalService {

    @Autowired
    private GFCommandList gfCommandList;

    private Stage gfStage;
    private Text displayText;
    private TextField inputField;
    private boolean windowOpen = false;

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static boolean toolkitStarted = false;

    public GFTerminalService() {
        System.out.println("[GFTerminalService] Created");
    }

    @PostConstruct
    public void init() {
        System.out.println("[GFTerminalService] Initializing...");

        if (!toolkitStarted) {
            toolkitStarted = true;

            new Thread(() -> {
                try {
                    javafx.application.Application.launch(JavaFXApp.class);
                } catch (IllegalStateException e) {
                    System.out.println("[GFTerminalService] JavaFX already initialized");
                    latch.countDown();
                }
            }).start();
        }
    }

    public static void notifyToolkitStarted() {
        System.out.println("[GFTerminalService] Toolkit ready");
        latch.countDown();
    }

    public void waitForToolkit() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void openWindow() {
        System.out.println("[GFTerminalService] Opening GF window...");
        waitForToolkit();

        Platform.runLater(() -> {
            if (gfStage != null && gfStage.isShowing()) {
                System.out.println("[GFTerminalService] Window already open");
                return;
            }

            gfStage = new Stage();
            gfStage.initStyle(StageStyle.UNDECORATED);
            gfStage.setFullScreen(true);
            gfStage.setAlwaysOnTop(true);

            // Display text (output area)
            displayText = new Text("GF Terminal\n\nType 'help' for commands...");
            displayText.setFont(Font.font("Courier New", 16));
            displayText.setFill(Color.MAGENTA);
            displayText.setTextAlignment(TextAlignment.CENTER);

            // Input field
            inputField = new TextField();
            inputField.setFont(Font.font("Courier New", 14));
            inputField.setStyle(
                    "-fx-background-color: black;" +
                            "-fx-text-fill: #00ff00;" +
                            "-fx-border-color: magenta;" +
                            "-fx-border-width: 2;" +
                            "-fx-padding: 10;"
            );
            inputField.setPromptText("Enter command...");
            inputField.setMaxWidth(400);

            // Handle input - process GF commands exclusively
            inputField.setOnAction(e -> {
                String input = inputField.getText().trim();
                System.out.println("[GFTerminalService] Input: " + input);

                processCommand(input);
                inputField.clear();
            });

            // Layout
            VBox root = new VBox(30, displayText, inputField);
            root.setStyle("-fx-background-color: black; -fx-padding: 50;");
            root.setAlignment(javafx.geometry.Pos.CENTER);

            Scene scene = new Scene(root, Color.BLACK);

            // Close on click outside input
            scene.setOnMouseClicked(e -> {
                if (e.getTarget() != inputField) {
                    closeWindow();
                }
            });

            // Close on ESC
            scene.setOnKeyPressed(e -> {
                if (e.getCode().toString().equals("ESCAPE")) {
                    closeWindow();
                }
            });

            gfStage.setScene(scene);
            gfStage.show();
            windowOpen = true;

            inputField.requestFocus();

            System.out.println("[GFTerminalService] Window opened");
        });
    }

    private void processCommand(String input) {
        if (input.isEmpty()) return;

        String[] parts = input.split("\\s+", 2);
        String commandName = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        appendToDisplay("> " + input);

        switch(commandName) {
            case "help":
                showHelp();
                break;
            case "clear":
                setDisplayText("GF Terminal\n\nCleared!");
                break;
            case "close":
            case "exit":
                appendToDisplay("Closing...");
                closeWindow();
                break;
            default:
                // Look up in GFCommandList
                GFCommandTemplate cmd = gfCommandList.getCommandByName(commandName);
                if (cmd != null) {
                    appendToDisplay(cmd.getContent());
                } else {
                    appendToDisplay("Unknown command: " + commandName);
                    appendToDisplay("Type 'help' for available commands");
                }
        }
    }

    private void showHelp() {
        StringBuilder help = new StringBuilder();
        help.append("GF Terminal Commands:\n\n");

        for (GFCommandTemplate cmd : gfCommandList.getCommands()) {
            help.append("  - ").append(cmd.getCommandName()).append("\n");
        }

        help.append("\nSystem commands:\n");
        help.append("  - help\n");
        help.append("  - clear\n");
        help.append("  - close/exit");

        setDisplayText(help.toString());
    }

    public void closeWindow() {
        Platform.runLater(() -> {
            if (gfStage != null) {
                gfStage.close();
                windowOpen = false;
                System.out.println("[GFTerminalService] Window closed");
            }
        });
    }

    public void setDisplayText(String text) {
        Platform.runLater(() -> {
            if (displayText != null) {
                displayText.setText(text);
            }
        });
    }

    public void appendToDisplay(String text) {
        Platform.runLater(() -> {
            if (displayText != null) {
                String current = displayText.getText();
                displayText.setText(current + "\n" + text);
            }
        });
    }

    public boolean isWindowOpen() {
        return windowOpen;
    }
}