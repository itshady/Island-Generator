package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class RegularMesh extends Mesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        for (int i=0; i<matrixHeight; i+=matrixWidth/square_size) {
            for (int j=0; j<matrixWidth; j+=matrixWidth/square_size) {
                coordList.add(new Coordinate((j+square_size/2),(i+square_size/2)));
            }
        }
        return coordList;
    }
}
