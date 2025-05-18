#!/bin/bash

# build compose-stack with new versions of docker images
echo ''
echo 'compiling app..'
echo 'docker-compose build'
echo ''
#docker-compose -p campus_day_app up --build
docker-compose -p campus_day_app build
echo ''



# archive container images
echo ''
echo 'archiving docker images..'

echo 'docker save -o nginx.tar nginx'
docker save -o nginx.tar nginx

echo 'docker save -o ui.tar ui'
docker save -o ui.tar ui

echo 'docker save -o dashboard.tar dashboard'
docker save -o dashboard.tar dashboard

echo 'docker save -o postgres.tar postgres'
docker save -o postgres.tar postgres

echo 'docker save -o user-station-service.tar user-station-service'
docker save -o user-station-service.tar user-station-service
echo ''



# archive named volume 'data'
echo "archiving name volume 'data'.."
docker run --rm \
  -v data:/volume \
  -v $(pwd):/backup \
  alpine \
  tar czf /backup/data_backup.tar.gz -C /volume .
echo ''




# upload new versions of buildfiles and docker images to cloud vm 
echo ''
echo 'uploading files to azure vm..'
echo ''
echo 'scp compose.yaml README.md unpack.sh reverse-proxy.tar ui.tar dashboard.tar postgres.tar postgres.tar user-station-service.tar CampusDayApp@campusdayapp.germanywestcentral.cloudapp.azure:/home/CampusDayApp/'
scp compose.yaml README.md unpack.sh nginx.tar ui.tar dashboard.tar postgres.tar user-station-service.tar CampusDayApp@campusdayapp.germanywestcentral.cloudapp.azure.com:/home/CampusDayApp/
echo ''

