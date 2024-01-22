FROM ubuntu:latest
LABEL authors="Ethan.Nguyen"

ENTRYPOINT ["top", "-b"]

# Install required dependencies
RUN apt-get update -y && \
    apt-get install -y wget unzip libglu1-mesa && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* \

# Set environment variables
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $PATH:$JAVA_HOME/bin

# Install Node.js
RUN curl -sL https://deb.nodesource.com/setup_21.x | bash - && \
    apt-get install -y nodejs

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

# Install Appium
RUN npm install -g appium

# Create an Android emulator
RUN echo "no" | ${ANDROID_HOME}/tools/bin avdmanager create avd -n test_emulator -k "system-images;android-33;google_apis;x86_64" --device "pixel"

# Start Appium when the container starts
CMD ["appium", "--log-level", "debug"]