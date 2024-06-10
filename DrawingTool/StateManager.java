import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

public class StateManager {
    private State state; // 現在の状態
    private MyCanvas canvas; // 保持するMyCanvas
    private Mediator mediator; // 保持するMediator
    // 基本的にこれらの色などの情報はMediator内の選択されている図形と、次以降につくるMyDrawingに適用される
    private boolean isDashed = false; // 破線有無
    private boolean shadow = false; // 影の有無
    private int patternNum = 0; // 破線パターン
    private int lineCount = 1; // 線の本数
    private int lineWidth = 1; // 線の幅
    private Color lineColor = Color.black; // 線の色
    private Color fillColor = Color.white; // 塗色
    private int clickedX; // クリックされた座標
    private int clickedY;
    private int fillColortransparency = 255; // 塗色透明度
    private int lineColortransparency = 255; // 線の色透明度
    private int isAnimation = 0; // アニメーション状態

    private Timer timer1 = new Timer(); // アニメーション管理タイマー
    private int clock = 0; // ステップ管理
    private int bpm = 100; // BPM値
    private final double v_0 = -0.1; // 初速度パラメータ
    private final double g = 0.01; // 加速度パラメータ

    // コンストラクタ
    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
        this.mediator = canvas.getMediator();
        state = new RectState(this);
    }

    // Stateの設定
    public void setState(State state) {
        resetSelect();
        Stop();
        this.state = state;
        canvas.repaint();
    }

    // マウス処理
    public void mouseDown(int x, int y) {
        state.mouseDown(x, y);
        canvas.repaint();
    }

    public void mouseUp(int x, int y) {
        state.mouseUp(x, y);
        canvas.repaint();
    }

    public void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        canvas.repaint();
    }

    // 各変数のアクションリスナから呼び出す処理、受け取った変数にStateManagerの値と現在選択されているMediator内の図形の値を変更し再描画
    public void setDashed(boolean isDashed) {
        this.isDashed = isDashed;
        mediator.setselectedDashed(isDashed);
        canvas.repaint();
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
        mediator.setselectedShadow(shadow);
        canvas.repaint();
    }

    public void setPatternNum(int patternNum) {
        this.patternNum = patternNum;
        mediator.setselectedPatternNum(patternNum);
        canvas.repaint();
    }

    public void setLinewidth(int lineWidth) {
        this.lineWidth = lineWidth;
        mediator.setselectedLineWidth(lineWidth);
        canvas.repaint();
    }

    public void setLineCount(int linecount) {
        this.lineCount = linecount;
        mediator.setselectedLineCount(linecount);
        canvas.repaint();
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        mediator.setselectedLineColor(lineColor);
        canvas.repaint();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        mediator.setselectedFillColor(fillColor);
        canvas.repaint();
    }

    public void setfillColorTransparency(int fillColortransparency) {
        this.fillColortransparency = fillColortransparency;
        mediator.setselectedfillColortransparency(fillColortransparency);
        canvas.repaint();
    }

    public void setlineColorTransparency(int lineColortransparency) {
        this.lineColortransparency = lineColortransparency;
        mediator.setselectedlineColortransparency(lineColortransparency);
        canvas.repaint();
    }

    public void setisAnimation(int isAnimation) {
        this.isAnimation = isAnimation;
        mediator.setselectedDrawingsanimation(isAnimation);
    }

    // 現在のStateManager内の色を返す
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
        return lineCount;
    }

    public int getLinewidth() {
        return lineWidth;
    }

    public MyCanvas getCanvas() {
        return canvas;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public Color getLineColor() {
        return new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), lineColortransparency);
    }

    public Color getFillColor() {
        return new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColortransparency);
    }

    public int getfillColorTransparency() {
        return fillColortransparency;
    }

    public int getlineColorTransparency() {
        return lineColortransparency;
    }

    public int getisAnimation() {
        return isAnimation;
    }

    // 新たに描画されるdにStateManager内の変数を適用し、Mediatorに追加
    public void addDrawing(MyDrawing d) {
        d.setDashed(isDashed);
        d.setShadow(shadow);
        d.setPatternNum(patternNum);
        d.setLineCount(lineCount);
        d.setLineColor(lineColor);
        d.setFillColor(fillColor);
        d.setLinewidth(lineWidth);
        d.setfillColorTransparency(fillColortransparency);
        d.setlineColorTransparency(lineColortransparency);
        d.setisAnimation(isAnimation);
        mediator.addDrawing(d);
    }

    // 選択領域の表示
    public void addSelectRegion(MyDrawing d) {
        d.setDashed(true);
        d.setPatternNum(1);
        d.setLineCount(1);
        d.setLineColor(new Color(0, 0, 0, 0));
        d.setFillColor(new Color(0, 0, 0, 0));
        d.setLinewidth(1);
        d.setfillColorTransparency(0);
        d.setlineColorTransparency(255);
        d.setisAnimation(0);
        mediator.addDrawing(d);
    }

    // dをMediatorから取り除く
    public void removeDrawing(MyDrawing d) {
        mediator.removeDrawing(d);
    }

    // x,yにある図形の選択を行う。この時、クリックされた座標を保持する。
    public boolean setSelected(int x, int y) {
        mediator.setSelected(x, y);
        clickedX = x;
        clickedY = y;
        return mediator.getSelectedDrawingsExist();
    }

    // 選択されている図形が存在するかを返す
    public boolean getSelectedDrawingsExist() {
        return mediator.getSelectedDrawingsExist();
    }

    // 選択状態をリセットする
    public void resetSelect() {
        mediator.resetSelect();
        canvas.repaint();
    }

    // 選択状態の図形を移動、リサイズする
    // x,yにはドラッグされた座標が入っているため、前の地点からの差分をmediatorのmoveメソッドに渡す
    // クリックされた座標も更新する
    public void moveSelected(int x, int y) {
        int dx = x - clickedX;
        int dy = y - clickedY;
        mediator.move(dx, dy);
        clickedX = x;
        clickedY = y;
    }

    // 選択された図形を削除する
    public void removeselectedDrawing() {
        mediator.removeselectedDrawings();
        canvas.repaint();
    }

    // コピー
    public void copy() {
        mediator.copy();
    }

    // カット
    public void cut() {
        mediator.cut();
    }

    // 座標x,yを左上として貼り付け
    // 選択座標を記録
    public void paste(int x, int y) {
        if (mediator.getBufferExist()) {
            state = new SelectState(this);
            mediator.paste(x, y);
            clickedX = x;
            clickedY = y;
        }
    }

    // 領域内に含まれる図形の判定
    public void SelectinRegion(int x, int y, int w, int h) {
        mediator.SelectinRegion(x, y, w, h);
        canvas.repaint();
    }

    // セーブ
    public void Save() {
        mediator.Save();
    }

    // ロード
    public void Load() {
        mediator.Load();
        canvas.repaint();
    }

    // マウスアップ時の処理
    public void selectMouseUp() {
        mediator.selectMouseUp();
    }

    // 選択図形を先頭に持ってくる
    public void frontSelected() {
        mediator.frontSelected();
        canvas.repaint();
    }

    // x,yに位置するMyDrawingがスピンしているのかを調べる
    public void setSelectedisSpin(int x, int y) {
        mediator.setSelectedisSpin(x, y, clock);
    }

    // BPMアクセッサ
    public void setBPM(int bpm) {
        this.bpm = bpm;
    }

    // アニメーションの開始
    public void Play() {
        mediator.setNoAnimation(); // 静止状態の保存
        clock = 0; // 開始時刻
        timer1.scheduleAtFixedRate(new squad(), 0, 15); // タイマーの設定
        canvas.repaint();
    }

    public void Stop() {
        timer1.cancel(); // タイマー取り消し
        timer1 = new Timer(); // タイマーを新たにする
        mediator.stop(); // Stop処理
        canvas.repaint();
    }

    // タイマーによって呼び出されるスクワッド関数
    class squad extends TimerTask {
        public void run() {
            clock++;
            int t = clock % (60000 / (bpm * 15));
            double y = v_0 * t + g * t * t;
            double rate = 1.0 + y; // 伸縮率を決める
            if (rate > 1.0) {
                rate = 1.0;
            }
            mediator.squad(rate); // 伸縮率に合わせたスクワッド
            mediator.Spin(clock); // clockにあわせたスピン
            canvas.repaint();
        }
    }
}
