package Blue;

import java.awt.Color;

public class Sun implements Solid, LightSource, Callable {
    Color color;
    Point center;
    Vector normal;

    public Sun(Point center) {
        this.center = center;
        this.normal = new Vector(new Point(), center);
        color = Color.WHITE;
    }

    @Override
    public double get_brightness(Point p, Vector obj_normal, Vector reflected_ray, double distance_to_nearest_object) {
        if (Vector.angle_between(this.normal, reflected_ray) > (Math.PI / 2.0))
            return 0.0;

        double angle = Vector.angle_between(obj_normal, reflected_ray);

        if (distance_to_nearest_object != Double.MAX_VALUE)
            return 0.0;

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public boolean is_inside(Point p) {
        return false;
    }

    @Override
    public boolean is_on_surface(Point p) {
        return false;
    }

    @Override
    public boolean do_intersect(Point point_on_screen, Vector ray) {
        return true;
    }

    @Override
    public Vector get_normal(Vector intersection) {
        return normal;
    }

    @Override
    public Vector get_reflected_ray(Vector intersection_point, Vector ray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector get_intersection_point(Point p, Vector u) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color get_color() {
        return this.color;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.center = p;
        }
    }

    @Override
    public double get_reflectivity() {
        throw new UnsupportedOperationException();
    }

}
