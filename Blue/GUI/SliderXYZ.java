package Blue.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import Blue.Geometry.Point;

public class SliderXYZ implements ChangeListener {

    JFrame frame;
    JPanel panel;
    JLabel labelX, labelY, labelZ;
    JSlider sliderX, sliderY, sliderZ;
    String title;
    String parm_name;
    Callable update_function;

    public SliderXYZ(Callable update_function, String title, Point min, Point max, Point initial, String parm_name) {

        this.title = title;
        this.parm_name = parm_name;
        this.update_function = update_function;
        frame = new JFrame(title);
        panel = new JPanel();
        labelX = new JLabel("X");
        labelY = new JLabel("Y");
        labelZ = new JLabel("Z");
        sliderX = new JSlider((int) min.x, (int) max.x, (int) initial.x);
        sliderY = new JSlider((int) min.y, (int) max.y, (int) initial.y);
        sliderZ = new JSlider((int) min.z, (int) max.z, (int) initial.z);

        add_components(sliderX, (int) (max.x - min.x) / 10, (int) (max.x - min.x) / 5);
        add_components(sliderY, (int) (max.y - min.y) / 10, (int) (max.y - min.y) / 5);
        add_components(sliderZ, (int) (max.z - min.z) / 10, (int) (max.z - min.z) / 5);

        labelX.setText("X = " + (int) initial.x);
        labelY.setText("Y = " + (int) initial.y);
        labelZ.setText("Z = " + (int) initial.z);

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

    public SliderXYZ(Callable update_function, String title, int min, int max, int initial, String param_name) {

        this.title = title;
        this.update_function = update_function;
        this.parm_name = param_name;
        frame = new JFrame(title);
        panel = new JPanel();
        labelX = new JLabel("X");
        labelY = new JLabel("Y");
        labelZ = new JLabel("Z");
        int mid = (min + max) / 2;
        sliderX = new JSlider(min, max, initial);
        sliderY = new JSlider(min, max, initial);
        sliderZ = new JSlider(min, max, initial);

        int minor_tick_spacing = (max - min) / 10;
        int major_tick_spacing = (max - mid) / 5;

        add_components(sliderX, minor_tick_spacing, major_tick_spacing);
        add_components(sliderY, minor_tick_spacing, major_tick_spacing);
        add_components(sliderZ, minor_tick_spacing, major_tick_spacing);

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

    private void add_components(JSlider slider, int minor_tick_spacing, int major_tick_spacing) {
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(minor_tick_spacing);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(major_tick_spacing);

        slider.setPaintLabels(true);

        slider.setPreferredSize(new Dimension(480, 120));

        slider.addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int x = sliderX.getValue(), y = sliderY.getValue(), z = sliderZ.getValue();
        labelX.setText("X = " + x);
        labelY.setText("Y = " + y);
        labelZ.setText("Z = " + z);
        update_function.transform(new Point(x, y, z), parm_name);
    }

}