package Blue;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public List<Shape> objects;
    public LightSource lightSource;

    public Scene() {
        objects = new ArrayList<Shape>();
        lightSource = new LightSource(new Point(200, 75, 300), 50, 1);
    }

    public void add(Shape object) {
        objects.add(object);
    }

}