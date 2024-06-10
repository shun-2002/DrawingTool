import java.awt.*;

public class MyFlower extends MyDrawing {

    public MyFlower(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyFlower(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyFlower(int xpt, int ypt, Color fc, Color lc) {
        super(xpt, ypt, fc, lc);
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
        g2.setColor(fc);
        g2.fillArc(x, y + 3 * h / 8, w / 2, h / 4, 0, 180);
        g2.fillArc(x, y + 3 * h / 8, w / 2, h / 4, 0, -180);
        g2.fillArc(x + w / 2, y + 3 * h / 8, w / 2, h / 4, 0, 180);
        g2.fillArc(x + w / 2, y + 3 * h / 8, w / 2, h / 4, 0, -180);
        g2.fillArc(x + 3 * w / 8, y, w / 4, h / 2, 90, 270);
        g2.fillArc(x + 3 * w / 8, y, w / 4, h / 2, 90, -90);
        g2.fillArc(x + 3 * w / 8, y + h / 2, w / 4, h / 2, 90, 270);
        g2.fillArc(x + 3 * w / 8, y + h / 2, w / 4, h / 2, 90, -90);
        g2.setColor(lc);
        g2.drawArc(x, y + 3 * h / 8, w / 2, h / 4, 0, 180);
        g2.drawArc(x, y + 3 * h / 8, w / 2, h / 4, 0, -180);
        g2.drawArc(x + w / 2, y + 3 * h / 8, w / 2, h / 4, 0, 180);
        g2.drawArc(x + w / 2, y + 3 * h / 8, w / 2, h / 4, 0, -180);
        g2.drawArc(x + 3 * w / 8, y, w / 4, h / 2, 90, 270);
        g2.drawArc(x + 3 * w / 8, y, w / 4, h / 2, 90, -90);
        g2.drawArc(x + 3 * w / 8, y + h / 2, w / 4, h / 2, 90, 270);
        g2.drawArc(x + 3 * w / 8, y + h / 2, w / 4, h / 2, 90, -90);
        g2.setColor(fc);
        g2.fillOval(x + 3 * w / 8, y + 3 * h / 8, w / 4, h / 4);
        g2.setColor(lc);
        g2.drawOval(x + 3 * w / 8, y + 3 * h / 8, w / 4, h / 4);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setRegion() {
        int x = getRegionX();
        int y = getRegionY();
        int h = getRegionH();
        int w = getRegionW();
        int[] xi = new int[12];
        int[] yi = new int[12];
        xi[0] = x + 3 * w / 8;
        xi[1] = x;
        xi[2] = x;
        xi[3] = x + 3 * w / 8;
        xi[4] = x + 3 * w / 8;
        xi[5] = x + 5 * w / 8;
        xi[6] = x + 5 * w / 8;
        xi[7] = x + w;
        xi[8] = x + w;
        xi[9] = x + 5 * w / 8;
        xi[10] = x + 5 * w / 8;
        xi[11] = x + 3 * w / 8;
        yi[0] = y + 3 * h / 8;
        yi[1] = y + 3 * h / 8;
        yi[2] = y + 5 * h / 8;
        yi[3] = y + 5 * h / 8;
        yi[4] = y + h;
        yi[5] = y + h;
        yi[6] = y + 5 * h / 8;
        yi[7] = y + 5 * h / 8;
        yi[8] = y + 3 * h / 8;
        yi[9] = y + 3 * h / 8;
        yi[10] = y;
        yi[11] = y;
        region = new Polygon(xi, yi, 12);
    }

    public void setLoad() {
        setRegion();
    }
}
