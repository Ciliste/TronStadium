<?php

function getFileContents($path) {

	$contents = file_get_contents($path);

	if ($contents === false) {

		http_response_code(500);
		die(json_encode([
			'error' => 'Failed to read file.',
		]));
	}

	return $contents;
}

$data = [];

$data['name'] = getFileContents('../../../info/NAME.txt');
$data['description'] = getFileContents('../../../info/DESCRIPTION.txt');

http_response_code(200);
echo json_encode($data);