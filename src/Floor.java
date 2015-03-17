/**
 * Created by Fredrik on 3/17/2015.
 */
public class Floor {
    private boolean specialFloor;
    private boolean terminalFloor;
    private int level;

    public Floor (int level) {
        this (false, false, level);
    }

    public Floor (boolean specialFloor, boolean terminalFloor, int level) {
        this.specialFloor = specialFloor;
        this.terminalFloor = terminalFloor;
        this.level = level;
    }

}
