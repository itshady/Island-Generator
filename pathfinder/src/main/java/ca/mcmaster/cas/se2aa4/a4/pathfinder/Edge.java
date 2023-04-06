package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ca.mcmaster.cas.se2aa4.a4.pathfinder.Edge.EdgeStatus.UNDISCOVERED;

public class Edge {
    private final Integer id;
    private final Double weight;
    private final Node startNode;
    private final Node endNode;
    private EdgeStatus status;
    private final List<Property> propertyList = new ArrayList<>();

    protected enum EdgeStatus {
        DISCOVERED, UNDISCOVERED,
    }

    protected Edge(Node n1, Node n2, Double weight, Integer id) {
        startNode = n1;
        endNode = n2;
        this.weight = weight;
        this.id = id;
        status = UNDISCOVERED;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
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

    // equals if start and end node are the same (can't have 2 edges from one node to another)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(startNode, edge.startNode) && Objects.equals(endNode, edge.endNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, startNode, endNode);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "id=" + id +
                ", weight=" + weight +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                ", status=" + status +
                '}';
    }
}
