package Blue.Solids;

import java.awt.Color;

import Blue.GUI.Callable;
import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class Plane implements Callable, Solid {

    public int width, height;
    public double rx, ry, rz;
    public double reflectivity = 1.0;
    public Point center, ref_point, center_orginal, ref_point_orginal;
    public Vector normal, ref_dir;
    public Color color;

    public Plane(int width, int height, double reflectivity) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        this.reflectivity = reflectivity;
        normal = new Vector(new Point(), new Point(0, 1, 0));
        center = new Point(width / 2.0, 0, height / 2.0);
        center_orginal = center.copy();
        ref_point = new Point(width / 2.0, 0, 0);
        ref_point_orginal = ref_point.copy();
        ref_dir = new Vector(center, ref_point);
        this.ref_dir.unit_vector();
        color = new Color(255, 255, 255, 255);
    }

    public Plane(int width, int height, Color color, double reflectivity) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        this.reflectivity = reflectivity;
        center = new Point(width / 2.0, 0, height / 2.0);
        center_orginal = center.copy();
        ref_point = new Point(width / 2.0, 0, 0);
        ref_point_orginal = ref_point.copy();
        ref_point_orginal = ref_point.copy();
        ref_dir = new Vector(center, ref_point);
        this.ref_dir.unit_vector();
        normal = new Vector(new Point(), new Point(0, 1, 0));
        this.color = color;
    }

    public Plane(int width, int height, Point center, Color color, double reflectivity) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        this.reflectivity = reflectivity;
        this.center = center;
        center_orginal = center.copy();
        ref_point = new Point(center.x, 0, 0);
        ref_point_orginal = ref_point.copy();
        ref_dir = new Vector(center, ref_point);
        this.ref_dir.unit_vector();
        this.normal = Vector.unitVector(normal);
        this.color = color;
    }

    public Plane(int width, int height, double rx, double ry, double rz, Color color, double reflectivity) {
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.reflectivity = reflectivity;
        this.color = new Color(255, 255, 255, 255);
        normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.rotateX(rx);
        normal.rotateY(ry);
        normal.rotateZ(rz);
        this.center = new Point(width / 2.0, 0, height / 2.0);
        center_orginal = center.copy();
        Vector temp = new Vector(new Point(), center);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.center = new Point(temp.i, temp.j, temp.k);
        temp = new Vector(new Point(), new Point(width / 2.0, 0, 0));
        ref_point_orginal = new Point(temp.i, temp.j, temp.k);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.ref_point = new Point(temp.i, temp.j, temp.k);
        this.ref_dir = new Vector(this.center, ref_point);
        this.ref_dir.unit_vector();
        this.color = color;

    }

    public Plane(int width, int height, Point center, double rx, double ry, double rz, Color color,
            double reflectivity) {
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.reflectivity = reflectivity;
        this.color = new Color(255, 255, 255, 255);
        normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.rotateX(rx);
        normal.rotateY(ry);
        normal.rotateZ(rz);
        center_orginal = center.copy();
        Vector temp = new Vector(new Point(), center);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.center = new Point(temp.i, temp.j, temp.k);
        temp = new Vector(new Point(), new Point(center.x, center.y, center.z - height / 2.0));
        this.ref_point_orginal = new Point(temp.i, temp.j, temp.k);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.ref_point = new Point(temp.i, temp.j, temp.k);
        this.ref_dir = new Vector(this.center, ref_point);
        this.ref_dir.unit_vector();
        this.color = color;

    }

    public Plane(int width, int height, Point center, Vector normal) {
        this.width = width;
        this.height = height;
        this.center = center;
        this.normal = normal;
    }

    public void updateOrientation(double rx, double ry, double rz) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        Vector temp = new Vector(new Point(), center_orginal);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.center = new Point(temp.i, temp.j, temp.k);
        temp = new Vector(new Point(), ref_point_orginal);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.ref_point = new Point(temp.i, temp.j, temp.k);
        this.ref_dir = new Vector(this.center, ref_point);
        this.ref_dir.unit_vector();
        normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.rotateX(rx);
        normal.rotateY(ry);
        normal.rotateZ(rz);

    }

    private void updateCenter(Point new_center) {
        center_orginal = new_center;
        Vector temp = new Vector(new Point(), new_center);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.center = new Point(temp.i, temp.j, temp.k);
        temp = new Vector(new Point(), new Point(new_center.x, new_center.y, new_center.z - height / 2.0));
        this.ref_point_orginal = new Point(temp.i, temp.j, temp.k);
        temp.rotateX(rx);
        temp.rotateY(ry);
        temp.rotateZ(rz);
        this.ref_point = new Point(temp.i, temp.j, temp.k);
        this.ref_dir = new Vector(this.center, ref_point);
        this.ref_dir.unit_vector();
    }

    public void updateEulerAngles() {
        rx = Vector.angleBetween(new Vector(new Point(), new Point(1, 0, 0)), normal);
        ry = Vector.angleBetween(new Vector(new Point(), new Point(0, 1, 0)), normal);
        rz = Vector.angleBetween(new Vector(new Point(), new Point(0, 0, 1)), normal);
    }

    public Point transformCoordinate(Point point) {

        Vector v = new Vector(new Point(), new Point(point.x, point.y, point.z));

        v.rotateX(rx);
        v.rotateY(ry);
        v.rotateZ(rz);

        Point result = new Point(v.i, v.j, v.k);

        return result;
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
    public boolean doIntersect(Point point, Vector dir) {
        Vector intersection = getIntersectionPoint(point, dir);

        if (intersection == null)
            return false;

        // Vector dir_hit = new Vector(point, intersection[0]);

        // double angle = Vector.angle_between(dir_hit, dir);

        // if ((Math.PI - angle) <= Limit.ERROR_LIMIT)
        // return false;

        return true;
    }

    @Override
    public Vector getReflectedRay(Vector intersection_point, Vector ray) {

        // d = d - (2*d.n)n

        if (intersection_point == null)
            return ray;

        Vector d = Vector.unitVector(ray);

        Vector n = Vector.unitVector(normal);

        n.scale(2.0 * Vector.dotProduct(d, n));

        d.substract(n);

        // System.out.println(normal);

        // System.out.println(Math.toDegrees(Vector.angle_between(ray, normal)) + " "
        // + Math.toDegrees(Vector.angle_between(d, normal)));

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
        u.unit_vector();

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

        Vector temp = new Vector(center, intersection);
        double angle = Vector.angleBetween(ref_dir, temp);

        double dist = temp.getMagnitude();

        double _width = Math.abs(dist * Math.cos(angle));
        double _height = Math.abs(dist * Math.sin(angle));

        u = u_temp;

        if (_width <= width / 2.0 && _height <= height / 2.0) {
            return intersection;
        }

        return null;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void transform(Point p, String type) {
        if (type.compareTo("center") == 0) {
            updateCenter(p);
            // System.out.println("Center: " + center);
        } else if (type.compareTo("rotation") == 0) {
            updateOrientation(Math.toRadians(p.x), Math.toRadians(p.y), Math.toRadians(p.z));
            // System.out.println(p);
            // System.out.println(normal);
        }
    }

    @Override
    public Vector getNormal(Vector intersection) {
        return this.normal;
    }

    @Override
    public double getReflectivity() {
        return this.reflectivity;
    }

    @Override
    public String toString() {
        return "{" + " width='" + width + "'" + ", height='" + height + "'" + ", rx='" + rx + "'" + ", ry='" + ry + "'"
                + ", rz='" + rz + "'" + ", center='" + center + "'" + ", ref_point='" + ref_point + "'" + ", normal='"
                + normal + "'" + ", ref_dir='" + ref_dir + "'" + ", color='" + color + "'" + "}";
    }

}