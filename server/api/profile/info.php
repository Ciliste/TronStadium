<?php

require_once '../../DB.inc.php';
require_once '../../API_Utils.inc.php';

# Show errors
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

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

if (!isValidToken($token)) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Invalid token.',
	]));
}

if (isset($_GET['user_id'])) {

	$user_id = $_GET['user_id'];
}
else {

	$stmt = $PDO->prepare('SELECT * FROM users WHERE token = ?');
	$stmt->execute([$token]);

	$user = $stmt->fetch(PDO::FETCH_ASSOC);

	$data = [
		'id' => $user['id'],
		'username' => $user['username'],
		'created_at' => $user['created_at'],
	];

	echo json_encode($data);
}