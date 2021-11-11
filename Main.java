import java.awt.Color;
import java.io.*;
import Blue.*;

public class Main {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final double FL = 2500.0;

    public static void main(String[] args) throws IOException {
        // LightSource lightSource = new AreaLight(200, 200, new Point(0, 0, 0), 0.0,
        // 0.0, Math.PI, Color.WHITE);
        // LightSource lightSource = new SpotLight(new Point(-100, -100, -100), 500, 1);
        // LightSource lightSource = new SpotLight(new Point(-1000, -1000, -1000), 1700,
        // 1);
        LightSource lightSource = new Sun(new Point());

        Scene scene = new Scene(lightSource);

        Plane pln = new Plane(400, 400, new Point(250, -1000, -250), Math.PI, 0.0, 0.0, Color.ORANGE, 0.5);
        scene.add(pln);

        // System.out.println(pln);
        // System.exit(0);

        scene.add(new Sphere(60, new Point(100, 400, 250), new Color(0, 255, 128), 0.4));
        scene.add(new Sphere(60, new Point(240, 400, 250), new Color(0, 0, 255), 0.2));
        scene.add(new Sphere(60, new Point(380, 400, 250), new Color(255, 255, 255), 0.3));

        Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        Window window = new Window(WIDTH, HEIGHT);
        new SliderXYZ(cam, "Rotation", 0, 360, 0, "rotation");
        // new SliderXYZ(cam, "Location", new Point(-1000, -1000, -1000), new
        // Point(1000, 1000, 1000), new Point(0, 0, 0),
        // "center");

        new SliderXYZ((Callable) scene.lightSource, "Location", new Point(-1000, -1000, -1000),
                new Point(1000, 1000, 1000), new Point(0, 0, 0), "center");
        // new SliderXYZ((Callable) pln, "Plane Location", new Point(-1000, -1000,
        // -1000), new Point(1000, 1000, 1000),
        // new Point(0, 0, 0), "center");
        game_loop(window, cam);

    }

    private static void game_loop(Window window, Camera cam) {
        while (true) {
            window.update_frame(cam.get_frame());
        }
    }

}