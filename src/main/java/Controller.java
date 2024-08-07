import javax.swing.*;

public class Controller implements ModelListener, GameEventListener {
    TetrisModel model;
    View view;
    JPanel gamePanel;

    public Controller(TetrisModel model, View view, JPanel gamePanel) {
        this.view = view;
        this.model = model;
        this.gamePanel = gamePanel;
        model.addListener(this);
    }

    public void infinite() {
        model.infinite();
    }

    @Override
    public void slideDown() {
        model.slideDown();
    }

    @Override
    public void moveLeft() {
        model.moveLeft();
    }

    @Override
    public void moveRight() {
        model.moveRight();
    }

    @Override
    public void rotate() {
        model.rotate();
    }

    @Override
    public void drop() {
        model.drop();
    }

    @Override
    public void onChange(TetrisModel tetrisModel) {
        view.setFinish(model.finished);
        gamePanel.repaint();
    }

    @Override
    public void over(TetrisModel tetrisModel) {
        view.setFinish(model.finished);
        gamePanel.repaint();
    }

    public void levelUp() {
        model.levelUp();
    }

    public void levelDown() {
        model.levelDown();
    }
}
