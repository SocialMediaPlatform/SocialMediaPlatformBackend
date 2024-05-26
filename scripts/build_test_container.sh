#!/bin/sh

USER=$(whoami)

REPO_NAME=$(basename "$PWD")

IMAGE="${USER}/${REPO_NAME}-test"

docker build -t "$IMAGE" -f ./docker/test.Dockerfile .
