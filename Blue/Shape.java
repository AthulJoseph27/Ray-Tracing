package Blue;

interface Shape {

    public boolean is_inside(Point p);

    public boolean is_on_surface(Point p);

    public boolean do_intersect(Point point_on_screen, Vector normal);

}