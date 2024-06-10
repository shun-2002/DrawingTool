import java.awt.*;

public class MyHendecagonal extends MyDrawing {

    public MyHendecagonal(int xpt, int ypt, int wpt, int hpt) {
        super(xpt, ypt, wpt, hpt);
    }

    public MyHendecagonal(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyHendecagonal(int xpt, int ypt, Color fc, Color lc) {
        super(xpt, ypt, fc, lc);
    }

    public void figure(Graphics2D g2, int x, int y, int w, int h, Color fc, Color lc) {
        int[] xi = new int[11];
        int[] yi = new int[11];
        for (int i = 0; i < 11; i++) {
            xi[i] = (int) (x + w / 2.0 + (w / 2.0) * Math.cos(i * (2 * 3.1415 / 11)));
            yi[i] = (int) (y + h / 2.0 + (h / 2.0) * Math.sin(i * (2 * 3.1415 / 11)));
        }
        g2.setColor(fc);
        g2.fillPolygon(xi, yi, 11);
        g2.setColor(lc);
        g2.drawPolygon(xi, yi, 11);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

    public void setRegion() {
        int w = getW();
        int h = getH();
        int x = getX();
        int y = getY();
        int[] xi = new int[11];
        int[] yi = new int[11];
        for (int i = 0; i < 11; i++) {
            xi[i] = (int) (x + w / 2.0 + (w / 2.0) * Math.cos(i * (2 * 3.1415 / 11)));
            yi[i] = (int) (y + h / 2.0 + (h / 2.0) * Math.sin(i * (2 * 3.1415 / 11)));
        }
        region = new Polygon(xi, yi, 11);
    }

    // public void setMouseUp() {
    // int rw = getRegionW();
    // int rh = getRegionH();
    // int r = Math.min(rw, rh);
    // int rx = getRegionX();
    // int ry = getRegionY();
    // setSize(r, r);
    // setLocation(rx + (rw / 2 - r / 2), ry + (rh / 2 - r / 2));
    // }

    public void setLoad() {
        setRegion();
    }
}
