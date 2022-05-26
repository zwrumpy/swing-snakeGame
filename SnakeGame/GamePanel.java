package SnakeGame;

import SnakeGame.sound.Sound;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    GamePanel instance;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    final int[] x = new int[GAME_UNIT];
    final int[] y =new int[GAME_UNIT];

    int bodyParts = 6;
    int foodAte;
    int foodX;
    int foodY;
    public char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    final String soundFolder = "F:\\GitHub\\swing-snakeGame\\SnakeGame\\sound\\";

    GamePanel(){
        random  = new Random();
        instance = this;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(25, 25, 25));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newFood();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            drawFood(g);
            drawBody(g);
            drawScore(g);
        }
        else{ gameOver(g); }
    }

    public void drawFood(Graphics g){
        g.setColor(Color.ORANGE);
        g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
    }

    public void drawBody(Graphics g){
        for(int i = 0; i<bodyParts; i++){
            if(i == 0){
                g.setColor(Color.GREEN);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }else{
                g.setColor(new Color(45, 100, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
    }

    public void drawScore(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Dialog", Font.BOLD, 30));
        FontMetrics metrics  =getFontMetrics(g.getFont());
        g.drawString("Score: "+foodAte, (SCREEN_WIDTH-metrics.stringWidth("SCORE: "+foodAte))/2, g.getFont().getSize());
    }

    public void move() {
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1]; 
            y[i] = y[i-1]; 
        }

        switch (direction) {
            case 'U' -> {y[0] = y[0] - UNIT_SIZE;}
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }

    public void newFood(){
        foodX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        foodY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void checkFood(){
        if((x[0]==foodX)&&(y[0]==foodY)){
            bodyParts++;
            foodAte++;
            newFood();
        }
    }

    public void checkCollision() {
        //check the collision with body
        for(int i= bodyParts; i>0; i--){
            if ((x[0] == x[i]) && y[0] == y[i]) {
                running = false;
                break;
            }
        }
        //check if the head touches border
        if(x[0] < 0){
            running = false;
        }
        //check if the head touches right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //check if the head touches the top border
        if(y[0] < 0){
            running =false;
        }
        //check if the head touches the bottom border
        if(y[0]>SCREEN_HEIGHT){
            running =false;
        }

        if (!running) {
            Sound.playMusic(soundFolder + "drum.wav");
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("dialog", Font.BOLD, 15));
        FontMetrics metrics  =getFontMetrics(g.getFont());
        g.drawString("GameOver!", (SCREEN_WIDTH-metrics.stringWidth("GameOver!"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction!='R') {
                        if (direction != 'L') {
                            Sound.playMusic(soundFolder +"f.wav");
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
                    break;
                case KeyEvent.VK_SPACE:
                    running = true;
                    startGame();
                default:
                    break;
            }
        }
    }

    public GamePanel getInstance(){
        return instance;
    }
}
