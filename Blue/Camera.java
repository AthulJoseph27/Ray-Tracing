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
                boolean set = false;
                for (Shape obj : scene.objects) {
                    Vector dir = new Vector(focus, p);
                    dir.unit_vector();
                    if (!obj.do_intersect(p, dir))
                        continue;
                    Vector reflected_ray = obj.get_refeclected_ray(p, dir);
                    if (scene.lightSource.do_intersect(reflected_ray)) {
                        double angle = (2.0 * Math.PI - Vector.angle_between(dir, reflected_ray)) / (2.0 * Math.PI);
                        Color ctemp = obj.get_color();
                        // System.err.println(angle);
                        // System.err.println(Math.round(ctemp.getRed() * angle));
                        // System.err.println(Math.round(ctemp.getGreen() * angle));
                        // System.err.println(Math.round(ctemp.getBlue() * angle));

                        frame[y][x] = new Color((int) Math.round(ctemp.getRed() * angle),
                                (int) Math.round(ctemp.getGreen() * angle), (int) Math.round(ctemp.getBlue() * angle));
                        // System.err.println(angle + " " + frame[y][x]);
                        set = true;
                    }
                    // if (obj.do_intersect(p, dir)) {
                    // frame[y][x] = new Color(0xFFFFFF);
                    // }
                }
                if (!set) {
                    frame[y][x] = new Color(0);
                }
            }
        }
        return frame;
    }

}