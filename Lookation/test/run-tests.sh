#!/usr/bin/env bash
set -euo pipefail

HERE="$(cd "$(dirname "$0")" && pwd)"
APP="$(cd "$HERE/.." && pwd)"
WEB="$APP/WebContent"
BUILD="$HERE/build"
CP="$WEB/WEB-INF/lib/*:$HERE/lib/*"

rm -rf "$BUILD"
mkdir -p "$BUILD/main" "$BUILD/test"

find "$APP/src" -name '*.java' | sort > "$BUILD/main-sources.txt"
find "$HERE" -name '*Test.java' -not -path "$BUILD/*" | sort > "$BUILD/test-sources.txt"

echo "compiling $(wc -l < "$BUILD/main-sources.txt" | tr -d ' ') main sources"
javac --release 8 -nowarn -encoding UTF-8 -cp "$CP" -d "$BUILD/main" @"$BUILD/main-sources.txt"

echo "compiling $(wc -l < "$BUILD/test-sources.txt" | tr -d ' ') test sources"
javac --release 8 -nowarn -encoding UTF-8 -cp "$BUILD/main:$CP" -d "$BUILD/test" @"$BUILD/test-sources.txt"

TEST_CLASSES="$(cd "$HERE" && find . -name '*Test.java' -not -path './build/*' \
	| sed 's|^\./||; s|/|.|g; s|\.java$||' | sort | tr '\n' ' ')"

echo "running: $TEST_CLASSES"
java -Dnet.bytebuddy.experimental=true -cp "$BUILD/main:$BUILD/test:$CP" \
	org.junit.runner.JUnitCore $TEST_CLASSES
