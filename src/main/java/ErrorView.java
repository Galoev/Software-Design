import javax.swing.*;
import java.awt.*;

public class ErrorView {
    private final JDialog jDialog;

    public ErrorView(JDialog owner, String errMsg) {
        jDialog = new JDialog(owner, "ERROR", true);
        init(errMsg);
    }

    public ErrorView(JFrame owner, String errMsg) {
        jDialog = new JDialog(owner, "ERROR", true);
        init(errMsg);
    }

    private void init(String errMsg) {
        jDialog.setLayout(new BoxLayout(jDialog.getContentPane(), BoxLayout.Y_AXIS));
        jDialog.setResizable(false);
        JLabel jLabel = new JLabel(errMsg);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jDialog.add(jLabel);
        jDialog.pack();
    }

    public void display() {
        jDialog.setVisible(true);
    }
}
