package utils;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundPlayer {
    public enum SoundType {
        STARTUP, EXIT, NEW_ITEM_ADD, SUBMIT_ORDER, ORDER_COMPLETE
    }

    public static void playSound(SoundType type) {
        new Thread(() -> {
            try {
                String soundFile = getSoundFile(type);
                InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/files/" + soundFile);

                if (audioSrc != null) {
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioSrc);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } else {
                    java.awt.Toolkit.getDefaultToolkit().beep(); // When there is no sound file
                }
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }

    private static String getSoundFile(SoundType type) {
        return switch (type) {
            case STARTUP -> "Startup.wav";
            case EXIT -> "Exit.wav";
            case NEW_ITEM_ADD -> "NewItemAdd.wav";
            case ORDER_COMPLETE -> "OrderComplete.wav";
            case SUBMIT_ORDER -> "SubmitOrder.wav";
        };
    }
}