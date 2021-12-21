package Blue.Solids;

import java.awt.Color;
import java.util.List;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Rendering.Ray;

public class CustomSolid implements Solid, Callable {
    int index;
    Point rotation;
    Point center;
    double reflectivity;
    Color color;
    List<Triangle> triangles;

    public CustomSolid(String fileName, Color color, double reflectivity, int index) {
        this.triangles = load(fileName);
        this.color = color;
        this.index = index;
        this.reflectivity = reflectivity;
        this.rotation = new Point();
        this.center = new Point();
    }

    private List<Triangle> load(String fileName) {
        // Check extenstions and call corresponding load Functions
        return STL.load(fileName, 100.0);
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
    public Ray getReflectedRay(Ray ray) {
        if (ray.getIntersection() == null)
            return null;

        return triangles.get(ray.getHitSubIndex()).getReflectedRay(ray);
    }

    @Override
    public Ray getIntersectionPoint(Ray ray) {
        Vector initialIntersection = ray.getIntersection();

        for (Triangle t : triangles) {
            ray = t.getIntersectionPoint(ray);
        }

        if (initialIntersection != ray.getIntersection()) {
            ray.setHitIndex(index);
        }

        return ray;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getReflectivity() {
        return this.reflectivity;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.center = p;
        }

    }

}
