import java.awt.event.*;
import javax.swing.*;

public class CopyButton extends JButton {
    private StateManager stateManager;

    public CopyButton(StateManager stateManager) {
        super("Copy");

        addActionListener(new CopyListener());

        this.stateManager = stateManager;
    }

    class CopyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.copy();
        }
    }
}