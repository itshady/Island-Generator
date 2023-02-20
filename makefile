run:
	cd generator && java -jar generator.jar -m irregular -r 50 -p 35 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg -X

.PHONY: run
