package Blue;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public List<Shape> objects;

    public Scene() {
        objects = new ArrayList<Shape>();
    }

    public void add(Shape object) {
        objects.add(object);
    }

}