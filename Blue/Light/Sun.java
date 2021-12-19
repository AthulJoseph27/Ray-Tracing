package Blue.Light;

import java.awt.Color;
import java.util.List;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Solid;

public class Sun implements Solid, LightSource, Callable {
    Color color;
    public Point center;
    Vector normal;

    public Sun(Point center) {
        this.center = center;
        this.normal = new Vector(new Point(), center);
        this.normal.unitVector();
        color = Color.WHITE;
    }

    @Override
    public double getDiffuseBrightness(Point p, Vector objNormal, Vector reflectedRay,
            double distance_to_nearest_object) {

        // System.out.println(Math.toDegrees(Vector.angleBetween(this.normal,
        // reflected_ray)));
        // if (Vector.angleBetween(this.normal, reflected_ray) < (Math.PI / 2.0))
        // return 0.0;

        double angle = Vector.angleBetween(objNormal, reflectedRay);

        // if (distance_to_nearest_object != Double.MAX_VALUE)
        // return 0.0;

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        double factor = 1.0 - angle / (Math.PI / 2.0);

        return factor;
    }

    public Vector getDirection() {
        return normal.copy();
    }

    @Override
    public double getSpecularBrightness(Vector reflectedRay, double distanceToNearestObject) {
        // if (distance_to_nearest_object != Double.MAX_VALUE)
        // return 0.0;

        double angle = Vector.angleBetween(this.normal, reflectedRay);
        if (angle <= (Math.PI / 2.0))
            return 0;

        angle = Math.PI - angle;

        return Math.pow(1 - angle / (Math.PI / 2.0), 2);
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
    public boolean doIntersect(Point point_on_screen, Vector ray) {
        return true;
    }

    @Override
    public Vector getNormal(Vector intersection) {
        return normal;
    }

    @Override
    public Vector getReflectedRay(Vector intersection_point, Vector ray) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Vector getIntersectionPoint(Point p, Vector u) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.compareTo("rotation") == 0) {
            this.normal = new Vector(new Point(), p);
            this.normal.unitVector();
        }
    }

    @Override
    public double getReflectivity() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getRefractiveIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Vector> getAllIntersectionPoint(Point p, Vector u) {
        throw new UnsupportedOperationException();
    }

}
