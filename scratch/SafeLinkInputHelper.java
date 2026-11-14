package com.zyro.browser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;
import org.json.JSONObject;

public class SafeLinkInputHelper {

    private static final String TAG = "LinkScanner";
    public static final String SAFE_LINK_API_URL = "https://zyrobrowser.com/api.php";
    public static final String SAFE_LINK_APP_KEY = "b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f";

    // Prefetch token state
    public static volatile String sPrefetchTargetUrl = null;
    public static volatile String sPrefetchedDestinationUrl = null;
    public static volatile String sPrefetchedToken = null;
    public static volatile boolean sIsPrefetching = false;
    public static volatile boolean sPrefetchSuccess = false;
    public static volatile String sPrefetchError = null;

    // Active Banner View for cleanup
    private static volatile View sActiveSafeLinkBannerView = null;

    private static int dp(Context ctx, float dp) {
        if (ctx == null) return (int) dp;
        return (int) (dp * ctx.getResources().getDisplayMetrics().density + 0.5f);
    }

    private static int getId(View root, String name) {
        if (root == null || root.getContext() == null) return 0;
        return root.getResources().getIdentifier(name, "id", root.getContext().getPackageName());
    }

    public static boolean isManualMode(String targetUrl) {
        return targetUrl == null || targetUrl.trim().isEmpty() || "manual".equalsIgnoreCase(targetUrl.trim());
    }

    public static boolean isScannerUri(Uri uri) {
        if (uri == null) return false;
        try {
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();
            if (scheme != null) scheme = scheme.toLowerCase();
            if (host != null) host = host.toLowerCase();
            if (path != null) path = path.toLowerCase();

            if ("zyro".equals(scheme)) {
                if ("link-scanner".equals(host) || "linkscanner".equals(host) || "scan".equals(host) || "safelink".equals(host)) {
                    return true;
                }
            }
            if ("http".equals(scheme) || "https".equals(scheme)) {
                if (path != null && (path.contains("link-scanner") || path.contains("safelink"))) {
                    return true;
                }
                boolean hasTarget = hasQueryParam(uri, "url") || hasQueryParam(uri, "alias") || hasQueryParam(uri, "link") || hasQueryParam(uri, "dest");
                boolean hasUuid = hasQueryParam(uri, "uuid") || hasQueryParam(uri, "uid");
                if (hasTarget && hasUuid) {
                    return true;
                }
            }
        } catch (Throwable t) {
            Log.e(TAG, "isScannerUri error: " + t.getMessage());
        }
        return false;
    }

