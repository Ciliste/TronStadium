<?php

$PG_HOST = 'localhost';
$PG_PORT = 54322;
$PG_DATABASE = 'postgres';
$PG_USER = 'postgres';
$PG_PASSWORD = 'change_me';

$PDO = new PDO("pgsql:host=$PG_HOST;port=$PG_PORT;dbname=$PG_DATABASE", $PG_USER, $PG_PASSWORD);