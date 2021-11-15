package Blue.Solids;

import java.awt.*;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class Sphere implements Solid, Callable {

    public double radius, r2;
    public Point center;
    public Color color;
    public double reflectivity = 1.0;

    public Sphere(double radius, Point center, double reflectivity) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.reflectivity = reflectivity;
        color = new Color(0xFFFFFF);
    }

    public Sphere(double radius, Point center, Color color, double reflectivity) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.color = color;
        this.reflectivity = reflectivity;
    }

    @Override
    public boolean isInside(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.getMagnitude();
        magnitude *= magnitude;
        return magnitude <= r2;
    }

    @Override
    public boolean isOnSurface(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.getMagnitude();
        magnitude *= magnitude;
        return magnitude == r2;
    }

    @Override
    public boolean doIntersect(Point p, Vector ray) {

        /*
         * A ray passing through point P and direction vector U can be represented as
         * 
         * R(t) = P + tU
         * 
         * |X-C|^2 = r^2 ; C is center of the Sphere and r is radius of the sphere
         * 
         * substituting X with R(t)
         * 
         * |P-C|^2 - r^2 + 2tU * (P-C) + t^2(U.U) = 0
         * 
         * a = |U.U| b = 2*|U.(P-C)| c = |P-C|^2 - r^2
         * 
         * d = b^2 - 4ac ; --> d >= 0
         */

        Vector u = Vector.unitVector(ray);

        Vector q = new Vector(center, p);

        double q_magnitude = q.getMagnitude();
        double b = 2.0 * Vector.dotProduct(u, q);
        double c = q_magnitude * q_magnitude - r2;

        double d = (b * b - 4.0 * c); // b^2 - 4ac, here a = 1.0

        if (d < 0)
            return false;

        d = Math.sqrt(d);

        double x1 = (-b + d) / 2.0;
        double x2 = (-b - d) / 2.0;

        if (x1 < 0 && x2 < 0) {
            return false;
        }

        return true;
    }

    @Override
    public Vector getIntersectionPoint(Point p, Vector ray) {
        Vector u = Vector.unitVector(ray);

        Vector q = new Vector(center, p);
        double q_magnitude = q.getMagnitude();
        double b = 2.0 * Vector.dotProduct(u, q);
        double c = q_magnitude * q_magnitude - r2;

        double d = (b * b - 4.0 * c);

        if (d < 0)
            return null;

        d = Math.sqrt(d);

        double x1 = (-b + d) / 2.0;
        double x2 = (-b - d) / 2.0;

        if (x1 < 0 && x2 < 0) {
            return null;
        }

        Vector p1 = new Vector(new Point(), p), p2 = new Vector(new Point(), p);

        Vector t = u.copy();
        t.scale(x1);
        p1.add(t);

        t = u.copy();
        t.scale(x2);
        p2.add(t);

        if (x1 < 0)
            return p2;
        if (x2 < 0)
            return p1;

        if (p.euclideanDistance(p1) < p.euclideanDistance(p2))
            return p1;

        return p2;
    }

    @Override
    public Vector getReflectedRay(Vector intersection_point, Vector ray) {

        Vector normal = new Vector(this.center, intersection_point);

        Vector n = Vector.unitVector(normal);
        Vector d = Vector.unitVector(ray);

        // Reflected ray = d - 2*(d.n)n^

        n.scale(2.0 * Vector.dotProduct(n, d));

        d.substract(n);

        return d;
    }

    @Override
    public Vector getNormal(Vector intersection) {
        return new Vector(this.center, intersection);
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.center = p;
        }

    }

    @Override
    public double getReflectivity() {
        return reflectivity;
    }

    @Override
    public String toString() {
        return "{Shape : Sphere; radius: +" + radius + " , center : " + center + "}";
    }

}