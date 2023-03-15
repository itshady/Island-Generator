package ca.mcmaster.cas.se2aa4.a2.visualizer.Modes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.apache.batik.ext.awt.geom.Polygon2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Has implementations for methods that the types of visual modes will each possess
 */
public abstract class VisualMode {

    /**
     * Processes the data stored in the Mesh, and visualizes as an SVG
     * @param aMesh is of type Structs.Mesh
     * @param canvas is of type Graphics2D
     */
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        List<Structs.Segment> segmentsList = aMesh.getSegmentsList();

        // Visualizing polygons
        visualizePolygon(aMesh, canvas);

        // Visualizing Segments
        visualizeSegments(aMesh, canvas);

        // Visualizing Vertices and Centroids
        visualizeVertices(aMesh, canvas);
    }

    /**
     * Responsible to extracting Vertices from the Mesh (Structs) and visualizes as an SVG
     * @param aMesh: Mesh with a list of vertices
     * @param canvas: Graphics2D Canvas to draw on
     */
    protected void visualizeVertices(Structs.Mesh aMesh, Graphics2D canvas) {
        for (Structs.Vertex v : aMesh.getVerticesList()) {
            float vertexThickness = extractThickness(v.getPropertiesList());
            double centre_x = v.getX() - (vertexThickness / 2.0d);
            double centre_y = v.getY() - (vertexThickness / 2.0d);
            Color old = canvas.getColor();
            if (isCentroid(v.getPropertiesList())) {
                if (!isDebug()) continue;
                canvas.setColor(Color.RED);
            } else {
                if (isDebug()) canvas.setColor(Color.BLACK);
                else canvas.setColor(extractColor(v.getPropertiesList()));
            }
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, vertexThickness, vertexThickness);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }

    protected abstract boolean isDebug();

    /**
     * Responsible for extracting segments and draws lines between every pair of segments
     * @param aMesh: Mesh with a list of segments
     * @param canvas: Graphics2D Canvas to draw on
     */
    protected void visualizeSegments (Structs.Mesh aMesh, Graphics2D canvas) {
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        for (Structs.Segment s: aMesh.getSegmentsList()) {
            Stroke segmentStroke = new BasicStroke(extractThickness(s.getPropertiesList()));
            canvas.setStroke(segmentStroke);
            Structs.Vertex v1 = vertexList.get(s.getV1Idx());
            Structs.Vertex v2 = vertexList.get(s.getV2Idx());
            Point2D point1 = new Point2D.Double(v1.getX(), v1.getY());
            Point2D point2 = new Point2D.Double(v2.getX(), v2.getY());
            Color old = canvas.getColor();
            Color color = isDebug() ? Color.BLACK : extractColor(s.getPropertiesList());
            canvas.setColor(color);
            Line2D line = new Line2D.Double(point1, point2);
            canvas.draw(line);
            canvas.setColor(old);
        }
    }

    /**
     * Responsible for extracting polygons from a Mesh (Structs) and draws polygons for each collection of segments
     * @param aMesh: Mesh with a list of Polygons
     * @param canvas: Graphics2D Canvas to draw on
     */
    protected void visualizePolygon(Structs.Mesh aMesh, Graphics2D canvas) {
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        List<Structs.Segment> segmentsList = aMesh.getSegmentsList();
        for (Structs.Polygon p: aMesh.getPolygonsList()) {
            List<Integer> polygonSegments = p.getSegmentIdxsList();
            Color old = canvas.getColor();
            Stroke polygonStroke = new BasicStroke(extractThickness(p.getPropertiesList()));
            canvas.setStroke(polygonStroke);
            Color color = isDebug() ? Color.BLACK : extractColor(p.getPropertiesList());
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

    /**
     * Responsible for setting the order of creating polygons through updating the coordinates
     * @param vertexList: List of vertices
     * @param segmentsList: List of all segments
     * @param polygonSegments: List of segment ids from a polygon
     * @param xValues: List to put the polygon x coords in
     * @param yValues: List to put the polygon y coords in
     */
    protected void updateCoordsForPolygons(List<Structs.Vertex> vertexList, List<Structs.Segment> segmentsList, List<Integer> polygonSegments, double[] xValues, double[] yValues) {
        // makes sure that the points for the polygon are in order
        for (int i = 0; i < polygonSegments.size(); i++) {
            if (i > 0) {
                // for all the segments that aren't the first, makes sure to get the point that intersects with the previous segment.
                Structs.Segment curr = segmentsList.get(polygonSegments.get(i));
                Structs.Segment prev = segmentsList.get(polygonSegments.get(i-1));
                if (curr.getV1Idx() == prev.getV1Idx() || curr.getV1Idx() == prev.getV2Idx()) {
                    xValues[i] = vertexList.get(curr.getV1Idx()).getX();
                    yValues[i] = vertexList.get(curr.getV1Idx()).getY();
                } else {
                    xValues[i] = vertexList.get(curr.getV2Idx()).getX();
                    yValues[i] = vertexList.get(curr.getV2Idx()).getY();
                }
            } else {
                // for the first segment, makes sure it gets the vertex that isn't intersects with the second segment
                Structs.Segment segment = segmentsList.get(polygonSegments.get(i));
                Structs.Segment next = segmentsList.get(polygonSegments.get(i+1));
                if (segment.getV1Idx() != next.getV1Idx() && segment.getV1Idx() != next.getV2Idx()) {
                    xValues[i] = vertexList.get(segment.getV1Idx()).getX();
                    yValues[i] = vertexList.get(segment.getV1Idx()).getY();
                } else {
                    xValues[i] = vertexList.get(segment.getV2Idx()).getX();
                    yValues[i] = vertexList.get(segment.getV2Idx()).getY();
                }
            }
        }
    }

    /**
     * Obtains the colour property for any element that needs to be visualized
     * @param properties: List of properties
     * @return of Type Color
     */
    protected Color extractColor(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("rgba_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }

    /**
     * Checks if a given vertex is a centroid
     * @param properties: List of properties
     * @return boolean
     */
    protected boolean isCentroid(List<Structs.Property> properties) {
        String val = "false";
        for(Structs.Property p: properties) {
            if (p.getKey().equals("is_centroid")) {
                val = p.getValue();
            }
        }
        return val.equals("true");
    }

    /**
     * Obtain the thickness property for any element that needs to be visualized
     * @param properties: : List of properties
     * @return Float
     */
    protected Float extractThickness(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 2f;
        return Float.parseFloat(val);
    }

}
