<?php

require_once '../../DB.inc.php';

require_once '../../API_Utils.inc.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {

	http_response_code(405);
	die(json_encode([
		'error' => 'Only POST requests are allowed.',
	]));
}

if (!isset($_POST['username']) || !isset($_POST['password'])) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Username and password are required.',
	]));
}

$username = $_POST['username'];
$password = $_POST['password'];

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

registerToken($username, $token);

echo json_encode([
	'token' => $token,
]);