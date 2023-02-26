package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon {

    private final List<Segment> segmentList;
    private final Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private final Color color;
    private Centroid centroid;
    private final Set<Integer> neighbourIdxSet = new HashSet<>();
    private final PropertyHandler propertyHandler = new PropertyHandler();

    public Polygon(Integer id, List<Segment> segments) {
        this.id = id;
        this.color = propertyHandler.averageColor(segments);
        segmentList = segments;
    }

    public Polygon(Integer id, List<Segment> segments, Color color) {
        this.id = id;
        this.color = color;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public Polygon(Integer id, List<Segment> segments, Float thickness) {
        this.id = id;
        this.color = propertyHandler.averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public Polygon(Integer id, List<Segment> segments, Color color, Float thickness) {
        this.id = id;
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (Segment segment : segmentList) {
            idList.add(segment.getId());
        }

        //System.out.println("Segments: " + vertexPointList + " Centroid:" + centroid.getVertex());
        polygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(idList)
                .setCentroidIdx(centroid.getId())
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setThicknessProperty(thickness))
                .addAllNeighborIdxs(neighbourIdxSet).build();
    }

    public Integer getCentroidId() {
        return centroid.getId();
    }

    public Centroid getCentroid() {
        return centroid;
    }

    public Integer getId() {
        return id;
    }

    public void setCentroid(Centroid centroid) {
        this.centroid = centroid;
    }

    public Structs.Polygon getPolygon() {
        return polygon;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void addPolygonNeighbourSet (Set<Polygon> neighbourSet) {
        for (Polygon p : neighbourSet) {
            this.neighbourIdxSet.add(p.getId());
        }
    }

    public void removePolygonNeighbour (Polygon neighbour) {
        this.neighbourIdxSet.remove(neighbour.getId());
    }

    public Set<Integer> getPolygonNeighbours () {
        return this.neighbourIdxSet;
    }

}
