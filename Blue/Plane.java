package Blue;

import java.awt.Color;

public class Plane implements Callable, Shape {

    public int width, height;
    public double rx, ry, rz;
    public Point center, ref_point, center_orginal, ref_point_orginal;
    public Vector normal, ref_dir;
    public Color color;

    public Plane(int width, int height) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        normal = new Vector(new Point(), new Point(0, 1, 0));
        center = new Point(width / 2.0, 0, height / 2.0);
        center_orginal = center.copy();
        ref_point = new Point(width / 2.0, 0, 0);
        ref_point_orginal = ref_point.copy();
        ref_dir = new Vector(center, ref_point);
        this.ref_dir.unit_vector();
        color = new Color(255, 255, 255, 255);
    }

    public Plane(int width, int height, Color color) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
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

    public Plane(int width, int height, Point center, Color color) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        this.center = center;
        center_orginal = center.copy();
        ref_point = new Point(center.x, 0, 0);
        ref_point_orginal = ref_point.copy();
        ref_dir = new Vector(center, ref_point);
        this.ref_dir.unit_vector();
        this.normal = Vector.unit_vector(normal);
        this.color = color;
    }

    public Plane(int width, int height, double rx, double ry, double rz, Color color) {
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.color = new Color(255, 255, 255, 255);
        center_orginal = center.copy();
        normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.rotateX(rx);
        normal.rotateY(ry);
        normal.rotateZ(rz);
        this.center = new Point(width / 2.0, 0, height / 2.0);
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

    public Plane(int width, int height, Point center, double rx, double ry, double rz, Color color) {
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

    public void update_orientation(double rx, double ry, double rz) {
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
    public boolean do_intersect(Point point, Vector dir) {
        Vector[] intersection = get_intersection_point(point, dir);

        return intersection[0] != null;
    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        if (Vector.angle_between(normal, ray) <= (Math.PI / 2.0))
            return ray;

        Vector[] intersection_point = get_intersection_point(new Point(), ray);

        // d = d - (2*d.n)n

        if (intersection_point[0] == null)
            return ray;

        Vector d = Vector.unit_vector(ray);

        Vector n = Vector.unit_vector(normal);

        n.scale(2.0 * Vector.dot_product(d, n));

        d.substract(n);

        // System.out.println(Math.toDegrees(Vector.angle_between(normal, ray)) + " "
        // + Math.toDegrees(Vector.angle_between(normal, d)));

        return d;
    }

    @Override
    public Vector[] get_intersection_point(Point p, Vector u) {

        /*
         * Vector passing through p and direction u => V = p + t*u; CV.N = 0
         * n.x*p.x+u.x*t*n.x-c.x*n.x + n.y*p.y+u.y*t*n.y-c.y*n.y +
         * n.z*p.z+u.z*t*n.z-c.z*n.z = 0
         * 
         * t(n.u) + n.p = c.n R = c.n; Q = n.p; S = u.p; t = (R-Q)/S
         */
        Vector u_temp = u.copy();
        u.unit_vector();

        Vector[] result = { null, null };

        Vector c = new Vector(new Point(), center);
        double R = Vector.dot_product(normal, c);
        double Q = Vector.dot_product(normal, new Vector(new Point(), p));
        double S = Vector.dot_product(normal, u);

        if (S == 0) {
            u = u_temp;
            return result;
        }

        double t = (R - Q) / S;

        Vector intersection = u.copy();

        intersection.scale(t);
        intersection.add(new Vector(new Point(), p));

        Vector temp = new Vector(center, intersection);
        double angle = Vector.angle_between(ref_dir, temp);

        double dist = temp.get_magnitude();

        double _width = Math.abs(dist * Math.cos(angle));
        double _height = Math.abs(dist * Math.sin(angle));

        if (_width <= width / 2.0 && _height <= height / 2.0) {
            result[0] = intersection;
        }

        u = u_temp;
        return result;
    }

    @Override
    public Color get_color() {
        return this.color;
    }

    @Override
    public void call(Point p, String type) {
        if (type.compareTo("center") == 0) {
            this.center = p;
        } else if (type.compareTo("rotation") == 0) {
            update_orientation(Math.toRadians(p.x), Math.toRadians(p.y), Math.toRadians(p.z));
        }
    }

    @Override
    public Vector get_normal(Vector intersection) {
        return this.normal.copy();
    }

    @Override
    public String toString() {
        return "{" + " width='" + width + "'" + ", height='" + height + "'" + ", rx='" + rx + "'" + ", ry='" + ry + "'"
                + ", rz='" + rz + "'" + ", center='" + center + "'" + ", ref_point='" + ref_point + "'" + ", normal='"
                + normal + "'" + ", ref_dir='" + ref_dir + "'" + ", color='" + color + "'" + "}";
    }

}