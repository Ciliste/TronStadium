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

echo "Moving database directory..."
mv ./TronStadium/server/database ./