# Assignment A4: Urbanism and Graphs

  - Hady Ibrahim #1 [ibrahh14@mcmaster.ca]

### Installation instructions

First follow A2 and A3 installation instructions below.

### Make Scenarios
If you have make, run the following command in order:

* To create the base mesh:
  * `make run-irregular-relaxed`

* To generate the island on top of the mesh
  * `make island`

### Other Make Scenarios
* Star Network
  * `make island-urbanism-star`

* NonStar Network
  * `make island-urbanism-nonstar`

This should generate a default implementation of the urbanism with the island generation.

### Without Make
Run the following commands in order:
* To create the base mesh in a sample.mesh file
  * `cd generator && java -jar generator.jar -m irregular -r 100 -p 1000 -o sample.mesh`
* To generate the island with urbanism in a lagoon.mesh file
  * `cd island && java -jar island.jar -i ../generator/sample.mesh -o lagoon.mesh -shape circLe -cities 40 -network nonstar -altitude mountain -aquifer 2 -lake 7 -river 5 -soil dry -biomes america`
* To visualize the final mesh in sample.svg
  * `cd visualizer && java -jar visualizer.jar -mesh ../island/lagoon.mesh -output sample.svg`

Check out [Island Documentation](island/island.md) to see all the configurations for Island generation via CLI.

Check out [Pathfinder Documentation](pathfinder/pathfinder.md) to see the GraphADT and Pathfinder functionality.

# Assignment A2 + A3: Mesh Generator (A2) + Island Generator (A3)

  - Hady Ibrahim #1 [ibrahh14@mcmaster.ca]
  - Cyruss Amante #2 [amantec@mcmaster.ca]
  - Richard Li #3 [li1502@mcmaster.ca]

