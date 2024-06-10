import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImage extends MyDrawing {
    transient BufferedImage image; // イメージ(表)
    transient BufferedImage reverseimage; // イメージ(裏)
    int[][] imagearray; // 画像セーブ用配列(表)

    // コンストラクタ
    public MyImage(int xpt, int ypt, int wpt, int hpt, BufferedImage image) {
        super(xpt, ypt, wpt, hpt);
        this.image = image;
        int width = image.getWidth();
        int height = image.getHeight();
        reverseimage = new BufferedImage(width, height, 6);
        imagearray = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = image.getRGB(j, i);
                imagearray[j][i] = color; // 画像の配列保存
                reverseimage.setRGB(width - 1 - j, i, color);
            }
        }

    }

    public void draw(Graphics g) { // 透明度非対応としたため、drawの上書き
        int gx = getRegionX();
        int gy = getRegionY();
        int gh = getRegionH();
        int gw = getRegionW();
        Graphics2D g2 = (Graphics2D) g;

        if (getSelected()) {
            g2.setColor(Color.black);
            g2.fillRect(gx - SIZE / 2, gy - SIZE / 2, SIZE, SIZE); // 0
            g2.fillRect(gx + gw / 2 - SIZE / 2, gy - SIZE / 2, SIZE, SIZE); // 1
            g2.fillRect(gx + gw - SIZE / 2, gy - SIZE / 2, SIZE, SIZE); // 2
            g2.fillRect(gx + gw - SIZE / 2, gy + gh / 2 - SIZE / 2, SIZE, SIZE); // 3
            g2.fillRect(gx + gw - SIZE / 2, gy + gh - SIZE / 2, SIZE, SIZE); // 4
            g2.fillRect(gx + gw / 2 - SIZE / 2, gy + gh - SIZE / 2, SIZE, SIZE); // 5
            g2.fillRect(gx - SIZE / 2, gy + gh - SIZE / 2, SIZE, SIZE); // 6
            g2.fillRect(gx - SIZE / 2, gy + gh / 2 - SIZE / 2, SIZE, SIZE); // 7
        }

        if (getDashed()) {
            g2.setStroke(new MyDashStroke(getLineWidth(), getPatternNum()));
        } else {
            g2.setStroke(new BasicStroke(getLineWidth()));
        }
        if (getShadow()) {
            figure(g2, gx + 10, gy + 10, gw, gh, Color.black, Color.black);
        }
        int i;
        for (i = 0; i < getLineCount(); i++) {
            if (gw - (4 * getLineWidth()) * i <= 0 || gh - (4 * getLineWidth()) * i <= 0) {
                break;
            }
            figure(g2, gx + (2 * getLineWidth()) * i, gy + (2 * getLineWidth()) * i, gw - (4 * getLineWidth()) * i,
                    gh - (4 * getLineWidth()) * i, getFillColor(), getLineColor());
        }
        i--;
        figure(g2, gx + (2 * getLineWidth()) * i, gy + (2 * getLineWidth()) * i, gw - (4 * getLineWidth()) * i,
                gh - (4 * getLineWidth()) * i, new Color(0, 0, 0, 0), getLineColor());
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
        // File入力
        if (getReverse()) { // 裏面
            g2.drawImage(reverseimage, x, y, w, h, null);
        } else { // 表面
            g2.drawImage(image, x, y, w, h, null);
        }
        g2.setColor(fc);
        g2.fillRect(x, y, w, h);
        g2.setColor(lc);
        g2.drawRect(x, y, w, h);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setRegion() {
        int gx = getRegionX();
        int gy = getRegionY();
        int gh = getRegionH();
        int gw = getRegionW();
        region = new Rectangle(gx, gy, gw, gh);
    }

    public void setLoad() {
        setRegion();
        int width = imagearray.length;
        int height = imagearray[0].length;
        image = new BufferedImage(width, height, 6);
        reverseimage = new BufferedImage(width, height, 6);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) { // 配列情報からのイメージの復元
                int color1 = imagearray[j][i];
                image.setRGB(j, i, color1);
                reverseimage.setRGB(width - 1 - j, i, color1);
            }
        }
    }
}
