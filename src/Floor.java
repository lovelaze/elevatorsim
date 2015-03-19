import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */
public class Floor {
    private boolean specialFloor;
    private boolean terminalFloor;
    private int level;
    private ArrayList<Passenger> passengers;

    public int getLevel() {
        return level;
    }

    public boolean isSpecialFloor() {
        return specialFloor;
    }

    public boolean isTerminalFloor() {
        return terminalFloor;
    }

    public Floor(int level) {
        this (false, false, level);
    }

    public Floor(int level, boolean terminalFloor) {
        this(false, terminalFloor, level);
    }

    public Floor(boolean specialFloor, int level) {
        this(specialFloor, false, level);
    }

    public Floor(boolean specialFloor, boolean terminalFloor, int level) {
        this.specialFloor = specialFloor;
        this.terminalFloor = terminalFloor;
        this.level = level;
        passengers = new ArrayList<Passenger>();
    }

}
