import java.awt.event.*;
import javax.swing.*;

public class Load extends JMenuItem {
    StateManager stateManager;

    public Load(StateManager stateManager) {
        super("Load");
        this.stateManager = stateManager;
        addActionListener(new LoadListener());

    }

    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.Load();
        }
    }
}
