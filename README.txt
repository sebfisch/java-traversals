# Java Traversals

## Integrated Development Environment

This repository contains a `docker-compose.yml` file
which allows to run a terminal based development environment
in a docker container.
When building the container, a user is created
based on build args defined in the `docker-compose.yml` file.
To avoid permission issues,
you can adjust the listed build args to reflect your local setup.
After adjusting the build args appropriately,
you can build the container with the following command:

    docker-compose build

The development environment basically consists of `git`, `mvn`,
and `vim` with plugins for Java development.

You can run the tests using the following command:

    docker-compose run --rm dev mvn test

Downloaded Java dependencies will be cached in a docker volume
so they can be reused in new instances of the container
and won't be downloaded again until you remove the volume.

To open a terminal in the docker image 
for developing, building, and testing the underlying code, 
run the following command:

    docker-compose run --rm dev

Whenever you start bash in the container
(which happens automatically when you do not specify a different command)
Java dependencies will be resolved automatically and cached for future use.

Before running the container you can change the value

    TZ=Europe/Berlin

to a more appropriate timezone in the `docker-compose.yml` file.

If you want to use the docker image to commit changes to the code, 
make sure the local git configuration
(which is mounted into the running dev container automatically)
contains at least your name and email.
On a Unix system, you could copy your global git configuration
(which presumably contains your name and email)
into the local configuration with the following command:

    cat ~/.gitconfig >> .git/config

