package ca.mcmaster.cas.se2aa4.a2.island.UI;

public interface Factory {
    /**
     * All factories must be able to create the wanted object.
     * @param s An input string to choose the object to create
     * @return Object Factory is responsible for
     */
    Object create(String s);
}
