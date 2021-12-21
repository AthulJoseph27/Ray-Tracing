import java.awt.Color;
import java.io.*;
import Blue.GUI.*;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Light.*;
import Blue.Rendering.*;
import Blue.Solids.*;

public class Main {

        private static final int WIDTH = 600;
        private static final int HEIGHT = 600;
        private static final double FL = 1000.0;

        // public static void main(String[] args) throws IOException {
        // STL.load("/Users/athuljoseph/Desktop/hex.stl");
        // }

        public static void main(String[] args) throws IOException {
                // LightSource lightSource = new AreaLight(200, 200, new Point(0, 0, 0), 0.0,
                // 0.0, Math.PI, Color.WHITE);
                // LightSource lightSource = new SpotLight(new Point(), 100, 1);
                // LightSource lightSource = new SpotLight(new Point(-1000, -1000, -1000),
                // 1700,
                // 1);
                LightSource lightSource = new Sun(new Point(0, 1, -1));

                // Scene scene = new Scene(lightSource);

                Scene scene = new Scene(lightSource,
                                "/Users/athuljoseph/Desktop/Projects/RayTracing/assets/Equirectangular_Images/Sky.jpg");

                scene.add(new CustomSolid("/Users/athuljoseph/Desktop/hex.stl", Color.WHITE, 0.0,
                                scene.objects.size()));

                // scene.add(new Sphere(200, new Point(100, 900, 250), new Color(27, 140, 255),
                // 1.0, scene.objects.size()));

                // scene.add(new Sphere(200, new Point(500, 900, 250), new Color(27, 140, 255),
                // 0.50, scene.objects.size()));

                Camera cam = new Camera(WIDTH, HEIGHT, scene, FL);

                Window window = new Window(WIDTH, HEIGHT);
                new SliderXYZ(scene, "BG Rotation", -360, 360, 0, "bgRotation");
                new SliderXYZ((Callable) cam, "Rotation", -360, 360, 0,
                                "rotation");
                new SliderXYZ(cam, "Location", new Point(-10000, -10000, -10000),
                                new Point(10000, 10000, 10000),
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
