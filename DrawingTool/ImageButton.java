import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ImageButton extends JButton {
    private StateManager stateManager;

    public ImageButton(StateManager stateManager) {
        super("Image");

        addActionListener(new ImageListener());

        this.stateManager = stateManager;
    }

    class ImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new ImageState(stateManager));
        }
    }
}

class ImageState implements State {
    private StateManager stateManager;

    public ImageState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        JFileChooser filechooser = new JFileChooser();
        int returnVal = filechooser.showOpenDialog(null); // ファイルロード用のダイアログを開く
        if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
            File file = filechooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(file);
                MyImage d = new MyImage(x, y, image.getWidth() / 2, image.getHeight() / 2, image);
                stateManager.addDrawing(d);
                d.setMouseUp();
            } catch (Exception ex) {
            }
        }
    }

    public void mouseUp(int x, int y) {
    }

    public void mouseDrag(int x, int y) {
    }
}