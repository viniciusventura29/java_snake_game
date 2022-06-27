import javax.swing.*;

public class Game_frame extends JFrame {

    Game_frame(){
        this.add(new Game_panel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
