package Blue;

import java.awt.*;

interface Shape {

    public boolean is_inside(Point p);

    public boolean is_on_surface(Point p);

    public boolean do_intersect(Point point_on_screen, Vector normal);

    public boolean do_intersect(Vector ray);

    public Vector get_refeclected_ray(Point p, Vector u);

    public Color get_color();

}