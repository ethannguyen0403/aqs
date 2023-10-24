# First stage: complete build environment
FROM maven:3.8.5-openjdk-18 AS builder

WORKDIR /build
# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean dependency:copy-dependencies compile

# # Second stage: minimal runtime environment
FROM openjdk:20-jdk

WORKDIR /build
# # # copy jar from the first stage
COPY --from=builder /build/ /build/
COPY ./drivers/ /build/drivers
# CMD ["java", "-cp", "/target/classes/;/target/dependency/"]

RUN apt-get -y install lsb-release libappindicator3-1
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb || true
RUN apt-get -fy install
