package ca.mcmaster.cas.se2aa4.a2.visualizer.Modes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * Decorator that adds visualizing neighbours on top of normal visualization
 */
public class DebugMode extends VisualMode {

    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        // Visualize Neighbours
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        visualizePolygonNeighbours(aMesh, canvas, vertexList);
        super.render(aMesh, canvas);
    }

    /**
     * Iterates through each polygon and draws a line throughout each of its polygon neighbours.
     * Light gray lines are shown through each neighbour's centroid.
     */
    protected void visualizePolygonNeighbours(Structs.Mesh aMesh, Graphics2D canvas, List<Structs.Vertex> vertexList) {
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            // Gather polygons property
            Color old = canvas.getColor();
            Stroke polygonStroke = new BasicStroke(0.5f);
            canvas.setStroke(polygonStroke);
            Structs.Vertex currentCentroid = vertexList.get(p.getCentroidIdx());
            List<Integer> polygonNeighbours = p.getNeighborIdxsList();
            for (Integer id : polygonNeighbours) {
                // Iterates throughout each neighbour and draws a line from the current centroid.
                canvas.setColor(Color.LIGHT_GRAY);
                Structs.Polygon neighbour = aMesh.getPolygons(id);
                Structs.Vertex nextCentroid = vertexList.get(neighbour.getCentroidIdx());
                Point2D point1 = new Point2D.Double(currentCentroid.getX(), currentCentroid.getY());
                Point2D point2 = new Point2D.Double(nextCentroid.getX(), nextCentroid.getY());
                Line2D line = new Line2D.Double(point1,point2);
                canvas.draw(line);
                canvas.setColor(old);
            }
        }
    }

    protected boolean isDebug() {
        return true;
    }

}
