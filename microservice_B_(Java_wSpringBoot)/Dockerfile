FROM adoptopenjdk/openjdk8:ubi
RUN mkdir /opt/app
COPY microservice_b.jar /opt/app
CMD ["java", "-jar", "/opt/app/microservice_b.jar"]