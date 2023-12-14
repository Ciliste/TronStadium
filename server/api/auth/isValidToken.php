<?php

require_once '../../../API_Utils.inc.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {

	http_response_code(405);
	die(json_encode([
		'error' => 'Only GET requests are allowed.',
	]));
}

if (!isset($_GET['token'])) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Token is required.',
	]));
}

$token = $_GET['token'];

http_response_code(200);
echo json_encode([
	'valid' => isValidToken($token),
]);