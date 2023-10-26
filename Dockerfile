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

COPY ./drivers/google-chrome-118.0.5993.117.deb ./google-chrome-118.0.5993.117.deb
# Install chrome broswer
RUN apt-get update && apt-get install -y gnupg2 gdebi xvfb gtk2-engines-pixbuf xfonts-cyrillic xfonts-100dpi xfonts-75dpi xfonts-base xfonts-scalable
RUN gdebi -n ./google-chrome-118.0.5993.117.deb

RUN apt install -y sudo
RUN useradd -ms /bin/bash automation && usermod -aG sudo automation
RUN echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER automation
WORKDIR /home/automation/
RUN PATH=$PATH:/usr/local/openjdk-18/bin/ && sudo chown automation:automation /home/automation/
# # # copy jar from the first stage
COPY --from=builder /build/ ./
COPY ./drivers/chromedriver ./chromedriver

# CMD ["java", "-cp", "/target/classes/;/target/dependency/"]

