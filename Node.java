import java.util.*;

/**
 * Course: Objektorienterad programmering (Java)
 * Class name: Node
 * Description: The node represents all the positions in the environment.
 * The whole environment is made of nodes.
 * v1.0
 * @since 2023-05-25
 **/
public class Node {
    private ArrayList<Node> neighbours = new ArrayList<>();
    private Position position;
    private HashMap<Integer, Routing> routingTable = new HashMap<>();
    private HashMap<Integer, Event> eventTable = new HashMap<>();
    private boolean busyStatus = false;
    private boolean agentStatus;
    private Queue<Message> messageQueue = new LinkedList<>();

    /**
     * Constructor: Node
     * Description: Contains the necessary building blocks for a node.
     * The node position, the agents chance and the agent lifespan.
     * @param pos - Node position
     */

    public Node(Position pos) {
        this.position = pos;
    }

    /**
     * Method: insertNeighbours
     * Description: Sets the neighbouring nodes to this node.
     * @param inputNeighbours
     */
    public void insertNeighbours(ArrayList<Node> inputNeighbours){
        this.neighbours = inputNeighbours;
    }
    /**
     * Method: setBusyStatus
     * Description: Sets the status of the node to the current status(Busy or not).
     */
    public void setBusyStatus(boolean status) {
        this.busyStatus = status;
    }

    /**
     * Method: getBusyStatus
     * Description: Returns the current status of the node.
     * @return
     */
    public Boolean getBusyStatus() {
        return busyStatus;
    }

    /**
     * Method: getAgentStatus
     * Description: Returns the current status of the agent.
     * @return
     */
    public Boolean getAgentStatus() {return agentStatus; }

    /**
     * Method: getEvent
     * Description: Takes the requested eventID and returns the corresponding
     * event from the eventTable.
     * @param eventID
     * @return
     */
    public Event getEvent(int eventID) {
        return eventTable.get(eventID);
    }

    /**
     * Method: getNeighbours
     * Description: Returns the nodes that are neighbours with this node.
     * @return neighbours
     */
    public ArrayList<Node> getNeighbours() {
        return this.neighbours;
    }

    /**
     * Method: insertMessage
     * Description: Inserts the message in to the message queue.
     * @param message
     */
    public void insertMessage(Message message) {
        messageQueue.add(message);
        this.busyStatus = true;
    }

    /**
     * Method: insertEvent
     * Description: Adds the event to the nodes routingTable and the
     * eventTable. Has a 50 percent change of creating an agent and
     * adding it to the nodes queue.
     * @param event
     */
    public void insertEvent(Event event, int agentDuration) {

        Random random = new Random();
        int agentChance = random.nextInt(2);
        this.agentStatus = false;

        Routing route = new Routing(event.getEventID(), 0, this);
        routingTable.put(event.getEventID(), route);
        eventTable.put(event.getEventID(), event);

        if (agentChance == 1) {
            Agent agent = new Agent(this, agentDuration, event.getEventID());
            this.agentStatus = true;
            messageQueue.add(agent);
        }
    }

    /**
     * Method: sendMessage
     * Description: Sends the message to another node and removes it from the queue.
     */
    public void sendMessage() {
        if (!messageQueue.isEmpty()) {
            if (messageQueue.peek().getDurationTime() > 0) {
                if (messageQueue.peek().send()) {
                    messageQueue.remove();
                    busyStatus = true;
                }
            } else {
                messageQueue.remove();
                sendMessage();
            }
        }
    }

    /**
     * Method: getPosition
     * Description: Returns the position of the node.
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Method: getRoutingTable
     * description: Returns the routingTable that belong to this node.
     * @return routingTable
     */
     public HashMap<Integer, Routing> getRoutingTable() {
        return routingTable;
    }

    /**
     * Method: isContainsEvent
     * description: Returns true if ´eventTable´ contains the given event id.
     * @param eventID the given event id.
     * @return
     */
    public boolean isContainsEvent(int eventID) {
         return eventTable.containsKey(eventID);
    }

}
