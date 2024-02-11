FROM openjdk:11
ARG JAR_FILE=build/libs/app.jar
#app.jar 이라는 네임으로 카피
COPY ${JAR_FILE} ./app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "./app.jar"]