package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class HoneyCombRegularMesh extends RegularMesh {
    protected List<Coordinate> generatePoints() {
        List<Coordinate> coordList = new ArrayList<>();
        int octaHeight = 5*5;
        int polyWidth = 2*5;
        int counter = 0;
        int triangleVar;
//        for (int i=octaHeight; i<height; i+= octaHeight) {
//            int counter2 = 0;
//            for (int j=(counter%2==0 ? octaHeight : octaHeight-polyWidth); j<width; j+= (counter%2==0 ? octaHeight : triangleVar)) {
//                coordList.add(new Coordinate((j),(i)));
//                if (counter2%2 == 0) triangleVar = polyWidth*2;
//                else triangleVar = octaHeight*2 - polyWidth*2;
//                counter2++;
//            }
//            counter++;
//        }
        for (int i=octaHeight; i < height; i+=octaHeight*2) {
            for (int j=octaHeight; j < width; j+=octaHeight*2) {
                coordList.add(new Coordinate(j, i));
            }
        }
        int var = polyWidth*2;
        for (int i=octaHeight*2; i < height; i+=octaHeight*2) {
            for (int j=octaHeight-polyWidth; j < width; j+=var) {
                coordList.add(new Coordinate(j, i));
                if (counter%2 != 0) var = octaHeight*2 - polyWidth*2;
                else var = polyWidth*2;
                counter++;
            }
            counter = 0;
        }
        return coordList;
    }
}
