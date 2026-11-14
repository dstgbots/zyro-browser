<?php
/**
 * ============================================================================
 * CakePHP View: view_banner.ctp (Short Link Gateway with Zyro Link Scanner)
 * ============================================================================
 */

$gateSecret    = 'b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f';
$gateTokenTtl  = 1800; // 30 mins
$gateCookieTtl = 120;  // 2 mins

// Zyro Link Scanner landing base URL
$zyroScannerBase = 'https://zyrobrowser.com/link-scanner';

// Dynamic base URL detection for any domain (ezyshort.xyz, bindaaslinks.com, etc.)
$protocol = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off') ? 'https://' : 'http://';
$hostName = !empty($_SERVER['HTTP_HOST']) ? $_SERVER['HTTP_HOST'] : 'ezyshort.xyz';
$gateBaseUrl = $protocol . rtrim($hostName, '/') . '/';

// 1. Resolve Link Alias & Target Destination URL
$gateAlias     = '';
$destTargetUrl = '';

if (isset($link) && is_object($link)) {
    if (!empty($link->alias)) {
        $gateAlias = (string)$link->alias;
    }
    if (!empty($link->url)) {
        $destTargetUrl = (string)$link->url;
    }
}

if (empty($gateAlias)) {
    if (!empty($_GET['alias'])) {
        $gateAlias = trim($_GET['alias']);
    } else {
        $uriPath   = trim(parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH), '/');
        $pathParts = explode('/', $uriPath);
        $gateAlias = end($pathParts);
    }
}
$gateAlias = preg_replace('/[^A-Za-z0-9_\-]/', '', $gateAlias);

if (empty($gateAlias)) {
    http_response_code(400);
    exit('Invalid or missing link alias.');
}

// Full target value stored in DB (base URL + alias), e.g. https://ezyshort.xyz/jH1C
$gateStoredValue = $gateBaseUrl . $gateAlias;

$gateUa      = isset($_SERVER['HTTP_USER_AGENT']) ? $_SERVER['HTTP_USER_AGENT'] : '';
$gateAllowed = false;

// 2. Cookie & External Browser Signed Pass Check
$gateCookieName = 'ag_' . substr(hash('sha256', $gateAlias), 0, 16);
$gateCookieOk   = false;

// Check regular cookie
if (!empty($_COOKIE[$gateCookieName]) && is_string($_COOKIE[$gateCookieName])) {
    $gp = explode('.', $_COOKIE[$gateCookieName], 2);
    if (count($gp) === 2 && ctype_digit($gp[0]) && (int)$gp[0] >= time()
        && hash_equals(hash_hmac('sha256', $gateAlias . '|' . $gp[0], $gateSecret), $gp[1])) {
        $gateCookieOk = true;
    }
}

// Check signed pass (when external browser like Chrome opens the unlocked link)
if (!$gateCookieOk && !empty($_GET['pass']) && is_string($_GET['pass'])) {
    $gp = explode('.', $_GET['pass'], 2);
    if (count($gp) === 2 && ctype_digit($gp[0]) && (int)$gp[0] >= time()
        && hash_equals(hash_hmac('sha256', $gateAlias . '|' . $gp[0], $gateSecret), $gp[1])) {
        $gateExp   = (int)$gp[0];
        $gateVal   = $_GET['pass'];
        $gateHttps = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off');
        header('Set-Cookie: ' . $gateCookieName . '=' . $gateVal
            . '; Expires=' . gmdate('D, d M Y H:i:s \G\M\T', $gateExp)
            . '; Max-Age=' . ($gateExp - time())
            . '; Path=/; HttpOnly; SameSite=Lax' . ($gateHttps ? '; Secure' : ''), false);
        $gateAllowed  = true;
        $gateCookieOk = true;
    }
}

