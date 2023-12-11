if command -v docker &> /dev/null; then
    echo "Docker is installed."
else
    echo "/!\ Docker is not installed!"
	echo "Please install Docker before running this script."
	exit 1
fi