# Contribute

## Prerequisites

1. Game Master file can be found on Android devices at `<internal storage OR sd card>/Android/data/com.ninaticlabs.pokemongo/files/remote_config_cache`. Copy the Game Master file in this directory.

2. Install [Docker](https://docs.docker.com/install/).

## Generate GAME_MASTER.json

You can generate a new GAME_MASTER.json using the following command.
It will create a new folder `versions/TIMESTAMP_OF_THE_FILE`.
If everything was configured correctly, it will output in the
corresponding directory inside the `version` folder.

```bash

docker build -t "$USER/pokemongo-game-master" .
docker run \
  -v "$(pwd)/versions:/var/lib/pokemongo-game-master/versions" \
  -v "$(pwd)/GAME_MASTER.protobuf:/var/lib/pokemongo-game-master/GAME_MASTER.protobuf" \
  -v "$(pwd)/.git:/var/lib/pokemongo-game-master/.git" \
  -v "$HOME/.gitconfig:/root/.gitconfig" \
  -v "$HOME/.m2:/root/.m2" \
  -it "$USER/pokemongo-game-master" \
  -f /var/lib/pokemongo-game-master/GAME_MASTER.protobuf --latest

```
