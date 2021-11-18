package Blue.Rendering;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Light.LightSource;
import Blue.Light.SpotLight;
import Blue.Solids.Solid;

public class Scene {

    public List<Solid> objects;
    public LightSource lightSource;
    public BufferedImage image;

    public Scene() {
        objects = new ArrayList<Solid>();
        lightSource = new SpotLight(new Point(0, 0, 0), 200, 1);
        image = null;
    }

    public Scene(String imagePath) {
        objects = new ArrayList<Solid>();
        lightSource = new SpotLight(new Point(0, 0, 0), 200, 1);
        image = loadImage(imagePath);
    }

    public Scene(LightSource lightSource) {
        objects = new ArrayList<Solid>();
        this.lightSource = lightSource;
        image = null;
    }

    public Scene(LightSource lightSource, String imagePath) {
        objects = new ArrayList<Solid>();
        this.lightSource = lightSource;
        image = loadImage(imagePath);
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void add(Solid object) {
        objects.add(object);
    }

    public Color getSkyBoxColor(Vector u) {
        if (image == null)
            return new Color(0);

        double _u = 0.5 + Math.atan2(u.k, u.i) / (2.0 * Math.PI);
        double _v = 0.5 - Math.asin(u.j) / Math.PI;

        _u *= image.getWidth() - 1;
        _v *= image.getHeight() - 1;

        // return new Color(0);

        return new Color(image.getRGB((int) _u, (int) _v));
    }

}