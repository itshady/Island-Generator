run:
	cd generator && java -jar generator.jar sample.mesh
	cd visualizer && java -jar visualizer.jar ../generator/sample.mesh sample.svg

.PHONY: run
