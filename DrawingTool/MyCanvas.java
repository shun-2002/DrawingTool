import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.print.*;
import java.io.File;

public class MyCanvas extends JPanel implements Printable {
    private Mediator mediator;
    private Color backgroundColor;

    public MyCanvas() {
        this.mediator = new Mediator(this);
        setBackground(Color.white);
    }

    public Mediator getMediator() {
        return mediator;
    }

    // キャンバスへの描画を行う
    public void paint(Graphics g) {
        super.paint(g);

        Enumeration<MyDrawing> e = mediator.drawingsElements();
        while (e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            d.draw(g);
        }
    }

    // 印刷を行う関数
    public int print(Graphics g, PageFormat fmt, int pageIndex) {
        if (pageIndex >= 1) { // 最初のページのみを印刷
            return NO_SUCH_PAGE;
        }
        Enumeration<MyDrawing> e = mediator.drawingsElements();
        Graphics2D g2 = (Graphics2D) g;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double s = Math.min(fmt.getHeight() / getHeight(), fmt.getWidth() / getWidth()); // 画面サイズに合わせた調整
        g2.setColor(backgroundColor);
        g2.scale(s, s);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawRect(0, 0, getWidth(), getHeight());
        while (e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            d.draw(g2);
        }
        g2.scale(1 / s, 1 / s);
        g2.setColor(Color.white);
        g2.fillRect((int) (getWidth() * s), 0, screenSize.width, screenSize.height);
        g2.drawRect((int) (getWidth() * s), 0, screenSize.width, screenSize.height);
        g2.fillRect(0, (int) (getHeight() * s), screenSize.width, screenSize.height);
        g2.drawRect(0, (int) (getHeight() * s), screenSize.width, screenSize.height);
        return Printable.PAGE_EXISTS;
    }

    // スクリーンショットを撮影し、pngファイルとして保存
    public void Capture() {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null); // ファイルセーブ用のダイアログを開く
        if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
            File file = fc.getSelectedFile();
            BufferedImage bi = new BufferedImage(getWidth(), getHeight(), 6);
            try {
                Enumeration<MyDrawing> e = mediator.drawingsElements();
                Graphics2D g2 = bi.createGraphics();
                g2.setColor(backgroundColor);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.drawRect(0, 0, getWidth(), getHeight());
                while (e.hasMoreElements()) {
                    MyDrawing d = e.nextElement();
                    d.draw(g2);
                }
                ImageIO.write(bi, "png", file);
            } catch (Exception ex) {
            }
        }
    }

    public void setBackGroundColor(Color color) {
        backgroundColor = color;
        setBackground(color);
    }

    public Color getBackGroundColor() {
        return backgroundColor;
    }

}
