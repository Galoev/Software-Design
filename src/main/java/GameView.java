import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import javax.swing.*;
import java.io.IOException;

public class GameView {
    private final JFrame frame;
    private final AsciiPanel terminal;
    private final GameLogic gameLogic;
    public final int TERMINAL_WIDTH = 80;
    public final int TERMINAL_HEIGHT = 40;

    public GameView() throws IOException, ClassNotFoundException {
        this("");
    }

    public GameView(String saveName) throws IOException, ClassNotFoundException {
        frame = new JFrame();
        terminal = new AsciiPanel(TERMINAL_WIDTH, TERMINAL_HEIGHT, AsciiFont.CP437_16x16);
        gameLogic = new GameLogic(terminal, TERMINAL_WIDTH, TERMINAL_HEIGHT, this, saveName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(gameLogic);
        frame.add(terminal);
        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void display() {
        frame.setVisible(true);
    }

    public void callExitMenu() {
        new ExitMenuView(this).display();
    }

    public void saveGame(String saveName) throws IOException {
        gameLogic.saveGame(saveName);
    }
}
