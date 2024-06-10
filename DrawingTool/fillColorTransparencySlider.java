import javax.swing.*;
import javax.swing.event.*;

public class fillColorTransparencySlider implements ChangeListener {
    JSlider slider;
    JLabel label;
    private StateManager stateManager;

    public fillColorTransparencySlider(JPanel jp, StateManager stateManager) {
        slider = new JSlider(0, 255, 255);
        this.stateManager = stateManager;
        slider.addChangeListener(this);
        label = new JLabel();
        label.setText("FillColorTransparency:" + slider.getValue());
        jp.add(label);
        jp.add(slider);

    }

    public void stateChanged(ChangeEvent e) {
        stateManager.setfillColorTransparency(slider.getValue());
        label.setText("FillColorTransparency:" + slider.getValue());
    }
}
