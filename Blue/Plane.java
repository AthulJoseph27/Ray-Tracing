package Blue;

import java.awt.Color;

public class Plane implements Callable, Shape {

    public int width, height;
    public double rx, ry, rz;
    public Point center;
    public Vector normal;
    public Color color;

    public Plane(int width, int height) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        normal = new Vector(new Point(), new Point(0, 1, 0));
        center = new Point(width / 2.0, 0, height / 2.0);
        color = new Color(255, 255, 255, 255);
    }

    public Plane(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        center = new Point(width / 2.0, 0, height / 2.0);
        normal = new Vector(new Point(), new Point(0, 1, 0));
        this.color = color;
    }

    public Plane(int width, int height, Point center, Vector normal, Color color) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        this.center = center;
        this.normal = Vector.unit_vector(normal);
        this.color = color;
    }

    public Plane(int width, int height, double rx, double ry, double rz) {
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.color = new Color(255, 255, 255, 255);
        normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.rotateX(rx);
        normal.rotateY(ry);
        normal.rotateZ(rz);
    }

    public void update_angle(double rx, double ry, double rz) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    public Point transform_coordinate(Point point) {

        Vector v = new Vector(new Point(), new Point(point.x, point.y, point.z));

        v.rotateX(rx);
        v.rotateY(ry);
        v.rotateZ(rz);

        Point result = new Point(v.i, v.j, v.k);

        return result;
    }

    @Override
    public boolean is_inside(Point p) {
        return false;
    }

    @Override
    public boolean is_on_surface(Point p) {
        return false;
    }

    @Override
    public boolean do_intersect(Point point_on_screen, Vector dir) {
        return Math.abs(Vector.dot_product(dir, normal)) < 0.001;
    }

    @Override
    public boolean do_intersect(Vector ray) {
        return Math.abs(Vector.dot_product(ray, normal)) < 0.001;

    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        Vector[] intersection_point = get_intersection_point(new Point(), ray);

        // d = d - (2*d.n)n

        if (intersection_point[0] == null)
            return ray;

        Vector d = ray.copy();

        Vector n = Vector.unit_vector(normal);

        n.scale(2.0 * Vector.dot_product(d, n));

        d.substract(n);

        return d;
    }

    @Override
    public Vector[] get_intersection_point(Point p, Vector u) {
        /*
         * Intersection Point(P)(x,y,z)
         * 
         * x-u.x/u.x = y-u.y/u.y = z-u.z/u.z = r
         * 
         * x => u.x(r+1) , y => u.y(r+1) , z => u.z(r+1)
         * 
         * PC.N = 0
         * 
         * PC => (x-c.x,y-c.y,z-c.z) PC => (u.x(r+1)-c.x,u.y(r+1)-c.y,u.z(r+1)-c.z)
         * 
         * n.x(u.x(r+1)-c.x) + n.y(u.y(r+1)-c.y) + n.z(u.z(r+1)-c.z) = 0
         * 
         * n.x*u.x*r + n.x*u.x - c.x*n.x + n.y*u.y*r + n.y*u.y - c.y*n.y + n.z*u.z*r +
         * n.z*u.z - c.z*n.z = 0
         * 
         * r(n.x*u.x + n.y*u.y + n.z*u.z) + (n.x*u.x + n.y*u.y + n.z*u.z) = (c.x*n.x +
         * c.y*n.y + c.z*n.z)
         * 
         * (n.x*u.x + n.y*u.y + n.z*u.z) = Q (c.x*n.x + c.y*n.y + c.z*n.z) = R
         * 
         * (R - Q)/Q = r
         * 
         * r = R/Q - 1
         */

        Vector[] result = { null, null };

        Vector c = new Vector(new Point(), center);
        double R = Vector.dot_product(normal, c);
        double Q = Vector.dot_product(normal, u);

        if (Q == 0)
            return result;

        double r = R / Q - 1.0;

        r += 1.0;

        Vector intersection = u.copy();

        intersection.scale(r);

        // if(Math.abs(intersection.i-center.x) < width/2)

        result[0] = intersection;

        System.out.println(result[0]);

        return result;
    }

    @Override
    public Color get_color() {
        return this.color;
    }

    @Override
    public void call(Point p, String type) {
        if (type.compareTo("rotation") == 0) {
            this.rx = Math.toRadians(p.x);
            this.ry = Math.toRadians(p.y);
            this.rz = Math.toRadians(p.z);
        }
    }

}