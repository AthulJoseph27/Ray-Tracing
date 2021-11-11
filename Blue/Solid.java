package Blue;

import java.awt.*;

interface Solid {

    public boolean is_inside(Point p);

    public boolean is_on_surface(Point p);

    public boolean do_intersect(Point point_on_screen, Vector ray);

    public Vector get_normal(Vector intersection);

    public Vector get_reflected_ray(Vector intersection_point, Vector ray);

    public Vector get_intersection_point(Point p, Vector u);

    public Color get_color();

    public double get_reflectivity();

}