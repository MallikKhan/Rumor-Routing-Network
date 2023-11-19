import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Environment class represents an environment(network) with nodes and events.
 * It manages the nodes and their relationships(neighbours),
 * including generation of random events with the environment.
 * Moreover, the environment performs rumor routing algorithm.
 *
 * @since 2023-05-25
 */

public class Environment {
    public static void main(String[] args) throws FileNotFoundException {
        int simulationTime = 10000;

        File inputFile = new File(args[0]);
        Scanner data = new Scanner(inputFile);
        Environment environment = new Environment(data);
        String information;
        for (int i = 0; i < simulationTime; i++) {
            environment.performRumorRouting();
        }
        information = environment.toString();
        System.out.println(information);
    }

    /*New changed*/
    private Random random = new Random();
    private int eventIdCounter;
    private int numberOfNodes;
    private int numberOfSentRequests;
    private int numberOfFoundEvent;
    private int time;
    private ArrayList<Node> requestNodes = new ArrayList<>();
    private ArrayList<Request> sentRequest = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private int agentDuration = 50;
    private int requestDuration = 45;
    private int numOfRequestNodes = 4;


    /**
     * Constructs an Environment object by reading input from a Scanner.
     * It initializes the environment with the specified number of nodes and their positions.
     *
     * @param input A Scanner object
     */
    public Environment(Scanner input) {
        this.numberOfNodes = input.nextInt();

        /*Move to the next line after reading the number of nodes*/
        input.nextLine();

        for (int i = 0; i < this.numberOfNodes; i++) {
            String line = input.nextLine();
            String[] lineArray = line.split(",");
            Position position = new Position(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1]));
            Node node = new Node(position);
            nodes.add(node);
        }
        setupNeighbours();
        setupRequestNodes();
    }

    /**
     * Finds all neighbours node and sets up the neighbours for each node.
     */
    private void setupNeighbours() {
        for (int nodeIndex = 0; nodeIndex < this.numberOfNodes; nodeIndex++) {
            ArrayList<Node> neighbours = new ArrayList<>();
            Node currentNode = nodes.get(nodeIndex);
            for (int otherIndex = 0; otherIndex < this.numberOfNodes; otherIndex++) {
                Node otherNodes = nodes.get(otherIndex);
                if (!currentNode.getPosition().equals(otherNodes.getPosition())) {
                    double distance = currentNode.getPosition().distance(otherNodes.getPosition());
                    if (distance <= 15) {
                        neighbours.add(otherNodes);
                    }
                }
            }
            /*set the neighbours to the node*/
            currentNode.insertNeighbours(neighbours);
        }
    }

    /**
     * Selected randomly nodes to represent request nodes.
     */
    private void setupRequestNodes() {
        for (int i = 0; i < numOfRequestNodes; i++) {
            /*Generate a random index to select a node from the allNodes HashMap*/
            int randomIndex = random.nextInt(nodes.size());

            /*Add the randomly requested node to the requestNodes list*/
            requestNodes.add(nodes.get(randomIndex));
        }
    }

    /**
     * Generate a random event for a given node in the environment.
     * the event is associated with an agent, if it generated and added to the event table in the given node.
     *
     * @param node The node to generate the event.
     */
    private void generateEvent(Node node) {
        /*Create a new event with the currently time step*/
        Event event = new Event(eventIdCounter, time);
        eventIdCounter++;
        /*Node's method that can insert new event, and it's creation time*/
        node.insertEvent(event, agentDuration);
    }

    /**
     * Runs the rumor routing algorithm.
     */
    public void performRumorRouting() {

        if (eventIdCounter != 0 && time % 400 == 0) {
            for (Node requestNode : requestNodes) {
                Request request = new Request(requestNode, requestDuration, random.nextInt(eventIdCounter));
                this.numberOfSentRequests++;
                sentRequest.add(request);
                requestNode.insertMessage(request);
            }
        }

        Iterator<Request> requestIterator = sentRequest.iterator();

        while (requestIterator.hasNext()) {
            Request request = requestIterator.next();
            if (request.isRequestReturned()) {
                sentRequest.remove(request);
                numberOfFoundEvent++;
                break;
            }else if (request.getDurationTime() == 0 && !request.isSentTwice()) {
                request.resendingRequest(requestDuration);
            } else if (request.isSentTwice()) {
                requestIterator.remove();
            }
        }

        for (Node node : nodes) {
            if (random.nextInt(10000) == 1) {
                generateEvent(node);
            }
            node.sendMessage();
        }

        updateTime();
    }

    /**
     * Increase the time step of the program.
     */
    private void updateTime() {
        time++;
        for (Node node: nodes) {
            node.setBusyStatus(false);
        }
    }

    /**
     * Gets the total number of nodes in the environment.
     *
     * @return The number of nodes.
     */
    public int getNumberOfNodes() {
        if(nodes.size() != this.numberOfNodes) {
            return 0;
        }
        return this.numberOfNodes;
    }

    /**
     * Return a string that represent information of the program.
     *
     * @return A formatted string that provides information about the result of the program.
     */
    @Override
    public String toString() {
        return "Number of nodes created: " + numberOfNodes + "\n" +
                "Number of events created: " + eventIdCounter + "\n" +
                "Number of sent request: " + numberOfSentRequests + "\n" +
                "Number of found events: " + numberOfFoundEvent;
    }
}

