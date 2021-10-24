package Blue;

import java.awt.*;
import javax.swing.JFrame;

public class Window extends JFrame {

    private Screen screen;

    public Window() {
        screen = new Screen(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(screen);
        this.pack(); // Add componenets above this

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        // this.getContentPane().setBackground(Color.BLACK);
    }

    public void update_frame(Color[][] frame) {
        screen.update_frame(frame);
    }
}