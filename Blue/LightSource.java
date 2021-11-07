package Blue;

public interface LightSource {

    public double get_brightness(Point p, Vector normal, Vector ray, double distance_to_nearest_object);
}
