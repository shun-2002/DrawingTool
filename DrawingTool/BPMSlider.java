import javax.swing.*;
import javax.swing.event.*;

// BPMを変更するスライダー
public class BPMSlider implements ChangeListener {
    JSlider slider; // BPM 値のスライダー
    JLabel label; // スライダーのラベル
    private StateManager stateManager;

    // スライダーの生成
    public BPMSlider(JPanel jp, StateManager stateManager) {
        slider = new JSlider(1, 200, 100);
        this.stateManager = stateManager;
        slider.addChangeListener(this);
        label = new JLabel();
        label.setText("BPM:" + slider.getValue());
        jp.add(label);
        jp.add(slider);

    }

    // スライダー変更時の処理
    public void stateChanged(ChangeEvent e) {
        stateManager.setBPM(slider.getValue());
        label.setText("BPM:" + slider.getValue());
    }
}
