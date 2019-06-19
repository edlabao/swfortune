#------------------------------------------------------------------------------
#
# Dockerfile for building a java development image based on openjdk-1.12
#
# Run this container ando mount your java project workspace into it:
#
#     docker run -it --rm \
#         -u `id -u`:`id -g` \
#         -v `pwd`:/opt/develop \
#         -w /opt/develop \
#         eldr/java-devel:1.12.0-1 bash
#
# In the example above, we assume that we are in the top directory of our
# project and mount it into the container at /opt/develop which we make the 
# working directory. In addition we use our real user's uid and gid inside the
# container so that any generated files will be owned by our real user account.
#
# When running services, don't forget to also bind the container to the host
# port (e.g. -p 8080:8080).
#
#------------------------------------------------------------------------------

# Variables used for generating the image tag.
ARG REGISTRY=
ARG ORGANIZATION=eldr
ARG APP_NAME=swfortune
ARG APP_VERSION=0.1.0
ARG APP_BUILD_NUMBER=1

# Other build variables.
ARG COMMIT_HASH=n/a
ARG MAINTAINER=n/a
ARG GIT_PROJECT=swfortune
ARG GIT_REPO_URL=git@github.com:edlabao/${GIT_PROJECT}.git


FROM eldr/java-devel:1.12.0-1 AS build-stage

RUN cd /tmp \
    && git clone ${GIT_REPO_URL} \
    && cd ${GIT_PROJECT} \
    && make build


FROM build-stage

RUN mkdir -p /opt/swfortune/libexec
COPY --from build-stage \
    /tmp/${GIT_PROJECT}/service/target/*jar \
    /opt/swfortune/libexec/swfortune.jar


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
