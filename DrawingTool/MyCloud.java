import java.awt.*;

public class MyCloud extends MyDrawing {

    public MyCloud(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyCloud(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyCloud(int xpt, int ypt, Color fc, Color lc) {
        super(xpt, ypt, fc, lc);
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
        g2.setColor(fc);
        g2.fillArc(x, y + h / 3, w / 2, 2 * h / 3, 90, 180);
        g2.fillArc(x + w / 4, y, w / 2, 2 * h / 3, 0, 180);
        g2.fillArc(x + w / 2, y + h / 3, w / 2, 2 * h / 3, 90, -180);
        g2.fillRect(-1 + x + w / 4, -1 + y + h / 3, 2 + w / 2, 2 + 2 * h / 3);
        g2.setColor(lc);
        g2.drawArc(x, y + h / 3, w / 2, 2 * h / 3, 90, 180);
        g2.drawArc(x + w / 4, y, w / 2, 2 * h / 3, 0, 180);
        g2.drawArc(x + w / 2, y + h / 3, w / 2, 2 * h / 3, 90, -180);
        g2.drawLine(x + w / 4, y + h, x + 3 * w / 4, y + h);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setRegion() {
        int x = getRegionX();
        int y = getRegionY();
        int h = getRegionH();
        int w = getRegionW();
        int[] xi = new int[8];
        int[] yi = new int[8];
        xi[0] = x + w / 4;
        xi[1] = x;
        xi[2] = x;
        xi[3] = x + w;
        xi[4] = x + w;
        xi[5] = x + 3 * w / 4;
        xi[6] = x + 3 * w / 4;
        xi[7] = x + w / 4;
        yi[0] = y + h / 3;
        yi[1] = y + h / 3;
        yi[2] = y + h;
        yi[3] = y + h;
        yi[4] = y + h / 3;
        yi[5] = y + h / 3;
        yi[6] = y;
        yi[7] = y;
        region = new Polygon(xi, yi, 8);
    }

    public void setLoad() {
        setRegion();
    }
}
