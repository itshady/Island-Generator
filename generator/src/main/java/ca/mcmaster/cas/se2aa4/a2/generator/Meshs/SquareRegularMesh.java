package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SquareRegularMesh extends RegularMesh {
    private final int square_size = 20;

    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        for (int i=0; i<height; i+=square_size) {
            for (int j=0; j<width; j+=square_size) {
                coordList.add(new Coordinate((j+square_size/2.0),(i+square_size/2.0)));
            }
        }
        return coordList;
    }
}
