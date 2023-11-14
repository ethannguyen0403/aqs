# First stage: complete build environment
FROM maven:3.8.5-openjdk-18 AS builder

WORKDIR /build
# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean dependency:copy-dependencies compile

# FROM maven:3.8.7-openjdk-18-slim
FROM registry.internal.techtank9.com/registry/analyst/automation/aqs:base

RUN useradd -ms /bin/bash automation && usermod -aG sudo automation && echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER automation
WORKDIR /home/automation/

# # # copy jar from the first stage
COPY --from=builder /build/ ./
COPY ./drivers/chromedriver ./chromedriver

# CMD ["java", "-cp", "/target/classes/;/target/dependency/"]
