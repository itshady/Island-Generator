package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.Node.NodeStatus.UNVISITED;

public class Node {
    private Integer id;
    private NodeStatus status;


    protected enum NodeStatus {
        UNVISITED, VISITED, VISITING,
    }

    public Node(Integer id) {
        this.id = id;
        status = UNVISITED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
