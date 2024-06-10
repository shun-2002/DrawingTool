import java.awt.event.*;
import javax.swing.*;

public class CaptureItem extends JMenuItem {
    private StateManager stateManager;

    // Captureボタンの生成
    public CaptureItem(StateManager stateManager) {
        super("Capture");

        addActionListener(new CaptureListener());

        this.stateManager = stateManager;
    }

    // Capture関数の呼び出し
    class CaptureListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.getCanvas().Capture();
        }
    }
}
