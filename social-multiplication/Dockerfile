# Start with a base image containing Java runtime (mine java 17)
FROM openjdk:17-jdk-slim-buster

# Add a volume pointing to /tmp
VOLUME /tmp
# Make port 8080 available to the world outside this container
EXPOSE 8080
# The application's jar file (when packaged)
ARG JAR_FILE=target/social-multiplication-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} social-multiplication.jar
EXPOSE 8080
# Run the jar file 
ENTRYPOINT ["java","-jar","/social-multiplication.jar"]