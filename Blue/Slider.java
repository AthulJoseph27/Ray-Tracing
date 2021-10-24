package Blue;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Slider implements ChangeListener {

    JFrame frame;
    JPanel panel;
    JLabel labelX, labelY, labelZ;
    JSlider sliderX, sliderY, sliderZ;
    Camera cam;

    public Slider(Camera cam) {

        this.cam = cam;
        frame = new JFrame("Rotation");
        panel = new JPanel();
        labelX = new JLabel("X");
        labelY = new JLabel("Y");
        labelZ = new JLabel("Z");
        sliderX = new JSlider(0, 360, 0);
        sliderY = new JSlider(0, 360, 0);
        sliderZ = new JSlider(0, 360, 0);

        add_components(sliderX);
        add_components(sliderY);
        add_components(sliderZ);

        labelX.setText("X = " + sliderX.getValue());
        labelY.setText("Y = " + sliderX.getValue());
        labelZ.setText("Z = " + sliderX.getValue());

        panel.add(sliderX);
        panel.add(sliderY);
        panel.add(sliderZ);

        panel.add(labelX);
        panel.add(labelY);
        panel.add(labelZ);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }

    private void add_components(JSlider slider) {
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(15);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(45);

        slider.setPaintLabels(true);

        slider.setPreferredSize(new Dimension(480, 120));

        slider.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        labelX.setText("X = " + sliderX.getValue());
        labelY.setText("Y = " + sliderY.getValue());
        labelZ.setText("Z = " + sliderZ.getValue());
        cam.update_angle(Math.toRadians(sliderX.getValue()), Math.toRadians(sliderY.getValue()),
                Math.toRadians(sliderZ.getValue()));
        cam.clear_frame();
    }

}