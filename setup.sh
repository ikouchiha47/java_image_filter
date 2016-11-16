#!/bin/bash
echo "Creating classes directory"

if [ ! -d "classes" ]; then
  rm -R classes/
  mkdir classes
fi

echo "Compiling classes"

javac -d classes -classpath classes filter/*java effects/*.java

echo "Creating jar"
cd classes
jar cfm ../flimter.jar ../Manifest.txt org/**/*.class

echo "Created flimter.jar"
echo "Run with: java -jar flimter.jar"
