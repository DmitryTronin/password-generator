#!/usr/bin/env bash
set -euo pipefail

cd "$(dirname "$0")/../.."

if [ -n "${HTTPS_PROXY:-${HTTP_PROXY:-}}" ]; then
  proxy_url="${HTTPS_PROXY:-${HTTP_PROXY:-}}"
  proxy_host_port="${proxy_url#*://}"
  proxy_host="${proxy_host_port%:*}"
  proxy_port="${proxy_host_port##*:}"
  export GRADLE_OPTS="${GRADLE_OPTS:-} -Dhttps.proxyHost=${proxy_host} -Dhttps.proxyPort=${proxy_port} -Dhttp.proxyHost=${proxy_host} -Dhttp.proxyPort=${proxy_port}"
fi

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
