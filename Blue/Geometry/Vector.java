package Blue.Geometry;

public class Vector {
    public double i, j, k;

    public Vector(Point start, Point end) {
        i = end.x - start.x;
        j = end.y - start.y;
        k = end.z - start.z;
    }

    public Vector(Point start, Vector end) {
        i = end.i - start.x;
        j = end.j - start.y;
        k = end.k - start.z;
    }

    public Vector(Vector start, Point end) {
        i = end.x - start.i;
        j = end.y - start.j;
        k = end.z - start.k;
    }

    public Vector(Vector start, Vector end) {
        i = end.i - start.i;
        j = end.j - start.j;
        k = end.k - start.k;
    }

    public Vector() {
        i = 0;
        j = 0;
        k = 0;
    }

    public double getMagnitude() {
        return Math.sqrt(i * i + j * j + k * k);
    }

    public double getAlpha() {
        return Math.acos(i / getMagnitude());
    }

    public double getBeta() {
        return Math.acos(i / getMagnitude());
    }

    public double getGamma() {
        return Math.acos(i / getMagnitude());
    }

    public static Vector unitVector(Vector v) {
        double magnitude = v.getMagnitude();
        return new Vector(new Point(), new Point(v.i / magnitude, v.j / magnitude, v.k / magnitude));
    }

    public Vector unitVector() {
        double magnitude = getMagnitude();
        i /= magnitude;
        j /= magnitude;
        k /= magnitude;
        return this;
    }

    public Vector add(Vector o) {
        i += o.i;
        j += o.j;
        k += o.k;
        return this;
    }

    public Vector substract(Vector o) {
        i -= o.i;
        j -= o.j;
        k -= o.k;
        return this;
    }

    public double euclideanDistance(Vector o) {
        return Math.sqrt((i - o.i) * (i - o.i) + (j - o.j) * (j - o.j) + (k - o.k) * (k - o.k));
    }

    public static double dotProduct(Vector a, Vector b) {
        return a.i * b.i + a.j * b.j + a.k * b.k;
    }

    public static Vector crossProduct(Vector a, Vector b) {

        Point p = new Point(a.j * b.k - a.k * b.j, a.i * b.k - a.k * b.i, a.i * b.j -
                a.j * b.i);

        return new Vector(new Point(), p);
    }

    public static double angleBetween(Vector a, Vector b) {
        double dot_pd = Vector.dotProduct(a, b);
        double result = (double) Math.round(dot_pd / (a.getMagnitude() * b.getMagnitude()) * 1000000) / 1000000.0;
        return Math.acos(result);
    }

    public static Vector rotateWRT(Vector a, Vector b, double angle) {
        // a = a||b + a_|_b

        // a||b = (a.b/b.b)*b

        // a_|_b = a - a||b

        Vector aParallel = b.copy().scale(Vector.dotProduct(a, b) / Vector.dotProduct(b, b));
        Vector aPerpendicular = a.copy().substract(aParallel);

        // w = b x a_|_b

        Vector w = Vector.crossProduct(b, aPerpendicular);

        double x1 = Math.cos(angle) / aPerpendicular.getMagnitude();
        double x2 = Math.sin(angle) / w.getMagnitude();

        // a_|_b,angle = ||aPerpendicular||(x1aPerpendicular + x2w)

        Vector q = aPerpendicular.copy().scale(x1).add(w.scale(x2)).scale(aPerpendicular.getMagnitude());

        // aRequired = a_|_b,angle + aParallel

        return q.add(aParallel);
    }

    public Vector rotateX(double angle) {

        double rx[][] = { { 1, 0, 0 }, { 0, Math.cos(angle), (-Math.sin(angle)) },
                { 0, Math.sin(angle), Math.cos(angle) } };

        double new_j;
        new_j = rx[1][0] * i + rx[1][1] * j + rx[1][2] * k;
        k = rx[2][0] * i + rx[2][1] * j + rx[2][2] * k;
        j = new_j;

        return this;
    }

    public Vector rotateY(double angle) {

        double ry[][] = { { Math.cos(angle), 0, Math.sin(angle) }, { 0, 1, 0 },
                { (-Math.sin(angle)), 0, Math.cos(angle) } };

        double new_i;
        new_i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        k = ry[2][0] * i + ry[2][1] * j + ry[2][2] * k;
        i = new_i;

        return this;
    }

    public Vector rotateZ(double angle) {

        double ry[][] = { { Math.cos(angle), (-Math.sin(angle)), 0 }, { Math.sin(angle), Math.cos(angle), 0 },
                { 0, 0, 1 } };

        double new_i;
        new_i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        j = ry[1][0] * i + ry[1][1] * j + ry[1][2] * k;
        i = new_i;

        return this;
    }

    public Vector rotateXYZ(double rx, double ry, double rz) {
        rotateX(rx);
        rotateY(ry);
        rotateZ(rz);

        return this;
    }

    public Vector customRotate(double[][] r) {
        double new_i = r[0][0] * i + r[0][1] * j + r[0][2] * k;
        double new_j = r[1][0] * i + r[1][1] * j + r[1][2] * k;
        double new_k = r[2][0] * i + r[2][1] * j + r[2][2] * k;
        i = new_i;
        j = new_j;
        k = new_k;

        return this;
    }

    public Vector scale(double value) {
        i *= value;
        j *= value;
        k *= value;
        return this;
    }

    public Vector copy() {
        return new Vector(new Point(), new Point(i, j, k));
    }

    public boolean equalsTo(Vector o) {
        return (this.i == o.i) && (this.j == o.j) && (this.k == o.k);
    }

    @Override
    public String toString() {
        String sign_i = i < 0 ? "-" : "";
        String sign_j = j < 0 ? "- " : "+ ";
        String sign_k = k < 0 ? "- " : "+ ";
        return sign_i + Math.abs(i) + "i " + sign_j + Math.abs(j) + "j " + sign_k + Math.abs(k) + "k";
    }
}