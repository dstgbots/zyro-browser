package com.zyro.admin

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.content.res.ColorStateList
import android.graphics.Color
import com.zyro.admin.databinding.ActivityMainBinding
import com.zyro.admin.databinding.ItemSentNotificationBinding
import com.zyro.admin.databinding.ItemSpeedDialAdminBinding
import com.zyro.admin.model.AdNetworkConfig
import com.zyro.admin.model.AppLovinAdsConfig
import com.zyro.admin.model.GoogleAdManagerConfig
import com.zyro.admin.model.GoogleAdsConfig
import com.zyro.admin.model.MetaAdsConfig
import com.zyro.admin.model.SafeLinkAd
import com.zyro.admin.model.SpeedDialItem
import com.zyro.admin.model.ZyroNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    enum class AdminSection {
        DASHBOARD,
        SAFELINK,
        GOOGLE_ADS,
        NOTIFICATIONS,
        SPEED_DIAL
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    private var currentSection = AdminSection.DASHBOARD

    // SafeLink Data
    private val stepsList = mutableListOf<SafeLinkAd>()
    private var selectedIndex = 0
    private var isFormInitialized = false

    // Monetization & Ad Networks Data
    private var googleAdsConfig = GoogleAdsConfig()
    private var metaAdsConfig = MetaAdsConfig()
    private var applovinAdsConfig = AppLovinAdsConfig()
    private var gamConfig = GoogleAdManagerConfig()
    private var adNetworkConfig = AdNetworkConfig()

    // Speed Dial Data
    private val speedDialList = mutableListOf<SpeedDialItem>()
    private var editingSpeedDialId: String? = null

    companion object {
        private const val PREFS_NAME = "zyro_admin_prefs"
        private const val KEY_DARK_MODE = "admin_dark_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean(KEY_DARK_MODE, false) // Default to modern clean Light Theme!
        delegate.localNightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Seed default 4 in-memory steps
        for (i in 1..4) {
            stepsList.add(getDefaultAd(i))
        }

        setupNavigation()
        setupDashboard()
        setupSafeLinkModule()
        setupGoogleAdsModule()
        setupNotificationsModule()
        setupSpeedDialModule()

        // Set initial view to Dashboard
        navigateTo(AdminSection.DASHBOARD)

        // Render SafeLink tabs
        renderStepTabs()
        selectStepIndex(0)
        isFormInitialized = true

        // Load data from Firestore
        loadAdsFromFirestore()
        loadAdNetworkConfig()
        loadGoogleAdsConfig()
        loadMetaAdsConfig()
        loadAppLovinAdsConfig()
        loadGamAdsConfig()
        loadNotificationsHistory()
        loadSpeedDialFromFirestore()

        // Handle Back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else if (currentSection != AdminSection.DASHBOARD) {
                    navigateTo(AdminSection.DASHBOARD)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    // =========================================================================
    // NAVIGATION & DRAWER SETUP
    // =========================================================================

    private fun setupNavigation() {
        binding.btnMenuToggle.setOnClickListener {
            if (currentSection == AdminSection.DASHBOARD) {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
            } else {
                navigateTo(AdminSection.DASHBOARD)
            }
        }

        // Drawer Menu Items
        binding.navItemDashboard.setOnClickListener {
            navigateTo(AdminSection.DASHBOARD)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navItemSafelink.setOnClickListener {
            navigateTo(AdminSection.SAFELINK)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navItemGoogleAds.setOnClickListener {
            navigateTo(AdminSection.GOOGLE_ADS)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navItemNotifications.setOnClickListener {
            navigateTo(AdminSection.NOTIFICATIONS)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navItemSpeedDial.setOnClickListener {
            navigateTo(AdminSection.SPEED_DIAL)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.navItemSync.setOnClickListener {
            Toast.makeText(this, "Syncing all data with Firebase...", Toast.LENGTH_SHORT).show()
            loadAdsFromFirestore()
            loadAdNetworkConfig()
            loadGoogleAdsConfig()
            loadMetaAdsConfig()
            loadAppLovinAdsConfig()
            loadNotificationsHistory()
            loadSpeedDialFromFirestore()
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Theme Toggle Listeners
        binding.btnThemeToggle.setOnClickListener {
            toggleTheme()
        }

        binding.navItemTheme.setOnClickListener {
            toggleTheme()
        }

        updateThemeUI(isCurrentThemeDark())
    }

    private fun isCurrentThemeDark(): Boolean {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    private fun updateThemeUI(isDark: Boolean) {
        if (isDark) {
            binding.btnThemeToggle.setImageResource(R.drawable.ic_theme_sun)
            binding.btnThemeToggle.contentDescription = "Switch to Light Theme"
            binding.ivDrawerThemeIcon.setImageResource(R.drawable.ic_theme_sun)
            binding.tvDrawerThemeLabel.text = "Dark Mode"
            binding.switchDrawerTheme.isChecked = true
        } else {
            binding.btnThemeToggle.setImageResource(R.drawable.ic_theme_moon)
            binding.btnThemeToggle.contentDescription = "Switch to Dark Theme"
            binding.ivDrawerThemeIcon.setImageResource(R.drawable.ic_theme_moon)
            binding.tvDrawerThemeLabel.text = "Light Mode"
            binding.switchDrawerTheme.isChecked = false
        }

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isDark
            isAppearanceLightNavigationBars = !isDark
        }
    }

    private fun toggleTheme() {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val currentDark = isCurrentThemeDark()
        val newDark = !currentDark
        prefs.edit().putBoolean(KEY_DARK_MODE, newDark).apply()

        delegate.localNightMode = if (newDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        recreate()
    }

    private fun navigateTo(section: AdminSection) {
        currentSection = section

        when (section) {
            AdminSection.DASHBOARD -> {
                binding.viewDashboard.visibility = View.VISIBLE
                binding.viewSafelink.visibility = View.GONE
                binding.viewGoogleAds.visibility = View.GONE
                binding.viewPushNotifications.visibility = View.GONE
                binding.viewSpeedDial.visibility = View.GONE

                binding.btnMenuToggle.setImageResource(R.drawable.ic_menu_hamburger)
                binding.tvTopbarTitle.text = "Zyro Admin Panel"

                highlightDrawerItem(binding.navItemDashboard)
                updateDashboardStats()
            }
            AdminSection.SAFELINK -> {
                binding.viewDashboard.visibility = View.GONE
                binding.viewSafelink.visibility = View.VISIBLE
                binding.viewGoogleAds.visibility = View.GONE
                binding.viewPushNotifications.visibility = View.GONE
                binding.viewSpeedDial.visibility = View.GONE

                binding.btnMenuToggle.setImageResource(R.drawable.ic_arrow_back)
                binding.tvTopbarTitle.text = "SafeLink Steps Manager"

                highlightDrawerItem(binding.navItemSafelink)
                updateSafeLinkActivePlatformBanner()
            }
            AdminSection.GOOGLE_ADS -> {
                binding.viewDashboard.visibility = View.GONE
                binding.viewSafelink.visibility = View.GONE
                binding.viewGoogleAds.visibility = View.VISIBLE
                binding.viewPushNotifications.visibility = View.GONE
                binding.viewSpeedDial.visibility = View.GONE

                binding.btnMenuToggle.setImageResource(R.drawable.ic_arrow_back)
                binding.tvTopbarTitle.text = "Monetization & Ad Networks"

                highlightDrawerItem(binding.navItemGoogleAds)
                populateMonetizationHub()
            }
            AdminSection.NOTIFICATIONS -> {
                binding.viewDashboard.visibility = View.GONE
                binding.viewSafelink.visibility = View.GONE
                binding.viewGoogleAds.visibility = View.GONE
                binding.viewPushNotifications.visibility = View.VISIBLE
                binding.viewSpeedDial.visibility = View.GONE

                binding.btnMenuToggle.setImageResource(R.drawable.ic_arrow_back)
                binding.tvTopbarTitle.text = "Push Notifications"

                highlightDrawerItem(binding.navItemNotifications)
            }
            AdminSection.SPEED_DIAL -> {
                binding.viewDashboard.visibility = View.GONE
                binding.viewSafelink.visibility = View.GONE
                binding.viewGoogleAds.visibility = View.GONE
                binding.viewPushNotifications.visibility = View.GONE
                binding.viewSpeedDial.visibility = View.VISIBLE

                binding.btnMenuToggle.setImageResource(R.drawable.ic_arrow_back)
                binding.tvTopbarTitle.text = "Speed Dial & Bookmarks"

                highlightDrawerItem(binding.navItemSpeedDial)
            }
        }
    }

    private fun highlightDrawerItem(activeItem: View) {
        val items = listOf(
            binding.navItemDashboard,
            binding.navItemSafelink,
            binding.navItemGoogleAds,
            binding.navItemNotifications,
            binding.navItemSpeedDial
        )

        for (item in items) {
            if (item == activeItem) {
                item.setBackgroundResource(R.drawable.bg_nav_item)
            } else {
                item.background = null
            }
        }
    }

    // =========================================================================
    // DASHBOARD SETUP
    // =========================================================================

    private fun setupDashboard() {
        // Module card click listeners
        binding.cardFeatureSafelink.setOnClickListener {
            navigateTo(AdminSection.SAFELINK)
        }
        binding.btnOpenSafelink.setOnClickListener {
            navigateTo(AdminSection.SAFELINK)
        }

        binding.cardFeatureGoogleAds.setOnClickListener {
            navigateTo(AdminSection.GOOGLE_ADS)
        }
        binding.btnOpenGoogleAds.setOnClickListener {
            navigateTo(AdminSection.GOOGLE_ADS)
        }

        binding.cardFeatureNotifications.setOnClickListener {
            navigateTo(AdminSection.NOTIFICATIONS)
        }
        binding.btnOpenNotifications.setOnClickListener {
            navigateTo(AdminSection.NOTIFICATIONS)
        }

        binding.cardFeatureSpeedDial.setOnClickListener {
            navigateTo(AdminSection.SPEED_DIAL)
        }
        binding.btnOpenSpeedDial.setOnClickListener {
            navigateTo(AdminSection.SPEED_DIAL)
        }

        // Quick Ads master switch on Dashboard
        binding.switchDashQuickAds.setOnCheckedChangeListener { _, isChecked ->
            saveGlobalAdsToggle(isChecked)
        }
    }

    private fun updateDashboardStats() {
        val count = stepsList.size
        binding.tvDashStatSteps.text = "$count Steps Active"
        binding.badgeSafelinkCount.text = "$count STEPS"
        binding.tvCardSafelinkSubtitle.text = "$count security verification steps configured & synced."

        val (activeName, isEnabled, isTest) = when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_META -> Triple("Meta Network", metaAdsConfig.enabled, metaAdsConfig.testMode)
            AdNetworkConfig.PLATFORM_APPLOVIN -> Triple("AppLovin MAX", applovinAdsConfig.enabled, applovinAdsConfig.testMode)
            else -> Triple("Google AdMob", googleAdsConfig.adsEnabled, googleAdsConfig.testMode)
        }
        val badgeText = when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_META -> "META"
            AdNetworkConfig.PLATFORM_APPLOVIN -> "APPLOVIN"
            else -> "ADMOB"
        }

        if (adNetworkConfig.globalAdsEnabled && isEnabled) {
            val mode = if (isTest) "Test" else "Live"
            binding.tvDashStatAds.text = "$activeName ($mode)"
            binding.badgeAdsStatus.text = "$badgeText ($mode)"
            binding.switchDashQuickAds.isChecked = true
        } else {
            binding.tvDashStatAds.text = "Paused ($activeName)"
            binding.badgeAdsStatus.text = "PAUSED"
            binding.switchDashQuickAds.isChecked = false
        }

        val tileCount = speedDialList.size
        binding.badgeSpeedDialCount.text = "$tileCount TILES"
    }

    // =========================================================================
    // SAFELINK STEPS MODULE
    // =========================================================================

    data class AdTypeOption(val code: String, val displayName: String, val shortBadge: String)

    private val googleAdTypeOptions = listOf(
        AdTypeOption("BANNER", "Banner Ad (Popup Dialog)", "BANNER"),
        AdTypeOption("NATIVE", "Native Advanced Ad (Popup Dialog)", "NATIVE"),
        AdTypeOption("INTERSTITIAL", "Interstitial Ad (Full Screen)", "INTERSTITIAL"),
        AdTypeOption("REWARDED", "Rewarded Video Ad (Full Screen)", "REWARDED"),
        AdTypeOption("REWARDED_INTERSTITIAL", "Rewarded Interstitial Ad", "REW-INT"),
        AdTypeOption("APP_OPEN", "App Open Ad", "APP-OPEN"),
        AdTypeOption("CUSTOM", "Custom In-House Ad (Popup Dialog)", "CUSTOM")
    )

    private val metaAdTypeOptions = listOf(
        AdTypeOption("BANNER", "Standard Banner 50dp (Popup Dialog)", "BANNER"),
        AdTypeOption("BANNER_MREC", "Medium Rectangle 250dp / MREC (Popup Dialog)", "MREC"),
        AdTypeOption("INTERSTITIAL", "Interstitial Ad (Full Screen)", "INTERSTITIAL"),
        AdTypeOption("REWARDED", "Rewarded Video Ad (Full Screen)", "REWARDED"),
        AdTypeOption("NATIVE", "Native Full Ad (Popup Dialog)", "NATIVE"),
        AdTypeOption("NATIVE_BANNER", "Native Banner Ad (Popup Dialog)", "NAT-BAN"),
        AdTypeOption("PLAYABLE", "Playable Ad (Full Screen)", "PLAYABLE"),
        AdTypeOption("CUSTOM", "Custom In-House Ad (Popup Dialog)", "CUSTOM")
    )

    private val applovinAdTypeOptions = listOf(
        AdTypeOption("BANNER", "Banner Ad 320x50 (Popup Dialog)", "BANNER"),
        AdTypeOption("BANNER_MREC", "Medium Rectangle 300x250 / MREC (Popup Dialog)", "MREC"),
        AdTypeOption("INTERSTITIAL", "Interstitial Ad (Full Screen)", "INTERSTITIAL"),
        AdTypeOption("REWARDED", "Rewarded Video Ad (Full Screen)", "REWARDED"),
        AdTypeOption("NATIVE", "Native Ad (Popup Dialog)", "NATIVE"),
        AdTypeOption("APP_OPEN", "App Open Ad (Full Screen)", "APP-OPEN"),
        AdTypeOption("CUSTOM", "Custom In-House Ad (Popup Dialog)", "CUSTOM")
    )

    private val gamAdTypeOptions = listOf(
        AdTypeOption("BANNER", "GAM Adaptive Banner (Popup Dialog)", "BANNER"),
        AdTypeOption("INTERSTITIAL", "GAM Interstitial Ad (Full Screen)", "INTERSTITIAL"),
        AdTypeOption("REWARDED", "GAM Rewarded Video (Full Screen)", "REWARDED"),
        AdTypeOption("REWARDED_INTERSTITIAL", "GAM Rewarded Interstitial (Full Screen)", "REW-INT"),
        AdTypeOption("APP_OPEN", "GAM App Open Ad (Full Screen)", "APP-OPEN"),
        AdTypeOption("NATIVE", "GAM Native Advanced (Popup Dialog)", "NATIVE"),
        AdTypeOption("CUSTOM", "Custom In-House Ad (Popup Dialog)", "CUSTOM")
    )

    private fun getAvailableAdTypes(): List<AdTypeOption> {
        return when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_META -> metaAdTypeOptions
            AdNetworkConfig.PLATFORM_APPLOVIN -> applovinAdTypeOptions
            AdNetworkConfig.PLATFORM_GAM -> gamAdTypeOptions
            else -> googleAdTypeOptions
        }
    }

    private fun updateSafeLinkActivePlatformBanner() {
        when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_META -> {
                binding.tvSafelinkActivePlatformTitle.text = "Active Ad Platform: Meta Audience Network"
                binding.tvSafelinkActivePlatformDesc.text = "Step ad formats below dynamically match Meta Audience Network."
                binding.ivSafelinkPlatformIcon.setImageResource(R.drawable.ic_meta)
                binding.ivSafelinkPlatformIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.admin_accent))
            }
            AdNetworkConfig.PLATFORM_APPLOVIN -> {
                binding.tvSafelinkActivePlatformTitle.text = "Active Ad Platform: AppLovin MAX"
                binding.tvSafelinkActivePlatformDesc.text = "Step ad formats below dynamically match AppLovin MAX."
                binding.ivSafelinkPlatformIcon.setImageResource(R.drawable.ic_applovin)
                binding.ivSafelinkPlatformIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.admin_green))
            }
            AdNetworkConfig.PLATFORM_GAM -> {
                binding.tvSafelinkActivePlatformTitle.text = "Active Ad Platform: Google Ad Manager (GAM)"
                binding.tvSafelinkActivePlatformDesc.text = "Step ad formats below dynamically match Google Ad Manager."
                binding.ivSafelinkPlatformIcon.setImageResource(R.drawable.ic_ads)
                binding.ivSafelinkPlatformIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.admin_primary))
            }
            else -> {
                binding.tvSafelinkActivePlatformTitle.text = "Active Ad Platform: Google AdMob"
                binding.tvSafelinkActivePlatformDesc.text = "Step ad formats below dynamically match Google AdMob."
                binding.ivSafelinkPlatformIcon.setImageResource(R.drawable.ic_ads)
                binding.ivSafelinkPlatformIcon.imageTintList = ColorStateList.valueOf(getColor(R.color.admin_primary))
            }
        }
    }

    private fun setupSafeLinkModule() {
        binding.btnSafelinkBack.setOnClickListener {
            navigateTo(AdminSection.DASHBOARD)
        }

        binding.btnSafelinkSwitchNetwork.setOnClickListener {
            val newPlatform = when (adNetworkConfig.activePlatform) {
                AdNetworkConfig.PLATFORM_GOOGLE -> AdNetworkConfig.PLATFORM_META
                AdNetworkConfig.PLATFORM_META -> AdNetworkConfig.PLATFORM_APPLOVIN
                AdNetworkConfig.PLATFORM_APPLOVIN -> AdNetworkConfig.PLATFORM_GAM
                else -> AdNetworkConfig.PLATFORM_GOOGLE
            }
            setActivePlatform(newPlatform)
        }

        setupAdTypeDropdown()
        setupAddStepButton()
        setupDeleteStepButton()
        setupSaveButton()
        updateSafeLinkActivePlatformBanner()
    }

    private fun setupAdTypeDropdown() {
        val currentOptions = getAvailableAdTypes()
        val displayNames = currentOptions.map { it.displayName }
        val adapter = android.widget.ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            displayNames
        )
        binding.actvStepAdType.setAdapter(adapter)
        binding.actvStepAdType.setOnItemClickListener { _, _, position, _ ->
            if (selectedIndex in stepsList.indices && position in currentOptions.indices) {
                stepsList[selectedIndex].adType = currentOptions[position].code
                renderStepTabs()
            }
        }
    }

    private fun renderStepTabs() {
        binding.stepsTabContainer.removeAllViews()

        binding.tvStepsCountHint.text = "Total Active Steps: ${stepsList.size}"
        updateDashboardStats()

        val currentOptions = getAvailableAdTypes()
        stepsList.forEachIndexed { index, ad ->
            val button = MaterialButton(this).apply {
                layoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    if (index > 0) marginStart = (8 * resources.displayMetrics.density).toInt()
                }

                val displayName = if (ad.stepName.isNotBlank()) ad.stepName else "Step ${index + 1}"
                val truncated = if (displayName.length > 12) displayName.take(10) + "…" else displayName
                val badgeCode = currentOptions.find { it.code.equals(ad.adType, ignoreCase = true) }?.shortBadge
                    ?: googleAdTypeOptions.find { it.code.equals(ad.adType, ignoreCase = true) }?.shortBadge
                    ?: metaAdTypeOptions.find { it.code.equals(ad.adType, ignoreCase = true) }?.shortBadge
                    ?: applovinAdTypeOptions.find { it.code.equals(ad.adType, ignoreCase = true) }?.shortBadge
                    ?: ad.adType
                text = "${index + 1}. $truncated [$badgeCode]"
                textSize = 11f
                cornerRadius = (20 * resources.displayMetrics.density).toInt()

                if (index == selectedIndex) {
                    setBackgroundColor(getColor(R.color.admin_primary))
                    setTextColor(getColor(R.color.white))
                    strokeWidth = 0
                } else {
                    setBackgroundColor(getColor(R.color.admin_card))
                    setTextColor(getColor(R.color.admin_text_secondary))
                    strokeColor = android.content.res.ColorStateList.valueOf(getColor(R.color.admin_card_border))
                    strokeWidth = (1 * resources.displayMetrics.density).toInt()
                }

                setOnClickListener {
                    selectStepIndex(index)
                }
            }

            binding.stepsTabContainer.addView(button)
        }
    }

    private fun selectStepIndex(index: Int) {
        if (stepsList.isEmpty()) return

        if (isFormInitialized && selectedIndex in stepsList.indices) {
            cacheCurrentForm()
        }

        selectedIndex = index.coerceIn(0, stepsList.size - 1)
        val currentAd = stepsList[selectedIndex]

        binding.tvEditingStepTitle.text = "Editing Step ${selectedIndex + 1}: ${currentAd.stepName}"

        // Delete button disabled if only 1 step left
        binding.btnDeleteStep.isEnabled = stepsList.size > 1
        binding.btnDeleteStep.alpha = if (stepsList.size > 1) 1.0f else 0.4f

        populateForm(currentAd)
        renderStepTabs()
    }

    private fun populateForm(ad: SafeLinkAd) {
        binding.etStepName.setText(ad.stepName)
        binding.etStepSubtitle.setText(ad.stepSubtitle)

        // Populate Ad Format dropdown dynamically
        val currentOptions = getAvailableAdTypes()
        val matchedOption = currentOptions.find { it.code.equals(ad.adType, ignoreCase = true) }
            ?: currentOptions[0]
        ad.adType = matchedOption.code
        binding.actvStepAdType.setText(matchedOption.displayName, false)

        binding.etAdBadge.setText(ad.badge)
        binding.etAdTitle.setText(ad.title)
        binding.etAdDesc.setText(ad.description)
        binding.etAdCtaText.setText(ad.ctaText)
        binding.etAdCtaUrl.setText(ad.ctaUrl)
        binding.etAdDuration.setText(ad.durationSeconds.toString())
    }

    private fun cacheCurrentForm() {
        if (selectedIndex !in stepsList.indices) return

        val duration = binding.etAdDuration.text.toString().toIntOrNull() ?: 3
        val current = stepsList[selectedIndex]

        current.stepName = binding.etStepName.text.toString().trim().ifEmpty { "Step ${selectedIndex + 1}" }
        current.stepSubtitle = binding.etStepSubtitle.text.toString().trim()

        val currentOptions = getAvailableAdTypes()
        val selectedText = binding.actvStepAdType.text.toString().trim()
        val matchedOption = currentOptions.find { it.displayName.equals(selectedText, ignoreCase = true) }
        if (matchedOption != null) {
            current.adType = matchedOption.code
        }

        current.badge = binding.etAdBadge.text.toString().trim()
        current.title = binding.etAdTitle.text.toString().trim()
        current.description = binding.etAdDesc.text.toString().trim()
        current.ctaText = binding.etAdCtaText.text.toString().trim()
        current.ctaUrl = binding.etAdCtaUrl.text.toString().trim()
        current.durationSeconds = duration
        current.order = selectedIndex + 1
    }

    private fun setupAddStepButton() {
        binding.btnAddStep.setOnClickListener {
            if (isFormInitialized && selectedIndex in stepsList.indices) {
                cacheCurrentForm()
            }

            val nextNum = stepsList.size + 1
            val defaultAdType = when (nextNum % 4) {
                1 -> "BANNER"
                2 -> "NATIVE"
                3 -> "INTERSTITIAL"
                0 -> "REWARDED"
                else -> "BANNER"
            }
            val newStep = SafeLinkAd(
                stepId = "step_${System.currentTimeMillis()}",
                stepName = "Threat Analysis $nextNum",
                stepSubtitle = "Analyzing zero-day vulnerabilities and protocols",
                order = nextNum,
                adType = defaultAdType,
                badge = "SPONSORED AD ($nextNum/${nextNum})",
                title = "Zyro Cyber Guard",
                description = "Real-time AI malware prevention and threat defense engine.",
                ctaText = "Activate Protection",
                ctaUrl = "https://example.com",
                durationSeconds = 3
            )

            stepsList.add(newStep)

            // Auto-persist new step document to Firestore
            db.collection("safelink_ads").document(newStep.stepId)
                .set(newStep.toMap())
                .addOnSuccessListener {
                    Toast.makeText(this, "Step $nextNum created & saved to Firebase!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Added locally. Tap Save to sync.", Toast.LENGTH_SHORT).show()
                }

            selectStepIndex(stepsList.size - 1)
        }
    }

    private fun setupDeleteStepButton() {
        binding.btnDeleteStep.setOnClickListener {
            if (stepsList.size <= 1) {
                Toast.makeText(this, "Cannot delete! SafeLink must have at least 1 step.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val current = stepsList[selectedIndex]

            AlertDialog.Builder(this)
                .setTitle("Delete Step ${selectedIndex + 1}?")
                .setMessage("Are you sure you want to remove '${current.stepName}' from SafeLink verification?")
                .setPositiveButton("DELETE") { _, _ ->
                    val toDelete = stepsList.removeAt(selectedIndex)

                    // Delete from Firestore
                    db.collection("safelink_ads").document(toDelete.stepId)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "'${toDelete.stepName}' deleted from Firebase!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Delete error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                    // Re-index remaining steps
                    stepsList.forEachIndexed { i, ad ->
                        ad.order = i + 1
                        db.collection("safelink_ads").document(ad.stepId).set(ad.toMap())
                    }

                    selectedIndex = (selectedIndex - 1).coerceAtLeast(0)
                    selectStepIndex(selectedIndex)
                }
                .setNegativeButton("CANCEL", null)
                .show()
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveAd.setOnClickListener {
            cacheCurrentForm()
            val ad = stepsList.getOrNull(selectedIndex) ?: return@setOnClickListener

            binding.btnSaveAd.isEnabled = false
            binding.btnSaveAd.text = "SAVING..."

            // Safety timeout
            val timeoutRunnable = Runnable {
                if (!binding.btnSaveAd.isEnabled) {
                    binding.btnSaveAd.isEnabled = true
                    binding.btnSaveAd.text = "SAVE TO FIREBASE"
                    Toast.makeText(
                        this@MainActivity,
                        "Firebase timeout: Please check connection.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            binding.root.postDelayed(timeoutRunnable, 6000L)

            db.collection("safelink_ads").document(ad.stepId)
                .set(ad.toMap())
                .addOnSuccessListener {
                    binding.root.removeCallbacks(timeoutRunnable)
                    binding.btnSaveAd.isEnabled = true
                    binding.btnSaveAd.text = "SAVE TO FIREBASE"
                    Toast.makeText(this, "Step '${ad.stepName}' updated in Firebase!", Toast.LENGTH_SHORT).show()
                    renderStepTabs()
                }
                .addOnFailureListener { e ->
                    binding.root.removeCallbacks(timeoutRunnable)
                    binding.btnSaveAd.isEnabled = true
                    binding.btnSaveAd.text = "SAVE TO FIREBASE"
                    Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun loadAdsFromFirestore() {
        db.collection("safelink_ads").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    // Seed Firestore with default ads
                    for (ad in stepsList) {
                        db.collection("safelink_ads").document(ad.stepId).set(ad.toMap())
                    }
                } else {
                    val loaded = mutableListOf<SafeLinkAd>()
                    for (doc in snapshot.documents) {
                        val ad = SafeLinkAd.fromMap(doc.id, doc.data ?: emptyMap())
                        loaded.add(ad)
                    }
                    loaded.sortBy { it.order }

                    if (loaded.isNotEmpty()) {
                        stepsList.clear()
                        stepsList.addAll(loaded)
                    }
                }
                selectStepIndex(0)
                updateDashboardStats()
            }
            .addOnFailureListener {
                // Keep defaults if offline
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
                badge = "SPONSORED AD (1/4)",
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
                badge = "SPONSORED AD (2/4)",
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
                badge = "SPONSORED AD (3/4)",
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
                badge = "SPONSORED AD (4/4)",
                title = "Zyro AdBlock Pro",
                description = "Stop tracking cookies, malicious banners, and popups forever.",
                ctaText = "Block All Trackers",
                ctaUrl = "https://example.com/adblock",
                durationSeconds = 3
            )
        }
    }

    // =========================================================================
    // MONETIZATION & AD NETWORKS MODULE
    // =========================================================================

    private fun setupGoogleAdsModule() {
        binding.btnAdsBack.setOnClickListener {
            navigateTo(AdminSection.DASHBOARD)
        }

        // Master 1-Active Platform Selectors
        binding.cardSelectGoogle.setOnClickListener {
            setActivePlatform(AdNetworkConfig.PLATFORM_GOOGLE)
        }
        binding.cardSelectMeta.setOnClickListener {
            setActivePlatform(AdNetworkConfig.PLATFORM_META)
        }
        binding.cardSelectApplovin.setOnClickListener {
            setActivePlatform(AdNetworkConfig.PLATFORM_APPLOVIN)
        }
        binding.cardSelectGam.setOnClickListener {
            setActivePlatform(AdNetworkConfig.PLATFORM_GAM)
        }

        // Global master switch
        binding.switchGlobalAdsEnabled.setOnCheckedChangeListener { _, isChecked ->
            saveGlobalAdsToggle(isChecked)
        }

        // Tabs to switch which config view is shown
        binding.btnTabGoogle.setOnClickListener {
            switchConfigTab(0)
        }
        binding.btnTabMeta.setOnClickListener {
            switchConfigTab(1)
        }
        binding.btnTabApplovin.setOnClickListener {
            switchConfigTab(2)
        }
        binding.btnTabGam.setOnClickListener {
            switchConfigTab(3)
        }

        // Google AdMob actions
        binding.btnFillTestKeys.setOnClickListener {
            fillGoogleTestKeys()
            Toast.makeText(this, "Official Google AdMob test IDs loaded!", Toast.LENGTH_SHORT).show()
        }
        binding.btnSaveGoogleAds.setOnClickListener {
            saveGoogleAdsConfig()
        }

        // Meta Audience Network actions
        binding.btnFillMetaTestKeys.setOnClickListener {
            fillMetaTestKeys()
            Toast.makeText(this, "Official Meta Audience Network test IDs loaded!", Toast.LENGTH_SHORT).show()
        }
        binding.btnSaveMetaAds.setOnClickListener {
            saveMetaAdsConfig()
        }

        // AppLovin MAX actions
        binding.btnFillApplovinTestKeys.setOnClickListener {
            fillAppLovinTestKeys()
            Toast.makeText(this, "Sample AppLovin MAX test keys loaded!", Toast.LENGTH_SHORT).show()
        }
        binding.btnSaveApplovinAds.setOnClickListener {
            saveAppLovinAdsConfig()
        }

        // Google Ad Manager (GAM) actions
        binding.btnFillGamTestKeys.setOnClickListener {
            fillGamTestKeys()
            Toast.makeText(this, "Official Google Ad Manager demo units loaded!", Toast.LENGTH_SHORT).show()
        }
        binding.btnSaveGamAds.setOnClickListener {
            saveGamAdsConfig()
        }
    }

    private fun switchConfigTab(tabIndex: Int) {
        // tabIndex: 0 = Google AdMob, 1 = Meta Audience Network, 2 = AppLovin MAX, 3 = Google Ad Manager
        binding.layoutConfigGoogle.visibility = if (tabIndex == 0) View.VISIBLE else View.GONE
        binding.layoutConfigMeta.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE
        binding.layoutConfigApplovin.visibility = if (tabIndex == 2) View.VISIBLE else View.GONE
        binding.layoutConfigGam.visibility = if (tabIndex == 3) View.VISIBLE else View.GONE

        // Google Tab Styling
        if (tabIndex == 0) {
            binding.btnTabGoogle.setBackgroundColor(getColor(R.color.admin_primary_soft))
            binding.btnTabGoogle.setTextColor(getColor(R.color.admin_primary))
        } else {
            binding.btnTabGoogle.setBackgroundColor(Color.TRANSPARENT)
            binding.btnTabGoogle.setTextColor(getColor(R.color.admin_text_secondary))
        }

        // Meta Tab Styling
        if (tabIndex == 1) {
            binding.btnTabMeta.setBackgroundColor(getColor(R.color.admin_accent_soft))
            binding.btnTabMeta.setTextColor(getColor(R.color.admin_accent))
        } else {
            binding.btnTabMeta.setBackgroundColor(Color.TRANSPARENT)
            binding.btnTabMeta.setTextColor(getColor(R.color.admin_text_secondary))
        }

        // AppLovin Tab Styling
        if (tabIndex == 2) {
            binding.btnTabApplovin.setBackgroundColor(getColor(R.color.admin_green_soft))
            binding.btnTabApplovin.setTextColor(getColor(R.color.admin_green))
        } else {
            binding.btnTabApplovin.setBackgroundColor(Color.TRANSPARENT)
            binding.btnTabApplovin.setTextColor(getColor(R.color.admin_text_secondary))
        }

        // GAM Tab Styling
        if (tabIndex == 3) {
            binding.btnTabGam.setBackgroundColor(getColor(R.color.admin_primary_soft))
            binding.btnTabGam.setTextColor(getColor(R.color.admin_primary))
        } else {
            binding.btnTabGam.setBackgroundColor(Color.TRANSPARENT)
            binding.btnTabGam.setTextColor(getColor(R.color.admin_text_secondary))
        }
    }

    private fun setActivePlatform(platform: String) {
        adNetworkConfig.activePlatform = platform
        db.collection("app_config").document("ad_network")
            .set(adNetworkConfig.toMap())
            .addOnSuccessListener {
                val name = when (platform) {
                    AdNetworkConfig.PLATFORM_META -> "Meta Audience Network"
                    AdNetworkConfig.PLATFORM_APPLOVIN -> "AppLovin MAX"
                    AdNetworkConfig.PLATFORM_GAM -> "Google Ad Manager (GAM)"
                    else -> "Google AdMob"
                }
                Toast.makeText(this, "Active ad network switched to $name!", Toast.LENGTH_SHORT).show()
                updateMonetizationUI()
                updateSafeLinkActivePlatformBanner()
                setupAdTypeDropdown()
                renderStepTabs()
                if (selectedIndex in stepsList.indices) {
                    populateForm(stepsList[selectedIndex])
                }
                updateDashboardStats()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to update active platform: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateMonetizationUI() {
        when (adNetworkConfig.activePlatform) {
            AdNetworkConfig.PLATFORM_META -> {
                binding.badgePlatformMeta.text = "ACTIVE"
                binding.badgePlatformMeta.setBackgroundResource(R.drawable.bg_badge_green)
                binding.badgePlatformMeta.setTextColor(getColor(R.color.admin_green))

                binding.badgePlatformGoogle.text = "STANDBY"
                binding.badgePlatformGoogle.setBackgroundResource(R.drawable.bg_badge_primary)
                binding.badgePlatformGoogle.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformApplovin.text = "STANDBY"
                binding.badgePlatformApplovin.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformApplovin.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformGam.text = "STANDBY"
                binding.badgePlatformGam.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformGam.setTextColor(getColor(R.color.admin_text_secondary))

                binding.cardSelectMeta.setBackgroundResource(R.drawable.bg_admin_card_accent)
                binding.cardSelectGoogle.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectApplovin.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectGam.setBackgroundResource(R.drawable.bg_admin_card)

                switchConfigTab(1)
            }
            AdNetworkConfig.PLATFORM_APPLOVIN -> {
                binding.badgePlatformApplovin.text = "ACTIVE"
                binding.badgePlatformApplovin.setBackgroundResource(R.drawable.bg_badge_green)
                binding.badgePlatformApplovin.setTextColor(getColor(R.color.admin_green))

                binding.badgePlatformGoogle.text = "STANDBY"
                binding.badgePlatformGoogle.setBackgroundResource(R.drawable.bg_badge_primary)
                binding.badgePlatformGoogle.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformMeta.text = "STANDBY"
                binding.badgePlatformMeta.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformMeta.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformGam.text = "STANDBY"
                binding.badgePlatformGam.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformGam.setTextColor(getColor(R.color.admin_text_secondary))

                binding.cardSelectApplovin.setBackgroundResource(R.drawable.bg_admin_card_accent)
                binding.cardSelectGoogle.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectMeta.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectGam.setBackgroundResource(R.drawable.bg_admin_card)

                switchConfigTab(2)
            }
            AdNetworkConfig.PLATFORM_GAM -> {
                binding.badgePlatformGam.text = "ACTIVE"
                binding.badgePlatformGam.setBackgroundResource(R.drawable.bg_badge_green)
                binding.badgePlatformGam.setTextColor(getColor(R.color.admin_green))

                binding.badgePlatformGoogle.text = "STANDBY"
                binding.badgePlatformGoogle.setBackgroundResource(R.drawable.bg_badge_primary)
                binding.badgePlatformGoogle.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformMeta.text = "STANDBY"
                binding.badgePlatformMeta.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformMeta.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformApplovin.text = "STANDBY"
                binding.badgePlatformApplovin.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformApplovin.setTextColor(getColor(R.color.admin_text_secondary))

                binding.cardSelectGam.setBackgroundResource(R.drawable.bg_admin_card_accent)
                binding.cardSelectGoogle.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectMeta.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectApplovin.setBackgroundResource(R.drawable.bg_admin_card)

                switchConfigTab(3)
            }
            else -> {
                binding.badgePlatformGoogle.text = "ACTIVE"
                binding.badgePlatformGoogle.setBackgroundResource(R.drawable.bg_badge_green)
                binding.badgePlatformGoogle.setTextColor(getColor(R.color.admin_green))

                binding.badgePlatformMeta.text = "STANDBY"
                binding.badgePlatformMeta.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformMeta.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformApplovin.text = "STANDBY"
                binding.badgePlatformApplovin.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformApplovin.setTextColor(getColor(R.color.admin_text_secondary))

                binding.badgePlatformGam.text = "STANDBY"
                binding.badgePlatformGam.setBackgroundResource(R.drawable.bg_badge_accent)
                binding.badgePlatformGam.setTextColor(getColor(R.color.admin_text_secondary))

                binding.cardSelectGoogle.setBackgroundResource(R.drawable.bg_admin_card_accent)
                binding.cardSelectMeta.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectApplovin.setBackgroundResource(R.drawable.bg_admin_card)
                binding.cardSelectGam.setBackgroundResource(R.drawable.bg_admin_card)

                switchConfigTab(0)
            }
        }

        binding.switchGlobalAdsEnabled.isChecked = adNetworkConfig.globalAdsEnabled
    }

    private fun populateMonetizationHub() {
        updateMonetizationUI()
        populateGoogleAdsForm()
        populateMetaAdsForm()
        populateAppLovinAdsForm()
        populateGamForm()
    }

    private fun populateGoogleAdsForm() {
        binding.switchAdsEnabled.isChecked = googleAdsConfig.adsEnabled
        binding.switchAdsTestMode.isChecked = googleAdsConfig.testMode
        binding.switchSafelinkGoogleAds.isChecked = googleAdsConfig.safelinkGoogleAdsEnabled

        binding.etAdmobAppId.setText(googleAdsConfig.appId)
        binding.etAdmobBannerId.setText(googleAdsConfig.bannerAdUnitId)
        binding.etAdmobInterstitialId.setText(googleAdsConfig.interstitialAdUnitId)
        binding.etAdmobRewardedId.setText(googleAdsConfig.rewardedAdUnitId)
        binding.etAdmobNativeId.setText(googleAdsConfig.nativeAdUnitId)
        binding.etAdmobInterval.setText(googleAdsConfig.interstitialInterval.toString())
    }

    private fun populateMetaAdsForm() {
        binding.switchMetaAdsEnabled.isChecked = metaAdsConfig.enabled
        binding.switchMetaTestMode.isChecked = metaAdsConfig.testMode

        binding.etMetaAppId.setText(metaAdsConfig.appId)
        binding.etMetaBannerId.setText(metaAdsConfig.bannerPlacementId)
        binding.etMetaBannerMrecId.setText(metaAdsConfig.bannerMrecPlacementId)
        binding.etMetaInterstitialId.setText(metaAdsConfig.interstitialPlacementId)
        binding.etMetaRewardedId.setText(metaAdsConfig.rewardedPlacementId)
        binding.etMetaNativeId.setText(metaAdsConfig.nativePlacementId)
        binding.etMetaNativeBannerId.setText(metaAdsConfig.nativeBannerPlacementId)
        binding.etMetaPlayableId.setText(metaAdsConfig.playablePlacementId)
    }

    private fun populateAppLovinAdsForm() {
        binding.switchEnableApplovinAds.isChecked = applovinAdsConfig.enabled
        binding.switchApplovinTestMode.isChecked = applovinAdsConfig.testMode

        binding.etApplovinSdkKey.setText(applovinAdsConfig.sdkKey)
        binding.etApplovinBannerId.setText(applovinAdsConfig.bannerAdUnitId)
        binding.etApplovinMrecId.setText(applovinAdsConfig.mrecAdUnitId)
        binding.etApplovinInterstitialId.setText(applovinAdsConfig.interstitialAdUnitId)
        binding.etApplovinRewardedId.setText(applovinAdsConfig.rewardedAdUnitId)
        binding.etApplovinNativeId.setText(applovinAdsConfig.nativeAdUnitId)
        binding.etApplovinAppOpenId.setText(applovinAdsConfig.appOpenAdUnitId)
    }

    private fun fillGoogleTestKeys() {
        binding.switchAdsEnabled.isChecked = true
        binding.switchAdsTestMode.isChecked = true
        binding.switchSafelinkGoogleAds.isChecked = true

        binding.etAdmobAppId.setText("ca-app-pub-3940256099942544~3347511713")
        binding.etAdmobBannerId.setText("ca-app-pub-3940256099942544/9214589741")
        binding.etAdmobInterstitialId.setText("ca-app-pub-3940256099942544/1033173712")
        binding.etAdmobRewardedId.setText("ca-app-pub-3940256099942544/5224354917")
        binding.etAdmobNativeId.setText("ca-app-pub-3940256099942544/2247696110")
        binding.etAdmobInterval.setText("3")
    }

    private fun fillMetaTestKeys() {
        binding.switchMetaAdsEnabled.isChecked = true
        binding.switchMetaTestMode.isChecked = true

        binding.etMetaAppId.setText("123456789012345")
        binding.etMetaBannerId.setText("IMG_16_9_LINK#YOUR_PLACEMENT_ID")
        binding.etMetaBannerMrecId.setText("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
        binding.etMetaInterstitialId.setText("VID_HD_16_9_46S_LINK#YOUR_PLACEMENT_ID")
        binding.etMetaRewardedId.setText("VID_HD_16_9_46S_APP_INSTALL#YOUR_PLACEMENT_ID")
        binding.etMetaNativeId.setText("IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID")
        binding.etMetaNativeBannerId.setText("IMG_16_9_LINK#YOUR_PLACEMENT_ID")
        binding.etMetaPlayableId.setText("PLAYABLE#YOUR_PLACEMENT_ID")
    }

    private fun fillAppLovinTestKeys() {
        binding.switchEnableApplovinAds.isChecked = true
        binding.switchApplovinTestMode.isChecked = true

        binding.etApplovinSdkKey.setText("YOUR_APPLOVIN_SDK_KEY")
        binding.etApplovinBannerId.setText("YOUR_BANNER_AD_UNIT_ID")
        binding.etApplovinMrecId.setText("YOUR_MREC_AD_UNIT_ID")
        binding.etApplovinInterstitialId.setText("YOUR_INTERSTITIAL_AD_UNIT_ID")
        binding.etApplovinRewardedId.setText("YOUR_REWARDED_AD_UNIT_ID")
        binding.etApplovinNativeId.setText("YOUR_NATIVE_AD_UNIT_ID")
        binding.etApplovinAppOpenId.setText("YOUR_APP_OPEN_AD_UNIT_ID")
    }

    private fun populateGamForm() {
        binding.switchEnableGamAds.isChecked = gamConfig.adsEnabled
        binding.switchGamTestMode.isChecked = gamConfig.testMode
        binding.switchGamSafelinkEnabled.isChecked = gamConfig.safelinkGamAdsEnabled
        binding.switchGamInspectorEnabled.isChecked = gamConfig.enableAdInspector

        binding.etGamNetworkCode.setText(gamConfig.networkCode)
        binding.etGamBannerId.setText(gamConfig.bannerAdUnitId)
        binding.etGamInterstitialId.setText(gamConfig.interstitialAdUnitId)
        binding.etGamRewardedId.setText(gamConfig.rewardedAdUnitId)
        binding.etGamRewardedInterstitialId.setText(gamConfig.rewardedInterstitialAdUnitId)
        binding.etGamAppOpenId.setText(gamConfig.appOpenAdUnitId)
        binding.etGamNativeId.setText(gamConfig.nativeAdUnitId)

        val targetingStr = gamConfig.customTargeting.entries.joinToString(", ") { "${it.key}=${it.value}" }
        binding.etGamCustomTargeting.setText(targetingStr)
        binding.etGamInterval.setText(gamConfig.interstitialInterval.toString())
    }

    private fun fillGamTestKeys() {
        binding.switchEnableGamAds.isChecked = true
        binding.switchGamTestMode.isChecked = true
        binding.switchGamSafelinkEnabled.isChecked = true
        binding.switchGamInspectorEnabled.isChecked = true

        binding.etGamNetworkCode.setText("21775744923")
        binding.etGamBannerId.setText(GoogleAdManagerConfig.DEMO_ADAPTIVE_BANNER)
        binding.etGamInterstitialId.setText(GoogleAdManagerConfig.DEMO_INTERSTITIAL)
        binding.etGamRewardedId.setText(GoogleAdManagerConfig.DEMO_REWARDED)
        binding.etGamRewardedInterstitialId.setText(GoogleAdManagerConfig.DEMO_REWARDED_INTERSTITIAL)
        binding.etGamAppOpenId.setText(GoogleAdManagerConfig.DEMO_APP_OPEN)
        binding.etGamNativeId.setText(GoogleAdManagerConfig.DEMO_NATIVE)
        binding.etGamCustomTargeting.setText("category=browser, tier=free, audience=all")
        binding.etGamInterval.setText("3")
    }

    private fun saveGoogleAdsConfig() {
        googleAdsConfig.adsEnabled = binding.switchAdsEnabled.isChecked
        googleAdsConfig.testMode = binding.switchAdsTestMode.isChecked
        googleAdsConfig.safelinkGoogleAdsEnabled = binding.switchSafelinkGoogleAds.isChecked

        googleAdsConfig.appId = binding.etAdmobAppId.text.toString().trim()
        googleAdsConfig.bannerAdUnitId = binding.etAdmobBannerId.text.toString().trim()
        googleAdsConfig.interstitialAdUnitId = binding.etAdmobInterstitialId.text.toString().trim()
        googleAdsConfig.rewardedAdUnitId = binding.etAdmobRewardedId.text.toString().trim()
        googleAdsConfig.nativeAdUnitId = binding.etAdmobNativeId.text.toString().trim()
        googleAdsConfig.interstitialInterval = binding.etAdmobInterval.text.toString().toIntOrNull() ?: 3

        binding.btnSaveGoogleAds.isEnabled = false
        binding.btnSaveGoogleAds.text = "SAVING..."

        val timeoutRunnable = Runnable {
            if (!binding.btnSaveGoogleAds.isEnabled) {
                binding.btnSaveGoogleAds.isEnabled = true
                binding.btnSaveGoogleAds.text = "SAVE ADS CONFIG TO FIREBASE"
                Toast.makeText(this@MainActivity, "Firebase timeout: Please check connection.", Toast.LENGTH_LONG).show()
            }
        }
        binding.root.postDelayed(timeoutRunnable, 6000L)

        db.collection("app_config").document("google_ads")
            .set(googleAdsConfig.toMap())
            .addOnSuccessListener {
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveGoogleAds.isEnabled = true
                binding.btnSaveGoogleAds.text = "SAVE GOOGLE ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Google Ads configuration saved to Firebase!", Toast.LENGTH_SHORT).show()
                updateDashboardStats()
            }
            .addOnFailureListener { e ->
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveGoogleAds.isEnabled = true
                binding.btnSaveGoogleAds.text = "SAVE GOOGLE ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveMetaAdsConfig() {
        metaAdsConfig.enabled = binding.switchMetaAdsEnabled.isChecked
        metaAdsConfig.testMode = binding.switchMetaTestMode.isChecked

        metaAdsConfig.appId = binding.etMetaAppId.text.toString().trim()
        metaAdsConfig.bannerPlacementId = binding.etMetaBannerId.text.toString().trim()
        metaAdsConfig.bannerMrecPlacementId = binding.etMetaBannerMrecId.text.toString().trim()
        metaAdsConfig.interstitialPlacementId = binding.etMetaInterstitialId.text.toString().trim()
        metaAdsConfig.rewardedPlacementId = binding.etMetaRewardedId.text.toString().trim()
        metaAdsConfig.nativePlacementId = binding.etMetaNativeId.text.toString().trim()
        metaAdsConfig.nativeBannerPlacementId = binding.etMetaNativeBannerId.text.toString().trim()
        metaAdsConfig.playablePlacementId = binding.etMetaPlayableId.text.toString().trim()

        binding.btnSaveMetaAds.isEnabled = false
        binding.btnSaveMetaAds.text = "SAVING..."

        val timeoutRunnable = Runnable {
            if (!binding.btnSaveMetaAds.isEnabled) {
                binding.btnSaveMetaAds.isEnabled = true
                binding.btnSaveMetaAds.text = "SAVE META ADS CONFIG TO FIREBASE"
                Toast.makeText(this@MainActivity, "Firebase timeout: Please check connection.", Toast.LENGTH_LONG).show()
            }
        }
        binding.root.postDelayed(timeoutRunnable, 6000L)

        db.collection("app_config").document("facebook_ads")
            .set(metaAdsConfig.toMap())
            .addOnSuccessListener {
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveMetaAds.isEnabled = true
                binding.btnSaveMetaAds.text = "SAVE META ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Meta Audience Network configuration saved to Firebase!", Toast.LENGTH_SHORT).show()
                updateDashboardStats()
            }
            .addOnFailureListener { e ->
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveMetaAds.isEnabled = true
                binding.btnSaveMetaAds.text = "SAVE META ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveAppLovinAdsConfig() {
        applovinAdsConfig.enabled = binding.switchEnableApplovinAds.isChecked
        applovinAdsConfig.testMode = binding.switchApplovinTestMode.isChecked

        applovinAdsConfig.sdkKey = binding.etApplovinSdkKey.text.toString().trim()
        applovinAdsConfig.bannerAdUnitId = binding.etApplovinBannerId.text.toString().trim()
        applovinAdsConfig.mrecAdUnitId = binding.etApplovinMrecId.text.toString().trim()
        applovinAdsConfig.interstitialAdUnitId = binding.etApplovinInterstitialId.text.toString().trim()
        applovinAdsConfig.rewardedAdUnitId = binding.etApplovinRewardedId.text.toString().trim()
        applovinAdsConfig.nativeAdUnitId = binding.etApplovinNativeId.text.toString().trim()
        applovinAdsConfig.appOpenAdUnitId = binding.etApplovinAppOpenId.text.toString().trim()

        binding.btnSaveApplovinAds.isEnabled = false
        binding.btnSaveApplovinAds.text = "SAVING..."

        val timeoutRunnable = Runnable {
            if (!binding.btnSaveApplovinAds.isEnabled) {
                binding.btnSaveApplovinAds.isEnabled = true
                binding.btnSaveApplovinAds.text = "SAVE APPLOVIN ADS CONFIG TO FIREBASE"
                Toast.makeText(this@MainActivity, "Firebase timeout: Please check connection.", Toast.LENGTH_LONG).show()
            }
        }
        binding.root.postDelayed(timeoutRunnable, 6000L)

        db.collection("app_config").document("applovin_ads")
            .set(applovinAdsConfig.toMap())
            .addOnSuccessListener {
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveApplovinAds.isEnabled = true
                binding.btnSaveApplovinAds.text = "SAVE APPLOVIN ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "AppLovin MAX configuration saved to Firebase!", Toast.LENGTH_SHORT).show()
                updateDashboardStats()
            }
            .addOnFailureListener { e ->
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveApplovinAds.isEnabled = true
                binding.btnSaveApplovinAds.text = "SAVE APPLOVIN ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveGlobalAdsToggle(enabled: Boolean) {
        adNetworkConfig.globalAdsEnabled = enabled
        db.collection("app_config").document("ad_network")
            .update("globalAdsEnabled", enabled)
            .addOnSuccessListener {
                val state = if (enabled) "RESUMED" else "PAUSED"
                Toast.makeText(this, "Global ads monetization $state!", Toast.LENGTH_SHORT).show()
                updateDashboardStats()
            }
            .addOnFailureListener {
                db.collection("app_config").document("ad_network")
                    .set(adNetworkConfig.toMap())
                    .addOnSuccessListener { updateDashboardStats() }
            }
    }

    private fun loadAdNetworkConfig() {
        db.collection("app_config").document("ad_network").get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    adNetworkConfig = AdNetworkConfig.fromMap(doc.data!!)
                } else {
                    db.collection("app_config").document("ad_network").set(adNetworkConfig.toMap())
                }
                updateDashboardStats()
                updateSafeLinkActivePlatformBanner()
                setupAdTypeDropdown()
                renderStepTabs()
                if (selectedIndex in stepsList.indices) {
                    populateForm(stepsList[selectedIndex])
                }
                if (currentSection == AdminSection.GOOGLE_ADS) {
                    updateMonetizationUI()
                }
            }
            .addOnFailureListener {
                updateDashboardStats()
            }
    }

    private fun loadGoogleAdsConfig() {
        db.collection("app_config").document("google_ads").get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    googleAdsConfig = GoogleAdsConfig.fromMap(doc.data!!)
                } else {
                    db.collection("app_config").document("google_ads").set(googleAdsConfig.toMap())
                }
                updateDashboardStats()
                if (currentSection == AdminSection.GOOGLE_ADS) {
                    populateGoogleAdsForm()
                }
            }
            .addOnFailureListener {
                updateDashboardStats()
            }
    }

    private fun loadMetaAdsConfig() {
        db.collection("app_config").document("facebook_ads").get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    metaAdsConfig = MetaAdsConfig.fromMap(doc.data!!)
                } else {
                    db.collection("app_config").document("facebook_ads").set(metaAdsConfig.toMap())
                }
                updateDashboardStats()
                if (currentSection == AdminSection.GOOGLE_ADS) {
                    populateMetaAdsForm()
                }
            }
            .addOnFailureListener {
                updateDashboardStats()
            }
    }

    private fun loadAppLovinAdsConfig() {
        db.collection("app_config").document("applovin_ads").get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    applovinAdsConfig = AppLovinAdsConfig.fromMap(doc.data!!)
                } else {
                    db.collection("app_config").document("applovin_ads").set(applovinAdsConfig.toMap())
                }
                updateDashboardStats()
                if (currentSection == AdminSection.GOOGLE_ADS) {
                    populateAppLovinAdsForm()
                }
            }
            .addOnFailureListener {
                updateDashboardStats()
            }
    }

    private fun saveGamAdsConfig() {
        gamConfig.adsEnabled = binding.switchEnableGamAds.isChecked
        gamConfig.testMode = binding.switchGamTestMode.isChecked
        gamConfig.safelinkGamAdsEnabled = binding.switchGamSafelinkEnabled.isChecked
        gamConfig.enableAdInspector = binding.switchGamInspectorEnabled.isChecked

        gamConfig.networkCode = binding.etGamNetworkCode.text.toString().trim().ifEmpty { "21775744923" }
        gamConfig.bannerAdUnitId = binding.etGamBannerId.text.toString().trim()
        gamConfig.interstitialAdUnitId = binding.etGamInterstitialId.text.toString().trim()
        gamConfig.rewardedAdUnitId = binding.etGamRewardedId.text.toString().trim()
        gamConfig.rewardedInterstitialAdUnitId = binding.etGamRewardedInterstitialId.text.toString().trim()
        gamConfig.appOpenAdUnitId = binding.etGamAppOpenId.text.toString().trim()
        gamConfig.nativeAdUnitId = binding.etGamNativeId.text.toString().trim()
        gamConfig.interstitialInterval = binding.etGamInterval.text.toString().toIntOrNull() ?: 3

        val targetingMap = mutableMapOf<String, String>()
        val rawTargeting = binding.etGamCustomTargeting.text.toString().trim()
        if (rawTargeting.isNotEmpty()) {
            val pairs = rawTargeting.split(",")
            for (p in pairs) {
                val kv = p.split("=")
                if (kv.size == 2 && kv[0].isNotBlank()) {
                    targetingMap[kv[0].trim()] = kv[1].trim()
                }
            }
        }
        gamConfig.customTargeting = targetingMap

        binding.btnSaveGamAds.isEnabled = false
        binding.btnSaveGamAds.text = "SAVING..."

        val timeoutRunnable = Runnable {
            if (!binding.btnSaveGamAds.isEnabled) {
                binding.btnSaveGamAds.isEnabled = true
                binding.btnSaveGamAds.text = "SAVE GAM ADS CONFIG TO FIREBASE"
                Toast.makeText(this@MainActivity, "Firebase timeout: Please check connection.", Toast.LENGTH_LONG).show()
            }
        }
        binding.root.postDelayed(timeoutRunnable, 6000L)

        db.collection("app_config").document("google_ad_manager")
            .set(gamConfig.toMap())
            .addOnSuccessListener {
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveGamAds.isEnabled = true
                binding.btnSaveGamAds.text = "SAVE GAM ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Google Ad Manager configuration saved to Firebase!", Toast.LENGTH_SHORT).show()
                updateDashboardStats()
            }
            .addOnFailureListener { e ->
                binding.root.removeCallbacks(timeoutRunnable)
                binding.btnSaveGamAds.isEnabled = true
                binding.btnSaveGamAds.text = "SAVE GAM ADS CONFIG TO FIREBASE"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadGamAdsConfig() {
        db.collection("app_config").document("google_ad_manager").get()
            .addOnSuccessListener { doc ->
                if (doc.exists() && doc.data != null) {
                    gamConfig = GoogleAdManagerConfig.fromMap(doc.data!!)
                } else {
                    db.collection("app_config").document("google_ad_manager").set(gamConfig.toMap())
                }
                updateDashboardStats()
                if (currentSection == AdminSection.GOOGLE_ADS) {
                    populateGamForm()
                }
            }
            .addOnFailureListener {
                updateDashboardStats()
            }
    }

    // =========================================================================
    // PUSH NOTIFICATIONS MODULE
    // =========================================================================

    private val topicOptions = listOf(
        "All Users (all_users)",
        "Beta Testers (beta_users)",
        "Offers & Updates (promo_deals)",
        "Security Alerts (security_alerts)"
    )

    private fun setupNotificationsModule() {
        binding.btnNotifBack.setOnClickListener {
            navigateTo(AdminSection.DASHBOARD)
        }

        // Setup Target Topic Exposed Dropdown
        val topicAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, topicOptions)
        binding.actvNotifTopic.setAdapter(topicAdapter)
        binding.actvNotifTopic.setText(topicOptions[0], false)
        binding.actvNotifTopic.setOnClickListener {
            binding.actvNotifTopic.showDropDown()
        }

        // Live Preview Text Watchers
        val previewWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val title = binding.etNotifTitle.text.toString().trim()
                val body = binding.etNotifBody.text.toString().trim()

                binding.tvPreviewTitle.text = if (title.isNotEmpty()) title else "Notification Title Preview"
                binding.tvPreviewBody.text = if (body.isNotEmpty()) body else "Notification message text will be displayed here."
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etNotifTitle.addTextChangedListener(previewWatcher)
        binding.etNotifBody.addTextChangedListener(previewWatcher)

        // "SAMPLE PROMO" auto filler button
        binding.btnFillSampleNotif.setOnClickListener {
            binding.etNotifTitle.setText("🚀 New Update: Zyro 2.0 Unleashed!")
            binding.etNotifBody.setText("Blazing fast browsing speed with enhanced cyber protection & tracker shielding.")
            binding.etNotifImageUrl.setText("https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800")
            binding.etNotifTargetUrl.setText("https://google.com")
            binding.actvNotifTopic.setText(topicOptions[0], false)
            binding.switchNotifPriority.isChecked = true
            Toast.makeText(this, "Sample promotion loaded into composer!", Toast.LENGTH_SHORT).show()
        }

        // "SEND PUSH NOTIFICATION" button
        binding.btnSendNotification.setOnClickListener {
            sendPushNotification()
        }
    }

    private fun sendPushNotification() {
        val title = binding.etNotifTitle.text.toString().trim()
        val body = binding.etNotifBody.text.toString().trim()
        val imageUrl = binding.etNotifImageUrl.text.toString().trim()
        val targetUrl = binding.etNotifTargetUrl.text.toString().trim()
        val rawTopic = binding.actvNotifTopic.text.toString().trim()
        val topic = when {
            rawTopic.contains("beta_users") -> "beta_users"
            rawTopic.contains("promo_deals") -> "promo_deals"
            rawTopic.contains("security_alerts") -> "security_alerts"
            else -> "all_users"
        }
        val priority = if (binding.switchNotifPriority.isChecked) "HIGH" else "NORMAL"

        if (title.isEmpty()) {
            binding.etNotifTitle.error = "Title is required"
            binding.etNotifTitle.requestFocus()
            return
        }
        if (body.isEmpty()) {
            binding.etNotifBody.error = "Message body is required"
            binding.etNotifBody.requestFocus()
            return
        }

        binding.btnSendNotification.isEnabled = false
        binding.btnSendNotification.text = "BROADCASTING..."

        val notifDoc = db.collection("push_notifications").document()
        val notification = ZyroNotification(
            id = notifDoc.id,
            title = title,
            body = body,
            imageUrl = imageUrl,
            targetUrl = targetUrl,
            topic = topic,
            priority = priority,
            timestamp = System.currentTimeMillis(),
            status = "SENT"
        )

        notifDoc.set(notification.toMap())
            .addOnSuccessListener {
                binding.btnSendNotification.isEnabled = true
                binding.btnSendNotification.text = "SEND PUSH NOTIFICATION"
                Toast.makeText(this, "🎉 Push notification broadcast successfully!", Toast.LENGTH_LONG).show()

                // Clear composer fields
                binding.etNotifTitle.setText("")
                binding.etNotifBody.setText("")
                binding.etNotifImageUrl.setText("")
                binding.etNotifTargetUrl.setText("")
            }
            .addOnFailureListener { e ->
                binding.btnSendNotification.isEnabled = true
                binding.btnSendNotification.text = "SEND PUSH NOTIFICATION"
                Toast.makeText(this, "Failed to send notification: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadNotificationsHistory() {
        db.collection("push_notifications")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(25)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val list = mutableListOf<ZyroNotification>()
                snapshot?.documents?.forEach { doc ->
                    val data = doc.data
                    if (data != null) {
                        list.add(ZyroNotification.fromMap(doc.id, data))
                    }
                }
                renderNotificationsHistory(list)
            }
    }

    private fun renderNotificationsHistory(list: List<ZyroNotification>) {
        binding.notifHistoryContainer.removeAllViews()
        if (list.isEmpty()) {
            binding.tvNotifHistoryEmpty.visibility = View.VISIBLE
            binding.badgeNotifCount.text = "0 SENT"
        } else {
            binding.tvNotifHistoryEmpty.visibility = View.GONE
            binding.badgeNotifCount.text = "${list.size} SENT"
            val sdf = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())

            for (notif in list) {
                val itemBinding = ItemSentNotificationBinding.inflate(
                    layoutInflater,
                    binding.notifHistoryContainer,
                    false
                )

                itemBinding.tvItemTitle.text = notif.title
                itemBinding.tvItemBody.text = notif.body
                itemBinding.tvItemTopic.text = notif.topic.uppercase()
                itemBinding.tvItemPriority.text = notif.priority.uppercase()
                itemBinding.tvItemTime.text = sdf.format(Date(notif.timestamp))

                if (notif.targetUrl.isNotBlank()) {
                    itemBinding.tvItemTargetUrl.visibility = View.VISIBLE
                    itemBinding.tvItemTargetUrl.text = "Target: ${notif.targetUrl}"
                } else {
                    itemBinding.tvItemTargetUrl.visibility = View.GONE
                }

                // Resend button: Load back into composer and scroll to top
                itemBinding.btnItemResend.setOnClickListener {
                    binding.etNotifTitle.setText(notif.title)
                    binding.etNotifBody.setText(notif.body)
                    binding.etNotifImageUrl.setText(notif.imageUrl)
                    binding.etNotifTargetUrl.setText(notif.targetUrl)
                    binding.switchNotifPriority.isChecked = notif.priority.equals("HIGH", ignoreCase = true)
                    binding.viewPushNotifications.smoothScrollTo(0, 0)
                    Toast.makeText(this, "Notification loaded into composer. Tap Send when ready!", Toast.LENGTH_SHORT).show()
                }

                // Delete button: Remove record from Firebase
                itemBinding.btnItemDelete.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("Delete Notification Record?")
                        .setMessage("Are you sure you want to delete this notification record from history?")
                        .setPositiveButton("DELETE") { _, _ ->
                            db.collection("push_notifications").document(notif.id).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Notification record deleted", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("CANCEL", null)
                        .show()
                }

                binding.notifHistoryContainer.addView(itemBinding.root)
            }
        }
    }

    // =========================================================================
    // DYNAMIC SPEED DIAL & BOOKMARKS MODULE
    // =========================================================================

    private fun setupSpeedDialModule() {
        binding.btnSpeedDialBack.setOnClickListener {
            navigateTo(AdminSection.DASHBOARD)
        }

        binding.btnNewTileToggle.setOnClickListener {
            resetSpeedDialForm()
            binding.etSpeedDialTitle.requestFocus()
        }

        binding.btnCancelSpeedDialEdit.setOnClickListener {
            resetSpeedDialForm()
        }

        // Quick Preset Chips
        binding.chipPresetAmazon.setOnClickListener {
            loadPresetToForm("Amazon", "https://www.amazon.com", "AMZ", "#FF9900")
        }
        binding.chipPresetTwitter.setOnClickListener {
            loadPresetToForm("X / Twitter", "https://twitter.com", "X", "#000000")
        }
        binding.chipPresetReddit.setOnClickListener {
            loadPresetToForm("Reddit", "https://www.reddit.com", "RD", "#FF4500")
        }
        binding.chipPresetNetflix.setOnClickListener {
            loadPresetToForm("Netflix", "https://www.netflix.com", "NFLX", "#E50914")
        }
        binding.chipPresetTelegram.setOnClickListener {
            loadPresetToForm("Telegram", "https://web.telegram.org", "TG", "#0088CC")
        }

        // Quick Color Dots
        binding.colorDotOrange.setOnClickListener { setFormColor("#FF6D00") }
        binding.colorDotRed.setOnClickListener { setFormColor("#EF4444") }
        binding.colorDotCyan.setOnClickListener { setFormColor("#00F2FE") }
        binding.colorDotGreen.setOnClickListener { setFormColor("#10B981") }
        binding.colorDotPurple.setOnClickListener { setFormColor("#8B5CF6") }
        binding.colorDotDark.setOnClickListener { setFormColor("#24292E") }

        // Live Editor Preview Watcher
        val speedDialWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateSpeedDialEditorPreview()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        binding.etSpeedDialTitle.addTextChangedListener(speedDialWatcher)
        binding.etSpeedDialUrl.addTextChangedListener(speedDialWatcher)
        binding.etSpeedDialBadge.addTextChangedListener(speedDialWatcher)
        binding.etSpeedDialColor.addTextChangedListener(speedDialWatcher)

        // Reset to Defaults Button
        binding.btnSeedDefaultTiles.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Reset to Default Tiles?")
                .setMessage("This will restore the standard 4 speed dial tiles (Google, YouTube, GitHub, Wikipedia). Proceed?")
                .setPositiveButton("RESET") { _, _ ->
                    seedDefaultSpeedDialTiles()
                }
                .setNegativeButton("CANCEL", null)
                .show()
        }

        // Save Tile Button
        binding.btnSaveSpeedDialTile.setOnClickListener {
            saveSpeedDialTile()
        }
    }

    private fun loadPresetToForm(title: String, url: String, badge: String, color: String) {
        binding.etSpeedDialTitle.setText(title)
        binding.etSpeedDialUrl.setText(url)
        binding.etSpeedDialBadge.setText(badge)
        setFormColor(color)
        Toast.makeText(this, "$title preset loaded!", Toast.LENGTH_SHORT).show()
    }

    private fun setFormColor(hexColor: String) {
        binding.etSpeedDialColor.setText(hexColor)
        updateSpeedDialEditorPreview()
    }

    private fun updateSpeedDialEditorPreview() {
        val title = binding.etSpeedDialTitle.text.toString().trim()
        val url = binding.etSpeedDialUrl.text.toString().trim()
        val customBadge = binding.etSpeedDialBadge.text.toString().trim()
        val colorHex = binding.etSpeedDialColor.text.toString().trim()

        binding.tvPreviewEditorTitle.text = if (title.isNotEmpty()) title else "Google"
        binding.tvPreviewEditorUrl.text = if (url.isNotEmpty()) url else "https://www.google.com"

        val badge = if (customBadge.isNotEmpty()) {
            customBadge
        } else if (title.isNotEmpty()) {
            title.take(2).uppercase()
        } else {
            "G"
        }
        binding.tvPreviewEditorBadge.text = badge

        try {
            val color = Color.parseColor(colorHex)
            binding.previewEditorBadgeFrame.backgroundTintList = ColorStateList.valueOf(color)
        } catch (e: Exception) {
            binding.previewEditorBadgeFrame.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FF6D00"))
        }
    }

    private fun resetSpeedDialForm() {
        editingSpeedDialId = null
        binding.tvEditorHeaderTitle.text = "Add New Speed Dial Tile"
        binding.btnCancelSpeedDialEdit.visibility = View.GONE
        binding.btnSaveSpeedDialTile.text = "SAVE TILE TO FIREBASE"
        binding.etSpeedDialTitle.setText("")
        binding.etSpeedDialUrl.setText("")
        binding.etSpeedDialBadge.setText("")
        binding.etSpeedDialColor.setText("#FF6D00")
        binding.etSpeedDialOrder.setText((speedDialList.size + 1).toString())
        binding.switchSpeedDialEnabled.isChecked = true
        updateSpeedDialEditorPreview()
    }

    private fun saveSpeedDialTile() {
        val title = binding.etSpeedDialTitle.text.toString().trim()
        val url = binding.etSpeedDialUrl.text.toString().trim()
        val customBadge = binding.etSpeedDialBadge.text.toString().trim()
        val colorHex = binding.etSpeedDialColor.text.toString().trim().let {
            if (it.startsWith("#") && (it.length == 7 || it.length == 9)) it else "#FF6D00"
        }
        val order = binding.etSpeedDialOrder.text.toString().toIntOrNull() ?: (speedDialList.size + 1)
        val enabled = binding.switchSpeedDialEnabled.isChecked

        if (title.isEmpty()) {
            binding.etSpeedDialTitle.error = "Title is required"
            binding.etSpeedDialTitle.requestFocus()
            return
        }
        if (url.isEmpty()) {
            binding.etSpeedDialUrl.error = "URL is required"
            binding.etSpeedDialUrl.requestFocus()
            return
        }

        val badge = if (customBadge.isNotEmpty()) customBadge else title.take(2).uppercase()
        val docId = editingSpeedDialId ?: db.collection("speed_dial_bookmarks").document().id

        val item = SpeedDialItem(
            id = docId,
            title = title,
            url = url,
            badgeText = badge,
            bgColor = colorHex,
            order = order,
            enabled = enabled
        )

        binding.btnSaveSpeedDialTile.isEnabled = false
        binding.btnSaveSpeedDialTile.text = "SAVING..."

        db.collection("speed_dial_bookmarks").document(docId)
            .set(item.toMap())
            .addOnSuccessListener {
                binding.btnSaveSpeedDialTile.isEnabled = true
                Toast.makeText(this, "Speed dial tile '$title' saved!", Toast.LENGTH_SHORT).show()
                resetSpeedDialForm()
            }
            .addOnFailureListener { e ->
                binding.btnSaveSpeedDialTile.isEnabled = true
                binding.btnSaveSpeedDialTile.text = "SAVE TILE TO FIREBASE"
                Toast.makeText(this, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun seedDefaultSpeedDialTiles() {
        val defaults = SpeedDialItem.getDefaultItems()
        val batch = db.batch()
        for (item in defaults) {
            val docRef = db.collection("speed_dial_bookmarks").document(item.id)
            batch.set(docRef, item.toMap())
        }
        batch.commit()
            .addOnSuccessListener {
                Toast.makeText(this, "Standard speed dial defaults restored!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to restore defaults: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadSpeedDialFromFirestore() {
        db.collection("speed_dial_bookmarks")
            .orderBy("order", com.google.firebase.firestore.Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot == null || snapshot.isEmpty) {
                    // Seed initial defaults if collection is empty
                    seedDefaultSpeedDialTiles()
                    return@addSnapshotListener
                }

                speedDialList.clear()
                for (doc in snapshot.documents) {
                    val data = doc.data ?: continue
                    speedDialList.add(SpeedDialItem.fromMap(doc.id, data))
                }

                renderSpeedDialList()
                updateDashboardStats()
            }
    }

    private fun renderSpeedDialList() {
        binding.speedDialListContainer.removeAllViews()

        if (speedDialList.isEmpty()) {
            binding.tvSpeedDialEmpty.visibility = View.VISIBLE
            binding.tvSpeedDialCountHint.text = "Total Active Speed Dial Tiles: 0"
        } else {
            binding.tvSpeedDialEmpty.visibility = View.GONE
            val activeCount = speedDialList.count { it.enabled }
            binding.tvSpeedDialCountHint.text = "Total Active Speed Dial Tiles: $activeCount / ${speedDialList.size}"

            for (item in speedDialList) {
                val itemBinding = ItemSpeedDialAdminBinding.inflate(
                    layoutInflater,
                    binding.speedDialListContainer,
                    false
                )

                itemBinding.tvItemTitle.text = item.title
                itemBinding.tvItemUrl.text = item.url
                itemBinding.tvItemBadgeText.text = if (item.badgeText.isNotBlank()) item.badgeText else item.title.take(2).uppercase()
                itemBinding.tvItemOrderBadge.text = "POS #${item.order}"
                itemBinding.tvItemColorPreview.text = "Color: ${item.bgColor}"

                val itemColor = try {
                    Color.parseColor(item.bgColor)
                } catch (e: Exception) {
                    Color.parseColor("#FF6D00")
                }
                itemBinding.frameItemBadge.backgroundTintList = ColorStateList.valueOf(itemColor)

                itemBinding.switchItemStatus.isChecked = item.enabled
                itemBinding.switchItemStatus.setOnCheckedChangeListener { _, isChecked ->
                    db.collection("speed_dial_bookmarks").document(item.id)
                        .update("enabled", isChecked)
                }

                // Edit
                itemBinding.btnItemEdit.setOnClickListener {
                    editingSpeedDialId = item.id
                    binding.tvEditorHeaderTitle.text = "Edit Tile: ${item.title}"
                    binding.btnCancelSpeedDialEdit.visibility = View.VISIBLE
                    binding.btnSaveSpeedDialTile.text = "UPDATE TILE"
                    binding.etSpeedDialTitle.setText(item.title)
                    binding.etSpeedDialUrl.setText(item.url)
                    binding.etSpeedDialBadge.setText(item.badgeText)
                    binding.etSpeedDialColor.setText(item.bgColor)
                    binding.etSpeedDialOrder.setText(item.order.toString())
                    binding.switchSpeedDialEnabled.isChecked = item.enabled
                    updateSpeedDialEditorPreview()
                    binding.viewSpeedDial.smoothScrollTo(0, 0)
                }

                // Delete
                itemBinding.btnItemDelete.setOnClickListener {
                    AlertDialog.Builder(this)
                        .setTitle("Delete '${item.title}'?")
                        .setMessage("Are you sure you want to remove this tile from the browser speed dial?")
                        .setPositiveButton("DELETE") { _, _ ->
                            db.collection("speed_dial_bookmarks").document(item.id).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Tile deleted", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("CANCEL", null)
                        .show()
                }

                binding.speedDialListContainer.addView(itemBinding.root)
            }
        }
    }
}
