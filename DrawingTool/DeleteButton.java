import java.awt.event.*;
import javax.swing.*;

public class DeleteButton extends JButton {
    private StateManager stateManager;

    public DeleteButton(StateManager stateManager) {
        super("Delete");

        addActionListener(new DeleteListener());

        this.stateManager = stateManager;
    }

    class DeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.removeselectedDrawing();
        }
    }
}