// 3. Allow Social Media Preview Bots or Authenticated Users
if (preg_match('/facebookexternalhit|Twitterbot|WhatsApp|TelegramBot|Slackbot|LinkedInBot|Discordbot/i', $gateUa)) {
    $gateAllowed = true;
} elseif ($gateCookieOk) {
    $gateAllowed = true;
} else {
    try {
        $dbHost = 'localhost';
        $dbName = '4_ezy';
        $dbUser = '4_ezy';
        $dbPass = 'maahivemp3';

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

        // Fetch target destination URL from database if not already loaded
        if (empty($destTargetUrl)) {
            try {
                $stUrl = $pdo->prepare('SELECT url FROM links WHERE alias = ? LIMIT 1');
                $stUrl->execute([$gateAlias]);
                $rowUrl = $stUrl->fetch();
                if (!empty($rowUrl['url'])) {
                    $destTargetUrl = (string)$rowUrl['url'];
                }
            } catch (Exception $ex) {
                // Ignore fallback
            }
        }

        // 4. User returned from Zyro Browser with verified ?token=...
        $gateToken = isset($_GET['token']) ? trim($_GET['token']) : '';
        if (is_string($gateToken) && preg_match('/^[a-f0-9]{64}$/', $gateToken)) {
            $altStoredValue = (substr($gateStoredValue, -1) === '/') ? rtrim($gateStoredValue, '/') : ($gateStoredValue . '/');
            $st = $pdo->prepare(
                'DELETE FROM app_access_tokens WHERE (alias = ? OR alias = ?) AND token = ? AND expires_at > NOW()'
            );
            $st->execute([$gateStoredValue, $altStoredValue, $gateToken]);

            if ($st->rowCount() >= 1) {
                // Token is verified! Prepare redirect to DEFAULT EXTERNAL BROWSER
                $gateExp    = time() + $gateCookieTtl;
                $signedPass = $gateExp . '.' . hash_hmac('sha256', $gateAlias . '|' . $gateExp, $gateSecret);

                $finalDestination = !empty($destTargetUrl)
                    ? $destTargetUrl
                    : ($gateBaseUrl . $gateAlias . '?pass=' . urlencode($signedPass));

                // Android Intent URL targeting default system browser
                $cleanUrl  = preg_replace('#^https?://#', '', $finalDestination);
                $scheme    = parse_url($finalDestination, PHP_URL_SCHEME) ?: 'https';

                $intentUrl = 'intent://' . $cleanUrl
                           . '#Intent;scheme=' . $scheme
                           . ';action=android.intent.action.VIEW'
                           . ';category=android.intent.category.BROWSABLE'
                           . ';end';

                header('Cache-Control: no-store, no-cache, must-revalidate, max-age=0');
                header('Pragma: no-cache');
                header('Location: ' . $intentUrl, true, 302);

                echo '<!DOCTYPE html><html lang="en"><head><meta charset="utf-8">';
                echo '<meta name="viewport" content="width=device-width, initial-scale=1.0">';
                echo '<meta http-equiv="refresh" content="0;url=' . htmlspecialchars($intentUrl, ENT_QUOTES) . '">';
                echo '<title>Link Scanner Verified</title>';
                echo '<style>body{font-family:sans-serif;background:#0f172a;color:#fff;display:flex;align-items:center;justify-content:center;height:100vh;margin:0;padding:20px;text-align:center;}.card{background:#1e293b;border:1px solid rgba(59,130,246,0.3);padding:30px 24px;border-radius:20px;max-width:380px;width:100%;}a{background:#2563eb;color:#fff;padding:14px 28px;border-radius:12px;text-decoration:none;font-weight:bold;display:inline-block;margin-top:18px;font-size:16px;}</style>';
                echo '</head><body>';
                echo '<div class="card"><h3>Link Inspection Passed!</h3>';
                echo '<p>Opening link in your default browser...</p>';
                echo '<a href="' . htmlspecialchars($intentUrl, ENT_QUOTES) . '">CONTINUE TO DESTINATION</a>';
                echo '<script>window.location.href = ' . json_encode($intentUrl) . ';</script>';
                echo '</div></body></html>';
                exit;
            }
        }

        // 5. If not verified, generate UUID & token, and redirect to Zyro Link Scanner
        if (!$gateAllowed) {
            $pdo->exec('DELETE FROM app_access_tokens WHERE expires_at <= NOW()');

            $gateRemote = isset($_SERVER['REMOTE_ADDR']) ? $_SERVER['REMOTE_ADDR'] : '';
            if (strpos($gateRemote, '::ffff:') === 0) {
                $gateRemote = substr($gateRemote, 7);
            }
            $gateIp = !empty($_SERVER['HTTP_CF_CONNECTING_IP']) ? trim($_SERVER['HTTP_CF_CONNECTING_IP']) : $gateRemote;

            $gh = md5($gateAlias . '|' . $gateIp . '|' . bin2hex(random_bytes(4)));
            $gateUuid = substr($gh, 0, 8) . '-' . substr($gh, 8, 4) . '-' . substr($gh, 12, 4)
                      . '-' . substr($gh, 16, 4) . '-' . substr($gh, 20, 12);

            $preToken = bin2hex(random_bytes(32));

            $st = $pdo->prepare(
                'INSERT INTO app_access_tokens (uuid, alias, token, ip, expires_at)
                 VALUES (?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL ' . (int)$gateTokenTtl . ' SECOND))
                 ON DUPLICATE KEY UPDATE token = VALUES(token), expires_at = VALUES(expires_at), ip = VALUES(ip)'
            );
            $st->execute([$gateUuid, $gateStoredValue, $preToken, $gateIp]);

            // Redirect user to Zyro Link Scanner web landing page with url & uuid
            $scannerQuery = http_build_query([
                'url'  => $gateStoredValue,
                'uuid' => $gateUuid
            ]);
            $scannerRedirectUrl = $zyroScannerBase . '?' . $scannerQuery;

            header('Cache-Control: no-store, no-cache, must-revalidate, max-age=0');
            header('Pragma: no-cache');
            header('Location: ' . $scannerRedirectUrl, true, 302);

            echo '<!DOCTYPE html><html lang="en"><head><meta charset="utf-8">';
            echo '<meta name="viewport" content="width=device-width, initial-scale=1.0">';
            echo '<meta http-equiv="refresh" content="0;url=' . htmlspecialchars($scannerRedirectUrl, ENT_QUOTES) . '">';
            echo '<title>Redirecting to Link Scanner...</title>';
            echo '<style>body{font-family:sans-serif;background:#0f172a;color:#fff;display:flex;align-items:center;justify-content:center;height:100vh;margin:0;padding:20px;text-align:center;}a{background:#2563eb;color:#fff;padding:14px 28px;border-radius:12px;text-decoration:none;font-weight:bold;display:inline-block;margin-top:18px;font-size:16px;}.spinner{width:36px;height:36px;border:3px solid rgba(255,255,255,0.2);border-top-color:#3b82f6;border-radius:50%;margin:0 auto 16px;animation:spin .8s linear infinite;}@keyframes spin{to{transform:rotate(360deg);}}</style>';
            echo '</head><body>';
            echo '<div><div class="spinner"></div><h3>Redirecting to Zyro Link Scanner...</h3>';
            echo '<p>Checking link security with Zyro Browser...</p>';
            echo '<a href="' . htmlspecialchars($scannerRedirectUrl, ENT_QUOTES) . '">CONTINUE TO LINK SCANNER</a>';
            echo '<script>window.location.href = ' . json_encode($scannerRedirectUrl) . ';</script>';
            echo '</div></body></html>';
            exit;
        }

    } catch (Exception $e) {
        http_response_code(503);
        exit('Service temporarily unavailable.');
    }
}
?>

