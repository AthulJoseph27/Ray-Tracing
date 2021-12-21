package Blue.Solids;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import Blue.Geometry.Limit;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Rendering.Ray;

public class Triangle implements Solid {

    Point[] vertices = new Point[3];
    Point center;
    Vector normal;
    double reflectivity, refractivity;
    Color color;
    int index = -1;

    public Triangle(Point a, Point b, Point c, Vector normal, int index) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.normal = normal;
        this.reflectivity = 0;
        this.index = index;
        this.center = getCenter();
        this.color = Color.WHITE;
    }

    public Triangle(Point a, Point b, Point c, Vector normal, double reflectivity, int index) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.normal = normal;
        this.center = getCenter();
        this.index = index;
        this.reflectivity = reflectivity;
        this.color = Color.WHITE;
    }

    private Point getCenter() {
        Point p = new Point();

        for (int i = 0; i < 3; i++) {
            p.x += vertices[i].x;
            p.y += vertices[i].y;
            p.z += vertices[i].z;

        }

        p.x /= 3.0;
        p.y /= 3.0;
        p.z /= 3.0;

        return p;
    }

    private boolean isInsideTriangle(Vector p) {
        Vector AB = new Vector(vertices[0], vertices[1]);
        Vector BC = new Vector(vertices[1], vertices[2]);
        Vector CA = new Vector(vertices[2], vertices[0]);

        Vector AP = new Vector(vertices[0], p);
        Vector BP = new Vector(vertices[1], p);
        Vector CP = new Vector(vertices[2], p);

        Vector u = Vector.unitVector(Vector.crossProduct(AB, AP));
        Vector v = Vector.unitVector(Vector.crossProduct(BC, BP));
        Vector w = Vector.unitVector(Vector.crossProduct(CA, CP));

        double dir1 = Vector.dotProduct(u, v);
        double dir2 = Vector.dotProduct(v, w);

        // both should be 1

        return (Math.abs(1.0 - dir1) <= Limit.ERROR_LIMIT && Math.abs(1.0 - dir2) <= Limit.ERROR_LIMIT);

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
            return ray;

        Vector d = Vector.unitVector(ray.getRay());

        Vector n = Vector.unitVector(ray.getNormal());

        n.scale(2.0 * Vector.dotProduct(d, n));

        d.substract(n);

        return new Ray(new Point(ray.getIntersection()), d, ray.getHitIndex(), ray.getHitSubIndex());
    }

    @Override
    public Ray getIntersectionPoint(Ray ray) {
        /*
         * Vector passing through p and direction u => V = p + t*u; CV.N = 0
         * n.x*p.x+u.x*t*n.x-c.x*n.x + n.y*p.y+u.y*t*n.y-c.y*n.y +
         * n.z*p.z+u.z*t*n.z-c.z*n.z = 0
         * 
         * t(n.u) + n.p = c.n R = c.n; Q = n.p; S = u.p; t = (R-Q)/S
         */

        Vector u = Vector.unitVector(ray.getRay().copy());

        Vector c = new Vector(new Point(), center);
        double R = Vector.dotProduct(normal, c);
        double Q = Vector.dotProduct(normal, new Vector(new Point(), ray.getOrigin()));
        double S = Vector.dotProduct(normal, u);

        if (S == 0) {
            return null;
        }

        double t = (R - Q) / S;

        if (t < 0)
            return null;

        Vector intersection = u.copy();

        intersection.scale(t);
        intersection.add(new Vector(new Point(), ray.getOrigin()));

        if (isInsideTriangle(intersection)) {
            if ((ray.getIntersection() == null)
                    || (ray.getOrigin().euclideanDistance(ray.getIntersection()) > ray.getOrigin()
                            .euclideanDistance(intersection))) {
                ray.setHitSubIndex(index);
                ray.setIntersection(intersection);
                ray.setNormal(this.normal.copy());
            }
        }

        return ray;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getReflectivity() {
        return reflectivity;
    }

    @Override
    public String toString() {
        return "{" +
                " vertices='" + Arrays.toString(vertices) + "'" +
                ", normal='" + normal + "'" +
                "}";
    }

}
