# Generator

This Generator can generate different types of meshes.

## Command Line Args
| shortOption | longOption | Args? | Arg value | Desc | Required |
|:--:|---------------|------|-------|-----|--------|
| o | output | x | .mesh file | Specify the output mesh file | x |
| m | mesh | x | String | Specifies the type of mesh to output: irregular, square, hex, honeycomb, diamond | x |
| p | polygons | x | Integer | Number of polygons to output (Note: only for irregular meshes) |  |
| r | relaxation | x | Integer | The Lloyd Relaxation constant (Note: only for irregular meshes) |  |
| ht | height | x | Integer | Specifies the height of the svg (Note: only for irregular meshes) |  |
| wt | width | x | Integer | Specifies the width of the svg (Note: only for irregular meshes) |  |

### Properties
| Vertex? | Segment? | Polygon? | Property Key | Property Value | Desc | Required? |
|:--:|---------------|------|-------|-----|--------|----|
| x | x | x | thickness | *float | Specifies the radius of the vertex, or thickness of a unique segment or segments around a polygon |  |
| x | x | x | rgba_color | r(int),g(int),b(int),a(int) | Specifies the colour of the geometry or of a polygons segments | |
| x |  |  | is_centroid | *boolean | Specifies if a vertex is a centroid or not | |