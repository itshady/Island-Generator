package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class HexRegularMesh extends RegularMesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        int hexWall = 15;
        int apothem = (int) (hexWall * Math.sqrt(3) / 2);
        int hexHeight = apothem;
        int hexWidth = 2*apothem + hexWall;
        int spacing = apothem + hexWall/2;

        int rowCounter = 0;
        for (int i=apothem; i<height; i+=hexHeight) {
            for (int j=apothem; j<width; j+=hexWidth) {
                if (rowCounter%2 == 0) {
                    coordList.add(new Coordinate((j),(i)));
                }
                else {
                    coordList.add(new Coordinate((j+spacing),(i)));
                }
            }
            rowCounter++;
        }
        return coordList;
    }

    @Override
    protected List<Coordinate> generatePoints(Integer numOfPolygons) {
        return null;
    }
}
