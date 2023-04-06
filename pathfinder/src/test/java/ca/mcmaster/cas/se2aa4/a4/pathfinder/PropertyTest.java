package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {
    @Test
    void addAndExtractProperty() {
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
}