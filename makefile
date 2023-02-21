run:
	cd generator && java -jar generator.jar -m square -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

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
	cd generator && java -jar generator.jar -m irregular -r 100 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-irregular-relaxed-debug:
	cd generator && java -jar generator.jar -m irregular -r 100 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

.PHONY: run