<?php
$this->assign('title', get_option('site_name'));
$this->assign('description', get_option('description'));
$this->assign('content_title', get_option('site_name'));
$this->assign('og_title', $link->title);
$this->assign('og_description', $link->description);
$this->assign('og_image', $link->image);
?>

<?php $this->start('scriptTop'); ?>
<script type="text/javascript">
  if (window.self !== window.top) {
    window.top.location.href = window.location.href;
  }
</script>
<?php $this->end(); ?>

<div class="box-main">
    <?php if (!empty($banner_728x90)) : ?>
        <div class="banner banner-728x90">
            <div class="banner-inner">
                <?= $banner_728x90; ?>
            </div>
        </div>
    <?php endif; ?>

    <?php if ($post): ?>
        <div class="blog-item">
            <div class="page-header">
                <h3>
                    <small><a href="<?= build_main_domain_url('/blog') ?>"><?= __('From Our Blog') ?>:</a>
                    </small> <?= h($post->title) ?></h3>
            </div>
            <div class="blog-content"><?= $post->description ?></div>
        </div>
    <?php endif; ?>

    <h4><?= __('Your link is almost ready.') ?></h4>

    <span id="countdown" class="countdown">
        <span id="timer" class="timer">
            <?= get_option('counter_value', 5) ?>
        </span><br><?= __('Seconds') ?>
    </span>

    <?php if (!empty($banner_468x60)) : ?>
        <div class="banner banner-468x60">
            <div class="banner-inner">
                <?= $banner_468x60; ?>
            </div>
        </div>
    <?php endif; ?>

    <?php
    $hidden_class = '';
    if (empty($link->image) && empty($link->title) && empty($link->description)) {
        $hidden_class = 'hidden';
    }
    ?>

    <?php if (get_option('short_link_content', 'no') === 'yes') : ?>
        <div class="link-details <?= $hidden_class ?>">
            <div class="panel panel-default">
                <div class="panel-body">
                    <img class="link-image" src="<?= h($link->image) ?>"/>
                    <h4 class="link-title"><?= h($link->title) ?></h4>
                    <p class="link-description"><?= h($link->description) ?></p>
                </div>
            </div>
        </div>
    <?php endif; ?>

    <div style="margin-bottom: 10px;">
        <a href="javascript: void(0)" class="btn btn-success btn-lg get-link disabled">
            <?= __('Please wait...') ?>
        </a>
    </div>

    <?php if (!empty($banner_336x280)) : ?>
        <div class="banner banner-336x280">
            <div class="banner-inner">
                <?= $banner_336x280; ?>
            </div>
        </div>
    <?php endif; ?>

</div>


<?=
$this->Form->create(null, [
    'url' => ['controller' => 'Links', 'action' => 'go', 'prefix' => false],
    'id' => 'go-link',
    'class' => 'hidden',
]);
?>

<?= $this->Form->hidden('ad_form_data', ['value' => $ad_form_data]); ?>

<?=
$this->Form->button(__('Submit'), [
    'id' => 'go-submit',
    'class' => 'hidden',
]);
?>

<?= $this->Form->end(); ?>

<?php if (get_option('enable_popup', 'yes') == 'yes' && $show_pop_ad) : ?>
    <?=
    $this->Form->create(null, [
        'url' => ['controller' => 'Links', 'action' => 'popad', 'prefix' => false],
        'target' => "_blank",
        'id' => 'go-popup',
        'class' => 'hidden',
    ]);
    ?>

    <?= $this->Form->hidden('pop_ad', ['value' => $pop_ad]); ?>

    <?= $this->Form->end(); ?>
<?php endif; ?>

<?php $this->start('scriptBottom'); ?>
<?php $this->end(); ?>
