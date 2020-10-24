docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
git pull
./gradlew build
docker-compose up -d --force-recreate
