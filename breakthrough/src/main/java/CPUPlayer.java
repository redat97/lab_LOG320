import java.util.ArrayList;
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

    public ArrayList<Move> getNextMoveMinMax(Board board)
    {
        ArrayList<Move> bestMove = new ArrayList<>();
        double highestPossibleScore = Double.NEGATIVE_INFINITY;
        ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move move = possibleMoves.get(i);
            board.play(move, this.getPlayerNumber());
            int score = minimax(board,false, 4);
            if (score>highestPossibleScore){
                highestPossibleScore = score;
                bestMove = new ArrayList<>();
                bestMove.add(move);
            } else if (score==highestPossibleScore) {
                bestMove.add(move);
            }
            board.undo();
        }
        return bestMove;
    }

    public ArrayList<Move> getNextMoveAB(Board board){
        int alpha = (int) Double.NEGATIVE_INFINITY;
        int beta = (int) Double.POSITIVE_INFINITY;
        double highestPossibleScore = Double.NEGATIVE_INFINITY;
        ArrayList<Move> bestMove = new ArrayList<>();
        ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move m = possibleMoves.get(i);
            board.play(m, this.getPlayerNumber());
            int score = minimaxAB(board,false,alpha,beta,4);
            if (score>highestPossibleScore){
                highestPossibleScore = score;
                bestMove = new ArrayList<>();
                bestMove.add(m);
            } else if (score==highestPossibleScore) {
                bestMove.add(m);
            }
            board.undo();
        }
        return bestMove;
    }

    public int minimax(Board board, boolean isMaximizing, int depth){
        if (depth == 0){
            return board.evaluate(this.getPlayerNumber());
        }

        if(isMaximizing){
            int bestValue = Integer.MAX_VALUE;
            ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
            for (int i = 0; i < possibleMoves.size(); i++) {
                Move move = possibleMoves.get(i);
                board.play(move,this.getPlayerNumber());
                int score = minimax(board,false, depth - 1);
                bestValue = Math.max(score,bestValue);
                board.undo();
            }
            return bestValue;
        }

        int minValue = Integer.MIN_VALUE;
        ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getOpponentNumber());
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move move = possibleMoves.get(i);
            board.play(move,this.getOpponentNumber());
            int score = minimax(board,true, depth - 1);
            minValue = Math.min(score,minValue);
            board.undo();
        }
        return minValue;
    }

    public int minimaxAB(Board board, boolean isMaximizing, int alpha, int beta, int depth){
        addNumOfExploredNodes();
        if (depth == 0){
            return board.evaluate(this.getPlayerNumber());
        }

        ArrayList<Move> possibleMoves;
        if (isMaximizing){
            possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
            for (Move m : possibleMoves) {
                board.play(m, this.getPlayerNumber());
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



    public Move getNextMove(Board board, boolean alphaBeta){

        if (alphaBeta){
            ArrayList<Move> minMaxABMoves = this.getNextMoveAB(board);
            Random random = new Random();
            int randomNumber = random.nextInt(minMaxABMoves.size()-1 + 1);

            return minMaxABMoves.get(randomNumber);
        }
        ArrayList<Move> minMaxMoves = this.getNextMoveMinMax(board);

        Random random = new Random();
        int randomNumber = random.nextInt(minMaxMoves.size()-1 + 1);

        return minMaxMoves.get(randomNumber);
    }
}
