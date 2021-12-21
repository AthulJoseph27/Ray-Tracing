package Blue.Rendering;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Light.LightSource;
import Blue.Light.SpotLight;
import Blue.Light.Sun;
import Blue.Solids.Solid;

public class Scene implements Callable {

    public List<Solid> objects;
    public LightSource lightSource;
    public BufferedImage image;
    private Point rotation;

    public Scene() {
        objects = new ArrayList<Solid>();
        lightSource = new Sun(new Point(0, 1, -1));
        image = null;
        rotation = new Point();
    }

    public Scene(String imagePath) {
        objects = new ArrayList<Solid>();
        lightSource = new Sun(new Point(0, 1, -1));
        image = loadImage(imagePath);
        rotation = new Point();
    }

    public Scene(LightSource lightSource) {
        objects = new ArrayList<Solid>();
        this.lightSource = lightSource;
        image = null;
        rotation = new Point();
    }

    public Scene(LightSource lightSource, String imagePath) {
        objects = new ArrayList<Solid>();
        this.lightSource = lightSource;
        image = loadImage(imagePath);
        rotation = new Point();
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

    private void normalizeCoordinate(Vector u) {
        u.rotateX(rotation.x);
        u.rotateY(rotation.y);
        u.rotateZ(rotation.z);
    }

    public Color getSkyBoxColor(Vector u) {
        if (image == null)
            return new Color(0);

        normalizeCoordinate(u);

        double w = Math.max(image.getWidth(), image.getHeight());
        double h = Math.min(image.getWidth(), image.getHeight());

        double _u = 0.5 + Math.atan2(u.j, u.i) / (2.0 * Math.PI);
        double _v = 0.5 - Math.asin(u.k) / Math.PI;

        _u *= w - 1;
        _v *= h - 1;

        if (image.getWidth() < image.getHeight())
            return new Color(image.getRGB((int) _v, (int) _u));
        return new Color(image.getRGB((int) _u, (int) _v));
    }

    @Override
    public void transform(Point p, String parameterName) {
        if (parameterName.equals("bgRotation")) {
            rotation = new Point(Math.toRadians(p.x), Math.toRadians(p.y), Math.toRadians(p.z));
        }
    }

}