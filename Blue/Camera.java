package Blue;

import java.awt.*;

public class Camera {

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
        plane = new Plane(width, height);
        focus = get_focus();
        frame = new Color[height][width];
        clear_frame();
    }

    public void update_angle(double rx, double ry, double rz) {
        plane.update_orientation(rx, ry, rz);
    }

    private Point get_focus() {

        Point _center = plane.transform_coordinate(new Point(width / 2.0, 0, height / 2.0));

        Vector normal = plane.normal.copy();
        normal.unit_vector();
        normal.scale(focal_length * -1);

        Point f = new Point(_center.x + normal.i, _center.y + normal.j, _center.z + normal.k);

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

    private Color reflect_ray(int depth, Point p, Vector ray) {

        Vector u = Vector.unit_vector(ray);

        Vector_Index vi = get_intersection_point(p, u, -1);

        if (vi.v == null)
            return new Color(0);

        Vector reflected_ray = scene.objects.get(vi.index).get_reflected_ray(vi.v, u);

        if (depth == 0) {

            // checking if the reflected ray hits any object
            Vector_Index v_next = get_intersection_point(new Point(vi.v), reflected_ray, vi.index);
            double _dist = v_next.v == null ? Double.MAX_VALUE : (new Point(v_next.v)).euclidean_distance(vi.v);
            // System.out.println(_dist + " " + v_next.v + " " + vi.v);

            return adjust_brightness(scene.lightSource.get_brightness(p, scene.objects.get(vi.index).get_normal(vi.v),
                    reflected_ray, _dist), scene.objects.get(vi.index).get_color());
        }

        return reflect_ray(depth - 1, new Point(vi.v), reflected_ray);

    }

    public Color[][] get_frame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = plane.transform_coordinate(new Point(x, 0, y));
                // boolean set = false;
                // for (Shape obj : scene.objects) {
                // if (obj.do_intersect(p, new Vector(focus, p))) {
                // frame[height - y - 1][x] = obj.get_color();
                // set = true;
                // }
                // }
                // if (!set)
                // frame[height - y - 1][x] = new Color(128, 128, 128);
                frame[height - y - 1][x] = reflect_ray(4, p, new Vector(focus, p));
            }
        }

        return frame;
    }

    @Override
    public String toString() {
        return "{" + " focus='" + focus + "'" + ", plane='" + plane + "'" + ", width='" + width + "'" + ", height='"
                + height + "'" + ", focal_length='" + focal_length + "'" + "}";
    }

}