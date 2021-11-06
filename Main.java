import java.awt.Color;
import java.io.*;
import Blue.*;

public class Main {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final double FL = 1000.0;

    public static void main(String[] args) throws IOException {
        LightSource lightSource = new AreaLight(200, 200, new Point(-250, 200, 200), 0.0, 0.0, Math.PI, Color.WHITE);
        Scene scene = new Scene();
        // scene.add(new Plane(100, 100, new Point(300, 0, 2A50), 0.0, 0.0, 0.0,
        // Color.ORANGE));
        Plane pln = new Plane(400, 500, new Point(250, 200, 250), Math.PI / 3.0, 0.0, 0.0, Color.ORANGE);
        scene.add(pln);
        scene.add(new Sphere(50, new Point(200, 0, 250), new Color(0, 255, 0)));
        scene.add(new Sphere(50, new Point(300, 0, 250), new Color(0, 0, 255)));
        Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        Window window = new Window(WIDTH, HEIGHT);
        new SliderXYZ(cam.plane, "Rotation", 0, 360, 0, "rotation");
        new SliderXYZ((Callable) scene.lightSource, "Location", new Point(-1000, -1000, -1000),
                new Point(1000, 1000, 1000), new Point(0, 0, 0), "center");
        game_loop(window, cam);

    }

    private static void game_loop(Window window, Camera cam) {
        while (true) {
            window.update_frame(cam.get_frame());
        }
    }

}