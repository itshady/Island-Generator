package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Segment;
import Geometries.Vertex;

import java.awt.*;

public class Border {

    Segment segment;
    VertexDecorator v1;
    VertexDecorator v2;


    public Segment getSegment() {
        return segment;
    }

    public static BorderBuilder newBuilder() {
        return new BorderBuilder();
    }

    protected Border(Segment segment, VertexDecorator v1, VertexDecorator v2) {
        this.segment = segment;
        this.v1 = v1;
        this.v2 = v2;
    }

    public void setColor(Color color) {
        segment.setColor(color);
    }

    public Color getColor() {
        return segment.getColor();
    }

    public void setThickness(float thickness) {
        segment.setThickness(thickness);
    }

    public Float getThickness() {
        return segment.getThickness();
    }

    public VertexDecorator getV1() {
        return v1;
    }

    public VertexDecorator getV2() {
        return v2;
    }
}