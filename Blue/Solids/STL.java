package Blue.Solids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.io.*;

import Blue.Geometry.Point;
import Blue.Geometry.Vector;

public class STL {

    private static float getFloat(byte[] totalBytes, int offset) {
        byte[] bytes = new byte[4];

        for (int i = 0; i < 4; i++)
            bytes[i] = totalBytes[offset + i];

        return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    private static Vector getVector(byte[] totalBytes, int offset) {
        Vector v = new Vector();

        v.i = getFloat(totalBytes, offset);
        v.j = getFloat(totalBytes, offset + 4);
        v.k = getFloat(totalBytes, offset + 8);

        return v;
    }

    private static Triangle getTriangle(byte[] totalBytes, int offset) {

        Vector normal = getVector(totalBytes, offset);
        Point a = new Point(getVector(totalBytes, offset + 12));
        Point b = new Point(getVector(totalBytes, offset + 24));
        Point c = new Point(getVector(totalBytes, offset + 36));

        return new Triangle(a, b, c, normal);
    }

    public static List<Triangle> load(String fileName) {
        List<Triangle> triangles = new ArrayList<>();

        try {
            File file = new File(fileName);

            byte[] bytes = FileUtils.readFileToByteArray(file);

            /*
             * UINT8[80] – Header - 80 bytes
             * UINT32 – Number of triangles - 4 bytes
             * 
             * foreach triangle - 50 bytes:
             * REAL32[3] – Normal vector - 12 bytes
             * REAL32[3] – Vertex 1 - 12 bytes
             * REAL32[3] – Vertex 2 - 12 bytes
             * REAL32[3] – Vertex 3 - 12 bytes
             * UINT16 – Attribute byte count - 2 bytes
             * end
             */
            int noOfVertices = 0;

            for (int i = 83; i >= 80; i--) {
                noOfVertices = (noOfVertices << 8);
                noOfVertices |= (bytes[i] & 0xFF);
            }

            int offset = 84;

            for (int i = 0; i < noOfVertices; i++) {
                triangles.add(getTriangle(bytes, offset));
                offset += 50;
            }

            for (Triangle t : triangles) {
                System.out.println(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return triangles;

    }
}
