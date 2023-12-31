# If TronStadium directory exists, then delete it
if [ -d "./TronStadium" ]; then
	echo "TronStadium directory exists, deleting it..."
	rm -fR ./TronStadium
fi

# Clone the TronStadium repository
echo "Cloning TronStadium repository..."
git clone https://github.com/Ciliste/TronStadium.git

# If database directory exists, then delete it
if [ -d "./database" ]; then
	echo "Database directory exists, deleting it..."
	rm -fR ./database
fi

# Move database directory
echo "Moving database directory..."
mv ./TronStadium/server/database ./

# If scripts files exists, then delete it
if [ -f "./ServerStart.sh" ]; then
	echo "ServerStart.sh file exists, deleting it..."
	rm -f ./ServerStart.sh
fi

if [ -f "./ServerStop.sh" ]; then
	echo "ServerStop.sh file exists, deleting it..."
	rm -f ./ServerStop.sh
fi

# Move scripts
echo "Moving start script..."
mv ./TronStadium/script/ServerStart.sh ./
chmod +x ./ServerStart.sh

echo "Moving stop script..."
mv ./TronStadium/script/ServerStop.sh ./
chmod +x ./ServerStop.sh

#
# API Setup
#

# If www directory exists, then delete it
if [ -d "./www" ]; then
	echo "www directory exists, deleting it..."
	rm -fR ./www
fi

# Move API files
echo "Moving API files..."
mkdir ./www
mkdir ./www/api
mv ./TronStadium/server/api/* ./www/api
mv ./www/api/DB.inc.php ./DB.inc.php
mv ./www/api/API_Utils.inc.php ./API_Utils.inc.php

#If info directory exists, then delete it
if [ -d "./info" ]; then
	echo "Info directory exists, deleting it..."
	rm -fR ./info
fi

# Create information files
echo "Creating information files..."
mkdir ./info
echo "This directory contains information about the server." > ./info/README.txt
echo "New Server" > ./info/NAME.txt
echo "This is a new server." > ./info/DESCRIPTION.txt

echo "Removing TronStadium directory..."
rm -fR ./TronStadium