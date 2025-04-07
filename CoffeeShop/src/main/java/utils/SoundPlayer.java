package utils;

import javax.sound.sampled.*;
import java.io.InputStream;

/**
 * Utility class for playing sound effects in the application.
 *
 * @author Mohd Faiz
 */
public class SoundPlayer {
    /**
     * Enum representing the different types of sounds available in the application.
     */
    public enum SoundType {
        STARTUP, EXIT, NEW_ITEM_ADD, SUBMIT_ORDER, ORDER_COMPLETE
    }

    /**
     * Plays the specified sound type.
     * If the sound file cannot be loaded or found in the application then it will play system beep sound.
     *
     * @param type The type of sound to play (from the SoundType enum)
     */
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
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Maps sound types to their corresponding sound file names.
     *
     * @param type The sound type to look up
     * @return The filename associated with the sound type
     */
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