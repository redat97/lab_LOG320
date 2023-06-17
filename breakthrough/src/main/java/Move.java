public class Move {
    private final int x_startingPosition;
    private final int y_startingPosition;
    private final int x_destinationPosition;
    private final int y_destinationPosition;
    private Integer capturedColor = 0; // True if a piece was captured

    public Move(int x_startingPosition, int y_startingPosition, int x_destinationPosition, int y_destinationPosition) {
        this.x_startingPosition = x_startingPosition;
        this.y_startingPosition = y_startingPosition;
        this.x_destinationPosition = x_destinationPosition;
        this.y_destinationPosition = y_destinationPosition;
    }

    public Move (String move){
        move = move.trim();
        this.x_startingPosition = getXNumberFromLetter(move.charAt(0));
        this.y_startingPosition = Character.getNumericValue(move.charAt(1))-1;
        this.x_destinationPosition = getXNumberFromLetter(move.charAt(5));
        this.y_destinationPosition = Character.getNumericValue(move.charAt(6))-1;
    }

    public int getX_startingPosition() {
        return x_startingPosition;
    }

    public int getY_startingPosition() {
        return y_startingPosition;
    }

    public int getX_destinationPosition() {
        return x_destinationPosition;
    }

    public int getY_destinationPosition() {
        return y_destinationPosition;
    }

    public Integer getCapturedColor() {
        return this.capturedColor;
    }

    public void setCapturedColor(int capturedColor) {
        this.capturedColor = capturedColor;
    }

    public String toString(){
        //StringBuilder destinationPosition = new StringBuilder();

        String startingPosition = this.getXLetterFromNumber(this.x_startingPosition) + (this.getY_startingPosition() + 1);
        String destinationPosition = this.getXLetterFromNumber(this.x_destinationPosition) + (this.getY_destinationPosition()+1);

        return startingPosition + "-" + destinationPosition;
    }

    public String getXLetterFromNumber(int x_position){
        if (x_position == 0) {
            return ("A");
        } else if (x_position == 1) {
            return("B");
        } else if (x_position == 2) {
            return("C");
        } else if (x_position == 3) {
            return("D");
        } else if (x_position == 4) {
            return("E");
        } else if (x_position == 5) {
            return("F");
        } else if (x_position == 6) {
            return("G");
        }
        return("H");
    }

    public int getXNumberFromLetter(char x_position){
        if (x_position == 'A') {
            return (0);
        } else if (x_position == 'B') {
            return(1);
        } else if (x_position == 'C') {
            return(2);
        } else if (x_position == 'D') {
            return(3);
        } else if (x_position == 'E') {
            return(4);
        } else if (x_position == 'F') {
            return(5);
        } else if (x_position == 'G') {
            return(6);
        }
        return(7);
    }
}
