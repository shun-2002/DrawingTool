import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFileChooser;

public class Mediator {
    private Vector<MyDrawing> drawings;
    private MyCanvas canvas;
    private Vector<MyDrawing> selectedDrawings;
    private Vector<MyDrawing> buffer; // Cut,Copyバッファ
    private int resizeNumber;

    // Mediatorの生成
    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new Vector<MyDrawing>();
        selectedDrawings = new Vector<MyDrawing>();
        buffer = new Vector<MyDrawing>();
    }

    // 描画されているMyDrawingを返す
    public Enumeration<MyDrawing> drawingsElements() {
        return drawings.elements();
    }

    // 図形dを追加
    public void addDrawing(MyDrawing d) {
        drawings.add(d);
    }

    // 図形dを削除
    public void removeDrawing(MyDrawing d) {
        drawings.remove(d);
    }

    // 選択されている図形を削除
    public void removeselectedDrawings() {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                drawings.remove(selectedDrawing); // ひとつずつ図形を削除
            }
            selectedDrawings.clear(); // 選択図形配列を空にする
        }
    }

    // 選択図形配列にdを追加
    public void setSelectedDrawings(MyDrawing d) {
        selectedDrawings.add(d);
        d.setSelected(true);
    }

    // MyCanvasを返す
    public MyCanvas getCanvas() {
        return canvas;
    }

    // 選択図形が存在するのかを返す
    public boolean getSelectedDrawingsExist() {
        if (selectedDrawings.isEmpty()) {
            return false;
        }
        return true;
    }

    // コピーされている図形があるのかを返す
    public boolean getBufferExist() {
        if (buffer.isEmpty()) {
            return false;
        }
        return true;
    }

    // 選択されている図形を返す
    public Vector<MyDrawing> getSelectedDrawings() {
        return selectedDrawings;
    }

    // 選択図形に対して、マウスアップ時の領域再設定などの処理を行う
    public void selectMouseUp() {
        for (MyDrawing d : selectedDrawings) {
            d.setMouseUp();
        }
    }

    // 状態に合わせた処理を行う
    public void move(int dx, int dy) {
        if (resizeNumber >= 0) { // この時は選択されている図形に対してリサイズを行う。リサイズはひとつの図形選択時のみ可能
            MyDrawing d = selectedDrawings.get(0);
            int gw = d.getW();
            int gh = d.getH();
            switch (resizeNumber) {
                case 0:
                    d.move(dx, dy);
                    gw -= dx;
                    gh -= dy;
                    break;
                case 1:
                    d.move(0, dy);
                    gh -= dy;
                    break;
                case 2:
                    d.move(0, dy);
                    gw += dx;
                    gh -= dy;
                    break;
                case 3:
                    gw += dx;
                    break;
                case 4:
                    gw += dx;
                    gh += dy;
                    break;
                case 5:
                    gh += dy;
                    break;
                case 6:
                    d.move(dx, 0);
                    gw -= dx;
                    gh += dy;
                    break;
                case 7:
                    d.move(dx, 0);
                    gw -= dx;
                    break;
            }
            d.setSize(gw, gh);
        } else { // ひとつずつ選択図形を移動、これは複数図形への同時実行可
            if (!selectedDrawings.isEmpty()) {
                for (MyDrawing selectedDrawing : selectedDrawings) {
                    selectedDrawing.move(dx, dy);
                }
            }
        }
    }

    // MyCanvasのrepaintを行う
    public void repaint() {
        canvas.repaint();
    }

    // クリックした座標のひとつの図形の選択を設定する
    public void setSelected(int x, int y) {
        for (int i = drawings.size() - 1; i >= 0; i--) {
            MyDrawing d = drawings.get(i);
            if (selectedDrawings.contains(d)) { // すでに選択済み
                Vector<Shape> shape = d.getEdgeRegion(); // 四つ角と辺の中点の領域
                for (int j = 0; j < 8; j++) {
                    if (shape.get(j).contains(x, y)) { // 四つ角または中点を選択
                        resetSelect();
                        setSelectedDrawings(d); // 選択図形をdのみにする
                        resizeNumber = j; // 点に合わせたリサイズモード番号
                        return;
                    }
                }
            }
            resizeNumber = -1;
            if (d.contains(x, y)) {
                if (selectedDrawings.contains(d)) {
                    // この時dは選択済みであったものの、上と違い8つのリサイズ用の点は選択されず。
                    // 元々の選択図形配列を維持
                    return;
                } else {
                    resetSelect();
                    setSelectedDrawings(d); // 選択状態をdのみに更新
                    return;
                }
            }
        }
        resetSelect(); // クリックされたところにdがなくリセット
        return;
    }

    // 選択領域(位置x,y,幅w,高さh)に図形がすべて収まっているか判定
    public void SelectinRegion(int x, int y, int w, int h) {
        resetSelect(); // 選択状態のリセット
        for (int i = 0; i < drawings.size() - 1; i++) {
            MyDrawing d = drawings.get(i); // 各Drawingsについて
            if (d.getX() >= x && d.getY() >= y && d.getX() + d.getW() <= x + w
                    && d.getY() + d.getH() <= y + h) {
                setSelectedDrawings(d); // 領域内に収まっていれば追加
            }
        }
    }

    // 選択済みの図形を一番前面に描画する
    public void frontSelected() {
        if (!selectedDrawings.isEmpty()) {
            for (int i = 0; i < selectedDrawings.size(); i++) {
                MyDrawing newselectedDrawing = selectedDrawings.get(i).clone();
                drawings.remove(selectedDrawings.get(i));
                drawings.add(newselectedDrawing);
                selectedDrawings.set(i, newselectedDrawing);
                // 配列の後ろにあるものほど前面に描画されるので、cloneを前面に配置し元々のものを削除
            }
        }
    }

    // 選択図形のリセット
    public void resetSelect() {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setSelected(false); // 各図形の選択状態をfalseに
            }
            selectedDrawings.clear();
        }
    }

    // 選択図形の線の色の変更
    public void setselectedLineColor(Color color) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setLineColor(color);
            }
        }
    }

    // 選択図形の塗り色の変更
    public void setselectedFillColor(Color color) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setFillColor(color);
            }
        }
    }

    // 選択図形の実線破線の変更
    public void setselectedDashed(boolean dashed) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setDashed(dashed);
            }
        }
    }

    // 選択図形の影の有無の変更
    public void setselectedShadow(boolean shadow) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setShadow(shadow);
            }
        }
    }

    // 選択図形の破線パターンの変更
    public void setselectedPatternNum(int PatternNum) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setPatternNum(PatternNum);
            }
        }
    }

    // 選択図形の線の幅の変更
    public void setselectedLineWidth(int lineWidth) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setLinewidth(lineWidth);
            }
        }
    }

    // 選択図形の線の本数の変更
    public void setselectedLineCount(int linecount) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setLineCount(linecount);
            }
        }
    }

    // 選択図形の塗色の透明度の変更
    public void setselectedfillColortransparency(int fillColortransparency) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setfillColorTransparency(fillColortransparency);
            }
        }
    }

    // 選択図形の線の色の透明度の変更
    public void setselectedlineColortransparency(int lineColortransparency) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setlineColorTransparency(lineColortransparency);
            }
        }
    }

    // 選択図形のアニメーション対象か否かの変更
    public void setselectedDrawingsanimation(int isAnimation) {
        if (!selectedDrawings.isEmpty()) {
            for (MyDrawing selectedDrawing : selectedDrawings) {
                selectedDrawing.setisAnimation(isAnimation);
            }
        }
    }

    // コピー図形の削除
    public void clearBuffer() {
        buffer.clear();
    }

    // コピー
    public void copy() {
        if (!selectedDrawings.isEmpty()) {
            // バッファをクリアする
            clearBuffer();
            for (MyDrawing selectedDrawing : selectedDrawings) {
                buffer.add(selectedDrawing.clone()); // クローンを配列に保存
            }
        }
    }

    // カット
    public void cut() {
        if (!selectedDrawings.isEmpty()) {
            // バッファをクリアする
            clearBuffer();
            for (MyDrawing selectedDrawing : selectedDrawings) {
                buffer.add(selectedDrawing.clone()); // クローンを配列に保存
            }
            removeselectedDrawings(); // drawingsからselectedDrawingを削除。
            canvas.repaint();
        }
    }

    // 貼り付け
    public void paste(int x, int y) {
        if (!buffer.isEmpty()) {
            resetSelect();
            int rx = Integer.MAX_VALUE;
            int ry = Integer.MAX_VALUE;
            for (MyDrawing b : buffer) {
                if (b.getRegionX() < rx) {
                    rx = b.getRegionX();
                }
                if (b.getRegionY() < ry) {
                    ry = b.getRegionY();
                }
                // 全図形で一番左上に近い座標を基準にする
            }
            for (MyDrawing b : buffer) {
                MyDrawing clone = (MyDrawing) b.clone();
                clone.setLocation(x + clone.getX() - rx, y + clone.getY() - ry); // クリックした位置を左上として、位置関係を維持してペースト
                addDrawing(clone);
                setSelectedDrawings(clone);
                clone.setSelected(true);
            }
            repaint();
        }
    }

    // セーブファイルの読み込み
    public void Load() {
        // File入力
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null); // ファイルロード用のダイアログを開く
        if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
            File file = fc.getSelectedFile();
            try {
                FileInputStream fin = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fin);

                drawings = (Vector<MyDrawing>) in.readObject(); // 図形配列の復元
                for (MyDrawing drawing : drawings) {
                    drawing.setLoad();
                }
                resetSelect();
                canvas.setBackGroundColor((Color) in.readObject()); // 背景色の復元
                fin.close();
            } catch (Exception ex) {
            }
        }
    }

    // セーブ
    public void Save() {
        // File出力
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null); // ファイルセーブ用のダイアログを開く
        if (returnVal == JFileChooser.APPROVE_OPTION) { // OKボタンが押されたとき
            File file = fc.getSelectedFile();
            try {
                FileOutputStream fout = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fout);

                out.writeObject(drawings); // 描画されている図形配列の保存
                out.writeObject(canvas.getBackGroundColor()); // 背景色の保存
                out.flush();

                fout.close();
            } catch (Exception ex) {
            }
        }
    }

    // アニメーション内のスクワット
    public void squad(double rate) { // rateはStateManager内のタイマーに合わせている
        if (!drawings.isEmpty()) {
            for (MyDrawing d : drawings) {
                if (d.getisAnimation() == 1) {
                    int gh = d.getH();
                    int gw = d.getW();
                    int s = d.getNoAnimationH();
                    int g = (int) (rate * s);
                    int dy = gh - g;
                    d.move(0, dy);
                    gh -= dy;
                    d.setSize(gw, gh); // 移動後リサイズし位置を維持
                }
            }
        }
    }

    // 静止状態における位置を定める
    public void setNoAnimation() {
        if (!drawings.isEmpty()) {
            for (MyDrawing d : drawings) {
                d.setNoAnimationX();
                d.setNoAnimationY();
                d.setNoAnimationW();
                d.setNoAnimationH();
            }
        }
    }

    // アニメーションを停止する
    public void stop() {
        if (!drawings.isEmpty()) {
            for (MyDrawing d : drawings) {
                if (d.getisAnimation() != 0) {
                    d.setLocation(d.getNoAnimationX(), d.getNoAnimationY());
                    d.setSize(d.getNoAnimationW(), d.getNoAnimationH());
                    d.setisAnimation(1);
                }
            }
        }
    }

    // AnimationがONになっている図形をドラッグまたはクリックしたときにスピンを開始する処理
    public void setSelectedisSpin(int x, int y, int i) {
        for (int j = drawings.size() - 1; j >= 0; j--) {
            MyDrawing d = drawings.get(j);
            if (d.contains(x, y)) { // 座標に図形発見
                if (d.getisAnimation() == 1) { // 現在スピンしていないかつAnimationがON
                    d.setisAnimation(2);
                    d.setSpinTimer(i); // スピンにかかる時間を図形ごとに計測
                    d.setSize(d.getNoAnimationW(), d.getNoAnimationH());
                    d.setLocation(d.getNoAnimationX(), d.getNoAnimationY());
                }
                return;
            }
        }
    }

    // AnimationがONになっている図形のスピン描画を管理する
    public void Spin(int spintimer) {
        int spendtime = 28;
        double v_0 = -15.0;
        double g = 0.5;
        // spintimerがどれだけ経過しているかに合わせて、横方向への伸縮、縦方向への移動距離、裏面と表面のどちらを描画するかを管理する
        for (int j = drawings.size() - 1; j >= 0; j--) {
            MyDrawing d = drawings.get(j);
            if (d.getisAnimation() == 2) {
                int start = d.getSpinTimer();
                int dw = d.getNoAnimationW() / (spendtime / 4);
                int now = spintimer - start;
                int dy = (int) (v_0 * now + g * now * now) - (int) (v_0 * (now - 1) + g * (now - 1) * (now - 1));
                if (now <= spendtime / 4) {
                    d.setReverse(false);
                    d.setSize(d.getW() - dw, d.getH());
                    d.move(dw / 2, dy);
                } else if (now <= spendtime / 2) {
                    d.setReverse(true);
                    d.setSize(d.getW() + dw, d.getH());
                    d.move(-dw / 2, dy);
                } else if (now <= (3 * spendtime) / 4) {
                    d.setReverse(true);
                    d.setSize(d.getW() - dw, d.getH());
                    d.move(dw / 2, dy);
                } else if (now <= spendtime) {
                    d.setReverse(false);
                    d.setSize(d.getW() + dw, d.getH());
                    d.move(-dw / 2, dy);
                } else { // アニメーション終了
                    d.setisAnimation(1);
                    d.setLocation(d.getNoAnimationX(), d.getNoAnimationY());
                    d.setSize(d.getNoAnimationW(), d.getNoAnimationH());
                }
            }
        }

    }

}