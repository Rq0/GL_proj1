#!/bin/bash
mkdir -p out

javac -encoding utf8 -d ./out/ -sourcepath src/ ./src/Main.java -classpath "./libs/commons-codec-1.10.jar;./libs/commons-lang3-3.5.jar;./libs/commons-cli-1.3.1.jar"

jar cvmf META-INF/MANIFEST.MF untitled.jar out/
