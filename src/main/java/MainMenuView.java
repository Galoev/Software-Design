import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenuView {
    private final JFrame frame;

    public MainMenuView() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setResizable(false);
        addComponents();
        frame.pack();
    }

    public void display() {
        frame.setVisible(true);
    }

    public void replaceWith(JFrame otherFrame) {
        frame.setVisible(false);
        otherFrame.setVisible(true);
    }

    private void addComponents() {
        JLabel header = new JLabel("Welcome to Roguelike!");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(header);

        JButton newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> {
            try {
                replaceWith(new GameView().getFrame());
            } catch (IOException | ClassNotFoundException ex) {
                new ErrorView(frame, ex.getMessage()).display();
            }
        });
        frame.add(newGameButton);

        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadGameButton.addActionListener(e -> {
            replaceWith(new LoadMenuView().getFrame());
        });
        frame.add(loadGameButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener( e -> {
            System.exit(0);
        });
        frame.add(exitButton);
    }
}
