package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.BodyOfWater;

import java.awt.*;

/**
 * Border holds a mesh segment and adds additional properties to it
 */

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

    public void setColor(Color color) {
        segment.setColor(color);
    }

    public void setThickness(float thickness) {
        segment.setThickness(thickness);
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

    public BodyOfWater getWater(){
        return water;
    }
}