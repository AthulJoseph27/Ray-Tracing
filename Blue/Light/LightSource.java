package Blue.Light;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public interface LightSource {

    public double getDiffuseBrightness(Point p, Vector obj_normal, Vector reflected_ray,
            double distance_to_nearest_object);

    public double getSpecularBrightness(Vector reflected_ray, double distance_to_nearest_object);
}
