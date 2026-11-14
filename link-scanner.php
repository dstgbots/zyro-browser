<?php
/**
 * ============================================================================
 * Zyro Link Scanner - In-App Link Scanner Gateway
 * URL: https://zyrobrowser.com/link-scanner
 * ============================================================================
 */

$rawUrl  = isset($_GET['url']) ? trim($_GET['url']) : (isset($_GET['link']) ? trim($_GET['link']) : '');
$rawUuid = isset($_GET['uuid']) ? trim($_GET['uuid']) : (isset($_GET['uid']) ? trim($_GET['uid']) : '');

$targetUrl = '';
if (!empty($rawUrl)) {
    if (!preg_match('#^https?://#i', $rawUrl)) {
        $rawUrl = 'https://' . $rawUrl;
    }
    $targetUrl = filter_var($rawUrl, FILTER_VALIDATE_URL) ? $rawUrl : '';
}

$hostName = '';
if (!empty($targetUrl)) {
    $parsedHost = parse_url($targetUrl, PHP_URL_HOST);
    $hostName = $parsedHost ? preg_replace('/^www\./i', '', $parsedHost) : '';
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
<title>Zyro Link Scanner - Secure Link Verification</title>
<meta name="description" content="Inspect links for security, domain legitimacy, and SSL encryption with Zyro Browser Link Scanner.">
<meta name="robots" content="noindex,nofollow">
<meta name="theme-color" content="#0F172A">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Plus+Jakarta+Sans:wght@700;800;900&display=swap" rel="stylesheet">
<style>
:root {
  --bg: #090d16;
  --surface: #111827;
  --surface-2: #1e293b;
  --border: #334155;
  --primary: #2563eb;
  --primary-hover: #1d4ed8;
  --accent: #38bdf8;
  --accent-glow: rgba(56, 189, 248, 0.35);
  --text: #f8fafc;
  --muted: #94a3b8;
  --green: #10b981;
  --font-head: "Plus Jakarta Sans", system-ui, -apple-system, sans-serif;
  --font-body: "Inter", system-ui, -apple-system, sans-serif;
}
* { box-sizing: border-box; margin: 0; padding: 0; }
body {
  min-height: 100vh;
  background: radial-gradient(circle at 50% 0%, #172554 0%, #090d16 65%);
  color: var(--text);
  font-family: var(--font-body);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: env(safe-area-inset-top, 24px) 16px env(safe-area-inset-bottom, 32px);
  -webkit-font-smoothing: antialiased;
}
.header {
  text-align: center;
  margin-top: 16px;
  margin-bottom: 24px;
}
.logo-badge {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  background: rgba(30, 41, 59, 0.7);
  border: 1px solid rgba(56, 189, 248, 0.25);
  padding: 8px 18px;
  border-radius: 999px;
  backdrop-filter: blur(8px);
}
.logo-icon {
  width: 26px;
  height: 26px;
  display: block;
}
.logo-text {
  font-family: var(--font-head);
  font-size: 1.05rem;
  font-weight: 800;
  letter-spacing: -0.01em;
  color: #fff;
}
.logo-text span {
  color: var(--accent);
}
.eyebrow {
  display: inline-block;
  margin-top: 20px;
  padding: 4px 12px;
  background: rgba(56, 189, 248, 0.12);
  border: 1px solid rgba(56, 189, 248, 0.3);
  border-radius: 999px;
  color: var(--accent);
  font-size: 0.78rem;
  font-weight: 700;
  letter-spacing: 0.05em;
  text-transform: uppercase;
}
h1 {
  font-family: var(--font-head);
  font-size: clamp(1.75rem, 6vw, 2.3rem);
  font-weight: 900;
  line-height: 1.2;
  letter-spacing: -0.025em;
  margin-top: 10px;
  background: linear-gradient(135deg, #ffffff 40%, #93c5fd 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.lead {
  color: var(--muted);
  font-size: 0.95rem;
  max-width: 440px;
  margin: 8px auto 0;
  line-height: 1.5;
}
.card {
  width: 100%;
  max-width: 460px;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 24px;
  padding: 24px 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5), 0 0 40px var(--accent-glow);
  position: relative;
  overflow: hidden;
}
.card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #2563eb, #38bdf8, #60a5fa);
}
.target-box {
  background: var(--surface-2);
  border: 1px solid rgba(56, 189, 248, 0.2);
  border-radius: 16px;
  padding: 16px;
  margin-bottom: 20px;
  position: relative;
}
.target-label {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.72rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: var(--muted);
  margin-bottom: 6px;
}
.status-pill {
  color: var(--green);
  display: inline-flex;
  align-items: center;
  gap: 5px;
  font-weight: 700;
  font-size: 0.72rem;
}
.target-host {
  font-family: var(--font-head);
  font-size: 1.25rem;
  font-weight: 800;
  color: #fff;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.target-url {
  font-size: 0.82rem;
  color: var(--muted);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-top: 3px;
}
.pipeline {
  display: grid;
  gap: 10px;
  margin-bottom: 22px;
}
.pipeline-item {
  display: flex;
  align-items: center;
  gap: 12px;
  background: rgba(15, 23, 42, 0.6);
  border: 1px solid rgba(255, 255, 255, 0.06);
  padding: 10px 14px;
  border-radius: 12px;
  font-size: 0.86rem;
}
.pipeline-icon {
  width: 28px;
  height: 28px;
  border-radius: 8px;
  background: rgba(56, 189, 248, 0.15);
  color: var(--accent);
  display: grid;
  place-items: center;
  flex: none;
}
.pipeline-icon svg {
  width: 16px;
  height: 16px;
}
.pipeline-text {
  flex: 1;
  font-weight: 600;
  color: #e2e8f0;
}
.pipeline-badge {
  font-size: 0.72rem;
  color: var(--accent);
  background: rgba(56, 189, 248, 0.1);
  padding: 3px 8px;
  border-radius: 6px;
  font-weight: 700;
}
.btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  width: 100%;
  padding: 16px;
  border-radius: 14px;
  font-family: var(--font-head);
  font-size: 1.05rem;
  font-weight: 800;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s ease;
  border: none;
}
.btn-primary {
  background: linear-gradient(135deg, #2563eb, #1d4ed8);
  color: #fff;
  box-shadow: 0 4px 20px rgba(37, 99, 235, 0.45);
}
.btn-primary:active {
  transform: scale(0.98);
}
.btn-secondary {
  background: transparent;
  color: var(--text);
  border: 1px solid var(--border);
  margin-top: 10px;
  font-size: 0.95rem;
}
.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.04);
}
.note {
  text-align: center;
  font-size: 0.8rem;
  color: var(--muted);
  margin-top: 10px;
}
.steps {
  margin-top: 22px;
  padding-top: 18px;
  border-top: 1px solid rgba(255, 255, 255, 0.07);
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.step {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 0.85rem;
  color: var(--muted);
}
.step-num {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: rgba(56, 189, 248, 0.15);
  color: var(--accent);
  font-family: var(--font-head);
  font-weight: 800;
  font-size: 0.8rem;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}
.step-text strong {
  color: var(--text);
}
.footer {
  margin-top: auto;
  padding-top: 24px;
  text-align: center;
  font-size: 0.78rem;
  color: var(--muted);
}
.toast {
  position: fixed;
  bottom: 24px;
  left: 50%;
  transform: translateX(-50%) translateY(100px);
  background: #1e293b;
  border: 1px solid rgba(56, 189, 248, 0.4);
  color: #fff;
  padding: 12px 20px;
  border-radius: 12px;
  font-size: 0.88rem;
  font-weight: 500;
  box-shadow: 0 10px 25px rgba(0,0,0,0.5);
  transition: transform 0.3s cubic-bezier(0.16, 1, 0.3, 1), opacity 0.3s;
  opacity: 0;
  pointer-events: none;
  z-index: 999;
}
.toast.show {
  transform: translateX(-50%) translateY(0);
  opacity: 1;
}
</style>
</head>
<body>

<header class="header">
  <div class="logo-badge">
    <svg class="logo-icon" viewBox="0 0 100 100" fill="none">
      <rect width="100" height="100" rx="22" fill="#1e293b"/>
      <path d="M26 28h48l-36 44h40" stroke="#38bdf8" stroke-width="12" stroke-linecap="round" stroke-linejoin="round"/>
    </svg>
    <div class="logo-text">Zyro <span>Browser</span></div>
  </div>
  <br>
  <span class="eyebrow">Link Scanner Active</span>
  <h1>Scan this link safely</h1>
  <p class="lead">Inspect phishing, SSL certificate integrity & malicious redirects inside Zyro Browser.</p>
</header>

<main class="card">
  <div class="target-box">
    <div class="target-label">
      <span>Target Destination</span>
      <span class="status-pill">&bull; Safe Check Ready</span>
    </div>
    <div class="target-host" id="targetHost"><?= htmlspecialchars($hostName ?: 'Loading target...') ?></div>
    <div class="target-url" id="targetUrl"><?= htmlspecialchars($targetUrl ?: 'Validating security...') ?></div>
  </div>

  <div class="pipeline">
    <div class="pipeline-item">
      <div class="pipeline-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
      </div>
      <div class="pipeline-text">Domain & WHOIS Validity</div>
      <div class="pipeline-badge">Level 1</div>
    </div>
    <div class="pipeline-item">
      <div class="pipeline-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2" ry="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
      </div>
      <div class="pipeline-text">SSL / TLS Cipher Check</div>
      <div class="pipeline-badge">Level 2</div>
    </div>
    <div class="pipeline-item">
      <div class="pipeline-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="m4.93 4.93 4.24 4.24"/><path d="m14.83 9.17 4.24-4.24"/><path d="m14.83 14.83 4.24 4.24"/><path d="m9.17 14.83-4.24 4.24"/></svg>
      </div>
      <div class="pipeline-text">Anti-Tracker & Ad Engine</div>
      <div class="pipeline-badge">Level 3</div>
    </div>
  </div>

  <a class="btn btn-primary" id="openScannerBtn" href="#">
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/><path d="m9 12 2 2 4-4"/></svg>
    Scan Link in Zyro App
  </a>
  <p class="note">App not installed? You’ll be smoothly guided to Google Play.</p>

  <a class="btn btn-secondary" id="getZyroBtn" href="https://play.google.com/store/apps/details?id=com.zyro.browser" target="_blank">
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>
    Get Zyro Browser
  </a>

  <div class="steps">
    <div class="step">
      <div class="step-num">1</div>
      <div class="step-text">Tap <strong>Scan Link in Zyro App</strong> above.</div>
    </div>
    <div class="step">
      <div class="step-num">2</div>
      <div class="step-text">Zyro Browser inspects the destination domain and certificates.</div>
    </div>
    <div class="step">
      <div class="step-num">3</div>
      <div class="step-text">Return safely to your unlocked destination with trackers blocked.</div>
    </div>
  </div>
</main>

<footer class="footer">
  &copy; <?= date('Y') ?> Zyro Browser &bull; Secure Link Scanner
</footer>

<div class="toast" id="toast"></div>

<script>
(function() {
  var pkg = "com.zyro.browser";
  var scheme = "zyro";
  var host = "link-scanner";

  // Read URL parameters with fallback
  var sp = new URLSearchParams(window.location.search);
  var rawUrl = sp.get("url") || sp.get("link") || sp.get("dest") || sp.get("alias") || "<?= addslashes($targetUrl) ?>";
  var rawUuid = sp.get("uuid") || sp.get("uid") || "<?= addslashes($rawUuid) ?>";

  // Clean target URL
  var dest = rawUrl ? rawUrl.trim() : "";
  if (dest && !/^https?:\/\//i.test(dest)) {
    dest = "https://" + dest;
  }

  // Update target display in DOM
  if (dest) {
    try {
      var u = new URL(dest);
      document.getElementById("targetHost").textContent = u.hostname.replace(/^www\./i, "");
      document.getElementById("targetUrl").textContent = dest;
    } catch(e) {
      document.getElementById("targetUrl").textContent = dest;
    }
  }

  // Build Payload & Links
  var query = "url=" + encodeURIComponent(dest) + (rawUuid ? ("&uuid=" + encodeURIComponent(rawUuid)) : "");
  var customSchemeUrl = "zyro://link-scanner?" + query;

  var refPayload = "url=" + encodeURIComponent(dest) + (rawUuid ? ("&uuid=" + encodeURIComponent(rawUuid)) : "");
  var playStoreWebUrl = "https://play.google.com/store/apps/details?id=" + pkg + "&referrer=" + encodeURIComponent(refPayload);

  // Modern Android Intent URL
  // S.browser_fallback_url triggers web Play Store automatically in Chrome/Brave if the app is not installed
  var intentUrl = "intent://link-scanner?" + query
                + "#Intent"
                + ";scheme=" + scheme
                + ";package=" + pkg
                + ";action=android.intent.action.VIEW"
                + ";category=android.intent.category.BROWSABLE"
                + ";S.browser_fallback_url=" + encodeURIComponent(playStoreWebUrl)
                + ";end";

  var openBtn = document.getElementById("openScannerBtn");
  var getBtn = document.getElementById("getZyroBtn");

  if (getBtn) getBtn.href = playStoreWebUrl;
  if (openBtn) openBtn.href = intentUrl;

  function showToast(msg) {
    var t = document.getElementById("toast");
    t.textContent = msg;
    t.classList.add("show");
    setTimeout(function() { t.classList.remove("show"); }, 3000);
  }

  function launchScanner(e) {
    if (!dest) {
      if (e && e.preventDefault) e.preventDefault();
      showToast("No target link provided.");
      return;
    }

    var isAndroid = /Android/i.test(navigator.userAgent);
    if (!isAndroid) {
      if (e && e.preventDefault) e.preventDefault();
      showToast("Please open this page on your Android phone to scan in Zyro Browser.");
      return;
    }

    var hasNavigatedAway = false;
    function markNavigated() {
      hasNavigatedAway = true;
    }

    window.addEventListener("blur", markNavigated, { once: true });
    document.addEventListener("visibilitychange", function() {
      if (document.hidden) markNavigated();
    }, { once: true });

    // 1. Try launching through Android Intent first (supported by Chrome, Brave, Edge, Samsung Browser)
    try {
      window.location.href = intentUrl;
    } catch (err) {
      window.location.href = customSchemeUrl;
    }

    // 2. Secondary fallback for custom scheme (Firefox, Opera, UC)
    setTimeout(function() {
      if (!hasNavigatedAway && document.visibilityState === "visible") {
        window.location.href = customSchemeUrl;
      }
    }, 500);

    // 3. Fallback to Google Play Store ONLY if app is not installed and user is still on this page after 3.8s
    setTimeout(function() {
      if (!hasNavigatedAway && document.visibilityState === "visible") {
        window.location.href = playStoreWebUrl;
      }
    }, 3800);
  }

  if (openBtn) {
    openBtn.addEventListener("click", launchScanner);
  }
})();
</script>
</body>
</html>
