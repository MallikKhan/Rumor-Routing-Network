import java.util.*;

/**
 * The Agent class spreads information and the route for a specific event.
 * It takes random routes in the environment and exchanges the info and the
 * shortest route to the event. It does so until it runs out of life
 *
 * @since 2023-05-25
 */

public class Agent extends Message {

    /**
     * All the visited nodes of the agent.
     */
    private ArrayList<Node> visitedNodes = new ArrayList<>();

    /**
     * The routingTable of the Agent which contains events.
     */
    private HashMap<Integer, Routing> routingTable = new HashMap<>();

    /**
     * This is the constructor for Agent. To be able to create an Agent it
     * needs its life length, the eventID it's going to spread and
     * which node it starts to move from.
     *
     * @param node The node where agent starts.
     * @param maxDuration The life of the agent.
     * @param eventID   The event that agent will spread.
     */
    public Agent(Node node, int maxDuration, int eventID) {
        super(node, maxDuration, eventID);
        visitedNodes.add(node);
    }

    /**
     * This method moves the agent from a node to the other. It starts by checking
     * the neighbours of the current node. It chooses a random neighbour node.
     * If the agent hasn't been there, and it isn't busy it moves there and turns
     * the busy status true. All events get increased distance and agent loses
     * a life. Agent and the node gets updated.
     *
     * @return If the random chosen neighbour isn't busy and hasn't been visited
     * true is returned otherwise false
     */
    public boolean send() {
        ArrayList<Node> neighbours = currentNode.getNeighbours();

        Collections.shuffle(neighbours);

        for (Node neighbour : neighbours) {
            if (!visitedNodes.contains(neighbour) &
                    !neighbour.getBusyStatus()) {
                visitedNodes.add(currentNode);
                currentNode.setBusyStatus(true);
                this.currentNode = neighbour;

                /*Increase the distance for each entry in the routingTable*/
                for (Routing routing : routingTable.values()) {
                    routing.increaseDistance();
                }

                /*Decrease the duration time of the agent, also lifetime*/
                decreaseDurationTime();

                currentNode.insertMessage(this);
                updateTable(routingTable, currentNode.getRoutingTable());
                return true;
            }
        }
        return false;
    }


    /**
     * This method exchanges info about events from an agent routing to a node
     * routing and then from node routing to agent routing.
     *
     * The information that gets exchanged is events that one of the routing
     * doesn't have and if one of the routings have shorter distance to the
     * same event.
     *
     * @param agent The agent that contains information to exchange.
     * @param node The node that contains information to exchange.
     */
    public void updateTable(HashMap<Integer, Routing> agent, HashMap<Integer, Routing> node) {
        for (Map.Entry<Integer, Routing> entry : node.entrySet()) {
            int eventID = entry.getKey();
            Routing nodeRouting = entry.getValue();
            Routing agentRouting = agent.get(eventID);

            if (agentRouting == null || nodeRouting.getDistance() <= agentRouting.getDistance()) {
                agent.put(eventID, new Routing(eventID, nodeRouting.getDistance(), nodeRouting.getNextNode()));
            }
        }

        for (Map.Entry<Integer, Routing> entry : agent.entrySet()) {
            int eventID = entry.getKey();
            Routing agentRouting = entry.getValue();
            Routing nodeRouting = node.get(eventID);

            if (nodeRouting == null || agentRouting.getDistance() < nodeRouting.getDistance()) {
                node.put(eventID, new Routing(eventID, agentRouting.getDistance(), agentRouting.getNextNode()));
            }
        }
    }
}

