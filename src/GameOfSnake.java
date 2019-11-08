import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameOfSnake extends JPanel implements ActionListener {
    private final int size = 320;
    private final int icon = 16;
    private final int all_icon = 400;
    private Image snake;
    private Image beer;
    private int beerX;
    private int beerY;
    private int[] x = new int[all_icon];
    private int[] y = new int[all_icon];
    private int snakes;
    private Timer timer;
    private boolean up = false;
    private boolean down = false;
    private boolean right = true;
    private boolean left = false;
    private boolean InGame = true;


    public GameOfSnake() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void loadImages() {
        ImageIcon iib = new ImageIcon("beer.png");
        beer = iib.getImage();
        ImageIcon iis = new ImageIcon("snake.png");
        snake = iis.getImage();
    }

    public void initGame() {
        snakes = 3;
        for (int i = 0; i < snakes; i++) {
            x[i] = 48 - i * all_icon;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createBeer();
    }

    public void createBeer() {
        beerX = new Random().nextInt(20)*icon;
        beerY = new Random().nextInt(20)*icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (InGame) {
            g.drawImage(beer, beerX, beerY, this);
            for (int i = 0; i < snakes; i++) {
                g.drawImage(snake, x[i], y[i], this);
            }
        } else {
            String str = "Game over";
            g.setColor(Color.white);
            g.drawString(str,125,size/2);
        }
    }

    public void move() {
        for (int i = snakes; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= icon;
        }
        if (right) {
            x[0] += icon;
        }
        if (up) {
            y[0] -= icon;
        }
        if (down) {
            y[0] += icon;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (InGame) {
            checkBeer();
            checkCollisions();
            move();
        }
        repaint();
    }

    public void checkBeer() {
        if (x[0] == beerX && y[0] == beerY) {
            snakes++;
            createBeer();
        }
    }

    public void checkCollisions(){
        for (int i = snakes; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                InGame = false;
            }
        }

        if(x[0]>size){
            InGame = false;
        }
        if(x[0]<0){
            InGame = false;
        }
        if(y[0]>size){
            InGame = false;
        }
        if(y[0]<0){
            InGame = false;
        }
    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_NUMPAD4 && ! right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_NUMPAD6 && ! left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_NUMPAD8 && ! down){
                up = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_NUMPAD5 && ! up){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
