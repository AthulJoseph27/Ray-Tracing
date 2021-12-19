package Blue.Solids;

import java.awt.Color;
import java.util.List;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class CustomSolid implements Solid {

    List<Triangle> triangles;
    double reflectivity, refractiveIndex;

    CustomSolid(String fileName, double reflectivity) {

    }

    @Override
    public boolean isInside(Point p) {
        return false;
    }

    @Override
    public boolean isOnSurface(Point p) {
        return false;
    }

    @Override
    public boolean doIntersect(Point pointOnScreen, Vector ray) {
        return false;
    }

    @Override
    public Vector getNormal(Vector intersection) {
        return null;
    }

    @Override
    public Vector getReflectedRay(Vector intersectionPoint, Vector ray) {
        return null;
    }

    @Override
    public Vector getIntersectionPoint(Point p, Vector u) {
        return null;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public double getReflectivity() {
        return 0;
    }

    @Override
    public double getRefractiveIndex() {
        return 0;
    }

    @Override
    public List<Vector> getAllIntersectionPoint(Point p, Vector u) {
        return null;
    }

}
