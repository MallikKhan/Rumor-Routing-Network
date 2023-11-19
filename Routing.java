/**
 * Course: Objektorienterad programmering (Java)
 * Class name: Routing
 * Description: Stores the event ID and distance and updates and retrieves information about routing
 * 
 * @since  2023-08-16
 **/

public class Routing {
    private int eventID;
    private int distance;
    private Node nextNode;

    /**
    * Constructor: Routing
    * Description: Contains the parts for the routing
    */
    public Routing(int id, int distance, Node node) {
        this.eventID = id;
        this.distance = distance;
        this.nextNode = node;
    }

    /**
    * Method: getEventID
    * Description: Returns the id of the current event
    * @Return: eventID
    */
    public int getEventID() {
        return eventID;
    }

    /**
     * Method: getDistance
     * Description: Returns the distance of current position
     * @Return: distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Method: increaseDistance
     * Description: Increases the distance in the routing for each time it moves
     */
    public void increaseDistance() {
        this.distance++;
    }

    /**
     * Method: getNextNode
     * Description: returns the next node in the route of the program
     * @Return: nextNode
     */
    public Node getNextNode() {
        return nextNode;
    }


    /**
     * Method: equals
     * Description: looks if current instance is the same as the provided object by looking at the event ID
     * @param: object to compare with current instance
     * @return: boolean, true if equal otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != Routing.class) {
            return false;
        }
        return ((Routing) o).getEventID() == this.eventID;
    }
}
