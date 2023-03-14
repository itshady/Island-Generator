package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Mutable Mesh.Mesh that generates diamond tessellation.
 */
public class DiamondRegularMesh extends RegularMesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        int diamondWall = 20;
        // diamond width is determined by the mathematical calculation between the apothem of the current shape and the next
        int diamondWidth = (int) (2*(diamondWall) + (2/Math.sqrt(3))*(diamondWall - (diamondWall*(Math.sqrt(3)))/2));
        int rowCounter = 0;
        // loop through and add each point
        for (double i=0; i<height; i+=diamondWall+1.5) {
            for (double j=0; j<width; j+=diamondWidth) {
                if (rowCounter%2 == 0) {
                    coordList.add(new Coordinate((j),(i)));
                }
                else {
                    coordList.add(new Coordinate((j+diamondWidth/2.0),(i)));
                }
            }
            rowCounter++;
        }
        return coordList;
    }
}

