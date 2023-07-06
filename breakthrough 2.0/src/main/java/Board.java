import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int EMPTY = 0;
    private static final int BOARDSIZE = 8;
    private static final int BOARD_BORDER = 7;
    private final int[][] weights = {
            {1, 1, 2, 2, 2, 2, 1, 1},
            {1, 2, 3, 3, 3, 3, 2, 1},
            {2, 3, 4, 4, 4, 4, 3, 2},
            {2, 3, 4, 5, 5, 4, 3, 2},
            {2, 3, 4, 5, 5, 4, 3, 2},
            {2, 3, 4, 4, 4, 4, 3, 2},
            {1, 2, 3, 3, 3, 3, 2, 1},
            {1, 1, 2, 2, 2, 2, 1, 1}
    };

    int[][] board;
    ArrayList<String[]> captures;
    ArrayList<Move> moveHistory;

    public Board(String s){
        board = new int[8][8];
        captures = new ArrayList<>();
        moveHistory = new ArrayList<>();
        String[] boardValues;
        boardValues = s.split(" ");

        int x = 0, y = 0;
        for(int i = 0; i < boardValues.length; i++){
            board[x][y] = Integer.parseInt(boardValues[i]);
            y++;
            if(y == 8){
                y = 0;
                x++;
            }
        }
    }

    public ArrayList<Move> getPossibleMoves(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        if (player == 4){
            for (int i = BOARDSIZE-1; i > 0; i--) {
                for (int j = 0; j < BOARDSIZE-1; j++) {
                    if (board[i][j] == player){

                        if (j!= 0 && board[i-1][j-1] != player){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i ) + 1, (j - 1));
                            possibleMoves.add(move);
                        }

                        if (board[i-1][j] == EMPTY){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i ) + 1, j);
                            possibleMoves.add(move);
                        }

                        if (board[i-1][j+1] != player){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i ) + 1, (j + 1));
                            possibleMoves.add(move);
                        }
                    }

                }
            }
        }else{
            for (int i = 0; i < BOARDSIZE-1; i++) {
                for (int j = 0; j < BOARDSIZE-1; j++) {
                    if (board[i][j] == player){
                        if (board[i+1][j] == EMPTY){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i) - 1, j);
                            possibleMoves.add(move);
                        }
                    }

                    if (board[i][j] == player){
                        if (board[i+1][j+1] != player){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i) - 1, (j + 1));
                            possibleMoves.add(move);
                        }
                    }

                    if (board[i][j] == player){
                        if (j!= 0 && board[i+1][j-1] != player){
                            Move move = new Move((BOARDSIZE - i),j,(BOARDSIZE - i) - 1, (j - 1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    public void play(Move move, int player){
        // Save the piece that was at the destination position, if any
        int capturedPiece = board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()];

        // Update the board to reflect the move
        board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()] = player;
        board[BOARDSIZE - move.getVerticalStartingPosition()][move.getHorizontalStartingPosition()] = EMPTY;

        // If a piece was captured, save it in the move
        if (capturedPiece != EMPTY) {
            move.setCapturedColor(capturedPiece);
        }


        moveHistory.add(move);
    }

    public void undo(){
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.remove(moveHistory.size()-1);

            // Revert the board to its state before the move
            board[BOARDSIZE - lastMove.getVerticalStartingPosition()][lastMove.getHorizontalStartingPosition()] =
                    board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()];

            if (lastMove.getCapturedColor() != null) {
                // If a piece was captured, put it back at the destination position
                board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()] = lastMove.getCapturedColor();
            } else {
                // If no piece was captured, the destination position should be empty
                board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()] = EMPTY;
            }
        }
    }



    public void printBoard() {
        System.out.println("Current Board:");

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Captures:");
        for (String[] capture : captures) {
            System.out.println(Arrays.toString(capture));
        }
    }

    public int evaluate2(int player, int depth) {
        int opponent = (player == 2) ? 4 : 2;
        int score = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            if (board[0][i] == 4 && player == 4) {
                return 1000;
            } else if (board[0][i] == 4 && player == 2) {
                return -1000;
            } else if (board[BOARD_BORDER][i] == 2 && player == 2) {
                return 1000;
            } else if (board[BOARD_BORDER][i] == 2 && player == 4) {
                return -1000;
            }
        }


        return score;
    }

    public int getPositionCoefficicent(int i, int j){
        return weights[i][j];
    }

    public int evaluate(int player, int depth) {

        int score = 0;

        for (int i = 0; i < BOARDSIZE; i++) {
            if (board[0][i] == 4 && player == 4) {
                return depth == 0 ? 10000 : 10000 / depth + 1;
            } else if (board[0][i] == 4 && player == 2) {
                return depth == 0 ? -10000 : -10000 / depth + 1;
            } else if (board[BOARD_BORDER][i] == 2 && player == 2) {
                return depth == 0 ? 10000 : 10000 / depth + 1;
            } else if (board[BOARD_BORDER][i] == 2 && player == 4) {
                return depth == 0 ? -10000 : -10000 / depth + 1;
            }
        }

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j] == player) {
                    if (player == 4) {
                        score -= numberOfSurroundingOppPlayers(4,i,j);
                        score += getPositionCoefficicent(i,j);
                    }
                }

                if ((j!= 7) && board[i][j] == player && (board[i][j+1]!=EMPTY || board[i][j+1]!=player)) {
                    score++;
                }

            }
        }

        int opponentTokens = getNumberOfOpponentTokens(player);
        int playerTokens = getNumberOfPlayerTokens(player);

        if (opponentTokens > playerTokens){
            score -= opponentTokens - playerTokens;
        }

        if (playerTokens < opponentTokens){
            score += playerTokens - opponentTokens;
        }

        for (int i = 0; i < BOARD_BORDER; i++) {
            if (board[0][i] == EMPTY){
                score +=2;
            }

            if (board[1][i] == EMPTY){
                score +=1;
            }

            if (board[i][0] == player){
                score +=1;
            }
        }

        return score;
    }

    public int getNumberOfPlayerTokens(int player){
        int numOfTokens = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j]==player){
                    numOfTokens++;
                }
            }
        }
        return numOfTokens;
    }

    public int getNumberOfOpponentTokens(int player){
        int opponent = (player == 2) ? 4 : 2;
        int numOfTokens = 0;

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j]==opponent){
                    numOfTokens++;
                }
            }
        }
        return numOfTokens;
    }

    public int numberOfSurroundingOppPlayers(int player, int x, int y){
        int opponent = (player == 2) ? 4 : 2;
        int counter = 0;

        // Check the four diagonals
        if (x > 0 && y > 0 && board[x-1][y-1] == opponent) {
            counter++;
        }
        if (x > 0 && y < BOARD_BORDER && board[x-1][y+1] == opponent) {
            counter++;
        }
        if (x < BOARD_BORDER && y > 0 && board[x+1][y-1] == opponent) {
            counter++;
        }
        if (x < BOARD_BORDER && y < BOARD_BORDER && board[x+1][y+1] == opponent) {
            counter++;
        }

        return counter;
    }
}
