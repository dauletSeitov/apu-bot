FROM openjdk

# Create an app directory in the container
WORKDIR /app

# Copy the compiled Java application (JAR file) into the app directory
COPY build/libs/pipisya-bot-1.0-SNAPSHOT.jar /app/app.jar

VOLUME /app/data

# Set the entrypoint or CMD for running your Java application
CMD ["java", "-jar", "/app/app.jar"]