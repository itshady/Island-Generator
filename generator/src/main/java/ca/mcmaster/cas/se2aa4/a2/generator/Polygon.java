package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private List<Segment> segmentList;
    private Integer id;
    private Structs.Polygon polygon;

    public Polygon(List<Segment> segments) {
        segmentList = segments;
    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < segmentList.size(); i++) {
            idList.add(segmentList.get(i).getId());
            System.out.print(segmentList.get(i).getId() + ", ");
        }
        System.out.println();
        polygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(idList).build();
    }

    public Structs.Polygon getPolygon() {
        return polygon;
    }


}
