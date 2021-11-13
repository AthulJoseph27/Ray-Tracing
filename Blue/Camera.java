package Blue;

import java.awt.*;

public class Camera implements Callable {

    Scene scene;
    Point focus;
    public Plane plane;
    int width, height;
    double focal_length;
    Color[][] frame;

    private class VectorIndex {
        Vector v;
        int index;

        VectorIndex(Vector v, int index) {
            this.v = v;
            this.index = index;
        }
    }

    public Camera(int width, int height, Scene scene, double focal_length) {
        this.width = width;
        this.height = height;
        this.scene = scene;
        this.focal_length = focal_length;
        plane = new Plane(width, height, -1);
        focus = getFocus();
        frame = new Color[height][width];
        clearFrame();
    }

    // public void updateAngle(double rx, double ry, double rz) {
    // plane.updateOrientation(rx, ry, rz);
    // this.focus = getFocus();
    // System.out.println(focus);
    // }

    private Point getFocus() {
        Vector normal = plane.normal.copy();
        normal.unit_vector();
        normal.scale(focal_length * -1);

        Point f = new Point(plane.center.x + normal.i, plane.center.y + normal.j, plane.center.z + normal.k);

        return f;
    }

    private Point getFocus(Point center) {
        Vector normal = new Vector(new Point(), new Point(0, 1, 0));
        normal.unit_vector();
        normal.scale(focal_length * -1);

        Point f = new Point(center.x + normal.i, center.y + normal.j, center.z + normal.k);

        return f;
    }

    public void clearFrame() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                frame[y][x] = new Color(0);
            }
        }
    }

    private Color adjustBrightness(double scale, Color color) {
        int R = (int) Math.min((color.getRed() * scale), 255);
        int G = (int) Math.min((color.getGreen() * scale), 255);
        int B = (int) Math.min((color.getBlue() * scale), 255);
        return new Color(R, G, B, 255);
    }

    private VectorIndex getIntersectionPoint(Point p, Vector u, int skipindex) {
        Vector intersectionpoint = null;

        int index = -1;

        for (int i = 0; i < scene.objects.size(); i++) {
            if (i == skipindex)
                continue;
            Vector ip = scene.objects.get(i).getIntersectionPoint(p, u);
            if (ip == null)
                continue;
            if ((intersectionpoint == null) || p.euclideanDistance(ip) < p.euclideanDistance(intersectionpoint)) {
                intersectionpoint = ip;
                index = i;
            }
        }

        return new VectorIndex(intersectionpoint, index);
    }

    private Color blendColor(Color c1, Color c2, double r1, double r2) {
        double r = r1 + r2;

        double R = c1.getRed() * r1 / r + c2.getRed() * r2 / r;
        double G = c1.getGreen() * r1 / r + c2.getGreen() * r2 / r;
        double B = c1.getBlue() * r1 / r + c2.getBlue() * r2 / r;

        return new Color((int) R, (int) G, (int) B, 255);
    }

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private Color lerp(Color c1, Color c2, double t) {
        int R = (int) lerp(c1.getRed(), c2.getRed(), t);
        int G = (int) lerp(c1.getGreen(), c2.getGreen(), t);
        int B = (int) lerp(c1.getBlue(), c2.getBlue(), t);

        return new Color(R, G, B, 255);

    }

    private Color reflectRay(int depth, Point p, Vector ray) {

        Vector u = Vector.unitVector(ray);

        VectorIndex vi = getIntersectionPoint(p, u, -1);

        if (vi.v == null)
            return new Color(0);

        Vector reflectedRay = scene.objects.get(vi.index).getReflectedRay(vi.v, u);
        reflectedRay.unit_vector();

        VectorIndex vNext = getIntersectionPoint(new Point(vi.v), reflectedRay, vi.index);

        Solid curObj = scene.objects.get(vi.index);

        if (vNext.v == null) {
            // return curObj.getColor();
            double factor = scene.lightSource.getDiffuseBrightness(new Point(vi.v), curObj.getNormal(vi.v),
                    reflectedRay, Double.MAX_VALUE);
            factor = Math.max(factor, 0.1) + scene.lightSource.getSpecularBrightness(reflectedRay, Double.MAX_VALUE);
            return adjustBrightness(factor, curObj.getColor());
        }

        // TODO: Have to check if the ray hits light before any other object!!

        if (depth == 0) {

            // checking if the reflected ray hits any object
            double _dist = vNext.v == null ? Double.MAX_VALUE : (new Point(vNext.v)).euclideanDistance(vi.v);

            double factor = scene.lightSource.getDiffuseBrightness(new Point(vi.v),
                    scene.objects.get(vi.index).getNormal(vi.v), reflectedRay, _dist);
            factor = Math.max(factor, 0.1) * curObj.getReflectivity()
                    + scene.lightSource.getSpecularBrightness(reflectedRay, _dist);
            return adjustBrightness(factor, scene.objects.get(vi.index).getColor());
        }

        Color c = reflectRay(depth - 1, new Point(vi.v), reflectedRay);

        if (c.getRGB() == 0)
            return c;

        // return blendColor(c, curObj.getColor(), 1.0, curObj.getReflectivity());

        return lerp(c, curObj.getColor(), curObj.getReflectivity());
    }

    public Color[][] getFrame() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = plane.transformCoordinate(new Point(x, 0, y));
                frame[height - y - 1][x] = reflectRay(10, p, new Vector(focus, p));
            }
        }

        return frame;
    }

    @Override
    public void transform(Point p, String type) {
        /*
         * rotating a around b 𝑎⃗ = 𝑎⃗ ∥𝑏⃗ +𝑎⃗ ⊥𝑏⃗ 𝑎⃗ ∥𝑏⃗ =(𝑎⃗ ⋅𝑏⃗ / 𝑏⃗ ⋅𝑏⃗
         * )𝑏⃗ 𝑎⃗ ⊥𝑏⃗ ,𝜃 = ||𝑎⃗ ⊥𝑏⃗ ||(𝑥1𝑎⃗ ⊥𝑏⃗ +𝑥2𝑤⃗ )
         * 
         * 𝑤⃗ =𝑏⃗ ×𝑎⃗ ⊥𝑏⃗
         * 
         * 𝑥1=𝑐𝑜𝑠(𝜃)/||𝑎⃗ ⊥𝑏⃗ ||
         * 
         * 𝑥2=𝑠𝑖𝑛(𝜃)/||𝑤⃗ ||
         * 
         * 
         * 
         * 𝑎⃗ 𝑏,𝜃=𝑎⃗ ⊥𝑏⃗ ,𝜃 + 𝑎⃗ ∥𝑏⃗
         */

        Vector b = new Vector(focus, plane.center);
        Vector a = plane.normal.copy();

    }

    @Override
    public String toString() {
        return "{" + " focus='" + focus + "'" + ", plane='" + plane + "'" + ", width='" + width + "'" + ", height='"
                + height + "'" + ", focal_length='" + focal_length + "'" + "}";
    }

}