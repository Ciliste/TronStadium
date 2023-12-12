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

	// TODO: Resoudre le problème du prepared statement qui ne fonctionne pas avec le token
	$sql = "UPDATE users SET token = '$token', token_expires = NOW() + INTERVAL '1 day' WHERE id = $user_id";
	$PDO->query($sql);

	echo $sql;
}