public class Move {
    private final int verticalStart;
    private final int horizontalStart;
    private final int verticalDestination;
    private final int horizontalDestination;
    private Integer capturedColor = 0; // True if a piece was captured

    public Move(int verticalStart, int horizontalStart, int verticalDestination, int horizontalDestination) {
        this.verticalStart = verticalStart;
        this.horizontalStart = horizontalStart;
        this.verticalDestination = verticalDestination;
        this.horizontalDestination = horizontalDestination;
    }

    public Move (String move){
        move = move.trim();
        this.verticalStart = getXNumberFromLetter(move.charAt(0));
        this.horizontalStart = Character.getNumericValue(move.charAt(1));
        this.verticalDestination = getXNumberFromLetter(move.charAt(5));
        this.horizontalDestination = Character.getNumericValue(move.charAt(6));
    }

    public int getHorizontalStartingPosition() {
        return horizontalStart;
    }

    public int getVerticalStartingPosition() {
        return verticalStart;
    }

    public int getHorizontalDestinationPosition() {
        return horizontalDestination;
    }

    public int getVerticalDestinationPosition() {
        return verticalDestination;
    }

    public Integer getCapturedColor() {
        return this.capturedColor;
    }

    public void setCapturedColor(int capturedColor) {
        this.capturedColor = capturedColor;
    }

    public String toString(){
        String startingPosition =  this.getXLetterFromNumber(this.verticalStart) + (this.horizontalStart + 1);
        String destinationPosition = this.getXLetterFromNumber(this.verticalDestination) + (this.horizontalStart + 1);

        return startingPosition + "-" + destinationPosition;
    }

    public String getXLetterFromNumber(int verticalPosition){
        if (verticalPosition == 0) {
            return ("A");
        } else if (verticalPosition == 1) {
            return("B");
        } else if (verticalPosition == 2) {
            return("C");
        } else if (verticalPosition == 3) {
            return("D");
        } else if (verticalPosition == 4) {
            return("E");
        } else if (verticalPosition == 5) {
            return("F");
        } else if (verticalPosition == 6) {
            return("G");
        }
        return("H");
    }

    public int getXNumberFromLetter(char position){
        if (position == 'A') {
            return (0);
        } else if (position == 'B') {
            return(1);
        } else if (position == 'C') {
            return(2);
        } else if (position == 'D') {
            return(3);
        } else if (position == 'E') {
            return(4);
        } else if (position == 'F') {
            return(5);
        } else if (position == 'G') {
            return(6);
        }
        return(7);
    }
}
