# Microservice Demo

Demo application to go with my [Microservices Blog](https://spring.io/admin/blog/2181-microservices-with-spring) on the spring.io website.



## Helpful Docker commands
- Run docker bash
```sh
docker run <container id> /bin/bash
```
- Clean up exited containers
```sh
docker ps -a | grep 'Exited' | awk '{print $1}' | xargs --no-run-if-empty docker rm
```
- Clean up hanging Volumes
```sh
docker volume ls -qf dangling=true | xargs -r docker volume rm
```
- Kill all running containers
```sh
docker kill $(docker ps -q)
```
- Delete all stopped containers (including data-only containers)
```sh
docker rm $(docker ps -a -q)
```
- Delete all 'untagged/dangling' (<none>) images
```sh
docker rmi $(docker images -q -f dangling=true)
```