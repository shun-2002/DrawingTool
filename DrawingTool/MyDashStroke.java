import java.awt.*;

public class MyDashStroke extends BasicStroke {
    private static float pattern[][] = { { 10, 15 }, { 5, 5 }, { 10, 5 } };

    public MyDashStroke(float lineWidth, int PatternNum) {
        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, pattern[PatternNum], 0);
    }
}
