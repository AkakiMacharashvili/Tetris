import javax.swing.*;
import java.awt.*;

public class View {
    static final int BOX_SIZE = 30;

    private final int ORIGIN = 50;
    private final JPanel gamePanel;
    private boolean finished = false;
    private final TetrisModel model;

    public View(JPanel gamePanel, TetrisModel model) {
        this.gamePanel = gamePanel;
        this.model = model;
    }

    public void draw(TetrisModel model, Graphics2D graphics) {
        drawData(model.field, 0, 0, true, graphics);
        drawData(model.figure, model.position.x(), model.position.y(), false, graphics);
        drawGrid(model.field.length, model.field[0].length, graphics);
        drawScore(graphics);
        drawLevel(graphics);
        if (finished) drawGameOver(graphics);
    }

    private void drawData(int[][] field, int col, int row, boolean drawBackground, Graphics2D graphics) {
        for (int r = 0; r < field.length; r++) {
            for (int c = 0; c < field[r].length; c++) {
                if (field[r][c] != 0 || drawBackground) {
                    drawBoxAt(row + r, col + c, field[r][c], graphics);
                }
            }
        }
    }

    private void drawBoxAt(int col, int row, int value, Graphics2D graphics) {
        graphics.setColor(Tetris.COLORS[value]);
        graphics.fillRect(ORIGIN + row * BOX_SIZE, ORIGIN + col * BOX_SIZE, BOX_SIZE, BOX_SIZE);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(ORIGIN + row * BOX_SIZE, ORIGIN + col * BOX_SIZE, BOX_SIZE, BOX_SIZE);
    }

    private void drawGrid(int rows, int cols, Graphics2D graphics) {
        graphics.setColor(Color.GRAY);
        for (int r = 0; r <= rows; r++) {
            graphics.drawLine(ORIGIN, ORIGIN + r * BOX_SIZE, ORIGIN + cols * BOX_SIZE, ORIGIN + r * BOX_SIZE);
        }
        for (int c = 0; c <= cols; c++) {
            graphics.drawLine(ORIGIN + c * BOX_SIZE, ORIGIN, ORIGIN + c * BOX_SIZE, ORIGIN + rows * BOX_SIZE);
        }
    }

    public void setFinish(boolean finished) {
        this.finished = finished;
        gamePanel.repaint();
    }

    private void drawGameOver(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 36));
        FontMetrics metrics = graphics.getFontMetrics();
        String message = "Game Over";
        int x = ORIGIN + (gamePanel.getWidth() - metrics.stringWidth(message)) / 2 - 50;
        int y = ORIGIN + (gamePanel.getHeight()  - metrics.getHeight()) / 2 - 125;
        graphics.drawString(message, x, y);
    }

    private void drawScore(Graphics2D graphics){
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 15));
        String message = "Score: " + model.score;
        int y = ORIGIN - 5;
        graphics.drawString(message, ORIGIN, y);
    }

    private void drawLevel(Graphics2D graphics){
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics metrics = graphics.getFontMetrics();
        String message = "Level: " + (11 - (model.level) / 100);

        int x = ORIGIN + model.field[0].length * BOX_SIZE - metrics.stringWidth(message);
        int y = ORIGIN - 5;
        graphics.drawString(message, x, y);
    }


}
