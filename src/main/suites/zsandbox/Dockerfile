# First stage: complete build environment
FROM maven:3.8.6-openjdk-18 AS builder

WORKDIR /build
# add pom.xml and source code
ADD ./pom.xml pom.xml
ADD ./src src/

# package jar
RUN mvn clean dependency:copy-dependencies compile

# FROM maven:3.8.7-openjdk-18-slim
FROM registry.internal.techtank9.com/registry/analyst/automation/coreselenium:base

RUN useradd -ms /bin/bash automation && usermod -aG sudo automation && echo '%sudo ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers
USER mobileautomation
WORKDIR /home/automation/

# # # copy jar from the first stage
COPY --from=builder /build/ ./
COPY ./drivers/chromedriver ./chromedriver

# Install Node.js
RUN npm install

# Set up Android SDK
ENV ANDROID_HOME /opt/android-sdk
ENV PATH ${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/platform-tools

# Set environment variables for Android JDK
ENV ANDROID_JDK_HOME ${ANDROID_HOME}/cmdline-tools/latest/jre
ENV PATH ${PATH}:${ANDROID_JDK_HOME}/bin

# Download and install Android SDK
RUN wget https://dl.google.com/android/repository/commandlinetools-linux-7302050_latest.zip -O /tmp/android-sdk.zip && \
    mkdir -p /opt/android-sdk && \
    unzip /tmp/android-sdk.zip -d /opt/android-sdk && \
    rm /tmp/android-sdk.zip

# Accept Android SDK licenses
RUN yes | ${ANDROID_HOME}/tools/bin/sdkmanager --licenses

# Install required Android components
RUN ${ANDROID_HOME}/tools/bin/sdkmanager "platform-tools" "platforms;android-33" "emulator" "system-images;android-33;google_apis;x86_64"


# Create an Android emulator
RUN echo "no" | ${ANDROID_HOME}/tools/bin avdmanager create avd -n test_emulator -k "system-images;android-33;google_apis;x86_64" --device "pixel"

# CMD ["java", "-cp", "/target/classes/;/target/dependency/"]





