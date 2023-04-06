package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    @Test
    void addAndExtractPropertyFromNode() {
        GraphADT graph = new GraphADT(false);
        try {
            Property property = Property.newBuilder().setKey("testing").setValue("testValue").build();
            graph.addNode(0);
            graph.getNode(0).addProperty(property);
        } catch (IdAlreadyExists e) {
            System.out.println(e.getMessage());
        }

        assertEquals("testValue", graph.getNode(0).extractProperty("testing").getValue());
    }

    @Test
    void addAndExtractPropertyFromEdge() {
        GraphADT graph = new GraphADT(false);
        try {
            Property property = Property.newBuilder().setKey("testing").setValue("testValue").build();
            graph.addNode(0);
            graph.addNode(1);
            graph.addEdge(0, 1, 1, 0);
            graph.getEdge(0).forEach(e -> e.addProperty(property));
        } catch (NoSuchNodeExists | IdAlreadyExists e) {
            System.out.println(e.getMessage());
        }

        graph.getEdge(0).forEach(
                e -> assertEquals("testValue", e.extractProperty("testing").getValue())
        );
    }
}