package Blue;

import java.awt.Color;

public class AreaLight extends Plane implements LightSource {

    public AreaLight(int width, int height, Point center, double rx, double ry, double rz, Color color) {
        super(width, height, center, rx, ry, rz, color, -1);

    }

    @Override
    public double get_brightness(Point p, Vector normal, Vector reflected_ray, double distance_to_nearest_object) {
        Vector intersection = get_intersection_point(p, reflected_ray);

        if (intersection == null)
            return 0.0;

        // System.out.println(p.euclidean_distance(intersection) + " " +
        // distance_to_nearest_object);

        if (p.euclidean_distance(intersection) > distance_to_nearest_object)
            return 0.0;

        double angle = Vector.angle_between(normal, reflected_ray);

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        throw new UnsupportedOperationException();
    }

}
