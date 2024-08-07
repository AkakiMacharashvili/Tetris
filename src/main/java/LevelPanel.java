import javax.swing.*;
import java.awt.*;

public class LevelPanel extends JPanel implements ModelListener {
    private final JLabel levelLabel;
    private TetrisModel model;

    public LevelPanel(TetrisModel model) {
        levelLabel = new JLabel("Level: 1");
        this.add(levelLabel);
        model.addListener(this);
        this.setPreferredSize(new Dimension(200, 50)); // Adjust size as needed
    }


    @Override
    public void onChange(TetrisModel tetrisModel) {
        levelLabel.setText("Level: " + (11 - tetrisModel.level / 100));
    }

    @Override
    public void over(TetrisModel tetrisModel) {

    }
}
