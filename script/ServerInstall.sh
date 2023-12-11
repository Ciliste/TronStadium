# If TronStadium directory exists, then delete it
if [ -d "./TronStadium" ]; then
	echo "TronStadium directory exists, deleting it..."
	rm -r ./TronStadium
fi