import javax.swing.*;
import javax.swing.event.*;

public class lineColorTransparencySlider implements ChangeListener {
    JSlider slider;
    JLabel label;
    private StateManager stateManager;

    public lineColorTransparencySlider(JPanel jp, StateManager stateManager) {
        slider = new JSlider(0, 255, 255);
        this.stateManager = stateManager;
        slider.addChangeListener(this);
        label = new JLabel();
        label.setText("LineColorTransparency:" + slider.getValue());
        jp.add(label);
        jp.add(slider);

    }

    public void stateChanged(ChangeEvent e) {
        stateManager.setlineColorTransparency(slider.getValue());
        label.setText("LineColorTransparency:" + slider.getValue());
    }
}
