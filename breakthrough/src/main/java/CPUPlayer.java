import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CPUPlayer {
    private final int opponentNumber;
    private final int playerNumber;
    private int numExploredNodes;

    public CPUPlayer(int player){
        this.playerNumber = player;
        if (player==2){
            this.opponentNumber = 4;
        }else {
            this.opponentNumber = 2;
        }
    }

    public void addNumOfExploredNodes(){
        this.numExploredNodes++;
    }

    public int getPlayerNumber(){
        return this.playerNumber;
    }
    public int getOpponentNumber(){
        return this.opponentNumber;
    }

    public Map<Move, Integer> getNextMoveAB(Board board){
        int alpha = (int) Double.NEGATIVE_INFINITY;
        int beta = (int) Double.POSITIVE_INFINITY;
        double highestPossibleScore = Double.NEGATIVE_INFINITY;
        Map<Move, Integer> bestMoves = new HashMap<>();
        ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move m = possibleMoves.get(i);
            board.play(m, this.getPlayerNumber());
            int score = minimaxAB(board,false,alpha,beta,4);
            if (score>highestPossibleScore){
                highestPossibleScore = score;
                bestMoves = new HashMap<>();
                bestMoves.put(m, score);
            } else if (score==highestPossibleScore) {
                bestMoves.put(m, score);
            }
            board.undo();
        }
        return bestMoves;
    }

    public int minimaxAB(Board board, boolean isMaximizing, int alpha, int beta, int depth){
        addNumOfExploredNodes();
        if (depth == 0){
            return board.evaluate(this.getPlayerNumber(), depth);
        }

        ArrayList<Move> possibleMoves;
        if (isMaximizing){
            possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
            for (Move m : possibleMoves) {
                board.play(m, this.getPlayerNumber());
                System.out.println("move played " + m.getVerticalStartingPosition() + "-" + m.getHorizontalStartingPosition() + ";" + m.getVerticalDestinationPosition() + "-" + m.getHorizontalStartingPosition());
                board.printBoard();
                int score = minimaxAB(board, false, alpha, beta, depth-1);
                board.undo();
                alpha = Math.max(alpha, score);
                if (beta <= alpha){
                    break;
                }
            }
            return alpha;
        } else {
            possibleMoves = board.getPossibleMoves(this.getOpponentNumber());
            for (Move m : possibleMoves) {
                board.play(m, this.getOpponentNumber());
                int score = minimaxAB(board, true, alpha, beta, depth-1);
                board.undo();
                beta = Math.min(beta, score);
                if (beta <= alpha){
                    break;
                }
            }
            return beta;
        }
    }

    public Move getNextMove(Board board) {
        Map.Entry<Move, Integer> bestEntry = null;
        Map<Move, Integer> bestMoves = this.getNextMoveAB(board);

        for (Map.Entry<Move, Integer> entry : bestMoves.entrySet()) {
            if (bestEntry == null || entry.getValue().compareTo(bestEntry.getValue()) > 0) {
                bestEntry = entry;
            }
        }
        return bestEntry.getKey();
    }
}
