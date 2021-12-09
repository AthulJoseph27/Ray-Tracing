import java.awt.Color;
import java.io.*;
import Blue.GUI.*;
import Blue.Geometry.Point;
import Blue.Light.*;
import Blue.Rendering.*;
import Blue.Solids.*;

public class Main {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final double FL = 10000.0;

    public static void main(String[] args) throws IOException {
        // LightSource lightSource = new AreaLight(200, 200, new Point(0, 0, 0), 0.0,
        // 0.0, Math.PI, Color.WHITE);
        // LightSource lightSource = new SpotLight(new Point(), 100, 1);
        // LightSource lightSource = new SpotLight(new Point(-1000, -1000, -1000), 1700,
        // 1);
        LightSource lightSource = new Sun(new Point(0, 1, -1));

        Scene scene = new Scene(lightSource,
                "/Users/athuljoseph/Desktop/Projects/RayTracing/assets/Equirectangular_Images/Sky.jpg");

        // scene.add(new Plane(5000, 5000, new Point(260, 60, 180), Math.toRadians(105),
        // 0.0, 0.0, new Color(129, 47, 255),
        // 0.5));

        scene.add(new Sphere(100, new Point(150, 300, 300), new Color(0, 186, 255), 1.0));
        scene.add(new Sphere(100, new Point(350, 300, 300), new Color(255, 0, 59), 0));

        // scene.add(new Sphere(50, new Point(100, 600, 400), new Color(255, 73, 138),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(120, 450, 300), new Color(255, 69, 0),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(140, 300, 200), new Color(255, 236, 73),
        // Math.random()));

        // scene.add(new Sphere(50, new Point(240, 600, 400), new Color(0, 255, 196),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(255, 450, 300), new Color(73, 92, 255),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(270, 300, 200), new Color(0, 186, 255),
        // Math.random()));

        // scene.add(new Sphere(50, new Point(380, 600, 400), new Color(255, 208, 210),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(390, 450, 300), new Color(196, 0, 255),
        // Math.random()));
        // scene.add(new Sphere(50, new Point(400, 300, 200), new Color(255, 0, 59),
        // Math.random()));

        Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        Window window = new Window(WIDTH, HEIGHT);
        new SliderXYZ(cam, "Rotation", -360, 360, 0, "rotation");
        new SliderXYZ(cam, "Location", new Point(-1000, -1000, -1000), new Point(1000, 1000, 1000), new Point(0, 0, 0),
                "center");

        // new SliderXYZ((Callable) scene.lightSource, "Location", new Point(-1000,
        // -1000, -1000),
        // new Point(1000, 1000, 1000), new Point(0, 0, 0), "center");
        // new SliderXYZ((Callable) pln, "Plane Location", new Point(-1000, -1000,
        // -1000), new Point(1000, 1000, 1000),
        // new Point(0, 0, 0), "center");
        gameLoop(window, cam);

    }

    private static void gameLoop(Window window, Camera cam) {
        while (true) {
            window.updateFrame(cam.getFrame());
        }
    }

}