import java.util.ArrayList;

public class CPUPlayer {
    private int opponentNumber;
    private int playerNumber;
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
            int score = minimax(board,false);
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

    public int minimax(Board board, boolean isMaximizing){
        int resultat = board.evaluate(this.getPlayerNumber());
        addNumOfExploredNodes();
        if (resultat!=-1 || numExploredNodes == 1000){
            return resultat;
        }

        if(isMaximizing){
            double bestValue = Double.NEGATIVE_INFINITY;
            ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getPlayerNumber());
            for (int i = 0; i < possibleMoves.size(); i++) {
                Move move = possibleMoves.get(i);
                board.play(move,this.getPlayerNumber());
                int score = minimax(board,false);
                bestValue = Double.max(score,bestValue);
                board.undo();
            }
            return (int) bestValue;
        }

        double minValue = Double.POSITIVE_INFINITY;
        ArrayList<Move> possibleMoves = board.getPossibleMoves(this.getOpponentNumber());
        for (int i = 0; i < possibleMoves.size(); i++) {
            Move move = possibleMoves.get(i);
            board.play(move,this.getOpponentNumber());
            int score = minimax(board,true);
            minValue = Double.min(score,minValue);
            board.undo();
        }
        return (int) minValue;

    }

}
