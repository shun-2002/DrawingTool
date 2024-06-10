import java.awt.*;
import java.io.*;
import java.util.Vector;

abstract public class MyDrawing implements Cloneable, Serializable {
    private int x, y, w, h; // x座標,y座標,幅,高さ
    private Color lineColor, fillColor; // 線の色、塗り色
    private int lineWidth; // 線の太さ
    private int AnimationMode = 0;
    transient private int spinTimer;
    transient private boolean reverse = false;
    transient private boolean isSelected = false; // 選択状態
    transient Shape region; // 包含判定用
    transient final int SIZE = 15; // 選択表示短形に付く四角形の大きさ

    private boolean isDashed = false; // 破線状態
    private boolean shadow = false; // 影
    private int patternNum = 0; // 破線パターン
    private int linecount = 1; // 線の本数
    private int fillColortransparency = 255; // 塗りつぶしの透明度
    private int lineColortransparency = 255; // 線の透明度
    private int noAnimationx, noAnimationw, noAnimationh, noAnimationy; // Animationなしの時の座標

    // コンストラクタ
    public MyDrawing() {
        x = y = 0;
        w = h = 40;
        lineColor = Color.black;
        fillColor = Color.white;
        lineWidth = 1;
        setRegion();
    }

    public MyDrawing(int xpt, int ypt, int wpt, int hpt) {
        setLocation(xpt, ypt);
        setSize(wpt, hpt);
        lineColor = Color.black;
        fillColor = Color.white;
        lineWidth = 1;
        setRegion();
    }

    public MyDrawing(int xpt, int ypt, Color fc, Color lc) {
        setLocation(xpt, ypt);
        w = h = 40;
        setFillColor(fc);
        setLineColor(lc);
        lineWidth = 1;
        setRegion();
    }

    public MyDrawing(int xpt, int ypt) {
        setLocation(xpt, ypt);
        setRegion();
    }

    // 共通の描画部分
    public void draw(Graphics g) {
        // getRegionでは領域左上の座標と、幅高さを正の値に変換し返す
        int gx = getRegionX();
        int gy = getRegionY();
        int gh = getRegionH();
        int gw = getRegionW();
        Graphics2D g2 = (Graphics2D) g;

        if (isSelected) { // 選択背れているときには枠の表示
            g2.setColor(Color.black);
            g2.fillRect(x + w / 2 - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x + w / 2 - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x + w - SIZE / 2, y + h / 2 - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x + w - SIZE / 2, y - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
            g2.fillRect(x + w - SIZE / 2, y + h - SIZE / 2, SIZE, SIZE);
        }

        // 破線パターンの変更
        if (getDashed()) {
            g2.setStroke(new MyDashStroke(getLineWidth(), getPatternNum()));
        } else {
            g2.setStroke(new BasicStroke(getLineWidth()));
        }
        // 影の表示
        if (getShadow()) {
            figure(g2, gx + 10, gy + 10, gw, gh, Color.black, Color.black);
        }
        // 線の本数に合わせた複数回描画
        // figureが各図形ごとの描画方法
        for (int i = 0; i < getLineCount(); i++) {
            if (gw - (4 * getLineWidth()) * i <= 0 || gh - (4 * getLineWidth()) * i <= 0) {
                break;
            }
            figure(g2, gx + (2 * getLineWidth()) * i, gy + (2 * getLineWidth()) * i, gw - (4 * getLineWidth()) * i,
                    gh - (4 * getLineWidth()) * i, getFillColor(), getLineColor());
        }
    }

    // 各図形ごとの描画方法
    public void figure(Graphics2D g2, int xpt, int ypt, int wpt, int hpt, Color fc, Color lc) {

    }

    // 図形の移動(dx,dyは差分)
    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        setRegion();
    }

