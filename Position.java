import java.util.Objects;

/**
 * Course: Objektorienterad programmering (Java)
 * Class name: Position
 * Description: Gets the coordinates of the nodes
 * 
 * @since 2023-06-15
 **/

public class Position {
    private int x;
    private int y;

    /**
    * Method: Position
    * Description: set the x and y coordinates
    */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method: getX
     * Description: Returns the value of x
     * @return: int x
     */
    public int getX() {
        return x;
    }

    /**
     * Method: getY
     * Description: Returns the value of y
     * @return: int y
     */
    public int getY() {
        return y;
    }

    /**
     * Method: equals
     * Description: Compares two position to see if x and y coordinates are the same in both
     * @return: boolean
     */
    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        } else if (o.getClass() != Position.class) {
            return false;
        }
        return ((Position) o).getX() == this.x && ((Position) o).getY() == this.y;
    }




    /**
    * Method: distance
    * Description: Calculates distance between current position and given position
    * @param: position
    * @return: distance between two positions
     */
    /*New method by Napat 06-03-2023*/
    public double distance(Position position) {
        int inputX = position.getX();
        int inputY = position.getY();

        return Math.sqrt((inputY - this.y) * (inputY - this.y) + (inputX - this.x) * (inputX - this.x));
    }
}
