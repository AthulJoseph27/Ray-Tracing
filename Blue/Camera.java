package Blue;

import java.awt.*;

public class Camera {

    Scene scene;
    Plane plane;
    Point focus;
    int width, height;
    double focal_length;
    Color[][] frame;

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
        plane.update_angle(rx, ry, rz);
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

    public Color[][] get_frame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = plane.transform_coordinate(new Point(x, 0, y));
                for (Shape obj : scene.objects) {
                    Vector dir = new Vector(focus, p);
                    dir.unit_vector();
                    if (obj.do_intersect(p, dir)) {
                        frame[y][x] = new Color(0xFFFFFF);
                    }
                    // else {
                    // frame[y][x] = new Color(0);
                    // }
                }
            }
        }
        return frame;
    }

}