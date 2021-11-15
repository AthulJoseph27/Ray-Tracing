package Blue.Light;

import java.awt.Color;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Solid;

public class Sun implements Solid, LightSource, Callable {
    Color color;
    Point center;
    Vector normal;

    public Sun(Point center) {
        this.center = center;
        this.normal = new Vector(new Point(), center);
        color = Color.WHITE;
    }

    @Override
    public double getDiffuseBrightness(Point p, Vector obj_normal, Vector reflected_ray,
            double distance_to_nearest_object) {

        // System.out.println(Math.toDegrees(Vector.angleBetween(this.normal,
        // reflected_ray)));
        // if (Vector.angleBetween(this.normal, reflected_ray) < (Math.PI / 2.0))
        // return 0.0;

        double angle = Vector.angleBetween(obj_normal, reflected_ray);

        if (distance_to_nearest_object != Double.MAX_VALUE)
            return 0.0;

        if (angle >= (Math.PI / 2.0))
            return 0.0;

        double factor = 1.0 - angle / (Math.PI / 2.0);

        return factor;
        // factor *= brightness;

        // // Specular Reflections

        // factor += (1.0 - Vector.angleBetween(normal, reflected_ray) / Math.PI) /
        // 10.0;

        // return Math.min(factor, 1.0);
    }

    @Override
    public double getSpecularBrightness(Vector reflected_ray, double distance_to_nearest_object) {
        if (distance_to_nearest_object != Double.MAX_VALUE)
            return 0.0;

        double angle = Vector.angleBetween(this.normal, reflected_ray);
        if (angle <= (Math.PI / 2.0))
            return 0;

        angle = Math.PI - angle;

        return 1 - angle / (Math.PI / 2.0);
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
        if (type.compareTo("center") == 0) {
            this.center = p;
        }
    }

    @Override
    public double getReflectivity() {
        throw new UnsupportedOperationException();
    }

}
