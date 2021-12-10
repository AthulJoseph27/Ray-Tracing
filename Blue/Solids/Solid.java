package Blue.Solids;

import java.awt.*;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public interface Solid {

    public boolean isInside(Point p);

    public boolean isOnSurface(Point p);

    public boolean doIntersect(Point pointOnScreen, Vector ray);

    public Vector getNormal(Vector intersection);

    public Vector getReflectedRay(Vector intersectionPoint, Vector ray);

    public Vector getIntersectionPoint(Point p, Vector u);

    public Color getColor();

    public double getReflectivity();

}