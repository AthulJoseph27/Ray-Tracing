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
        focus = get_focus();
        frame = new Color[height][width];
        clear_frame();
    }

    public void update_angle(double rx, double ry, double rz) {
        plane.update_orientation(rx, ry, rz);
    }

    private Point get_focus() {

        // Point _center = plane.transform_coordinate(new Point(width / 2.0, 0, height /
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

    private Color adjust_brightness(double scale, Color color) {
        return new Color((int) (color.getRed() * scale), (int) (color.getGreen() * scale),
                (int) (color.getBlue() * scale), 255);
    }

    private Vector_Index get_intersection_point(Point p, Vector u, int skip_index) {
        Vector intersection_point = null;

        int index = -1;

        for (int i = 0; i < scene.objects.size(); i++) {
            if (i == skip_index)
                continue;
            Vector _ip = scene.objects.get(i).get_intersection_point(p, u);
            if (_ip == null)
                continue;
            if ((intersection_point == null) || p.euclidean_distance(_ip) < p.euclidean_distance(intersection_point)) {
                intersection_point = _ip;
                index = i;
            }
        }

        return new Vector_Index(intersection_point, index);
    }

    private Color blendColor(Color c1, Color c2, double r1, double r2) {
        double r = r1 + r2;

        double R = c1.getRed() * r1 / r + c2.getRed() * r2 / r;
        double G = c1.getGreen() * r1 / r + c2.getGreen() * r2 / r;
        double B = c1.getBlue() * r1 / r + c2.getBlue() * r2 / r;

        return new Color((int) R, (int) G, (int) B, 255);
    }

    private Color reflect_ray(int depth, Point p, Vector ray) {

        Vector u = Vector.unit_vector(ray);

        Vector_Index vi = get_intersection_point(p, u, -1);

        if (vi.v == null)
            return new Color(0);

        Vector reflected_ray = scene.objects.get(vi.index).get_reflected_ray(vi.v, u);
        reflected_ray.unit_vector();

        Vector_Index v_next = get_intersection_point(new Point(vi.v), reflected_ray, vi.index);

        Solid cur_obj = scene.objects.get(vi.index);

        if (v_next.v == null) {
            return adjust_brightness(scene.lightSource.get_brightness(new Point(vi.v), cur_obj.get_normal(vi.v),
                    reflected_ray, Double.MAX_VALUE), cur_obj.get_color());
        }

        // TODO: Have to check if the ray hits light before any other object!!

        if (depth == 0) {

            // checking if the reflected ray hits any object
            double _dist = v_next.v == null ? Double.MAX_VALUE : (new Point(v_next.v)).euclidean_distance(vi.v);
            // if (v_next.v != null && vi.v.i > 220) {
            // System.out.println("\n" + vi.v + " " + v_next.v);
            // assert (Vector.angle_between(reflected_ray, new Vector(vi.v, v_next.v)) ==
            // 0);
            // System.out.println(_dist + " from : " +
            // scene.objects.get(vi.index).getClass() + " to: "
            // + scene.objects.get(v_next.index).getClass());
            // }
            return adjust_brightness(scene.lightSource.get_brightness(new Point(vi.v),
                    scene.objects.get(vi.index).get_normal(vi.v), reflected_ray, _dist),
                    scene.objects.get(vi.index).get_color());
        }

        Color c = reflect_ray(depth - 1, new Point(vi.v), reflected_ray);

        if (c.getRGB() == 0)
            return c;

        Solid other_obj = scene.objects.get(v_next.index);

        return blendColor(c, cur_obj.get_color(), other_obj.get_reflectivity(), cur_obj.get_reflectivity());
    }

    public Color[][] get_frame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = plane.transform_coordinate(new Point(x, 0, y));
                frame[height - y - 1][x] = reflect_ray(5, p, new Vector(focus, p));
            }
        }

        return frame;
    }

    @Override
    public void transform(Point p, String type) {
        this.plane.transform(p, type);
        this.focus = get_focus();
        // System.out.println(focus);

    }

    @Override
    public String toString() {
        return "{" + " focus='" + focus + "'" + ", plane='" + plane + "'" + ", width='" + width + "'" + ", height='"
                + height + "'" + ", focal_length='" + focal_length + "'" + "}";
    }

}