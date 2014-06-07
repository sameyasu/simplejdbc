@echo on

set BASE_DIR=.
set TOOL_JAR=simplejdbc-tools-mysql-1.0.jar

set LIBS=%BASE_DIR%\libs
set SIMPLEJDBC=simplejdbc-1.1.jar
set COMMONS_LOGGING=commons-logging-1.1.jar
set LOG4J=log4j-1.2.13.jar
set JDBC_DRIVER=mysql-connector-java-5.1.22-bin.jar

set CLASSPATH=%BASE_DIR%;%BASE_DIR%\%TOOL_JAR%;%LIBS%\%SIMPLEJDBC%;%LIBS%\%COMMONS_LOGGING%;%LIBS%\%LOG4J%;%LIBS%\%JDBC_DRIVER%

java Main
