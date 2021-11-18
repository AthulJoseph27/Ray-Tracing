package Blue.Light;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public interface LightSource {

    public double getDiffuseBrightness(Point p, Vector objNormal, Vector reflectedRay, double distanceToNearestObject);

    public double getSpecularBrightness(Vector reflected_ray, double distanceToNearestObject);

}
