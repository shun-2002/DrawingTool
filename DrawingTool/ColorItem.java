import java.awt.*;

public class ColorItem {
    private Color color; // 色
    private String string; // ラベル

    public ColorItem(String s, Color color) {
        string = s;
        this.color = color;
    }

    public String toString() {
        return string;
    }

    public Color getColor() {
        return color;
    }
}
