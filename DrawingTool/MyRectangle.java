import java.awt.*;

public class MyRectangle extends MyDrawing {
    public MyRectangle(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyRectangle(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyRectangle(int xpt, int ypt, Color fc, Color lc) {
        super(xpt, ypt, fc, lc);
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
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
        int gh = Math.abs(getH());
        int gw = Math.abs(getW());
        region = new Rectangle(gx, gy, gw, gh);
    }

    public void setLoad() {
        setRegion();
    }

}
