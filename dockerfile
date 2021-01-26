FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY ./target/demo-0.0.1-SNAPSHOT.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar","-Dspring.datasource.url=jdbc:mysql://db:3306/rhb", "/opt/app/app.jar"]