import java.awt.*;
import java.io.Serializable;

public class Tile extends Entity implements Serializable {
    private final Color foreground;
    private final Color background;

    public Tile(String type, char symbol, int x, int y, Color foreground, Color background) {
        super(type, symbol, x, y);
        this.foreground = foreground;
        this.background = background;
    }

    public static Tile createWall(int x, int y) {
        return new Tile("wall", '#', x, y, Color.GREEN, Color.BLACK);
    }

    public static Tile createGround(int x, int y) {
        return new Tile("ground", '.', x, y, Color.BLACK, Color.BLACK);
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }
}
