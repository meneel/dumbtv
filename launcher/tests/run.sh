#!/usr/bin/env bash
# Host-side Java checks: compile the launcher and the tests against the android-25 stubs, then run
# every tests/*Test.java main(). No device and no test framework.
set -euo pipefail
cd "$(dirname "$0")/.."
JAVA_HOME=${JAVA_HOME:-/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home}
JAR=${ANDROID_JAR:-/opt/homebrew/share/android-commandlinetools/platforms/android-25/android.jar}
OUT=$(mktemp -d)
"$JAVA_HOME/bin/javac" --release 8 -nowarn -d "$OUT" -cp "$JAR" src/tv/dumbtv/home/*.java tests/*.java 2> >(grep -v '^Note:' >&2)
for f in tests/*Test.java; do
  "$JAVA_HOME/bin/java" -cp "$OUT:$JAR" "tv.dumbtv.home.$(basename "$f" .java)"
done
