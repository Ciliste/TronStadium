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

function generateToken($length = 20) {

	$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$characters_length = strlen($characters);
	$token = '';

	for ($i = 0; $i < $length; $i++) {

		$token .= $characters[rand(0, $characters_length - 1)];
	}

	return $token;
}

function registerToken($user_id, $token) {

	global $PDO;

	$stmt = $PDO->prepare('UPDATE users SET token = ?, token_expires = NOW() + INTERVAL \'1 day\' WHERE id = ?');
	$stmt->execute([$token, $user_id]);
}