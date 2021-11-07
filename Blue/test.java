package Blue;

import java.awt.Color;

public class test implements Shape {

    @Override
    public boolean is_inside(Point p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean is_on_surface(Point p) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean do_intersect(Point point_on_screen, Vector ray) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Vector get_normal(Vector intersection) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector get_reflected_ray(Vector normal, Vector ray) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector[] get_intersection_point(Point p, Vector u) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color get_color() {
        // TODO Auto-generated method stub
        return null;
    }

}
