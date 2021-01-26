# Goh Joon Sern Interview answer
### Prerequisite
1. jdk 11
2. mvn
3. docker
4. mysql 5.7.3 (if not using docker-compose)
### Start with start.bat

1. start.bat in its directory
2. start.bat will first do a mvn clean install
3. then it will do a docker-compose up, it will first pull and start mysql, then it will build and start demo docker image
4. to shutdown just do docker-compose down

### Start manually
1. start database in localhost:3306 (if you already have mysql 5.7.3 running locally then you can skip this step)\
docker-compose up -f docker-compose-myqsl.yaml
2. clean install app\
mvn clean install
3. start application\
java -jar target/demo-0.0.1-SNAPSHOT.jar
