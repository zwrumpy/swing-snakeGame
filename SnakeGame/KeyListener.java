package SnakeGame;

import SnakeGame.sound.Sound;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
    String soundFolder;
    char direction;

    public KeyListener(){

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(direction!='R') {
                    if (direction != 'L') {
                        Sound.playMusic( soundFolder+"f.wav");
                    }
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(direction!='L') {
                    if (direction != 'R') {
                        Sound.playMusic(soundFolder +"a.wav");
                    }
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if(direction!='D') {
                    if (direction != 'U') {
                        Sound.playMusic(soundFolder + "e.wav");
                    }
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(direction!='U') {
                    if (direction != 'D') {
                        Sound.playMusic(soundFolder +"c.wav");
                    }
                    direction = 'D';
                }
            default:
                break;
        }
    }
}
