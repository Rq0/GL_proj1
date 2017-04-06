#!/bin/bash
# —оздать папку: out
mkdir -p out
# ’ранить файлы в отдельной папке: out, не мешать с исходниками
javac -encoding utf8 -d ./out/ -sourcepath src/ ./src/Main.java -classpath "./libs/commons-codec-1.10.jar;./libs/commons-lang3-3.5.jar;./libs/commons-cli-1.3.1.jar"
#”паковать файлы в jar-архив
jar cvmf MANIFEST.MF untitled.jar out/