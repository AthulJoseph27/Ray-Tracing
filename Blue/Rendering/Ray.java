package Blue.Rendering;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class Ray {
    private Point origin;
    private Vector intersection;
    private int orginIndex, orginSubIndex = -1, hitIndex = -1, hitSubIndex = -1;
    private Vector normal; // Normal at the point of intersection
    private Vector ray;

    public Ray(Point origin, Vector ray, int index, int subIndex) {
        this.origin = origin;
        this.ray = ray;
        this.orginIndex = index;
        this.orginSubIndex = subIndex;
        this.normal = null;
        this.intersection = null;
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Vector getIntersection() {
        return this.intersection;
    }

    public void setIntersection(Vector intersection) {
        this.intersection = intersection;
    }

    public int getOrginIndex() {
        return this.orginIndex;
    }

    public void setOrginIndex(int orginIndex) {
        this.orginIndex = orginIndex;
    }

    public int getOrginSubIndex() {
        return this.orginSubIndex;
    }

    public void setOrginSubIndex(int orginSubIndex) {
        this.orginSubIndex = orginSubIndex;
    }

    public int getHitIndex() {
        return this.hitIndex;
    }

    public void setHitIndex(int hitIndex) {
        this.hitIndex = hitIndex;
    }

    public int getHitSubIndex() {
        return this.hitSubIndex;
    }

    public void setHitSubIndex(int hitSubIndex) {
        this.hitSubIndex = hitSubIndex;
    }

    public Vector getNormal() {
        return this.normal;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    public Vector getRay() {
        return this.ray;
    }

    public void setRay(Vector ray) {
        this.ray = ray;
    }

}
