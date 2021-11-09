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
    public double get_brightness(Point p, Vector obj_normal, Vector reflected_ray, double distance_to_nearest_object) {
        Vector intersections = get_intersection_point(p, reflected_ray);

        if (intersections == null)
            return 0.0;

        double angle = Vector.angle_between(obj_normal, reflected_ray);
        if (angle >= (Math.PI / 2.0))
            return 0.0;

        return 1.0 - angle / (Math.PI / 2.0);
    }

    @Override
    public String toString() {
        return "{LightSource ; radius: +" + radius + " , center : " + center + " , intensisty : " + intensity + "}";
    }

}