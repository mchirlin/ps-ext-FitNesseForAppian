#!/bin/sh
source fitnesse.properties

if [[ -n "${FitNesseRootDir/[ ]*\n/}" ]] && [[ -n "${FitNesseRoot/[ ]*\n/}" ]]
then
  echo 1
  java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -d "/Users/jorge.sanchez/Documents/github/ps-app-AppianCMF" -r "$FitNesseRoot" -f configs/custom.properties
else
  if [[ -n "${FitNesseRootDir/[ ]*\n/}" ]]
  then
    echo 2
    echo $FitNesseRootDir
    java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -d "$FitNesseRootDir" -f configs/custom.properties
  else
    if [[ -n "${FitNesseRoot/[ ]*\n/}" ]]
    then
      echo 3
      java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -r "$FitNesseRoot" -f configs/custom.properties
    else
      echo 4
      java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -f configs/custom.properties
    fi
  fi
fi
