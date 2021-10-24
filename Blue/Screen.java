package Blue;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Screen extends JPanel {

    public BufferedImage canvas;
    int width, height;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.BLACK);
        this.setPreferredSize(new Dimension(width, height));
    }

    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void update_frame(Color[][] frame) {
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, frame[y][x].getRGB());
            }
        }
        repaint();
    }

    @Override
    public void paint(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;

        g2D.drawImage(canvas, null, null);

    }
}