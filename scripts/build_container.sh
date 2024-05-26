#!/bin/sh

# Get the current user name
USER=$(whoami)

# Get the current directory name
REPO_NAME=$(basename "$PWD")

# Create the image name
IMAGE="${USER}/${REPO_NAME}-springapp"

# Build the Docker image
docker build -t "$IMAGE" -f ./docker/Dockerfile .
