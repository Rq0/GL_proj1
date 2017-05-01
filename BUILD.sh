#!/bin/bash
mkdir bin #
javac -d ./bin/ -sourcepath src/ ./src/Main.java -classpath "./libs/commons-cli-1.3.1.jar;./libs/commons-codec-1.10.jar;./libs/commons-lang3-3.5.jar;./libs/flyway-core-4.1.2.jar;./libs/h2-1.4.194.jar;./libs/log4j-core-2.8.2.jar;./libs/log4j-api-2.8.2.jar" #
cp -R -v ./resources/* ./bin/ #
echo "Class good"
touch manifest.txt
echo "Manifest-Version: 1.0 " > manifest.txt
echo "Created-By: rq0 " >> manifest.txt
echo "Main-Class: Main " >> manifest.txt
echo "Class-Path: bin/ libs/ libs/commons-cli-1.3.1.jar libs/commons-codec-1.10.jar libs/commons-lang3-3.5.jar libs/flyway-core-4.1.2.jar libs/h2-1.4.194.jar libs/log4j-api-2.8.2.jar libs/log4j-core-2.8.2.jar" >> manifest.txt
#”паковать файлы в jar-архив
jar cvmf manifest.txt GL_proj.jar bin/ libs/ #
rm manifest.txt
echo "Jar good"
rm -rf bin #
