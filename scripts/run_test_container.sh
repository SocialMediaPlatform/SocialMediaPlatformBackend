#!/bin/sh

CONTAINER=$(basename "$PWD")-test

USER=$(whoami)
IMAGE="${USER}/${CONTAINER}"

docker stop "$CONTAINER" 2> /dev/null

docker run \
    --name "$CONTAINER" \
    --rm \
    "$IMAGE" \
