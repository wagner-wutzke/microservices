# Microservices Demo

Demo microservice application based on [Microservices Blog](https://spring.io/blog/2015/07/14/microservices-with-spring) on the spring.io website.

This is an example of a simple microservices distributed system, which consists of a service, a client and a discovery service (Netflix Eureka), each deployed as a Docker container in one single Host.

### Components
- discovery: registration, discovery service. 
- service: producer service. It provides a REST endpointm which is being called by the consumer service.
- client: consumer service. It also provides a REST endpoint for testing purpose.

Doscovery endpoint: 
```
http://[docker_host_ip]:8761/
```

Service endpoint:
```
http://[docker_host_ip]:8880/service
```

Client endpoint:
```
http://[docker_host_ip]:8890/client
```
Calling this URL makes this service to make a discovery call to the _discovery_ service, find out which is the ip of the _service_ service and then, call its endpoint, returning its response. Since there might be more than one service registered with the same id / name, the registry works also as a Load Balancer.


## Building the containers with the Docker Maven Plugin
The Docker Maven Plugin makes ones life easier, when changes in the service implementation are made constantly and re-build the docker image has to be done several times.
In order to build the Docker image, the Docker Machine must be running and connected to the Docker Host.

The Docker Maven Plugin needs a Dockerfile file within each microservice project.
This is one plugin configuration I used:
```xml
            <plugin>
	            <groupId>com.spotify</groupId>
	            <artifactId>docker-maven-plugin</artifactId>
	            <version>0.4.3</version>
	            <configuration>
	                <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
	                <dockerDirectory>src/main/docker</dockerDirectory>
	                <resources>
	                    <resource>
	                        <targetPath>/</targetPath>
	                        <directory>${project.build.directory}</directory>
	                        <include>${project.build.finalName}.jar</include>
	                    </resource>
	                </resources>
	            </configuration>
	        </plugin>
```

I work on an Windows 10 machine with VirtualBox. Due to this configuration, I faced a problem where some required system variables have not been properly set, so I had to set them manually. Those are the shell commands I used on my command line:
```sh
set DOCKER_HOST=tcp://192.168.99.100:2376
set DOCKER_MACHINE_NAME=default
set DOCKER_TLS_VERIFY=1
set DOCKER_TOOLBOX_INSTALL_PATH=C:\Program Files\Docker Toolbox
set DOCKER_CERT_PATH=C:\Users\Admin\.docker\machine\machines\default
```
After setting these system variables, I have been able to build the project jar file and build a new Docker image (within the Docker Host) with the following maven comand from command line (in each project's directory):
```sh
mvn clean package docker:build
```

## Running the docker containers
There are two possibilities to start the containers. First one is to use a Docker Compose file for starting all three containers together and linking them properly. The second one is to start every single one manualy with a docker command.

For the second approach, here are the Docker commands:
```sh
# Discovery container
docker run -d -p 8761:8761 -h discovery --name discovery apexitsystems/discovery

# Service container - it is being replicated
docker run -d -p 8880:8880 --link discovery:discovery --name service-1 apexitsystems/service
docker run -d -p 8881:8880 --link discovery:discovery --name service-2 apexitsystems/service

# Client container
docker run -d -p 8890:8890 --link discovery:discovery service:service --name client apexitsystems/client-1
```

Note that the discovery container is registering its hostname "-h discovery", so the other services can find it.

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