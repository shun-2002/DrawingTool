import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class File extends JMenu {
    StateManager stateManager;

    public File(StateManager stateManager) {
        super("File");
        this.stateManager = stateManager;
        addMenuListener(new FileListener());
    }

    // メニューが選択された時に、アニメーションを止める
    class FileListener implements MenuListener {
        public void menuSelected(MenuEvent e) {
            stateManager.Stop();
        }

        public void menuCanceled(MenuEvent e) {

        }

        public void menuDeselected(MenuEvent e) {
        }
    }

}