<?php

require_once '../../../DB.inc.php';

require_once '../../../API_Utils.inc.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {

	http_response_code(405);
	die(json_encode([
		'error' => 'Only GET requests are allowed.',
	]));
}

if (!isset($_GET['username']) || !isset($_GET['password'])) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Username and password are required.',
	]));
}

$username = $_GET['username'];
$password = $_GET['password'];

$stmt = $PDO->prepare('SELECT password_hash FROM users WHERE username = ?');
$stmt->execute([$username]);

if ($stmt->rowCount() === 0) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Username does not exist.',
	]));
}

$password_hash = $stmt->fetchColumn();

if (!verifyPassword($password, $password_hash)) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Incorrect password.',
	]));
}

$token = generateToken();

$stmt = $PDO->prepare('SELECT id FROM users WHERE username = ?');
$stmt->execute([$username]);

$user_id = $stmt->fetchColumn();

registerToken($user_id, $token);

echo json_encode([
	'token' => $token,
]);