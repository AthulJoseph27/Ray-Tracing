package Blue;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    public List<Shape> objects;
    public LightSource lightSource;

    public Scene() {
        objects = new ArrayList<Shape>();
        lightSource = new SpotLight(new Point(250, 500, 200), 500, 1);
    }

    public Scene(LightSource lightSource) {
        objects = new ArrayList<Shape>();
        this.lightSource = lightSource;
    }

    public void add(Shape object) {
        objects.add(object);
    }

}