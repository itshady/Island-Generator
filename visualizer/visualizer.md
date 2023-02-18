# Visualizer

This visualizer takes a .mesh file created via the io Structs

## Inputs

### Vertices
* double getX(): method that returns the x coordinate
* double getY(): method that returns the y coordinate
* List<Structs.Property> getPropertiesList(): method that returns a list of properties where the key and value are both strings in a dictionary. Ex. { key="myKey", value="myValue" }

### Segments
* int getV1Idx(): method that returns the index of the segments first vertex
* int getV2Idx(): method that returns the index of the segments second vertex
* List<Structs.Property> getPropertiesList(): method that returns a list of properties where the key and value are both strings in a dictionary. Ex. { key="myKey", value="myValue" }

### Polygons
* List<Integer> getSegmentIdxsList: method that returns a list of all indices of its segments _in order_
* Integer getCentroidIdx: method that returns the index of its centroid
* List<Integer> getNeighborIdxsList: method that returns a list of all the indices of its neighbour polygons
* List<Structs.Property> getPropertiesList(): method that returns a list of properties where the key and value are both strings in a dictionary. Ex. { key="myKey", value="myValue" }

### Properties
| Vertex? | Segment? | Polygon? | Property Key | Property Value | Desc | Required? |
|:--:|---------------|------|-------|-----|--------|----|
| x | x | x | thickness | *float | Specifies the radius of the vertex, or thickness of a unique segment or segments around a polygon |  |
| x | x | x | rgba_color | r(int),g(int),b(int),a(int) | Specifies the colour of the geometry or of a polygons segments | |
| x |  |  | is_centroid | *boolean | Specifies if a vertex is a centroid or not | |

## Command Line Args
| shortOption | longOption | Args? | Arg value | Desc | Required |
|:--:|---------------|------|-------|-----|--------|
| o | output | x | .svg file | Specify the output svg file | x |
| m | mesh | x | .mesh file | Specify the mesh file where the data in [Inputs](#Inputs) exists | x |
| X | debug |  | | Turns debug mode on. This makes the polygon segments black, centroid red, and neighbour relations gray lines. | |