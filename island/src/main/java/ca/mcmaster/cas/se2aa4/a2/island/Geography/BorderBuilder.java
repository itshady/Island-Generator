package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Segment;


/**
 * Creation class for borders
 */
public class BorderBuilder {
    private Segment segment;
    private VertexDecorator v1;
    private VertexDecorator v2;

    public BorderBuilder addV1(VertexDecorator v1) {
        this.v1 = v1;
        return this;
    }

    public BorderBuilder addV2(VertexDecorator v2) {
        this.v2 = v2;
        return this;
    }

    public BorderBuilder addSegment(Segment segment) {
        this.segment = segment;
        return this;
    }

    public Border build() {
        if (segment == null || v1 == null || v2 == null) throw new IllegalArgumentException("Segment, v1 and v2 are required.");
        return new Border(segment, v1, v2);
    }
}


