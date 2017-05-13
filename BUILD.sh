#!/bin/bash
mkdir out #
cp -R -v ./resources/* ./out #
javac -encoding utf8 -d "./out" -sourcepath src/ ./src/main/Main.java -classpath "./libs/*" #
echo "Class good"
touch manifest.txt
echo "Manifest-Version: 1.0 " > manifest.txt
echo "Created-By: rq0 " >> manifest.txt
echo "Main-Class: main.Main " >> manifest.txt
echo "Class-Path: out/ libs/commons-cli-1.3.1.jar libs/commons-codec-1.10.jar libs/commons-lang3-3.5.jar libs/flyway-core-4.1.2.jar libs/h2-1.4.194.jar libs/log4j-api-2.8.2.jar libs/log4j-core-2.8.2.jar" >> manifest.txt
#Упаковать файлы в jar-архив
jar cvfm GL_proj.jar manifest.txt ./libs/ -C out/ . #
rm manifest.txt
echo "Jar good"
rm -rf out #
