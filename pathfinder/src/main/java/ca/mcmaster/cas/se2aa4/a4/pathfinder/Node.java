package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Node used in Graph.
 */
public class Node {
    private final Integer id;
    private NodeStatus status;
    private final List<Property> propertyList = new ArrayList<>();
    private Double cost;

    protected enum NodeStatus {
        UNVISITED, VISITED, VISITING,
    }

    protected Node(Integer id) {
        this.id = id;
        status = NodeStatus.UNVISITED;
    }

    protected Double getCost() {
        return cost;
    }

    protected void setCost(Double cost) {
        this.cost = cost;
    }

    protected NodeStatus getStatus() {
        return status;
    }

    protected void setStatus(NodeStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void addProperty(Property property) {
        propertyList.add(property);
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public Property extractProperty(String key) {
        return propertyList.stream().filter(e -> Objects.equals(e.getKey(), key)).findFirst().orElse(null);
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

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