## How to run the product

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run (while in A2):
```
mvn clean install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one. 

# A2 - Mesh Generation

## Running The Product

### Without Make

To generate the square mesh, enter this into the terminal. 

```
cd generator
java -jar generator.jar -m square -o sample.mesh
```

Then go to the visualizer directory.
```
cd visualizer
java -jar visualizer.jar -m ../generator/sample.mesh -o sample.svg
```
To activate debug mode, add `-X` to the end of the command line during the visualizer execution.
```
cd visualizer
java -jar visualizer.jar -m ../generator/sample.mesh -o sample.svg -X
```

To vizualize the SVG file:

  - Open it with a web browser
  - If need be: Convert it into something else with tool slike `rsvg-convert`

### With Make (Has scenarios)

A makefile has been provided to easily create meshes according to their default types (however, this can be adjusted accordingly). 

* To create a regular square mesh: 
  * `make run`

* For debug mode with a regular square mesh: 
  * `make run-square-debug`

The following commands can add -debug to the end of the command-name to activate debug mode (i.e `make run-hex-debug` in the case of a hex mesh).

* To create an irregular unrelaxed mesh: 
  * `make run-irregular`

* To create an irregular relaxed mesh: 
  * `make run-irregular-relaxed`

* To create a regular hexagon mesh: 
  * `make run-hex`

* To create a regular diamond mesh: 
  * `make run-diamond`

* To create a regular honeycomb mesh:
  * `make run-honeycomb`
  
For the square and irregular mesh in normal mode, width and height can be adjusted to 100x100 by adding -size to the end of the command-name (i.e `make run-square-size` or `make-run-irregular-size`).


### Generator Customization

If you wish to customize the generation of a mesh, such as the relaxation level and number of polygons, simply add an argument to the generator portion of execution.

```
cd generator
java -jar generator.jar -m *TYPE_OF_MESH* -r *RELAXATION_LEVEL* -p *NUM_OF_POLYGONS* -o *OUTPUT_NAME*.mesh -ht *HEIGHT_INT* -wt *WIDTH_INT*
```

These can be written in any order and if you choose to omit any arguments, default values will be provided. Please note for regular meshes, relaxation level and number of polygons will be ignored.

Need help? Simply use `-h` or `--help` within the generator execution or run `make help`.

## Visualizing the project

If running the project in normal mode, vertices will be randomly choose between two colours. The segment colours will be the average colour between its two vertices and the polygon colour will be filled in as the average colour of all its vertices.

For TA: Go to JTSToGeneratorConverter [lines 97-105](https://github.com/2AA4-W23/a2---mesh-generator-team-1/blob/4009122343966ac63634c0c6535294f71b35b18b/generator/src/main/java/ca/mcmaster/cas/se2aa4/a2/generator/Helpers/JTSToGeneratorConverter.java#L97) to be able to test with colours_ 

# A3 - Island Generation

## Running The Product

### Without Make

To generate an island, enter this into the terminal. This assumes you have generated a mesh from 

```
cd island
java -jar island.jar -i ../generator/sample.mesh -o lagoon.mesh -shape circle -altitude mountain -aquifer 2 -lake 3 -river 4 -soil dry -biomes america
```

Then go to the visualizer directory.
```
cd visualizer
java -jar visualizer.jar -m ../generator/sample.mesh -o sample.svg
```

To vizualize the SVG file:

- Open it with a web browser
- If need be: Convert it into something else with tool slike `rsvg-convert`

### With Make (Has scenarios)

A makefile has been provided to easily create islands according to their default types (however, this can be adjusted accordingly).

* To begin, make a starting irregular relaxed mesh with this command:
  * `make run-irregular-relaxed`

* To make an island, simply run the following command adjusting values to your liking in the makefile:
  * `make island`
 
You can make your own combination island with the command-line arguments in [Island.md](./island/island.md), however different scenarios have been added to the makefile for easy execution:

* To make a lagoon island (sandbox):
  * `make island-lagoon`
 
* To make an island with an already specified seed (reproducability):
  * `make island-seed`
  * Different heatmap modes have been added to view this specific seed:
    * `make island-seed-debug` for debug mode
    * `make island-seed-moisture` for a moisture heatmap
    * `make island-seed-altitude` for an altitude heatmap

* To make a square mountain island:
  * `make island-mountain-square`    

* To make a three circle prairie island:
  * `make island-prairie-threecircle`   

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Developer information

Check [Island.md](./island/island.md), [Generator.md](./generator/generator.md) and [Visualizer.md](./visualizer/visualizer.md) for more in depth information, such as geometry properties, command line args, and whittaker diagram information.

## Backlog

### Status:
* Pending (P), Started (S), Blocked (B), Done (D)

### Definition of Done

* Feature is permanently completed with consideration of edge cases. 
* Code is either readable, commented, or documented. 
* Code passes all tests.

### Product Backlog

_**NOTE: For any features with more than 1 person, means it was most likely one person worked on it and others came in later to finish or fix it. Or some rare cases of pair programming during difficult features.**_ 

| Id | Feature title | Who? | Start | End | Status |
|:--:|---------------|------|-------|-----|--------|
| F01 | Square visualization | Richard, Hady, Cyruss | 01/30/23 | 02/03/23 | D |
| F02 | Add support for representing squares as polygons | Richard | 02/04/23 | 02/08/23 | D |
| F03 | Determine the centroid location for each polygon | Richard, Hady, Cyruss | 02/04/23 | 02/10/23 | D |
| F04 | RGBA support (alpha value for transparency affect) | Cyruss | 02/04/23 | 02/09/23 | D |
| F05 | Manipulate the thickness of vertices, polygons, segments | Cyruss | 02/04/23 | 02/09/23 | D |
| F06 | Allow visualizer to take in Command line args, to display colours indicated by the user | Hady | 02/04/23 | 02/11/2023 | D |
| F07 | Add support for a debug mode | Hady & Cyruss | 02/04/23 | 02/11/2023 | D |
| F08 | Generate irregular voronoi tiles for random points | Hady, Cyruss, Richard | 02/14/23 | 02/16/23 | D |
| F09 | Incorporate Lloyd relaxation for the voronoi tiles | Hady | 02/14/23 | 02/18/23 | D |
| F10 | Display neighborhood relationships (Delaunay's Triangulation) | Cyruss | 02/14/23 | 02/18/23 | D |
| F11 | Reorder segments (Convex Hull) | Cyruss | 02/14/23 | 02/18/23 | D |
| F12 | Add Hexagonal Tessellation | Hady |  02/18/23 | 02/18/23 | D |
| F13 | Add Diamond Tessellation | Cyruss |  02/20/23 | 02/21/23 | D |
| F14 | Minimize Mesh (unique vertices) | Hady |  02/19/23 | 02/21/23 | D |
| F15 | Allow generator to take in command line args to select the type of mesh, relaxation, and num of polygons | Hady, Richard | 02/18/23 | 02/21/23 | D |
| F16 | Minimize Mesh (unique segments) | Hady |  02/19/23 | 02/26/23 | D |
| F17 | Minimize Mesh (unique polygons) | Hady |  02/19/23 | 02/26/23 | D |
| F18 | Add Honeycomb Tessellation | Hady |  02/26/23 | 02/27/23 | D |
| F19 | Support for Lagoon and Oval Shaped Island | Cyruss |  03/11/23 | 03/13/23 | D |
| F20 | Support for Triangle Shaped Island | Richard |  03/12/23 | 03/13/23 | D |
| F21 | User can choose which island shape | Hady, Richard, Cyruss |  03/12/23 | 03/14/23 | D |
| F22 | Support for ThreeCircle (Complex) Shaped Island | Hady | 03/13/23 | 03/13/23 | D |
| F23 | Created Altimetric profile for Mountain | Hady |  03/14/23 | 03/14/23 | D |
| F24 | Created Altimetric profile for Prairie | Hady |  03/14/23 | 03/14/23 | D |
| F25 | User can choose which Altimetric profile | Hady, Cyruss, Richard |  03/14/23 | 03/14/23 | D |
| F26 | Add randomly spawning lakes of random size in a range within the island | Hady, Cyruss, Richard | 03/15/23 | 03/18/23 | D |
| F27 | Randomly spawn rivers that flow from high to low elevation, ending in a lake/ocean or lowest point (creating a lake) | Richard, Hady | 03/18/23 | 03/18/23 | D |
| F28 | User can choose the max number of lakes | Hady | 03/15/23 | 03/18/23 | D |
| F29 | Rivers can merge and multiply | Richard, Hady | 03/18/23 | 03/18/23 | D |
| F30 | User can choose the number of rivers to spawn | Hady | 03/18/23 | 03/18/23 | D |
| F31 | Aquifers randomly generated based on user input | Hady, Richard, Cyruss | 03/15/23 | 03/18/23 | D |
| F32 | Add soil types that get moisture based on their type and the humidity | Cyruss | 03/18/23 | 03/18/23 | D |
| F33 | Add Dry Soil Type | Cyruss, Hady | 03/18/23 | 03/20/23 | D |
| F34 | Add Wet Soil Type | Cyruss, Hady | 03/18/23 | 03/20/23 | D |
| F35 | User can choose the absorption profile | Cyruss | 03/18/23 | 03/18/23 | D |
| F36 | Add a American Whittaker Diagram | Richard, Cyruss | 03/21/23 | 03/22/23 | D |
| F37 | Add a second Asian Whittaker Diagram | Richard, Cyruss | 03/21/23 | 03/22/23 | D |
| F38 | User can choose which whittaker diagram to follow | Richard, Cyruss | 03/21/23  | 03/22/23 | D |
| F39 | Regions are split into biomes based on the environment and whittaker diagrams | Richard, Cyruss |  03/21/23 | 03/22/23 | D |
| F40 | Randomly generate islands based off of a seed  | Hady | 03/15/23 | 03/18/23 | D |
| F41 | Return the seed to the user and allow them to pass in a seed (this should produce the same map every time) | Hady | 03/15/23 | 03/18/23 | D |
| F42 | Add Altitude Heatmap Visualization | Hady | 03/23/23 | 03/24/23 | D |
| F43 | Add Moisture Heatmap Visualization | Hady | 03/23/23 | 03/24/23 | D |
| F44 | Add Debug Visualization (show spring sources and aquifers) | Hady | 03/23/23 | 03/24/23 | D |
| F45 | User can choose visualization type | Hady | 03/23/23 | 03/24/23 | D |


