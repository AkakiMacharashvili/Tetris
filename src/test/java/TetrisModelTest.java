import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisModelTest {
    private TetrisModel model;


    @BeforeEach
    public void setUp(){
        model = new TetrisModel(TetrisModel.DEFAULT_WIDTH, TetrisModel.DEFAULT_HEIGHT, TetrisModel.DEFAULT_COLOR_NUMBER);
    }

    @Test
    public void TestModel(){
        Pair pair = model.size();
        assertEquals(pair.x(), 10);
        assertEquals(pair.y(), 20);
        assertNotNull(model.field);
        assertEquals(model.field.length, pair.y());
    }

    @Test
    public void TestColors(){
        assertEquals(model.maxColors, TetrisModel.DEFAULT_COLOR_NUMBER);
    }

    @Test
    public void TestFigure(){
        int[][] figure = model.figure;
        assertNotNull(figure);
    }

    @Test
    public void positionExists(){
        Pair position = model.position;
        assertNotNull(position);
        assertEquals(position.x(), model.size().x() / 2 - 2);
        assertEquals(position.y(), 0);
    }

    @Test
    public void testName(){
        GameEventListener listener = model;
    }

    @Test
    public void testSlideDown(){
        Pair old = model.position;
        model.slideDown();
        assertEquals(model.position, new Pair(old.x(), old.y() + 1));
    }

    @Test
    public void testFigureNotOverlapFieldCellsAfterSlideDown(){
        model.field[2][model.size().x() / 2] = 1;
        model.slideDown();
        assertTrue(model.isNewFigurePositionValid(model.position));
    }

//    @Test
//    public void testPasteFigure() throws Exception {
//        model.pasteFigure();
//        assertEquals(1, model.field[0][model.size().x()/2-1]);
//        assertEquals(1, model.field[0][model.size().x()/2]);
//        assertEquals(1, model.field[1][model.size().x()/2-1]);
//        assertEquals(1, model.field[1][model.size().x()/2]);
//    }

    @Test
    public void rotateTest(){
        model.figure = FigureFactory.J();
        model.rotate();
        int[][] rotated = FigureFactory.rotatedJ();
        Arrays.equals(model.figure, rotated);
    }

    @Test
    public void testMoveLeft() throws Exception {
        var oldPos = model.position;
        model.moveLeft();
        assertEquals(oldPos.x() - 1, model.position.x());
    }

    @Test
    public void testMoveRight() throws Exception {
        var oldPos = model.position;
        model.moveRight();
        assertEquals(oldPos.x() + 1, model.position.x());
    }

    @Test
    public void fullRowOnesTest(){
        assertFalse(model.isRowFullOfOnes(1));
        assertTrue(model.isRowFullOfZeros(1));
    }

    @Test
    public void ShiftTest(){
        int[][] field = {
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };

        field = model.shiftRowsDown(field);

        int[][] answer = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 1, 0}
        };
        Arrays.deepEquals(answer, field);
    }

    @Test
    public void addListenerTest(){
        ModelListener modelListener = new ModelListener() {
            @Override
            public void onChange(TetrisModel tetrisModel) {

            }

            @Override
            public void over(TetrisModel tetrisModel) {

            }
        };
        this.model.addListener(modelListener);
        assertEquals(model.listeners.size(), 1);
    }

    @Test
    public void removeListenerTest(){
        ModelListener modelListener = new ModelListener() {
            @Override
            public void onChange(TetrisModel tetrisModel) {

            }

            @Override
            public void over(TetrisModel tetrisModel) {

            }
        };
        this.model.addListener(modelListener);
        assertEquals(model.listeners.size(), 1);
        this.model.removeListener(modelListener);
        assertEquals(model.listeners.size(), 0);
    }

    @Test
    public void updateScoreTest(){
        int current = model.score;
        model.updateScore(100);
        assertEquals(current + 100, model.score);
    }

    @Test
    public void infiniteTest(){
        model.infinite();
        assertFalse(model.FirstTry);
    }

    @Test
    public void levelUpTest(){
        model.levelUp();
        assertEquals(TetrisModel.DEFAULT_MAX_LEVEL - 100, model.level);
        for(int i = 0; i < 10; i++) model.levelUp();
        assertEquals(100, model.level);
    }

    @Test
    public void levelDownTest(){
        model.levelDown();
        assertEquals(TetrisModel.DEFAULT_MAX_LEVEL, model.level);
        model.levelUp();
        assertEquals(TetrisModel.DEFAULT_MAX_LEVEL - 100, model.level);
        for(int i = 0; i < 10; i++) model.levelUp();
        assertEquals(100, model.level);
        model.levelDown();
        assertEquals(200, model.level);
    }

    @Test
    public void resetTest(){
        model.finished = true;
        model.levelUp();
        model.levelUp();
        model.levelDown();
        model.score += 100;
        model.reset();
        assertEquals(model.field.length, TetrisModel.DEFAULT_HEIGHT);
        assertEquals(model.field[0].length, TetrisModel.DEFAULT_WIDTH);
        assertFalse(model.finished);
        assertEquals(0, model.score);
    }

    @Test
    public void pauseTest(){
        assertFalse(model.paused);
        model.pause();
        assertTrue(model.paused);
    }

    @Test
    public void nextLevelTest(){
        model.score = 300;
        model.nextLevel();
        assertEquals(700, model.level);
    }

    @Test
    public void initFigureTest(){
        model.initFigure();
        assertEquals(model.position, new Pair(TetrisModel.DEFAULT_WIDTH / 2 - 2, 0));
    }

    @Test
    public void gameOverTest(){
        assertFalse(model.finished);
        model.gameOver();
        assertTrue(model.finished);
    }

    @Test
    public void shiftRowsDownTest(){
        int[][] field =new int[][] {
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        };
        field = model.shiftRowsDown(field);

        int[][] newField =new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 1, 1, 1}
        };
        Arrays.equals(field, newField);
    }

    @Test
    public void deleteFullRowsTest(){
        for(int i = 0; i < model.field[0].length; i++) model.field[0][i] = 1;
        int[][] result = new int[20][10];
        model.deleteFullRows();
        Arrays.equals(result, model.field);
    }

    @Test
    public void checkAbsPositionTest(){
        model.field[10][9] = 1;
        assertFalse(model.checkAbsPos(new Pair(10, 0)));
        assertFalse(model.checkAbsPos(new Pair(0, 20)));
        assertFalse(model.checkAbsPos(new Pair(-1, 0)));
        assertFalse(model.checkAbsPos(new Pair(0, -1)));
        assertTrue(model.checkAbsPos(new Pair(3, 3)));
        assertFalse(model.checkAbsPos(new Pair(10, 9)));
    }

   @Test
   public void isRowFullOffOnesTest(){
       Arrays.fill(model.field[0], 1);
       assertTrue(model.isRowFullOfOnes(0));
       assertFalse(model.isRowFullOfOnes(10));
   }

   @Test
    public void isRowFullOfZerosTest(){
        assertTrue(model.isRowFullOfZeros(0));
        model.field[0][0] = 1;
        assertFalse(model.isRowFullOfZeros(0));
   }

}
