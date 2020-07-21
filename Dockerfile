FROM adoptopenjdk/openjdk11:alpine-slim
RUN mkdir /opt/app
COPY target/bitcoin_investment-0.0.1.jar /opt/app
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "/opt/app/bitcoin_investment-0.0.1.jar"]