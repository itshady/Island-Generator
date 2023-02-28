package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class HoneyCombRegularMesh extends RegularMesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        int polyHeight = 5*5;
        int polyWidth = 2*5;
        int counter = 0;

        // get the centroids of the uneven hexagons
        for (int i=polyHeight; i < height; i+=polyHeight*2) {
            for (int j=polyHeight; j < width; j+=polyHeight*2) {
                coordList.add(new Coordinate(j, i));
            }
        }

        // get the centroids of the sideways trapezoids
        int var = polyWidth*2;
        for (int i=polyHeight*2; i < height; i+=polyHeight*2) {
            for (int j=polyHeight-polyWidth; j < width; j+=var) {
                coordList.add(new Coordinate(j, i));
                if (counter%2 != 0) var = polyHeight*2 - polyWidth*2;
                else var = polyWidth*2;
                counter++;
            }
            counter = 0;
        }
        return coordList;
    }
}
