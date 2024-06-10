import java.awt.event.*;
import javax.swing.*;

public class StopButton extends JButton {
    private StateManager stateManager;

    public StopButton(StateManager stateManager) {
        super("Stop");

        addActionListener(new StopListener());

        this.stateManager = stateManager;
    }

    class StopListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.Stop();
            stateManager.setState(new SelectState(stateManager));
        }
    }
}