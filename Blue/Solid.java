package Blue;

import java.awt.*;

interface Solid {

    public boolean isInside(Point p);

    public boolean isOnSurface(Point p);

    public boolean doIntersect(Point point_on_screen, Vector ray);

    public Vector getNormal(Vector intersection);

    public Vector getReflectedRay(Vector intersection_point, Vector ray);

    public Vector getIntersectionPoint(Point p, Vector u);

    public Color getColor();

    public double getReflectivity();

}