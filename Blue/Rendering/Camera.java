package Blue.Rendering;

import java.awt.*;
import java.util.List;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Solid;
import Blue.Solids.Sphere;
import Blue.Light.Sun;

public class Camera implements Callable {

    private static final double MINIMUM_ILLUMINATION = 0.5;
    private static final int MAX_BOUNCES = 2;

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

    private VectorIndex getIntersectionPoint(Point p, Vector u, int skipindex) {
        Vector intersectionPoint = null;

        int index = -1;

        for (int i = 0; i < scene.objects.size(); i++) {
            if (i == skipindex)
                continue;
            Vector ip = scene.objects.get(i).getIntersectionPoint(p, u);
            if (ip == null)
                continue;
            if ((intersectionPoint == null) || p.euclideanDistance(ip) < p.euclideanDistance(intersectionPoint)) {
                intersectionPoint = ip;
                index = i;
            }
        }

        return new VectorIndex(intersectionPoint, index);
    }

    // private Color reflectRayWithRefraction(int depth, int skipIndex, Point p, Vector ray) {
    //     Vector u = Vector.unitVector(ray);

    //     VectorIndex intersection = getIntersectionPoint(p, u, skipIndex);

    //     if (intersection.v == null) {
    //         return scene.getSkyBoxColor(u);
    //     }

    //     Point intersectionPoint = new Point(intersection.v);
    //     Solid curObj = scene.objects.get(intersection.index);
    //     Vector reflectedRay = curObj.getReflectedRay(intersection.v, u);

    //     Color reflectedRayColor;

    //     if (depth == 0) {
    //         reflectedRayColor = scene.getSkyBoxColor(u);
    //     } else {
    //         Color refractedRayColor = null;
    //         reflectedRayColor = reflectRayWithRefraction(depth - 1, intersection.index, intersectionPoint,
    //                 reflectedRay);
    //         if (curObj instanceof Sphere) {
    //             if (curObj.getRefractiveIndex() > 1.0) {
    //                 Vector normal = curObj.getNormal(intersection.v);
    //                 Vector perp = Vector.crossProduct(normal, u);
    //                 double incidenctAngle = Math.PI - Vector.angleBetween(normal, u);
    //                 double refractedAngle = Math.asin(Math.sin(incidenctAngle) / curObj.getRefractiveIndex());
    //                 double alpha = incidenctAngle - refractedAngle;
    //                 Vector refractedRay = u.copy();
    //                 refractedRay.scale(Math.cos(alpha));
    //                 Vector temp = Vector.crossProduct(u, perp);
    //                 temp.scale(Math.sin(alpha));
    //                 refractedRay.add(temp);

    //                 List<Vector> intersectionPoints = curObj.getAllIntersectionPoint(intersectionPoint, refractedRay);

    //                 if (intersectionPoints.get(1) != null) {
    //                     System.out.println(intersectionPoint.euclideanDistance(intersectionPoints.get(0))
    //                             + " should be close to zero");
    //                     System.out.println(intersectionPoint.euclideanDistance(intersectionPoints.get(1))
    //                             + " should be greater than 0, a reasonable distance");
    //                     normal = curObj.getNormal(intersectionPoints.get(1));
    //                     perp = Vector.crossProduct(normal, refractedRay);
    //                     incidenctAngle = Vector.angleBetween(normal, refractedRay);
    //                     System.out.println(Math.toDegrees(incidenctAngle) + " should be less than 90");
    //                     refractedAngle = Math.asin(Math.sin(incidenctAngle) * curObj.getRefractiveIndex());
    //                     System.out.println(Math.toDegrees(refractedAngle) + " should be greater than incident angle");
    //                     alpha = refractedAngle - incidenctAngle;
    //                     Vector tmp = refractedRay.copy();
    //                     refractedRay.scale(Math.cos(alpha));
    //                     temp = Vector.crossProduct(tmp, perp);
    //                     temp.scale(Math.sin(alpha));
    //                     refractedRay.add(temp);
    //                     refractedRayColor = reflectRayWithRefraction(depth - 1, intersection.index, intersectionPoint,
    //                             refractedRay);

    //                     reflectedRayColor = lerp(reflectedRayColor, refractedRayColor, 1.0 - curObj.getReflectivity());
    //                 }
    //             }
    //         }
    //     }

    //     double diffuseBrightness = getDiffuseBrightness(intersectionPoint, curObj.getNormal(intersection.v),
    //             intersection.index);

    //     double specularBrightness = getSpecularBrightness(reflectedRay) *
    //             curObj.getReflectivity();

    //     Color c = lerp(curObj.getColor(), reflectedRayColor, curObj.getReflectivity());
    //     c = scaleBrightness(diffuseBrightness, c);
    //     c = addBrightness(specularBrightness, c);

    //     return c;

    // }

    private Color reflectRay(int depth, int skipIndex, Point p, Vector ray) {
        Vector u = Vector.unitVector(ray);

        VectorIndex intersection = getIntersectionPoint(p, u, skipIndex);

        if (intersection.v == null) {
            // doesnt intersectin anything
            // if (depth == MAX_BOUNCES)
            // return new Color(255, 255, 255);
            return scene.getSkyBoxColor(u);
        }

        Point intersectionPoint = new Point(intersection.v);
        Solid curObj = scene.objects.get(intersection.index);
        Vector reflectedRay = curObj.getReflectedRay(intersection.v, u);

        Color reflectedRayColor;

        if (depth == 0) {
            reflectedRayColor = scene.getSkyBoxColor(u);
        } else {
            reflectedRayColor = reflectRay(depth - 1, intersection.index, intersectionPoint, reflectedRay);
        }

        double diffuseBrightness = getDiffuseBrightness(intersectionPoint, curObj.getNormal(intersection.v),
                intersection.index);

        double specularBrightness = getSpecularBrightness(reflectedRay) *
                curObj.getReflectivity();

        Color c = lerp(curObj.getColor(), reflectedRayColor, curObj.getReflectivity());
        c = scaleBrightness(diffuseBrightness, c);
        c = addBrightness(specularBrightness, c);

        return c;
    }

    private double getDiffuseBrightness(Point p, Vector normal, int skipIndex) {
        Vector dir = ((Sun) scene.lightSource).getDirection();
        dir.scale(-1);
        VectorIndex intersection = getIntersectionPoint(p, dir, skipIndex);
        if (intersection.v != null) {
            return MINIMUM_ILLUMINATION;
        }

        return Math.max(MINIMUM_ILLUMINATION, Math.cos(Vector.angleBetween(normal, dir)));
    }

    private double getSpecularBrightness(Vector reflectedRay) {
        Vector dir = ((Sun) scene.lightSource).getDirection();
        double angle = Vector.angleBetween(dir, reflectedRay);

        return Math.pow(Math.min(0, Math.cos(angle)), 20.0);
    }

    public Color[][] getFrame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x - deltaLocation.x, deltaLocation.y, y - deltaLocation.z);
                Vector ray = new Vector(focus, p);
                ray.unitVector();
                ray.rotateXYZ(rotation.x, rotation.y, rotation.z);
                frame[height - y - 1][x] = reflectRay(MAX_BOUNCES, -1, p, ray);
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