    private static boolean hasQueryParam(Uri uri, String key) {
        try {
            String val = uri.getQueryParameter(key);
            return val != null && !val.trim().isEmpty();
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static String extractScannerTarget(Uri uri) {
        if (uri == null) return "https://zyrobrowser.com";
        String target = uri.getQueryParameter("url");
        if (target == null || target.trim().isEmpty()) {
            target = uri.getQueryParameter("alias");
        }
        if (target == null || target.trim().isEmpty()) {
            target = uri.getQueryParameter("link");
        }
        if (target == null || target.trim().isEmpty()) {
            target = uri.getQueryParameter("dest");
        }
        String destParam = uri.getQueryParameter("dest");
        String apiParam = uri.getQueryParameter("api");
        return resolveFullTargetUrl(target, destParam, apiParam);
    }

    public static String extractScannerUuid(Uri uri) {
        if (uri == null) return null;
        String uuid = uri.getQueryParameter("uuid");
        if (uuid == null || uuid.trim().isEmpty()) {
            uuid = uri.getQueryParameter("uid");
        }
        return (uuid != null) ? uuid.trim() : null;
    }

    public static String getReferrerParam(String referrer, String... keys) {
        if (referrer == null || referrer.isEmpty()) return null;
        for (String key : keys) {
            String k = key + "=";
            int idx = referrer.indexOf(k);
            if (idx != -1) {
                int start = idx + k.length();
                int end = referrer.indexOf('&', start);
                if (end == -1) end = referrer.length();
                try {
                    return java.net.URLDecoder.decode(referrer.substring(start, end), "UTF-8").trim();
                } catch (Throwable ignored) {
                    return referrer.substring(start, end).trim();
                }
            }
        }
        return null;
    }

    public static String cleanTargetUrl(String url) {
        if (url == null) return "";
        String s = url.trim();
        while (s.matches("^https?://[^/]+/https?://.*")) {
            int secondIdx = s.indexOf("://", 8);
            if (secondIdx != -1) {
                int proto = s.lastIndexOf("http", secondIdx);
                if (proto != -1) {
                    s = s.substring(proto);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return s;
    }

    public static String resolveFullTargetUrl(String alias, String dest, String api) {
        String url = null;
        if (dest != null && !dest.trim().isEmpty()) {
            url = dest.trim();
        } else if (alias != null && (alias.trim().startsWith("http://") || alias.trim().startsWith("https://"))) {
            url = alias.trim();
        } else if (alias != null && !alias.trim().isEmpty()) {
            String cleanAlias = alias.trim();
            if (api != null && (api.startsWith("http://") || api.startsWith("https://"))) {
                try {
                    int lastSlash = api.lastIndexOf('/');
                    if (lastSlash > 8) {
                        url = api.substring(0, lastSlash) + "/" + cleanAlias;
                    }
                } catch (Throwable ignored) {}
            }
            if (url == null) {
                url = "https://ezyshort.xyz/" + cleanAlias;
            }
        }

        if (url == null || url.isEmpty()) {
            return "https://zyrobrowser.com";
        }

        url = cleanTargetUrl(url);

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        return url;
    }

    public static String getFinalUrlWithToken(String alias, String baseUrl, String token) {
        if (sPrefetchSuccess && sPrefetchedDestinationUrl != null && !sPrefetchedDestinationUrl.isEmpty()) {
            return sPrefetchedDestinationUrl;
        }
        String cleanAlias = (alias != null) ? cleanTargetUrl(alias.trim()) : "";
        String target = cleanAlias;
        if (!target.startsWith("http://") && !target.startsWith("https://")) {
            String base = (baseUrl != null && !baseUrl.isEmpty()) ? baseUrl : "https://ezyshort.xyz";
            if (!base.endsWith("/")) base = base + "/";
            target = base + target;
        }
        if (token != null && !token.isEmpty()) {
            if (target.contains("?")) {
                return target + "&token=" + token;
            } else {
                return target + "?token=" + token;
            }
        }
        return target;
    }

    public static String getPersistentDeviceUuid(Context ctx) {
        if (ctx == null) return "8caa17c2-9f3d-5580-e2f0-b5596fcf65f8";
        try {
            SharedPreferences sp = ctx.getSharedPreferences("zyro_browser_prefs", Context.MODE_PRIVATE);
            String uuid = sp.getString("safe_link_device_uuid", null);
            if (uuid == null || uuid.trim().isEmpty()) {
                uuid = UUID.randomUUID().toString();
                sp.edit().putString("safe_link_device_uuid", uuid).apply();
            }
            return uuid;
        } catch (Throwable t) {
            return "8caa17c2-9f3d-5580-e2f0-b5596fcf65f8";
        }
    }

    public static void prefetchToken(final Activity activity, final String targetUrl) {
        if (activity == null || targetUrl == null || targetUrl.trim().isEmpty()) {
            return;
        }
        String cleanUrl = targetUrl.trim();
        if ("manual".equalsIgnoreCase(cleanUrl)) {
            return;
        }

        // Obtain Alias and UUID from activity fields if available
        String resolvedAlias = cleanUrl;
        try {
            Field f = activity.getClass().getDeclaredField("currentSafeLinkAlias");
            f.setAccessible(true);
            String actAlias = (String) f.get(activity);
            if (actAlias != null && !actAlias.trim().isEmpty() && !isManualMode(actAlias)) {
                resolvedAlias = actAlias.trim();
            }
        } catch (Throwable ignored) {}

        resolvedAlias = cleanTargetUrl(resolvedAlias);
        if (!resolvedAlias.startsWith("http://") && !resolvedAlias.startsWith("https://")) {
            resolvedAlias = "https://" + resolvedAlias;
        }

        sPrefetchTargetUrl = resolvedAlias;
        sPrefetchedDestinationUrl = null;
        sPrefetchedToken = null;
        sIsPrefetching = true;
        sPrefetchSuccess = false;
        sPrefetchError = null;

        // Obtain UUID
        String resolvedUuid = null;
        try {
            Field f = activity.getClass().getDeclaredField("currentSafeLinkUuid");
            f.setAccessible(true);
            resolvedUuid = (String) f.get(activity);
        } catch (Throwable ignored) {}

        if (resolvedUuid == null || resolvedUuid.trim().isEmpty()) {
            resolvedUuid = getPersistentDeviceUuid(activity);
        }
        final String uuid = resolvedUuid;
        final String alias = resolvedAlias;

        Log.i(TAG, "Starting Link Scanner Token prefetch: alias=" + alias + ", uuid=" + uuid);

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    String query = "?alias=" + URLEncoder.encode(alias, "UTF-8")
                            + "&url=" + URLEncoder.encode(alias, "UTF-8")
                            + "&uuid=" + URLEncoder.encode(uuid, "UTF-8");
                    URL url = new URL(SAFE_LINK_API_URL + query);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("X-App-Key", SAFE_LINK_APP_KEY);
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 ZyroBrowser/1.0");
                    conn.setConnectTimeout(15000);
                    conn.setReadTimeout(15000);

                    int responseCode = conn.getResponseCode();
                    InputStream is = (responseCode >= 200 && responseCode < 300)
                            ? conn.getInputStream() : conn.getErrorStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();

                    String responseText = sb.toString();
                    Log.i(TAG, "Link Scanner API Response: " + responseText);

                    JSONObject json = new JSONObject(responseText);
                    if (json.optBoolean("success")) {
                        String token = json.optString("token", "");
                        String finalDest = null;
                        if (json.has("destination") && !json.isNull("destination")) {
                            finalDest = json.getString("destination");
                        } else if (json.has("url") && !json.isNull("url")) {
                            finalDest = json.getString("url");
                        } else if (!token.isEmpty()) {
                            if (alias.contains("?")) {
                                finalDest = alias + "&token=" + URLEncoder.encode(token, "UTF-8");
                            } else {
                                finalDest = alias + "?token=" + URLEncoder.encode(token, "UTF-8");
                            }
                        } else {
                            finalDest = alias;
                        }
                        sPrefetchedToken = token;
                        sPrefetchedDestinationUrl = finalDest;
                        sPrefetchSuccess = true;
                        Log.i(TAG, "Link Scanner Token obtained: " + token + " -> " + finalDest);
                    } else {
                        sPrefetchError = json.optString("error", "Gate verification error");
                        sPrefetchSuccess = false;
                        Log.w(TAG, "Link Scanner API returned non-success: " + sPrefetchError);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                    sPrefetchError = t.getMessage();
                    sPrefetchSuccess = false;
                    Log.e(TAG, "Link Scanner API fetch exception: " + t.getMessage());
                } finally {
                    if (conn != null) {
                        try { conn.disconnect(); } catch (Throwable ignored) {}
                    }
                    sIsPrefetching = false;
                }
            }
        }).start();
    }

    public static void setupSafeLinkMode(final Activity activity, final View root, final String targetUrl) {
        if (activity == null || root == null) return;

        try {
            int urlId = getId(root, "tv_target_url");
            View urlView = root.findViewById(urlId);
            if (!(urlView instanceof EditText)) {
                return;
            }
            final EditText et = (EditText) urlView;

            int btnId = getId(root, "btn_safelink_continue");
            final View btnView = root.findViewById(btnId);
            final TextView btn = (btnView instanceof TextView) ? (TextView) btnView : null;

            int pingId = getId(root, "tv_ping");
            final TextView tvPing = (TextView) root.findViewById(pingId);

            int headlineId = getId(root, "tv_safelink_headline");
            final TextView tvHeadline = (TextView) root.findViewById(headlineId);

            int pbId = getId(root, "pb_safelink_global");
            final ProgressBar pb = (ProgressBar) root.findViewById(pbId);

            int shieldId = getId(root, "iv_radar_shield");
            final ImageView ivShield = (ImageView) root.findViewById(shieldId);

            int containerId = getId(root, "safelink_steps_container");
            final LinearLayout stepsContainer = (LinearLayout) root.findViewById(containerId);

            // Subtitle inside bg_url_box (sibling of EditText)
            TextView subtitleView = null;
            if (et.getParent() instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) et.getParent();
                if (parent.getChildCount() > 1 && parent.getChildAt(1) instanceof TextView) {
                    subtitleView = (TextView) parent.getChildAt(1);
                }
            }
            final TextView subtitle = subtitleView;

            final boolean isDark = SafeLinkAnimationHelper.sIsDarkTheme;
            int textColor = isDark ? 0xFF60A5FA : 0xFF0F172A;
            int hintColor = isDark ? 0xFF64748B : 0xFF94A3B8;
            et.setTextColor(textColor);
            et.setHintTextColor(hintColor);

            // Setup middle banner ad card powered by Admin App settings
            setupMiddleBannerAd(activity, root, isDark);

            boolean manual = isManualMode(targetUrl);

            if (manual) {
                // MANUAL MODE (From Menu): Empty & Editable!
                et.setText("");
                et.setHint("Enter or paste link to verify...");
                et.setFocusable(true);
                et.setFocusableInTouchMode(true);
                et.setClickable(true);
                et.setLongClickable(true);
                et.setCursorVisible(true);
                et.setEllipsize(null);
                et.setSingleLine(true);
                et.setKeyListener(TextKeyListener.getInstance());

                et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        try {
                            if (et.getParent() instanceof View) {
                                GradientDrawable gd = new GradientDrawable();
                                gd.setShape(GradientDrawable.RECTANGLE);
                                gd.setColor(SafeLinkAnimationHelper.sIsDarkTheme ? 0xFF0F172A : 0xFFF8FAFC);
                                gd.setCornerRadius(dp(root.getContext(), 12f));
                                int strokeColor = hasFocus
                                        ? (SafeLinkAnimationHelper.sIsDarkTheme ? 0xFF3B82F6 : 0xFF2563EB)
                                        : (SafeLinkAnimationHelper.sIsDarkTheme ? 0xFF1E293B : 0xFFE2E8F0);
                                gd.setStroke(dp(root.getContext(), hasFocus ? 2f : 1f), strokeColor);
                                ((View) et.getParent()).setBackground(gd);
                            }
                        } catch (Throwable ignored) {}
                    }
                });

                if (tvPing != null) {
                    tvPing.setText("\u25cf MANUAL SCAN");
                }
                if (subtitle != null) {
                    subtitle.setText("Enter any URL to test threat & security levels");
                }
                if (tvHeadline != null) {
                    tvHeadline.setText("Link Scanner");
                }
                if (pb != null) {
                    pb.setVisibility(View.INVISIBLE);
                }

                // Reset all step views to pending badge numbers (1..4)
                if (stepsContainer != null) {
                    for (int i = 0; i < stepsContainer.getChildCount(); i++) {
                        View stepItem = stepsContainer.getChildAt(i);
                        SafeLinkAnimationHelper.initStepView(stepItem, i);
                    }
                }

                if (btn != null) {
                    btn.setEnabled(true);
                    btn.setAlpha(1.0f);
                    btn.setText("Start Security Scan \u2192");
                    applyOrangeButtonStyle(btn, root.getContext());

                    final Runnable startScanRunnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String input = et.getText() != null ? et.getText().toString().trim() : "";
                                if (TextUtils.isEmpty(input)) {
                                    Toast.makeText(activity, "Please enter a URL to verify", Toast.LENGTH_SHORT).show();
                                    et.requestFocus();
                                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (imm != null) {
                                        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                                    }
                                    return;
                                }

                                if (!input.startsWith("http://") && !input.startsWith("https://")) {
                                    input = "https://" + input;
                                    et.setText(input);
                                }

                                // Hide keyboard
                                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                                if (imm != null) {
                                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                                }

                                // Lock the field so user cannot edit during scan
                                et.clearFocus();
                                et.setFocusable(false);
                                et.setFocusableInTouchMode(false);
                                et.setClickable(false);
                                et.setLongClickable(false);
                                et.setCursorVisible(false);
                                et.setKeyListener(null);
                                et.setEllipsize(TextUtils.TruncateAt.END);

                                if (et.getParent() instanceof View) {
                                    GradientDrawable gd = new GradientDrawable();
                                    gd.setShape(GradientDrawable.RECTANGLE);
                                    gd.setColor(SafeLinkAnimationHelper.sIsDarkTheme ? 0xFF0F172A : 0xFFF8FAFC);
                                    gd.setCornerRadius(dp(root.getContext(), 12f));
                                    int strokeColor = SafeLinkAnimationHelper.sIsDarkTheme ? 0xFF1E293B : 0xFFE2E8F0;
                                    gd.setStroke(dp(root.getContext(), 1f), strokeColor);
                                    ((View) et.getParent()).setBackground(gd);
                                }

                                if (tvPing != null) {
                                    tvPing.setText("\u25cf SCANNING TARGET");
                                }
                                if (subtitle != null) {
                                    subtitle.setText("Host: Validating \u2022 TLS Handshake");
                                }
                                if (tvHeadline != null) {
                                    tvHeadline.setText("Link Scanner");
                                }
                                if (pb != null) {
                                    pb.setVisibility(View.VISIBLE);
                                }

                                btn.setEnabled(false);
                                btn.setAlpha(0.6f);
                                btn.setText("Validating...");

                                // Set step 0 active
                                if (stepsContainer != null && stepsContainer.getChildCount() > 0) {
                                    View step0 = stepsContainer.getChildAt(0);
                                    SafeLinkAnimationHelper.setStepActive(step0, 0);
                                }

                                // Start shield pulse
                                if (ivShield != null) {
                                    SafeLinkAnimationHelper.startShieldPulse(ivShield);
                                }

                                // PREFETCH TOKEN RIGHT AT START!
                                prefetchToken(activity, input);

                                // Launch progress on MainActivity safely
                                startProgressOnActivity(activity, 0, input);
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    };

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startScanRunnable.run();
                        }
                    });

                    et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_GO ||
                                actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_SEND ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                actionId == EditorInfo.IME_NULL ||
                                (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == 160))) {
                                startScanRunnable.run();
                                return true;
                            }
                            return false;
                        }
                    });
                }

            } else {
                // FIXED / LOCKED MODE (From deep link / our domain)
                String displayUrl = targetUrl;
                if (displayUrl != null && displayUrl.startsWith("https://ezyshort.xyz/http")) {
                    displayUrl = displayUrl.substring("https://ezyshort.xyz/".length());
                }

                et.setText(displayUrl);
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
                et.setClickable(false);
                et.setLongClickable(false);
                et.setCursorVisible(false);
                et.setKeyListener(null);
                et.setEllipsize(TextUtils.TruncateAt.END);

                if (tvPing != null) {
                    tvPing.setText("\u25cf OFFICIAL & AUTHENTIC");
                }
                if (subtitle != null) {
                    subtitle.setText("Host: edge.cloudflare.net \u2022 TLS 1.3 Validated");
                }
                if (tvHeadline != null) {
                    tvHeadline.setText("Link Scanner");
                }
                if (pb != null) {
                    pb.setVisibility(View.VISIBLE);
                }
                if (btn != null) {
                    btn.setEnabled(false);
                    btn.setAlpha(0.6f);
                    btn.setText("Validating...");
                    applyOrangeButtonStyle(btn, root.getContext());
                }

                // PREFETCH TOKEN RIGHT AT START!
                prefetchToken(activity, displayUrl);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static Object getMainActivityField(Activity activity, String fieldName) {
        if (activity == null) return null;
        try {
            Field f = activity.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(activity);
        } catch (Throwable t) {
            return null;
        }
    }

    private static boolean getBooleanGetter(Object obj, String methodName, boolean def) {
        if (obj == null) return def;
        try {
            Method m = obj.getClass().getMethod(methodName);
            Object res = m.invoke(obj);
            if (res instanceof Boolean) return (Boolean) res;
        } catch (Throwable ignored) {}
        return def;
    }

    private static String getStringGetter(Object obj, String methodName, String def) {
        if (obj == null) return def;
        try {
            Method m = obj.getClass().getMethod(methodName);
            Object res = m.invoke(obj);
            if (res != null) return res.toString();
        } catch (Throwable ignored) {}
        return def;
    }

    public static void destroyBannerAd() {
        if (sActiveSafeLinkBannerView != null) {
            try {
                Method destroyMethod = sActiveSafeLinkBannerView.getClass().getMethod("destroy");
                destroyMethod.invoke(sActiveSafeLinkBannerView);
            } catch (Throwable ignored) {}
            sActiveSafeLinkBannerView = null;
        }
    }

    private static View createGamBanner(Activity activity, String adUnitId) {
        try {
            Class<?> viewClass = Class.forName("com.google.android.gms.ads.admanager.AdManagerAdView");
            Object view = viewClass.getConstructor(Context.class).newInstance(activity);
            Method setUnit = viewClass.getMethod("setAdUnitId", String.class);
            String unit = (adUnitId != null && !adUnitId.trim().isEmpty()) ? adUnitId.trim() : "/21775744923/example/banner";
            setUnit.invoke(view, unit);

            Class<?> sizeClass = Class.forName("com.google.android.gms.ads.AdSize");
            Object rectSize = null;
            try {
                Field f = sizeClass.getField("MEDIUM_RECTANGLE");
                rectSize = f.get(null);
            } catch (Throwable t) {
                rectSize = sizeClass.getConstructor(int.class, int.class).newInstance(300, 250);
            }

            Object sizesArray = java.lang.reflect.Array.newInstance(sizeClass, 1);
            java.lang.reflect.Array.set(sizesArray, 0, rectSize);

            try {
                Method setSizesMethod = viewClass.getMethod("setAdSizes", sizesArray.getClass());
                setSizesMethod.invoke(view, sizesArray);
            } catch (Throwable tArray) {
                Method setSizeMethod = viewClass.getMethod("setAdSize", sizeClass);
                setSizeMethod.invoke(view, rectSize);
            }

            Class<?> reqBuilderClass = Class.forName("com.google.android.gms.ads.admanager.AdManagerAdRequest$Builder");
            Object reqBuilder = reqBuilderClass.newInstance();
            Method build = reqBuilderClass.getMethod("build");
            Object req = build.invoke(reqBuilder);

            Method loadAd = viewClass.getMethod("loadAd", Class.forName("com.google.android.gms.ads.admanager.AdManagerAdRequest"));
            loadAd.invoke(view, req);
            return (View) view;
        } catch (Throwable t) {
            Log.e(TAG, "Error creating GAM 300x250 banner directly: " + t.getMessage(), t);
            return null;
        }
    }

    private static View createAdMobBanner(Activity activity, String adUnitId) {
        try {
            Class<?> viewClass = Class.forName("com.google.android.gms.ads.AdView");
            Object view = viewClass.getConstructor(Context.class).newInstance(activity);
            Method setUnit = viewClass.getMethod("setAdUnitId", String.class);
            String unit = (adUnitId != null && !adUnitId.trim().isEmpty()) ? adUnitId.trim() : "ca-app-pub-3940256099942544/6300978111";
            setUnit.invoke(view, unit);

            Class<?> sizeClass = Class.forName("com.google.android.gms.ads.AdSize");
            Object rectSize = null;
            try {
                Field f = sizeClass.getField("MEDIUM_RECTANGLE");
                rectSize = f.get(null);
            } catch (Throwable t) {
                rectSize = sizeClass.getConstructor(int.class, int.class).newInstance(300, 250);
            }

            Method setAdSize = viewClass.getMethod("setAdSize", sizeClass);
            setAdSize.invoke(view, rectSize);

            Class<?> reqBuilderClass = Class.forName("com.google.android.gms.ads.AdRequest$Builder");
            Object reqBuilder = reqBuilderClass.newInstance();
            Method build = reqBuilderClass.getMethod("build");
            Object req = build.invoke(reqBuilder);

            Method loadAd = viewClass.getMethod("loadAd", Class.forName("com.google.android.gms.ads.AdRequest"));
            loadAd.invoke(view, req);
            return (View) view;
        } catch (Throwable t) {
            Log.e(TAG, "Error creating AdMob 300x250 AdView: " + t.getMessage(), t);
            return null;
        }
    }

    private static View createMetaBanner(Activity activity, String placementId) {
        try {
            Class<?> viewClass = Class.forName("com.facebook.ads.AdView");
            Class<?> sizeClass = Class.forName("com.facebook.ads.AdSize");
            Field rectSizeField = sizeClass.getField("RECTANGLE_HEIGHT_250");
            Object rectSize = rectSizeField.get(null);

            String placement = (placementId != null && !placementId.trim().isEmpty()) ? placementId.trim() : "IMG_16_9_LINK#YOUR_PLACEMENT_ID";
            Object view = viewClass.getConstructor(Context.class, String.class, sizeClass)
                    .newInstance(activity, placement, rectSize);

            Method loadAd = viewClass.getMethod("loadAd");
            loadAd.invoke(view);
            return (View) view;
        } catch (Throwable t) {
            Log.e(TAG, "Error creating Meta 300x250 AdView directly: " + t.getMessage(), t);
            return null;
        }
    }

    private static void setupMiddleBannerAd(final Activity activity, final View root, final boolean isDark) {
        if (activity == null || root == null) return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    int adRootId = getId(root, "custom_ad_root");
                    final View adCard = root.findViewById(adRootId);
                    if (adCard == null) return;

                    int slotId = getId(root, "admob_view_slot");
                    final FrameLayout admobSlot = (FrameLayout) root.findViewById(slotId);
                    if (admobSlot == null) return;

                    // Card background styling
                    GradientDrawable cardBg = new GradientDrawable();
                    cardBg.setShape(GradientDrawable.RECTANGLE);
                    cardBg.setCornerRadius(dp(root.getContext(), 16f));
                    cardBg.setColor(isDark ? 0xFF1E2230 : 0xFFFFFFFF);
                    cardBg.setStroke(dp(root.getContext(), 1f), isDark ? 0xFF2D3748 : 0xFFE2E8F0);
                    adCard.setBackground(cardBg);

                    // Badge styling
                    int badgeId = getId(root, "custom_ad_badge");
                    TextView tvBadge = (TextView) root.findViewById(badgeId);
                    if (tvBadge != null) {
                        GradientDrawable badgeBg = new GradientDrawable();
                        badgeBg.setShape(GradientDrawable.RECTANGLE);
                        badgeBg.setCornerRadius(dp(root.getContext(), 6f));
                        badgeBg.setColor(0xFF6366F1);
                        tvBadge.setBackground(badgeBg);
                        tvBadge.setTextColor(0xFFFFFFFF);
                    }

                    int titleId = getId(root, "ad_headline");
                    TextView tvTitle = (TextView) root.findViewById(titleId);
                    if (tvTitle != null) {
                        tvTitle.setText("");
                    }

                    // Clean previous banner
                    destroyBannerAd();
                    admobSlot.removeAllViews();

                    // Read config from MainActivity
                    Object adNetworkObj = getMainActivityField(activity, "adNetworkConfig");
                    Object googleAdsObj = getMainActivityField(activity, "googleAdsConfig");
                    Object gamObj = getMainActivityField(activity, "gamConfig");
                    Object metaAdsObj = getMainActivityField(activity, "metaAdsConfig");

                    // 1. Check Global Ads switch from Admin App
                    boolean globalAdsEnabled = getBooleanGetter(adNetworkObj, "getGlobalAdsEnabled", true);
                    if (!globalAdsEnabled) {
                        Log.i(TAG, "Banner Ad: Global ads turned OFF in admin. Hiding banner card.");
                        adCard.setVisibility(View.GONE);
                        return;
                    }

                    // 2. Active platform from Admin App
                    String activePlatform = getStringGetter(adNetworkObj, "getActivePlatform", "GOOGLE_AD_MANAGER");
                    Log.i(TAG, "Banner Ad: Active platform from Admin App = " + activePlatform);

                    View bannerView = null;

                    if ("GOOGLE_ADMOB".equalsIgnoreCase(activePlatform)) {
                        // Check if AdMob enabled
                        boolean adsEnabled = getBooleanGetter(googleAdsObj, "getAdsEnabled", true);
                        boolean safelinkEnabled = getBooleanGetter(googleAdsObj, "getSafelinkGoogleAdsEnabled", true);
                        boolean testMode = getBooleanGetter(googleAdsObj, "getTestMode", true);
                        String unitId = getStringGetter(googleAdsObj, "getBannerAdUnitId", "ca-app-pub-3940256099942544/6300978111");
                        if (testMode || TextUtils.isEmpty(unitId)) {
                            unitId = "ca-app-pub-3940256099942544/6300978111";
                        }

                        if (adsEnabled && safelinkEnabled) {
                            Log.i(TAG, "Loading Google AdMob 300x250 banner with unit: " + unitId);
                            bannerView = createAdMobBanner(activity, unitId);
                        } else {
                            Log.i(TAG, "Google AdMob disabled in admin. Falling back to default Google Ad Manager.");
                        }
                    } else if ("META_AUDIENCE_NETWORK".equalsIgnoreCase(activePlatform)) {
                        // Check if Meta enabled
                        boolean metaEnabled = getBooleanGetter(metaAdsObj, "getEnabled", true);
                        boolean testMode = getBooleanGetter(metaAdsObj, "getTestMode", true);
                        String placementId = getStringGetter(metaAdsObj, "getBannerPlacementId", "IMG_16_9_LINK#YOUR_PLACEMENT_ID");
                        if (testMode && !placementId.contains("#")) {
                            placementId = "IMG_16_9_LINK#" + placementId;
                        }

                        if (metaEnabled) {
                            Log.i(TAG, "Loading Meta Audience Network 300x250 banner with placement: " + placementId);
                            bannerView = createMetaBanner(activity, placementId);
                        } else {
                            Log.i(TAG, "Meta Ads disabled in admin. Falling back to default Google Ad Manager.");
                        }
                    }

                    // Default Fallback: If not loaded yet (or if platform is APPLOVIN_MAX or GAM or fallback)
                    if (bannerView == null) {
                        boolean gamEnabled = getBooleanGetter(gamObj, "getAdsEnabled", true);
                        boolean gamSafelink = getBooleanGetter(gamObj, "getSafelinkGamAdsEnabled", true);
                        boolean testMode = getBooleanGetter(gamObj, "getTestMode", true);
                        String bannerUnitId = getStringGetter(gamObj, "getBannerAdUnitId", "/21775744923/example/banner");
                        if (testMode || TextUtils.isEmpty(bannerUnitId)) {
                            bannerUnitId = "/21775744923/example/banner";
                        }

                        if (gamEnabled && gamSafelink) {
                            Log.i(TAG, "Loading default Google Ad Manager 300x250 banner with unit: " + bannerUnitId);
                            bannerView = createGamBanner(activity, bannerUnitId);
                        } else {
                            Log.i(TAG, "Google Ad Manager disabled in admin. Hiding banner card.");
                            adCard.setVisibility(View.GONE);
                            return;
                        }
                    }

                    if (bannerView != null) {
                        sActiveSafeLinkBannerView = bannerView;
                        admobSlot.removeAllViews();
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                                dp(activity, 300f),
                                dp(activity, 250f)
                        );
                        lp.gravity = Gravity.CENTER;
                        admobSlot.addView(bannerView, lp);
                        adCard.setVisibility(View.VISIBLE);
                    } else {
                        adCard.setVisibility(View.GONE);
                    }

                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    private static void applyOrangeButtonStyle(TextView btn, Context ctx) {
        if (btn == null) return;
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[] { 0xFFFB542B, 0xFFEA580C }
        );
        gd.setCornerRadius(dp(ctx, 14f));
        btn.setBackground(gd);
        btn.setTextColor(0xFFFFFFFF);
    }

    public static void onStepStarting(final Activity activity, final int stepIndex) {
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    View root = activity.findViewById(android.R.id.content);
                    int btnId = getId(root, "btn_safelink_continue");
                    View btnView = (root != null) ? root.findViewById(btnId) : null;
                    if (btnView instanceof TextView) {
                        TextView btn = (TextView) btnView;
                        btn.setEnabled(false);
                        btn.setAlpha(0.6f);
                        btn.setText("Validating...");
                        applyOrangeButtonStyle(btn, activity);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    public static void onStepProgressFinished(final Activity activity, final int stepIndex, final String targetUrl) {
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    View root = activity.findViewById(android.R.id.content);
                    if (root == null) return;

                    // 1. Mark completed step as verified in container
                    int containerId = getId(root, "safelink_steps_container");
                    LinearLayout stepsContainer = (LinearLayout) root.findViewById(containerId);
                    if (stepsContainer != null && stepIndex < stepsContainer.getChildCount()) {
                        View stepItem = stepsContainer.getChildAt(stepIndex);
                        SafeLinkAnimationHelper.setStepPassed(stepItem);
                    }

                    int totalSteps = 4;
                    final int nextStepIndex = stepIndex + 1;

                    int btnId = getId(root, "btn_safelink_continue");
                    final TextView btn = (TextView) root.findViewById(btnId);

                    if (nextStepIndex >= totalSteps) {
                        // ALL 4 CHECKS COMPLETED!
                        if (btn != null) {
                            btn.setEnabled(true);
                            btn.setAlpha(1.0f);
                            btn.setText("Access Verified Destination \u2192");

                            // Vibrant emerald green success button
                            GradientDrawable greenGd = new GradientDrawable(
                                    GradientDrawable.Orientation.TL_BR,
                                    new int[] { 0xFF059669, 0xFF047857 }
                            );
                            greenGd.setCornerRadius(dp(activity, 14f));
                            btn.setBackground(greenGd);
                            btn.setTextColor(0xFFFFFFFF);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    handleAccessDestination(activity, targetUrl, btn);
                                }
                            });
                        }

                        int headlineId = getId(root, "tv_safelink_headline");
                        TextView tvHeadline = (TextView) root.findViewById(headlineId);
                        if (tvHeadline != null) {
                            tvHeadline.setText("Safety Verified \u2022 Target Clean");
                        }

                        int threatId = getId(root, "tv_threat_level");
                        TextView tvThreat = (TextView) root.findViewById(threatId);
                        if (tvThreat != null) {
                            tvThreat.setText("\u25cf 100% SECURE & VERIFIED");
                        }

                        int pbId = getId(root, "pb_safelink_global");
                        View pb = root.findViewById(pbId);
                        if (pb != null) {
                            pb.setVisibility(View.GONE);
                        }

                    } else {
                        // NEXT STEP IS PENDING: USER MUST MANUALLY CLICK!
                        final String nextStepName = getStepName(nextStepIndex);

                        // Highlight next step row in UI
                        if (stepsContainer != null && nextStepIndex < stepsContainer.getChildCount()) {
                            View nextStepItem = stepsContainer.getChildAt(nextStepIndex);
                            SafeLinkAnimationHelper.setStepActive(nextStepItem, nextStepIndex);
                        }

                        if (btn != null) {
                            btn.setEnabled(true);
                            btn.setAlpha(1.0f);
                            btn.setText("Start: " + nextStepName);
                            applyOrangeButtonStyle(btn, activity);

                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showWatchVideoDialog(activity, stepIndex, nextStepName, targetUrl);
                                }
                            });
                        }
                    }

                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    private static String getStepName(int stepIndex) {
        switch (stepIndex) {
            case 1:
                return "SSL Certificate Checker";
            case 2:
                return "Redirection Checker";
            case 3:
                return "Threat Intelligence Scan";
            default:
                return "Security Check";
        }
    }

    public static void showWatchVideoDialog(final Activity activity, final int currentStepIndex, final String nextStepName, final String targetUrl) {
        if (activity == null || activity.isFinishing()) return;

        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            // Create custom dialog layout matching Image 3
            LinearLayout dialogCard = new LinearLayout(activity);
            dialogCard.setOrientation(LinearLayout.VERTICAL);
            int pad = dp(activity, 22f);
            dialogCard.setPadding(pad, pad, pad, pad);

            GradientDrawable bg = new GradientDrawable();
            bg.setShape(GradientDrawable.RECTANGLE);
            bg.setCornerRadius(dp(activity, 20f));
            bg.setColor(0xFF1E2230); // Dark Slate background
            bg.setStroke(dp(activity, 1f), 0xFF2D3748);
            dialogCard.setBackground(bg);

            // Dialog Title
            TextView tvTitle = new TextView(activity);
            tvTitle.setText("Watch a video to continue?");
            tvTitle.setTextSize(18f);
            tvTitle.setTextColor(0xFFFFFFFF);
            tvTitle.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
            dialogCard.addView(tvTitle);

            // Dialog Message
            TextView tvMsg = new TextView(activity);
            tvMsg.setText("Watch a short video ad to run " + nextStepName.toLowerCase() + ".");
            tvMsg.setTextSize(14f);
            tvMsg.setTextColor(0xFF94A3B8);
            LinearLayout.LayoutParams msgParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            msgParams.topMargin = dp(activity, 14f);
            msgParams.bottomMargin = dp(activity, 24f);
            tvMsg.setLayoutParams(msgParams);
            dialogCard.addView(tvMsg);

            // Button Row
            LinearLayout buttonRow = new LinearLayout(activity);
            buttonRow.setOrientation(LinearLayout.HORIZONTAL);
            buttonRow.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            buttonRow.setLayoutParams(rowParams);

            // "Not now" button
            TextView btnNotNow = new TextView(activity);
            btnNotNow.setText("Not now");
            btnNotNow.setTextSize(14.5f);
            btnNotNow.setTextColor(0xFF818CF8); // Lavender / Indigo text
            btnNotNow.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
            btnNotNow.setPadding(dp(activity, 12f), dp(activity, 8f), dp(activity, 12f), dp(activity, 8f));
            btnNotNow.setClickable(true);
            btnNotNow.setFocusable(true);
            btnNotNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            buttonRow.addView(btnNotNow);

            // "Watch video" button
            TextView btnWatch = new TextView(activity);
            btnWatch.setText("Watch video");
            btnWatch.setTextSize(14.5f);
            btnWatch.setTextColor(0xFF818CF8); // Lavender / Indigo bold
            btnWatch.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
            btnWatch.setPadding(dp(activity, 12f), dp(activity, 8f), dp(activity, 12f), dp(activity, 8f));
            LinearLayout.LayoutParams watchParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            watchParams.leftMargin = dp(activity, 10f);
            btnWatch.setLayoutParams(watchParams);
            btnWatch.setClickable(true);
            btnWatch.setFocusable(true);

            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    // Show the ad for this step!
                    showAdForStepOnActivity(activity, currentStepIndex, targetUrl);
                }
            });
            buttonRow.addView(btnWatch);

            dialogCard.addView(buttonRow);

            dialog.setContentView(dialogCard);

            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.88);
                dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            dialog.show();

        } catch (Throwable t) {
            t.printStackTrace();
            // Fallback: trigger ad directly
            showAdForStepOnActivity(activity, currentStepIndex, targetUrl);
        }
    }

    private static void handleAccessDestination(final Activity activity, final String targetUrl, final TextView btn) {
        if (activity == null || activity.isFinishing()) return;

        if (sIsPrefetching) {
            // Still loading in background: show brief loading state on button
            if (btn != null) {
                btn.setEnabled(false);
                btn.setAlpha(0.7f);
                btn.setText("Securing Gateway Token...");
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    long start = System.currentTimeMillis();
                    while (sIsPrefetching && (System.currentTimeMillis() - start < 4000)) {
                        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dispatchOpenDestination(activity, targetUrl);
                        }
                    });
                }
            }).start();
        } else {
            // Prefetch already completed at the beginning! Instant open!
            dispatchOpenDestination(activity, targetUrl);
        }
    }

    private static void dispatchOpenDestination(final Activity activity, final String targetUrl) {
        String destUrl = (sPrefetchSuccess && sPrefetchedDestinationUrl != null)
                ? sPrefetchedDestinationUrl : targetUrl;

        if (destUrl == null || destUrl.isEmpty() || "manual".equalsIgnoreCase(destUrl)) {
            destUrl = (sPrefetchTargetUrl != null && !sPrefetchTargetUrl.isEmpty())
                    ? sPrefetchTargetUrl : "https://zyrobrowser.com";
        }

        if (destUrl.startsWith("https://ezyshort.xyz/http")) {
            destUrl = destUrl.substring("https://ezyshort.xyz/".length());
        }

        if (!destUrl.startsWith("http://") && !destUrl.startsWith("https://")) {
            destUrl = "https://" + destUrl;
        }

        Log.i(TAG, "Opening destination in external browser: " + destUrl);

        // Hide safelink overlay
        hideSafeLinkScreen(activity);

        // Open in external browser
        try {
            ExternalBrowserHelper.openInExternalBrowser(activity, destUrl);
        } catch (Throwable t) {
            t.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(destUrl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            } catch (Throwable ignored) {}
        }
    }

    private static void hideSafeLinkScreen(Activity activity) {
        if (activity == null) return;

        destroyBannerAd();

        try {
            if (activity instanceof MainActivity) {
                MainActivity.access$hideSafeLinkScreen((MainActivity) activity);
                return;
            }
        } catch (Throwable ignored) {}

        try {
            Method m = activity.getClass().getDeclaredMethod("access$hideSafeLinkScreen", activity.getClass());
            m.setAccessible(true);
            m.invoke(null, activity);
            return;
        } catch (Throwable ignored) {}

        try {
            Method m2 = activity.getClass().getDeclaredMethod("hideSafeLinkScreen");
            m2.setAccessible(true);
            m2.invoke(activity);
        } catch (Throwable ignored) {}
    }

    private static void showAdForStepOnActivity(Activity activity, int stepIndex, String url) {
        try {
            if (activity instanceof MainActivity) {
                MainActivity.access$showStepAdPopup((MainActivity) activity, stepIndex, url);
                return;
            }
        } catch (Throwable ignored) {}

        try {
            Method m = activity.getClass().getDeclaredMethod("access$showStepAdPopup", activity.getClass(), int.class, String.class);
            m.setAccessible(true);
            m.invoke(null, activity, stepIndex, url);
            return;
        } catch (Throwable ignored) {}

        try {
            Method m2 = activity.getClass().getDeclaredMethod("showStepAdPopup", int.class, String.class);
            m2.setAccessible(true);
            m2.invoke(activity, stepIndex, url);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void finishSafeLinkOnActivity(Activity activity, String url) {
        handleAccessDestination(activity, url, null);
    }

    private static void startProgressOnActivity(Activity activity, int step, String url) {
        try {
            if (activity instanceof MainActivity) {
                MainActivity.access$runStepWithProgress((MainActivity) activity, step, url);
                return;
            }
        } catch (Throwable ignored) {}

        try {
            Method m = activity.getClass().getDeclaredMethod("access$runStepWithProgress", activity.getClass(), int.class, String.class);
            m.setAccessible(true);
            m.invoke(null, activity, step, url);
            return;
        } catch (Throwable ignored) {}

        try {
            Method m2 = activity.getClass().getDeclaredMethod("runStepWithProgress", int.class, String.class);
            m2.setAccessible(true);
            m2.invoke(activity, step, url);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
