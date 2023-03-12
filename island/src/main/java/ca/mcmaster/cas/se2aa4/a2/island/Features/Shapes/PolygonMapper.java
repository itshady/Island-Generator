package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.ADTtoJTSConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;
import org.locationtech.jts.geom.Polygon;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PolygonMapper {
    Map<Polygon, Geometries.Polygon> polygonReferences = new LinkedHashMap<>();

    /**
     * Maps the JTSPolygon to its ADT version
     */
    public Map<Polygon, Geometries.Polygon> mapPolygons(ADTContainer container) {
        for (Geometries.Polygon p : container.getPolygons()) {
            Polygon JTSPolygon = ADTtoJTSConverter.polygonToJTS(p);
            polygonReferences.put(JTSPolygon, p);
        }
        return polygonReferences;
    }
}
