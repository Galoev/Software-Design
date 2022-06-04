import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class LoadMenuView {
    private final JFrame frame;

    public LoadMenuView() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setResizable(false);
        addComponents();
        frame.pack();
    }

    private void addComponents() {
        JLabel header = new JLabel("Load Menu");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(header);

        try {
            List<String> saves = SaveController.getListOfSaves();
            DefaultListModel<String> listModel = new DefaultListModel<>();
            listModel.addAll(saves);
            JList<String> jList = new JList<>(listModel);
            jList.setAlignmentX(Component.CENTER_ALIGNMENT);
            frame.add(jList);

            JButton loadButton = new JButton("Load");
            loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            loadButton.addActionListener( e -> {
                if (jList.getSelectedIndex() != -1) {
                    String saveName = jList.getSelectedValue();
                    try {
                        replaceWith(new GameView(saveName).getFrame());
                    } catch (IOException | ClassNotFoundException ex) {
                        new ErrorView(frame, ex.getMessage()).display();
                    }
                }
            });
            frame.add(loadButton);
        } catch (IOException e) {
            new ErrorView(frame, e.getMessage()).display();
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public void replaceWith(JFrame otherFrame) {
        frame.setVisible(false);
        otherFrame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
}
