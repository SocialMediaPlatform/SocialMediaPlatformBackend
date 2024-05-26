#!/bin/sh

# Get the current directory name (repo name)
CONTAINER=$(basename "$PWD")-springapp

# Get the current user name and create the image name
USER=$(whoami)
IMAGE="${USER}/${CONTAINER}"

# Ensure container is not running
docker stop "$CONTAINER" 2> /dev/null

# Run docker container
docker run \
    --name "$CONTAINER" \
    --rm \
    "$IMAGE" \
