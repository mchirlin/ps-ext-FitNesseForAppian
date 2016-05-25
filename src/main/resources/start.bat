@echo off
FOR /F "tokens=1,2 delims==" %%G IN (fitnesse.properties) DO ( set %%G=%%H )

IF "%FitNesseRootDir%" == " " IF "%FitNesseRoot%" == " " (
  @echo on
  java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -f configs/custom.properties
  @echo off
  GOTO END
)

IF "%FitNesseRootDir%" == " " (
  @echo on
  java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -r %FitNesseRoot% -f configs/custom.properties
  @echo off
  GOTO END
)

IF "%FitNesseRoot%" == " " (
  @echo on
  java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -d %FitNesseRootDir% -f configs/custom.properties
  @echo off
  GOTO END
)
@echo on
java -jar lib/fitnesse-20160515-standalone.jar -p 8980 -e 0 -d %FitNesseRootDir% -r %FitNesseRoot% -f configs/custom.properties
@echo off

:END
pause
