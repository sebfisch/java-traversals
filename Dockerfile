FROM openjdk:8-jdk-alpine
ENV TZ=Europe/Berlin
RUN apk add --no-cache curl tar bash bash-completion tzdata git tmux vim
RUN sed -i 's/ash$/bash/g' /etc/passwd
RUN curl -fsSL -o /usr/share/git-core/git-prompt.sh https://raw.githubusercontent.com/git/git/master/contrib/completion/git-prompt.sh
ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
ENV EDITOR=vim
WORKDIR "$USER_HOME_DIR"
CMD tmux
