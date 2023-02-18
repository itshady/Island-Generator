run:
	cd generator && java -jar generator.jar -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

.PHONY: run
