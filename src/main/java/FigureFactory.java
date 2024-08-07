import java.util.Random;

public class FigureFactory {

    public int[][] createNextFigure(){
        Random random = new Random();
        int nextFigure = random.nextInt(7) + 1;

        return switch (nextFigure) {
            case 1 -> OShape();
            case 2 -> LShape();
            case 3 -> JShape();
            case 4 -> IShape();
            case 5 -> SShape();
            case 6 -> ZShape();
            case 7 -> TShape();
            default -> TShape();
        };

    }

    public int[][] OShape(){
        return new int[][] {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
    }

    public int[][] LShape(){
        return new int[][] {
                {0, 2, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        };
    }

    public int[][] JShape(){
        return new int[][] {
                {0, 0, 3, 0},
                {0, 0, 3, 0},
                {0, 3, 3, 0},
                {0, 0, 0, 0}
        };
    }

    public int[][] IShape(){
        return new int[][] {
                {0, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 4, 0, 0},
                {0, 4, 0, 0}
        };
    }

    public int[][] SShape(){
        return new int[][] {
                {0, 0, 0, 0},
                {0, 5, 5, 0},
                {5, 5, 0, 0},
                {0, 0, 0, 0}
        };
    }

    public int[][] ZShape(){
        return new int[][] {
                {0, 0, 0, 0},
                {6, 6, 0, 0},
                {0, 6, 6, 0},
                {0, 0, 0, 0}
        };
    }

    public int[][] TShape(){
        return new int[][] {
                {0, 0, 0, 0},
                {0, 7, 0, 0},
                {7, 7, 7, 0},
                {0, 0, 0, 0}
        };
    }





//  for testers...

    static int[][] J(){
        return new int[][] {
                {0, 0, 2, 0},
                {0, 0, 2, 0},
                {0, 2, 2, 0},
                {0, 0, 0, 0},
        };
    }

    static int[][] rotatedJ(){
        return new int[][] {
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 2, 2, 2},
                {0, 0, 0, 0},
        };
    }
}
