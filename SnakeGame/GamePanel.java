package SnakeGame;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    // private static final long serialVersionUID = 1L;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 105;
    int tickcount = 0;
    final int[] x = new int[GAME_UNIT];
    final int[] y =new int[GAME_UNIT];

    int bodyParts = 6;
    int foodAte;
    int foodX;
    int foodY;
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random;

    GamePanel(){
        random  = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
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
        debug(g);

        if (running) {
//            //box cell border
//            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
//                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//            }
            g.setColor(Color.ORANGE);
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);
    
            for(int i = 0; i<bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }else{
                    g.setColor(new Color(45, 100, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }        g.setFont(new Font("Dialog", Font.BOLD, 10));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dialog", Font.BOLD, 20));
            metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: "+foodAte, (SCREEN_WIDTH-metrics.stringWidth("SCORE: "+foodAte))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }


    }

    public void move() {
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
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
            if((x[0] == x[i])&&y[0]==y[i]){
                running = false;
            }
        }
        //check if the head  touches border
        if(x[0]<0){
            running = false;
        }
        //check if the head touches right border
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        //check if the head touches the top border
        if(y[0]<0){
            running =false;
        }
        //check if the head touches the bottom border
        if(y[0]>SCREEN_HEIGHT){
            running =false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        debug(g);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.setFont(new Font("Dialog", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    void debug(Graphics g){
        g.setFont(new Font("Dialog", Font.BOLD, 10));
        FontMetrics metrics = getFontMetrics(g.getFont());
        tickcount++;
        g.drawString("tick: "+tickcount +" running:"+running, 10, 10);
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
                    if(direction!='R')direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U') direction = 'D';
                    break;

                default:
                    break;
            }
        }
    }
}
