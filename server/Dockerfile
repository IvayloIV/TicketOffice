FROM adoptopenjdk/openjdk11
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build/libs/ticketoffice-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]