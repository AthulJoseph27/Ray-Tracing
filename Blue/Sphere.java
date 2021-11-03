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
        double magnitude = v.magnitude;
        magnitude *= magnitude;
        return magnitude <= r2;
    }

    @Override
    public boolean is_on_surface(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.magnitude;
        magnitude *= magnitude;
        return magnitude == r2;
    }

    @Override
    public boolean do_intersect(Point p, Vector u) {

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

        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        return (b * b - 4.0 * a * c) >= 0;
    }

    @Override
    public boolean do_intersect(Vector ray) {
        Vector u = Vector.unit_vector(ray);
        Point p = new Point(ray.i, ray.j, ray.k);

        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        double d = (b * b - 4.0 * a * c);

        if (d < 0)
            return false;

        d = Math.sqrt(d);

        double x1 = (-b + d) / (2.0 * a);
        double x2 = (-b - d) / (2.0 * a);

        if (x1 < 0 && x2 < 0)
            return false;

        return true;
    }

    @Override
    public Vector[] get_intersection_point(Point p, Vector u) {

        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        double d = (b * b - 4.0 * a * c);

        if (d < 0)
            return null;

        d = Math.sqrt(d);

        double x1 = (-b + d) / (2.0 * a);
        double x2 = (-b - d) / (2.0 * a);

        if (x1 < 0 && x2 < 0)
            return null;

        Vector p1 = new Vector(new Point(), p), p2 = new Vector(new Point(), p);

        Vector temp = u.copy();

        temp.scale(x1);

        p1.add(temp);

        temp.unit_vector();
        temp.scale(x2);

        p2.add(temp);

        if (x1 < 0)
            p1 = null;
        if (x2 < 0)
            p2 = null;

        return new Vector[] { p1, p1 };

        // Vector q = new Vector(center, p);

        // double a = u.magnitude * u.magnitude;
        // double b = 2.0 * Vector.dot_product(u, q);
        // double c = q.magnitude * q.magnitude - r2;

        // double d = b * b - 4.0 * a * c;

        // Vector r = new Vector(new Point(), p);
        // r.add(u);
        // r.unit_vector();

        // if (d < 0) {
        // /*
        // * (p.x,p.y,p.z) + t(u.i,u.j,u.k)
        // *
        // * let t = 1, we get a vector....then scale it up/down to unit vector
        // */

        // return r;
        // }

        // d = Math.sqrt(d);

        // double x1 = (-b + d) / (2.0 * a);
        // double x2 = (-b - d) / (2.0 * a);

        // if (x1 < 0 && x2 < 0)
        // return r;

        // Vector p1 = new Vector(new Point(), p);
        // Vector p2 = new Vector(new Point(), p);

        // Vector pi = null;

        // Vector temp_u = u.copy();

        // temp_u.scale(x2);
        // p1.add(temp_u);
        // p1.unit_vector();

        // temp_u.unit_vector();
        // temp_u.scale(x1);
        // p2.add(temp_u);
        // p2.unit_vector();

        // if (x1 < 0 && x2 < 0) {
        // return null;
        // }

        // if (x1 < 0) {
        // pi = p2;
        // } else if (x2 < 0) {
        // pi = p1;
        // }

        // Vector p_temp = new Vector(new Point(), p);

        // if (p_temp.euclidean_distance(p1) < p_temp.euclidean_distance(p2))
        // pi = p1;
        // else
        // pi = p2;

        // // System.out.println(pi);
        // Vector normal = new Vector(center, new Point(pi.i, pi.j, pi.k));
        // normal.unit_vector();
        // // System.out.println(normal);

        // normal.scale(2 * Vector.dot_product(pi, normal));
        // pi.substract(normal);
        // pi.unit_vector();
        // // System.out.println(pi);

        // // System.out.println();
        // return pi;

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
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        Vector n = normal.copy();
        Vector d = ray.copy();

        // Reflected ray = d - 2*(d.n)n^

        n.scale(2.0 * Vector.dot_product(n, d));

        d.substract(n);

        return d;
    }

    @Override
    public String toString() {
        return "{Shape : Sphere; radius: +" + radius + " , center : " + center + "}";
    }

}