package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.BodyOfWater;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.River;

import java.awt.*;

public class Border {

    Segment segment;
    VertexDecorator v1;
    VertexDecorator v2;
    BodyOfWater water;


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

    private void setColor(Color color) {
        segment.setColor(color);
    }

    public Color getColor() {
        return segment.getColor();
    }

    private void setThickness(float thickness) {
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

    public void setWater(BodyOfWater water) {this.water = water;}

    public boolean hasRiver() {
        return water != null && water.isRiver();
    }

    public void enhancedBorder() {
        if (hasRiver()) {
            setColor(Color.BLUE);
            setThickness(water.multiplicity());
        }
    }

    public BodyOfWater getWater(){
        return water;
    }
}