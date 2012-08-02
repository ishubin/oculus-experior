
set -e

mkdir -p dist
rm -rf dist/*
mkdir -p dist/bin
mkdir -p dist/sources

versionInPom=`cat pom.xml | grep "<version>.*</version>" | head -n 1 | sed 's/[<version> | </version>]//g'`
version=`cat pom.xml | grep "<version>.*</version>" | head -n 1 | sed 's/[<version> | </version> | (SNAPSHOT) | -]//g'`
echo Version is $version

mvn clean
mvn clean install -DskipTests=true
mvn assembly:assembly -DskipTests=true
cp target/oculus-experior-$versionInPom.jar dist/bin/oculus-experior-$version.jar 
cp target/oculus-experior-jar-with-dependencies.jar dist/bin/oculus-experior-$version-all-dep.jar 
cp experior.properties dist/bin/.
cp experior.test-loaders.xml dist/bin/.
cp LICENSE-2.0.txt dist/bin/.
cp README dist/bin/.
cp LICENSE-2.0.txt dist/sources/.
cp README dist/sources/.


cd dist/bin
zip -r -9 ../oculus-experior-$version-bin.zip *

cd ../..
cp -r src/ dist/sources/src 
cp experior.properties dist/sources/.
cp experior.test-loaders.xml dist/sources/.
cp pom.xml dist/sources/.

cd dist/sources
zip -r -9 ../oculus-experior-$version-sources.zip *

cd ..
rm -rf bin
rm -rf sources
