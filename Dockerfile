FROM openjdk:11
ADD target/demo-with-tests.jar /app/demo-with-tests.jar
ENTRYPOINT ["java","-jar","/app/demo-with-tests.jar"]