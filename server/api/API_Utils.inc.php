<?php

require_once 'DB.inc.php';

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

	global $PDO;

	$stmt = $PDO->prepare('UPDATE users SET token = ?, token_expires = NOW() + INTERVAL \'1 day\' WHERE id = ?');

	$stmt->bindParam(1, $token, PDO::PARAM_STR);
	$stmt->bindParam(2, $user_id, PDO::PARAM_INT);

	$stmt->execute();
}