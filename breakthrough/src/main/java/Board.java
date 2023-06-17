import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    int[][] board;
    ArrayList<String[]> captures;
    ArrayList<Move> moveHistory;

    public Board(String s){
        captures = new ArrayList<>();
        moveHistory = new ArrayList<Move>();
        s = s.replace(" ", "");
        board = new int[8][8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = Character.getNumericValue(s.charAt(index));
                index++;
            }
        }

        board = transposeAndMirror(board);
    }

    public int[][] transposeAndMirror(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        int[][] transposedMatrix = new int[n][m];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix[j][i] = board[i][j];
            }
        }

        int[][] mirroredMatrix = new int[n][m];
        for(int i = 0; i < n; i++) {
            mirroredMatrix[i] = Arrays.copyOf(transposedMatrix[n-i-1], m);
        }

        return mirroredMatrix;
    }


    public ArrayList<Move> getPossibleMoves(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (player == 4)
            for (int i = 7; i >= 0; i--) {
                for (int j = 7; j >= 0; j--) {
                    if (this.board[i][j] == player) {
                        if ((i != 0 & j!=0) && (this.board[i][j-1] == 0)) {
                            Move move = new Move(i,j,(i),(j-1));
                            possibleMoves.add(move);
                        }

                        if ((i != 0 & j != 0) && !(this.board[i - 1][j - 1]==player)) {
                            Move move = new Move(i,j,(i-1),(j-1));
                            possibleMoves.add(move);
                        }

                        if ((i != 7 & j != 0) && !(this.board[i + 1][j - 1]==player)) {
                            Move move = new Move(i,j,(i+1),(j-1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j]==player) {
                        if ((j!=7) && (this.board[i][j+1]==0)) {
                            Move move = new Move(i,j,(i),(j+1));
                            possibleMoves.add(move);
                        }

                        if ((i != 7 & j != 7) && !(this.board[i + 1][j + 1]==player)) {
                            Move move = new Move(i,j,(i+1),(j+1));
                            possibleMoves.add(move);
                        }

                        if ((i != 0 & j != 7) && !(this.board[i - 1][j + 1]==player)) {
                            Move move = new Move(i,j,(i-1),(j+1));
                            possibleMoves.add(move);
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }



    public void play(Move move, int player){
        if (board[move.getX_destinationPosition()][move.getY_destinationPosition()] == 0){
            board[move.getX_destinationPosition()][move.getY_destinationPosition()] = board[move.getX_startingPosition()][move.getY_startingPosition()];
            board[move.getX_startingPosition()][move.getY_startingPosition()] = 0;
            moveHistory.add(move);
        } else if (board[move.getX_destinationPosition()][move.getY_destinationPosition()] != player){
            if (player == 2){
                move.setCapturedColor(4);
                moveHistory.add(move);
            }else {
                move.setCapturedColor(2);
                moveHistory.add(move);
            }
            board[move.getX_destinationPosition()][move.getY_destinationPosition()] = player;
            board[move.getX_startingPosition()][move.getY_startingPosition()] = 0;
        }
    }

    public void undo(){
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.remove(moveHistory.size()-1);

            if (lastMove.getCapturedColor() != null) {
                this.board[lastMove.getX_startingPosition()][lastMove.getY_startingPosition()] =
                        this.board[lastMove.getX_destinationPosition()][lastMove.getY_destinationPosition()];
                this.board[lastMove.getX_destinationPosition()][lastMove.getY_destinationPosition()] = lastMove.getCapturedColor();

            } else {
                this.board[lastMove.getX_startingPosition()][lastMove.getY_startingPosition()] =
                        this.board[lastMove.getX_destinationPosition()][lastMove.getY_destinationPosition()];
                this.board[lastMove.getX_destinationPosition()][lastMove.getY_destinationPosition()] = 0;
            }
        }
    }

    public void printBoard() {
        System.out.println("Current Board:");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Captures:");
        for (String[] capture : captures) {
            System.out.println(capture);
        }
    }

    public int evaluate_old(int player){
        int opponent;
        if (player==2){
            opponent = 4;
            for (int i = 0; i < 8; i++) {
                if (board[i][7] == player){
                    return 100;
                }

                if (board[i][0]==opponent){
                    return -100;
                }
            }
        }else {
            opponent = 2;
            for (int i = 0; i < 8; i++) {
                if (board[i][0] == opponent){
                    return -100;
                }

                if (board[i][7] == player){
                    return 100;
                }
            }
        }
        return 0;
    }

    public int evaluate(int player) {
        for (int i = 0; i < 8; i++) {
            if (board[i][7] == 2 && player == 2) {
                return 100;
            } else if (board[i][7] == 2 && player == 4) {
                return -100;
            } else if (board[i][0] == 4 && player == 4) {
                return 100;
            } else if (board[i][0] == 4 && player == 2) {
                return -100;
            }
        }
        return 0;
    }
}
