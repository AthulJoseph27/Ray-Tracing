package Blue.Solids;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class Triangle implements Solid {

    Point[] vertices = new Point[3];
    Vector normal;
    double reflectivity, refractivity;
    Color color;

    public Triangle(Point a, Point b, Point c, Vector normal) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.normal = normal;
        this.reflectivity = 0;
        this.color = Color.WHITE;
    }

    public Triangle(Point a, Point b, Point c, Vector normal, double reflectivity) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.normal = normal;
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
        Vector a = new Vector(new Point(), vertices[0]);
        Vector b = new Vector(new Point(), vertices[1]);
        Vector c = new Vector(new Point(), vertices[2]);

        // Making p the origin
        a.substract(p);
        b.substract(p);
        c.substract(p);

        Vector u = Vector.crossProduct(b, c);
        Vector v = Vector.crossProduct(c, a);
        Vector w = Vector.crossProduct(a, b);

        if (Vector.dotProduct(u, v) < 0.0) {
            return false;
        }

        if (Vector.dotProduct(u, w) < 0.0) {
            return false;
        }

        return true;

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
    public boolean doIntersect(Point p, Vector u) {
        Vector intersection = getIntersectionPoint(p, u);

        if (intersection == null)
            return false;

        return true;
    }

    @Override
    public Vector getNormal(Vector intersection) {
        return this.normal.copy();
    }

    @Override
    public Vector getReflectedRay(Vector intersectionPoint, Vector ray) {
        if (intersectionPoint == null)
            return ray;

        Vector d = Vector.unitVector(ray);

        Vector n = Vector.unitVector(normal);

        n.scale(2.0 * Vector.dotProduct(d, n));

        d.substract(n);

        return d;
    }

    @Override
    public Vector getIntersectionPoint(Point p, Vector u) {
        /*
         * Vector passing through p and direction u => V = p + t*u; CV.N = 0
         * n.x*p.x+u.x*t*n.x-c.x*n.x + n.y*p.y+u.y*t*n.y-c.y*n.y +
         * n.z*p.z+u.z*t*n.z-c.z*n.z = 0
         * 
         * t(n.u) + n.p = c.n R = c.n; Q = n.p; S = u.p; t = (R-Q)/S
         */

        Vector u_temp = u.copy();
        u.unitVector();

        Point center = getCenter();
        Vector c = new Vector(new Point(), center);
        double R = Vector.dotProduct(normal, c);
        double Q = Vector.dotProduct(normal, new Vector(new Point(), p));
        double S = Vector.dotProduct(normal, u);

        if (S == 0) {
            u = u_temp;
            return null;
        }

        double t = (R - Q) / S;

        if (t < 0)
            return null;

        Vector intersection = u.copy();

        intersection.scale(t);
        intersection.add(new Vector(new Point(), p));

        u = u_temp;

        if (isInsideTriangle(intersection)) {
            return intersection;
        }

        return null;
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
    public double getRefractiveIndex() {
        return refractivity;
    }

    @Override
    public List<Vector> getAllIntersectionPoint(Point p, Vector u) {
        return null;
    }

    @Override
    public String toString() {
        return "{" +
                " vertices='" + Arrays.toString(vertices) + "'" +
                ", normal='" + normal + "'" +
                "}";
    }

}
