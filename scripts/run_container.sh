#!/bin/sh

CONTAINER=$(basename "$PWD")-springapp

USER=$(whoami)
IMAGE="${USER}/${CONTAINER}"

docker stop "$CONTAINER" 2> /dev/null

docker run \
    --name "$CONTAINER" \
    --rm \
    "$IMAGE" \
