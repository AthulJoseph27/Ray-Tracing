package Blue.Rendering;

import java.awt.*;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Solid;

public class Camera implements Callable {

    Scene scene;
    Point focus, planeCenter, deltaLocation, rotation;
    int width, height;
    double focal_length;
    Color[][] frame;

    private class VectorIndex {
        Vector v;
        int index;

        VectorIndex(Vector v, int index) {
            this.v = v;
            this.index = index;
        }
    }

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

    private Color adjustBrightness(double scale, Color color) {
        int R = (int) Math.min((color.getRed() * scale), 255);
        int G = (int) Math.min((color.getGreen() * scale), 255);
        int B = (int) Math.min((color.getBlue() * scale), 255);
        return new Color(R, G, B, 255);
    }

    private VectorIndex getIntersectionPoint(Point p, Vector u, int skipindex) {
        Vector intersectionpoint = null;

        int index = -1;

        for (int i = 0; i < scene.objects.size(); i++) {
            if (i == skipindex)
                continue;
            Vector ip = scene.objects.get(i).getIntersectionPoint(p, u);
            if (ip == null)
                continue;
            if ((intersectionpoint == null) || p.euclideanDistance(ip) < p.euclideanDistance(intersectionpoint)) {
                intersectionpoint = ip;
                index = i;
            }
        }

        return new VectorIndex(intersectionpoint, index);
    }

    private Color blendColor(Color c1, Color c2, double r1, double r2) {
        double r = r1 + r2;

        double R = c1.getRed() * r1 / r + c2.getRed() * r2 / r;
        double G = c1.getGreen() * r1 / r + c2.getGreen() * r2 / r;
        double B = c1.getBlue() * r1 / r + c2.getBlue() * r2 / r;

        return new Color((int) R, (int) G, (int) B, 255);
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

    private Color reflectRay(int depth, Point p, Vector ray) {

        Vector u = Vector.unitVector(ray);

        VectorIndex vi = getIntersectionPoint(p, u, -1);

        if (vi.v == null)
            return new Color(0);

        Vector reflectedRay = scene.objects.get(vi.index).getReflectedRay(vi.v, u);
        reflectedRay.unit_vector();

        VectorIndex vNext = getIntersectionPoint(new Point(vi.v), reflectedRay, vi.index);

        Solid curObj = scene.objects.get(vi.index);

        if (vNext.v == null) {
            // return curObj.getColor();
            double factor = scene.lightSource.getDiffuseBrightness(new Point(vi.v), curObj.getNormal(vi.v),
                    reflectedRay, Double.MAX_VALUE);
            factor = Math.max(factor, 0.1) + scene.lightSource.getSpecularBrightness(reflectedRay, Double.MAX_VALUE);
            return adjustBrightness(factor, curObj.getColor());
        }

        // TODO: Have to check if the ray hits light before any other object!!

        if (depth == 0) {

            // checking if the reflected ray hits any object
            double _dist = vNext.v == null ? Double.MAX_VALUE : (new Point(vNext.v)).euclideanDistance(vi.v);

            double factor = scene.lightSource.getDiffuseBrightness(new Point(vi.v),
                    scene.objects.get(vi.index).getNormal(vi.v), reflectedRay, _dist);
            factor = Math.max(factor, 0.1) * curObj.getReflectivity()
                    + scene.lightSource.getSpecularBrightness(reflectedRay, _dist);
            return adjustBrightness(factor, scene.objects.get(vi.index).getColor());
        }

        Color c = reflectRay(depth - 1, new Point(vi.v), reflectedRay);

        if (c.getRGB() == 0)
            return c;

        // return blendColor(c, curObj.getColor(), 1.0, curObj.getReflectivity());

        return lerp(c, curObj.getColor(), curObj.getReflectivity());
    }

    public Color[][] getFrame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x - deltaLocation.x, deltaLocation.y, y - deltaLocation.z);
                Vector ray = new Vector(focus, p);
                ray.unit_vector();
                ray.rotateXYZ(rotation.x, rotation.y, rotation.z);
                frame[height - y - 1][x] = reflectRay(5, p, ray);
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