import java.util.ArrayList;

public class Board {
    int[][] board;
    ArrayList<String[]> captures;
    ArrayList<Move> movesCompleted;

    public Board(String s){
        captures = new ArrayList<>();
        movesCompleted = new ArrayList<Move>();
        s = s.replace(" ", "");
        board = new int[8][8];
        int index = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = Character.getNumericValue(s.charAt(index));
                index++;
            }
        }

        board = transpose(board);
    }

    public int[][] transpose(int[][] board) {
        int m = board.length;
        int n = board[0].length;

        int[][] transposedMatrix = new int[n][m];

        // Effectuer la transposée
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                transposedMatrix[j][i] = board[i][j];
            }
        }

        // Copy the last column of the original matrix to the last row of the transposed matrix
        for (int i = 0; i < n; i++) {
            transposedMatrix[i][m-1] = board[m-1][i];
        }

        return transposedMatrix;
    }

    public ArrayList<Move> getPossibleMoves(int player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        if (player == 4)
            for (int i = 7; i >= 0; i--) {
                for (int j = 7; j >= 0; j--) {
                    if (this.board[i][j] == player) {
                        if ((i != 0) && (this.board[i][j-1] == 0)) {
                            possibleMoves.add(new Move(i,j,i,j-1));
                        }

                        if ((i != 0 & j != 7) && !(this.board[i - 1][j - 1]==player)) {
                            possibleMoves.add(new Move(i,j,(i-1),(j-1)));
                        }

                        if ((i != 0 & j != 0) && !(this.board[i + 1][j - 1]==player)) {
                            possibleMoves.add(new Move(i,j,(i+1),(j-1)));
                        }
                    }
                }
            }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.board[i][j]==player) {
                        if ((j != 7) && (this.board[i][j+1]==0)) {
                            possibleMoves.add(new Move(i,j,(i),(j+1)));
                        }

                        if ((i != 7 & j != 7) && !(this.board[i + 1][j + 1]==player)) {
                            possibleMoves.add(new Move(i,j,(i+1),(j+1)));
                        }

                        if ((i != 0 & j != 7) && !(this.board[i - 1][j + 1]==player)) {
                            possibleMoves.add(new Move(i,j,(i-1),(j+1)));
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
            movesCompleted.add(move);
        } else if (board[move.getX_destinationPosition()][move.getY_destinationPosition()] != player){
            if (player == 2){
                move.setCapturedColor(4);
                movesCompleted.add(move);
            }else {
                move.setCapturedColor(2);
                movesCompleted.add(move);
            }
            board[move.getX_destinationPosition()][move.getY_destinationPosition()] = player;
            board[move.getX_startingPosition()][move.getY_startingPosition()] = 0;
        }
    }

    public void undo(){
        if (!movesCompleted.isEmpty()) {
            Move lastMove = movesCompleted.remove(movesCompleted.size()-1);

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



    public String translateToLetters(int i, int j){
        StringBuilder s = new StringBuilder();
        if (i == 0) {
            s.append("H");
        } else if (i == 1) {
            s.append("G");
        } else if (i == 2) {
            s.append("F");
        } else if (i == 3) {
            s.append("E");
        } else if (i == 4) {
            s.append("D");
        } else if (i == 5) {
            s.append("C");
        } else if (i == 6) {
            s.append("B");
        } else if (i == 7) {
            s.append("A");
        }

        return String.valueOf(s.append(j+1));
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

    public int evaluate(int player){
        int opponent;
        if (player==2){
            opponent = 4;
            for (int i = 0; i < 8; i++) {
                if (board[7][i] == player){
                    return 100;
                }

                if (board[0][i]==opponent){
                    return -100;
                }
            }
        }else {
            opponent = 2;
            for (int i = 0; i < 8; i++) {
                if (board[7][i] == opponent){
                    return -100;
                }

                if (board[0][i] == player){
                    return 100;
                }
            }
        }
        return 0;
    }
}
