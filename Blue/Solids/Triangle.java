package Blue.Solids;

import java.awt.Color;
import java.util.Arrays;

import Blue.Geometry.Limit;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Rendering.Ray;

public class Triangle implements Solid {

    Point[] vertices = new Point[3];
    Point[] orginalVertices = new Point[3];
    Point center, delta, rotation;
    Vector normal, orginalNormal;
    double reflectivity;
    Color color;
    int index = -1;

    public Triangle(Point a, Point b, Point c, Vector normal, int index) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.orginalVertices[0] = a.copy();
        this.orginalVertices[1] = b.copy();
        this.orginalVertices[2] = c.copy();
        this.normal = normal;
        this.orginalNormal = normal.copy();
        this.reflectivity = 0;
        this.index = index;
        this.delta = new Point();
        this.rotation = new Point();
        this.center = getCenter();
        this.color = Color.WHITE;
    }

    public Triangle(Point a, Point b, Point c, Vector normal, double reflectivity, int index) {
        this.vertices[0] = a;
        this.vertices[1] = b;
        this.vertices[2] = c;
        this.orginalVertices[0] = a.copy();
        this.orginalVertices[1] = b.copy();
        this.orginalVertices[2] = c.copy();
        this.normal = normal;
        this.orginalNormal = normal.copy();
        this.center = getCenter();
        this.delta = new Point();
        this.rotation = new Point();
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

    // private boolean sameSide(Point A, Vector n, Point p) {
    // // EQ -> n.(A-X)

    // double sign1 = Vector.dotProduct(n, new Vector(A, p));
    // double sign2 = Vector.dotProduct(n, new Vector(A, center));

    // // System.out.println(n);
    // // System.out.println(sign1);
    // // System.out.println(sign2);
    // // System.out.println();

    // return ((sign1 > 0.0 && sign2 > 0.0) || (sign1 < 0.0 && sign2 < 0.0));
    // }

    private boolean isInsideTriangle(Point p) {

        Vector AP = new Vector(p, vertices[1]);
        Vector BP = new Vector(p, vertices[2]);
        Vector CP = new Vector(p, vertices[0]);

        Vector u = Vector.crossProduct(BP, CP);
        Vector v = Vector.crossProduct(CP, AP);
        Vector w = Vector.crossProduct(AP, BP);

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
        double Q = Vector.dotProduct(normal, new Vector(new Point(),
                ray.getOrigin()));
        double S = Vector.dotProduct(normal, u);

        if (S == 0) {
            return ray;
        }

        double t = (R - Q) / S;

        if (t < 0)
            return ray;

        Vector intersection = u.copy();

        intersection.scale(t);
        intersection.add(new Vector(new Point(), ray.getOrigin()));

        if (isInsideTriangle(new Point(intersection))) {
            if ((ray.getIntersection() == null)
                    || (ray.getOrigin().euclideanDistance(ray.getIntersection()) > ray.getOrigin()
                            .euclideanDistance(intersection))) {
                ray.setHitSubIndex(index);
                ray.setIntersection(intersection);
                ray.setNormal(this.normal.copy());
            }
        }

        return ray;

        // Möller–Trumbore intersection algorithm

        // Vector AB = new Vector(vertices[0], vertices[1]);
        // Vector AC = new Vector(vertices[0], vertices[2]);

        // Vector h, s, q;

        // h = Vector.crossProduct(ray.getRay().unitVector(), AC);

        // double a, f, u, v;

        // a = Vector.dotProduct(AB, h);

        // if (Math.abs(a) < Limit.ERROR_LIMIT) {
        // // Parallel to triangle
        // return ray;
        // }

        // f = 1.0 / a;

        // s = new Vector(vertices[0], ray.getOrigin());
        // u = f * Vector.dotProduct(s, h);

        // if (u < 0.0 || u > 1.0) {
        // return ray;
        // }

        // q = Vector.crossProduct(s, AB);
        // v = f * Vector.dotProduct(ray.getRay(), q);

        // if (v < 0.0 || (u + v) > 1.0) {
        // return ray;
        // }

        // // At this stage we can compute t to find out where the intersection point is
        // // on the line.
        // double t = f * Vector.dotProduct(AC, q);

        // // At this stage we can compute t to find out where the intersection point is
        // on
        // // the line.
        // if (t > Limit.ERROR_LIMIT) {
        // // ray intersection
        // Vector intersection = Vector.unitVector(ray.getRay()).scale(t)
        // .add(new Vector(new Point(), ray.getOrigin()));
        // ray.setHitSubIndex(index);
        // ray.setIntersection(intersection);
        // ray.setNormal(this.normal.copy());
        // } // else This means that there is a line intersection but not a ray
        // intersection.

        // return ray;

    }

    public void transform(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.delta = p;
            for (int i = 0; i < 3; i++) {
                vertices[i] = orginalVertices[i].copy();
                vertices[i].x += p.x;
                vertices[i].y += p.y;
                vertices[i].z += p.z;
            }
            this.center = getCenter();
        } else if (type.compareTo("rotation") == 0) {
            this.rotation = p;
            this.normal = orginalNormal.copy();
            this.normal.rotateX(p.x);
            this.normal.rotateY(p.y);
            this.normal.rotateZ(p.z);
        }

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
