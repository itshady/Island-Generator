# Island Generator

This is an island generator that works on already created meshes.

## Command Line Args
| shortOption | longOption | Args? | Arg value | Desc | Required |
|:--:|---------------|------|-------|-----|--------|
| o | output | x | .mesh file | Specify the output mesh file | x |
| i | input | x | .mesh file | Specify the input mesh file | x |
| shape | shape | x | String | Select the type of shape the island should be (circle, triangle, oval, square, threecircle) |  |
| altitude | altitude | x | String | Selects the altitude profile for the island (crater, mountain, prairie) |  |
| aquifer | aquifer | x | Integer | Specify the number of aquifers  |  |
| lake | lake | x | Integer | Specify the number of lakes  |  |
| river | river | x | Integer | Specify the number of rivers  |  |
| soil | soil | x | String | Selects the soil profile for the island (Dry, Wet) |  |
| biomes | biomes | x | String | Selects the whittaker diagram for the island (america, asia) |  |
| visual | visual | x | String | Selects the visual mode for the island (altitude, biome, debug, lagoon, moisture) |  |
| cities | cities | x | Integer | Number of cities on island |  |
| network | network | x | String | Type of network to connect the cities: Star, NonStar |  |

## Networks
### Star Network
StarNetwork is a way to connect all cities (capitols, villages, hamlets).
* The most central city connects to all other cities via highways.
* In other words, all cities go through the central one.

### NonStar Network
NonStarNetwork is a way to connect the cities via highways, secondary and tertiary roads.
* The central hub connects only to other capitols via highways. (this still follows a star network)
* The villages connect to the nearest capitol via secondary road.
* The hamlets connect to the nearest 2 either hamlets or villages via tertiary roads.

## Developer Information
For PreFeatures, Features, and PostFeatures, the order of the feature fields in the Configuration class matters. 
That is the order in which the Factories will be run within their respective subcategory.

### Cities
To find the shortest path between two cities, euclidean distance was used.

## Whittaker Diagrams 

Here is how we distributed biomes for our Whittaker Diagrams:
![Whittaker Diagram](whittaker.png)