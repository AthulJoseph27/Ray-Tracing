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
        private static final double FL = 400.0;

        public static void main(String[] args) throws IOException {
                // LightSource lightSource = new AreaLight(200, 200, new Point(0, 0, 0), 0.0,
                // 0.0, Math.PI, Color.WHITE);
                // LightSource lightSource = new SpotLight(new Point(), 100, 1);
                // LightSource lightSource = new SpotLight(new Point(-1000, -1000, -1000), 1700,
                // 1);
                LightSource lightSource = new Sun(new Point(0, 1, -1));

                Scene scene = new Scene(lightSource,
                                "/Users/athuljoseph/Desktop/Projects/RayTracing/assets/Equirectangular_Images/City.jpg");

                // scene.add(new Plane(100000, 1000000, new Point(260, 50, -200),
                // Math.toRadians(105),
                // 0.0, 0.0, new Color(255, 255, 255),
                // 0.0));

                scene.add(new Sphere(200, new Point(-150, 600, 350), new Color(0, 186, 255),
                                0.6));
                scene.add(new Sphere(200, new Point(300, 600, 350), new Color(255, 208, 210),
                                0.5));

                scene.add(new Sphere(200, new Point(750, 600, 350), new Color(0, 255, 196),
                                0.3));

                Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

                Window window = new Window(WIDTH, HEIGHT);
                new SliderXYZ(scene, "BG Rotation", -360, 360, 0, "bgRotation");
                new SliderXYZ((Callable) lightSource, "Light Rotation", -360, 360, 0, "rotation");
                new SliderXYZ(cam, "Location", new Point(-1000, -1000, -1000), new Point(1000, 1000, 1000),
                                new Point(0, 0, 0),
                                "center");

                gameLoop(window, cam);

        }

        private static void gameLoop(Window window, Camera cam) {
                while (true) {
                        window.updateFrame(cam.getFrame());
                }
        }

}