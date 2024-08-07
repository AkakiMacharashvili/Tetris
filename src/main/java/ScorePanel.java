import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel implements ModelListener {

    private final JLabel scoreLabel;

    public ScorePanel(TetrisModel model) {
        scoreLabel = new JLabel("Score: 0");
        this.add(scoreLabel);
        this.setPreferredSize(new Dimension(200, 50));
        model.addListener(this);
    }

    @Override
    public void onChange(TetrisModel model) {
        scoreLabel.setText("Score: " + model.score);
    }

    @Override
    public void over(TetrisModel tetrisModel) {

    }


}
