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
        captures = new ArrayList<>();
        moveHistory = new ArrayList<>();
        s = s.replace(" ", "");
        board = new int[BOARDSIZE][BOARDSIZE];
        int index = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                board[i][j] = Character.getNumericValue(s.charAt(index));
                index++;
            }
        }

        //board = transposeAndMirror(board);
    }

    public ArrayList<Move> getPossibleMoves(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (player == 4){
            for (int i = BOARD_BORDER; i > 0; i--) {
                for (int j = 0; j <= BOARD_BORDER; j++) {
                    if (board[i][j] == player){
                        if ((i != 0) && (this.board[i - 1][j] == EMPTY)) {
                            Move move = new Move((BOARDSIZE - i), j, BOARDSIZE - (i - 1), j);
                            possibleMoves.add(move);
                        }

                        if ((i != 0 && j != 0) && !(this.board[i - 1][j - 1]==player)) {
                            Move move = new Move((BOARDSIZE - i), j, BOARDSIZE - (i - 1), (j - 1));
                            System.out.println(move);
                            possibleMoves.add(move);
                        }

                        if ((i != 0 && j != BOARD_BORDER) && !(this.board[i - 1][j + 1]==player)) {
                            Move move = new Move((BOARDSIZE - i), j, BOARDSIZE - (i - 1), (j + 1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }else {
            for (int i = 0; i < BOARDSIZE; i++) {
                for (int j = 0; j <= BOARD_BORDER; j++) {
                    if (board[i][j] == player) {
                        if ((i + 1 < BOARDSIZE) && (this.board[i + 1][j] == EMPTY)) {
                            Move move = new Move(i, j, (i + 1), j);
                            possibleMoves.add(move);
                        }

                        if ((i + 1 < BOARDSIZE && j != 0) && !(this.board[i + 1][j - 1] == player)) {
                            Move move = new Move(i, j, (i + 1), (j - 1));
                            possibleMoves.add(move);
                        }

                        if ((i + 1 < BOARDSIZE && j != BOARD_BORDER) && !(this.board[i + 1][j + 1] == player)) {
                            Move move = new Move(i, j, (i + 1), (j + 1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    /*public ArrayList<Move> getPossibleMovesOld(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (player == 4)
            for (int i = BOARD_BORDER; i >= 0; i--) {
                for (int j = BOARD_BORDER; j >= 0; j--) {
                    if (this.board[i][j] == player) {
                        if ((i != 0) && (this.board[i + 1][j] == EMPTY)) {
                            Move move = new Move(i,j,(i + 1),(j));
                            possibleMoves.add(move);
                        }

                        if ((i != 0 & j != 0) && !(this.board[i - 1][j - 1]==player)) {
                            Move move = new Move(i,j,(i - 1),(j - 1));
                            possibleMoves.add(move);
                        }

                        if ((i != 0 & j != BOARD_BORDER) && !(this.board[i - 1][j + 1]==player)) {
                            Move move = new Move(i,j,(i - 1),(j + 1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        else {
            for (int i = 0; i < BOARD_BORDER; i++) {
                for (int j = 0; j < BOARD_BORDER; j++) {
                    if (this.board[i][j]==player) {
                        if ((i!=(BOARD_BORDER-1)) && (this.board[i + 1][j]==EMPTY)) {
                            Move move = new Move(i,j,(i + 1),(j));
                            possibleMoves.add(move);
                        }

                        if ((i != (BOARD_BORDER-1) & j != 0) && !(this.board[i + 1][j - 1]==player)) {
                            Move move = new Move(i,j,(i + 1),(j - 1));
                            possibleMoves.add(move);
                        }

                        if ((i != 0 & j != (BOARD_BORDER-1) && !(this.board[i + 1][j + 1]==player))) {
                            Move move = new Move(i,j,(i + 1),(j + 1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }*/

    public void play(Move move, int player){
        if (board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()] == EMPTY){
            board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()] = board[BOARDSIZE - move.getVerticalStartingPosition()][move.getHorizontalStartingPosition()];
            board[BOARDSIZE - move.getVerticalStartingPosition()] [move.getHorizontalStartingPosition()]= 0;
            moveHistory.add(move);
        } else if (board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()] != player){
            if (player == 2){
                move.setCapturedColor(4);
                moveHistory.add(move);
            }else {
                move.setCapturedColor(2);
                moveHistory.add(move);
            }
            board[BOARDSIZE - move.getVerticalDestinationPosition()][move.getHorizontalDestinationPosition()] = player;
            board[BOARDSIZE - move.getVerticalStartingPosition()][move.getHorizontalStartingPosition()] = 0;
        }
    }

    public void undo(){
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.remove(moveHistory.size()-1);

            if (lastMove.getCapturedColor() != null) {
                this.board[BOARDSIZE - lastMove.getVerticalStartingPosition()][lastMove.getHorizontalStartingPosition()] =
                        this.board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()];
                this.board[BOARDSIZE - lastMove.getVerticalStartingPosition()][lastMove.getHorizontalStartingPosition()] = lastMove.getCapturedColor();

            } else {
                this.board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()] =
                        this.board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()];
                this.board[BOARDSIZE - lastMove.getVerticalDestinationPosition()][lastMove.getHorizontalDestinationPosition()] = 0;
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

    public int evaluate(int player, int depth) {
        int opponent = (player == 2) ? 4 : 2;
        int score = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            int scoreToReturn = depth == 0 ? 1000 : 1000 / depth;
            if (board[i][BOARD_BORDER] == player && player == 2) {
                return scoreToReturn;
            } else if (board[i][BOARD_BORDER] == player && player == 4) {
                return -scoreToReturn;
            } else if (board[i][0] == 4 && player == 4) {
                return scoreToReturn;
            } else if (board[i][0] == 4 && player == 2) {
                return -scoreToReturn;
            }
        }

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j] == player){
                    score += getPositionCoefficicent(i, j);
                }else if (board[i][j] == opponent){
                    score -= getPositionCoefficicent(i, j);
                }

                score += BOARD_BORDER - j;
            }
        }

        return score;
    }

    public int getPositionCoefficicent(int i, int j){
        return weights[i][j];
    }

    public int evaluate2(int player, int depth) {
        int score = 0;
        for (int i = 0; i < BOARDSIZE; i++) {
            int scoreToReturn = depth == 0 ? Integer.MAX_VALUE : Integer.MAX_VALUE - depth;
            if (board[i][BOARD_BORDER] == 2 && player == 2) {
                return scoreToReturn;
            } else if (board[i][BOARD_BORDER] == 2 && player == 4) {
                return -scoreToReturn;
            } else if (board[i][0] == 4 && player == 4) {
                return scoreToReturn;
            } else if (board[i][0] == 4 && player == 2) {
                return -scoreToReturn;
            }
        }

        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (board[i][j] == player) {
                    if (player == 2) {
                        score += numberOfSurroundingOppPlayers(2,i,j);
                        score += j;
                    } else {
                        score -= numberOfSurroundingOppPlayers(4,i,j);
                        score += 7 - j;
                    }
                }

                if (board[i][j] == player && (board[i][j+1]!=EMPTY || board[i][j+1]!=player)) {
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
                score +=2;
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
