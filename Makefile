clean:
	mvn clean -e -U -DskipTests
compile:
	mvn compile -e -U -DskipTests
package: clean
	mvn package -e -U -DskipTests

install: clean
	mvn install -e -U -DskipTests

build: install
	cd xiuyuan-admin && mvn docker:build docker:push


.PHONY: clean compile package install