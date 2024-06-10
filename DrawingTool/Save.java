import java.awt.event.*;
import javax.swing.*;

public class Save extends JMenuItem {
    StateManager stateManager;

    public Save(StateManager stateManager) {
        super("Save");
        this.stateManager = stateManager;
        addActionListener(new SaveListener());
    }

    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.Save();
        }
    }
}
