import java.awt.event.*;
import javax.swing.*;

public class FrontButton extends JButton {
    private StateManager stateManager;

    public FrontButton(StateManager stateManager) {
        super("Front");

        addActionListener(new FrontListener());

        this.stateManager = stateManager;
    }

    class FrontListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.frontSelected();
        }
    }
}