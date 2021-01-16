docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)
git pull
./gradlew build -x test
docker-compose up -d --force-recreate
