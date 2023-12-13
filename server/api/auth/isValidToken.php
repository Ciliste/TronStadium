<?php

require_once '../../DB.inc.php';

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

$stmt = $PDO->prepare('SELECT COUNT(*) FROM users WHERE token = ? AND token_expires > NOW()');
$stmt->execute([$token]);

if ($stmt->fetchColumn() === 0) {

	http_response_code(200);
	echo json_encode([
		'valid' => false,
	]);
}
else {

	http_response_code(200);
	echo json_encode([
		'valid' => true,
	]);
}