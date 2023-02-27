package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyHandlerTest {
    private static PropertyHandler propertyHandler;

    @BeforeAll
    public static void setupTestObjects() {
        propertyHandler = new PropertyHandler();
    }

    @BeforeAll
    static void setup() {
        System.out.println("PropertyHandler Tests Starting.");
    }

    @AfterAll
    static void teardown() {
        System.out.println("PropertyHandler Tests Ending.");
    }

    @Test
    void generateColors() {
        assertNotNull(propertyHandler.generateColors());
    }

    @Test
    void averageColor() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(200,200,200));
        colors.add(new Color(0,0,100));
        colors.add(new Color(200,200,200));
        colors.add(new Color(0,0,100));
        Color avg = propertyHandler.averageColor(colors);
        assertEquals(new Color(100,100,150), avg);
    }

    @Test
    void setColorProperty() {
        Structs.Property property = propertyHandler.setColorProperty(new Color(0,0,0));

        assertEquals("rgba_color", property.getKey());
        assertEquals("0,0,0,255", property.getValue());
    }

    @Test
    void setThicknessProperty() {
        Structs.Property property = propertyHandler.setThicknessProperty(3f);

        assertEquals("thickness", property.getKey());
        assertEquals(Float.toString(3f), property.getValue());
    }

    @Test
    void setCentroidProperty() {
        Structs.Property property = propertyHandler.setCentroidProperty(true);

        assertEquals("is_centroid", property.getKey());
        assertEquals("true", property.getValue());

        property = propertyHandler.setCentroidProperty(false);

        assertEquals("is_centroid", property.getKey());
        assertEquals("false", property.getValue());
    }
}