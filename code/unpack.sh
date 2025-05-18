#!/bin/bash

# load archived docker images
echo 'unpacking archived container images...'

echo 'docker load -i reverse-proxy.tar'
docker load -i reverse-proxy.tar

echo 'docker load -i ui.tar'
docker load -i ui.tar

echo 'docker load -i dashboard.tar'
docker load -i dashboard.tar

echo 'docker load -i postgres.tar'
docker load -i postgres.tar

echo 'docker load -i user-station-service.tar'
docker load -i user-station-service.tar