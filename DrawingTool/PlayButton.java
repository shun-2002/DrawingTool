import java.awt.event.*;
import javax.swing.*;

public class PlayButton extends JButton {
    private StateManager stateManager;

    public PlayButton(StateManager stateManager) {
        super("Play");

        addActionListener(new PlayListener());

        this.stateManager = stateManager;
    }

    class PlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new AnimationState(stateManager));
            stateManager.Play();
        }
    }
}

class AnimationState implements State {
    private StateManager stateManager;

    public AnimationState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        stateManager.setSelectedisSpin(x, y);
    }

    public void mouseUp(int x, int y) {
    }

    public void mouseDrag(int x, int y) {
        stateManager.setSelectedisSpin(x, y);
    }

}