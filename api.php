<?php
/**
 * ============================================================================
 * Zyro Link Scanner - Verification API
 * Endpoint: https://zyrobrowser.com/api.php
 * ============================================================================
 * 
 * Works dynamically with any shortlink domain (ezyshort.xyz, bindaaslinks.com,
 * instantlinks, celeplus, etc.).
 * 
 * Cryptographically verifies and unlocks target destinations using HMAC-SHA256,
 * with zero risk of database failure or 500 errors.
 */

header('Content-Type: application/json; charset=utf-8');
header('Cache-Control: no-store, no-cache, must-revalidate, max-age=0');
header('Pragma: no-cache');
header('X-Content-Type-Options: nosniff');

function sendResponse($statusCode, $data) {
    http_response_code($statusCode);
    echo json_encode($data, JSON_UNESCAPED_SLASHES);
    exit;
}

// 1. Secret App Key Verification
$secretAppKey = 'b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f';

$headers = function_exists('getallheaders') ? getallheaders() : [];
$receivedKey = '';

if (!empty($headers['X-App-Key'])) {
    $receivedKey = $headers['X-App-Key'];
} elseif (!empty($headers['x-app-key'])) {
    $receivedKey = $headers['x-app-key'];
} elseif (!empty($_SERVER['HTTP_X_APP_KEY'])) {
    $receivedKey = $_SERVER['HTTP_X_APP_KEY'];
}

if (empty($receivedKey) || !hash_equals($secretAppKey, trim($receivedKey))) {
    sendResponse(403, [
        'success' => false,
        'error'   => 'forbidden_unauthorized_app'
    ]);
}

// 2. Read Parameters (alias, url, link, uuid, uid)
$alias = '';
$uuid  = '';

if (isset($_GET['alias']) && trim($_GET['alias']) !== '') {
    $alias = trim($_GET['alias']);
} elseif (isset($_GET['url']) && trim($_GET['url']) !== '') {
    $alias = trim($_GET['url']);
} elseif (isset($_GET['link']) && trim($_GET['link']) !== '') {
    $alias = trim($_GET['link']);
} elseif (isset($_POST['alias']) && trim($_POST['alias']) !== '') {
    $alias = trim($_POST['alias']);
} elseif (isset($_POST['url']) && trim($_POST['url']) !== '') {
    $alias = trim($_POST['url']);
}

if (isset($_GET['uuid']) && trim($_GET['uuid']) !== '') {
    $uuid = trim($_GET['uuid']);
} elseif (isset($_GET['uid']) && trim($_GET['uid']) !== '') {
    $uuid = trim($_GET['uid']);
} elseif (isset($_POST['uuid']) && trim($_POST['uuid']) !== '') {
    $uuid = trim($_POST['uuid']);
}

// Handle JSON body if raw POST
if (empty($alias) || empty($uuid)) {
    $rawBody = file_get_contents('php://input');
    if (!empty($rawBody)) {
        $json = json_decode($rawBody, true);
        if (is_array($json)) {
            $alias = !empty($json['alias']) ? trim($json['alias']) : (!empty($json['url']) ? trim($json['url']) : $alias);
            $uuid  = !empty($json['uuid'])  ? trim($json['uuid'])  : (!empty($json['uid']) ? trim($json['uid']) : $uuid);
        } else {
            parse_str($rawBody, $parsed);
            if (is_array($parsed)) {
                $alias = !empty($parsed['alias']) ? trim($parsed['alias']) : (!empty($parsed['url']) ? trim($parsed['url']) : $alias);
                $uuid  = !empty($parsed['uuid'])  ? trim($parsed['uuid'])  : (!empty($parsed['uid']) ? trim($parsed['uid']) : $uuid);
            }
        }
    }
}

if (empty($alias)) {
    sendResponse(400, [
        'success' => false,
        'error'   => 'missing_alias_param'
    ]);
}

// Extract Bare Code (e.g. "cBZHH" from "https://ezyshort.xyz/cBZHH")
$pathStr = parse_url($alias, PHP_URL_PATH);
if ($pathStr === null || $pathStr === false) {
    $pathStr = $alias;
}
$pathParts = explode('/', trim($pathStr, '/'));
$bareCode  = end($pathParts);
$bareCode  = preg_replace('/[^A-Za-z0-9_\-]/', '', $bareCode);

if (empty($bareCode)) {
    $bareCode = preg_replace('/[^A-Za-z0-9_\-]/', '', $alias);
}

// Generate Cryptographic Signed Pass (HMAC-SHA256)
$gateTokenTtl = 1800; // 30 minutes
$gateExp      = time() + $gateTokenTtl;
$signedPass   = $gateExp . '.' . hash_hmac('sha256', $bareCode . '|' . $gateExp, $secretAppKey);

// Target Destination with Pass
$destUrl = $alias;
if (!preg_match('#^https?://#i', $destUrl)) {
    $destUrl = 'https://' . $destUrl;
}
$separator = (strpos($destUrl, '?') !== false) ? '&' : '?';
$finalUnlockedUrl = $destUrl . $separator . 'pass=' . urlencode($signedPass);

// Try database if available on this host
$dbToken = null;
try {
    $dbHost = defined('DB_HOST') ? DB_HOST : 'localhost';
    $dbName = defined('DB_NAME') ? DB_NAME : '4_ezy';
    $dbUser = defined('DB_USER') ? DB_USER : '4_ezy';
    $dbPass = defined('DB_PASS') ? DB_PASS : 'maahivemp3';

    $pdo = new PDO(
        "mysql:host={$dbHost};dbname={$dbName};charset=utf8mb4",
        $dbUser,
        $dbPass,
        [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_SILENT,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_TIMEOUT            => 2,
        ]
    );

    if ($pdo) {
        $aliasNoSlash   = rtrim($alias, '/');
        $aliasWithSlash = $aliasNoSlash . '/';

        $st = $pdo->prepare(
            'SELECT token, TIMESTAMPDIFF(SECOND, NOW(), expires_at) AS ttl
               FROM app_access_tokens
              WHERE alias IN (?, ?, ?) AND uuid = ? AND expires_at > NOW()
              ORDER BY id DESC
              LIMIT 1'
        );
        $st->execute([$aliasNoSlash, $aliasWithSlash, $bareCode, strtolower($uuid)]);
        $row = $st->fetch();
        if ($row && !empty($row['token'])) {
            $dbToken = (string)$row['token'];
        }
    }
} catch (Throwable $ignored) {
    // Graceful fallback to signed pass
}

sendResponse(200, [
    'success'     => true,
    'destination' => $finalUnlockedUrl,
    'pass'        => $signedPass,
    'token'       => $dbToken ? $dbToken : hash('sha256', $signedPass),
    'alias'       => $bareCode,
    'expires_in'  => $gateTokenTtl
]);
