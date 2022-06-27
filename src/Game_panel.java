import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class Game_panel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 90;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int body_parts = 6;
    int fruit_eaten;
    int fruit_x;
    int fruit_y;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    Game_panel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
    this.setBackground(new java.awt.Color(143, 210, 110));
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    start_Game();

    }

    public void start_Game(){
        new_fruit();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
        if(running){
            //fruta
            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE,0,UNIT_SIZE*i,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,UNIT_SIZE*i);
            }
            g.setColor(Color.red);
            g.fillOval(fruit_x,fruit_y,UNIT_SIZE,UNIT_SIZE);

            //Score
            g.setColor(Color.RED);
            g.setFont(new Font("Super Mario 256",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());

            //corpo
            for (int i = 0; i < body_parts ; i++) {
                if(i==0){
                    g.setColor(new java.awt.Color(36, 54, 36));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }

                else{
                    g.setColor(new java.awt.Color(29, 45, 29));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            g.drawString("Score: "+fruit_eaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+fruit_eaten))/2,g.getFont().getSize());

        }
        else{
            game_over(g);
        }

    }

    public void new_fruit(){
        fruit_x = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        fruit_y = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    public void move(){
        for (int i = body_parts; i >0 ; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    public void check_fruit(){
        if((x[0] == fruit_x) && (y[0] == fruit_y)){
            body_parts++;
            fruit_eaten++;
            new_fruit();
        }
    }

    public void check_colisions(){
        //verifica se a cabeça bate o corpo

        for (int i = body_parts; i > 0; i--) {

            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //verifica se a cabeça bate o lado esquerdo
        if(x[0] < 0){
            running = false;
        }

        //verifica se a cabeça bate o lado direito
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //verifica se a cabeça bate o topo
        if(y[0] < 0){
            running = false;
        }
        //verifica se a cabeça bate a base
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }

    public void game_over(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Super Mario 256",Font.BOLD,50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
        g.drawString("Score: "+fruit_eaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+fruit_eaten))/2,g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            check_fruit();
            check_colisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if (direction != 'R'){
                            direction = 'L';
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (direction != 'L'){
                            direction = 'R';
                        }
                        break;

                    case KeyEvent.VK_UP:
                        if (direction != 'D'){
                            direction = 'U';
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (direction != 'U'){
                            direction = 'D';
                        }
                        break;
                }
        }
    }
}
