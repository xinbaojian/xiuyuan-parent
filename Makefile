clean:
	mvn clean -e -U -DskipTests
compile:
	mvn compile -e -U -DskipTests
package:
	mvn package -e -U -DskipTests

install:
	mvn install -e -U -DskipTests

.PHONY: clean compile package install