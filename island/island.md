# Island Generator

This is an island generator that works on already created meshes.

## Command Line Args
| shortOption | longOption | Args? | Arg value | Desc | Required |
|:--:|---------------|------|-------|-----|--------|
| o | output | x | .mesh file | Specify the output mesh file | x |
| m | mesh | x | String | Specifies the type of mesh to output: irregular, square, hex, honeycomb, diamond | x |
| p | polygons | x | Integer | Number of polygons to output (Note: only for irregular meshes) |  |
| r | relaxation | x | Integer | The Lloyd Relaxation constant (Note: only for irregular meshes) |  |
| ht | height | x | Integer | Specifies the height of the svg (Note: only for irregular meshes) |  |
| wt | width | x | Integer | Specifies the width of the svg (Note: only for irregular meshes) |  |