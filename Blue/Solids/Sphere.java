package Blue.Solids;

import java.awt.*;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Rendering.Ray;

public class Sphere implements Solid, Callable {

    public double radius, r2;
    public Point center;
    public Color color;
    public double reflectivity = 1.0;
    public double refractiveIndex = -1;
    private int index;

    public Sphere(double radius, Point center, double reflectivity, int index) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.reflectivity = reflectivity;
        this.index = index;
        color = new Color(0xFFFFFF);
    }

    public Sphere(double radius, Point center, double reflectivity, double refractiveIndex, int index) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.reflectivity = reflectivity;
        this.refractiveIndex = refractiveIndex;
        this.index = index;
        color = new Color(0xFFFFFF);
    }

    public Sphere(double radius, Point center, Color color, double reflectivity, int index) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.color = color;
        this.reflectivity = reflectivity;
        this.index = index;
    }

    public Sphere(double radius, Point center, Color color, double reflectivity, double refractiveIndex, int index) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.color = color;
        this.reflectivity = reflectivity;
        this.refractiveIndex = refractiveIndex;
        this.index = index;
    }

    private boolean validIntersection(Ray ray, Vector intersection) {
        return ((ray.getIntersection() == null)
                || (ray.getOrigin().euclideanDistance(ray.getIntersection()) > ray.getOrigin()
                        .euclideanDistance(intersection)));
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
    public Ray getIntersectionPoint(Ray ray) {
        if (ray.getOrginIndex() == index)
            return ray;

        Vector u = Vector.unitVector(ray.getRay());

        Vector q = new Vector(center, ray.getOrigin());
        double q_magnitude = q.getMagnitude();
        double b = 2.0 * Vector.dotProduct(u, q);
        double c = q_magnitude * q_magnitude - r2;

        double d = (b * b - 4.0 * c);

        if (d < 0)
            return ray;

        d = Math.sqrt(d);

        double x1 = (-b + d) / 2.0;
        double x2 = (-b - d) / 2.0;

        if (x1 < 0 && x2 < 0) {
            return ray;
        }

        Vector p1 = new Vector(new Point(), ray.getOrigin()), p2 = new Vector(new Point(), ray.getOrigin());

        Vector t = u.copy();
        t.scale(x1);
        p1.add(t);

        t = u.copy();
        t.scale(x2);
        p2.add(t);

        if (x1 < 0) {
            if (validIntersection(ray, p2)) {
                ray.setHitSubIndex(-1);
                ray.setHitIndex(index);
                ray.setIntersection(p2);
                ray.setNormal(new Vector(this.center, p2));
            }
            return ray;
        }
        if (x2 < 0) {
            if (validIntersection(ray, p1)) {
                ray.setHitSubIndex(-1);
                ray.setHitIndex(index);
                ray.setIntersection(p1);
                ray.setNormal(new Vector(this.center, p1));
            }
            return ray;
        }

        if (ray.getOrigin().euclideanDistance(p1) < ray.getOrigin().euclideanDistance(p2)) {
            if (validIntersection(ray, p1)) {
                ray.setHitSubIndex(-1);
                ray.setHitIndex(index);
                ray.setIntersection(p1);
                ray.setNormal(new Vector(this.center, p1));
            }
        } else {
            if (validIntersection(ray, p2)) {
                ray.setHitSubIndex(-1);
                ray.setHitIndex(index);
                ray.setIntersection(p2);
                ray.setNormal(new Vector(this.center, p2));
            }
        }

        return ray;
    }

    @Override
    public Ray getReflectedRay(Ray ray) {
        if (ray.getIntersection() == null)
            return ray;

        Vector d = Vector.unitVector(ray.getRay());

        Vector n = Vector.unitVector(ray.getNormal());

        n.scale(2.0 * Vector.dotProduct(d, n));

        d.substract(n);

        return new Ray(new Point(ray.getIntersection()), d, ray.getHitIndex(), ray.getHitSubIndex());
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