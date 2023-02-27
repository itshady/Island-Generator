package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon {

    private final List<Segment> segmentList;
    private Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private final Color color;
    private Centroid centroid;
    private final Set<Integer> neighbourIdxSet = new HashSet<>();
    private final PropertyHandler propertyHandler = new PropertyHandler();

    public Polygon(List<Segment> segments) {
        this.color = propertyHandler.averageColor(segments);
        segmentList = segments;
        validateSegments();
    }

    public Polygon(List<Segment> segments, Color color) {
        this.color = color;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
        validateSegments();
    }

    public Polygon(List<Segment> segments, Float thickness) {
        this.color = propertyHandler.averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
        validateSegments();
    }

    public Polygon(List<Segment> segments, Color color, Float thickness) {
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
        validateSegments();
    }

    /**
     * A polygon must be comprised of 3 or more segments
     */
    private void validateSegments() {
        if (segmentList.size() <= 2) throw new IllegalCallerException("Must have more than 2 segments. Given " + segmentList.size());
    }

    public void setId(Integer newId) {
        id = newId;
    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (Segment segment : segmentList) {
            idList.add(segment.getId());
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return new HashSet<>(segmentList).equals(new HashSet<>(polygon.segmentList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(new HashSet<>(segmentList));
    }
}