package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IrregularMesh extends Mesh {

    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        Random bag = new Random();
        int rangeMin = 0;
        for (int i=0; i<bag.nextInt(100,150); i++) {
            double rand1 = ((int)((rangeMin + (width - rangeMin) * bag.nextDouble())*100))/100.0;
            double rand2 = ((int)((rangeMin + (height - rangeMin) * bag.nextDouble())*100))/100.0;
            coordList.add(new Coordinate(rand1,rand2));
        }
        return coordList;
    }
}
