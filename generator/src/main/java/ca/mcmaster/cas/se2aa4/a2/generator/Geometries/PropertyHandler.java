package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PropertyHandler {
    Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }

    Color averageColor(Color color1, Color color2) {
        int red = (color1.getRed()+color2.getRed())/2;
        int blue = (color1.getBlue()+color2.getBlue())/2;
        int green = (color1.getGreen()+color2.getGreen())/2;
        int alpha = (color1.getAlpha()+color2.getAlpha())/2;
        return new Color(red, green, blue, alpha);
    }

    Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        return Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
    }

    Structs.Property setThicknessProperty(Float thickness) {
        String segmentThickness = Float.toString(thickness);
        return Structs.Property.newBuilder().setKey("thickness").setValue(segmentThickness).build();
    }

    Structs.Property setCentroidProperty(Boolean isCentroid) {
        String stringCentroid = ""+isCentroid;
        return Structs.Property.newBuilder().setKey("is_centroid").setValue(stringCentroid).build();
    }

    Color averageColor(List<Segment> segments) {
        int totalColors = segments.size();
        int red = 0;
        int blue = 0;
        int green = 0;
        int alpha = 0;
        List<Color> segmentColors = new ArrayList<>();

        for (Segment s : segments) {
            segmentColors.add(s.getColor());
        }

        for (Color color : segmentColors) {
            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha += color.getAlpha();
        }

        red = red/totalColors;
        blue = blue/totalColors;
        green = green/totalColors;
        alpha = alpha/totalColors;
        return new Color(red, green, blue, alpha);
    }
}
