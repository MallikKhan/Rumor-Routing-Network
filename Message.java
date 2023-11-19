/**
 * Message class is an abstract class that represent Request and Agent.
 *
 * @since 2023-08-23
 */

public abstract class Message {
   protected Node currentNode;
   protected int eventID;
   protected int durationTime;

   /**
    * Constructor for the class Message that creates a message object.
    *
    * @param node The node that the message is currently at.
    * @param maxDuration the maximum step for the message.
    * @param eventID the event id that the message looking for.
    */
   public Message(Node node, int maxDuration, int eventID) {
      this.currentNode = node;
      this.durationTime = maxDuration;
      this.eventID = eventID;
   }

   /**
    * Abstract method for moving the request and agent.
    *
    * @return true if the request and agent move.
    */
   public abstract boolean send();

   /**
    * Gets the duration time of the message.
    *
    * @return the remaining duration time of the message.
    */
   public int getDurationTime() {
      return this.durationTime;
   }

   /**
    * Reduces the remaining duration time of the message by one time step.
    */
   public void decreaseDurationTime() {
      durationTime--;
   }
}
