package com.zyro.browser

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.graphics.Color
import android.speech.RecognizerIntent
import android.widget.LinearLayout
import android.graphics.RenderEffect
import android.graphics.Shader
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import java.util.concurrent.atomic.AtomicInteger
import java.util.Locale
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder
import org.json.JSONObject
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.google.android.gms.ads.appopen.AppOpenAd
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.zyro.browser.browser.BrowserTab
import com.zyro.browser.browser.SpeedDialAdapter
import com.zyro.browser.browser.TabGridAdapter
import com.zyro.browser.browser.TabManager
import com.zyro.browser.browser.ZyroChromeClient
import com.zyro.browser.security.AdBlockDetector
import com.zyro.browser.security.AdBlockDetectionResult
import com.zyro.browser.ads.AppLovinAdManager
import com.zyro.browser.ads.MetaAdManager
import com.zyro.browser.browser.ZyroWebViewClient
import com.zyro.browser.databinding.ActivityMainBinding
import com.zyro.browser.databinding.DialogBookmarksSheetBinding
import com.zyro.browser.firebase.ZyroFirebaseService
import com.zyro.browser.model.AdNetworkConfig
import com.zyro.browser.ads.GoogleAdManager
import com.zyro.browser.model.AppLovinAdsConfig
import com.zyro.browser.model.GoogleAdManagerConfig
import com.zyro.browser.model.GoogleAdsConfig
import com.zyro.browser.model.MetaAdsConfig
import com.zyro.browser.model.SafeLinkAd
import com.zyro.browser.model.SpeedDialItem
import com.zyro.browser.model.ZyroNotification

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabManager: TabManager
    private lateinit var tabGridAdapter: TabGridAdapter
    private lateinit var prefs: SharedPreferences

    private val speedDialList = mutableListOf<SpeedDialItem>()
    private lateinit var speedDialAdapter: SpeedDialAdapter

    private val dynamicStepsList = mutableListOf<SafeLinkAd>()
    private val stepViewHolders = mutableListOf<StepViewHolder>()

    // Monetization & Multi-Network Config
    private var adNetworkConfig = AdNetworkConfig()
    private var metaAdsConfig = MetaAdsConfig()
    private var applovinAdsConfig = AppLovinAdsConfig()
    private var googleAdsConfig = GoogleAdsConfig()
    private var gamConfig = GoogleAdManagerConfig()
    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null
    private var isAdLoading = false
    private var isRewardedLoading = false

    // SafeLink State & Dialog Controls
    private var isSafeLinkActive = false
    private var currentSafeLinkStep = -1
    private var currentAdDialog: AlertDialog? = null
    private var adCountdownTimer: CountDownTimer? = null

    // Pausable SafeLink & Ad Countdown Tracking
    private var isAdCountdownRunning = false
    private var isAdCountdownPaused = false
    private var adRemainingSeconds = 0
    private val adCountdownHandler = Handler(Looper.getMainLooper())
    private var adCountdownRunnable: Runnable? = null
    private var onAdCountdownTick: ((Int) -> Unit)? = null
    private var onAdCountdownFinish: (() -> Unit)? = null
    private var activeBannerAdView: View? = null
    private var isFullScreenLoadingShowing = false
    private var fullScreenLoadingTimeoutHandler: Handler? = null
    private var fullScreenLoadingTimeoutRunnable: Runnable? = null
    private var lastPauseResumeToastTime = 0L

    // SafeLink Deep Link & Token API Handshake
    private var currentSafeLinkAlias: String? = null
    private var currentSafeLinkUuid: String? = null
    private var currentSafeLinkApiUrl: String = "https://zyrobrowser.com/api.php"
    private var currentSafeLinkAppKey: String = "b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f"
    private var currentSafeLinkBaseUrl: String = "https://zyrobrowser.com"

    data class StepViewHolder(
        val root: View,
        val title: TextView,
        val subtitle: TextView,
        val badge: TextView,
        val progressBar: android.widget.ProgressBar
    )

    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var customView: View? = null
    private var customViewCallback: WebChromeClient.CustomViewCallback? = null
    private var isDarkTheme = true

    private val totalTrackersBlocked = AtomicInteger(48)
    private val pageTrackersBlocked = AtomicInteger(0)
    private val statsHandler = Handler(Looper.getMainLooper())
    private val statsRunnable = Runnable {
        if (binding.ntpView.root.visibility == View.VISIBLE) {
            updateDashboardStats()
        }
    }
    private var lastAdBlockCheckTime = 0L

    companion object {
        private const val DESKTOP_USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
        private const val DESKTOP_VIEWPORT_SCRIPT =
            """(function(){var m=document.querySelector('meta[name="viewport"]');if(!m){m=document.createElement('meta');m.name='viewport';document.head.appendChild(m);}m.setAttribute('content','width=1280,initial-scale=0.35,minimum-scale=0.25,maximum-scale=5.0,user-scalable=yes');})();"""
        private const val MOBILE_VIEWPORT_SCRIPT =
            """(function(){var m=document.querySelector('meta[name="viewport"]');if(m){m.setAttribute('content','width=device-width,initial-scale=1.0,user-scalable=yes');}})();"""
    }

    private val fileChooserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        val uris = WebChromeClient.FileChooserParams.parseResult(result.resultCode, data)
        filePathCallback?.onReceiveValue(uris)
        filePathCallback = null
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        Log.d("ZyroBrowser", "POST_NOTIFICATIONS granted: $isGranted")
    }

    private val voiceSearchLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!matches.isNullOrEmpty()) {
                val query = matches[0]
                binding.etUrl.setText(query)
                loadEnteredUrl()
            }
        }
    }

    private fun launchVoiceSearch() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search with Google or enter address...")
        }
        try {
            voiceSearchLauncher.launch(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Voice search is unavailable on this device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences("zyro_prefs", Context.MODE_PRIVATE)
        isDarkTheme = prefs.getBoolean("is_dark_theme", true)
        
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabManager = TabManager(this)

        speedDialList.addAll(SpeedDialItem.getDefaultItems())
        speedDialAdapter = SpeedDialAdapter(
            items = speedDialList,
            isDarkTheme = isDarkTheme,
            onItemClick = { item -> navigateToUrl(item.url) }
        )

        applyThemeStyles(isDarkTheme)
        setupTopOmnibox()
        setupBottomToolbar()
        setupNewTabDashboard()
        setupTabSwitcher()
        setupAdBlockWarning()
        setupBackNavigation()
        checkAdBlockerAndEnforce()

        // Remove placeholder WebView from XML
        binding.webContainer.removeView(binding.webView)

        // Default tab on launch
        val firstTab = tabManager.createNewTab("zyro://home")
        attachTab(firstTab)
        showDashboard(true)

        initSafeLinkAds()
        initAdMob()
        checkNotificationPermission()
        initPushNotifications()
        initSpeedDialSync()
        handleDeepLinkOrNotificationIntent(intent)
        checkInstallReferrer()
    }

    override fun onResume() {
        super.onResume()
        getCurrentWebView()?.onResume()
        checkAdBlockerAndEnforce()

        // 1. Resume SafeLink Progress Animation if active and paused
        if (isSafeLinkActive && activeStepAnimator?.isPaused == true) {
            if (currentSafeLinkStep in stepViewHolders.indices) {
                val holder = stepViewHolders[currentSafeLinkStep]
                holder.badge.setTextColor(getColor(R.color.cyber_cyan))
                holder.badge.text = "${holder.progressBar.progress}%"
            }
            activeStepAnimator?.resume()
            showPauseResumeNotice("⚠️ Security scan was paused in background · Resumed")
        }

        // 2. Resume SafeLink Ad Countdown if running and paused
        if (isAdCountdownRunning && isAdCountdownPaused) {
            resumeAdCountdown()
            showPauseResumeNotice("⚠️ Ad verification was paused in background · Resumed (${adRemainingSeconds}s)")
        }

        // 3. Resume active banner/GAM ad view if any
        (activeBannerAdView as? com.google.android.gms.ads.BaseAdView)?.resume()

        // 4. Resume full-screen loading HUD timeout if active
        if (isFullScreenLoadingShowing && isSafeLinkActive) {
            fullScreenLoadingTimeoutRunnable?.let {
                fullScreenLoadingTimeoutHandler?.removeCallbacks(it)
                fullScreenLoadingTimeoutHandler?.postDelayed(it, 5000L)
            }
            showPauseResumeNotice("⚠️ Full-screen loading was paused in background · Resumed")
        }
    }

    override fun onPause() {
        super.onPause()
        getCurrentWebView()?.onPause()
        CookieManager.getInstance().flush()

        // 1. Pause SafeLink Progress Animation if running
        if (isSafeLinkActive && activeStepAnimator?.isRunning == true) {
            activeStepAnimator?.pause()
            if (currentSafeLinkStep in stepViewHolders.indices) {
                val holder = stepViewHolders[currentSafeLinkStep]
                holder.badge.text = "${holder.progressBar.progress}% (PAUSED)"
                holder.badge.setTextColor(getColor(R.color.cyber_yellow))
            }
        }

        // 2. Pause SafeLink Ad Countdown if running
        if (isAdCountdownRunning && !isAdCountdownPaused) {
            pauseAdCountdown()
        }

        // 3. Pause active banner/GAM ad view if any
        (activeBannerAdView as? com.google.android.gms.ads.BaseAdView)?.pause()

        // 4. Pause full-screen loading HUD timeout to prevent premature fallback in background
        if (isFullScreenLoadingShowing) {
            fullScreenLoadingTimeoutRunnable?.let {
                fullScreenLoadingTimeoutHandler?.removeCallbacks(it)
            }
        }
    }

    private fun showPauseResumeNotice(message: String) {
        val now = System.currentTimeMillis()
        if (now - lastPauseResumeToastTime > 2500L) {
            lastPauseResumeToastTime = now
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPausableAdCountdown(
        seconds: Int,
        onTick: (Int) -> Unit,
        onFinish: () -> Unit
    ) {
        stopPausableAdCountdown()
        adRemainingSeconds = seconds
        isAdCountdownRunning = true
        isAdCountdownPaused = false
        onAdCountdownTick = onTick
        onAdCountdownFinish = onFinish

        // Immediate first tick callback
        onTick(adRemainingSeconds)

        val runnable = object : Runnable {
            override fun run() {
                if (!isAdCountdownRunning || isAdCountdownPaused) return
                adRemainingSeconds--
                if (adRemainingSeconds <= 0) {
                    isAdCountdownRunning = false
                    isAdCountdownPaused = false
                    onAdCountdownFinish?.invoke()
                } else {
                    onAdCountdownTick?.invoke(adRemainingSeconds)
                    adCountdownHandler.postDelayed(this, 1000L)
                }
            }
        }
        adCountdownRunnable = runnable
        adCountdownHandler.postDelayed(runnable, 1000L)
    }

    private fun pauseAdCountdown() {
        if (isAdCountdownRunning && !isAdCountdownPaused) {
            isAdCountdownPaused = true
            adCountdownRunnable?.let { adCountdownHandler.removeCallbacks(it) }
        }
    }

    private fun resumeAdCountdown() {
        if (isAdCountdownRunning && isAdCountdownPaused) {
            isAdCountdownPaused = false
            onAdCountdownTick?.invoke(adRemainingSeconds)
            adCountdownRunnable?.let {
                adCountdownHandler.removeCallbacks(it)
                adCountdownHandler.postDelayed(it, 1000L)
            }
        }
    }

    private fun stopPausableAdCountdown() {
        isAdCountdownRunning = false
        isAdCountdownPaused = false
        adRemainingSeconds = 0
        adCountdownRunnable?.let { adCountdownHandler.removeCallbacks(it) }
        adCountdownRunnable = null
        onAdCountdownTick = null
        onAdCountdownFinish = null
        adCountdownTimer?.cancel()
        adCountdownTimer = null
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLinkOrNotificationIntent(intent)
    }

    private fun applyThemeStyles(dark: Boolean) {
        val bgColor = if (dark) Color.parseColor("#121316") else Color.parseColor("#F5F6F8")
        val surfaceColor = if (dark) Color.parseColor("#1C1E23") else Color.parseColor("#FFFFFF")
        val cardBorderColor = if (dark) Color.parseColor("#2A2D34") else Color.parseColor("#E5E7EB")
        val urlBarColor = if (dark) Color.parseColor("#22252C") else Color.parseColor("#EAECEF")
        val chipColor = if (dark) Color.parseColor("#20232A") else Color.parseColor("#EAECEF")
        val textPrimary = if (dark) Color.parseColor("#F8F9FA") else Color.parseColor("#111827")
        val textSecondary = if (dark) Color.parseColor("#9CA3AF") else Color.parseColor("#6B7280")
        val bottomNavColor = if (dark) Color.parseColor("#18191D") else Color.parseColor("#FFFFFF")

        // 1. Root & App Bars
        binding.rootLayout.setBackgroundColor(bgColor)
        binding.appBar.setBackgroundColor(bgColor)
        binding.bottomToolbar.setBackgroundColor(bottomNavColor)

        // 2. Omnibox
        binding.urlContainer.backgroundTintList = ColorStateList.valueOf(urlBarColor)
        binding.etUrl.setTextColor(textPrimary)
        binding.etUrl.setHintTextColor(textSecondary)
        binding.sslIndicator.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.btnRefresh.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.btnClearUrl.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.btnVoiceSearchTop.imageTintList = ColorStateList.valueOf(textSecondary)

        // 3. Bottom Toolbar Buttons & Tab Badge
        binding.navHome.imageTintList = ColorStateList.valueOf(textPrimary)
        binding.navBookmarks.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.navSearch.imageTintList = ColorStateList.valueOf(textPrimary)
        binding.navMenu.imageTintList = ColorStateList.valueOf(textPrimary)
        binding.tvBottomTabCount.setTextColor(textPrimary)

        // 4. NTP Dashboard (Home Page)
        val ntp = binding.ntpView
        ntp.root.setBackgroundColor(bgColor)
        ntp.ntpTitle.setTextColor(textPrimary)

        // Hero Search Pill
        ntp.ntpSearchPill.backgroundTintList = ColorStateList.valueOf(urlBarColor)
        ntp.tvNtpSearchHint.setTextColor(textSecondary)
        ntp.btnNtpQrSearch.imageTintList = ColorStateList.valueOf(textSecondary)

        // Quick Actions Hub
        val qaBgTint = ColorStateList.valueOf(surfaceColor)
        ntp.qaIncognitoBg.backgroundTintList = qaBgTint
        ntp.qaBookmarksBg.backgroundTintList = qaBgTint
        ntp.qaHistoryBg.backgroundTintList = qaBgTint
        ntp.qaDownloadsBg.backgroundTintList = qaBgTint
        ntp.tvActionIncognito.setTextColor(textPrimary)
        ntp.tvActionBookmarks.setTextColor(textPrimary)
        ntp.tvActionHistory.setTextColor(textPrimary)
        ntp.tvActionDownloads.setTextColor(textPrimary)

        // Shields Metrics Card
        ntp.cardShieldsContainer.setCardBackgroundColor(surfaceColor)
        ntp.cardShieldsContainer.strokeColor = cardBorderColor
        ntp.tvShieldsTitle.setTextColor(textPrimary)
        ntp.viewShieldsDivider.setBackgroundColor(cardBorderColor)
        ntp.tvShieldsSub1.setTextColor(textSecondary)
        ntp.tvShieldsSub2.setTextColor(textSecondary)
        ntp.tvShieldsSub3.setTextColor(textSecondary)
        ntp.viewStatDivider1.setBackgroundColor(cardBorderColor)
        ntp.viewStatDivider2.setBackgroundColor(cardBorderColor)

        // Favorites Header
        ntp.tvFavoritesHeader.setTextColor(textSecondary)

        // Explore & Trending Section
        ntp.tvExploreHeader.setTextColor(textSecondary)
        val chipBgTint = ColorStateList.valueOf(chipColor)
        ntp.chipExploreAi.backgroundTintList = chipBgTint
        ntp.chipExploreAi.setTextColor(textPrimary)
        ntp.chipExploreNews.backgroundTintList = chipBgTint
        ntp.chipExploreNews.setTextColor(textPrimary)
        ntp.chipExploreSports.backgroundTintList = chipBgTint
        ntp.chipExploreSports.setTextColor(textPrimary)
        ntp.chipExploreMarkets.backgroundTintList = chipBgTint
        ntp.chipExploreMarkets.setTextColor(textPrimary)
        ntp.chipExploreDeals.backgroundTintList = chipBgTint
        ntp.chipExploreDeals.setTextColor(textPrimary)

        // Trending Query Cards
        val trendBgTint = ColorStateList.valueOf(surfaceColor)
        ntp.trendCard1.backgroundTintList = trendBgTint
        ntp.trendCard2.backgroundTintList = trendBgTint
        ntp.trendCard3.backgroundTintList = trendBgTint
        ntp.trendCard4.backgroundTintList = trendBgTint
        ntp.tvTrend1Title.setTextColor(textPrimary)
        ntp.tvTrend2Title.setTextColor(textPrimary)
        ntp.tvTrend3Title.setTextColor(textPrimary)
        ntp.tvTrend4Title.setTextColor(textPrimary)

        // Zero-Trace Card
        val zeroTraceBg = if (dark) Color.parseColor("#1B2028") else Color.parseColor("#F0FDF4")
        ntp.cardZeroTrace.backgroundTintList = ColorStateList.valueOf(zeroTraceBg)
        ntp.tvZeroTraceTitle.setTextColor(textPrimary)
        ntp.tvZeroTraceDesc.setTextColor(textSecondary)

        // Dynamic Speed Dial Theme Sync
        if (::speedDialAdapter.isInitialized) {
            speedDialAdapter.updateData(speedDialList, dark)
        }

        // 5. SafeLink Screen (always premium light theme)
        val sl = binding.safelinkView
        sl.safelinkScreenRoot.setBackgroundColor(Color.parseColor("#F1F5F9"))
        sl.tvTargetUrl.setTextColor(Color.parseColor("#1E293B"))
        sl.tvSafelinkHeadline.setTextColor(Color.parseColor("#0F172A"))

        // 6. System Status Bar & Navigation Bar Light/Dark Icons
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = !dark
            isAppearanceLightNavigationBars = !dark
        }
        window.statusBarColor = bgColor
        window.navigationBarColor = bottomNavColor
    }

    private fun setupTopOmnibox() {
        binding.etUrl.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
            ) {
                loadEnteredUrl()
                true
            } else {
                false
            }
        }

        binding.etUrl.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.btnRefresh.visibility = View.GONE
                binding.btnClearUrl.visibility = View.VISIBLE
                binding.etUrl.selectAll()
            } else {
                binding.btnClearUrl.visibility = View.GONE
                binding.btnRefresh.visibility = View.VISIBLE
            }
        }

        binding.btnClearUrl.setOnClickListener {
            binding.etUrl.setText("")
        }

        binding.btnRefresh.setOnClickListener {
            val wv = getCurrentWebView()
            if (binding.progressBar.visibility == View.VISIBLE) {
                wv?.stopLoading()
            } else {
                wv?.reload()
            }
        }

        // Brave Shields Button in Omnibox
        binding.btnShields.setOnClickListener {
            showShieldsDialog()
        }

        binding.btnVoiceSearchTop.setOnClickListener {
            launchVoiceSearch()
        }

        binding.swipeRefresh.setOnRefreshListener {
            getCurrentWebView()?.reload()
        }

        // Prevent SwipeRefresh from intercepting touches when WebView or dashboard is scrolled down
        binding.swipeRefresh.setOnChildScrollUpCallback { _, _ ->
            if (binding.ntpView.root.visibility == View.VISIBLE) {
                binding.ntpView.newTabDashboard.canScrollVertically(-1)
            } else {
                val wv = getCurrentWebView()
                wv != null && (wv.scrollY > 0 || wv.canScrollVertically(-1))
            }
        }
    }

    private fun returnToHomeScreen() {
        val tab = tabManager.currentTab
        if (tab != null) {
            tab.url = "zyro://home"
            tab.title = "New Tab"
            tab.thumbnail = null
            tab.favicon = null
            tab.webView?.apply {
                stopLoading()
                loadUrl("about:blank")
                clearHistory()
            }
        }
        binding.etUrl.setText("")
        binding.etUrl.clearFocus()
        hideKeyboard()
        showDashboard(true)
        if (::tabGridAdapter.isInitialized) {
            tabGridAdapter.notifyDataSetChanged()
        }
        val countStr = tabManager.tabCount.toString()
        binding.tvBottomTabCount.text = countStr
        binding.tabSwitcherView.tvSwitcherTabCount.text = countStr
    }

    private fun setupBottomToolbar() {
        binding.navHome.setOnClickListener {
            returnToHomeScreen()
        }

        binding.navSearch.setOnClickListener {
            showDashboard(false)
            binding.etUrl.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(binding.etUrl, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.navBookmarks.setOnClickListener {
            showBookmarksDialog()
        }

        binding.navTabs.setOnClickListener {
            showTabSwitcher()
        }

        binding.navMenu.setOnClickListener {
            showBraveBottomSheetMenu()
        }
    }

    private fun setupNewTabDashboard() {
        val ntp = binding.ntpView

        ntp.ntpSearchPill.setOnClickListener {
            showDashboard(false)
            binding.etUrl.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.showSoftInput(binding.etUrl, InputMethodManager.SHOW_IMPLICIT)
        }

        ntp.btnNtpVoiceSearch.setOnClickListener {
            launchVoiceSearch()
        }

        ntp.btnNtpQrSearch.setOnClickListener {
            Toast.makeText(this, "QR & Lens Scanner coming soon", Toast.LENGTH_SHORT).show()
        }

        // Quick Actions Hub
        ntp.btnActionIncognito.setOnClickListener {
            val newTab = tabManager.createNewTab("zyro://home")
            attachTab(newTab)
            Toast.makeText(this, "🕶️ Private Tab Opened", Toast.LENGTH_SHORT).show()
        }

        ntp.btnActionBookmarks.setOnClickListener {
            showBookmarksDialog()
        }

        ntp.btnActionHistory.setOnClickListener {
            Toast.makeText(this, "🛡️ History is protected & stored locally", Toast.LENGTH_SHORT).show()
        }

        ntp.btnActionDownloads.setOnClickListener {
            try {
                startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
            } catch (_: Exception) {
                Toast.makeText(this, "No downloads yet", Toast.LENGTH_SHORT).show()
            }
        }

        // Shields Widget
        ntp.ntpStatsCard.setOnClickListener {
            showShieldsDialog()
        }
        ntp.pillShieldsLive.setOnClickListener {
            showShieldsDialog()
        }

        // Speed Dial & Add Favorite
        ntp.rvSpeedDial.layoutManager = GridLayoutManager(this, 4)
        ntp.rvSpeedDial.adapter = speedDialAdapter

        ntp.btnAddSpeedDial.setOnClickListener {
            showAddFavoriteDialog()
        }

        // Trending & Explore Chips
        ntp.chipExploreAi.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Artificial+Intelligence+Tech+News") }
        ntp.chipExploreNews.setOnClickListener { navigateToUrl("https://news.google.com") }
        ntp.chipExploreSports.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Live+Cricket+and+Sports+Scores") }
        ntp.chipExploreMarkets.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Crypto+and+Stock+Market+Prices") }
        ntp.chipExploreDeals.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Best+Online+Deals+and+Discounts") }

        // Trending Query Cards
        ntp.trendCard1.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Latest+AI+Tech+Updates") }
        ntp.trendCard2.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Crypto+and+Market+News") }
        ntp.trendCard3.setOnClickListener { navigateToUrl("https://www.google.com/search?q=Live+Cricket+Scores+and+Updates") }
        ntp.trendCard4.setOnClickListener { navigateToUrl("https://www.google.com/search?q=New+Movies+and+Trailers") }

        updateDashboardStats()
    }

    private fun showAddFavoriteDialog() {
        val titleInput = android.widget.EditText(this).apply {
            hint = "Website Name (e.g. Netflix)"
            setTextColor(if (isDarkTheme) Color.WHITE else Color.BLACK)
            setHintTextColor(Color.GRAY)
        }
        val urlInput = android.widget.EditText(this).apply {
            hint = "Website URL (e.g. netflix.com)"
            setTextColor(if (isDarkTheme) Color.WHITE else Color.BLACK)
            setHintTextColor(Color.GRAY)
        }
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 24, 50, 0)
            addView(titleInput)
            addView(urlInput)
        }

        AlertDialog.Builder(this)
            .setTitle("Add Favorite Shortcut")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val title = titleInput.text.toString().trim()
                var url = urlInput.text.toString().trim()
                if (title.isNotEmpty() && url.isNotEmpty()) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "https://$url"
                    }
                    val newItem = SpeedDialItem(
                        id = "custom_${System.currentTimeMillis()}",
                        title = title.take(15),
                        url = url,
                        badgeText = title.take(2).uppercase(),
                        bgColor = "#FB542B",
                        order = speedDialList.size + 1,
                        enabled = true
                    )
                    speedDialList.add(newItem)
                    speedDialAdapter.updateData(speedDialList, isDarkTheme)
                    com.google.firebase.firestore.FirebaseFirestore.getInstance()
                        .collection("speed_dial_bookmarks")
                        .document(newItem.id)
                        .set(newItem.toMap())
                    Toast.makeText(this, "Added: $title", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateDashboardStats() {
        val total = totalTrackersBlocked.get()
        binding.ntpView.tvStatTrackers.text = total.toString()
        val mbSaved = String.format(Locale.US, "%.1f MB", total * 0.3)
        binding.ntpView.tvStatBandwidth.text = mbSaved
        val timeSaved = String.format(Locale.US, "%.1f s", total * 0.08)
        binding.ntpView.tvStatTime.text = timeSaved
    }

    private fun showDashboard(show: Boolean) {
        if (show) {
            binding.ntpView.root.visibility = View.VISIBLE
            binding.ntpView.root.bringToFront()
            binding.ntpView.newTabDashboard.post {
                binding.ntpView.newTabDashboard.scrollTo(0, 0)
            }
            getCurrentWebView()?.visibility = View.GONE
            binding.appBar.visibility = View.GONE
            binding.sslIndicator.visibility = View.GONE
            binding.btnRefresh.visibility = View.GONE
            binding.swipeRefresh.isEnabled = false
            binding.swipeRefresh.isRefreshing = false
            binding.btnShields.setColorFilter(getColor(R.color.zyro_orange))
            updateDashboardStats()
        } else {
            binding.ntpView.root.visibility = View.GONE
            getCurrentWebView()?.visibility = View.VISIBLE
            binding.appBar.visibility = View.VISIBLE
            binding.sslIndicator.visibility = View.VISIBLE
            binding.btnRefresh.visibility = View.VISIBLE
            binding.swipeRefresh.isEnabled = true
        }
    }

    private fun navigateToUrl(url: String) {
        try {
            val uri = Uri.parse(url)
            val scheme = uri.scheme?.lowercase() ?: ""
            val host = uri.host?.lowercase() ?: ""
            val path = uri.path?.lowercase() ?: ""
            val isSafeLinkScheme = SafeLinkInputHelper.isScannerUri(uri)
            if (isSafeLinkScheme) {
                hideKeyboard()
                binding.etUrl.clearFocus()
                handleDeepLinkOrNotificationIntent(Intent(Intent.ACTION_VIEW, uri))
                return
            }
        } catch (_: Exception) {}

        showDashboard(false)
        hideKeyboard()
        binding.etUrl.clearFocus()
        binding.etUrl.setText(url)
        tabManager.currentTab?.url = url
        getCurrentWebView()?.loadUrl(url)
    }

    private fun loadEnteredUrl() {
        val input = binding.etUrl.text.toString().trim()
        if (input.isEmpty()) return

        if (input.startsWith("zyro://", ignoreCase = true)) {
            navigateToUrl(input)
            return
        }

        val finalUrl = when {
            input.startsWith("http://", ignoreCase = true) || input.startsWith("https://", ignoreCase = true) -> input
            input.contains(".") && !input.contains(" ") -> "https://$input"
            else -> "https://www.google.com/search?q=" + Uri.encode(input)
        }

        navigateToUrl(finalUrl)
    }

    private fun attachTab(tab: BrowserTab?) {
        val activeTab = tab ?: return
        val wv = activeTab.webView ?: return

        // 1. Detach other WebViews from container, retaining ntpView, and pause background tabs
        val toRemove = mutableListOf<View>()
        for (i in 0 until binding.webContainer.childCount) {
            val child = binding.webContainer.getChildAt(i)
            if (child is WebView && child != wv) {
                child.onPause()
                toRemove.add(child)
            }
        }
        for (v in toRemove) {
            binding.webContainer.removeView(v)
        }

        // 2. Attach this tab's WebView and resume it
        if (activeTab.url == "zyro://home" || activeTab.url.isEmpty() || activeTab.url == "about:blank") {
            wv.visibility = View.GONE
        }
        if (wv.parent == null) {
            binding.webContainer.addView(wv)
        } else if (wv.parent != binding.webContainer) {
            (wv.parent as? ViewGroup)?.removeView(wv)
            binding.webContainer.addView(wv)
        }
        wv.onResume()
        applyDesktopMode(wv, activeTab.isDesktopMode)

        // 3. Set up clients for this active tab
        setupTabWebViewClients(activeTab)

        // 4. Update tab counts
        val countStr = tabManager.tabCount.toString()
        binding.tvBottomTabCount.text = countStr
        binding.tabSwitcherView.tvSwitcherTabCount.text = countStr

        // 5. Update Omnibox & view visibility
        if (activeTab.url == "zyro://home" || activeTab.url.isEmpty() || activeTab.url == "about:blank") {
            showDashboard(true)
            binding.etUrl.setText("")
        } else {
            showDashboard(false)
            binding.etUrl.setText(activeTab.url)
            if (wv.url.isNullOrEmpty()) {
                wv.loadUrl(activeTab.url)
            }
        }
    }

    private fun setupTabWebViewClients(activeTab: BrowserTab) {
        val wv = activeTab.webView ?: return

        wv.webViewClient = ZyroWebViewClient(
            onPageStartedCallback = { url, favicon ->
                if (tabManager.currentTab == activeTab) {
                    if (!binding.etUrl.hasFocus()) {
                        binding.etUrl.setText(url)
                    }
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRefresh.setImageResource(R.drawable.ic_close)
                }
                if (!url.isNullOrEmpty() && url != "about:blank") activeTab.url = url
                if (favicon != null) activeTab.favicon = favicon
                pageTrackersBlocked.set(0)
            },
            onPageFinishedCallback = { url ->
                if (tabManager.currentTab == activeTab) {
                    if (!binding.etUrl.hasFocus()) {
                        binding.etUrl.setText(url)
                    }
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.btnRefresh.setImageResource(R.drawable.ic_refresh)
                }
                if (!url.isNullOrEmpty() && url != "about:blank") activeTab.url = url
                // When in desktop mode, ensure desktop viewport tag is injected
                if (activeTab.isDesktopMode) {
                    wv.evaluateJavascript(DESKTOP_VIEWPORT_SCRIPT, null)
                }
            },
            onSslStateChanged = { isSecure ->
                if (tabManager.currentTab == activeTab) {
                    val color = if (isSecure) getColor(R.color.zyro_teal) else getColor(R.color.zyro_text_secondary)
                    binding.sslIndicator.setColorFilter(color)
                }
            },
            onTrackerBlockedCallback = {
                pageTrackersBlocked.incrementAndGet()
                totalTrackersBlocked.incrementAndGet()
                if (binding.ntpView.root.visibility == View.VISIBLE) {
                    statsHandler.removeCallbacks(statsRunnable)
                    statsHandler.postDelayed(statsRunnable, 500)
                }
            }
        )

        wv.webChromeClient = ZyroChromeClient(
            onProgressChangedCallback = { progress ->
                if (tabManager.currentTab == activeTab) {
                    binding.progressBar.progress = progress
                    if (progress >= 100) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            },
            onReceivedTitleCallback = { title ->
                activeTab.title = title ?: "Zyro Browser"
            },
            onShowCustomViewCallback = { view, callback ->
                showCustomView(view, callback)
            },
            onHideCustomViewCallback = {
                hideCustomView()
            },
            onShowFileChooserCallback = { callback, params ->
                filePathCallback?.onReceiveValue(null)
                filePathCallback = callback
                val intent = params?.createIntent() ?: Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                }
                try {
                    fileChooserLauncher.launch(intent)
                    true
                } catch (e: Exception) {
                    filePathCallback = null
                    false
                }
            },
            onReceivedIconCallback = { icon ->
                if (icon != null) {
                    activeTab.favicon = icon
                }
            }
        )

        // Native DownloadManager
        wv.setDownloadListener { url, userAgent, contentDisposition, mimetype, _ ->
            try {
                val request = DownloadManager.Request(Uri.parse(url)).apply {
                    setMimeType(mimetype)
                    val cookies = CookieManager.getInstance().getCookie(url)
                    addRequestHeader("cookie", cookies)
                    addRequestHeader("User-Agent", userAgent)
                    setDescription("Downloading via Zyro Browser...")
                    val fileName = URLUtil.guessFileName(url, contentDisposition, mimetype)
                    setTitle(fileName)
                    setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                }
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun captureThumbnail(tab: BrowserTab?) {
        val current = tab ?: return
        val wv = current.webView ?: return
        if (current.url == "zyro://home") return
        if (wv.width <= 0 || wv.height <= 0) return

        try {
            val previewWidth = 360
            val previewHeight = ((wv.height.toFloat() / wv.width.toFloat()) * previewWidth).toInt().coerceIn(200, 600)
            val bitmap = Bitmap.createBitmap(previewWidth, previewHeight, Bitmap.Config.RGB_565)
            val canvas = Canvas(bitmap)
            val scale = previewWidth.toFloat() / wv.width.toFloat()
            canvas.scale(scale, scale)
            wv.draw(canvas)

            current.thumbnail = bitmap
        } catch (e: Exception) {
            Log.w("MainActivity", "Failed to capture tab thumbnail: ${e.message}")
        }
    }

    private fun getCurrentWebView(): WebView? = tabManager.currentTab?.webView

    private fun showShieldsDialog() {
        AlertDialog.Builder(this)
            .setTitle("🦁 Zyro Shields Protection")
            .setMessage("Shields is ON.\n\n🛡️ ${pageTrackersBlocked.get()} trackers & ads blocked on this page.\n🚀 ${totalTrackersBlocked.get()} total threats blocked overall.")
            .setIcon(R.drawable.ic_shield)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun showBraveBottomSheetMenu() {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_menu, null)
        dialog.setContentView(sheetView)

        val switchDesktop = sheetView.findViewById<SwitchMaterial>(R.id.switch_desktop)
        val currentTab = tabManager.currentTab
        val isDesktop = currentTab?.isDesktopMode == true
        switchDesktop.isChecked = isDesktop
        switchDesktop.isClickable = false
        switchDesktop.isFocusable = false

        sheetView.findViewById<View>(R.id.menu_desktop_site).setOnClickListener {
            val newMode = !(tabManager.currentTab?.isDesktopMode == true)
            switchDesktop.isChecked = newMode
            toggleDesktopMode(newMode)
            dialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.menu_new_tab).setOnClickListener {
            val newTab = tabManager.createNewTab("zyro://home")
            attachTab(newTab)
            showDashboard(true)
            binding.etUrl.setText("")
            dialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.menu_settings).setOnClickListener {
            dialog.dismiss()
            showSettingsDialog()
        }

        sheetView.findViewById<View>(R.id.menu_test_safelink).setOnClickListener {
            dialog.dismiss()
            startSafeLinkVerification("https://example.com")
        }

        sheetView.findViewById<View>(R.id.menu_share).setOnClickListener {
            val url = getCurrentWebView()?.url ?: ""
            if (url.isNotEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, url)
                }
                startActivity(Intent.createChooser(shareIntent, "Share link"))
            }
            dialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.menu_downloads).setOnClickListener {
            startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))
            dialog.dismiss()
        }

        sheetView.findViewById<View>(R.id.menu_clear_data).setOnClickListener {
            clearBrowsingData()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSettingsDialog() {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.dialog_settings, null)
        dialog.setContentView(sheetView)

        val rgTheme = sheetView.findViewById<RadioGroup>(R.id.rg_theme)
        val rbDark = sheetView.findViewById<RadioButton>(R.id.rb_theme_dark)
        val rbLight = sheetView.findViewById<RadioButton>(R.id.rb_theme_light)

        if (isDarkTheme) {
            rbDark.isChecked = true
        } else {
            rbLight.isChecked = true
        }

        rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val newDarkTheme = (checkedId == R.id.rb_theme_dark)
            if (newDarkTheme != isDarkTheme) {
                isDarkTheme = newDarkTheme
                prefs.edit().putBoolean("is_dark_theme", isDarkTheme).apply()
                applyThemeStyles(isDarkTheme)
                Toast.makeText(
                    this,
                    if (isDarkTheme) "Dark Theme Applied" else "White Theme Applied",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun applyDesktopMode(wv: WebView?, enableDesktop: Boolean) {
        if (wv == null) return
        val mobileUa = WebSettings.getDefaultUserAgent(this)
        wv.settings.apply {
            userAgentString = if (enableDesktop) DESKTOP_USER_AGENT else mobileUa
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
        val script = if (enableDesktop) DESKTOP_VIEWPORT_SCRIPT else MOBILE_VIEWPORT_SCRIPT
        wv.evaluateJavascript(script, null)
    }

    private fun toggleDesktopMode(enableDesktop: Boolean) {
        val currentTab = tabManager.currentTab ?: return
        currentTab.isDesktopMode = enableDesktop
        val wv = currentTab.webView ?: return
        applyDesktopMode(wv, enableDesktop)
        val currentUrl = wv.url ?: currentTab.url
        if (currentUrl.isNotBlank() && currentUrl != "zyro://home") {
            wv.reload()
        }
        val modeText = if (enableDesktop) "Desktop site enabled" else "Mobile site enabled"
        Toast.makeText(this, modeText, Toast.LENGTH_SHORT).show()
    }

    private fun clearBrowsingData() {
        getCurrentWebView()?.clearCache(true)
        getCurrentWebView()?.clearHistory()
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        Toast.makeText(this, "Browsing history and cookies cleared", Toast.LENGTH_SHORT).show()
    }

    private fun setupTabSwitcher() {
        tabGridAdapter = TabGridAdapter(
            tabs = tabManager.getTabs(),
            getCurrentIndex = { tabManager.currentTabIndex },
            onTabClick = { position ->
                switchTabAndCloseSwitcher(position)
            },
            onTabClose = { position ->
                closeTabFromSwitcher(position)
            }
        )

        binding.tabSwitcherView.rvTabsGrid.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = tabGridAdapter
            setHasFixedSize(false)
        }

        binding.tabSwitcherView.btnSwitcherBack.setOnClickListener {
            hideTabSwitcher()
        }

        binding.tabSwitcherView.btnSwitcherDone.setOnClickListener {
            hideTabSwitcher()
        }

        binding.tabSwitcherView.btnSwitcherNewTab.setOnClickListener {
            openNewTabAndCloseSwitcher()
        }

        binding.tabSwitcherView.btnSwitcherNewTabTop.setOnClickListener {
            openNewTabAndCloseSwitcher()
        }

        binding.tabSwitcherView.btnSwitcherCloseAll.setOnClickListener {
            closeAllTabsFromSwitcher()
        }

        binding.tabSwitcherView.btnSwitcherMenu.setOnClickListener {
            showTabSwitcherMenu()
        }
    }

    private fun showTabSwitcher() {
        // Capture thumbnail of current foreground tab before displaying grid
        captureThumbnail(tabManager.currentTab)

        val countStr = tabManager.tabCount.toString()
        binding.tabSwitcherView.tvSwitcherTabCount.text = countStr
        binding.tvBottomTabCount.text = countStr

        tabGridAdapter.notifyDataSetChanged()
        binding.tabSwitcherView.rvTabsGrid.scrollToPosition(tabManager.currentTabIndex)

        binding.tabSwitcherView.root.visibility = View.VISIBLE
        binding.tabSwitcherView.root.bringToFront()
    }

    private fun hideTabSwitcher() {
        binding.tabSwitcherView.root.visibility = View.GONE
    }

    private fun switchTabAndCloseSwitcher(position: Int) {
        val tab = tabManager.switchTab(position) ?: return
        attachTab(tab)
        hideTabSwitcher()
    }

    private fun closeTabFromSwitcher(position: Int) {
        val wasCurrent = position == tabManager.currentTabIndex
        val activeTab = tabManager.closeTabAt(position)

        val countStr = tabManager.tabCount.toString()
        binding.tabSwitcherView.tvSwitcherTabCount.text = countStr
        binding.tvBottomTabCount.text = countStr
        tabGridAdapter.notifyDataSetChanged()

        if (wasCurrent && activeTab != null) {
            attachTab(activeTab)
        }
    }

    private fun openNewTabAndCloseSwitcher() {
        val newTab = tabManager.createNewTab("zyro://home")
        attachTab(newTab)
        hideTabSwitcher()
    }

    private fun closeAllTabsFromSwitcher() {
        val newTab = tabManager.closeAllTabs()
        attachTab(newTab)
        hideTabSwitcher()
        Toast.makeText(this, "Closed all tabs", Toast.LENGTH_SHORT).show()
    }

    private fun showTabSwitcherMenu() {
        val options = arrayOf("New Tab", "Close All Tabs")
        AlertDialog.Builder(this)
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openNewTabAndCloseSwitcher()
                    1 -> closeAllTabsFromSwitcher()
                }
            }
            .show()
    }

    // =========================================================================
    // EXTERNAL ADBLOCKER / PRIVATE DNS DETECTION & ENFORCEMENT
    // =========================================================================

    private fun setupAdBlockWarning() {
        binding.adblockWarningView.btnOpenDnsSettings.setOnClickListener {
            AdBlockDetector.openPrivateDnsSettings(this)
        }

        binding.adblockWarningView.btnCheckAdblockAgain.setOnClickListener {
            checkAdBlockerAndEnforce(manualCheck = true)
        }
    }

    private fun checkAdBlockerAndEnforce(manualCheck: Boolean = false) {
        val now = System.currentTimeMillis()
        if (!manualCheck && (now - lastAdBlockCheckTime < 60_000L)) {
            // Recently verified within last 60 seconds; skip redundant network probe
            return
        }
        lastAdBlockCheckTime = now

        if (manualCheck) {
            binding.adblockWarningView.pbCheckingAdblock.visibility = View.VISIBLE
            binding.adblockWarningView.btnCheckAdblockAgain.isEnabled = false
        }

        lifecycleScope.launch {
            val result = AdBlockDetector.detect(this@MainActivity)
            Log.d("MainActivity", "AdBlockDetector.detect: isBlocked=${result.isBlocked}, provider=${result.provider}")
            if (manualCheck) {
                binding.adblockWarningView.pbCheckingAdblock.visibility = View.GONE
                binding.adblockWarningView.btnCheckAdblockAgain.isEnabled = true
            }

            if (result.isBlocked) {
                showAdBlockWarning(result)
            } else {
                hideAdBlockWarning()
                if (manualCheck) {
                    Toast.makeText(this@MainActivity, "✅ External ad blocker disabled! Zyro Shields active.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showAdBlockWarning(result: AdBlockDetectionResult) {
        Log.d("MainActivity", "showAdBlockWarning shown for: ${result.provider}")
        binding.adblockWarningView.tvDetectedDnsName.text = if (result.provider.isNotBlank()) {
            result.provider
        } else {
            "dns.adguard.com"
        }
        binding.adblockWarningView.root.visibility = View.VISIBLE
        binding.adblockWarningView.root.elevation = 60f
        binding.adblockWarningView.root.bringToFront()

        // Apply smooth frosted glass blur to background app views on Android 12+ (API 31+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                val blur = RenderEffect.createBlurEffect(18f, 18f, Shader.TileMode.CLAMP)
                binding.webContainer.setRenderEffect(blur)
                binding.appBar.setRenderEffect(blur)
                binding.bottomToolbar.setRenderEffect(blur)
            } catch (_: Exception) {}
        }
    }

    private fun hideAdBlockWarning() {
        if (binding.adblockWarningView.root.visibility == View.GONE) return
        Log.d("MainActivity", "hideAdBlockWarning")
        binding.adblockWarningView.root.visibility = View.GONE

        // Remove background blur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            try {
                binding.webContainer.setRenderEffect(null)
                binding.appBar.setRenderEffect(null)
                binding.bottomToolbar.setRenderEffect(null)
            } catch (_: Exception) {}
        }
    }

    private fun showCustomView(view: View?, callback: WebChromeClient.CustomViewCallback?) {
        if (customView != null) {
            callback?.onCustomViewHidden()
            return
        }
        customView = view
        customViewCallback = callback
        binding.customViewContainer.addView(view)
        binding.customViewContainer.visibility = View.VISIBLE
        binding.appBar.visibility = View.GONE
        binding.bottomToolbar.visibility = View.GONE
        binding.swipeRefresh.visibility = View.GONE

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        WindowCompat.getInsetsController(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun hideCustomView() {
        if (customView == null) return
        binding.customViewContainer.visibility = View.GONE
        binding.customViewContainer.removeView(customView)
        customView = null
        customViewCallback?.onCustomViewHidden()
        binding.appBar.visibility = View.VISIBLE
        binding.bottomToolbar.visibility = View.VISIBLE
        binding.swipeRefresh.visibility = View.VISIBLE

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        WindowCompat.getInsetsController(window, window.decorView).apply {
            show(WindowInsetsCompat.Type.systemBars())
        }
    }

    private var activeStepAnimator: android.animation.ValueAnimator? = null

    private fun startSafeLinkVerification(targetUrl: String) {
        // Cancel any active animation/dialog
        activeStepAnimator?.cancel()
        activeStepAnimator = null
        stopPausableAdCountdown()
        activeBannerAdView = null
        currentAdDialog?.dismiss()
        currentAdDialog = null

        isSafeLinkActive = true
        currentSafeLinkStep = 0

        val sl = binding.safelinkView
        sl.root.visibility = View.VISIBLE
        binding.appBar.visibility = View.GONE
        binding.bottomToolbar.visibility = View.GONE

        sl.tvTargetUrl.text = targetUrl

        if (dynamicStepsList.isEmpty()) {
            for (i in 1..4) dynamicStepsList.add(getDefaultAd(i))
        }

        sl.tvSafelinkHeadline.text = "Verifying Link Safety"
        sl.tvSafelinkHeadline.setTextColor(Color.parseColor("#0F172A"))
        sl.pbSafelinkGlobal.visibility = View.VISIBLE

        // Destination button is strictly locked until ALL steps and their ads are finished
        sl.btnSafelinkContinue.isEnabled = false
        sl.btnSafelinkContinue.text = "Verifying Link Security..."
        sl.btnSafelinkContinue.alpha = 0.5f
        sl.btnSafelinkContinue.setBackgroundResource(R.drawable.bg_premium_button_disabled)

        // Reset and inflate dynamic step rows
        sl.safelinkStepsContainer.removeAllViews()
        stepViewHolders.clear()

        dynamicStepsList.forEachIndexed { index, ad ->
            val stepView = layoutInflater.inflate(R.layout.item_safelink_step, sl.safelinkStepsContainer, false)
            val tvTitle = stepView.findViewById<TextView>(R.id.tv_step_title)
            val tvSub = stepView.findViewById<TextView>(R.id.tv_step_subtitle)
            val tvBadge = stepView.findViewById<TextView>(R.id.tv_step_badge)
            val pb = stepView.findViewById<android.widget.ProgressBar>(R.id.pb_step)
            val divider = stepView.findViewById<View>(R.id.step_divider)
            val ivIconBg = stepView.findViewById<android.widget.ImageView>(R.id.iv_step_icon_bg)
            val ivCheck = stepView.findViewById<android.widget.ImageView>(R.id.iv_step_check)
            val pbSpinner = stepView.findViewById<android.widget.ProgressBar>(R.id.pb_step_spinner)
            val tvNum = stepView.findViewById<TextView>(R.id.tv_step_number)

            tvNum?.text = "${index + 1}"
            tvTitle.text = ad.stepName
            tvSub.text = ad.stepSubtitle
            tvBadge.text = if (index == 0) "Scanning" else "Pending"
            tvBadge.setTextColor(if (index == 0) getColor(R.color.premium_blue) else getColor(R.color.premium_text_muted))
            tvTitle.setTextColor(if (index == 0) getColor(R.color.premium_text_primary) else getColor(R.color.premium_text_secondary))
            pb.progress = 0
            pb.visibility = if (index == 0) android.view.View.VISIBLE else android.view.View.INVISIBLE

            // Set active state for first step
            if (index == 0) {
                ivIconBg?.setBackgroundResource(R.drawable.bg_step_icon_active)
                pbSpinner?.visibility = View.VISIBLE
                tvNum?.visibility = View.GONE
            }

            divider.visibility = if (index == dynamicStepsList.size - 1) View.GONE else View.VISIBLE

            // Update pipeline status
            try { sl.tvPipelineStatus.text = "Step 1 of ${dynamicStepsList.size} in progress" } catch (e: Exception) {}

            stepViewHolders.add(StepViewHolder(stepView, tvTitle, tvSub, tvBadge, pb))
            sl.safelinkStepsContainer.addView(stepView)
        }

        sl.btnCloseSafelink.setOnClickListener {
            hideSafeLinkScreen()
        }

        // Start from step 0
        runStepWithProgress(0, targetUrl)
    }

    private fun initSafeLinkAds() {
        dynamicStepsList.clear()
        for (i in 1..4) {
            dynamicStepsList.add(getDefaultAd(i))
        }
        try {
            val db = FirebaseFirestore.getInstance()
            db.collection("safelink_ads").addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener
                val loaded = mutableListOf<SafeLinkAd>()
                for (doc in snapshot.documents) {
                    val ad = SafeLinkAd.fromMap(doc.id, doc.data ?: emptyMap())
                    loaded.add(ad)
                }
                loaded.sortBy { it.order }
                if (loaded.isNotEmpty()) {
                    dynamicStepsList.clear()
                    dynamicStepsList.addAll(loaded)
                }
            }
        } catch (_: Exception) {
            // Offline fallback to default ads
        }
    }

    private fun getDefaultAd(step: Int): SafeLinkAd {
        return when (step) {
            1 -> SafeLinkAd(
                stepId = "step_1",
                stepName = "Domain Verification",
                stepSubtitle = "Checking WHOIS, DNS records & reputation authority",
                order = 1,
                adType = "BANNER",
                badge = "SECURITY CHECK (1/4)",
                title = "Zyro Quantum VPN",
                description = "Military-grade 512-bit cipher for zero-log browsing. Ultra-low 5ms latency.",
                ctaText = "Get Quantum Shield Free",
                ctaUrl = "https://example.com/vpn",
                durationSeconds = 3
            )
            2 -> SafeLinkAd(
                stepId = "step_2",
                stepName = "SSL Certificate Check",
                stepSubtitle = "Validating TLS 1.3 cipher suite & integrity",
                order = 2,
                adType = "NATIVE",
                badge = "SECURITY CHECK (2/4)",
                title = "Zyro Cloud Antivirus",
                description = "Real-time AI malware prevention and zero-day threat quarantine engine.",
                ctaText = "Activate Realtime Scan",
                ctaUrl = "https://example.com/antivirus",
                durationSeconds = 3
            )
            3 -> SafeLinkAd(
                stepId = "step_3",
                stepName = "Safe Browsing Check",
                stepSubtitle = "Scanning phishing, malware & zero-day databases",
                order = 3,
                adType = "INTERSTITIAL",
                badge = "SECURITY CHECK (3/4)",
                title = "Zyro Crypto Vault",
                description = "Hardware-isolated biometric password & private key security locker.",
                ctaText = "Secure Your Keys",
                ctaUrl = "https://example.com/vault",
                durationSeconds = 3
            )
            else -> SafeLinkAd(
                stepId = "step_4",
                stepName = "Redirection Check",
                stepSubtitle = "Detecting tracking bounces & URL hijacks",
                order = 4,
                adType = "REWARDED",
                badge = "SECURITY CHECK (4/4)",
                title = "Zyro AdBlock Pro",
                description = "Stop tracking cookies, malicious banners, and popups forever.",
                ctaText = "Block All Trackers",
                ctaUrl = "https://example.com/adblock",
                durationSeconds = 3
            )
        }
    }

    private fun runStepWithProgress(stepIndex: Int, targetUrl: String) {
        if (!isSafeLinkActive) return
        if (stepIndex !in stepViewHolders.indices || stepIndex !in dynamicStepsList.indices) {
            finishSafeLinkSuccess(targetUrl)
            return
        }

        currentSafeLinkStep = stepIndex
        val holder = stepViewHolders[stepIndex]
        val currentAd = dynamicStepsList[stepIndex]

        // Premium UI: update active step styling
        holder.title.setTextColor(getColor(R.color.premium_text_primary))
        holder.badge.setTextColor(getColor(R.color.premium_blue))
        holder.badge.text = "Scanning"

        // Update step icon to active (spinner)
        try {
            val iconBg = holder.root.findViewById<android.widget.ImageView>(R.id.iv_step_icon_bg)
            val spinner = holder.root.findViewById<android.widget.ProgressBar>(R.id.pb_step_spinner)
            val check = holder.root.findViewById<android.widget.ImageView>(R.id.iv_step_check)
            val numTv = holder.root.findViewById<TextView>(R.id.tv_step_number)
            iconBg?.setBackgroundResource(R.drawable.bg_step_icon_active)
            spinner?.visibility = View.VISIBLE
            check?.visibility = View.GONE
            numTv?.visibility = View.GONE
        } catch (e: Exception) {}

        // Show progress bar for this step
        holder.progressBar.visibility = View.VISIBLE
        holder.progressBar.setProgressDrawable(resources.getDrawable(R.drawable.bg_premium_progress, theme))
        holder.progressBar.max = 100
        holder.progressBar.progress = 0

        // Update pipeline status label
        try {
            binding.safelinkView.tvPipelineStatus.text = "Step ${stepIndex + 1} of ${dynamicStepsList.size} in progress"
        } catch (e: Exception) {}

        val durationMs = (currentAd.durationSeconds.coerceAtLeast(1) * 1000).toLong()

        // Proactively preload the full-screen ad required for this step and subsequent step
        preloadAdForStep(stepIndex)
        if (stepIndex + 1 < dynamicStepsList.size) {
            preloadAdForStep(stepIndex + 1)
        }

        activeStepAnimator?.cancel()
        var isCancelled = false
        val animator = android.animation.ValueAnimator.ofInt(0, 100).apply {
            duration = durationMs
            interpolator = android.view.animation.LinearInterpolator()
            addUpdateListener { anim ->
                if (!isSafeLinkActive || isCancelled || currentSafeLinkStep != stepIndex) return@addUpdateListener
                val progress = anim.animatedValue as Int
                holder.progressBar.progress = progress
                holder.badge.text = "$progress%"
            }
            addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationCancel(animation: android.animation.Animator) {
                    isCancelled = true
                }

                override fun onAnimationEnd(animation: android.animation.Animator) {
                    if (isCancelled || !isSafeLinkActive || currentSafeLinkStep != stepIndex) return
                    holder.progressBar.progress = 100
                    holder.badge.text = "Verified"
                    holder.badge.setTextColor(getColor(R.color.premium_green))

                    // Premium UI: mark step as done (green checkmark)
                    try {
                        val iconBg = holder.root.findViewById<android.widget.ImageView>(R.id.iv_step_icon_bg)
                        val spinner = holder.root.findViewById<android.widget.ProgressBar>(R.id.pb_step_spinner)
                        val check = holder.root.findViewById<android.widget.ImageView>(R.id.iv_step_check)
                        iconBg?.setBackgroundResource(R.drawable.bg_step_icon_done)
                        spinner?.visibility = View.GONE
                        check?.visibility = View.VISIBLE
                    } catch (e: Exception) {}

                    // Activate next step's icon to pending+highlighted
                    val nextIndex = stepIndex + 1
                    if (nextIndex in stepViewHolders.indices) {
                        val nextHolder = stepViewHolders[nextIndex]
                        nextHolder.title.setTextColor(getColor(R.color.premium_text_primary))
                        nextHolder.badge.text = "Pending"
                        try {
                            nextHolder.root.findViewById<android.widget.ImageView>(R.id.iv_step_icon_bg)
                                ?.setBackgroundResource(R.drawable.bg_step_icon_active)
                        } catch (e: Exception) {}
                    }

                    // Step 100% complete! Notify helper to enable manual next step button
                    SafeLinkInputHelper.onStepProgressFinished(this@MainActivity, stepIndex, targetUrl)
                }
            })
        }
        activeStepAnimator = animator
        animator.start()
    }

    private fun preloadAdForStep(stepIndex: Int) {
        if (stepIndex !in dynamicStepsList.indices) return
        val stepAd = dynamicStepsList[stepIndex]
        val adType = stepAd.adType.uppercase()
        when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_GAM -> {
                if (gamConfig.adsEnabled && gamConfig.safelinkGamAdsEnabled) {
                    when (adType) {
                        "INTERSTITIAL" -> GoogleAdManager.instance.preloadInterstitial(this)
                        "REWARDED", "REWARDED_INTERSTITIAL" -> GoogleAdManager.instance.preloadRewarded(this)
                    }
                }
            }
            AdNetworkConfig.PLATFORM_GOOGLE -> {
                if (googleAdsConfig.adsEnabled && googleAdsConfig.safelinkGoogleAdsEnabled) {
                    when (adType) {
                        "INTERSTITIAL" -> loadAdMobInterstitial()
                        "REWARDED", "REWARDED_INTERSTITIAL" -> loadAdMobRewarded()
                    }
                }
            }
            else -> {}
        }
    }

    private fun initAdMob() {
        try {
            // Register test device ID to guarantee immediate test ad delivery without rate limits
            val configuration = RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("C461CDBBAD2423FDF56B59308C7AA4AF"))
                .build()
            MobileAds.setRequestConfiguration(configuration)
            MobileAds.initialize(this) {}
            loadAdMobInterstitial()
            loadAdMobRewarded()

            // Real-time synchronization of Master Ad Network Config
            val db = FirebaseFirestore.getInstance()
            db.collection("app_config").document("ad_network")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                    val data = snapshot.data ?: return@addSnapshotListener
                    adNetworkConfig = AdNetworkConfig.fromMap(data)
                }

            // Real-time synchronization of Meta Ads config from Firestore
            db.collection("app_config").document("facebook_ads")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                    val data = snapshot.data ?: return@addSnapshotListener
                    metaAdsConfig = MetaAdsConfig.fromMap(data)
                    MetaAdManager.instance.updateConfig(metaAdsConfig)
                }

            // Initialize Meta Audience Network
            MetaAdManager.instance.init(this)

            // Real-time synchronization of AppLovin MAX config from Firestore
            db.collection("app_config").document("applovin_ads")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                    val data = snapshot.data ?: return@addSnapshotListener
                    applovinAdsConfig = AppLovinAdsConfig.fromMap(data)
                    AppLovinAdManager.instance.updateConfig(applovinAdsConfig)
                }

            // Initialize AppLovin MAX
            AppLovinAdManager.instance.init(this)

            // Real-time synchronization of Google Ads config from Firestore
            db.collection("app_config").document("google_ads")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                    val data = snapshot.data ?: return@addSnapshotListener
                    googleAdsConfig = GoogleAdsConfig.fromMap(data)
                    if (mInterstitialAd == null && googleAdsConfig.adsEnabled && googleAdsConfig.safelinkGoogleAdsEnabled) {
                        loadAdMobInterstitial()
                    }
                    if (mRewardedAd == null && googleAdsConfig.adsEnabled && googleAdsConfig.safelinkGoogleAdsEnabled) {
                        loadAdMobRewarded()
                    }
                }

            // Real-time synchronization of Google Ad Manager config from Firestore
            db.collection("app_config").document("google_ad_manager")
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null || !snapshot.exists()) return@addSnapshotListener
                    val data = snapshot.data ?: return@addSnapshotListener
                    gamConfig = GoogleAdManagerConfig.fromMap(data)
                    GoogleAdManager.instance.updateConfig(gamConfig)
                    if (gamConfig.adsEnabled && gamConfig.safelinkGamAdsEnabled) {
                        GoogleAdManager.instance.preloadInterstitial(this)
                        GoogleAdManager.instance.preloadRewarded(this)
                    }
                }

            // Initialize Google Ad Manager
            GoogleAdManager.instance.init(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadAdMobInterstitial() {
        if (!googleAdsConfig.adsEnabled || !googleAdsConfig.safelinkGoogleAdsEnabled || isAdLoading) return
        isAdLoading = true

        val adUnitId = if (googleAdsConfig.testMode) {
            "ca-app-pub-3940256099942544/1033173712"
        } else {
            googleAdsConfig.interstitialAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/1033173712" }
        }

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                isAdLoading = false
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                mInterstitialAd = null
                isAdLoading = false
            }
        })
    }

    private fun loadAdMobRewarded() {
        if (!googleAdsConfig.adsEnabled || !googleAdsConfig.safelinkGoogleAdsEnabled || isRewardedLoading) return
        isRewardedLoading = true

        val adUnitId = if (googleAdsConfig.testMode) {
            "ca-app-pub-3940256099942544/5224354917"
        } else {
            googleAdsConfig.rewardedAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/5224354917" }
        }

        RewardedAd.load(this, adUnitId, AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
                isRewardedLoading = false
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                mRewardedAd = null
                isRewardedLoading = false
            }
        })
    }

    private fun advanceToNextStep(stepIndex: Int, targetUrl: String) {
        if (!isSafeLinkActive) return
        val totalSteps = dynamicStepsList.size
        val isLastStep = (stepIndex >= totalSteps - 1)
        if (isLastStep) {
            finishSafeLinkSuccess(targetUrl)
        } else {
            runStepWithProgress(stepIndex + 1, targetUrl)
        }
    }

    private fun showStepAdPopup(stepIndex: Int, targetUrl: String) {
        if (!isSafeLinkActive || currentSafeLinkStep != stepIndex) return

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1
        val currentAd = dynamicStepsList.getOrNull(stepIndex) ?: getDefaultAd(currentStepNum)
        val isLastStep = (stepIndex >= totalSteps - 1)

        if (adNetworkConfig.activePlatform == AdNetworkConfig.PLATFORM_APPLOVIN) {
            // APPLOVIN MAX (EXCLUSIVE ACTIVE PLATFORM)
            if (!adNetworkConfig.globalAdsEnabled || !applovinAdsConfig.enabled) {
                advanceToNextStep(stepIndex, targetUrl)
                return
            }

            when (currentAd.adType.uppercase()) {
                "INTERSTITIAL", "APP_OPEN" -> showAppLovinInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED" -> showAppLovinRewardedStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "BANNER_MREC", "NATIVE" -> showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = true)
                "CUSTOM" -> showCustomStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                else -> showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
            }
        } else if (adNetworkConfig.activePlatform == AdNetworkConfig.PLATFORM_META) {
            // META AUDIENCE NETWORK (EXCLUSIVE ACTIVE PLATFORM)
            if (!adNetworkConfig.globalAdsEnabled || !metaAdsConfig.enabled) {
                advanceToNextStep(stepIndex, targetUrl)
                return
            }

            when (currentAd.adType.uppercase()) {
                "INTERSTITIAL", "PLAYABLE" -> showMetaInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED" -> showMetaRewardedStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "BANNER_MREC", "NATIVE", "NATIVE_BANNER" -> showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = true)
                "CUSTOM" -> showCustomStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                else -> showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
            }
        } else if (adNetworkConfig.activePlatform == AdNetworkConfig.PLATFORM_GAM) {
            // GOOGLE AD MANAGER (EXCLUSIVE ACTIVE PLATFORM)
            if (!adNetworkConfig.globalAdsEnabled || !gamConfig.adsEnabled || !gamConfig.safelinkGamAdsEnabled) {
                advanceToNextStep(stepIndex, targetUrl)
                return
            }

            when (currentAd.adType.uppercase()) {
                "INTERSTITIAL" -> showGamInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED" -> showGamRewardedStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED_INTERSTITIAL" -> showGamRewardedInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "APP_OPEN" -> showGamAppOpenStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "NATIVE" -> showGamNativeStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                "CUSTOM" -> showCustomStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                else -> showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
            }
        } else {
            // GOOGLE ADMOB (EXCLUSIVE ACTIVE PLATFORM)
            if (!adNetworkConfig.globalAdsEnabled || !googleAdsConfig.adsEnabled || !googleAdsConfig.safelinkGoogleAdsEnabled) {
                advanceToNextStep(stepIndex, targetUrl)
                return
            }

            when (currentAd.adType.uppercase()) {
                "INTERSTITIAL" -> showInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED" -> showRewardedStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "REWARDED_INTERSTITIAL" -> showRewardedInterstitialStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "APP_OPEN" -> showAppOpenStepAd(stepIndex, targetUrl, currentAd, isLastStep)
                "NATIVE" -> showNativeStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                "CUSTOM" -> showCustomStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                else -> showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
            }
        }
    }

    private fun showAppLovinInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Preparing full-screen security verification...",
            onFallback = { showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false) }
        ) { dismissLoading ->
            AppLovinAdManager.instance.showInterstitial(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onDismissed = { advanceToNextStep(stepIndex, targetUrl) },
                onError = {
                    dismissLoading()
                    showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
                }
            )
        }
    }

    private fun showAppLovinRewardedStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Buffering full-screen video verification...",
            onFallback = { showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false) }
        ) { dismissLoading ->
            AppLovinAdManager.instance.showRewardedVideo(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onRewardCompleted = { userEarnedReward ->
                    dismissLoading()
                    if (userEarnedReward) {
                        advanceToNextStep(stepIndex, targetUrl)
                    } else {
                        showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                },
                onError = {
                    dismissLoading()
                    showAppLovinBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
                }
            )
        }
    }

    private fun showAppLovinBannerStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean,
        isMrec: Boolean = false
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        val formatLabel = if (isMrec) "APPLOVIN MAX MREC (300X250)" else "APPLOVIN MAX BANNER (320X50)"
        tvBadge.text = "SECURITY CHECK · SPONSORED"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        try {
            val listener = object : com.applovin.mediation.MaxAdViewAdListener {
                override fun onAdLoaded(ad: com.applovin.mediation.MaxAd) {
                    layoutLoading.visibility = View.GONE
                }
                override fun onAdDisplayed(ad: com.applovin.mediation.MaxAd) {}
                override fun onAdHidden(ad: com.applovin.mediation.MaxAd) {}
                override fun onAdClicked(ad: com.applovin.mediation.MaxAd) {}
                override fun onAdLoadFailed(adUnitId: String, error: com.applovin.mediation.MaxError) {
                    layoutLoading.visibility = View.GONE
                    populateCustomFallback(admobSlot, currentAd)
                }
                override fun onAdDisplayFailed(ad: com.applovin.mediation.MaxAd, error: com.applovin.mediation.MaxError) {
                    layoutLoading.visibility = View.GONE
                    populateCustomFallback(admobSlot, currentAd)
                }
                override fun onAdExpanded(ad: com.applovin.mediation.MaxAd) {}
                override fun onAdCollapsed(ad: com.applovin.mediation.MaxAd) {}
            }
            val adView = AppLovinAdManager.instance.createBannerView(this, isMrec, listener)
            activeBannerAdView = adView
            admobSlot.removeAllViews()
            admobSlot.addView(adView)
        } catch (e: Exception) {
            layoutLoading.visibility = View.GONE
            populateCustomFallback(admobSlot, currentAd)
        }

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            activeBannerAdView = null
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { onAdClosedAndProceed() }
        btnClose.setOnClickListener { onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun showMetaInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Preparing full-screen security verification...",
            onFallback = { showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false) }
        ) { dismissLoading ->
            MetaAdManager.instance.showInterstitial(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onDismissed = { advanceToNextStep(stepIndex, targetUrl) },
                onError = {
                    dismissLoading()
                    showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
                }
            )
        }
    }

    private fun showMetaRewardedStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Buffering full-screen video verification...",
            onFallback = { showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false) }
        ) { dismissLoading ->
            MetaAdManager.instance.showRewardedVideo(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onRewardCompleted = { userEarnedReward ->
                    dismissLoading()
                    if (userEarnedReward) {
                        advanceToNextStep(stepIndex, targetUrl)
                    } else {
                        showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                },
                onError = {
                    dismissLoading()
                    showMetaBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep, isMrec = false)
                }
            )
        }
    }

    private fun showMetaBannerStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean,
        isMrec: Boolean = false
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        val formatLabel = if (isMrec) "META AUDIENCE MREC (250DP)" else "META AUDIENCE BANNER (50DP)"
        tvBadge.text = "SECURITY CHECK · SPONSORED"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        try {
            val listener = object : com.facebook.ads.AdListener {
                override fun onError(ad: com.facebook.ads.Ad?, error: com.facebook.ads.AdError?) {
                    layoutLoading.visibility = View.GONE
                    populateCustomFallback(admobSlot, currentAd)
                }

                override fun onAdLoaded(ad: com.facebook.ads.Ad?) {
                    layoutLoading.visibility = View.GONE
                }

                override fun onAdClicked(ad: com.facebook.ads.Ad?) {}
                override fun onLoggingImpression(ad: com.facebook.ads.Ad?) {}
            }
            val adView = MetaAdManager.instance.createBannerView(this, isMrec, listener)
            activeBannerAdView = adView
            admobSlot.removeAllViews()
            admobSlot.addView(adView)
        } catch (e: Exception) {
            layoutLoading.visibility = View.GONE
            populateCustomFallback(admobSlot, currentAd)
        }

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            activeBannerAdView = null
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { onAdClosedAndProceed() }
        btnClose.setOnClickListener { onAdClosedAndProceed() }

        alertDialog.show()
    }

    // =========================================================================
    // GOOGLE AD MANAGER (GAM) STEP ADS
    // =========================================================================

    private fun showGamInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Preparing full-screen security verification...",
            onFallback = { showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            GoogleAdManager.instance.showInterstitial(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onDismissed = { advanceToNextStep(stepIndex, targetUrl) },
                onError = {
                    dismissLoading()
                    showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            )
        }
    }

    private fun showGamRewardedStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Buffering full-screen video verification...",
            onFallback = { showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            GoogleAdManager.instance.showRewarded(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onUserEarnedReward = { /* marked in GoogleAdManager */ },
                onDismissed = { rewardEarned ->
                    dismissLoading()
                    if (rewardEarned) {
                        advanceToNextStep(stepIndex, targetUrl)
                    } else {
                        showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                },
                onError = {
                    dismissLoading()
                    showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            )
        }
    }

    private fun showGamRewardedInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Buffering full-screen video verification...",
            onFallback = { showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            GoogleAdManager.instance.showRewardedInterstitial(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onUserEarnedReward = { /* marked in GoogleAdManager */ },
                onDismissed = { rewardEarned ->
                    dismissLoading()
                    if (rewardEarned) {
                        advanceToNextStep(stepIndex, targetUrl)
                    } else {
                        showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                },
                onError = {
                    dismissLoading()
                    showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            )
        }
    }

    private fun showGamAppOpenStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Preparing full-screen security verification...",
            onFallback = { showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            GoogleAdManager.instance.showAppOpenAd(
                activity = this@MainActivity,
                onAdLoaded = { dismissLoading() },
                onDismissed = { advanceToNextStep(stepIndex, targetUrl) },
                onError = {
                    dismissLoading()
                    showGamBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            )
        }
    }

    private fun showGamBannerStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        tvBadge.text = "SECURITY CHECK · GOOGLE AD MANAGER"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        var gamAdView: com.google.android.gms.ads.admanager.AdManagerAdView? = null
        try {
            gamAdView = GoogleAdManager.instance.createBannerView(
                activity = this@MainActivity,
                isAdaptive = true,
                listener = object : AdListener() {
                    override fun onAdLoaded() {
                        layoutLoading.visibility = View.GONE
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        layoutLoading.visibility = View.GONE
                        populateCustomFallback(admobSlot, currentAd)
                    }
                }
            )
            activeBannerAdView = gamAdView
            admobSlot.removeAllViews()
            admobSlot.addView(gamAdView)
        } catch (e: Exception) {
            layoutLoading.visibility = View.GONE
            populateCustomFallback(admobSlot, currentAd)
        }

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            activeBannerAdView = null
            gamAdView?.destroy()
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { if (canProceed) onAdClosedAndProceed() }
        btnClose.setOnClickListener { if (canProceed) onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun showGamNativeStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        tvBadge.text = "SECURITY CHECK · GOOGLE AD MANAGER"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        val nativeAdView = layoutInflater.inflate(R.layout.layout_safelink_native_ad, admobSlot, false) as NativeAdView
        admobSlot.removeAllViews()
        admobSlot.addView(nativeAdView)

        var loadedNativeAd: NativeAd? = null
        GoogleAdManager.instance.loadNativeAd(
            context = this@MainActivity,
            onLoaded = { nativeAd ->
                loadedNativeAd = nativeAd
                layoutLoading.visibility = View.GONE
                populateNativeAdView(nativeAd, nativeAdView)
            },
            onError = { _ ->
                layoutLoading.visibility = View.GONE
                populateCustomFallback(admobSlot, currentAd)
            }
        )

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            loadedNativeAd?.destroy()
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { if (canProceed) onAdClosedAndProceed() }
        btnClose.setOnClickListener { if (canProceed) onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun showBannerStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        tvBadge.text = "SECURITY CHECK · SPONSORED"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        val adView = AdView(this).apply {
            adUnitId = if (googleAdsConfig.testMode) {
                "ca-app-pub-3940256099942544/6300978111" // Google Fixed Banner Demo
            } else {
                googleAdsConfig.bannerAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/6300978111" }
            }
            setAdSize(AdSize.BANNER)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    layoutLoading.visibility = View.GONE
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    layoutLoading.visibility = View.GONE
                    populateCustomFallback(admobSlot, currentAd)
                }
            }
        }
        activeBannerAdView = adView
        admobSlot.removeAllViews()
        admobSlot.addView(adView)
        adView.loadAd(AdRequest.Builder().build())

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            activeBannerAdView = null
            adView.destroy()
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { if (canProceed) onAdClosedAndProceed() }
        btnClose.setOnClickListener { if (canProceed) onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun showNativeStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        tvBadge.text = "SECURITY CHECK · SPONSORED"
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        val nativeAdView = layoutInflater.inflate(R.layout.layout_safelink_native_ad, admobSlot, false) as NativeAdView
        admobSlot.removeAllViews()
        admobSlot.addView(nativeAdView)

        val adUnitId = if (googleAdsConfig.testMode) {
            "ca-app-pub-3940256099942544/2247696110" // Google Demo Native ID
        } else {
            googleAdsConfig.nativeAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/2247696110" }
        }

        var loadedNativeAd: NativeAd? = null
        val adLoader = AdLoader.Builder(this, adUnitId)
            .forNativeAd { nativeAd ->
                loadedNativeAd = nativeAd
                layoutLoading.visibility = View.GONE
                populateNativeAdView(nativeAd, nativeAdView)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    layoutLoading.visibility = View.GONE
                    populateCustomFallback(admobSlot, currentAd)
                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            loadedNativeAd?.destroy()
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { if (canProceed) onAdClosedAndProceed() }
        btnClose.setOnClickListener { if (canProceed) onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        val headlineView = adView.findViewById<TextView>(R.id.ad_headline)
        val bodyView = adView.findViewById<TextView>(R.id.ad_body)
        val ctaView = adView.findViewById<TextView>(R.id.ad_call_to_action)
        val iconView = adView.findViewById<ImageView>(R.id.ad_app_icon)
        val advertiserView = adView.findViewById<TextView>(R.id.ad_advertiser)

        adView.headlineView = headlineView
        adView.bodyView = bodyView
        adView.callToActionView = ctaView
        adView.iconView = iconView
        adView.advertiserView = advertiserView

        headlineView.text = nativeAd.headline ?: "Recommended Security Tool"

        if (nativeAd.body != null) {
            bodyView.visibility = View.VISIBLE
            bodyView.text = nativeAd.body
        } else {
            bodyView.visibility = View.GONE
        }

        if (nativeAd.callToAction != null) {
            ctaView.visibility = View.VISIBLE
            ctaView.text = nativeAd.callToAction
        } else {
            ctaView.visibility = View.VISIBLE
            ctaView.text = "LEARN MORE"
        }

        if (nativeAd.icon != null) {
            iconView.visibility = View.VISIBLE
            iconView.setImageDrawable(nativeAd.icon?.drawable)
        } else {
            iconView.visibility = View.GONE
        }

        if (nativeAd.advertiser != null) {
            advertiserView.visibility = View.VISIBLE
            advertiserView.text = nativeAd.advertiser
        } else {
            advertiserView.visibility = View.VISIBLE
            advertiserView.text = "Verified AdMob Partner"
        }

        adView.setNativeAd(nativeAd)
    }

    private fun populateCustomFallback(admobSlot: FrameLayout, currentAd: SafeLinkAd) {
        admobSlot.removeAllViews()
        val customView = layoutInflater.inflate(R.layout.layout_safelink_custom_ad, admobSlot, false)
        val tvBadge = customView.findViewById<TextView>(R.id.custom_ad_badge)
        val tvTitle = customView.findViewById<TextView>(R.id.custom_ad_title)
        val tvDesc = customView.findViewById<TextView>(R.id.custom_ad_desc)
        val btnCta = customView.findViewById<TextView>(R.id.custom_ad_cta)

        tvBadge.text = currentAd.badge.ifEmpty { "SPONSORED SECURITY DEAL" }
        tvTitle.text = currentAd.title.ifEmpty { "Zyro Cyber Security Shield" }
        tvDesc.text = currentAd.description.ifEmpty { "Real-time AI malware protection and privacy encryption." }
        btnCta.text = currentAd.ctaText.ifEmpty { "EXPLORE SPONSOR →" }

        val ctaUrl = currentAd.ctaUrl.ifEmpty { "https://example.com" }
        btnCta.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(ctaUrl)))
            } catch (_: Exception) {}
        }

        admobSlot.addView(customView)
    }

    private fun showCustomStepAdDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        stopPausableAdCountdown()
        activeBannerAdView = null

        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_ad, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_ad_badge)
        val tvStepIndicator = dialogView.findViewById<TextView>(R.id.tv_step_indicator)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_ad_dialog_subtitle)
        val layoutLoading = dialogView.findViewById<View>(R.id.layout_ad_loading)
        val admobSlot = dialogView.findViewById<FrameLayout>(R.id.admob_view_slot)
        val tvTimerStatus = dialogView.findViewById<TextView>(R.id.tv_ad_timer_status)
        val btnProceed = dialogView.findViewById<TextView>(R.id.btn_proceed_step)
        val btnClose = dialogView.findViewById<View>(R.id.btn_close_ad)

        val totalSteps = dynamicStepsList.size
        val currentStepNum = stepIndex + 1

        tvBadge.text = currentAd.badge.ifEmpty { "SECURITY CHECK · SPONSOR" }
        tvStepIndicator.text = "CHECK $currentStepNum OF $totalSteps"
        tvSubtitle.text = "${currentAd.stepName} verified. Verifying link integrity..."

        layoutLoading.visibility = View.GONE
        populateCustomFallback(admobSlot, currentAd)

        var canProceed = false
        // Close and Proceed are strictly hidden until ad completion
        btnProceed.visibility = View.GONE
        btnProceed.alpha = 0.4f
        btnProceed.isEnabled = false
        btnProceed.text = if (isLastStep) "FINISH ALL CHECKS & OPEN LINK →" else "PROCEED TO NEXT CHECK (${currentStepNum + 1}/$totalSteps) →"
        btnClose.visibility = View.GONE
        btnClose.alpha = 0.3f
        btnClose.isEnabled = false

        val watchSeconds = currentAd.durationSeconds.coerceIn(2, 30)
        startPausableAdCountdown(
            seconds = watchSeconds,
            onTick = { sec ->
                tvTimerStatus.text = "Verifying link integrity (${sec}s)..."
            },
            onFinish = {
                canProceed = true
                tvTimerStatus.text = "Check Complete! Verified safe to proceed →"
                tvTimerStatus.setTextColor(getColor(R.color.cyber_green))
                // Reveal close and proceed buttons only after watching complete timer
                btnProceed.visibility = View.VISIBLE
                btnProceed.alpha = 1.0f
                btnProceed.isEnabled = true
                btnClose.visibility = View.VISIBLE
                btnClose.alpha = 1.0f
                btnClose.isEnabled = true
            }
        )

        fun onAdClosedAndProceed() {
            if (!canProceed || !isSafeLinkActive) return
            stopPausableAdCountdown()
            alertDialog.dismiss()
            currentAdDialog = null
            advanceToNextStep(stepIndex, targetUrl)
        }

        btnProceed.setOnClickListener { if (canProceed) onAdClosedAndProceed() }
        btnClose.setOnClickListener { if (canProceed) onAdClosedAndProceed() }

        alertDialog.show()
    }

    private fun showInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        val interstitial = mInterstitialAd
        if (interstitial != null) {
            mInterstitialAd = null
            interstitial.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadAdMobInterstitial()
                    advanceToNextStep(stepIndex, targetUrl)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    loadAdMobInterstitial()
                    showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            }
            interstitial.show(this)
        } else {
            showFullScreenAdLoading(
                stepIndex = stepIndex,
                totalSteps = dynamicStepsList.size,
                loadingText = "Preparing full-screen security verification...",
                onFallback = { showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
            ) { dismissLoading ->
                val adUnitId = if (googleAdsConfig.testMode) {
                    "ca-app-pub-3940256099942544/1033173712"
                } else {
                    googleAdsConfig.interstitialAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/1033173712" }
                }
                InterstitialAd.load(this, adUnitId, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: InterstitialAd) {
                        dismissLoading()
                        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                loadAdMobInterstitial()
                                advanceToNextStep(stepIndex, targetUrl)
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                loadAdMobInterstitial()
                                showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                            }
                        }
                        ad.show(this@MainActivity)
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        dismissLoading()
                        showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                })
            }
        }
    }

    private fun showRewardedStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        val rewarded = mRewardedAd
        if (rewarded != null) {
            mRewardedAd = null
            var rewardEarned = false
            rewarded.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadAdMobRewarded()
                    if (rewardEarned) {
                        advanceToNextStep(stepIndex, targetUrl)
                    } else {
                        showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    loadAdMobRewarded()
                    showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            }
            rewarded.show(this) { _ ->
                rewardEarned = true
            }
        } else {
            showFullScreenAdLoading(
                stepIndex = stepIndex,
                totalSteps = dynamicStepsList.size,
                loadingText = "Buffering full-screen video verification...",
                onFallback = { showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
            ) { dismissLoading ->
                val adUnitId = if (googleAdsConfig.testMode) {
                    "ca-app-pub-3940256099942544/5224354917"
                } else {
                    googleAdsConfig.rewardedAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/5224354917" }
                }
                RewardedAd.load(this, adUnitId, AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedAd) {
                        dismissLoading()
                        var rewardEarned = false
                        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                loadAdMobRewarded()
                                if (rewardEarned) {
                                    advanceToNextStep(stepIndex, targetUrl)
                                } else {
                                    showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                                }
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                loadAdMobRewarded()
                                showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                            }
                        }
                        ad.show(this@MainActivity) { _ ->
                            rewardEarned = true
                        }
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        dismissLoading()
                        showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                })
            }
        }
    }

    private fun showRewardedInterstitialStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Buffering full-screen video verification...",
            onFallback = { showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            val adUnitId = if (googleAdsConfig.testMode) {
                "ca-app-pub-3940256099942544/5354046379"
            } else {
                googleAdsConfig.rewardedInterstitialAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/5354046379" }
            }
            RewardedInterstitialAd.load(this, adUnitId, AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    dismissLoading()
                    var rewardEarned = false
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            if (rewardEarned) {
                                advanceToNextStep(stepIndex, targetUrl)
                            } else {
                                showRewardIncompleteDialog(stepIndex, targetUrl, currentAd, isLastStep)
                            }
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                        }
                    }
                    ad.show(this@MainActivity) { _ ->
                        rewardEarned = true
                    }
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    dismissLoading()
                    showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                }
            })
        }
    }

    private fun showAppOpenStepAd(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        showFullScreenAdLoading(
            stepIndex = stepIndex,
            totalSteps = dynamicStepsList.size,
            loadingText = "Preparing full-screen security verification...",
            onFallback = { showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep) }
        ) { dismissLoading ->
            val adUnitId = if (googleAdsConfig.testMode) {
                "ca-app-pub-3940256099942544/9257395921"
            } else {
                googleAdsConfig.appOpenAdUnitId.ifEmpty { "ca-app-pub-3940256099942544/9257395921" }
            }
            com.google.android.gms.ads.appopen.AppOpenAd.load(
                this,
                adUnitId,
                AdRequest.Builder().build(),
                object : com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: com.google.android.gms.ads.appopen.AppOpenAd) {
                        dismissLoading()
                        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                advanceToNextStep(stepIndex, targetUrl)
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                            }
                        }
                        ad.show(this@MainActivity)
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        dismissLoading()
                        showBannerStepAdDialog(stepIndex, targetUrl, currentAd, isLastStep)
                    }
                }
            )
        }
    }

    private fun showFullScreenAdLoading(
        stepIndex: Int,
        totalSteps: Int,
        loadingText: String,
        onFallback: () -> Unit,
        action: (dismiss: () -> Unit) -> Unit
    ) {
        currentAdDialog?.dismiss()
        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_fullscreen_loading, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val tvBadge = dialogView.findViewById<TextView>(R.id.tv_fullscreen_loading_badge)
        val tvSubtitle = dialogView.findViewById<TextView>(R.id.tv_fullscreen_loading_subtitle)
        val tvStatus = dialogView.findViewById<TextView>(R.id.tv_fullscreen_loading_status)

        val currentStepNum = stepIndex + 1
        tvBadge.text = "STEP $currentStepNum OF $totalSteps · SECURITY PROTOCOL"
        tvSubtitle.text = loadingText
        tvStatus.text = "Synchronizing high-priority fullscreen verification..."

        alertDialog.show()

        var isDismissed = false
        val dismissFunc = {
            if (!isDismissed) {
                isDismissed = true
                isFullScreenLoadingShowing = false
                fullScreenLoadingTimeoutHandler?.removeCallbacks(fullScreenLoadingTimeoutRunnable ?: Runnable {})
                fullScreenLoadingTimeoutHandler = null
                fullScreenLoadingTimeoutRunnable = null
                alertDialog.dismiss()
                currentAdDialog = null
            }
        }

        val timeoutHandler = android.os.Handler(android.os.Looper.getMainLooper())
        val timeoutRunnable = Runnable {
            if (!isDismissed && isSafeLinkActive) {
                dismissFunc()
                onFallback()
            }
        }
        isFullScreenLoadingShowing = true
        fullScreenLoadingTimeoutHandler = timeoutHandler
        fullScreenLoadingTimeoutRunnable = timeoutRunnable
        // 8-second buffer to ensure full-page ads load reliably without premature fallback to popups
        timeoutHandler.postDelayed(timeoutRunnable, 8000L)

        action {
            timeoutHandler.removeCallbacks(timeoutRunnable)
            dismissFunc()
        }
    }

    private fun showRewardIncompleteDialog(
        stepIndex: Int,
        targetUrl: String,
        currentAd: SafeLinkAd,
        isLastStep: Boolean
    ) {
        currentAdDialog?.dismiss()
        val dialogView = layoutInflater.inflate(R.layout.dialog_safelink_reward_incomplete, null)
        val alertDialog = AlertDialog.Builder(this, android.R.style.Theme_Translucent_NoTitleBar)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        alertDialog.setCanceledOnTouchOutside(false)
        currentAdDialog = alertDialog

        val btnRewatch = dialogView.findViewById<TextView>(R.id.btn_rewatch_video)
        val btnCancel = dialogView.findViewById<TextView>(R.id.btn_cancel_safelink)

        btnRewatch.setOnClickListener {
            alertDialog.dismiss()
            currentAdDialog = null
            showStepAdPopup(stepIndex, targetUrl)
        }

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
            currentAdDialog = null
            hideSafeLinkScreen()
        }

        alertDialog.show()
    }

    private fun finishSafeLinkSuccess(targetUrl: String) {
        val sl = binding.safelinkView
        sl.pbSafelinkGlobal.visibility = View.GONE

        // Premium: animate headline to "Safety Verified"
        sl.tvSafelinkHeadline.text = "Safety Verified"
        sl.tvSafelinkHeadline.setTextColor(getColor(R.color.premium_text_primary))

        // Premium: update status dot to green
        try {
            sl.vStatusDot.backgroundTintList = android.content.res.ColorStateList.valueOf(getColor(R.color.premium_green))
            sl.tvThreatLevel.text = "Defense Shield Active"
            sl.tvThreatLevel.setTextColor(getColor(R.color.premium_status_text))
        } catch (e: Exception) {}

        // Premium: update pipeline status
        try { sl.tvPipelineStatus.text = "${dynamicStepsList.size} of ${dynamicStepsList.size} checks cleared" } catch (e: Exception) {}

        val alias = currentSafeLinkAlias
        val uuid = currentSafeLinkUuid

        if (!alias.isNullOrBlank() && !uuid.isNullOrBlank()) {
            sl.btnSafelinkContinue.isEnabled = false
            sl.btnSafelinkContinue.alpha = 0.6f
            sl.btnSafelinkContinue.text = "Exchanging Gateway Token..."
            sl.btnSafelinkContinue.setBackgroundResource(R.drawable.bg_premium_button_disabled)
            sl.pbSafelinkGlobal.visibility = View.VISIBLE

            fetchSafeLinkToken(alias, uuid) { tokenResultUrl ->
                sl.pbSafelinkGlobal.visibility = View.GONE
                sl.btnSafelinkContinue.isEnabled = true
                sl.btnSafelinkContinue.alpha = 1.0f
                sl.btnSafelinkContinue.setBackgroundResource(R.drawable.bg_premium_button)
                if (tokenResultUrl != null) {
                    sl.btnSafelinkContinue.text = "Proceed to Destination →"
                    sl.btnSafelinkContinue.setOnClickListener {
                        hideSafeLinkScreen()
                        showDashboard(true)
                        openInDefaultBrowser(tokenResultUrl)
                    }
                } else {
                    sl.tvSafelinkHeadline.text = "Verification Required"
                    sl.tvSafelinkHeadline.setTextColor(getColor(R.color.cyber_warning_red))
                    sl.btnSafelinkContinue.text = "Retry Token Generation ↻"
                    sl.btnSafelinkContinue.setOnClickListener {
                        finishSafeLinkSuccess(targetUrl)
                    }
                }
            }
        } else {
            sl.btnSafelinkContinue.isEnabled = true
            sl.btnSafelinkContinue.alpha = 1.0f
            sl.btnSafelinkContinue.setBackgroundResource(R.drawable.bg_premium_button)
            sl.btnSafelinkContinue.text = "Proceed to Destination →"
            sl.btnSafelinkContinue.setOnClickListener {
                hideSafeLinkScreen()
                showDashboard(true)
                openInDefaultBrowser(targetUrl)
            }
        }
    }

    private fun openInDefaultBrowser(url: String) {
        try {
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            // Query resolving activities for this HTTP/HTTPS URL
            val pm = packageManager
            val resolveInfos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pm.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong()))
            } else {
                @Suppress("DEPRECATION")
                pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            }

            // Find default or installed browser that is NOT Zyro Browser
            val externalBrowser = resolveInfos.firstOrNull { it.activityInfo.packageName != packageName }
                ?: run {
                    val allBrowsers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        pm.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong()))
                    } else {
                        @Suppress("DEPRECATION")
                        pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
                    }
                    allBrowsers.firstOrNull { it.activityInfo.packageName != packageName }
                }

            if (externalBrowser != null) {
                intent.setPackage(externalBrowser.activityInfo.packageName)
                startActivity(intent)
            } else {
                val chooser = Intent.createChooser(intent, "Open in browser...")
                chooser.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(chooser)
            }
            // Minimize Zyro Browser so the user's default browser is in full focus
            moveTaskToBack(true)
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to launch external browser: ${e.message}")
            navigateToUrl(url)
        }
    }

    private fun fetchSafeLinkToken(
        alias: String,
        uuid: String,
        callback: (finalUrlWithToken: String?) -> Unit
    ) {
        Thread {
            try {
                val fullAlias = if (!alias.startsWith("http://") && !alias.startsWith("https://")) "https://$alias" else alias
                val endpoint = "$currentSafeLinkApiUrl?alias=" + URLEncoder.encode(fullAlias, "UTF-8") +
                        "&uuid=" + URLEncoder.encode(uuid, "UTF-8")
                val url = URL(endpoint)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 15000
                conn.readTimeout = 15000
                conn.setRequestProperty("X-App-Key", currentSafeLinkAppKey)
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 ZyroBrowser/1.0")

                val responseCode = conn.responseCode
                val stream = if (responseCode in 200..299) conn.inputStream else conn.errorStream
                val responseText = stream.bufferedReader().use { it.readText() }

                val json = JSONObject(responseText)
                if (json.optBoolean("success")) {
                    val token = json.optString("token", "")
                    val finalUrl = if (json.has("destination") && !json.isNull("destination")) {
                        json.getString("destination")
                    } else if (json.has("url") && !json.isNull("url")) {
                        json.getString("url")
                    } else if (token.isNotEmpty()) {
                        if (fullAlias.contains("?")) "$fullAlias&token=${URLEncoder.encode(token, "UTF-8")}"
                        else "$fullAlias?token=${URLEncoder.encode(token, "UTF-8")}"
                    } else {
                        fullAlias
                    }
                    runOnUiThread { callback(finalUrl) }
                } else {
                    val err = json.optString("error", "Failed")
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Gate API: $err", Toast.LENGTH_LONG).show()
                        callback(null)
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Network Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    callback(null)
                }
            }
        }.start()
    }

    private fun hideSafeLinkScreen() {
        isSafeLinkActive = false
        currentSafeLinkStep = -1
        currentSafeLinkAlias = null
        currentSafeLinkUuid = null
        activeStepAnimator?.cancel()
        activeStepAnimator = null
        stopPausableAdCountdown()
        activeBannerAdView = null
        currentAdDialog?.dismiss()
        currentAdDialog = null

        binding.safelinkView.root.visibility = View.GONE
        binding.appBar.visibility = View.VISIBLE
        binding.bottomToolbar.visibility = View.VISIBLE
    }

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.adblockWarningView.root.visibility == View.VISIBLE) {
                    moveTaskToBack(true)
                    return
                }
                if (binding.tabSwitcherView.root.visibility == View.VISIBLE) {
                    hideTabSwitcher()
                    return
                }
                if (binding.safelinkView.root.visibility == View.VISIBLE) {
                    hideSafeLinkScreen()
                    return
                }
                if (customView != null) {
                    hideCustomView()
                    return
                }
                if (binding.ntpView.root.visibility == View.VISIBLE && binding.appBar.visibility == View.VISIBLE) {
                    binding.appBar.visibility = View.GONE
                    binding.etUrl.clearFocus()
                    hideKeyboard()
                    return
                }
                val wv = getCurrentWebView()
                if (binding.ntpView.root.visibility == View.GONE) {
                    if (wv != null && wv.canGoBack()) {
                        wv.goBack()
                    } else {
                        returnToHomeScreen()
                    }
                } else if (tabManager.tabCount > 1) {
                    val tab = tabManager.closeCurrentTab()
                    attachTab(tab)
                } else {
                    finish()
                }
            }
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(binding.etUrl.windowToken, 0)
    }

    // =========================================================================
    // PUSH NOTIFICATIONS & DEEP LINK HANDLING
    // =========================================================================

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun initPushNotifications() {
        // 1. Subscribe to FCM topic "all_users"
        try {
            FirebaseMessaging.getInstance().subscribeToTopic("all_users")
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("ZyroBrowser", "Subscribed to all_users topic successfully")
                    }
                }
        } catch (e: Exception) {
            Log.w("ZyroBrowser", "FCM topic subscribe error: ${e.message}")
        }

        // 2. Real-time Firestore sync on "push_notifications"
        val db = FirebaseFirestore.getInstance()
        db.collection("push_notifications")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener { snapshots, error ->
                if (error != null || snapshots == null) return@addSnapshotListener

                val seenIds = prefs.getStringSet("seen_notif_ids", emptySet()) ?: emptySet()
                for (doc in snapshots.documents) {
                    val id = doc.id
                    if (!seenIds.contains(id)) {
                        val data = doc.data ?: continue
                        val timestamp = (data["timestamp"] as? Long) ?: 0L
                        val ageMs = System.currentTimeMillis() - timestamp

                        // Trigger if recent (under 2 hours)
                        if (ageMs in 0..(2 * 60 * 60 * 1000L)) {
                            val notif = ZyroNotification.fromMap(id, data)
                            ZyroFirebaseService.displaySystemNotification(this, notif)
                        } else {
                            // Mark older ones as seen so they don't pop up
                            val updated = HashSet(seenIds).apply { add(id) }
                            prefs.edit().putStringSet("seen_notif_ids", updated).apply()
                        }
                    }
                }
            }
    }

    private fun handleDeepLinkOrNotificationIntent(intent: Intent?) {
        val dataUri: Uri? = intent?.data
        if (dataUri != null) {
            val scheme = dataUri.scheme?.lowercase() ?: ""
            val host = dataUri.host?.lowercase() ?: ""
            val path = dataUri.path?.lowercase() ?: ""

            // Check for Link Scanner Protocol
            val isSafeLinkScheme = SafeLinkInputHelper.isScannerUri(dataUri)

            if (isSafeLinkScheme) {
                val dest = SafeLinkInputHelper.extractScannerTarget(dataUri)
                val uuid = SafeLinkInputHelper.extractScannerUuid(dataUri)
                val alias = dataUri.getQueryParameter("alias") ?: dataUri.getQueryParameter("url") ?: dest
                val api = dataUri.getQueryParameter("api") ?: "https://zyrobrowser.com/api.php"

                if (!uuid.isNullOrBlank()) {
                    currentSafeLinkAlias = alias
                    currentSafeLinkUuid = uuid
                    currentSafeLinkApiUrl = api
                    val base = if (dest.startsWith("http")) dest.substringBeforeLast("/") else "https://ezyshort.xyz"
                    currentSafeLinkBaseUrl = base

                    // Start Link Scanner verification & ads flow immediately
                    startSafeLinkVerification(dest)
                    return
                }
            }

            // Normal browser external URL clicked
            if (scheme in listOf("http", "https")) {
                navigateToUrl(dataUri.toString())
                return
            }
        }

        // Push notification target_url extra
        val targetUrl = intent?.getStringExtra("target_url")
        if (!targetUrl.isNullOrBlank()) {
            intent.removeExtra("target_url")
            navigateToUrl(targetUrl)
        }
    }

    private fun checkInstallReferrer() {
        val prefs = getSharedPreferences("zyro_referrer_prefs", Context.MODE_PRIVATE)
        if (prefs.getBoolean("has_checked_referrer", false)) return

        try {
            val referrerClient = InstallReferrerClient.newBuilder(this).build()
            referrerClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    when (responseCode) {
                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                            try {
                                val response = referrerClient.installReferrer
                                val referrerUrl = response.installReferrer
                                prefs.edit().putBoolean("has_checked_referrer", true).apply()
                                referrerClient.endConnection()
                                if (!referrerUrl.isNullOrEmpty()) {
                                    handleReferrerString(referrerUrl)
                                }
                            } catch (e: Exception) {
                                try { referrerClient.endConnection() } catch (_: Exception) {}
                            }
                        }
                        else -> {
                            prefs.edit().putBoolean("has_checked_referrer", true).apply()
                            try { referrerClient.endConnection() } catch (_: Exception) {}
                        }
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {}
            })
        } catch (_: Exception) {
            prefs.edit().putBoolean("has_checked_referrer", true).apply()
        }
    }

    private fun handleReferrerString(referrer: String) {
        try {
            val decoded = try { URLDecoder.decode(referrer, "UTF-8") } catch (_: Exception) { referrer }
            val pairs = decoded.split("&")
            var alias: String? = null
            var uuid: String? = null
            var api: String? = null
            for (pair in pairs) {
                val kv = pair.split("=")
                if (kv.size == 2) {
                    val k = kv[0].trim()
                    val v = kv[1].trim()
                    when (k) {
                        "alias" -> alias = v
                        "uuid" -> uuid = v
                        "api" -> api = v
                    }
                }
            }

            if (!alias.isNullOrBlank() && !uuid.isNullOrBlank()) {
                currentSafeLinkAlias = alias
                currentSafeLinkUuid = uuid
                currentSafeLinkApiUrl = api ?: "https://zyrobrowser.com/api.php"
                val destUrl = "https://ezyshort.xyz/$alias"
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "🔗 Resuming Link Scanner from install referrer...", Toast.LENGTH_SHORT).show()
                    startSafeLinkVerification(destUrl)
                }
            }
        } catch (_: Exception) {}
    }

    // =========================================================================
    // DYNAMIC SPEED DIAL & BOOKMARKS
    // =========================================================================

    private fun initSpeedDialSync() {
        val db = FirebaseFirestore.getInstance()
        db.collection("speed_dial_bookmarks")
            .orderBy("order", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Log.w("ZyroBrowser", "Speed dial sync error: ${error.message}")
                    return@addSnapshotListener
                }
                if (snapshots != null && !snapshots.isEmpty) {
                    val newItems = mutableListOf<SpeedDialItem>()
                    for (doc in snapshots.documents) {
                        val data = doc.data ?: continue
                        val item = SpeedDialItem.fromMap(doc.id, data)
                        if (item.enabled) {
                            newItems.add(item)
                        }
                    }
                    if (newItems.isNotEmpty()) {
                        speedDialList.clear()
                        speedDialList.addAll(newItems)
                        speedDialAdapter.updateData(speedDialList, isDarkTheme)
                    }
                }
            }
    }

    private fun showBookmarksDialog() {
        val dialog = BottomSheetDialog(this)
        val sheetBinding = DialogBookmarksSheetBinding.inflate(layoutInflater)
        dialog.setContentView(sheetBinding.root)

        val dialogAdapter = SpeedDialAdapter(
            items = speedDialList,
            isDarkTheme = isDarkTheme,
            onItemClick = { item ->
                dialog.dismiss()
                navigateToUrl(item.url)
            }
        )

        sheetBinding.rvBookmarksSheet.layoutManager = GridLayoutManager(this, 4)
        sheetBinding.rvBookmarksSheet.adapter = dialogAdapter

        sheetBinding.btnAddCurrentBookmark.setOnClickListener {
            val currentWv = getCurrentWebView()
            val currentUrl = currentWv?.url ?: binding.etUrl.text.toString().trim()
            val currentTitle = currentWv?.title ?: "My Favorite"

            if (currentUrl.isNotBlank() && currentUrl != "zyro://home") {
                val newItem = SpeedDialItem(
                    id = "bookmark_${System.currentTimeMillis()}",
                    title = currentTitle.take(20),
                    url = currentUrl,
                    badgeText = currentTitle.take(2).uppercase(),
                    bgColor = "#FF6D00",
                    order = speedDialList.size + 1,
                    enabled = true
                )
                speedDialList.add(newItem)
                speedDialAdapter.updateData(speedDialList, isDarkTheme)
                dialogAdapter.updateData(speedDialList, isDarkTheme)

                // Save to Firestore so it syncs across the browser
                FirebaseFirestore.getInstance().collection("speed_dial_bookmarks")
                    .document(newItem.id)
                    .set(newItem.toMap())

                Toast.makeText(this, "Bookmarked: $currentTitle", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Navigate to a webpage first to bookmark it!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
