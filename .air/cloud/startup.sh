#!/usr/bin/env bash
# Environment startup for password-generator (Gradle 8.0 wrapper => needs JDK 17, not the default JDK 25).
set -euo pipefail
cd "$(dirname "$0")/../.."

JDK_DIR="$HOME/.jdks/temurin-17"
if [ ! -x "$JDK_DIR/bin/java" ]; then
  tmp="$(mktemp -d)"
  curl -fsSL "https://api.adoptium.net/v3/binary/latest/17/ga/linux/x64/jdk/hotspot/normal/eclipse" -o "$tmp/jdk.tar.gz"
  mkdir -p "$JDK_DIR"
  tar -xzf "$tmp/jdk.tar.gz" -C "$JDK_DIR" --strip-components=1
  rm -rf "$tmp"
fi
export JAVA_HOME="$JDK_DIR"
export PATH="$JAVA_HOME/bin:$PATH"

# Java tooling (Gradle wrapper/dependency resolution) ignores HTTPS_PROXY; pass it explicitly.
if [ -n "${HTTPS_PROXY:-${https_proxy:-}}" ]; then
  px="${HTTPS_PROXY:-$https_proxy}"; px="${px#*://}"; px="${px%/}"
  export JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS:-} -Dhttp.proxyHost=${px%:*} -Dhttp.proxyPort=${px##*:} -Dhttps.proxyHost=${px%:*} -Dhttps.proxyPort=${px##*:} -Dhttp.nonProxyHosts=localhost|127.0.0.1"
fi

# Prime Gradle distribution, dependencies and build outputs.
bash ./gradlew --no-daemon build

healthcheck() {
  # Real tasks compile and run unit tests: assert that works.
  bash ./gradlew --no-daemon test --rerun-tasks
}

healthcheck
