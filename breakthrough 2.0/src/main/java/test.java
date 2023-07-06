import java.util.ArrayList;

public class test {

    public static void main(String[] args) {
        String s = "0 0 0 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0 4 0 0 0 0 0 0 2 0 4 0 2 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 4 0 0 0 0 0 0 0 0 0 0";

        Board dummyBoard = new Board(s);
        ArrayList<Move> possibleMoves = dummyBoard.getPossibleMoves(2);
        int i = 3;

        dummyBoard.printBoard();
        Move m = possibleMoves.get(3);

        dummyBoard.play(m,4);

        dummyBoard.printBoard();

        dummyBoard.undo();
        dummyBoard.printBoard();

    }
}
