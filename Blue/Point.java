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

    public double euclidean_distance(Point o) {
        return Math.sqrt((x - o.x) * (x - o.x) + (y - o.y) * (y - o.y) + (z - o.z) * (z - o.z));
    }

    @Override
    public String toString() {
        return "(x: " + x + " , y:" + y + " , z:" + z + ")";
    }
}