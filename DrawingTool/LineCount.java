public class LineCount {
    private int linecount;
    private String string;

    public LineCount(String s, int linecount) {
        string = s;
        this.linecount = linecount;
    }

    public String toString() {
        return string;
    }

    public int getLineCount() {
        return linecount;
    }
}