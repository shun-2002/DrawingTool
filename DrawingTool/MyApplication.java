import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MyApplication extends JFrame {
    private StateManager stateManager;
    private MyCanvas canvas;
    private JPanel jp;

    private JComboBox<ColorItem> linecolorjc; // 線の色選択
    private JComboBox<ColorItem> fillcolorjc; // 塗色選択
    private JComboBox<ColorItem> backgroungcolorjc; // 背景色選択
    private JComboBox<LineWidth> linewidthjc; // 線の幅指定
    private JComboBox<LineCount> linecountjc; // 線の本数指定
    private JComboBox<DropShadow> dropshadowjc; // 影の有無
    private JComboBox<Dash> dashjc; // 破線パターン
    private JComboBox<isAnimation> animationjc; // アニメーションの有無

    private JMenuBar menubar;
    private JMenu file;
    private Save save;
    private Load load;
    private CaptureItem capture;
    private PrintItem print;

    public MyApplication() {
        super("My Paint Application");

        canvas = new MyCanvas(); // 描画を行うキャンバス

        // 各コンボボックスの定義
        linecolorjc = new JComboBox<ColorItem>();
        fillcolorjc = new JComboBox<ColorItem>();
        backgroungcolorjc = new JComboBox<ColorItem>();
        linewidthjc = new JComboBox<LineWidth>();
        linecountjc = new JComboBox<LineCount>();
        dropshadowjc = new JComboBox<DropShadow>();
        dashjc = new JComboBox<Dash>();
        animationjc = new JComboBox<isAnimation>();

        // メニューバー(印刷、保存などの選択)の配置
        menubar = new JMenuBar();
        setJMenuBar(menubar);

        // 役割ごとのjpanelの設定
        JPanel jp = new JPanel(); // 全体用
        jp.setLayout(new GridLayout(4, 2));
        JPanel jp_Drawing = new JPanel(); // 描画図形種類の選択
        jp_Drawing.setLayout(new FlowLayout());
        JPanel jp_lineproperty = new JPanel(); // 線のプロパティ
        jp_lineproperty.setLayout(new FlowLayout());
        JPanel jp_select = new JPanel(); // キャンバス内の図形選択
        jp_select.setLayout(new FlowLayout());
        JPanel jp_fillColor = new JPanel(); // 塗色
        jp_fillColor.setLayout(new FlowLayout());
        JPanel jp_lineColor = new JPanel(); // 線の色
        jp_lineColor.setLayout(new FlowLayout());
        JPanel jp_BackGroundColor = new JPanel(); // 背景色
        jp_BackGroundColor.setLayout(new FlowLayout());
        JPanel jp_animation = new JPanel(); // アニメーション関連
        jp_animation.setLayout(new FlowLayout());

        stateManager = new StateManager(canvas);

        // MyDrawing
        RectButton rectButton = new RectButton(stateManager); // 長方形
        jp_Drawing.add(rectButton);
        FlowerButton flowerButton = new FlowerButton(stateManager); // 花
        jp_Drawing.add(flowerButton);
        OvalButton ovalButton = new OvalButton(stateManager); // 楕円
        jp_Drawing.add(ovalButton);
        HendecagonalButton hendecagonalButton = new HendecagonalButton(stateManager); // 十一角形
        jp_Drawing.add(hendecagonalButton);
        CloudButton cloudButton = new CloudButton(stateManager); // 雲
        jp_Drawing.add(cloudButton);
        ImageButton imageButton = new ImageButton(stateManager); // 任意の画像
        jp_Drawing.add(imageButton);

        // lineColor
        ColorItem color_black = new ColorItem("Black", Color.black);
        ColorItem color_white = new ColorItem("White", Color.white);
        ColorItem color_red = new ColorItem("Red", Color.red);
        ColorItem color_blue = new ColorItem("Blue", Color.blue);
        ColorItem color_green = new ColorItem("Green", Color.green);
        ColorItem color_yellow = new ColorItem("Yellow", Color.yellow);
        ColorItem color_other = new ColorItem("Other Colors", null);

        JLabel lb_lineColor = new JLabel("Line Color");
        linecolorjc.addActionListener(new LineColorListener());
        linecolorjc.addItem(color_black);
        linecolorjc.addItem(color_white);
        linecolorjc.addItem(color_red);
        linecolorjc.addItem(color_blue);
        linecolorjc.addItem(color_green);
        linecolorjc.addItem(color_yellow);
        linecolorjc.addItem(color_other);

        jp_lineColor.add(lb_lineColor);
        jp_lineColor.add(linecolorjc);
        new lineColorTransparencySlider(jp_lineColor, stateManager);

        // fillColor
        JLabel lb_fillColor = new JLabel("Fill Color");
        fillcolorjc.addActionListener(new FillColorListener());
        fillcolorjc.addItem(color_white);
        fillcolorjc.addItem(color_black);
        fillcolorjc.addItem(color_red);
        fillcolorjc.addItem(color_blue);
        fillcolorjc.addItem(color_green);
        fillcolorjc.addItem(color_yellow);
        fillcolorjc.addItem(color_other);

        jp_fillColor.add(lb_fillColor);
        jp_fillColor.add(fillcolorjc);
        new fillColorTransparencySlider(jp_fillColor, stateManager);

        // BackGroungColor

        JLabel lb_backgroundColor = new JLabel("BackGround Color");
        backgroungcolorjc.addActionListener(new BackGroundColorListener());
        backgroungcolorjc.addItem(color_white);
        backgroungcolorjc.addItem(color_black);
        backgroungcolorjc.addItem(color_red);
        backgroungcolorjc.addItem(color_blue);
        backgroungcolorjc.addItem(color_green);
        backgroungcolorjc.addItem(color_yellow);
        backgroungcolorjc.addItem(color_other);

        jp_BackGroundColor.add(lb_backgroundColor);
        jp_BackGroundColor.add(backgroungcolorjc);

        // lineproperty
        JLabel lb_pattern = new JLabel("Line Pattern");
        jp_lineproperty.add(lb_pattern);
        dashjc.addActionListener(new DashListener());
        Dash pattern1 = new Dash("Pattern1", 0, true);
        Dash pattern2 = new Dash("Pattern2", 1, true);
        Dash pattern3 = new Dash("Pattern3", 2, true);
        Dash solidline = new Dash("Solid Line", 0, false);
        dashjc.addItem(solidline);
        dashjc.addItem(pattern1);
        dashjc.addItem(pattern2);
        dashjc.addItem(pattern3);
        jp_lineproperty.add(dashjc);

        JLabel lb_lineWidth = new JLabel("LineWidth");
        jp_lineproperty.add(lb_lineWidth);
        LineWidth linewidth_1 = new LineWidth("1", 1);
        LineWidth linewidth_3 = new LineWidth("3", 3);
        LineWidth linewidth_6 = new LineWidth("6", 6);
        LineWidth linewidth_9 = new LineWidth("9", 9);
        LineWidth linewidth_12 = new LineWidth("12", 12);
        LineWidth linewidth_15 = new LineWidth("15", 15);
        LineWidth linewidth_18 = new LineWidth("18", 18);
        LineWidth linewidth_21 = new LineWidth("21", 21);
        LineWidth linewidth_24 = new LineWidth("24", 24);
        LineWidth linewidth_27 = new LineWidth("27", 27);

        linewidthjc.addActionListener(new LineWidthListener());
        linewidthjc.addItem(linewidth_1);
        linewidthjc.addItem(linewidth_3);
        linewidthjc.addItem(linewidth_6);
        linewidthjc.addItem(linewidth_9);
        linewidthjc.addItem(linewidth_12);
        linewidthjc.addItem(linewidth_15);
        linewidthjc.addItem(linewidth_18);
        linewidthjc.addItem(linewidth_21);
        linewidthjc.addItem(linewidth_24);
        linewidthjc.addItem(linewidth_27);
        jp_lineproperty.add(linewidthjc);

        JLabel lb_lineCount = new JLabel("LineCount");
        jp_lineproperty.add(lb_lineCount);
        LineCount linecount_1 = new LineCount("1", 1);
        LineCount linecount_2 = new LineCount("2", 2);
        LineCount linecount_3 = new LineCount("3", 3);
        LineCount linecount_4 = new LineCount("4", 4);
        LineCount linecount_5 = new LineCount("5", 5);
        LineCount linecount_6 = new LineCount("6", 6);
        LineCount linecount_7 = new LineCount("7", 7);
        LineCount linecount_8 = new LineCount("8", 8);
        LineCount linecount_9 = new LineCount("9", 9);
        LineCount linecount_10 = new LineCount("10", 10);

        linecountjc.addActionListener(new LineCountListener());
        linecountjc.addItem(linecount_1);
        linecountjc.addItem(linecount_2);
        linecountjc.addItem(linecount_3);
        linecountjc.addItem(linecount_4);
        linecountjc.addItem(linecount_5);
        linecountjc.addItem(linecount_6);
        linecountjc.addItem(linecount_7);
        linecountjc.addItem(linecount_8);
        linecountjc.addItem(linecount_9);
        linecountjc.addItem(linecount_10);
        jp_lineproperty.add(linecountjc);

        JLabel lb_shadow = new JLabel("Drop Shadow");
        jp_lineproperty.add(lb_shadow);

        DropShadow dropshadow_true = new DropShadow("True", true);
        DropShadow dropshadow_false = new DropShadow("False", false);
        dropshadowjc.addActionListener(new DropShadowListener());
        dropshadowjc.addItem(dropshadow_false);
        dropshadowjc.addItem(dropshadow_true);
        jp_lineproperty.add(dropshadowjc);

        // Select
        SelectButton selectButton = new SelectButton(stateManager); // 図形選択
        jp_select.add(selectButton);
        DeleteButton deleteButton = new DeleteButton(stateManager); // 選択図形の削除
        jp_select.add(deleteButton);
        CopyButton copyButton = new CopyButton(stateManager); // 選択図形のコピー(ペーストは右クリック)
        jp_select.add(copyButton);
        CutButton cutButton = new CutButton(stateManager); // 選択図形のカット(ペーストは右クリック)
        jp_select.add(cutButton);
        FrontButton topButton = new FrontButton(stateManager); // 選択した図形を最前面に持ってくる
        jp_select.add(topButton);

        // Animation
        JLabel lb_animation = new JLabel("Animation");
        jp_animation.add(lb_animation);
        isAnimation animation_on = new isAnimation("Off", 0);
        isAnimation animation_off = new isAnimation("On", 1);
        animationjc.addActionListener(new AnimationListener());
        animationjc.addItem(animation_on);
        animationjc.addItem(animation_off);
        jp_animation.add(animationjc);
        PlayButton playButton = new PlayButton(stateManager);
        jp_animation.add(playButton);
        StopButton stopButton = new StopButton(stateManager);
        jp_animation.add(stopButton);
        new BPMSlider(jp_animation, stateManager);

        jp.add(jp_Drawing);
        jp.add(jp_fillColor);
        jp.add(jp_lineproperty);
        jp.add(jp_lineColor);
        jp.add(jp_select);
        jp.add(jp_BackGroundColor);
        jp.add(jp_animation);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        file = new File(stateManager);
        save = new Save(stateManager);
        load = new Load(stateManager);
        print = new PrintItem(stateManager);
        capture = new CaptureItem(stateManager);
        file.add(save);
        file.add(load);
        file.add(print);
        file.add(capture);
        menubar.add(file);

        // マウス処理
        canvas.addMouseListener(new MouseAdapter() {
            // 現在の状態の mousedown 処理の呼び出し
            public void mousePressed(MouseEvent e) {
                if (canvas.getMediator().getBufferExist()) { // コピーされている図形あり
                    switch (e.getButton()) {
                        case MouseEvent.BUTTON1: // 左クリック、通常処理
                            stateManager.mouseDown(e.getX(), e.getY());
                            break;
                        case MouseEvent.BUTTON3:// 右クリック、貼り付け
                            stateManager.Stop();
                            stateManager.paste(e.getX(), e.getY());
                            break;
                    }
                } else { // コピーされている図形なし
                    stateManager.mouseDown(e.getX(), e.getY());
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            // 現在の状態の mouseup 処理の呼び出し
            public void mouseReleased(MouseEvent e) {
                stateManager.mouseUp(e.getX(), e.getY());
            }
        });

        canvas.addMouseMotionListener(new MouseMotionListener() {
            // mousedrag処理の呼び出し
            public void mouseDragged(MouseEvent e) {
                stateManager.mouseDrag(e.getX(), e.getY());
            }

            public void mouseMoved(MouseEvent e) {
            }
        });

        this.addWindowListener(new WindowAdapter() {
            // ウィンドウを閉じたら終了
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // 各コンボボックスに対するアクションリスナ
    // それぞれ、コンボボックスの選択された要素の変数をstateManagerに渡し、StateManagerが続きの処理を行う。
    class LineColorListener implements ActionListener {
        Color lc;

        public void actionPerformed(ActionEvent e) {
            lc = linecolorjc.getItemAt(linecolorjc.getSelectedIndex()).getColor();
            if (lc == null) {
                lc = JColorChooser.showDialog(null, "LineColorChooser", stateManager.getLineColor());
                if (lc == null) {
                    lc = stateManager.getLineColor();
                }
            }
            stateManager.setLineColor(lc);
        }
    }

    class FillColorListener implements ActionListener {
        Color fc;

        public void actionPerformed(ActionEvent e) {
            fc = fillcolorjc.getItemAt(fillcolorjc.getSelectedIndex()).getColor();
            if (fc == null) {
                fc = JColorChooser.showDialog(null, "FillColorChooser", stateManager.getFillColor());
                if (fc == null) {
                    fc = stateManager.getFillColor();
                }
            }
            stateManager.setFillColor(fc);
        }
    }

    class BackGroundColorListener implements ActionListener {
        Color bc;

        public void actionPerformed(ActionEvent e) {
            bc = backgroungcolorjc.getItemAt(backgroungcolorjc.getSelectedIndex()).getColor();
            if (bc == null) {
                bc = JColorChooser.showDialog(null, "BackGroundColorChooser", canvas.getBackGroundColor());
                if (bc == null) {
                    bc = canvas.getBackGroundColor();
                }
            }
            canvas.setBackGroundColor(bc);
        }
    }

    class LineWidthListener implements ActionListener {
        int linewidth;

        public void actionPerformed(ActionEvent e) {
            linewidth = linewidthjc.getItemAt(linewidthjc.getSelectedIndex()).getLineWidth();
            stateManager.setLinewidth(linewidth);
        }
    }

    class LineCountListener implements ActionListener {
        int linecount;

        public void actionPerformed(ActionEvent e) {
            linecount = linecountjc.getItemAt(linecountjc.getSelectedIndex()).getLineCount();
            stateManager.setLineCount(linecount);
        }
    }

    class DropShadowListener implements ActionListener {
        boolean shadow;

        public void actionPerformed(ActionEvent e) {
            shadow = dropshadowjc.getItemAt(dropshadowjc.getSelectedIndex()).getShadow();
            stateManager.setShadow(shadow);
        }
    }

    class DashListener implements ActionListener {
        boolean isDashed;
        int patternNum;

        public void actionPerformed(ActionEvent e) {
            isDashed = dashjc.getItemAt(dashjc.getSelectedIndex()).getisDashed();
            patternNum = dashjc.getItemAt(dashjc.getSelectedIndex()).getPatternNum();
            stateManager.setDashed(isDashed);
            stateManager.setPatternNum(patternNum);
        }
    }

    class AnimationListener implements ActionListener {
        int isAnimation;

        public void actionPerformed(ActionEvent e) {
            isAnimation = animationjc.getItemAt(animationjc.getSelectedIndex()).getisAnimation();
            stateManager.setisAnimation(isAnimation);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(1400, 1000);
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }

    public JPanel getJPanel() {
        return jp;
    }
}
