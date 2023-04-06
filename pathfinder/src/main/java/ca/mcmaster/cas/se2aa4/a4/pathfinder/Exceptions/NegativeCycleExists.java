package ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions;

public class NegativeCycleExists extends Exception {
    public NegativeCycleExists() {
        super();
    }

    public NegativeCycleExists(String message) {
        super(message);
    }
}