    // 図形の移動(x,yは座標)
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        setRegion();
    }

    // サイズ変更
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
        setRegion();
    }

    // 各変数に対するアクセッサが入っている
    public void setFillColor(Color fc) {
        this.fillColor = fc;
    }

    public void setLineColor(Color lc) {
        this.lineColor = lc;
    }

    public void setLinewidth(int w) {
        this.lineWidth = w;
    }

    public void setDashed(boolean b) {
        isDashed = b;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setPatternNum(int patternNum) {
        this.patternNum = patternNum;
    }

    public void setLineCount(int linecount) {
        this.linecount = linecount;
    }

    public void setfillColorTransparency(int fillColortransparency) {
        this.fillColortransparency = fillColortransparency;
    }

    public void setlineColorTransparency(int lineColortransparency) {
        this.lineColortransparency = lineColortransparency;
    }

    public void setisAnimation(int AnimationMode) {
        this.AnimationMode = AnimationMode;
    }

    public void setSpinTimer(int spinTimer) {
        this.spinTimer = spinTimer;
    }

    public void setNoAnimationX() {
        this.noAnimationx = x;
    }

    public void setNoAnimationY() {
        this.noAnimationy = y;
    }

    public void setNoAnimationW() {
        this.noAnimationw = w;
    }

    public void setNoAnimationH() {
        this.noAnimationh = h;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public int getNoAnimationX() {
        return noAnimationx;
    }

    public int getNoAnimationY() {
        return noAnimationy;
    }

    public int getNoAnimationW() {
        return noAnimationw;
    }

    public int getNoAnimationH() {
        return noAnimationh;
    }

    public int getSpinTimer() {
        return spinTimer;
    }

    public boolean getReverse() {
        return reverse;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    // 左上のx座標を取得
    public int getRegionX() {
        int gx = getX();
        int gw = getW();
        if (gw < 0) {
            gx += gw;
            gw *= -1;
        }
        return gx;
    }

    // 左上のy座標を取得
    public int getRegionY() {
        int gy = getY();
        int gh = getH();
        if (gh < 0) {
            gy += gh;
            gh *= -1;
        }
        return gy;
    }

    // 幅を正の数で取得
    public int getRegionW() {
        return Math.abs(w);
    }

    // 高さを正の数で取得
    public int getRegionH() {
        return Math.abs(h);
    }

    // 図形の中心のx座標を取得
    public int getCenterX() {
        return getRegionX() + getRegionW() / 2;
    }

    // 図形の中心のy座標を取得
    public int getCenterY() {
        return getRegionY() + getRegionH() / 2;
    }

    // 各変数に対するアクセッサ
    public Color getFillColor() {
        return new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColortransparency);
    }

    public Color getLineColor() {
        return new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), lineColortransparency);
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public boolean getDashed() {
        return isDashed;
    }

    public boolean getShadow() {
        return shadow;
    }

    public int getPatternNum() {
        return patternNum;
    }

    public int getLineCount() {
        return linecount;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getfillColorTransparency() {
        return fillColortransparency;
    }

    public int getlineColorTransparency() {
        return lineColortransparency;
    }

    public int getisAnimation() {
        return AnimationMode;
    }

    // 領域内にx,yを含むかの判定
    abstract public boolean contains(int x, int y);

    // 領域の設定
    abstract public void setRegion();

    // 図形の枠線に表示される8個の点の領域
    public Vector<Shape> getEdgeRegion() {
        Vector<Shape> EdgeRegion = new Vector<Shape>();
        int gx = getRegionX();
        int gy = getRegionY();
        int gh = getRegionH();
        int gw = getRegionW();
        int[] xi = new int[8];
        int[] yi = new int[8];
        xi[0] = gx;
        xi[1] = gx + gw / 2;
        xi[2] = gx + gw;
        xi[3] = gx + gw;
        xi[4] = gx + gw;
        xi[5] = gx + gw / 2;
        xi[6] = gx;
        xi[7] = gx;
        yi[0] = gy;
        yi[1] = gy;
        yi[2] = gy;
        yi[3] = gy + gh / 2;
        yi[4] = gy + gh;
        yi[5] = gy + gh;
        yi[6] = gy + gh;
        yi[7] = gy + gh / 2;
        for (int i = 0; i < 8; i++) {
            EdgeRegion.add(new Rectangle(xi[i] - SIZE / 2, yi[i] - SIZE / 2, SIZE, SIZE));
        }
        return EdgeRegion;
    }

    // クローンの生成
    public MyDrawing clone() {
        try {
            return (MyDrawing) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    // マウスアップ時の処理
    public void setMouseUp() {
        setLocation(getRegionX(), getRegionY()); // 座標を左上基準に
        setSize(getRegionW(), getRegionH()); // 幅高さを正に
        setNoAnimationX(); // アニメーションなしの状態を保存
        setNoAnimationY();
        setNoAnimationW();
        setNoAnimationH();
    }

    // ロードされた時の復元用の処理
    abstract public void setLoad();
}