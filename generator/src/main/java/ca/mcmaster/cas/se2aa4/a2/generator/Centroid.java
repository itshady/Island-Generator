package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Random;

public class Centroid extends Vertex{

    private Structs.Vertex centroid;
    private Color color;
    private Integer id;

    public Centroid() {
        this.color = generateCentroidColor();
    }


    public void generateCentroid() {

    }

    private Color generateCentroidColor() {
        Random bag = new Random();
        int red = 255;
        int green = 0;
        int blue = 0;
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }
}
