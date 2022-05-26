package SnakeGame.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Sound {
    public void sound() {
        Clip clip;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(this.getClass().getResource("NameOfFile.wav")
                    )
            );
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            // If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY);
            // If you want to stop the sound, then use clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void playLoop(String musicLocation){
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("cant find file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playMusic(String musicLocation){
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("cant find file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
