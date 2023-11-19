import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * The Request class tries to find specific events. It takes random routes in
 * the environment and tries to find the event or the path to the event. When
 * found it goes back the sam route it took to find the event and then prints it.
 *
 * @since 2023-05-25
 */
public class Request extends Message {
    /**
     * The current node request is on.
     */
    private Node sourceNode;
    /**
     * The route the request has taken until it finds the event or path to event it's looking for.
     */
    private Stack<Node> route = new Stack<>();
    /**
     * The life length of the agent.
     */
    private int timeRequestSent;
    private boolean sentTwice;
    private boolean requestReturned;
    private boolean foundRequestEvent;
    private String msg;

    /**
     * This is the constructor for request. To be able to create a request it
     * needs its life length, the request it's looking for and from which node
     * it starts to move from.
     *
     * @param lifeLength Life length for request.
     * @param requestedEvent The event that is searched for.
     * @param startNode   The start node.
     */
    public Request(Node startNode, int lifeLength, int requestedEvent) {
        super(startNode, lifeLength, requestedEvent);

        route.push(currentNode);
        sourceNode = currentNode;
        requestReturned = false;
    }


    /**
     * This is the method that makes request move. It starts to check if requests
     * life length has run out, if it has the method stops running. Otherwise, the
     * method checks the node it's on to see if it has the path to the event it's
     * looking for or the actual event. If it has either of them, request moves
     * towards that event until it finds where it happened. Then it takes the
     * same route it took to find the event but backwards until it reaches the
     * starting point of the request adn prints information about the event.
     *
     * If request doesn't find the event it's looking for on its current event
     * it will go to a random neighbour.
     *
     * The process mentioned above will continue until the life length runs out.
     * Then the request will be sent out again. If the request fails again it
     * will give up. But the second try doesn't happen from this function, it
     * will happen from the main function.
     *
     */
    public boolean send() {
        Routing routingEntry = currentNode.getRoutingTable().get(eventID);

        if (requestReturned) {
            this.durationTime = 0;
            return false;
        } else if (!foundRequestEvent) {
            if (routingEntry != null && routingEntry.getDistance() == 0 && currentNode.isContainsEvent(eventID)) {
                msg = this.toString();
                foundRequestEvent = true;
                if (!route.peek().getBusyStatus()) {
                    moveBackwards();
                    return true;
                }
            } else if (routingEntry != null && !routingEntry.getNextNode().getBusyStatus()) {
                route.push(currentNode);
                routingEntry.getNextNode().insertMessage(this);
                currentNode = routingEntry.getNextNode();
                return true;
            } else {
                ArrayList<Node> availableNeighbour = currentNode.getNeighbours();
                if (!availableNeighbour.isEmpty()) {

                    route.push(currentNode);
                    currentNode = availableNeighbour.get(new Random().nextInt(availableNeighbour.size()));
                    currentNode.insertMessage(this);
                    decreaseDurationTime();
                    return true;
                }
            }
        } else if(!route.peek().getBusyStatus()) {
            moveBackwards();
            return true;
        }
        return false;
    }

    /**
     * This method is used when the event that request is looking for has been
     * found. Then it takes the route request has taken and moves according to it
     * but backwards until it from where request.
     */
    public void moveBackwards() {
        if (!route.isEmpty()) {
            Node previousNode = route.pop();
            previousNode.insertMessage(this);
            currentNode = previousNode;
        }

        if (route.isEmpty()) {
            requestReturned = true;
            System.out.println(msg);
        }
    }

    /**
     * Sends the request again and the request is marked as having been sent twice to prevent further resending.
     *
     * @param requestDuration the maximum step for the request.
     */
    public void resendingRequest(int requestDuration) {
        currentNode = sourceNode;
        durationTime = requestDuration;
        currentNode.insertMessage(this);
        sentTwice = true;
    }

    /**
     * Checks if the request has returned.
     *
     * @return true if the request has returned.
     */
    public boolean isRequestReturned() {
        return requestReturned;
    }

    /**
     * Checks if the request has been sent twice.
     *
     * @return true if the request has benn sent twice
     */
    public boolean isSentTwice() {
        return sentTwice;
    }

    /**
     * This method prints the saved info about the found event. The position
     * and timeStep and id number of the event.
     */
    @Override
    public String toString(){
        Event event = currentNode.getEvent(eventID);
        return "Event ID: " + event.getEventID() +
                " At Position: " + currentNode.getPosition().getX() + ", " + currentNode.getPosition().getY() +
                " Created Time: " + event.getTimeStep();
    }
}
