import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

public class TetrisModel implements GameEventListener {
    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_WIDTH = 10;
    public static final int DEFAULT_COLOR_NUMBER = 7;
    public static final int DEFAULT_NEXT_LEVEL = 100;
    public static final int DEFAULT_MAX_LEVEL = 1000;

    final private int width;
    final private int height;
    public int[][] figure;
    public Pair position;
    public long level = 1000;
    public long maxLevel = 1000;
    public boolean FirstTry = false;
    int score = 0;

    int maxColors;
    public int[][] field;
    List<ModelListener> listeners = new ArrayList<>();
    public boolean finished = false;
    public boolean paused = false;

    public void initFigure() {
        deleteFullRows();
        field = shiftRowsDown(field);
        figure = new FigureFactory().createNextFigure();
        position = new Pair(this.width / 2 - 2, 0);
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    public TetrisModel(int width, int height, int maxColor) {
        this.width = width;
        this.height = height;
        this.field = new int[height][width];
        this.maxColors = maxColor;
        initFigure();
    }

    public Pair size() {
        return new Pair(width, height);
    }

    public void notifyListeners() {
        listeners.forEach(listener -> listener.onChange(this));
    }

    public void updateScore(int total) {
        score += total;
        notifyListeners();
    }

    public void infinite() {
        if (!paused) {
            if (FirstTry) FirstTry = false;
            else {
                if (!finished) slideDown();
                else itsOver();
            }
        }
    }

    private void itsOver() {
        listeners.forEach(listener -> listener.over(this));
    }

    @Override
    public void slideDown() {
        var newPosition = new Pair(position.x(), position.y() + 1);
        if (isNewFigurePositionValid(newPosition)) {
            position = newPosition;
            notifyListeners();
        } else {
            stay();
        }
    }

    public void stay() {
        pasteFigure();
        initFigure();
        notifyListeners();
        if (!isNewFigurePositionValid(position)) {
            gameOver();
        }

        updateScore(10);
        nextLevel();
    }

    public void nextLevel() {
        if (this.score % DEFAULT_NEXT_LEVEL == 0) {
            int current = this.score / DEFAULT_NEXT_LEVEL;
            if (current < 1000 / DEFAULT_NEXT_LEVEL) {
                this.level = Math.min(this.level, 1000 - current * DEFAULT_NEXT_LEVEL);
                this.maxLevel = 1000 - current * DEFAULT_NEXT_LEVEL;
            }
        }
    }

    public void gameOver() {
        finished = true;
        itsOver();
    }

    public int[][] shiftRowsDown(int[][] field) {
        for (int row = field.length - 2; row >= 0; row--) {
            int current = row;
            while (current < field.length - 1 && isRowFullOfZeros(current + 1)) {
                System.arraycopy(field[current], 0, field[current + 1], 0, field[current].length);
                Arrays.fill(field[current], 0);
                current++;
            }
        }
        return field;
    }

    public void deleteFullRows() {
        for (int row = 0; row < field.length; row++) {
            if (isRowFullOfOnes(row)) {
                Arrays.fill(field[row], 0);
            }
        }
    }

    public boolean isNewFigurePositionValid(Pair pair) {
        boolean[] result = new boolean[1];
        result[0] = true;

        walkThroughAllFigureCells(pair, (absPos, _) -> {
            if (result[0]) {
                result[0] = checkAbsPos(absPos);
            }
        });

        return result[0];
    }

    @Override
    public void moveLeft() {
        var newPosition = new Pair(position.x() - 1, position.y());
        if (isNewFigurePositionValid(newPosition)) {
            position = newPosition;
            notifyListeners();
        }
    }

    @Override
    public void moveRight() {
        var newPosition = new Pair(position.x() + 1, position.y());
        if (isNewFigurePositionValid(newPosition)) {
            position = newPosition;
            notifyListeners();
        }
    }

    @Override
    public void drop() {
        Pair newPosition = new Pair(position.x(), position.y() + 1);
        while (isNewFigurePositionValid(newPosition)) {
            position = newPosition;
            newPosition = new Pair(position.x(), position.y() + 1);
        }
    }

    @Override
    public void rotate() {
        tryRotation();
        notifyListeners();
    }

    public void tryRotation() {
        int[][] rotated = new int[figure.length][figure[0].length];
        int[][] prev = figure;
        for (int row = 0; row < figure.length; row++) {
            for (int col = 0; col < figure[row].length; col++) {
                rotated[col][3 - row] = figure[row][col];
            }
        }
        figure = rotated;
        if (!isNewFigurePositionValid(position)) {
            figure = prev;
        }
    }

    public boolean checkAbsPos(Pair absPos) {
        int absX = absPos.x();
        int absY = absPos.y();
        if (0 > absX || absX >= this.width) {
            return false;
        }
        if (0 > absY || absY >= this.height) {
            return false;
        }

        return field[absY][absX] == 0;
    }

    public void walkThroughAllFigureCells(Pair position, BiConsumer<Pair, Pair> payload) {
        for (int row = 0; row < figure.length; row++) {
            for (int col = 0; col < figure[row].length; col++) {
                if (figure[row][col] == 0) continue;

                int absRow = position.y() + row;
                int absCol = position.x() + col;
                payload.accept(new Pair(absCol, absRow), new Pair(col, row));
            }
        }
    }

    public void pasteFigure() {
        walkThroughAllFigureCells(position, (absPos, relPos) -> field[absPos.y()][absPos.x()] = figure[relPos.y()][relPos.x()]);
    }


    public boolean isRowFullOfOnes(int i) {
        for (int j = 0; j < field[i].length; j++) {
            if (field[i][j] == 0) return false;
        }
        return true;
    }

    public boolean isRowFullOfZeros(int i) {
        for (int j = 0; j < field[i].length; j++) {
            if (field[i][j] != 0) return false;
        }
        return true;
    }

    public void levelUp() {
        this.level = Math.max(this.level - 100, 100);
        notifyListeners();
    }

    public void levelDown() {
        this.level = Math.min(this.level + 100, maxLevel);
        notifyListeners();
    }

    public void reset() {
        field = new int[height][width];
        score = 0;
        finished = false;
        level = 1000;
        initFigure();
        notifyListeners();
    }

    public void pause() {
        this.paused = !this.paused;
    }
}
