package Blue;

import java.awt.*;

public class Camera {

    Scene scene;
    Point focus;
    public Plane plane;
    private static final double ERROR_LIMIT = 0.000001;
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

                    Vector[] intersection_points = obj.get_intersection_point(p, dir);

                    if (intersection_points == null)
                        continue;

                    Vector p_intersection = intersection_points[1];

                    if (intersection_points[0] == null)
                        p_intersection = intersection_points[1];
                    if (intersection_points[1] == null)
                        p_intersection = intersection_points[0];

                    if (intersection_points[0] != null && intersection_points[1] != null) {
                        if (p.euclidean_distance(new Point(intersection_points[0].i, intersection_points[0].j,
                                intersection_points[0].k)) > p
                                        .euclidean_distance(new Point(intersection_points[1].i,
                                                intersection_points[1].j, intersection_points[1].k))) {
                            p_intersection = intersection_points[1];
                        } else {
                            p_intersection = intersection_points[0];
                        }
                    }

                    if (p_intersection == null)
                        continue;

                    // Vector dir_intersection = new Vector(p, p_intersection);
                    // double dir_angle = Math.PI - Vector.angle_between(dir_intersection, dir);

                    // // System.out.println(Math.PI - Vector.angle_between(dir_intersection, dir));
                    // if (dir_angle <= ERROR_LIMIT) {
                    // // System.out.println(Vector.unit_vector(dir_intersection) + " " + dir);
                    // continue;
                    // }

                    Vector normal = obj.get_normal(p_intersection);

                    Vector reflected_ray = obj.get_reflected_ray(normal, dir);

                    double factor = scene.lightSource.get_brightness(new Point(p_intersection), normal, reflected_ray);

                    Color color = obj.get_color();
                    color = new Color((int) (color.getRed() * factor), (int) (color.getGreen() * factor),
                            (int) (color.getBlue() * factor));
                    frame[y][x] = color;
                    set = true;

                }
                if (!set) {
                    frame[y][x] = new Color(128, 128, 128);
                }
            }
        }
        return frame;
    }

}