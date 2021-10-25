package Blue;

import java.awt.*;

public class LightSource implements Shape {
    double radius, r2;
    double intensity;
    Color color;
    Point center;

    public LightSource(Point center, double radius, double intensity) {
        this.center = center;
        this.radius = radius;
        this.r2 = radius * radius;
        this.intensity = intensity;
        this.color = new Color(0xFFFFFF);
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
        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        return (b * b - 4.0 * a * c) >= 0;
    }

    @Override
    public boolean do_intersect(Vector ray) {

        /*
         * here direction is given up ray
         */

        Vector u = Vector.unit_vector(ray);
        Point p = new Point(ray.i, ray.j, ray.k);

        return do_intersect(p, u);

        // Vector q = new Vector(center, p);

        // double a = u.magnitude * u.magnitude;
        // double b = 2.0 * Vector.dot_product(u, q);
        // double c = q.magnitude * q.magnitude - r2;

        // double d = (b * b - 4.0 * a * c);

        // if (d < 0)
        // return false;

        // d = Math.sqrt(d);

        // double x1 = (-b + d) / (2.0 * a);
        // double x2 = (-b - d) / (2.0 * a);

        // if (x1 < 0 && x2 < 0)
        // return false;

        // return true;

        // System.out.println(b ? "True" : "False");
    }

    @Override
    public Vector get_refeclected_ray(Point p, Vector u) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color get_color() {
        return this.color;
    }

    @Override
    public String toString() {
        return "{LightSource ; radius: +" + radius + " , center : " + center + " , intensisty : " + intensity + "}";
    }
}