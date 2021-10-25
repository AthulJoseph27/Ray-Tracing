package Blue;

public class Vector {
    public double i, j, k;
    public double magnitude;

    public Vector(Point start, Point end) {
        i = end.x - start.x;
        j = end.y - start.y;
        k = end.z - start.z;
        magnitude = get_magnitude();
    }

    public Vector() {
        i = 0;
        j = 0;
        k = 0;
        magnitude = 0;
    }

    @Override
    public String toString() {
        String sign_i = i < 0 ? "-" : "";
        String sign_j = j < 0 ? "- " : "+ ";
        String sign_k = k < 0 ? "- " : "+ ";
        return sign_i + Math.abs(i) + "i " + sign_j + Math.abs(j) + "j " + sign_k + Math.abs(k) + "k";
    }

    public double get_magnitude() {
        return Math.sqrt(i * i + j * j + k * k);
    }

    public double get_alpha() {
        return Math.acos(i / magnitude);
    }

    public double get_beta() {
        return Math.acos(i / magnitude);
    }

    public double get_gamma() {
        return Math.acos(i / magnitude);
    }

    public static Vector unit_vector(Vector v) {
        v.magnitude = v.get_magnitude();
        return new Vector(new Point(), new Point(v.i / v.magnitude, v.j / v.magnitude, v.k / v.magnitude));
    }

    public void unit_vector() {
        magnitude = get_magnitude();
        i /= magnitude;
        j /= magnitude;
        k /= magnitude;
        magnitude = get_magnitude();
    }

    public void add(Vector o) {
        i += o.i;
        j += o.j;
        k += o.k;
        magnitude = get_magnitude();
    }

    public void substract(Vector o) {
        i -= o.i;
        j -= o.j;
        k -= o.k;
        magnitude = get_magnitude();
    }

    public double euclidean_distance(Vector o) {
        return Math.sqrt((i - o.i) * (i - o.i) + (j - o.j) * (j - o.j) + (k - o.k) * (k - o.k));
    }

    public static double dot_product(Vector a, Vector b) {
        return a.i * b.i + a.j * b.j + a.k * b.k;
    }

    public static Vector cross_product(Vector a, Vector b) {

        Point p = new Point(a.j * b.k - a.k * b.j, a.i * b.k - a.k * b.i, a.i * b.j - a.j * b.j);

        return new Vector(new Point(), p);
    }

    public static double angle_between(Vector a, Vector b) {
        double dot_pd = Vector.dot_product(a, b);
        // System.err.println(a + " " + b);
        // System.err.println(dot_pd);
        // System.err.println(a.magnitude + " " + b.magnitude);
        // System.err.println(dot_pd / (a.magnitude * b.magnitude));
        return Math.acos(dot_pd / (a.magnitude * b.magnitude));
    }

    public void rotateX(double angle) {

        double rx[][] = { { 1, 0, 0 }, { 0, Math.cos(angle), (-Math.sin(angle)) },
                { 0, Math.sin(angle), Math.cos(angle) } };

        j = rx[1][0] * i + rx[1][1] * j + rx[1][2] * k;
        k = rx[2][0] * i + rx[2][1] * j + rx[2][2] * k;

    }

    public void rotateY(double angle) {

        double ry[][] = { { Math.cos(angle), 0, Math.sin(angle) }, { 0, 1, 0 },
                { (-Math.sin(angle)), 0, Math.cos(angle) } };

        i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        k = ry[2][0] * i + ry[2][1] * j + ry[2][2] * k;

    }

    public void rotateZ(double angle) {

        double ry[][] = { { Math.cos(angle), (-Math.sin(angle)), 0 }, { Math.sin(angle), Math.cos(angle), 0 },
                { 0, 0, 1 } };

        i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        j = ry[1][0] * i + ry[1][1] * j + ry[1][2] * k;

    }

    public void custom_rotate(double[][] r) {
        i = r[0][0] * i + r[0][1] * j + r[0][2] * k;
        j = r[1][0] * i + r[1][1] * j + r[1][2] * k;
        k = r[2][0] * i + r[2][1] * j + r[2][2] * k;
    }

    public void scale(double value) {
        i *= value;
        j *= value;
        k *= value;
        magnitude = get_magnitude();
    }

    public Vector copy() {
        return new Vector(new Point(), new Point(i, j, k));
    }

}