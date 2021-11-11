package Blue;

import java.awt.*;

public class Camera implements Callable {

    Scene scene;
    Point focus;
    public Plane plane;
    int width, height;
    double focal_length;
    Color[][] frame;

    private class Vector_Index {
        Vector v;
        int index;

        Vector_Index(Vector v, int index) {
            this.v = v;
            this.index = index;
        }
    }

    public Camera(int width, int height, Scene scene, double focal_length) {
        this.width = width;
        this.height = height;
        this.scene = scene;
        this.focal_length = focal_length;
        plane = new Plane(width, height, -1);
        focus = getFocus();
        frame = new Color[height][width];
        clear_frame();
    }

    public void updateAngle(double rx, double ry, double rz) {
        plane.updateOrientation(rx, ry, rz);
    }

    private Point getFocus() {

        // Point center = plane.transformcoordinate(new Point(width / 2.0, 0, height /
        // 2.0));
        Vector normal = plane.normal.copy();
        normal.unit_vector();
        normal.scale(focal_length * -1);

        Point f = new Point(plane.center.x + normal.i, plane.center.y + normal.j, plane.center.z + normal.k);

        return f;
    }

    public void clear_frame() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = new Color(0);
            }
        }
    }

    private Color adjustBrightness(double scale, Color color) {
        return new Color((int) (color.getRed() * scale), (int) (color.getGreen() * scale),
                (int) (color.getBlue() * scale), 255);
    }

    private Vector_Index getIntersectionPoint(Point p, Vector u, int skipindex) {
        Vector intersectionpoint = null;

        int index = -1;

        for (int i = 0; i < scene.objects.size(); i++) {
            if (i == skipindex)
                continue;
            Vector ip = scene.objects.get(i).getIntersectionPoint(p, u);
            if (ip == null)
                continue;
            if ((intersectionpoint == null) || p.euclideanDistance(ip) < p.euclideanDistance(intersectionpoint)) {
                intersectionpoint = ip;
                index = i;
            }
        }

        return new Vector_Index(intersectionpoint, index);
    }

    private Color blendColor(Color c1, Color c2, double r1, double r2) {
        double r = r1 + r2;

        double R = c1.getRed() * r1 / r + c2.getRed() * r2 / r;
        double G = c1.getGreen() * r1 / r + c2.getGreen() * r2 / r;
        double B = c1.getBlue() * r1 / r + c2.getBlue() * r2 / r;

        return new Color((int) R, (int) G, (int) B, 255);
    }

    private Color reflectRay(int depth, Point p, Vector ray) {

        Vector u = Vector.unitVector(ray);

        Vector_Index vi = getIntersectionPoint(p, u, -1);

        if (vi.v == null)
            return new Color(0);

        Vector reflectedray = scene.objects.get(vi.index).getReflectedRay(vi.v, u);
        reflectedray.unit_vector();

        Vector_Index v_next = getIntersectionPoint(new Point(vi.v), reflectedray, vi.index);

        Solid cur_obj = scene.objects.get(vi.index);

        if (v_next.v == null) {
            return adjustBrightness(scene.lightSource.get_brightness(new Point(vi.v), cur_obj.getNormal(vi.v),
                    reflectedray, Double.MAX_VALUE), cur_obj.getColor());
        }

        // TODO: Have to check if the ray hits light before any other object!!

        if (depth == 0) {

            // checking if the reflected ray hits any object
            double _dist = v_next.v == null ? Double.MAX_VALUE : (new Point(v_next.v)).euclideanDistance(vi.v);
            // if (v_next.v != null && vi.v.i > 220) {
            // System.out.println("\n" + vi.v + " " + v_next.v);
            // assert (Vector.angle_between(reflectedray, new Vector(vi.v, v_next.v)) ==
            // 0);
            // System.out.println(_dist + " from : " +
            // scene.objects.get(vi.index).getClass() + " to: "
            // + scene.objects.get(v_next.index).getClass());
            // }
            return adjustBrightness(scene.lightSource.get_brightness(new Point(vi.v),
                    scene.objects.get(vi.index).getNormal(vi.v), reflectedray, _dist),
                    scene.objects.get(vi.index).getColor());
        }

        Color c = reflectRay(depth - 1, new Point(vi.v), reflectedray);

        if (c.getRGB() == 0)
            return c;

        Solid other_obj = scene.objects.get(v_next.index);

        return blendColor(c, cur_obj.getColor(), 1.0, cur_obj.getReflectivity());
    }

    public Color[][] getFrame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = plane.transformCoordinate(new Point(x, 0, y));
                frame[height - y - 1][x] = reflectRay(3, p, new Vector(focus, p));
            }
        }

        return frame;
    }

    @Override
    public void transform(Point p, String type) {
        this.plane.transform(p, type);
        this.focus = getFocus();
        // System.out.println(focus);

    }

    @Override
    public String toString() {
        return "{" + " focus='" + focus + "'" + ", plane='" + plane + "'" + ", width='" + width + "'" + ", height='"
                + height + "'" + ", focal_length='" + focal_length + "'" + "}";
    }

}