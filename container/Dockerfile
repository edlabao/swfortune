#------------------------------------------------------------------------------
#
# Dockerfile for building the swfortune service image.
#
#------------------------------------------------------------------------------

# Initialize global args.
ARG COMMIT_HASH=n/a
ARG APP_NAME=swfortune


# First stage builds the jar file.
FROM eldr/java-devel:1.12.0-1 AS build-stage

# Globals used this stage.
ARG APP_NAME
ARG COMMIT_HASH

# Variables used this stage.
ARG GIT_REPO_URL=https://github.com/edlabao/${APP_NAME}.git

RUN cd /tmp \
    && git clone ${GIT_REPO_URL} \
    && cd ${APP_NAME}\
    && git checkout ${COMMIT_HASH} \
    && cd service \
    && mvn package


# Second stage packages the jar into a slimmer java image.
FROM eldr/java-jdk:1.12.0-1

# Globals used this stage.
ARG APP_NAME
ARG COMMIT_HASH

# Variables used for generating the image tag.
ARG REGISTRY=
ARG ORGANIZATION=eldr
ARG APP_VERSION=0.1.0
ARG APP_BUILD_NUMBER=1

# Other build variables.
ARG CONTAINER_UID=40001
ARG CONTAINER_USER=kafka
ARG MAINTAINER=n/a

# Copy build artifacts from the build stage.
COPY --from=build-stage \
    /tmp/${APP_NAME}/service/target/*jar \
    /opt/swfortune/libexec/swfortune.jar

# Install the application and dependencies and perform cleanup tasks in a
# single RUN step to keep our image footprint as small as possible.
RUN mkdir -p /opt/swfortune/libexec \
    && tdnf install -y shadow \
    && groupadd --system \
    --gid ${CONTAINER_UID} ${CONTAINER_USER} \
    && useradd \
    --system \
    --no-create-home \
    --no-log-init \
    --gid ${CONTAINER_UID} \
    --uid ${CONTAINER_UID} ${CONTAINER_USER} \
    && /bin/chown -R ${CONTAINER_USER} /opt/${APP_NAME} \
    && tdnf remove -y shadow \
    && tdnf clean all \
    && rm -rf /var/cache/tdnf/*

# Expose application ports.
#     8080 - http port
EXPOSE 8080

# Add some hopefully useful labels.
#
# We use some standard Label Schema attributes defined at:
#     http://label-schema.org/rc1/
#
LABEL \
    maintainer="${MAINTAINER}" \
    org.label-schema.schema-version="1.0.0" \
    org.label-schema.name="${APP_NAME}" \
    org.label-schema.description="Java 12 development image" \
    org.label-schema.vcs-url="" \
    org.label-schema.vcs-ref="${COMMIT_HASH}" \
    org.label-schema.version="${APP_VERSION}" \
    eldr.maven.version=${MVN_VERSION}

# Run the service as the default command.
CMD [ "java", "-jar", "/opt/swfortune/libexec/swfortune.jar" ]
