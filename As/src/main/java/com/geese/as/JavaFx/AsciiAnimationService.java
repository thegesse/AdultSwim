package com.geese.as.JavaFx;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CountDownLatch;

@Service
public class AsciiAnimationService {

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static boolean toolkitStarted = false;
    private MediaPlayer mediaPlayer;

    @PostConstruct
    public void init() {
        if (!toolkitStarted) {
            toolkitStarted = true;

            new Thread(() -> {
                try {
                    javafx.application.Application.launch(JavaFXApp.class);
                } catch (IllegalStateException e) {
                    System.out.println("JavaFX already initialized");
                    latch.countDown();
                }
            }).start();
        }
    }

    public static void notifyToolkitStarted() {
        latch.countDown();
    }

    public void play(String asciiArt, String text, String musicPath, int durationMs) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        Platform.runLater(() -> {
            try {
                createAndShowAnimation(asciiArt, text, musicPath);
            } catch (Exception e) {
                System.out.println("Animation error: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void createAndShowAnimation(String asciiArt, String text, String musicPath) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setFullScreen(true);
        stage.setAlwaysOnTop(true);

        // First part text(0-8s)
        Text firstText = new Text();
        firstText.setFont(Font.font("Courier New", 14));
        firstText.setFill(Color.LIME);
        firstText.setTextAlignment(TextAlignment.CENTER);

        // Second part text
        Text secondText = new Text();
        secondText.setFont(Font.font("Courier New", 14));
        secondText.setFill(Color.LIME);
        secondText.setTextAlignment(TextAlignment.CENTER);
        secondText.setOpacity(0);

        // ASCII logo
        Text asciiText = new Text(asciiArt);
        asciiText.setFont(Font.font("Courier New", 16));
        asciiText.setFill(Color.LIME);
        asciiText.setTextAlignment(TextAlignment.CENTER);
        asciiText.setOpacity(0);

        VBox root = new VBox(20, firstText, secondText, asciiText);
        root.setStyle("-fx-background-color: black; -fx-padding: 50;");
        root.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root, Color.BLACK);
        scene.setOnMouseClicked(e -> {
            stopMusic();
            stage.close();
        });

        stage.setScene(scene);
        stage.show();

        // Load and play music
        loadAndPlayMusic(musicPath, () -> {
            Platform.runLater(() -> {
                stopMusic();
                stage.close();
            });
        });

        // Animation script
        String firstPart = "Use Javascript for Backend development";
        String secondPart = "Or don't";

        // Type first part
        typewriterWithDuration(firstText, firstPart, 0, 8000, () -> {
            //Show second part
            firstText.setOpacity(1);
            secondText.setText(secondPart);
            secondText.setOpacity(1);

            delay(1000, () -> {
                secondText.setOpacity(1);

                delay(1000, () -> {
                    //Fade in logo
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), asciiText);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);
                    fadeIn.play();
                });
            });
        });
    }

    private void loadAndPlayMusic(String resourcePath, Runnable onEnd) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);

            if (!resource.exists()) {
                System.out.println("Music not found: " + resourcePath);
                return;
            }

            Path tempFile = Files.createTempFile("music", ".mp3");
            tempFile.toFile().deleteOnExit();

            try (InputStream is = resource.getInputStream()) {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            }

            Media media = new Media(tempFile.toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.7);

            mediaPlayer.setOnEndOfMedia(onEnd);
            mediaPlayer.setOnError(() -> {
                System.out.println("Media error: " + mediaPlayer.getError().getMessage());
            });

            mediaPlayer.play();
            System.out.println("Playing: " + resourcePath);

        } catch (Exception e) {
            System.out.println("Music error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void typewriterWithDuration(Text textNode, String text, long startDelay, long duration, Runnable onComplete) {
        delay(startDelay, () -> {
            char[] chars = text.toCharArray();
            long charDelay = duration / chars.length;

            AnimationTimer timer = new AnimationTimer() {
                int currentIndex = 0;
                long startTime = 0;

                @Override
                public void handle(long now) {
                    if (startTime == 0) startTime = System.currentTimeMillis();

                    long elapsed = System.currentTimeMillis() - startTime;
                    int targetIndex = (int) (elapsed / charDelay);

                    if (targetIndex > currentIndex && currentIndex < chars.length) {
                        textNode.setText(text.substring(0, currentIndex + 1));
                        currentIndex++;
                    }

                    if (currentIndex >= chars.length || elapsed >= duration) {
                        stop();
                        textNode.setText(text);
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }
                }
            };

            timer.start();
        });
    }

    private void delay(long millis, Runnable action) {
        new Thread(() -> {
            try {
                Thread.sleep(millis);
                Platform.runLater(action);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}