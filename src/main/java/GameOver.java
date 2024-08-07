import javax.swing.*;

public class GameOver implements ModelListener {
    private final JButton refreshButton;
    private final JButton quitButton;

    public GameOver(TetrisModel model, JButton refreshButton, JButton quitButton) {
        this.refreshButton = refreshButton;
        this.quitButton = quitButton;
        this.refreshButton.setVisible(false);
        this.quitButton.setVisible(false);
        model.addListener(this);
    }

    @Override
    public void onChange(TetrisModel tetrisModel) {
        // Do nothing
    }

    @Override
    public void over(TetrisModel tetrisModel) {
        refreshButton.setVisible(true);
        quitButton.setVisible(true);
    }
}
