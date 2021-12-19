import java.awt.Color;
import java.io.*;
import Blue.GUI.*;
import Blue.Geometry.Point;
import Blue.Light.*;
import Blue.Rendering.*;
import Blue.Solids.*;

public class Main {

        private static final int WIDTH = 600;
        private static final int HEIGHT = 600;
        private static final double FL = 1000.0;

        public static void main(String[] args) throws IOException {
                STL.load("/Users/athuljoseph/Desktop/hex.stl");
        }

        // public static void main(String[] args) throws IOException {
        // // LightSource lightSource = new AreaLight(200, 200, new Point(0, 0, 0), 0.0,
        // // 0.0, Math.PI, Color.WHITE);
        // // LightSource lightSource = new SpotLight(new Point(), 100, 1);
        // // LightSource lightSource = new SpotLight(new Point(-1000, -1000, -1000),
        // 1700,
        // // 1);
        // LightSource lightSource = new Sun(new Point(0, 1, -1));

        // // Scene scene = new Scene(lightSource);

        // Scene scene = new Scene(lightSource,
        // "/Users/athuljoseph/Desktop/Projects/RayTracing/assets/Equirectangular_Images/Sky.jpg");

        // scene.add(new Plane(100000, 1000000, new Point(0, 0, 0),
        // Math.toRadians(90),
        // 0.0, 0.0, new Color(255, 255, 255),
        // 1.0));

        // // scene.add(new Sphere(200, new Point(-150, 600, 350), new Color(234, 145,
        // // 129),
        // // 0.15));

        // scene.add(new Sphere(200, new Point(100, 900, 250), new Color(27, 140, 255),
        // 0.5, 1.5));

        // // scene.add(new Sphere(50, new Point(300, 900, 250), new Color(255, 19, 92),
        // // 0.0));

        // scene.add(new Sphere(180, new Point(100, 1500, 250), new Color(255, 19, 92),
        // 0.5));

        // // scene.add(new Sphere(350, new Point(900, 800, 350), new Color(255, 208,
        // 210),
        // // 1.0));

        // // scene.add(new Sphere(120, new Point(650, 150, 120), new Color(0, 196,
        // 255),
        // // 0.1));

        // // scene.add(new Sphere(50, new Point(150, 150, 50), new Color(255, 236, 73),
        // // 0.2));

        // // scene.add(new Sphere(120, new Point(-20, 150, 120), new Color(0, 255,
        // 196),
        // // 1.0));

        // // scene.add(new Sphere(250, new Point(-300, 800, 250), new Color(73, 92,
        // 255),
        // // 1.0));

        // // scene.add(new Sphere(100, new Point(-100, 1200, 100), new Color(0, 186,
        // 255),
        // // 1.0));

        // Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

        // Window window = new Window(WIDTH, HEIGHT);
        // new SliderXYZ(scene, "BG Rotation", -360, 360, 0, "bgRotation");
        // new SliderXYZ((Callable) lightSource, "Light Rotation", -360, 360, 0,
        // "rotation");
        // new SliderXYZ(cam, "Location", new Point(-1000, -1000, -1000),
        // new Point(1000, 1000, 1000),
        // new Point(0, 0, 0),
        // "center");

        // gameLoop(window, cam);

        // }

        private static void gameLoop(Window window, Camera cam) {
                while (true) {
                        window.updateFrame(cam.getFrame());
                }
        }

}
