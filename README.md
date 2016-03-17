# Microservices Demo

Demo application based on [Microservices Blog](https://spring.io/blog/2015/07/14/microservices-with-spring) on the spring.io website.

This source code is an implementation example of a simple microservices distributed system. It consists of a service, a client and a discovery service (Netflix Eureka), each deployed as a Docker container in one single Host.


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
After setting these system variables, I have been able to build the project jar file and build a new Docker image (within the Docker Host) with the following maven comand from command line (in the project's directory):
```sh
mvn clean package docker:build
```

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