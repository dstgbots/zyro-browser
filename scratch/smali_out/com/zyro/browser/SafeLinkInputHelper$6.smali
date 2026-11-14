.class Lcom/zyro/browser/SafeLinkInputHelper$6;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->setupMiddleBannerAd(Landroid/app/Activity;Landroid/view/View;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$isDark:Z

.field final synthetic val$root:Landroid/view/View;


# direct methods
.method constructor <init>(Landroid/view/View;ZLandroid/app/Activity;)V
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 768
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    iput-boolean p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$isDark:Z

    iput-object p3, p0, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 18

    .line 772
    move-object/from16 v1, p0

    const-string v0, "/21775744923/example/banner"

    const-string v2, "ca-app-pub-3940256099942544/6300978111"

    :try_start_6
    iget-object v3, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    const-string v4, "custom_ad_root"

    invoke-static {v3, v4}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v3

    .line 773
    iget-object v4, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v4, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .line 774
    if-nez v3, :cond_17

    return-void

    .line 776
    :cond_17
    iget-object v4, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    const-string v5, "admob_view_slot"

    invoke-static {v4, v5}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v4

    .line 777
    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v5, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/FrameLayout;

    .line 778
    if-nez v4, :cond_2a

    return-void

    .line 781
    :cond_2a
    new-instance v5, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v5}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 782
    const/4 v6, 0x0

    invoke-virtual {v5, v6}, Landroid/graphics/drawable/GradientDrawable;->setShape(I)V

    .line 783
    iget-object v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v7}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v7

    const/high16 v8, 0x41800000    # 16.0f

    invoke-static {v7, v8}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v7

    int-to-float v7, v7

    invoke-virtual {v5, v7}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 784
    iget-boolean v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$isDark:Z

    const/4 v8, -0x1

    if-eqz v7, :cond_4c

    const v7, -0xe1ddd0

    goto :goto_4d

    :cond_4c
    const/4 v7, -0x1

    :goto_4d
    invoke-virtual {v5, v7}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 785
    iget-object v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v7}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v7

    const/high16 v9, 0x3f800000    # 1.0f

    invoke-static {v7, v9}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v7

    iget-boolean v9, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$isDark:Z

    if-eqz v9, :cond_64

    const v9, -0xd2c8b8

    goto :goto_67

    :cond_64
    const v9, -0x1d1710

    :goto_67
    invoke-virtual {v5, v7, v9}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 786
    invoke-virtual {v3, v5}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 789
    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    const-string v7, "custom_ad_badge"

    invoke-static {v5, v7}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v5

    .line 790
    iget-object v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v7, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/TextView;

    .line 791
    if-eqz v5, :cond_a3

    .line 792
    new-instance v7, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v7}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 793
    invoke-virtual {v7, v6}, Landroid/graphics/drawable/GradientDrawable;->setShape(I)V

    .line 794
    iget-object v9, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v9}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v9

    const/high16 v10, 0x40c00000    # 6.0f

    invoke-static {v9, v10}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v9

    int-to-float v9, v9

    invoke-virtual {v7, v9}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 795
    const v9, -0x9c990f

    invoke-virtual {v7, v9}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 796
    invoke-virtual {v5, v7}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 797
    invoke-virtual {v5, v8}, Landroid/widget/TextView;->setTextColor(I)V

    .line 800
    :cond_a3
    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    const-string v7, "ad_headline"

    invoke-static {v5, v7}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v5

    .line 801
    iget-object v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$root:Landroid/view/View;

    invoke-virtual {v7, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v5

    check-cast v5, Landroid/widget/TextView;

    .line 802
    if-eqz v5, :cond_ba

    .line 803
    const-string v7, ""

    invoke-virtual {v5, v7}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 807
    :cond_ba
    invoke-static {}, Lcom/zyro/browser/SafeLinkInputHelper;->destroyBannerAd()V

    .line 808
    invoke-virtual {v4}, Landroid/widget/FrameLayout;->removeAllViews()V

    .line 811
    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    const-string v7, "adNetworkConfig"

    invoke-static {v5, v7}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v5

    .line 812
    iget-object v7, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    const-string v8, "googleAdsConfig"

    invoke-static {v7, v8}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v7

    .line 813
    iget-object v8, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    const-string v9, "gamConfig"

    invoke-static {v8, v9}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v8

    .line 814
    iget-object v9, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    const-string v10, "metaAdsConfig"

    invoke-static {v9, v10}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v9

    .line 817
    const-string v10, "getGlobalAdsEnabled"

    const/4 v11, 0x1

    invoke-static {v5, v10, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v10
    :try_end_e7
    .catchall {:try_start_6 .. :try_end_e7} :catchall_242

    .line 818
    const/16 v12, 0x8

    const-string v13, "LinkScanner"

    if-nez v10, :cond_f6

    .line 819
    :try_start_ed
    const-string v0, "Banner Ad: Global ads turned OFF in admin. Hiding banner card."

    invoke-static {v13, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 820
    invoke-virtual {v3, v12}, Landroid/view/View;->setVisibility(I)V

    .line 821
    return-void

    .line 825
    :cond_f6
    const-string v10, "getActivePlatform"

    const-string v14, "GOOGLE_AD_MANAGER"

    invoke-static {v5, v10, v14}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 826
    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "Banner Ad: Active platform from Admin App = "

    invoke-virtual {v10, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v10

    invoke-static {v13, v10}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 828
    nop

    .line 830
    const-string v10, "GOOGLE_ADMOB"

    invoke-virtual {v10, v5}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v10
    :try_end_11b
    .catchall {:try_start_ed .. :try_end_11b} :catchall_242

    const-string v14, "getBannerAdUnitId"

    const-string v15, "getAdsEnabled"

    const-string v6, "getTestMode"

    const/16 v16, 0x0

    if-eqz v10, :cond_16c

    .line 832
    :try_start_125
    invoke-static {v7, v15, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v5

    .line 833
    const-string v9, "getSafelinkGoogleAdsEnabled"

    invoke-static {v7, v9, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v9

    .line 834
    invoke-static {v7, v6, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v10

    .line 835
    invoke-static {v7, v14, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 836
    if-nez v10, :cond_142

    invoke-static {v7}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v10

    if-eqz v10, :cond_140

    goto :goto_142

    :cond_140
    move-object v2, v7

    goto :goto_143

    .line 837
    :cond_142
    :goto_142
    nop

    .line 840
    :goto_143
    if-eqz v5, :cond_166

    if-eqz v9, :cond_166

    .line 841
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "Loading Google AdMob 300x250 banner with unit: "

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v13, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 842
    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    invoke-static {v5, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smcreateAdMobBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object v2

    move-object/from16 v16, v2

    goto :goto_16b

    .line 844
    :cond_166
    const-string v2, "Google AdMob disabled in admin. Falling back to default Google Ad Manager."

    invoke-static {v13, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 846
    :cond_16b
    :goto_16b
    goto :goto_1c7

    :cond_16c
    const-string v2, "META_AUDIENCE_NETWORK"

    invoke-virtual {v2, v5}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_16b

    .line 848
    const-string v2, "getEnabled"

    invoke-static {v9, v2, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v2

    .line 849
    invoke-static {v9, v6, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v5

    .line 850
    const-string v7, "getBannerPlacementId"

    const-string v10, "IMG_16_9_LINK#YOUR_PLACEMENT_ID"

    invoke-static {v9, v7, v10}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 851
    if-eqz v5, :cond_1a3

    const-string v5, "#"

    invoke-virtual {v7, v5}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v5

    if-nez v5, :cond_1a3

    .line 852
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "IMG_16_9_LINK#"

    invoke-virtual {v5, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    .line 855
    :cond_1a3
    if-eqz v2, :cond_1c2

    .line 856
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Loading Meta Audience Network 300x250 banner with placement: "

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v13, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 857
    iget-object v2, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    invoke-static {v2, v7}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smcreateMetaBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object v16

    goto :goto_1c7

    .line 859
    :cond_1c2
    const-string v2, "Meta Ads disabled in admin. Falling back to default Google Ad Manager."

    invoke-static {v13, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 864
    :goto_1c7
    if-nez v16, :cond_213

    .line 865
    invoke-static {v8, v15, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v2

    .line 866
    const-string v5, "getSafelinkGamAdsEnabled"

    invoke-static {v8, v5, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v5

    .line 867
    invoke-static {v8, v6, v11}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result v6

    .line 868
    invoke-static {v8, v14, v0}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 869
    if-nez v6, :cond_1e6

    invoke-static {v7}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v6

    if-eqz v6, :cond_1e4

    goto :goto_1e6

    :cond_1e4
    move-object v0, v7

    goto :goto_1e7

    .line 870
    :cond_1e6
    :goto_1e6
    nop

    .line 873
    :goto_1e7
    if-eqz v2, :cond_20a

    if-eqz v5, :cond_20a

    .line 874
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "Loading default Google Ad Manager 300x250 banner with unit: "

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v13, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 875
    iget-object v2, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    invoke-static {v2, v0}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smcreateGamBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object v16

    move-object/from16 v0, v16

    goto :goto_215

    .line 877
    :cond_20a
    const-string v0, "Google Ad Manager disabled in admin. Hiding banner card."

    invoke-static {v13, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 878
    invoke-virtual {v3, v12}, Landroid/view/View;->setVisibility(I)V

    .line 879
    return-void

    .line 864
    :cond_213
    move-object/from16 v0, v16

    .line 883
    :goto_215
    if-eqz v0, :cond_23e

    .line 884
    invoke-static {v0}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$sfputsActiveSafeLinkBannerView(Landroid/view/View;)V

    .line 885
    invoke-virtual {v4}, Landroid/widget/FrameLayout;->removeAllViews()V

    .line 886
    new-instance v2, Landroid/widget/FrameLayout$LayoutParams;

    iget-object v5, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    .line 887
    const/high16 v6, 0x43960000    # 300.0f

    invoke-static {v5, v6}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v5

    iget-object v6, v1, Lcom/zyro/browser/SafeLinkInputHelper$6;->val$activity:Landroid/app/Activity;

    .line 888
    const/high16 v7, 0x437a0000    # 250.0f

    invoke-static {v6, v7}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v6

    invoke-direct {v2, v5, v6}, Landroid/widget/FrameLayout$LayoutParams;-><init>(II)V

    .line 890
    const/16 v5, 0x11

    iput v5, v2, Landroid/widget/FrameLayout$LayoutParams;->gravity:I

    .line 891
    invoke-virtual {v4, v0, v2}, Landroid/widget/FrameLayout;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    .line 892
    const/4 v0, 0x0

    invoke-virtual {v3, v0}, Landroid/view/View;->setVisibility(I)V

    .line 893
    goto :goto_241

    .line 894
    :cond_23e
    invoke-virtual {v3, v12}, Landroid/view/View;->setVisibility(I)V
    :try_end_241
    .catchall {:try_start_125 .. :try_end_241} :catchall_242

    .line 899
    :goto_241
    goto :goto_246

    .line 897
    :catchall_242
    move-exception v0

    .line 898
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 900
    :goto_246
    return-void
.end method
