import java.awt.*;
import java.io.Serializable;

public class Player extends Entity implements Serializable {
    private final World world;
    private final Color color = Color.RED;
    public Player(String name, int x, int y, World world) {
        super(name, '@', x, y);
        this.world = world;
    }

    public void moveUp() {
        int nextY = y - 1;
        if (nextY < 0 || nextY >= world.getHeight() || world.getTile(x, y - 1).getType().equals("wall")) return;
        y = nextY;
    }

    public void moveDown() {
        int nextY = y + 1;
        if (nextY < 0 || nextY >= world.getHeight() || world.getTile(x, y + 1).getType().equals("wall")) return;
        y = nextY;
    }

    public void moveRight() {
        int nextX = x + 1;
        if (nextX < 0 || nextX >= world.getWidth() || world.getTile(x + 1, y).getType().equals("wall")) return;
        x = nextX;
    }

    public void moveLeft() {
        int nextX = x - 1;
        if (nextX < 0 || nextX >= world.getWidth() || world.getTile(x - 1, y).getType().equals("wall")) return;
        x = nextX;
    }

    public Color getColor() {
        return color;
    }
}
