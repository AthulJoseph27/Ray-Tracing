package Blue;

public class Quaternion {
    public double w;
    public Vector v;

    public Quaternion() {
        w = 0;
        v = new Vector();
    }

    public Quaternion(double angle, Vector axis) {
        w = Math.cos(angle / 2.0);
        v = axis;
        v.scale(Math.sin(angle / 2.0));
    }

    public static Quaternion get_inverse(Quaternion q) {

        Vector v = new Vector(new Point(), new Point(q.v.i, q.v.j, q.v.k));
        v.scale(-1);

        Quaternion nq = new Quaternion();
        nq.v = v;
        nq.w = q.w;

        return nq;
    }

    public void multiply(Quaternion o) {
        Quaternion temp = new Quaternion();
        temp.v = new Vector();
        temp.w = w * o.w - v.i * o.v.i - v.j * o.v.j - v.k * o.v.k;
        temp.v.i = w * o.v.i + o.w * v.i + v.j * o.v.k - v.k * o.v.j;
        temp.v.j = w * o.v.j - v.i * o.v.k + v.j * o.w + v.k * o.v.i;
        temp.v.k = w * o.v.k + v.i * o.v.j - v.j * o.v.i + v.k * o.w;

        this.w = temp.w;
        this.v = temp.v;
    }

    public static Quaternion copy(Quaternion q) {
        Quaternion qb = new Quaternion();
        qb.w = q.w;
        qb.v = new Vector();
        qb.v.i = q.v.i;
        qb.v.j = q.v.j;
        qb.v.k = q.v.k;

        return qb;
    }

    public static Point rotate(Quaternion q, Point p) {
        Quaternion qc = Quaternion.copy(q);
        Quaternion q_inverse = Quaternion.get_inverse(q);
        Quaternion qp = new Quaternion();
        qp.w = 0;
        qp.v.i = p.x;
        qp.v.j = p.y;
        qp.v.k = p.z;

        qc.multiply(qp);
        qc.multiply(q_inverse);

        Point result = new Point(qc.v.i, qc.v.j, qc.v.k);

        return result;
    }

    @Override
    public String toString() {
        return "(w: " + w + " , v: {" + v + "})";
    }
}