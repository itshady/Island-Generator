package Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
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

    @Test
    void generateColors() {
        assertNotNull(propertyHandler.generateColors());
    }

    @Test
    void averageColor() {
        // context
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(200,200,200));
        colors.add(new Color(0,0,100));
        colors.add(new Color(200,200,200));
        colors.add(new Color(0,0,100));

        // action
        Color avg = propertyHandler.averageColor(colors);

        // assertion
        assertEquals(new Color(100,100,150), avg);
    }

    @Test
    void setColorProperty() {
        // action
        Structs.Property property = propertyHandler.setColorProperty(new Color(0,0,0));

        // assertion
        assertEquals("rgba_color", property.getKey());
        assertEquals("0,0,0,255", property.getValue());
    }

    @Test
    void setThicknessProperty() {
        // action
        Structs.Property property = propertyHandler.setThicknessProperty(3f);

        // assertion
        assertEquals("thickness", property.getKey());
        assertEquals(Float.toString(3f), property.getValue());
    }

    @Test
    void setCentroidProperty() {
        // action
        Structs.Property property = propertyHandler.setCentroidProperty(true);

        // assertion
        assertEquals("is_centroid", property.getKey());
        assertEquals("true", property.getValue());

        // action
        property = propertyHandler.setCentroidProperty(false);

        // assertion
        assertEquals("is_centroid", property.getKey());
        assertEquals("false", property.getValue());
    }
}