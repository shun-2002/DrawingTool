import java.awt.event.*;
import javax.swing.*;

public class OvalButton extends JButton {
    private StateManager stateManager;

    public OvalButton(StateManager stateManager) {
        super("Oval");

        addActionListener(new OvalListener());

        this.stateManager = stateManager;
    }

    class OvalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new OvalState(stateManager));
        }
    }
}

class OvalState implements State {
    private StateManager stateManager;
    private MyOval d;

    public OvalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyOval(x, y, 0, 0);
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