<?php
/**
 * ============================================================================
 * Zyro Link Scanner - Token Verification API
 * Endpoint: https://zyrobrowser.com/api.php
 * ============================================================================
 * 
 * Flow:
 * - Zyro Browser Link Scanner makes a request to this endpoint with:
 *   - Header: X-App-Key: <secret_key>
 *   - Query / Body: alias (or url), uuid
 * - Validates credentials and queries MySQL `app_access_tokens` table.
 * - Returns JSON: { "success": true, "token": "...", "expires_in": 1800 }
 * 
 * Works dynamically with any shortlink domain (ezyshort.xyz, bindaaslinks.com,
 * instantlinks, celeplus, etc.) by matching full URLs and aliases.
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

// 3. Validation
$aliasNoSlash   = rtrim($alias, '/');
$aliasWithSlash = $aliasNoSlash . '/';

// Extract bare alias code (e.g. "cBZHH" from "https://ezyshort.xyz/cBZHH")
$pathParts = explode('/', parse_url($alias, PHP_URL_PATH) ?? $alias);
$bareCode  = end($pathParts);

$validUuid = preg_match(
    '/^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i',
    $uuid
);

if (empty($alias) || empty($uuid) || strlen($alias) > 500 || !$validUuid) {
    sendResponse(400, [
        'success' => false,
        'error'   => 'invalid_params'
    ]);
}

// 4. Query MySQL Database
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
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES   => false,
        ]
    );

    // Look for matching token by full URL (with and without trailing slash) or bare alias code
    $st = $pdo->prepare(
        'SELECT token, TIMESTAMPDIFF(SECOND, NOW(), expires_at) AS ttl
           FROM app_access_tokens
          WHERE alias IN (?, ?, ?) AND uuid = ? AND expires_at > NOW()
          ORDER BY id DESC
          LIMIT 1'
    );
    $st->execute([$aliasNoSlash, $aliasWithSlash, $bareCode, strtolower($uuid)]);
    $row = $st->fetch();

    if (!$row || empty($row['token'])) {
        sendResponse(404, [
            'success' => false,
            'error'   => 'not_found_or_expired'
        ]);
    }

    sendResponse(200, [
        'success'    => true,
        'token'      => (string)$row['token'],
        'expires_in' => max(0, (int)$row['ttl'])
    ]);

} catch (Exception $e) {
    sendResponse(500, [
        'success' => false,
        'error'   => 'server_error'
    ]);
}
?>
