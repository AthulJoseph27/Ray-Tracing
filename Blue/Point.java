package Blue;

public class Point {
    public double x, y, z;

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(Vector v) {
        this.x = v.i;
        this.y = v.j;
        this.z = v.k;
    }

    public double euclidean_distance(Point o) {
        return Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y) + (z - o.z) * (z - o.z));
    }

    public double euclidean_distance(Vector o) {
        return Math.sqrt((x - o.i) * (x - o.i) + (y - o.j) * (y - o.j) + (z - o.k) * (z - o.k));
    }

    public Point copy() {
        return new Point(x, y, z);
    }

    public static Vector get_closest_point(Point p, Vector[] intersections) {
        if (intersections == null)
            return null;

        Vector result = null;
        double dist = Double.MAX_VALUE;

        for (Vector v : intersections) {
            if (v == null) {
                continue;
            }
            double _temp = p.euclidean_distance(v);
            if (result == null || (_temp < dist)) {
                dist = _temp;
                result = v;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return "(x: " + x + " , y:" + y + " , z:" + z + ")";
    }
}