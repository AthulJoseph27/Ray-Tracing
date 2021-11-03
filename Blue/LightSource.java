package Blue;

import java.awt.*;

public class LightSource implements Shape, Callable {
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
        // check if the ray hits the center

        // u can be considered a point, p can be considered a point and center of sphere
        // another point
        // slope pu = slope uc, ie , unit vector will be identical

        Vector v1 = new Vector(p, new Point(u.i, u.j, u.k));

        Vector v2 = new Vector(new Point(u.i, u.j, u.k), center);

        // angle btw v1, v2 should be 0 or 180

        double angle = Vector.angle_between(v1, v2);

        if (Math.abs(angle) < 0.0001 || Math.abs((Math.abs(angle) - Math.PI)) < 0.0001)
            return true;

        return false;

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

    public double get_brightness(Point p, Vector ray) {

        Vector u = ray.copy();
        u.unit_vector();

        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        double d = (b * b - 4.0 * a * c);

        if (d < 0)
            return 0.0;

        d = Math.sqrt(d);

        double x1 = (-b + d) / (2.0 * a);
        double x2 = (-b - d) / (2.0 * a);

        if (x1 < 0 && x2 < 0)
            return 0.0;

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

        Vector p_intersection = p1;

        if (p1 != null && p2 != null) {
            if (p.euclidean_distance(new Point(p1.i, p1.j, p1.k)) > p.euclidean_distance(new Point(p2.i, p2.j, p2.k))) {
                p_intersection = p2;
            } else {
                p_intersection = p1;
            }
        } else {
            if (p_intersection == null)
                p_intersection = p2;

            if (p_intersection == null) {
                return 0.0;
            }
        }

        Vector normal = new Vector(center, new Point(p_intersection.i, p_intersection.j, p_intersection.k));

        double angle = Math.PI - Vector.angle_between(normal, ray);
        if (angle > (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public Vector[] get_intersection_point(Point p, Vector u) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "{LightSource ; radius: +" + radius + " , center : " + center + " , intensisty : " + intensity + "}";
    }

}