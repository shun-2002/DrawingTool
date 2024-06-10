import java.awt.event.*;
import javax.swing.*;

public class CutButton extends JButton {
    private StateManager stateManager;

    public CutButton(StateManager stateManager) {
        super("Cut");

        addActionListener(new CutListener());

        this.stateManager = stateManager;
    }

    class CutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.cut();
        }
    }
}
