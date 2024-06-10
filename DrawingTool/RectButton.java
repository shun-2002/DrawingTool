import java.awt.event.*;
import javax.swing.*;

public class RectButton extends JButton {
    private StateManager stateManager;

    public RectButton(StateManager stateManager) {
        super("Rectangle");

        addActionListener(new RectListener());

        this.stateManager = stateManager;
    }

    class RectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RectState(stateManager));
        }
    }
}

class RectState implements State {
    private StateManager stateManager;
    private MyRectangle d;

    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyRectangle(x, y, 0, 0);
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