import java.awt.event.*;
import javax.swing.*;

public class SelectButton extends JButton {
    private StateManager stateManager;

    public SelectButton(StateManager stateManager) {
        super("Select");

        addActionListener(new SelectListener());

        this.stateManager = stateManager;
    }

    class SelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new SelectState(stateManager));
        }
    }
}

class SelectState implements State {
    StateManager stateManager;
    MyRectangle selectregion;

    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
        selectregion = null;
    }

    public void mouseDown(int x, int y) {
        if (!stateManager.setSelected(x, y)) { // setSelectedは座標x,yに選択図形があるかを返すため、選択図形がないときの処理
            selectregion = new MyRectangle(x, y, 0, 0);
            stateManager.addSelectRegion(selectregion); // 選択領域の追加
        }
    }

    public void mouseUp(int x, int y) {
        if (selectregion != null) {
            stateManager.removeDrawing(selectregion); // 選択領域の削除
            selectregion = null;
        }
        stateManager.selectMouseUp();
    }

    public void mouseDrag(int x, int y) {
        if (selectregion == null) { // 選択領域なし
            stateManager.moveSelected(x, y);
        } else { // 選択領域表示中
            selectregion.setSize(x - selectregion.getX(), y - selectregion.getY());
            stateManager.SelectinRegion(selectregion.getRegionX(), selectregion.getRegionY(), selectregion.getRegionW(),
                    selectregion.getRegionH());
        }
    }

}