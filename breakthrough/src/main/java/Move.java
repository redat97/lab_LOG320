public class Move {
    private int x_startingPosition;
    private int y_startingPosition;
    private int x_destinationPosition;
    private int y_destinationPosition;
    private Integer capturedColor = 0; // True if a piece was captured

    public Move(int x_startingPosition, int y_startingPosition, int x_destinationPosition, int y_destinationPosition) {
        this.x_startingPosition = x_startingPosition;
        this.y_startingPosition = y_startingPosition;
        this.x_destinationPosition = x_destinationPosition;
        this.y_destinationPosition = y_destinationPosition;
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
}
