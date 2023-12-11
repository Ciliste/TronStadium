if command -v docker &> /dev/null; then
    echo "Docker is installed."
else
    echo "/!\ Docker is not installed!"
	echo "Please install Docker before running this script."
	exit 1
fi

if groups | grep -q '\bdocker\b'; then
    echo "The user is in the Docker group."
else
    echo "/!\ The user is not in the Docker group!"
	echo "Please add the user to the Docker group before running this script."
	exit 1
fi

if docker inspect -f '{{.State.Running}}' tron-stadium-db &> /dev/null; then
	echo "/!\ The database container is running!"
	echo "Please stop the database container before running this script."
	exit 1
else
	echo "The database container is not running."
fi

docker compose -f ./database/stack.yml up -d