FROM openjdk:21-slim

WORKDIR /app

RUN apt-get update && apt-get install -y findutils

COPY src/ ./
COPY mysql-connector-j-9.4.0.jar ./mysql.jar

RUN echo "Files in container:" && find . -type f

RUN find . -name "*.java" > sources.txt \
    && cat sources.txt \
    && javac --release 21 --enable-preview -cp .:mysql.jar @sources.txt

CMD ["java", "--enable-preview", "-cp", ".:mysql.jar", "GameServer"]
