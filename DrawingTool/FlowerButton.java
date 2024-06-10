import java.awt.event.*;
import javax.swing.*;

public class FlowerButton extends JButton {
    private StateManager stateManager;

    // ボタンの生成
    public FlowerButton(StateManager stateManager) {
        super("Flower");

        addActionListener(new FlowerListener());

        this.stateManager = stateManager;
    }

    // アクションリスナでStateの変更
    class FlowerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new FlowerState(stateManager));
        }
    }

}

class FlowerState implements State {
    StateManager stateManager;
    MyFlower d; // 現在描画中の花

    public FlowerState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    // マウスを押した時、描画の追加
    public void mouseDown(int x, int y) {
        d = new MyFlower(x, y, 0, 0);
        stateManager.addDrawing(d);
    }

    // マウスを離したとき、サイズを確認してサイズが合わなければ描画の削除
    public void mouseUp(int x, int y) {
        if (Math.abs(d.getW()) < 20 && Math.abs(d.getH()) < 20) {
            stateManager.removeDrawing(d);
        }
        d.setMouseUp(); // 領域等の設定
    }

    // マウスドラッグしたとき、リサイズ
    public void mouseDrag(int x, int y) {
        d.setSize(x - d.getX(), y - d.getY());
    }
}