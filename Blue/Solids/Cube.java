package Blue.Solids;

import java.awt.Color;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class Cube implements Callable, Solid {

    public Color color;
    public int a;
    public Point center;
    public Vector rotation;
    public double reflectivity;
    private Vector[] normals;
    private Point[] centers;
    private Plane[] faces;
    private int lastIntersectionIndex = -1;

    public Cube(int a, Point center, Color color, double reflectivity) {
        this.a = a;
        this.center = center;
        this.color = color;
        this.reflectivity = reflectivity;
        this.rotation = new Vector();
        this.normals = getNormals();
        this.centers = getCenters();
        this.faces = getFaces();
    }

    private Plane[] getFaces() {
        Plane[] faces = {
                new Plane(a, a, centers[0], 0.0, 0.0, 0.0, color, reflectivity),
                new Plane(a, a, centers[1], 0.0, 0.0, Math.PI / 2.0, color, reflectivity),
                new Plane(a, a, centers[2], 0.0, 0.0, Math.PI, color, reflectivity),
                new Plane(a, a, centers[3], 0.0, 0.0, -Math.PI / 2.0, color, reflectivity),
                new Plane(a, a, centers[4], Math.PI / 2.0, 0.0, 0.0, color, reflectivity),
                new Plane(a, a, centers[5], -Math.PI / 2.0, 0.0, 0.0, color, reflectivity)
        };

        // Rotate the faces here

        return faces;
    }

    private Vector[] getNormals() {
        Vector[] n = {
                new Vector(new Point(), new Point(1, 0, 0)),
                new Vector(new Point(), new Point(-1, 0, 0)),

                new Vector(new Point(), new Point(0, 1, 0)),
                new Vector(new Point(), new Point(0, -1, 0)),

                new Vector(new Point(), new Point(0, 0, 1)),
                new Vector(new Point(), new Point(0, 0, -1)),
        };

        for (int i = 0; i < 6; i++) {
            n[i].rotateX(rotation.i);
            n[i].rotateY(rotation.j);
            n[i].rotateZ(rotation.k);
        }

        return n;
    }

    private Point[] getCenters() {
        Point[] c = new Point[6];

        for (int i = 0; i < 6; i++) {
            Vector v = normals[i].copy();
            v.scale(a / 2.0);
            v.add(new Vector(new Point(), center));
            c[i] = new Point(v.i, v.j, v.k);
        }

        return c;
    }

    // private Vector[] getRefDirections(){

    // }

    @Override
    public boolean isInside(Point p) {
        return false;
    }

    @Override
    public boolean isOnSurface(Point p) {
        return false;
    }

    @Override
    public boolean doIntersect(Point p, Vector u) {
        return getIntersectionPoint(p, u) == null;
    }

    @Override
    public Vector getNormal(Vector intersection) {
        if (lastIntersectionIndex != -1)
            return faces[lastIntersectionIndex].normal;
        return null;
    }

    @Override
    public Vector getReflectedRay(Vector intersectionPoint, Vector ray) {
        if (lastIntersectionIndex != -1)
            return faces[lastIntersectionIndex].getReflectedRay(intersectionPoint, ray);
        return null;
    }

    @Override
    public Vector getIntersectionPoint(Point p, Vector u) {
        Vector[] intersectionPoints = new Vector[6];

        for (int i = 0; i < 6; i++) {
            intersectionPoints[i] = faces[i].getIntersectionPoint(p, u);
        }

        Vector result = null;

        for (int i = 0; i < 6; i++) {
            if (intersectionPoints[i] != null) {
                if (result == null) {
                    lastIntersectionIndex = i;
                    result = intersectionPoints[i];
                    continue;
                }
                if (p.euclideanDistance(result) > p.euclideanDistance(intersectionPoints[i])) {
                    lastIntersectionIndex = i;
                    result = intersectionPoints[i];
                }
            }
        }

        return result;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getReflectivity() {
        return this.reflectivity;
    }

    @Override
    public void transform(Point p, String parameterName) {
    }

}
