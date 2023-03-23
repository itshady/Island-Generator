run:
	cd generator && java -jar generator.jar -m square -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

island:
	cd island && java -jar island.jar -i ../generator/sample.mesh -o lagoon.mesh -shape square -altitude mountain -aquifer 2 -lake 5 -river 4 -soil dry -biomes america
	cd visualizer && java -jar visualizer.jar -mesh ../island/lagoon.mesh -output sample.svg

help:
	cd generator && java -jar generator.jar -h
	cd visualizer && java -jar visualizer.jar -h

run-square-debug:
	cd generator && java -jar generator.jar -m square -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-hex:
	cd generator && java -jar generator.jar -m hex -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-hex-debug:
	cd generator && java -jar generator.jar -m hex -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-diamond:
	cd generator && java -jar generator.jar -m diamond -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-diamond-debug:
	cd generator && java -jar generator.jar -m diamond -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-irregular:
	cd generator && java -jar generator.jar -m irregular -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-irregular-debug:
	cd generator && java -jar generator.jar -m irregular -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-irregular-relaxed:
	cd generator && java -jar generator.jar -m irregular -r 100 -p 1000 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-irregular-relaxed-debug:
	cd generator && java -jar generator.jar -m irregular -r 100 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-irregular-size:
	cd generator && java -jar generator.jar -m irregular -r 100 -p 100 -ht 100 -wt 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-square-size:
	cd generator && java -jar generator.jar -m square -r 100 -p 100 -ht 100 -wt 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-honeycomb:
	cd generator && java -jar generator.jar -m honeycomb -r 100 -p 100 -ht 100 -wt 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-honeycomb-debug:
	cd generator && java -jar generator.jar -m honeycomb -r 100 -p 100 -ht 100 -wt 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

.PHONY: run island
