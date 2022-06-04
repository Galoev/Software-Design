import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SaveMenuView {
    private final JDialog jDialog;
    private final GameView gameView;

    public SaveMenuView(JDialog owner, GameView gameView) {
        jDialog = new JDialog(owner, true);
        this.gameView = gameView;
        jDialog.setLayout(new BoxLayout(jDialog.getContentPane(), BoxLayout.Y_AXIS));
        jDialog.setResizable(false);
        addComponents();
        jDialog.pack();
    }

    private void addComponents() {
        JLabel header = new JLabel("Save Game!");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        jDialog.add(header);

        JTextField jTextField = new JTextField(20);
        jTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        jDialog.add(jTextField);

        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> {
            try {
                gameView.saveGame(jTextField.getText());
                jDialog.setVisible(false);
            } catch (IOException ex) {
                new ErrorView(jDialog, ex.getMessage()).display();
            }
        });
        jDialog.add(saveButton);

    }

    public void display() {
        jDialog.setVisible(true);
    }
}
