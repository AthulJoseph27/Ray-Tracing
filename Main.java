import java.awt.Color;
import java.io.*;

import javax.swing.JFrame;

import Blue.*;

public class Main {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final double FL = 1000.0;

    public static void main(String[] args) throws IOException {
        Scene scene = new Scene();
        scene.add(new Sphere(50, new Point(150, 50, 200)));
        scene.add(new Sphere(50, new Point(350, 50, 200)));

        Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        Window window = new Window();
        Slider slider = new Slider(cam);

        game_loop(window, slider, cam);

    }

    private static void game_loop(Window window, Slider slider, Camera cam) {
        while (true) {
            window.update_frame(cam.get_frame());
        }
    }

}

// (0.7853981633974483,1,2,3)
// (1.0471975511965976,5,4,3)