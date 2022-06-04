import java.io.Serializable;
import java.util.Random;

public class World implements Serializable {
    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        width = tiles.length;
        height = tiles[0].length;
    }

    public Tile getTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;
        else
            return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setEmptyLocation(Entity entity) {
        Random random = new Random();
        int x;
        int y;

        do {
            x = random.nextInt(width);
            y = random.nextInt(height);
        } while (!tiles[x][y].getType().equals("ground"));

        entity.setX(x);
        entity.setY(y);
    }
}
