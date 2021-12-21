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

    public void unitVector() {
        double magnitude = getMagnitude();
        i /= magnitude;
        j /= magnitude;
        k /= magnitude;
    }

    public void add(Vector o) {
        i += o.i;
        j += o.j;
        k += o.k;
    }

    public void substract(Vector o) {
        i -= o.i;
        j -= o.j;
        k -= o.k;
    }

    public double euclideanDistance(Vector o) {
        return Math.sqrt((i - o.i) * (i - o.i) + (j - o.j) * (j - o.j) + (k - o.k) * (k - o.k));
    }

    public static double dotProduct(Vector a, Vector b) {
        return a.i * b.i + a.j * b.j + a.k * b.k;
    }

    public static Vector crossProduct(Vector a, Vector b) {

        Point p = new Point(a.j * b.k - a.k * b.j, a.i * b.k - a.k * b.i, a.i * b.j - a.j * b.j);

        return new Vector(new Point(), p);
    }

    public static double angleBetween(Vector a, Vector b) {
        double dot_pd = Vector.dotProduct(a, b);
        double result = (double) Math.round(dot_pd / (a.getMagnitude() * b.getMagnitude()) * 1000000) / 1000000.0;
        return Math.acos(result);
    }

    public void rotateX(double angle) {

        double rx[][] = { { 1, 0, 0 }, { 0, Math.cos(angle), (-Math.sin(angle)) },
                { 0, Math.sin(angle), Math.cos(angle) } };

        double new_j;
        new_j = rx[1][0] * i + rx[1][1] * j + rx[1][2] * k;
        k = rx[2][0] * i + rx[2][1] * j + rx[2][2] * k;
        j = new_j;
    }

    public void rotateY(double angle) {

        double ry[][] = { { Math.cos(angle), 0, Math.sin(angle) }, { 0, 1, 0 },
                { (-Math.sin(angle)), 0, Math.cos(angle) } };

        double new_i;
        new_i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        k = ry[2][0] * i + ry[2][1] * j + ry[2][2] * k;
        i = new_i;

    }

    public void rotateZ(double angle) {

        double ry[][] = { { Math.cos(angle), (-Math.sin(angle)), 0 }, { Math.sin(angle), Math.cos(angle), 0 },
                { 0, 0, 1 } };

        double new_i;
        new_i = ry[0][0] * i + ry[0][1] * j + ry[0][2] * k;
        j = ry[1][0] * i + ry[1][1] * j + ry[1][2] * k;
        i = new_i;

    }

    public void rotateXYZ(double rx, double ry, double rz) {
        rotateX(rx);
        rotateY(ry);
        rotateZ(rz);
    }

    public void customRotate(double[][] r) {
        double new_i = r[0][0] * i + r[0][1] * j + r[0][2] * k;
        double new_j = r[1][0] * i + r[1][1] * j + r[1][2] * k;
        double new_k = r[2][0] * i + r[2][1] * j + r[2][2] * k;
        i = new_i;
        j = new_j;
        k = new_k;
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