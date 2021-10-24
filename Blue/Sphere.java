package Blue;

public class Sphere implements Shape {

    public double radius, r2;
    public Point center;

    public Sphere(double radius, Point center) {
        this.radius = radius;
        this.r2 = radius * radius;
        this.center = center;
    }

    @Override
    public boolean is_inside(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.magnitude;
        magnitude *= magnitude;
        return magnitude <= r2;
    }

    @Override
    public boolean is_on_surface(Point p) {
        Vector v = new Vector(new Point(), center);
        double magnitude = v.magnitude;
        magnitude *= magnitude;
        return magnitude == r2;
    }

    @Override
    public boolean do_intersect(Point p, Vector u) {

        /*
         * A ray passing through point P and direction vector U can be represented as
         * 
         * R(t) = P + tU
         * 
         * |X-C|^2 = r^2 ; C is center of the Sphere and r is radius of the sphere
         * 
         * substituting X with R(t)
         * 
         * |P-C|^2 - r^2 + 2tU * (P-C) + t^2(U.U) = 0
         * 
         * a = |U.U| b = 2*|U.(P-C)| c = |P-C|^2 - r^2
         * 
         * d = b^2 - 4ac ; --> d >= 0
         */

        Vector q = new Vector(center, p);

        double a = u.magnitude * u.magnitude;
        double b = 2.0 * Vector.dot_product(u, q);
        double c = q.magnitude * q.magnitude - r2;

        return (b * b - 4.0 * a * c) >= 0;
    }

    @Override
    public String toString() {
        return "{Shape : Sphere; radius: +" + radius + " , center : " + center + "}";
    }

}