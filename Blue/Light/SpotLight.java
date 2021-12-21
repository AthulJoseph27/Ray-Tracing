package Blue.Light;

import java.awt.*;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;
import Blue.Solids.Sphere;

public class SpotLight {
    // public class SpotLight extends Sphere implements LightSource {
    // double intensity;

    // public SpotLight(Point center, double radius, double intensity) {
    // super(radius, center, new Color(0xFFFFFF), -1);
    // this.intensity = intensity;
    // }

    // @Override
    // public Vector getReflectedRay(Vector normal, Vector ray) {
    // throw new UnsupportedOperationException();
    // }

    // @Override
    // public double getDiffuseBrightness(Point p, Vector obj_normal, Vector
    // reflected_ray,
    // double distance_to_nearest_object) {
    // Vector intersection = getIntersectionPoint(p, reflected_ray);
    // // System.out.println(p + " " + reflected_ray);
    // if (intersection == null)
    // return 0.0;

    // if (p.euclideanDistance(intersection) > distance_to_nearest_object)
    // return 0.0;

    // double angle = Vector.angleBetween(obj_normal, reflected_ray);
    // if (angle >= (Math.PI / 2.0))
    // return 0.0;

    // return 1.0 - angle / (Math.PI / 2.0);
    // }

    // @Override
    // public double getSpecularBrightness(Vector reflected_ray, double
    // distance_to_nearest_object) {
    // // TODO Auto-generated method stub
    // return 0;
    // }

    // @Override
    // public String toString() {
    // return "{LightSource ; radius: +" + radius + " , center : " + center + " ,
    // intensisty : " + intensity + "}";
    // }

}