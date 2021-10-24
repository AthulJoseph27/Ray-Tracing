package Blue;

class Plane {

    public int width, height;
    public double rx, ry, rz;
    public Vector normal;

    public Plane(int width, int height) {
        this.width = width;
        this.height = height;
        rx = 0;
        ry = 0;
        rz = 0;
        normal = new Vector(new Point(), new Point(0, 1, 0));
    }

    public Plane(int width, int height, double rx, double ry, double rz) {
        this.width = width;
        this.height = height;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
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

}