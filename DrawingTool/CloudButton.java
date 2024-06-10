import java.awt.event.*;
import javax.swing.*;

public class CloudButton extends JButton {
    private StateManager stateManager;

    public CloudButton(StateManager stateManager) {
        super("Cloud");

        addActionListener(new CloudListener());

        this.stateManager = stateManager;
    }

    class CloudListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new CloudState(stateManager));
        }
    }

}

class CloudState implements State {
    StateManager stateManager;
    MyCloud d; // 描画中の雲

    public CloudState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyCloud(x, y, 0, 0);
        stateManager.addDrawing(d);
    }

    public void mouseUp(int x, int y) {
        if (Math.abs(d.getW()) < 20 && Math.abs(d.getH()) < 20) {
            stateManager.removeDrawing(d);
        }
        d.setMouseUp();
    }

    public void mouseDrag(int x, int y) {
        d.setSize(x - d.getX(), y - d.getY());
    }
}