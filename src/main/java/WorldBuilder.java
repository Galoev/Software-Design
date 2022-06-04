import java.util.Random;

public class WorldBuilder {
    private Tile[][] tiles;
    private final int width;
    private final int height;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
    }

    public WorldBuilder createRandomCave() {
        return randomizeTiles(42).smooth(8);
    }

    public World build() {
        return new World(tiles);
    }

    private WorldBuilder randomizeTiles(long seed) {
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile randomTile;
                if (random.nextBoolean()) {
                    randomTile = Tile.createWall(x, y);
                } else {
                    randomTile = Tile.createGround(x, y);
                }
                tiles[x][y] = randomTile;
            }
        }
        return this;
    }

    private WorldBuilder smooth(int times) {
        Tile[][] tiles2 = new Tile[width][height];
        for (int time = 0; time < times; time++) {

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int grounds = 0;
                    int walls = 0;

                    for (int ox = -1; ox < 2; ox++) {
                        for (int oy = -1; oy < 2; oy++) {
                            if (x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) continue;

                            if (tiles[x + ox][y + oy].type.equals("ground")) grounds++;
                            else walls++;
                        }
                    }
                    tiles2[x][y] = grounds >= walls ? Tile.createGround(x, y) : Tile.createWall(x, y);
                }
            }
            tiles = tiles2;
        }
        return this;
    }
}
