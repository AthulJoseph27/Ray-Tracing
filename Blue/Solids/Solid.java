package Blue.Solids;

import java.awt.*;

import Blue.Geometry.Point;
import Blue.Rendering.Ray;

public interface Solid {

    public boolean isInside(Point p);

    public boolean isOnSurface(Point p);

    public Ray getReflectedRay(Ray ray);

    public Ray getIntersectionPoint(Ray ray);

    public Color getColor();

    public double getReflectivity();

}