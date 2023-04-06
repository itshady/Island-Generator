package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.Node.NodeStatus.UNVISITED;

public class Node {
    private final Integer id;
    private final NodeStatus status;
    private final List<Property> propertyList = new ArrayList<>();

    protected enum NodeStatus {
        UNVISITED, VISITED, VISITING,
    }

    protected Node(Integer id) {
        this.id = id;
        status = UNVISITED;
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
