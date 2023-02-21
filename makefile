run:
	cd generator && java -jar generator.jar -m diamond -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-square:
	cd generator && java -jar generator.jar -m square -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

run-hex:
	cd generator && java -jar generator.jar -m hex -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

run-irregular:
	cd generator && java -jar generator.jar -m irregular -r 100 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

.PHONY: run
