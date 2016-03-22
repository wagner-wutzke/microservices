FROM iron/java:1.8
VOLUME /tmp
ADD service-0.0.1.jar /app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 8880
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]