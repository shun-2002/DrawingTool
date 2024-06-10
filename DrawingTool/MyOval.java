import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MyOval extends MyDrawing {
    public MyOval(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyOval(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyOval(int xpt, int ypt, Color fc, Color lc) {
        super(xpt, ypt, fc, lc);
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
        g2.setColor(fc);
        g2.fillOval(x, y, w, h);
        g2.setColor(lc);
        g2.drawOval(x, y, w, h);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setRegion() {
        int gx = getRegionX();
        int gy = getRegionY();
        int gh = Math.abs(getH());
        int gw = Math.abs(getW());
        region = new Ellipse2D.Double(gx, gy, gw, gh);
    }

    public void setLoad() {
        setRegion();
    }
}
