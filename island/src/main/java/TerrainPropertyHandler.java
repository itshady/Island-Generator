import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.List;

public class TerrainPropertyHandler extends PropertyHandler{
    Structs.Property setTileProperty(TileType tileType) {
        String tileProperty = tileType.toString();
        return Structs.Property.newBuilder().setKey("tile_property").setValue(tileProperty).build();
    }

    Structs.Property setColorProperty(TileType tileType) {
        String landColor = 255+","+255+","+255+","+255;
        String beachColor = 242+","+243+","+200+","+255;
        String lagoonColor = 103+","+168+","+209+","+255;
        String oceanColor = 0+","+87+","+143+","+255;
        String color = oceanColor;
        switch (tileType) {
            case LAND -> color = landColor;
            case BEACH -> color = beachColor;
            case LAGOON -> color = lagoonColor;
            case OCEAN -> color = oceanColor;
        }
        return Structs.Property.newBuilder().setKey("rgba_color").setValue(color).build();
    }

    TileType extractTileProperty(List<Structs.Property> properties) {
        TileType val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("tile_property")) {
                String property = p.getValue();
                val = TileType.valueOf(property);

            }
        }
        if (val == null)
            return TileType.OCEAN;
        return val;
    }

    boolean isCentroid(List<Structs.Property> properties) {
        String val = "false";
        for (Structs.Property p : properties) {
            if (p.getKey().equals("is_centroid")) {
                val = p.getValue();
            }
        }
        return val.equals("true");
    }
}
