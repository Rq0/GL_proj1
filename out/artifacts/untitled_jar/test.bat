@echo off
java -jar untitled.jar 
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar  -h
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar  -login XXX -pass XXX
echo %ERRORLEVEL%
echo (1)

java -jar untitled.jar  -login jdoe -pass XXX
echo %ERRORLEVEL%
echo (2)

java -jar untitled.jar  -login jdoe -pass sup3rpaZZ
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar  -login jdoe -pass sup3rpaZZ -role READ -res a
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar  -login jdoe -pass sup3rpaZZ -role READ -res a.b
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar  -login jdoe -pass sup3rpaZZ -role XXX -res a.b
echo %ERRORLEVEL%
echo (3)

java -jar untitled.jar  -login jdoe -pass sup3rpaZZ -role READ -res XXX
echo %ERRORLEVEL%
echo (4)

java -jar untitled.jar -login jdoe -pass sup3rpaZZ -role WRITE -res a
echo %ERRORLEVEL%
echo (4)

java -jar untitled.jar -login jdoe -pass sup3rpaZZ -role WRITE -res a.bc
echo %ERRORLEVEL%
echo (4)

java -jar untitled.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100
echo %ERRORLEVEL%
echo (0)

java -jar untitled.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100
echo %ERRORLEVEL%
echo (5)

java -jar untitled.jar -login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX
echo %ERRORLEVEL%
echo (5)

java -jar untitled.jar -login X -pass X -role READ -res X -ds 2015-01-01 -de 2015-12-31 -vol XXX
echo %ERRORLEVEL%
echo (1)

java -jar untitled.jar -login X -pass X -role READ -res X
echo %ERRORLEVEL%
echo (1)

Pause