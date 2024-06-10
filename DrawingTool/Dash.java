public class Dash {
    private int patternNum;
    private boolean isDashed;
    private String string;

    public Dash(String s, int patternNum, boolean isDashed) {
        string = s;
        this.patternNum = patternNum;
        this.isDashed = isDashed;
    }

    public String toString() {
        return string;
    }

    public boolean getisDashed() {
        return isDashed;
    }

    public int getPatternNum() {
        return patternNum;
    }
}
