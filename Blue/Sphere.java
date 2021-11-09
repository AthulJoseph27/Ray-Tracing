package Blue;

import java.awt.*;

public class Sphere implements Shape, Callable {

    public double radius, r2;
    public Point center;
    public Color color;

    public Sphere(double radius, Point center) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        color = new Color(0xFFFFFF);
    }

    public Sphere(double radius, Point center, Color color) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
        this.color = color;
    }

    @Override
    public boolean is_inside(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.get_magnitude();
        magnitude *= magnitude;
        return magnitude <= r2;
    }

    @Override
    public boolean is_on_surface(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.get_magnitude();
        magnitude *= magnitude;
        return magnitude == r2;
    }

    @Override
    public boolean do_intersect(Point p, Vector ray) {

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

        Vector u = Vector.unit_vector(ray);

        Vector q = new Vector(center, p);

        double q_magnitude = q.get_magnitude();
        double b = 2.0 * Vector.dot_product(u, q);
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
    public Vector get_intersection_point(Point p, Vector ray) {
        Vector u = Vector.unit_vector(ray);

        Vector q = new Vector(center, p);
        double q_magnitude = q.get_magnitude();
        double b = 2.0 * Vector.dot_product(u, q);
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

        double x = Math.min(x1, x2);

        Vector pi = new Vector(new Point(), p);

        Vector t = u.copy();
        t.scale(x);

        pi.add(t);

        return pi;
    }

    @Override
    public Vector get_reflected_ray(Vector intersection_point, Vector ray) {

        Vector normal = new Vector(this.center, intersection_point);

        Vector n = Vector.unit_vector(normal);
        Vector d = Vector.unit_vector(ray);

        // Reflected ray = d - 2*(d.n)n^

        n.scale(2.0 * Vector.dot_product(n, d));

        d.substract(n);

        return d;
    }

    @Override
    public Vector get_normal(Vector intersection) {
        return new Vector(this.center, intersection);
    }

    @Override
    public Color get_color() {
        return this.color;
    }

    @Override
    public void call(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.center = p;
        }

    }

    @Override
    public String toString() {
        return "{Shape : Sphere; radius: +" + radius + " , center : " + center + "}";
    }

}