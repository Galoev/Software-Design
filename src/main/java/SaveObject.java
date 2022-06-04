import java.io.Serializable;

public class SaveObject implements Serializable {
    private final World world;
    private final Player player;

    public SaveObject(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }
}
