# First stage: complete build environment
FROM maven:3.8.5-openjdk-18 AS builder

WORKDIR /build
# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean dependency:copy-dependencies compile

# # Second stage: minimal runtime environment
# FROM openjdk:20-jdk
FROM maven:3.8.7-openjdk-18-slim

WORKDIR /build
# # # copy jar from the first stage
COPY --from=builder /build/ /build/
COPY ./drivers/ /build/drivers

# Install chrome broswer
RUN apt-get update && apt-get install -y gnupg2
RUN curl -sS -o - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list
RUN apt-get update && apt-get -y install google-chrome-stable=118.0.5993.88-1

# CMD ["java", "-cp", "/target/classes/;/target/dependency/"]
