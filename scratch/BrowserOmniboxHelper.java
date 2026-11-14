package com.zyro.browser;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.webkit.WebView;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BrowserOmniboxHelper {

    private static final String TAG = "ZyroOmnibox";
    public static volatile boolean isNavigating = false;

    private static int dp(Context ctx, float dp) {
        if (ctx == null) return (int) dp;
        return (int) (dp * ctx.getResources().getDisplayMetrics().density + 0.5f);
    }

    private static int getId(Context ctx, String name) {
        if (ctx == null) return 0;
        return ctx.getResources().getIdentifier(name, "id", ctx.getPackageName());
    }

    private static int getDrawableId(Context ctx, String name) {
        if (ctx == null) return 0;
        return ctx.getResources().getIdentifier(name, "drawable", ctx.getPackageName());
    }

    public static void setupOmnibox(final Activity activity) {
        if (activity == null) return;

        try {
            int etUrlId = getId(activity, "et_url");
            View etUrlView = activity.findViewById(etUrlId);
            if (!(etUrlView instanceof EditText)) {
                return;
            }
            final EditText etUrl = (EditText) etUrlView;

            // Prevent double setup
            Object tag = etUrl.getTag();
            if (Boolean.TRUE.equals(tag)) {
                return;
            }
            etUrl.setTag(Boolean.TRUE);

            int clearId = getId(activity, "btn_clear_url");
            final View clearView = activity.findViewById(clearId);
            final ImageButton btnClear = (clearView instanceof ImageButton) ? (ImageButton) clearView : null;

            int refreshId = getId(activity, "btn_refresh");
            final View refreshView = activity.findViewById(refreshId);
            final ImageButton btnRefresh = (refreshView instanceof ImageButton) ? (ImageButton) refreshView : null;

            int voiceId = getId(activity, "btn_voice_search_top");
            final View voiceView = activity.findViewById(voiceId);
            final ImageButton btnVoice = (voiceView instanceof ImageButton) ? (ImageButton) voiceView : null;

            int containerId = getId(activity, "url_container");
            final View containerView = activity.findViewById(containerId);
            final LinearLayout urlContainer = (containerView instanceof LinearLayout) ? (LinearLayout) containerView : null;

            // Ensure soft keyboard provides search / go action
            etUrl.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI);

            // Dynamically create or locate Go button inside urlContainer
            ImageButton goButton = null;
            if (urlContainer != null) {
                View existingGo = urlContainer.findViewWithTag("btn_go_url");
                if (existingGo instanceof ImageButton) {
                    goButton = (ImageButton) existingGo;
                } else {
                    goButton = new ImageButton(activity);
                    goButton.setTag("btn_go_url");
                    int size = dp(activity, 32f);
                    int pad = dp(activity, 7f);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                    lp.setMargins(dp(activity, 4f), 0, dp(activity, 2f), 0);
                    goButton.setLayoutParams(lp);
                    goButton.setPadding(pad, pad, pad, pad);
                    goButton.setScaleType(ImageView.ScaleType.FIT_CENTER);

                    // Non-focusable so tapping Go doesn't fire blur on EditText before click
                    goButton.setFocusable(false);
                    goButton.setFocusableInTouchMode(false);

                    // Circular accent badge
                    GradientDrawable goBg = new GradientDrawable();
                    goBg.setShape(GradientDrawable.OVAL);
                    goBg.setColor(0xFFFB542B); // Vibrant Zyro Orange
                    goButton.setBackground(goBg);

                    // Forward arrow icon
                    int arrowId = getDrawableId(activity, "ic_arrow_forward");
                    if (arrowId != 0) {
                        goButton.setImageResource(arrowId);
                    } else {
                        int searchId = getDrawableId(activity, "ic_search");
                        if (searchId != 0) goButton.setImageResource(searchId);
                    }
                    goButton.setImageTintList(ColorStateList.valueOf(0xFFFFFFFF));
                    goButton.setVisibility(View.GONE);

                    int insertIdx = urlContainer.getChildCount();
                    if (btnRefresh != null) {
                        insertIdx = urlContainer.indexOfChild(btnRefresh);
                        if (insertIdx < 0) insertIdx = urlContainer.getChildCount();
                    }
                    urlContainer.addView(goButton, insertIdx);
                }
            }
            final ImageButton btnGo = goButton;

            // Click listener for Go button
            if (btnGo != null) {
                btnGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String currentText = etUrl.getText() != null ? etUrl.getText().toString().trim() : "";
                        Log.d(TAG, "Go button tapped with query: " + currentText);
                        executeLoad(activity, etUrl, currentText);
                    }
                });
            }

            // Click listener for Clear button
            if (btnClear != null) {
                btnClear.setFocusable(false);
                btnClear.setFocusableInTouchMode(false);
                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        etUrl.setText("");
                        etUrl.requestFocus();
                        if (btnGo != null) btnGo.setVisibility(View.GONE);
                        btnClear.setVisibility(View.GONE);
                        if (btnVoice != null) btnVoice.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(etUrl, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });
            }

            // TextWatcher for dynamic UI state changes while typing
            etUrl.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        if (isNavigating) return;
                        String query = s != null ? s.toString().trim() : "";
                        boolean hasText = !TextUtils.isEmpty(query);
                        boolean isFocused = etUrl.hasFocus();

                        if (hasText) {
                            if (btnGo != null) btnGo.setVisibility(View.VISIBLE);
                            if (btnClear != null) btnClear.setVisibility(View.VISIBLE);
                            if (btnVoice != null) btnVoice.setVisibility(View.GONE);
                            if (btnRefresh != null) btnRefresh.setVisibility(View.GONE);
                        } else if (isFocused) {
                            if (btnGo != null) btnGo.setVisibility(View.GONE);
                            if (btnClear != null) btnClear.setVisibility(View.GONE);
                            if (btnVoice != null) btnVoice.setVisibility(View.VISIBLE);
                            if (btnRefresh != null) btnRefresh.setVisibility(View.GONE);
                        }
                    } catch (Throwable ignored) {}
                }
            });

            // Universal EditorActionListener accepting GO, SEARCH, SEND, NEXT, DONE, NULL, ENTER
            etUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Log.d(TAG, "onEditorAction: actionId=" + actionId + ", event=" + event);
                    if (actionId == EditorInfo.IME_ACTION_GO ||
                        actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_SEND ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_NULL ||
                        (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == 160))) {

                        String currentText = etUrl.getText() != null ? etUrl.getText().toString().trim() : "";
                        executeLoad(activity, etUrl, currentText);
                        return true;
                    }
                    return false;
                }
            });

            // OnKeyListener fallback for physical or software Enter key
            etUrl.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == 160) && event.getAction() == KeyEvent.ACTION_DOWN) {
                        Log.d(TAG, "onKey: Enter key down");
                        String currentText = etUrl.getText() != null ? etUrl.getText().toString().trim() : "";
                        executeLoad(activity, etUrl, currentText);
                        return true;
                    }
                    return false;
                }
            });

            // Focus listener with active border highlight & cancelation revert
            etUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    try {
                        if (hasFocus) {
                            if (btnRefresh != null) btnRefresh.setVisibility(View.GONE);
                            String cur = etUrl.getText() != null ? etUrl.getText().toString().trim() : "";
                            if (!TextUtils.isEmpty(cur)) {
                                if (btnClear != null) btnClear.setVisibility(View.VISIBLE);
                                if (btnGo != null) btnGo.setVisibility(View.VISIBLE);
                                if (btnVoice != null) btnVoice.setVisibility(View.GONE);
                            } else {
                                if (btnClear != null) btnClear.setVisibility(View.GONE);
                                if (btnGo != null) btnGo.setVisibility(View.GONE);
                                if (btnVoice != null) btnVoice.setVisibility(View.VISIBLE);
                            }
                            etUrl.selectAll();
                            updateContainerBorder(activity, urlContainer, true);
                        } else {
                            if (btnGo != null) btnGo.setVisibility(View.GONE);
                            if (btnClear != null) btnClear.setVisibility(View.GONE);
                            if (btnVoice != null) btnVoice.setVisibility(View.VISIBLE);
                            if (btnRefresh != null) btnRefresh.setVisibility(View.VISIBLE);
                            updateContainerBorder(activity, urlContainer, false);

                            // Only revert on blur if NOT actively navigating
                            if (!isNavigating) {
                                etUrl.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isNavigating && !etUrl.hasFocus()) {
                                            revertUrlIfUncommitted(activity, etUrl);
                                        }
                                    }
                                }, 300);
                            }
                        }
                    } catch (Throwable ignored) {}
                }
            });

            // Integrate New Tab Page search pill
            int ntpPillId = getId(activity, "ntp_search_pill");
            View ntpPill = activity.findViewById(ntpPillId);
            if (ntpPill != null) {
                ntpPill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int appBarId = getId(activity, "app_bar");
                            View appBar = activity.findViewById(appBarId);
                            if (appBar != null) appBar.setVisibility(View.VISIBLE);

                            etUrl.requestFocus();
                            etUrl.selectAll();
                            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm != null) {
                                imm.showSoftInput(etUrl, InputMethodManager.SHOW_IMPLICIT);
                            }
                        } catch (Throwable ignored) {}
                    }
                });
            }

        } catch (Throwable e) {
            Log.e(TAG, "setupOmnibox error", e);
        }
    }

    public static void executeLoad(final Activity activity, final EditText etUrl, String explicitQuery) {
        if (activity == null || etUrl == null) return;

        try {
            String input = explicitQuery != null ? explicitQuery.trim() : "";
            if (TextUtils.isEmpty(input) && etUrl.getText() != null) {
                input = etUrl.getText().toString().trim();
            }
            if (TextUtils.isEmpty(input)) return;

            isNavigating = true;
            Log.d(TAG, "executeLoad executing with query: " + input);

            // 1. Hide soft keyboard
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(etUrl.getWindowToken(), 0);
            }

            // 2. Hide Go & Clear buttons, restore Voice
            int containerId = getId(activity, "url_container");
            View containerView = activity.findViewById(containerId);
            if (containerView != null) {
                View goView = containerView.findViewWithTag("btn_go_url");
                if (goView != null) goView.setVisibility(View.GONE);
                int clearId = getId(activity, "btn_clear_url");
                View clr = activity.findViewById(clearId);
                if (clr != null) clr.setVisibility(View.GONE);
                int voiceId = getId(activity, "btn_voice_search_top");
                View vce = activity.findViewById(voiceId);
                if (vce != null) vce.setVisibility(View.VISIBLE);
                int refId = getId(activity, "btn_refresh");
                View ref = activity.findViewById(refId);
                if (ref != null) ref.setVisibility(View.VISIBLE);
            }

            // 3. Clear focus from EditText
            etUrl.clearFocus();

            // 4. Compute target URL
            String finalUrl;
            if (input.startsWith("zyro://")) {
                finalUrl = input;
            } else if (input.startsWith("http://") || input.startsWith("https://")) {
                finalUrl = input;
            } else if (input.contains(".") && !input.contains(" ")) {
                finalUrl = "https://" + input;
            } else {
                finalUrl = "https://www.google.com/search?q=" + Uri.encode(input);
            }

            Log.d(TAG, "Navigating to: " + finalUrl);

            // 5. Update etUrl text with formatted URL
            etUrl.setText(finalUrl);

            // 6. Direct navigation via MainActivity.access$navigateToUrl
            boolean navigated = false;
            try {
                if (activity instanceof MainActivity) {
                    MainActivity.access$navigateToUrl((MainActivity) activity, finalUrl);
                    navigated = true;
                }
            } catch (Throwable t) {
                Log.w(TAG, "access$navigateToUrl failed: " + t.getMessage());
            }

            if (!navigated) {
                try {
                    Method m = activity.getClass().getDeclaredMethod("access$navigateToUrl", activity.getClass(), String.class);
                    m.setAccessible(true);
                    m.invoke(null, activity, finalUrl);
                    navigated = true;
                } catch (Throwable t) {
                    Log.w(TAG, "Reflection access$navigateToUrl failed: " + t.getMessage());
                }
            }

            if (!navigated) {
                try {
                    Method m = activity.getClass().getDeclaredMethod("navigateToUrl", String.class);
                    m.setAccessible(true);
                    m.invoke(activity, finalUrl);
                    navigated = true;
                } catch (Throwable t) {
                    Log.w(TAG, "Reflection navigateToUrl failed: " + t.getMessage());
                }
            }

            if (!navigated) {
                try {
                    if (activity instanceof MainActivity) {
                        MainActivity.access$loadEnteredUrl((MainActivity) activity);
                    }
                } catch (Throwable ignored) {}
            }

        } catch (Throwable t) {
            Log.e(TAG, "executeLoad error: " + t.getMessage(), t);
        } finally {
            // Keep isNavigating true for a moment so focus transitions don't overwrite URL
            etUrl.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isNavigating = false;
                }
            }, 600);
        }
    }

    public static void executeLoad(Activity activity, EditText etUrl) {
        executeLoad(activity, etUrl, null);
    }

    private static void updateContainerBorder(Activity activity, View container, boolean focused) {
        if (activity == null || container == null) return;
        try {
            GradientDrawable gd = new GradientDrawable();
            gd.setShape(GradientDrawable.RECTANGLE);
            gd.setCornerRadius(dp(activity, 24f));

            boolean isDark = true;
            try {
                Field f = activity.getClass().getDeclaredField("isDarkTheme");
                f.setAccessible(true);
                isDark = f.getBoolean(activity);
            } catch (Throwable ignored) {}

            int bgColor = isDark ? 0xFF1E293B : 0xFFF1F5F9;
            gd.setColor(bgColor);

            if (focused) {
                gd.setStroke(dp(activity, 1.5f), 0xFFFB542B); // Glowing brand accent
            } else {
                gd.setStroke(dp(activity, 1f), isDark ? 0xFF334155 : 0xFFCBD5E1);
            }
            container.setBackground(gd);
        } catch (Throwable ignored) {}
    }

    public static void revertUrlIfUncommitted(Activity activity, EditText etUrl) {
        if (activity == null || etUrl == null || isNavigating) return;
        try {
            Field f = activity.getClass().getDeclaredField("tabManager");
            f.setAccessible(true);
            Object tm = f.get(activity);
            if (tm != null) {
                Method m = tm.getClass().getDeclaredMethod("getCurrentTab");
                m.setAccessible(true);
                Object tab = m.invoke(tm);
                if (tab != null) {
                    Method mUrl = tab.getClass().getDeclaredMethod("getUrl");
                    mUrl.setAccessible(true);
                    String activeUrl = (String) mUrl.invoke(tab);
                    if (activeUrl == null || activeUrl.isEmpty() || "zyro://home".equalsIgnoreCase(activeUrl)) {
                        etUrl.setText("");
                    } else {
                        etUrl.setText(activeUrl);
                    }
                }
            }
        } catch (Throwable ignored) {}
    }

    public static void returnToHomeScreen(final Activity activity) {
        if (activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 1. Reset current tab in TabManager
                    Object tm = getFieldValue(activity, "tabManager");
                    if (tm != null) {
                        try {
                            Method mGetTab = tm.getClass().getDeclaredMethod("getCurrentTab");
                            mGetTab.setAccessible(true);
                            Object tab = mGetTab.invoke(tm);
                            if (tab != null) {
                                // Set URL to zyro://home
                                Method mSetUrl = tab.getClass().getDeclaredMethod("setUrl", String.class);
                                mSetUrl.setAccessible(true);
                                mSetUrl.invoke(tab, "zyro://home");

                                // Set Title to "New Tab"
                                Method mSetTitle = tab.getClass().getDeclaredMethod("setTitle", String.class);
                                mSetTitle.setAccessible(true);
                                mSetTitle.invoke(tab, "New Tab");

                                // Clear Thumbnail
                                try {
                                    Method mSetThumb = tab.getClass().getDeclaredMethod("setThumbnail", android.graphics.Bitmap.class);
                                    mSetThumb.setAccessible(true);
                                    mSetThumb.invoke(tab, new Object[]{null});
                                } catch (Throwable ignored) {}

                                // Clear Favicon
                                try {
                                    Method mSetFavicon = tab.getClass().getDeclaredMethod("setFavicon", android.graphics.Bitmap.class);
                                    mSetFavicon.setAccessible(true);
                                    mSetFavicon.invoke(tab, new Object[]{null});
                                } catch (Throwable ignored) {}

                                // Clear & reset WebView
                                try {
                                    Method mGetWv = tab.getClass().getDeclaredMethod("getWebView");
                                    mGetWv.setAccessible(true);
                                    Object wvObj = mGetWv.invoke(tab);
                                    if (wvObj instanceof WebView) {
                                        WebView wv = (WebView) wvObj;
                                        wv.stopLoading();
                                        wv.clearHistory();
                                        wv.setVisibility(View.GONE);
                                    }
                                } catch (Throwable ignored) {}
                            }
                        } catch (Throwable t) {
                            Log.w(TAG, "Error resetting tab: " + t);
                        }
                    }

                    // 2. Clear Omnibox text and focus
                    int etUrlId = getId(activity, "et_url");
                    View etUrlView = activity.findViewById(etUrlId);
                    if (etUrlView instanceof EditText) {
                        EditText etUrl = (EditText) etUrlView;
                        etUrl.setText("");
                        etUrl.clearFocus();
                    }

                    // 3. Hide soft keyboard
                    try {
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null && etUrlView != null) {
                            imm.hideSoftInputFromWindow(etUrlView.getWindowToken(), 0);
                        }
                    } catch (Throwable ignored) {}

                    // 4. Show Dashboard & Hide AppBar
                    try {
                        int appBarId = getId(activity, "app_bar");
                        View appBar = activity.findViewById(appBarId);
                        if (appBar != null) {
                            appBar.setVisibility(View.GONE);
                        }

                        int ntpViewId = getId(activity, "ntp_view");
                        View ntpView = activity.findViewById(ntpViewId);
                        if (ntpView != null) {
                            ntpView.setVisibility(View.VISIBLE);
                        }

                        Method mShowDash = activity.getClass().getDeclaredMethod("showDashboard", boolean.class);
                        mShowDash.setAccessible(true);
                        mShowDash.invoke(activity, true);
                    } catch (Throwable t) {
                        try {
                            Method mAccessShowDash = activity.getClass().getDeclaredMethod("access$showDashboard", activity.getClass(), boolean.class);
                            mAccessShowDash.setAccessible(true);
                            mAccessShowDash.invoke(null, activity, true);
                        } catch (Throwable ignored) {}
                    }

                    // 5. Notify TabGridAdapter
                    try {
                        Object adapter = getFieldValue(activity, "tabGridAdapter");
                        if (adapter != null) {
                            Method mNotify = adapter.getClass().getMethod("notifyDataSetChanged");
                            mNotify.setAccessible(true);
                            mNotify.invoke(adapter);
                        }
                    } catch (Throwable ignored) {}

                    // 6. Update tab count TextViews
                    if (tm != null) {
                        try {
                            Method mCount = tm.getClass().getDeclaredMethod("getTabCount");
                            mCount.setAccessible(true);
                            Object countVal = mCount.invoke(tm);
                            String countStr = String.valueOf(countVal);

                            int tvBottomId = getId(activity, "tv_bottom_tab_count");
                            View tvBottom = activity.findViewById(tvBottomId);
                            if (tvBottom instanceof TextView) {
                                ((TextView) tvBottom).setText(countStr);
                            }

                            int tvSwitcherId = getId(activity, "tv_switcher_tab_count");
                            View tvSwitcher = activity.findViewById(tvSwitcherId);
                            if (tvSwitcher instanceof TextView) {
                                ((TextView) tvSwitcher).setText(countStr);
                            }
                        } catch (Throwable ignored) {}
                    }

                    Log.d(TAG, "returnToHomeScreen: successfully closed previous website in current tab");
                } catch (Throwable t) {
                    Log.e(TAG, "Error in returnToHomeScreen: " + t, t);
                }
            }
        });
    }

    private static Object getFieldValue(Object target, String fieldName) {
        if (target == null) return null;
        Class<?> cur = target.getClass();
        while (cur != null && cur != Object.class) {
            try {
                Field f = cur.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f.get(target);
            } catch (Throwable ignored) {}
            cur = cur.getSuperclass();
        }
        return null;
    }
}
