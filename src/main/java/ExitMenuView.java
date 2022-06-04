import javax.swing.*;
import java.awt.*;

public class ExitMenuView {
    private final JDialog jDialog;
    private final GameView gameView;

    public ExitMenuView(GameView gameView) {
        this.gameView = gameView;
        jDialog = new JDialog(gameView.getFrame(), true);
        jDialog.setLayout(new BoxLayout(jDialog.getContentPane(), BoxLayout.Y_AXIS));
        jDialog.setResizable(false);
        addComponents();
        jDialog.pack();
    }

    private void addComponents() {
        JLabel header = new JLabel("Exit Menu");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        jDialog.add(header);

        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> new SaveMenuView(jDialog, gameView).display());
        jDialog.add(saveButton);

        JButton continueButton = new JButton("Continue");
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.addActionListener(e -> jDialog.setVisible(false));
        jDialog.add(continueButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        jDialog.add(exitButton);
    }

    public void display() {
        jDialog.setVisible(true);
    }
}
