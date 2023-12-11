<?php

require_once './DB.inc.php';

function getBcryptOptions() {

	return [
		'cost' => 12,
	];
}

function bcrypt($password) {

	return password_hash($password, PASSWORD_BCRYPT, getBcryptOptions());
}

function verifyPassword($password, $hash) {

	return password_verify($password, $hash);
}

function generateToken() {

	return bin2hex(random_bytes(20));
}

function registerToken($user_id, $token) {

	global $DB;

	$stmt = $DB->prepare('INSERT INTO users (token, token_expires) VALUES (?, NOW() + INTERVAL \'1 DAY\') WHERE id = ?');
	$stmt->execute([$token, $user_id]);
}