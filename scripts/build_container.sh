#!/bin/sh

USER=$(whoami)

REPO_NAME=$(basename "$PWD")

IMAGE="${USER}/${REPO_NAME}-springapp"

docker build -t "$IMAGE" -f ./docker/Dockerfile .
