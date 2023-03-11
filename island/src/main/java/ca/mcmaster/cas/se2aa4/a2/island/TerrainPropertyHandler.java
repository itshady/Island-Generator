package ca.mcmaster.cas.se2aa4.a2.island;

import Geometries.PropertyHandler;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public class TerrainPropertyHandler extends PropertyHandler {

    /**
     * @param tileType : Polygon's desired tile type.
     * @return : The tile property based on its parameter.
     */
    Structs.Property setTileProperty(TileType tileType) {
        String tileProperty = tileType.toString();
        return Structs.Property.newBuilder().setKey("tile_property").setValue(tileProperty).build();
    }

    /**
     * @param tileType : Polygon's current tile type.
     * @return : The colour property based on its type.
     */
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

    /**
     * Extracts the tile type from the polygon's properties.
     */
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

}
