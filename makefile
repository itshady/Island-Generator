run:
	cd generator && java -jar generator.jar -m diamond -r 50 -p 100 -o sample.mesh
	cd visualizer && java -jar visualizer.jar -mesh ../generator/sample.mesh -output sample.svg

.PHONY: run
