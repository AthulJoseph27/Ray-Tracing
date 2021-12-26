package Blue.Rendering;

import java.awt.*;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Solid;
import Blue.Light.Sun;

public class Camera implements Callable {

    private static final double MINIMUM_ILLUMINATION = 0.5;
    private static final int MAX_BOUNCES = 0;

    Scene scene;
    Point focus, planeCenter, deltaLocation, rotation;
    int width, height;
    double focal_length;
    Color[][] frame;

    public Camera(int width, int height, Scene scene, double focal_length) {
        this.width = width;
        this.height = height;
        this.scene = scene;
        this.focal_length = focal_length;
        this.planeCenter = new Point(width / 2.0, 0, height / 2.0);
        this.deltaLocation = new Point();
        this.rotation = new Point();
        focus = getFocus();
        frame = new Color[height][width];
        clearFrame();
    }

    private Point getFocus() {
        return new Point(planeCenter.x - deltaLocation.x, planeCenter.y + deltaLocation.y - focal_length,
                planeCenter.z - deltaLocation.z);
    }

    public void clearFrame() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = new Color(0);
            }
        }
    }

    private Color scaleBrightness(double scale, Color color) {
        int R = (int) Math.min((color.getRed() * scale), 255);
        int G = (int) Math.min((color.getGreen() * scale), 255);
        int B = (int) Math.min((color.getBlue() * scale), 255);
        return new Color(R, G, B, 255);
    }

    private Color addBrightness(double factor, Color color) {
        factor *= 255;
        int R = (int) Math.min((color.getRed() + factor), 255);
        int G = (int) Math.min((color.getGreen() + factor), 255);
        int B = (int) Math.min((color.getBlue() + factor), 255);
        return new Color(R, G, B, 255);
    }

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private Color lerp(Color c1, Color c2, double t) {
        int R = (int) lerp(c1.getRed(), c2.getRed(), t);
        int G = (int) lerp(c1.getGreen(), c2.getGreen(), t);
        int B = (int) lerp(c1.getBlue(), c2.getBlue(), t);

        return new Color(R, G, B, 255);

    }

    private void getIntersectionPoint(Ray ray) {

        for (int i = 0; i < scene.objects.size(); i++) {
            if (ray.getHitSubIndex() == -1 && i == ray.getHitIndex())
                continue;

            ray = scene.objects.get(i).getIntersectionPoint(ray);
        }

    }

    private Color reflectRay(int depth, Ray ray) {
        Vector u = Vector.unitVector(ray.getRay());

        getIntersectionPoint(ray);

        if (ray.getIntersection() == null) {
            return scene.getSkyBoxColor(u);
        }

        Solid curObj = scene.objects.get(ray.getHitIndex());
        Ray reflectedRay = curObj.getReflectedRay(ray);

        Color reflectedRayColor;

        if (depth == 0) {
            reflectedRayColor = scene.getSkyBoxColor(u);
        } else {
            reflectedRayColor = reflectRay(depth - 1, reflectedRay);
        }

        double diffuseBrightness = getDiffuseBrightness(ray);

        double specularBrightness = getSpecularBrightness(reflectedRay) *
                curObj.getReflectivity();

        Color c = lerp(curObj.getColor(), reflectedRayColor, curObj.getReflectivity());
        c = scaleBrightness(diffuseBrightness, c);
        c = addBrightness(specularBrightness, c);

        return c;
    }

    private double getDiffuseBrightness(Ray ray) {
        Vector dir = ((Sun) scene.lightSource).getDirection();
        dir.scale(-1);
        if (ray.getIntersection() != null) {
            return MINIMUM_ILLUMINATION;
        }

        return Math.max(MINIMUM_ILLUMINATION, Math.cos(Vector.angleBetween(ray.getNormal(), dir)));
    }

    private double getSpecularBrightness(Ray reflectedRay) {
        Vector dir = ((Sun) scene.lightSource).getDirection();
        double angle = Vector.angleBetween(dir, reflectedRay.getRay());

        return Math.pow(Math.min(0, Math.cos(angle)), 20.0);
    }

    public Color[][] getFrame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x - deltaLocation.x, deltaLocation.y, y - deltaLocation.z);
                Vector ray = new Vector(focus, p);
                ray.unitVector();
                ray.rotateXYZ(rotation.x, rotation.y, rotation.z);
                frame[height - y - 1][x] = reflectRay(MAX_BOUNCES, new Ray(p, ray, -1, -1));
                // frame[height - y - 1][x] = reflectRayWithRefraction(MAX_BOUNCES, -1, p, ray);
            }
        }

        return frame;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.equals("center")) {
            deltaLocation = p;
            focus = getFocus();
        } else if (type.equals("rotation")) {
            rotation = new Point(Math.toRadians(p.x), Math.toRadians(p.y), Math.toRadians(p.z));
        }
    }

    @Override
    public String toString() {
        return "{" + " focus='" + focus + "'" + ", rotation='" + rotation + "'" + ", width='" + width + "'"
                + ", height='" + height + "'" + ", focal_length='" + focal_length + "'" + "}";
    }

}