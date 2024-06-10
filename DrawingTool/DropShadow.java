public class DropShadow {
    private boolean shadow;
    private String string;

    public DropShadow(String s, boolean shadow) {
        string = s;
        this.shadow = shadow;
    }

    public String toString() {
        return string;
    }

    public boolean getShadow() {
        return shadow;
    }
}
