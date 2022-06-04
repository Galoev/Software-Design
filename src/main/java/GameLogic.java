import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GameLogic implements KeyListener {
    private final AsciiPanel terminal;
    private World world;
    private Player player;
    private final int screenWidth;
    private final int screenHeight;
    private final GameView gameView;

    public GameLogic(AsciiPanel terminal, int screenWidth, int screenHeight, GameView gameView, String saveName) throws IOException, ClassNotFoundException {
        this.terminal = terminal;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.gameView = gameView;
        createWorld(saveName);
        updateCamera(player.x, player.y);
    }

    private void createWorld(String saveName) throws IOException, ClassNotFoundException {
        if(saveName.equals("")) {
            world = new WorldBuilder(screenWidth * 4, screenHeight * 4).createRandomCave().build();
            player = new Player("player", 0, 0, world);
            world.setEmptyLocation(player);
            return;
        }
        SaveObject save = SaveController.loadWorld(saveName);
        world = save.getWorld();
        player = save.getPlayer();
    }

    public void updateCamera(int xFocus, int yFocus) {
        Point origin = getCameraOrigin(xFocus, yFocus);
        displayTiles(origin.x, origin.y);
        terminal.repaint();
    }

    public void saveGame(String saveName) throws IOException {
        SaveController.saveGame(new SaveObject(world, player), saveName);
    }

    private Point getCameraOrigin(int xFocus, int yFocus) {
        int spx = Math.max(0, Math.min(xFocus - screenWidth / 2, world.getWidth() - screenWidth));
        int spy = Math.max(0, Math.min(yFocus - screenHeight / 2, world.getHeight() - screenHeight));
        return new Point(spx, spy);
    }

    private void displayTiles(int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                writeTile(world.getTile(wx, wy), x, y);
            }
        }

        terminal.write(player.getSymbol(), player.getX() - left, player.getY() - top, player.getColor());
    }

    private void writeTile(Tile tile, int x, int y) {
        terminal.write(tile.getSymbol(), x, y, tile.getForeground(), tile.getBackground());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                player.moveUp();
                updateCamera(player.getX(), player.getY());
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                player.moveDown();
                updateCamera(player.getX(), player.getY());
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                player.moveRight();
                updateCamera(player.getX(), player.getY());
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                player.moveLeft();
                updateCamera(player.getX(), player.getY());
            }
            case KeyEvent.VK_ESCAPE -> {
                gameView.callExitMenu();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}
}
