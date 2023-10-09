package gamewindow;

import java.util.Random;

public class GameLogic {
    private static final Random RANDOM = new Random();
    private static int cellCountX, cellCountY, winNumber;
    private static FieldState[][] field;
    private static GameState state;
    static {
        cellCountX = 3;
        cellCountY = 3;
        winNumber = 3;
    }
    public static void initField(){
        field = new FieldState[cellCountX][cellCountY];
        for(int x=0; x<cellCountX;x++){
            for(int y=0; y<cellCountY; y++){
                field[x][y] = FieldState.EMPTY;
            }
        }
        state = GameState.GAME;
    }
    public static boolean humanTurn(int posX, int posY) {
        if(field[posX][posY]==FieldState.EMPTY) {
            field[posX][posY] = FieldState.HUMAN_DOT;
            return true;
        }
        return false;
    }

    public static void aiTurn() {
        for (int x = 0; x < cellCountX; x++) {
            for (int y = 0; y < cellCountY; y++) {
                if (field[x][y] == FieldState.EMPTY){
                    field[x][y] = FieldState.AI_DOT;
                    if (checkWin(FieldState.AI_DOT, winNumber))
                        return;
                    else
                        field[x][y] = FieldState.EMPTY;
                }
            }
        }
        boolean f = checkWin(FieldState.HUMAN_DOT, winNumber - 1);
        for (int x = 0; x < cellCountX; x++) {
            for (int y = 0; y < cellCountY; y++) {
                if (field[x][y] == FieldState.EMPTY){
                    field[x][y] = FieldState.HUMAN_DOT;
                    if (checkWin(FieldState.HUMAN_DOT, winNumber - (f ? 0 : 1))) {
                        field[x][y] = FieldState.AI_DOT;
                        return;
                    }
                    else
                        field[x][y] = FieldState.EMPTY;
                }
            }
        }
        int x, y;
        do {
            x = RANDOM.nextInt(cellCountX);
            y = RANDOM.nextInt(cellCountY);
        } while (field[x][y]!=FieldState.EMPTY);
        field[x][y] = FieldState.AI_DOT;
    }


    private static boolean checkWin(FieldState dot, int winCount) {
        for (int x = 0; x < cellCountX; x++) {
            for (int y = 0; y < cellCountY; y++) {
                if (field[x][y] == dot)
                    if (checkXY(x, y, 1, winCount) ||
                            checkXY(x, y, -1, winCount) ||
                            checkDiagonal(x, y, -1, winCount) ||
                            checkDiagonal(x, y, 1, winCount))
                        return true;
            }
        }
        return false;
    }

    private static boolean checkXY(int x, int y, int dir, int winCount){
        FieldState dot = field[x][y];
        for(int i=1; i<winCount; i++) {
            if (dir > 0) {
                if (!isCellValid(x , y+i) || field[x][y+i] != dot) return false;
            }
            else if(dir<0) {
                if (!isCellValid(x+i, y) || field[x+i][y] != dot) return false;
            }
        }
        return true;
    }

    private static boolean checkDiagonal(int x, int y, int dir, int winCount){
        FieldState dot = field[x][y];
        for(int i=1; i<winCount; i++){
            if(!isCellValid(x+i, y+i*dir) || field[x+i][y+i*dir]!=dot) return false;
        }
        return true;
    }

    private static boolean checkDraw(){
        for (int x = 0; x < cellCountX; x++) {
            for (int y = 0; y < cellCountY; y++) {
                if (field[x][y] == FieldState.EMPTY)
                    return false;
            }
        }
        return true;
    }

    public static boolean gameChecks(FieldState dot) {
        if(checkWin(dot, winNumber)){
            if(dot==FieldState.HUMAN_DOT)
                state=GameState.HUMAN_WIN;
            else if(dot==FieldState.AI_DOT)
                state=GameState.AI_WIN;
            return true;
        }
        if(checkDraw()) {
            state = GameState.DRAW;
            return true;
        }
        return false;
    }

    private static boolean isCellValid(int x, int y){
        return (x<cellCountX)&&(x>=0)&&(y<cellCountY)&&(y>=0);
    }

    public static FieldState getFieldStateAt(int x, int y){
        return field[x][y];
    }

    public static int getCellCountX() {
        return cellCountX;
    }

    public static void setCellCountX(int cellCountX) {
        GameLogic.cellCountX = cellCountX;
    }

    public static int getCellCountY() {
        return cellCountY;
    }

    public static void setCellCountY(int cellCountY) {
        GameLogic.cellCountY = cellCountY;
    }

    public static int getWinNumber() {
        return winNumber;
    }

    public static void setWinNumber(int winNumber) {
        GameLogic.winNumber = winNumber;
    }

    public static GameState getState() {
        return state;
    }
}
