package Blue;

import java.awt.Color;

public class AreaLight extends Plane implements LightSource {

    public AreaLight(int width, int height, Point center, double rx, double ry, double rz, Color color) {
        super(width, height, center, rx, ry, rz, color);

    }

    @Override
    public double get_brightness(Point p, Vector normal, Vector ray, double distance_to_nearest_object) {
        Vector[] intersections = get_intersection_point(p, ray);
        if (intersections == null)
            return 0.0;

        if (intersections[0] == null && intersections[1] == null)
            return 0.0;

        if (p.euclidean_distance(Point.get_closest_point(p, intersections)) > distance_to_nearest_object)
            return 0.0;

        double angle = Vector.angle_between(normal, ray);

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        throw new UnsupportedOperationException();
    }

}
