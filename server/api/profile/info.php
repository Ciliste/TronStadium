<?php

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

	echo json_encode($user);
}