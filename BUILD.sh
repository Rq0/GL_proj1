#!/bin/bash
mkdir bin #
javac -cp "./libs/*" -d "./bin" src/*.java #
echo "Class good"
touch manifest.txt 
echo "Manifest-Version: 1.0 " > manifest.txt 
echo "Created-By: Rq0 " >> manifest.txt 
echo "Main-Class: Main " >> manifest.txt 
echo "Class-Path: libs/commons-cli-1.3.1.jar libs/commons-codec-1.10.jar libs/commons-lang3-3.5.jar " >> manifest.txt  
jar cvmf manifest.txt untitled.jar -C ./bin / ./libs/* #
rm manifest.txt 
echo "Jar good"
rm -rf bin #