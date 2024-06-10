import java.awt.event.*;
import javax.swing.*;

public class HendecagonalButton extends JButton {
    private StateManager stateManager;

    public HendecagonalButton(StateManager stateManager) {
        super("Hendecagonal");

        addActionListener(new HendecagonalListener());

        this.stateManager = stateManager;
    }

    class HendecagonalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new HendecagonalState(stateManager));
        }
    }

}

class HendecagonalState implements State {
    StateManager stateManager;
    MyHendecagonal d;

    public HendecagonalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyHendecagonal(x, y, 0, 0);
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