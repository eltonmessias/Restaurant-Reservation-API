FROM openjdk:24-jdk
LABEL authors="elton"
ADD target/reservationSystem.jar reservationSystem.jar
ENTRYPOINT ["java", "-jar", "/reservationSystem.jar"]