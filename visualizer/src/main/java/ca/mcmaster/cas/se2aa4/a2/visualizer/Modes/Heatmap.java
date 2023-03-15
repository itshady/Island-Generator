package ca.mcmaster.cas.se2aa4.a2.visualizer.Modes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Heatmap extends VisualMode {
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        super.render(aMesh,canvas);
    }

    protected void visualizePolygon(Structs.Mesh aMesh, Graphics2D canvas) {
        java.util.List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        java.util.List<Structs.Segment> segmentsList = aMesh.getSegmentsList();
        for (Structs.Polygon p: aMesh.getPolygonsList()) {
            List<Integer> polygonSegments = p.getSegmentIdxsList();
            Color old = canvas.getColor();
            Stroke polygonStroke = new BasicStroke(extractThickness(p.getPropertiesList()));
            canvas.setStroke(polygonStroke);
            Structs.Vertex centroid = aMesh.getVertices(p.getCentroidIdx());
            Color color = isDebug() ? Color.BLACK : getAltitudeColor(extractAltitude(centroid.getPropertiesList()), aMesh);
            canvas.setColor(color);
            double[] xValues = new double[polygonSegments.size()];
            double[] yValues = new double[polygonSegments.size()];
            updateCoordsForPolygons(vertexList, segmentsList, polygonSegments, xValues, yValues);
            Path2D.Double polygon = new Path2D.Double();

            polygon.moveTo(xValues[0], yValues[0]);
            for(int i = 1; i < xValues.length; ++i) {
                polygon.lineTo(xValues[i], yValues[i]);
            }
            polygon.closePath();
            if (isDebug()) canvas.draw(polygon);
            else canvas.fill(polygon);
            canvas.setColor(old);
        }
    }

    private Color getAltitudeColor(Integer altitude, Structs.Mesh mesh) {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(233,62,58));
        colors.add(new Color(237,104,60));
        colors.add(new Color(243,144,63));
        colors.add(new Color(253,199,12));
        colors.add(new Color(255,243,59));

        Color ocean = new Color(0,0,255);

        Integer max = getMaxAltitude(mesh.getVerticesList());
        Integer min = getMinAltitude(mesh.getVerticesList());
        int separation = (max-min)/colors.size();

        if (altitude <= min) return ocean;
        else if (altitude <= min+separation) return colors.get(4);
        else if (altitude <= min+separation*2) return colors.get(3);
        else if (altitude <= min+separation*3) return colors.get(2);
        else if (altitude <= min+separation*4) return colors.get(1);
        else return colors.get(0);
    }

    private Integer getMaxAltitude(List<Structs.Vertex> vertices) {
        Integer max = 0;
        for (Structs.Vertex vertex : vertices) {
            Integer altitude = extractAltitude(vertex.getPropertiesList());
            if (altitude > max) max = altitude;
        }
        return max;
    }

    private Integer getMinAltitude(List<Structs.Vertex> vertices) {
        Integer min = extractAltitude(vertices.get(0).getPropertiesList());
        for (Structs.Vertex vertex : vertices) {
            Integer altitude = extractAltitude(vertex.getPropertiesList());
            if (altitude < min) min = altitude;
        }
        return min;
    }

    protected boolean isDebug() {
        return false;
    }
}
