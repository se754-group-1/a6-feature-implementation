#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

IMAGE_NAME="learning-app:21"
CONTAINER_NAME="learning-app"

mvn -DskipTests package

docker build -t "$IMAGE_NAME" .

if docker ps -a --format '{{.Names}}' | grep -Eq "^${CONTAINER_NAME}$"; then
  docker rm -f "$CONTAINER_NAME"
fi

exec docker run --rm -p 8080:8080 --name "$CONTAINER_NAME" "$IMAGE_NAME"
