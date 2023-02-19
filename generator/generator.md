# Generator

This generator creates a mesh (WIP)

## Command Line Args
| shortOption | longOption | Args? | Arg value | Desc | Required |
|:--:|---------------|------|-------|-----|--------|
| o | output | x | .svg file | Specify the output svg file | x |
| m | mesh | x | String | Specifies the type of mesh to output: irregular, regular, square_regular, hex_regular (Note: regular is default) | x |
| p | polygons | x | Integer | Number of polygons to output (Note: only for irregular polygons) | For Irregular meshes |
| r | relaxation | x | Integer | The Lloyd Relaxation constant (Note: only for irregular polygons) | For Irregular meshes |
| X | debug |  | | Turns debug mode on. This makes the polygon segments black, centroid red, and neighbour relations gray lines. | |