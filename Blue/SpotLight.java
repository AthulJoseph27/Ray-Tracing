package Blue;

import java.awt.*;

public class SpotLight extends Sphere implements LightSource {
    double intensity;

    public SpotLight(Point center, double radius, double intensity) {
        super(radius, center, new Color(0xFFFFFF));
        this.intensity = intensity;
    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double get_brightness(Point p, Vector normal, Vector ray, double distance_to_nearest_object) {
        Vector[] intersections = get_intersection_point(p, ray);

        if (intersections == null)
            return 0.0;

        if (intersections[0] == null && intersections[1] == null)
            return 0.0;

        // System.out.println(
        // p.euclidean_distance(Point.get_closest_point(p, intersections)) + " " +
        // distance_to_nearest_object);
        // if (p.euclidean_distance(Point.get_closest_point(p, intersections)) >
        // distance_to_nearest_object)
        // return 0.0;

        double angle = Vector.angle_between(normal, ray);

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public String toString() {
        return "{LightSource ; radius: +" + radius + " , center : " + center + " , intensisty : " + intensity + "}";
    }

}