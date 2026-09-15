#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/../.."

log() {
  printf '[startup] %s\n' "$*"
}

healthcheck() {
  log "running Gradle test healthcheck"
  ./gradlew test --no-daemon
  log "healthcheck passed"
}

log "preparing Gradle wrapper"
chmod +x ./gradlew

log "warming Gradle dependencies and build cache"
./gradlew test --no-daemon

if [ "${AIR_STARTUP_MODE:-}" = warmup ]; then
  healthcheck
fi

log "startup complete"
