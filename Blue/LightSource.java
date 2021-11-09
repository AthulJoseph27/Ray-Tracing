package Blue;

public interface LightSource {

    public double get_brightness(Point p, Vector obj_normal, Vector reflected_ray, double distance_to_nearest_object);
}
