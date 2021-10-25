import java.awt.Color;
import java.io.*;
import Blue.*;

public class Main {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final double FL = 1000.0;

    public static void main(String[] args) throws IOException {
        Scene scene = new Scene();
        scene.add(new Sphere(50, new Point(150, 50, 200), Color.BLUE));
        scene.add(new Sphere(50, new Point(350, 100, 200), Color.GREEN));

        Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        Window window = new Window(WIDTH, HEIGHT);
        Slider slider = new Slider(cam);

        System.err.println(scene.lightSource);
        game_loop(window, slider, cam);

    }

    private static void game_loop(Window window, Slider slider, Camera cam) {
        while (true) {
            window.update_frame(cam.get_frame());
        }
    }

}