import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FigureFactoryTest {
    private int[][] shape;
    FigureFactory figureFactory;

    @BeforeEach
    public void setUp(){
        figureFactory = new FigureFactory();
    }

    @Test
    public void OShapeTest(){
        int[][] newFigure = figureFactory.OShape();
        shape = new int[][] {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void LShapeTest(){
        int[][] newFigure = figureFactory.LShape();
        shape = new int[][] {
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void JShapeTest(){
        int[][] newFigure = figureFactory.JShape();
        shape = new int[][] {
                {0, 0, 3, 0},
                {0, 0, 3, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void IShapeTest(){
        int[][] newFigure = figureFactory.IShape();
        shape = new int[][] {
                {0, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 4, 0, 0}
        };

        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void SShapeTest(){
        int[][] newFigure = figureFactory.SShape();
        shape = new int[][] {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void ZShapeTest(){
        int[][] newFigure = figureFactory.ZShape();
        shape = new int[][] {
                {0, 0, 0, 0},
                {6, 6, 0, 0},
                {0, 6, 6, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void TShapeTest(){
        int[][] newFigure = figureFactory.TShape();
        shape = new int[][] {
                {0, 0, 0, 0},
                {0, 7, 0, 0},
                {7, 7, 7, 0},
                {0, 0, 0, 0}
        };
        boolean b = Arrays.deepEquals(newFigure, shape);
        assertTrue(b);
    }

    @Test
    public void randomFigureTest() {
        HashSet<String> figureList = new HashSet<>();
        figureList.add(Arrays.deepToString(figureFactory.OShape()));
        figureList.add(Arrays.deepToString(figureFactory.LShape()));
        figureList.add(Arrays.deepToString(figureFactory.JShape()));
        figureList.add(Arrays.deepToString(figureFactory.IShape()));
        figureList.add(Arrays.deepToString(figureFactory.SShape()));
        figureList.add(Arrays.deepToString(figureFactory.ZShape()));
        figureList.add(Arrays.deepToString(figureFactory.TShape()));

        int[][] randomFigure = figureFactory.createNextFigure();

        assertTrue(figureList.contains(Arrays.deepToString(randomFigure)));
    }
}
