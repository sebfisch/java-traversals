# Java Traversals

## Integrated Development Environment

This repository contains a `docker-compose.yml` file
which allows to run a terminal based development environment
in a docker container.
This environment basically consists of `git`, `mvn`,
and `vim` with plugins for Java development.

If you have installed [docker compose]
you can run the tests using the following command:

    docker-compose run --rm dev mvn test

Downloaded Java dependencies will be cached in a docker volume
so they can be reused in new instances of the container
and won't be downloaded again until you remove the volume.

To open a terminal in the docker image 
for developing, building, and testing the underlying code, 
run the following command:

    docker-compose run --rm dev

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

