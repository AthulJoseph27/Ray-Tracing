package Blue.GUI;

import java.awt.*;
import javax.swing.JFrame;

public class Window extends JFrame {

    private Screen screen;
    private final int WIDTH;
    private final int HEIGHT;

    public Window(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        screen = new Screen(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(screen);
        this.pack(); // Add componenets above this

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        // this.getContentPane().setBackground(Color.BLACK);
    }

    public void updateFrame(Color[][] frame) {
        screen.updateFrame(frame);
    }
}