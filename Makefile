.PHONY: java
java : 
	javac -cp build/jars/NetworkTables.jar:build/jars/WPILib.jar src/org/stlpriory/**/*.java
	jar -cf test.jar ./**/*.class
	# Do I scp now? I dunno...
	rm ./**/*.class
