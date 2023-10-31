import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int ROW_COUNT =3;
    private static String CELL_STATE_EMPTY = " ";
    private static String CELL_STATE_X = "X";
    private static String CELL_STATE_Y = "0";

    private static String GAME_STATE_Y_WON = "0 победили!";
    private static String GAME_STATE_DRAW = "ничья!";
    private static String GAME_STATE_X_WON = "X победили!";
    private static String GAME_STATE_NOT_FINISHED = "Игра не закончена";
    private static  Scanner scanner = new Scanner((System.in));

    private static Random random = new Random();

    public static void main(String[] args) {
        startGameRound();
    }
    public static void StartGameLoop(String[][] board){
       do {
            makePlayerTurn(board);
            printBoard(board);

            System.out.println();

            makeBotTurn(board);
            printBoard(board);
            String gameStgate = checkGameState(board);
            if (!Objects.equals(gameStgate, GAME_STATE_NOT_FINISHED)){
                System.out.println(gameStgate);
                return;
            }
        } while (true);
    }

    public static void startGameRound(){
        String[][] board = createBoard();
        StartGameLoop(board);
    }
    public static String[][] createBoard(){
        String[][] board = new String[ROW_COUNT][ROW_COUNT];
        for(int row=0; row<ROW_COUNT; row++){
            for(int col=0; col<ROW_COUNT; col++){
                board[row][col] = CELL_STATE_EMPTY;
            }
        }

        return board;
    }

    public static void makeBotTurn(String[][] board){
        System.out.println("Ход бота");
        int[] coordinates = getRandoEmptyCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_Y;
    }
    public static int[] getRandoEmptyCellCoordinates(String[][] board){
        do {
            int row = random.nextInt(ROW_COUNT);
            int col = random.nextInt(ROW_COUNT);

            if (Objects.equals(board[row][col], CELL_STATE_EMPTY)) {
                return new int[]{row, col};
            }
        } while (true);

    }

    public static void makePlayerTurn(String[][] board){
        int[] coordinates = inputCellCoordinates(board);
        board[coordinates[0]][coordinates[1]] = CELL_STATE_X;

    }

    public static int[] inputCellCoordinates(String[][] board){
        System.out.println("Введите координаты через пробел от 0 до 2: ");
        do{
            String[] input = scanner.nextLine().split(" ");

            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            if((row < 0) || (row>= ROW_COUNT) || (col<0) || (col>=ROW_COUNT)){
                System.out.println("Введите координаты через пробел от 0 до 2: ");
            } else if(!Objects.equals(board[row][col], CELL_STATE_EMPTY)){
                System.out.println("Ячейка уже занята");
            } else{
                return new int[]{row, col};
            }
        }
        while (true);
    }

    public static void printBoard(String[][] board){
        System.out.println("---------");
        for(int row = 0; row < ROW_COUNT; row++){
            String line = "| ";
            for (int col = 0; col < ROW_COUNT; col++){
                line += board[row][col] + " ";
            }

            line += "|";

            System.out.println(line);
        }
        System.out.println("---------");
    }

    private static int calucateNewValue(String cellState){
        if (Objects.equals(cellState, CELL_STATE_X)){
            return 1;
        }
        else if (Objects.equals(cellState, CELL_STATE_Y)){
            return -1;
        }
        else {
            return 0;
        }
    }
    public static String checkGameState(String[][] board){
        ArrayList<Integer> sums = new ArrayList<>();

        //iterate rows
        for (int row = 0; row < ROW_COUNT; row++) {
            int rowSum = 0;
            for (int col=0; col < ROW_COUNT; col++ ){
                rowSum += calucateNewValue(board[row][col]);
            }
            sums.add(rowSum);
        }

        for (int col = 0; col < ROW_COUNT; col++) {
            int colSum = 0;
            for (int row=0; row < ROW_COUNT; row++ ){
                colSum += calucateNewValue(board[row][col]);
            }
            sums.add(colSum);
        }

        int leftDiagonalSum = 0;
        for (int i = 0; i<ROW_COUNT; i++){
            leftDiagonalSum += calucateNewValue(board[i][i]);
        }

        int rightDiagonalSum = 0;
        for (int i = 0; i<ROW_COUNT; i++){
            rightDiagonalSum += calucateNewValue(board[i][ROW_COUNT-1-i]);
        }
        sums.add(leftDiagonalSum);


        if (sums.contains((3))){
            return GAME_STATE_X_WON;
        } else if (sums.contains(-3)){
            return GAME_STATE_Y_WON;
        } else if (areALLCellsTaken(board)){
            return GAME_STATE_DRAW;
        }
        else{
            return GAME_STATE_NOT_FINISHED;
        }
    }

    public static boolean areALLCellsTaken(String[][] board){
        for (int row = 0; row < ROW_COUNT; row++) {
            for (int col = 0; col < ROW_COUNT; col++) {
                if (Objects.equals(board[row][col], CELL_STATE_EMPTY)){
                    return false;
                }
            }
        }
        return true;
    }
}