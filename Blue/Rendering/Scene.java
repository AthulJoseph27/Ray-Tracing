package Blue.Rendering;

import java.util.ArrayList;
import java.util.List;

import Blue.Geometry.Point;
import Blue.Light.LightSource;
import Blue.Light.SpotLight;
import Blue.Solids.Solid;

public class Scene {

    public List<Solid> objects;
    public LightSource lightSource;

    public Scene() {
        objects = new ArrayList<Solid>();

        lightSource = new SpotLight(new Point(0, 0, 0), 200, 1);
    }

    public Scene(LightSource lightSource) {
        objects = new ArrayList<Solid>();
        this.lightSource = lightSource;
    }

    public void add(Solid object) {
        objects.add(object);
    }

}