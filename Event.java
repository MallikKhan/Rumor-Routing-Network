import java.util.ArrayList;

/**
 * The Event class represents information for events.
 * Each event has an ID, a position, a time step and path.
 *
 * @since 2023-05-25
 */
public class Event {
    /**
     * The ID of the event.
     */
    private int eventID;

    /**
     * The time step of the event.
     */
    private int timeStep;

    /**
     * Constructs an Event object with the ID, position and time step.
     *
     * @param id    The unique number.
     * @param time  The time step of the event.
     */
    public Event (int id, int time) throws IllegalArgumentException{
        this.eventID = id;
        this.timeStep = time;
        if (timeStep < 0) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Gets the ID of the event.
     *
     * @return the ID of the event.
     */
    public int getEventID() {
        return this.eventID;
    }

    /**
     * Gets the time step of the event.
     *
     * @return The time step of the event.
     */
    public int getTimeStep() {
        return this.timeStep;
    }

    /**
     * Prints information of the event.
     */
    @Override
    public String toString(){
        return "Event ID: " + eventID + " Time step: " + timeStep;
    }

    /**
     * Checks if this event is equal to the given object.
     *
     * @param object The object to compare.
     * @return True if the object is equal to this event, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (object.getClass() != Event.class) {
            return false;
        }
        return ((Event) object).getEventID() == this.eventID;
    }


}
