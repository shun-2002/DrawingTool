public class LineWidth {
    private int linewidth;
    private String string;

    public LineWidth(String s, int linewidth) {
        string = s;
        this.linewidth = linewidth;
    }

    public String toString() {
        return string;
    }

    public int getLineWidth() {
        return linewidth;
    }
}
