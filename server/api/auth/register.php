<?php

require_once '../../DB.inc.php';

require_once '../../API_Utils.inc.php';

$username = $_POST['username'];
$password = $_POST['password'];

if (empty($username) || empty($password)) {

	http_response_code(400);
	die(json_encode([
		'error' => 'Username and password are required.',
	]));
}

function username_exists($username) {

	global $PDO;

	$stmt = $PDO->prepare('SELECT COUNT(*) FROM users WHERE username = ?');
	$stmt->execute([$username]);

	return $stmt->fetchColumn() > 0;
}

if (username_exists($username)) {

	http_response_code(400);
	die(
		json_encode([
			'error' => 'Username already exists.',
		])
	);
}

function create_user($username, $password) {

	global $PDO;

	$stmt = $PDO->prepare('INSERT INTO users (username, password_hash) VALUES (?, ?)');
	$stmt->execute([$username, bcrypt($password)]);

	$stmt = $PDO->prepare('SELECT id FROM users WHERE username = ?');
	$stmt->execute([$username]);

	return $stmt->fetchColumn();
}

$user_id = create_user($username, $password);

if ($user_id === false) {

	http_response_code(500);
	die(json_encode([
		'error' => 'Failed to create user.',
	]));
}
else {

	http_response_code(200);

	$token = generateToken();
	registerToken($user_id, $token);

	echo json_encode([
		'token' => $token,
	]);
}