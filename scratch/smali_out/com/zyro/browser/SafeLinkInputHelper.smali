.class public Lcom/zyro/browser/SafeLinkInputHelper;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"


# static fields
.field public static final SAFE_LINK_API_URL:Ljava/lang/String; = "https://zyrobrowser.com/api.php"

.field public static final SAFE_LINK_APP_KEY:Ljava/lang/String; = "b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f"

.field private static final TAG:Ljava/lang/String; = "LinkScanner"

.field private static volatile sActiveSafeLinkBannerView:Landroid/view/View;

.field public static volatile sIsPrefetching:Z

.field public static volatile sPrefetchError:Ljava/lang/String;

.field public static volatile sPrefetchSuccess:Z

.field public static volatile sPrefetchTargetUrl:Ljava/lang/String;

.field public static volatile sPrefetchedDestinationUrl:Ljava/lang/String;

.field public static volatile sPrefetchedToken:Ljava/lang/String;


# direct methods
.method static bridge synthetic -$$Nest$sfputsActiveSafeLinkBannerView(Landroid/view/View;)V
    .registers 1

    sput-object p0, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    return-void
.end method

.method static bridge synthetic -$$Nest$smapplyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->applyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V

    return-void
.end method

.method static bridge synthetic -$$Nest$smcreateAdMobBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->createAdMobBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smcreateGamBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->createGamBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smcreateMetaBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->createMetaBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smdispatchOpenDestination(Landroid/app/Activity;Ljava/lang/String;)V
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->dispatchOpenDestination(Landroid/app/Activity;Ljava/lang/String;)V

    return-void
.end method

.method static bridge synthetic -$$Nest$smdp(Landroid/content/Context;F)I
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$smgetBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z
    .registers 3

    invoke-static {p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->getBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method static bridge synthetic -$$Nest$smgetMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;
    .registers 2

    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->getMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smgetStepName(I)Ljava/lang/String;
    .registers 1

    invoke-static {p0}, Lcom/zyro/browser/SafeLinkInputHelper;->getStepName(I)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smgetStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 3

    invoke-static {p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->getStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic -$$Nest$smhandleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V
    .registers 3

    invoke-static {p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->handleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V

    return-void
.end method

.method static bridge synthetic -$$Nest$smshowAdForStepOnActivity(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 3

    invoke-static {p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->showAdForStepOnActivity(Landroid/app/Activity;ILjava/lang/String;)V

    return-void
.end method

.method static bridge synthetic -$$Nest$smstartProgressOnActivity(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 3

    invoke-static {p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->startProgressOnActivity(Landroid/app/Activity;ILjava/lang/String;)V

    return-void
.end method

.method static constructor <clinit>()V
    .registers 2

    .line 47
    const/4 v0, 0x0

    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchTargetUrl:Ljava/lang/String;

    .line 48
    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    .line 49
    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedToken:Ljava/lang/String;

    .line 50
    const/4 v1, 0x0

    sput-boolean v1, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    .line 51
    sput-boolean v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    .line 52
    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchError:Ljava/lang/String;

    .line 55
    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    return-void
.end method

.method public constructor <init>()V
    .registers 1

    .line 40
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static applyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V
    .registers 6

    .line 905
    if-nez p0, :cond_3

    return-void

    .line 906
    :cond_3
    new-instance v0, Landroid/graphics/drawable/GradientDrawable;

    sget-object v1, Landroid/graphics/drawable/GradientDrawable$Orientation;->TL_BR:Landroid/graphics/drawable/GradientDrawable$Orientation;

    const v2, -0x4abd5

    const v3, -0x15a7f4

    filled-new-array {v2, v3}, [I

    move-result-object v2

    invoke-direct {v0, v1, v2}, Landroid/graphics/drawable/GradientDrawable;-><init>(Landroid/graphics/drawable/GradientDrawable$Orientation;[I)V

    .line 910
    const/high16 v1, 0x41600000    # 14.0f

    invoke-static {p1, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result p1

    int-to-float p1, p1

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 911
    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 912
    const/4 p1, -0x1

    invoke-virtual {p0, p1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 913
    return-void
.end method

.method public static cleanTargetUrl(Ljava/lang/String;)Ljava/lang/String;
    .registers 4

    .line 157
    if-nez p0, :cond_5

    const-string p0, ""

    return-object p0

    .line 158
    :cond_5
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    .line 159
    :goto_9
    const-string v0, "^https?://[^/]+/https?://.*"

    invoke-virtual {p0, v0}, Ljava/lang/String;->matches(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_29

    .line 160
    const-string v0, "://"

    const/16 v1, 0x8

    invoke-virtual {p0, v0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;I)I

    move-result v0

    .line 161
    const/4 v1, -0x1

    if-eq v0, v1, :cond_29

    .line 162
    const-string v2, "http"

    invoke-virtual {p0, v2, v0}, Ljava/lang/String;->lastIndexOf(Ljava/lang/String;I)I

    move-result v0

    .line 163
    if-eq v0, v1, :cond_29

    .line 164
    invoke-virtual {p0, v0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    .line 171
    goto :goto_9

    .line 172
    :cond_29
    return-object p0
.end method

.method private static createAdMobBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 10

    .line 713
    const/4 v0, 0x0

    :try_start_1
    const-string v1, "com.google.android.gms.ads.AdView"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    .line 714
    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Landroid/content/Context;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v1, v3}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v3

    new-array v4, v2, [Ljava/lang/Object;

    aput-object p0, v4, v5

    invoke-virtual {v3, v4}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 715
    const-string v3, "setAdUnitId"

    new-array v4, v2, [Ljava/lang/Class;

    const-class v6, Ljava/lang/String;

    aput-object v6, v4, v5

    invoke-virtual {v1, v3, v4}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 716
    if-eqz p1, :cond_38

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->isEmpty()Z

    move-result v4

    if-nez v4, :cond_38

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    goto :goto_3a

    :cond_38
    const-string p1, "ca-app-pub-3940256099942544/6300978111"

    .line 717
    :goto_3a
    new-array v4, v2, [Ljava/lang/Object;

    aput-object p1, v4, v5

    invoke-virtual {v3, p0, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 719
    const-string p1, "com.google.android.gms.ads.AdSize"

    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p1
    :try_end_47
    .catchall {:try_start_1 .. :try_end_47} :catchall_ba

    .line 720
    nop

    .line 722
    :try_start_48
    const-string v3, "MEDIUM_RECTANGLE"

    invoke-virtual {p1, v3}, Ljava/lang/Class;->getField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v3

    .line 723
    invoke-virtual {v3, v0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3
    :try_end_52
    .catchall {:try_start_48 .. :try_end_52} :catchall_53

    .line 726
    goto :goto_77

    .line 724
    :catchall_53
    move-exception v3

    .line 725
    const/4 v3, 0x2

    :try_start_55
    new-array v4, v3, [Ljava/lang/Class;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v4, v5

    aput-object v6, v4, v2

    invoke-virtual {p1, v4}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v4

    const/16 v6, 0x12c

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    const/16 v7, 0xfa

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    new-array v3, v3, [Ljava/lang/Object;

    aput-object v6, v3, v5

    aput-object v7, v3, v2

    invoke-virtual {v4, v3}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 728
    :goto_77
    const-string v4, "setAdSize"

    new-array v6, v2, [Ljava/lang/Class;

    aput-object p1, v6, v5

    invoke-virtual {v1, v4, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 729
    new-array v4, v2, [Ljava/lang/Object;

    aput-object v3, v4, v5

    invoke-virtual {p1, p0, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 731
    const-string p1, "com.google.android.gms.ads.AdRequest$Builder"

    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p1

    .line 732
    invoke-virtual {p1}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v3

    .line 733
    const-string v4, "build"

    new-array v6, v5, [Ljava/lang/Class;

    invoke-virtual {p1, v4, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 734
    new-array v4, v5, [Ljava/lang/Object;

    invoke-virtual {p1, v3, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    .line 736
    const-string v3, "loadAd"

    const-string v4, "com.google.android.gms.ads.AdRequest"

    invoke-static {v4}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v4

    new-array v6, v2, [Ljava/lang/Class;

    aput-object v4, v6, v5

    invoke-virtual {v1, v3, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 737
    new-array v2, v2, [Ljava/lang/Object;

    aput-object p1, v2, v5

    invoke-virtual {v1, p0, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 738
    check-cast p0, Landroid/view/View;
    :try_end_b9
    .catchall {:try_start_55 .. :try_end_b9} :catchall_ba

    return-object p0

    .line 739
    :catchall_ba
    move-exception p0

    .line 740
    invoke-virtual {p0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object p1

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Error creating AdMob 300x250 AdView: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v1, "LinkScanner"

    invoke-static {v1, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 741
    return-object v0
.end method

.method private static createGamBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 11

    .line 671
    const/4 v0, 0x0

    :try_start_1
    const-string v1, "com.google.android.gms.ads.admanager.AdManagerAdView"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    .line 672
    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Landroid/content/Context;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v1, v3}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v3

    new-array v4, v2, [Ljava/lang/Object;

    aput-object p0, v4, v5

    invoke-virtual {v3, v4}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 673
    const-string v3, "setAdUnitId"

    new-array v4, v2, [Ljava/lang/Class;

    const-class v6, Ljava/lang/String;

    aput-object v6, v4, v5

    invoke-virtual {v1, v3, v4}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 674
    if-eqz p1, :cond_38

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->isEmpty()Z

    move-result v4

    if-nez v4, :cond_38

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    goto :goto_3a

    :cond_38
    const-string p1, "/21775744923/example/banner"

    .line 675
    :goto_3a
    new-array v4, v2, [Ljava/lang/Object;

    aput-object p1, v4, v5

    invoke-virtual {v3, p0, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 677
    const-string p1, "com.google.android.gms.ads.AdSize"

    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p1
    :try_end_47
    .catchall {:try_start_1 .. :try_end_47} :catchall_d8

    .line 678
    nop

    .line 680
    :try_start_48
    const-string v3, "MEDIUM_RECTANGLE"

    invoke-virtual {p1, v3}, Ljava/lang/Class;->getField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v3

    .line 681
    invoke-virtual {v3, v0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3
    :try_end_52
    .catchall {:try_start_48 .. :try_end_52} :catchall_53

    .line 684
    goto :goto_77

    .line 682
    :catchall_53
    move-exception v3

    .line 683
    const/4 v3, 0x2

    :try_start_55
    new-array v4, v3, [Ljava/lang/Class;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v4, v5

    aput-object v6, v4, v2

    invoke-virtual {p1, v4}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v4

    const/16 v6, 0x12c

    invoke-static {v6}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v6

    const/16 v7, 0xfa

    invoke-static {v7}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v7

    new-array v3, v3, [Ljava/lang/Object;

    aput-object v6, v3, v5

    aput-object v7, v3, v2

    invoke-virtual {v4, v3}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 686
    :goto_77
    invoke-static {p1, v2}, Ljava/lang/reflect/Array;->newInstance(Ljava/lang/Class;I)Ljava/lang/Object;

    move-result-object v4

    .line 687
    invoke-static {v4, v5, v3}, Ljava/lang/reflect/Array;->set(Ljava/lang/Object;ILjava/lang/Object;)V
    :try_end_7e
    .catchall {:try_start_55 .. :try_end_7e} :catchall_d8

    .line 690
    :try_start_7e
    const-string v6, "setAdSizes"

    invoke-virtual {v4}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v7

    new-array v8, v2, [Ljava/lang/Class;

    aput-object v7, v8, v5

    invoke-virtual {v1, v6, v8}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v6

    .line 691
    new-array v7, v2, [Ljava/lang/Object;

    aput-object v4, v7, v5

    invoke-virtual {v6, p0, v7}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_93
    .catchall {:try_start_7e .. :try_end_93} :catchall_94

    .line 695
    goto :goto_a6

    .line 692
    :catchall_94
    move-exception v4

    .line 693
    :try_start_95
    const-string v4, "setAdSize"

    new-array v6, v2, [Ljava/lang/Class;

    aput-object p1, v6, v5

    invoke-virtual {v1, v4, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 694
    new-array v4, v2, [Ljava/lang/Object;

    aput-object v3, v4, v5

    invoke-virtual {p1, p0, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 697
    :goto_a6
    const-string p1, "com.google.android.gms.ads.admanager.AdManagerAdRequest$Builder"

    invoke-static {p1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p1

    .line 698
    invoke-virtual {p1}, Ljava/lang/Class;->newInstance()Ljava/lang/Object;

    move-result-object v3

    .line 699
    const-string v4, "build"

    new-array v6, v5, [Ljava/lang/Class;

    invoke-virtual {p1, v4, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 700
    new-array v4, v5, [Ljava/lang/Object;

    invoke-virtual {p1, v3, v4}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    .line 702
    const-string v3, "loadAd"

    const-string v4, "com.google.android.gms.ads.admanager.AdManagerAdRequest"

    invoke-static {v4}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v4

    new-array v6, v2, [Ljava/lang/Class;

    aput-object v4, v6, v5

    invoke-virtual {v1, v3, v6}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 703
    new-array v2, v2, [Ljava/lang/Object;

    aput-object p1, v2, v5

    invoke-virtual {v1, p0, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 704
    check-cast p0, Landroid/view/View;
    :try_end_d7
    .catchall {:try_start_95 .. :try_end_d7} :catchall_d8

    return-object p0

    .line 705
    :catchall_d8
    move-exception p0

    .line 706
    invoke-virtual {p0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object p1

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Error creating GAM 300x250 banner directly: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v1, "LinkScanner"

    invoke-static {v1, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 707
    return-object v0
.end method

.method private static createMetaBanner(Landroid/app/Activity;Ljava/lang/String;)Landroid/view/View;
    .registers 11

    .line 747
    const/4 v0, 0x0

    :try_start_1
    const-string v1, "com.facebook.ads.AdView"

    invoke-static {v1}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v1

    .line 748
    const-string v2, "com.facebook.ads.AdSize"

    invoke-static {v2}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    .line 749
    const-string v3, "RECTANGLE_HEIGHT_250"

    invoke-virtual {v2, v3}, Ljava/lang/Class;->getField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v3

    .line 750
    invoke-virtual {v3, v0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 752
    if-eqz p1, :cond_28

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/String;->isEmpty()Z

    move-result v4

    if-nez v4, :cond_28

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    goto :goto_2a

    :cond_28
    const-string p1, "IMG_16_9_LINK#YOUR_PLACEMENT_ID"

    .line 753
    :goto_2a
    const/4 v4, 0x3

    new-array v5, v4, [Ljava/lang/Class;

    const-class v6, Landroid/content/Context;

    const/4 v7, 0x0

    aput-object v6, v5, v7

    const-class v6, Ljava/lang/String;

    const/4 v8, 0x1

    aput-object v6, v5, v8

    const/4 v6, 0x2

    aput-object v2, v5, v6

    invoke-virtual {v1, v5}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v2

    new-array v4, v4, [Ljava/lang/Object;

    aput-object p0, v4, v7

    aput-object p1, v4, v8

    aput-object v3, v4, v6

    .line 754
    invoke-virtual {v2, v4}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 756
    const-string p1, "loadAd"

    new-array v2, v7, [Ljava/lang/Class;

    invoke-virtual {v1, p1, v2}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 757
    new-array v1, v7, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v1}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    .line 758
    check-cast p0, Landroid/view/View;
    :try_end_59
    .catchall {:try_start_1 .. :try_end_59} :catchall_5a

    return-object p0

    .line 759
    :catchall_5a
    move-exception p0

    .line 760
    invoke-virtual {p0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object p1

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Error creating Meta 300x250 AdView directly: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v1, "LinkScanner"

    invoke-static {v1, p1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 761
    return-object v0
.end method

.method public static destroyBannerAd()V
    .registers 4

    .line 660
    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    if-eqz v0, :cond_20

    .line 662
    :try_start_4
    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    const-string v1, "destroy"

    const/4 v2, 0x0

    new-array v3, v2, [Ljava/lang/Class;

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v0

    .line 663
    sget-object v1, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    new-array v2, v2, [Ljava/lang/Object;

    invoke-virtual {v0, v1, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_1a
    .catchall {:try_start_4 .. :try_end_1a} :catchall_1b

    goto :goto_1c

    .line 664
    :catchall_1b
    move-exception v0

    :goto_1c
    nop

    .line 665
    const/4 v0, 0x0

    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sActiveSafeLinkBannerView:Landroid/view/View;

    .line 667
    :cond_20
    return-void
.end method

.method private static dispatchOpenDestination(Landroid/app/Activity;Ljava/lang/String;)V
    .registers 4

    .line 1192
    sget-boolean v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    if-eqz v0, :cond_b

    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    if-eqz v0, :cond_b

    .line 1193
    sget-object p1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    goto :goto_c

    :cond_b
    nop

    .line 1195
    :goto_c
    if-eqz p1, :cond_1c

    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_1c

    const-string v0, "manual"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_2d

    .line 1196
    :cond_1c
    sget-object p1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchTargetUrl:Ljava/lang/String;

    if-eqz p1, :cond_2b

    sget-object p1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchTargetUrl:Ljava/lang/String;

    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result p1

    if-nez p1, :cond_2b

    .line 1197
    sget-object p1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchTargetUrl:Ljava/lang/String;

    goto :goto_2d

    :cond_2b
    const-string p1, "https://zyrobrowser.com"

    .line 1200
    :cond_2d
    :goto_2d
    const-string v0, "https://ezyshort.xyz/http"

    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_3f

    .line 1201
    const-string v0, "https://ezyshort.xyz/"

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    invoke-virtual {p1, v0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p1

    .line 1204
    :cond_3f
    const-string v0, "http://"

    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_60

    const-string v0, "https://"

    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_60

    .line 1205
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 1208
    :cond_60
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Opening destination in external browser: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "LinkScanner"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 1211
    invoke-static {p0}, Lcom/zyro/browser/SafeLinkInputHelper;->hideSafeLinkScreen(Landroid/app/Activity;)V

    .line 1215
    :try_start_7b
    invoke-static {p0, p1}, Lcom/zyro/browser/ExternalBrowserHelper;->openInExternalBrowser(Landroid/app/Activity;Ljava/lang/String;)V
    :try_end_7e
    .catchall {:try_start_7b .. :try_end_7e} :catchall_7f

    .line 1223
    goto :goto_99

    .line 1216
    :catchall_7f
    move-exception v0

    .line 1217
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 1219
    :try_start_83
    new-instance v0, Landroid/content/Intent;

    const-string v1, "android.intent.action.VIEW"

    invoke-static {p1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object p1

    invoke-direct {v0, v1, p1}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    .line 1220
    const/high16 p1, 0x10000000

    invoke-virtual {v0, p1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 1221
    invoke-virtual {p0, v0}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;)V
    :try_end_96
    .catchall {:try_start_83 .. :try_end_96} :catchall_97

    goto :goto_98

    .line 1222
    :catchall_97
    move-exception p0

    :goto_98
    nop

    .line 1224
    :goto_99
    return-void
.end method

.method private static dp(Landroid/content/Context;F)I
    .registers 2

    .line 58
    if-nez p0, :cond_4

    float-to-int p0, p1

    return p0

    .line 59
    :cond_4
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p0

    iget p0, p0, Landroid/util/DisplayMetrics;->density:F

    mul-float p1, p1, p0

    const/high16 p0, 0x3f000000    # 0.5f

    add-float/2addr p1, p0

    float-to-int p0, p1

    return p0
.end method

.method public static extractScannerTarget(Landroid/net/Uri;)Ljava/lang/String;
    .registers 4

    .line 112
    if-nez p0, :cond_5

    const-string p0, "https://zyrobrowser.com"

    return-object p0

    .line 113
    :cond_5
    const-string v0, "url"

    invoke-virtual {p0, v0}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 114
    if-eqz v0, :cond_17

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_1d

    .line 115
    :cond_17
    const-string v0, "alias"

    invoke-virtual {p0, v0}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 117
    :cond_1d
    if-eqz v0, :cond_29

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_2f

    .line 118
    :cond_29
    const-string v0, "link"

    invoke-virtual {p0, v0}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 120
    :cond_2f
    const-string v1, "dest"

    if-eqz v0, :cond_3d

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_41

    .line 121
    :cond_3d
    invoke-virtual {p0, v1}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 123
    :cond_41
    invoke-virtual {p0, v1}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 124
    const-string v2, "api"

    invoke-virtual {p0, v2}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 125
    invoke-static {v0, v1, p0}, Lcom/zyro/browser/SafeLinkInputHelper;->resolveFullTargetUrl(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method public static extractScannerUuid(Landroid/net/Uri;)Ljava/lang/String;
    .registers 4

    .line 129
    const/4 v0, 0x0

    if-nez p0, :cond_4

    return-object v0

    .line 130
    :cond_4
    const-string v1, "uuid"

    invoke-virtual {p0, v1}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 131
    if-eqz v1, :cond_16

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-eqz v2, :cond_1c

    .line 132
    :cond_16
    const-string v1, "uid"

    invoke-virtual {p0, v1}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 134
    :cond_1c
    if-eqz v1, :cond_22

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    :cond_22
    return-object v0
.end method

.method public static finishSafeLinkOnActivity(Landroid/app/Activity;Ljava/lang/String;)V
    .registers 3

    .line 1277
    const/4 v0, 0x0

    invoke-static {p0, p1, v0}, Lcom/zyro/browser/SafeLinkInputHelper;->handleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V

    .line 1278
    return-void
.end method

.method private static getBooleanGetter(Ljava/lang/Object;Ljava/lang/String;Z)Z
    .registers 6

    .line 640
    if-nez p0, :cond_3

    return p2

    .line 642
    :cond_3
    :try_start_3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    const/4 v1, 0x0

    new-array v2, v1, [Ljava/lang/Class;

    invoke-virtual {v0, p1, v2}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 643
    new-array v0, v1, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 644
    instance-of p1, p0, Ljava/lang/Boolean;

    if-eqz p1, :cond_20

    check-cast p0, Ljava/lang/Boolean;

    invoke-virtual {p0}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p0
    :try_end_1e
    .catchall {:try_start_3 .. :try_end_1e} :catchall_1f

    return p0

    .line 645
    :catchall_1f
    move-exception p0

    :cond_20
    nop

    .line 646
    return p2
.end method

.method public static getFinalUrlWithToken(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 5

    .line 210
    sget-boolean v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    if-eqz v0, :cond_13

    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    if-eqz v0, :cond_13

    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_13

    .line 211
    sget-object p0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    return-object p0

    .line 213
    :cond_13
    if-eqz p0, :cond_1e

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Lcom/zyro/browser/SafeLinkInputHelper;->cleanTargetUrl(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    goto :goto_20

    :cond_1e
    const-string p0, ""

    .line 214
    :goto_20
    nop

    .line 215
    const-string v0, "http://"

    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_66

    const-string v0, "https://"

    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_66

    .line 216
    if-eqz p1, :cond_3a

    invoke-virtual {p1}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_3a

    goto :goto_3c

    :cond_3a
    const-string p1, "https://ezyshort.xyz"

    .line 217
    :goto_3c
    const-string v0, "/"

    invoke-virtual {p1, v0}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_55

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 218
    :cond_55
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 220
    :cond_66
    if-eqz p2, :cond_a6

    invoke-virtual {p2}, Ljava/lang/String;->isEmpty()Z

    move-result p1

    if-nez p1, :cond_a6

    .line 221
    const-string p1, "?"

    invoke-virtual {p0, p1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_8e

    .line 222
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, "&token="

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 224
    :cond_8e
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, "?token="

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 227
    :cond_a6
    return-object p0
.end method

.method private static getId(Landroid/view/View;Ljava/lang/String;)I
    .registers 4

    .line 63
    if-eqz p0, :cond_1c

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    if-nez v0, :cond_9

    goto :goto_1c

    .line 64
    :cond_9
    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p0

    const-string v1, "id"

    invoke-virtual {v0, p1, v1, p0}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result p0

    return p0

    .line 63
    :cond_1c
    :goto_1c
    const/4 p0, 0x0

    return p0
.end method

.method private static getMainActivityField(Landroid/app/Activity;Ljava/lang/String;)Ljava/lang/Object;
    .registers 4

    .line 629
    const/4 v0, 0x0

    if-nez p0, :cond_4

    return-object v0

    .line 631
    :cond_4
    :try_start_4
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object p1

    .line 632
    const/4 v1, 0x1

    invoke-virtual {p1, v1}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 633
    invoke-virtual {p1, p0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0
    :try_end_14
    .catchall {:try_start_4 .. :try_end_14} :catchall_15

    return-object p0

    .line 634
    :catchall_15
    move-exception p0

    .line 635
    return-object v0
.end method

.method public static getPersistentDeviceUuid(Landroid/content/Context;)Ljava/lang/String;
    .registers 5

    .line 231
    const-string v0, "safe_link_device_uuid"

    const-string v1, "8caa17c2-9f3d-5580-e2f0-b5596fcf65f8"

    if-nez p0, :cond_7

    return-object v1

    .line 233
    :cond_7
    :try_start_7
    const-string v2, "zyro_browser_prefs"

    const/4 v3, 0x0

    invoke-virtual {p0, v2, v3}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p0

    .line 234
    const/4 v2, 0x0

    invoke-interface {p0, v0, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    .line 235
    if-eqz v2, :cond_1f

    invoke-virtual {v2}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->isEmpty()Z

    move-result v3

    if-eqz v3, :cond_32

    .line 236
    :cond_1f
    invoke-static {}, Ljava/util/UUID;->randomUUID()Ljava/util/UUID;

    move-result-object v2

    invoke-virtual {v2}, Ljava/util/UUID;->toString()Ljava/lang/String;

    move-result-object v2

    .line 237
    invoke-interface {p0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    invoke-interface {p0, v0, v2}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    move-result-object p0

    invoke-interface {p0}, Landroid/content/SharedPreferences$Editor;->apply()V
    :try_end_32
    .catchall {:try_start_7 .. :try_end_32} :catchall_33

    .line 239
    :cond_32
    return-object v2

    .line 240
    :catchall_33
    move-exception p0

    .line 241
    return-object v1
.end method

.method public static varargs getReferrerParam(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;
    .registers 8

    .line 138
    const/4 v0, 0x0

    if-eqz p0, :cond_58

    invoke-virtual {p0}, Ljava/lang/String;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_a

    goto :goto_58

    .line 139
    :cond_a
    array-length v1, p1

    const/4 v2, 0x0

    :goto_c
    if-ge v2, v1, :cond_57

    aget-object v3, p1, v2

    .line 140
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, "="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    .line 141
    invoke-virtual {p0, v3}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v4

    .line 142
    const/4 v5, -0x1

    if-eq v4, v5, :cond_54

    .line 143
    invoke-virtual {v3}, Ljava/lang/String;->length()I

    move-result p1

    add-int/2addr v4, p1

    .line 144
    const/16 p1, 0x26

    invoke-virtual {p0, p1, v4}, Ljava/lang/String;->indexOf(II)I

    move-result p1

    .line 145
    if-ne p1, v5, :cond_3b

    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result p1

    .line 147
    :cond_3b
    :try_start_3b
    invoke-virtual {p0, v4, p1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    const-string v1, "UTF-8"

    invoke-static {v0, v1}, Ljava/net/URLDecoder;->decode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0
    :try_end_49
    .catchall {:try_start_3b .. :try_end_49} :catchall_4a

    return-object p0

    .line 148
    :catchall_4a
    move-exception v0

    .line 149
    invoke-virtual {p0, v4, p1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    return-object p0

    .line 139
    :cond_54
    add-int/lit8 v2, v2, 0x1

    goto :goto_c

    .line 153
    :cond_57
    return-object v0

    .line 138
    :cond_58
    :goto_58
    return-object v0
.end method

.method private static getStepName(I)Ljava/lang/String;
    .registers 1

    .line 1036
    packed-switch p0, :pswitch_data_10

    .line 1044
    const-string p0, "Security Check"

    return-object p0

    .line 1042
    :pswitch_6
    const-string p0, "Threat Intelligence Scan"

    return-object p0

    .line 1040
    :pswitch_9
    const-string p0, "Redirection Checker"

    return-object p0

    .line 1038
    :pswitch_c
    const-string p0, "SSL Certificate Checker"

    return-object p0

    nop

    :pswitch_data_10
    .packed-switch 0x1
        :pswitch_c
        :pswitch_9
        :pswitch_6
    .end packed-switch
.end method

.method private static getStringGetter(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 6

    .line 650
    if-nez p0, :cond_3

    return-object p2

    .line 652
    :cond_3
    :try_start_3
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v0

    const/4 v1, 0x0

    new-array v2, v1, [Ljava/lang/Class;

    invoke-virtual {v0, p1, v2}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object p1

    .line 653
    new-array v0, v1, [Ljava/lang/Object;

    invoke-virtual {p1, p0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    .line 654
    if-eqz p0, :cond_1c

    invoke-virtual {p0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p0
    :try_end_1a
    .catchall {:try_start_3 .. :try_end_1a} :catchall_1b

    return-object p0

    .line 655
    :catchall_1b
    move-exception p0

    :cond_1c
    nop

    .line 656
    return-object p2
.end method

.method private static handleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V
    .registers 4

    .line 1160
    if-eqz p0, :cond_30

    invoke-virtual {p0}, Landroid/app/Activity;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_9

    goto :goto_30

    .line 1162
    :cond_9
    sget-boolean v0, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    if-eqz v0, :cond_2c

    .line 1164
    if-eqz p2, :cond_1e

    .line 1165
    const/4 v0, 0x0

    invoke-virtual {p2, v0}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 1166
    const v0, 0x3f333333    # 0.7f

    invoke-virtual {p2, v0}, Landroid/widget/TextView;->setAlpha(F)V

    .line 1167
    const-string v0, "Securing Gateway Token..."

    invoke-virtual {p2, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1170
    :cond_1e
    new-instance p2, Ljava/lang/Thread;

    new-instance v0, Lcom/zyro/browser/SafeLinkInputHelper$11;

    invoke-direct {v0, p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper$11;-><init>(Landroid/app/Activity;Ljava/lang/String;)V

    invoke-direct {p2, v0}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 1184
    invoke-virtual {p2}, Ljava/lang/Thread;->start()V

    goto :goto_2f

    .line 1187
    :cond_2c
    invoke-static {p0, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->dispatchOpenDestination(Landroid/app/Activity;Ljava/lang/String;)V

    .line 1189
    :goto_2f
    return-void

    .line 1160
    :cond_30
    :goto_30
    return-void
.end method

.method private static hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z
    .registers 3

    .line 104
    const/4 v0, 0x0

    :try_start_1
    invoke-virtual {p0, p1}, Landroid/net/Uri;->getQueryParameter(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 105
    if-eqz p0, :cond_12

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/String;->isEmpty()Z

    move-result p0
    :try_end_f
    .catchall {:try_start_1 .. :try_end_f} :catchall_13

    if-nez p0, :cond_12

    const/4 v0, 0x1

    :cond_12
    return v0

    .line 106
    :catchall_13
    move-exception p0

    .line 107
    return v0
.end method

.method private static hideSafeLinkScreen(Landroid/app/Activity;)V
    .registers 7

    .line 1227
    if-nez p0, :cond_3

    return-void

    .line 1229
    :cond_3
    invoke-static {}, Lcom/zyro/browser/SafeLinkInputHelper;->destroyBannerAd()V

    .line 1232
    :try_start_6
    instance-of v0, p0, Lcom/zyro/browser/MainActivity;

    if-eqz v0, :cond_12

    .line 1233
    move-object v0, p0

    check-cast v0, Lcom/zyro/browser/MainActivity;

    invoke-static {v0}, Lcom/zyro/browser/MainActivity;->access$hideSafeLinkScreen(Lcom/zyro/browser/MainActivity;)V
    :try_end_10
    .catchall {:try_start_6 .. :try_end_10} :catchall_11

    .line 1234
    return-void

    .line 1236
    :catchall_11
    move-exception v0

    :cond_12
    nop

    .line 1239
    const/4 v0, 0x0

    const/4 v1, 0x1

    :try_start_15
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    const-string v3, "access$hideSafeLinkScreen"

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v4

    new-array v5, v1, [Ljava/lang/Class;

    aput-object v4, v5, v0

    invoke-virtual {v2, v3, v5}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    .line 1240
    invoke-virtual {v2, v1}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1241
    new-array v3, v1, [Ljava/lang/Object;

    aput-object p0, v3, v0

    const/4 v4, 0x0

    invoke-virtual {v2, v4, v3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_32
    .catchall {:try_start_15 .. :try_end_32} :catchall_33

    .line 1242
    return-void

    .line 1243
    :catchall_33
    move-exception v2

    .line 1246
    :try_start_34
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    const-string v3, "hideSafeLinkScreen"

    new-array v4, v0, [Ljava/lang/Class;

    invoke-virtual {v2, v3, v4}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    .line 1247
    invoke-virtual {v2, v1}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1248
    new-array v0, v0, [Ljava/lang/Object;

    invoke-virtual {v2, p0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_48
    .catchall {:try_start_34 .. :try_end_48} :catchall_49

    goto :goto_4a

    .line 1249
    :catchall_49
    move-exception p0

    :goto_4a
    nop

    .line 1250
    return-void
.end method

.method public static isManualMode(Ljava/lang/String;)Z
    .registers 2

    .line 68
    if-eqz p0, :cond_1b

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_1b

    const-string v0, "manual"

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v0, p0}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result p0

    if-eqz p0, :cond_19

    goto :goto_1b

    :cond_19
    const/4 p0, 0x0

    goto :goto_1c

    :cond_1b
    :goto_1b
    const/4 p0, 0x1

    :goto_1c
    return p0
.end method

.method public static isScannerUri(Landroid/net/Uri;)Z
    .registers 9

    .line 72
    const/4 v0, 0x0

    if-nez p0, :cond_4

    return v0

    .line 74
    :cond_4
    :try_start_4
    invoke-virtual {p0}, Landroid/net/Uri;->getScheme()Ljava/lang/String;

    move-result-object v1

    .line 75
    invoke-virtual {p0}, Landroid/net/Uri;->getHost()Ljava/lang/String;

    move-result-object v2

    .line 76
    invoke-virtual {p0}, Landroid/net/Uri;->getPath()Ljava/lang/String;

    move-result-object v3

    .line 77
    if-eqz v1, :cond_16

    invoke-virtual {v1}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v1

    .line 78
    :cond_16
    if-eqz v2, :cond_1c

    invoke-virtual {v2}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v2

    .line 79
    :cond_1c
    if-eqz v3, :cond_22

    invoke-virtual {v3}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v3

    .line 81
    :cond_22
    const-string v4, "zyro"

    invoke-virtual {v4, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4
    :try_end_28
    .catchall {:try_start_4 .. :try_end_28} :catchall_a9

    const-string v5, "safelink"

    const-string v6, "link-scanner"

    const/4 v7, 0x1

    if-eqz v4, :cond_4c

    .line 82
    :try_start_2f
    invoke-virtual {v6, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_4b

    const-string v4, "linkscanner"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_4b

    const-string v4, "scan"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_4b

    invoke-virtual {v5, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_4c

    .line 83
    :cond_4b
    return v7

    .line 86
    :cond_4c
    const-string v2, "http"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_5c

    const-string v2, "https"

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_a8

    .line 87
    :cond_5c
    if-eqz v3, :cond_6b

    invoke-virtual {v3, v6}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_6a

    invoke-virtual {v3, v5}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_6b

    .line 88
    :cond_6a
    return v7

    .line 90
    :cond_6b
    const-string v1, "url"

    invoke-static {p0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_8e

    const-string v1, "alias"

    invoke-static {p0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_8e

    const-string v1, "link"

    invoke-static {p0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_8e

    const-string v1, "dest"

    invoke-static {p0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_8c

    goto :goto_8e

    :cond_8c
    const/4 v1, 0x0

    goto :goto_8f

    :cond_8e
    :goto_8e
    const/4 v1, 0x1

    .line 91
    :goto_8f
    const-string v2, "uuid"

    invoke-static {p0, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_a2

    const-string v2, "uid"

    invoke-static {p0, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->hasQueryParam(Landroid/net/Uri;Ljava/lang/String;)Z

    move-result p0
    :try_end_9d
    .catchall {:try_start_2f .. :try_end_9d} :catchall_a9

    if-eqz p0, :cond_a0

    goto :goto_a2

    :cond_a0
    const/4 p0, 0x0

    goto :goto_a3

    :cond_a2
    :goto_a2
    const/4 p0, 0x1

    .line 92
    :goto_a3
    if-eqz v1, :cond_a8

    if-eqz p0, :cond_a8

    .line 93
    return v7

    .line 98
    :cond_a8
    goto :goto_c6

    .line 96
    :catchall_a9
    move-exception p0

    .line 97
    invoke-virtual {p0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object p0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "isScannerUri error: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v1, "LinkScanner"

    invoke-static {v1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 99
    :goto_c6
    return v0
.end method

.method public static onStepProgressFinished(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 4

    .line 939
    if-nez p0, :cond_3

    return-void

    .line 940
    :cond_3
    new-instance v0, Lcom/zyro/browser/SafeLinkInputHelper$8;

    invoke-direct {v0, p0, p1, p2}, Lcom/zyro/browser/SafeLinkInputHelper$8;-><init>(Landroid/app/Activity;ILjava/lang/String;)V

    invoke-virtual {p0, v0}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 1033
    return-void
.end method

.method public static onStepStarting(Landroid/app/Activity;I)V
    .registers 2

    .line 916
    if-nez p0, :cond_3

    return-void

    .line 917
    :cond_3
    new-instance p1, Lcom/zyro/browser/SafeLinkInputHelper$7;

    invoke-direct {p1, p0}, Lcom/zyro/browser/SafeLinkInputHelper$7;-><init>(Landroid/app/Activity;)V

    invoke-virtual {p0, p1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 936
    return-void
.end method

.method public static prefetchToken(Landroid/app/Activity;Ljava/lang/String;)V
    .registers 6

    .line 246
    if-eqz p0, :cond_d7

    if-eqz p1, :cond_d7

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_10

    goto/16 :goto_d7

    .line 249
    :cond_10
    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    .line 250
    const-string v0, "manual"

    invoke-virtual {v0, p1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1d

    .line 251
    return-void

    .line 255
    :cond_1d
    nop

    .line 257
    const/4 v0, 0x1

    :try_start_1f
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v1

    const-string v2, "currentSafeLinkAlias"

    invoke-virtual {v1, v2}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v1

    .line 258
    invoke-virtual {v1, v0}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 259
    invoke-virtual {v1, p0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/String;

    .line 260
    if-eqz v1, :cond_4a

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_4a

    invoke-static {v1}, Lcom/zyro/browser/SafeLinkInputHelper;->isManualMode(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_4a

    .line 261
    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1
    :try_end_48
    .catchall {:try_start_1f .. :try_end_48} :catchall_49

    goto :goto_4a

    .line 263
    :catchall_49
    move-exception v1

    :cond_4a
    :goto_4a
    nop

    .line 265
    invoke-static {p1}, Lcom/zyro/browser/SafeLinkInputHelper;->cleanTargetUrl(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 266
    const-string v1, "http://"

    invoke-virtual {p1, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_70

    const-string v1, "https://"

    invoke-virtual {p1, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_70

    .line 267
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 270
    :cond_70
    sput-object p1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchTargetUrl:Ljava/lang/String;

    .line 271
    const/4 v1, 0x0

    sput-object v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    .line 272
    sput-object v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedToken:Ljava/lang/String;

    .line 273
    sput-boolean v0, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    .line 274
    const/4 v2, 0x0

    sput-boolean v2, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    .line 275
    sput-object v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchError:Ljava/lang/String;

    .line 278
    nop

    .line 280
    :try_start_7f
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v2

    const-string v3, "currentSafeLinkUuid"

    invoke-virtual {v2, v3}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v2

    .line 281
    invoke-virtual {v2, v0}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 282
    invoke-virtual {v2, p0}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;
    :try_end_92
    .catchall {:try_start_7f .. :try_end_92} :catchall_94

    .line 283
    move-object v1, v0

    goto :goto_95

    :catchall_94
    move-exception v0

    .line 285
    :goto_95
    if-eqz v1, :cond_a1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_a5

    .line 286
    :cond_a1
    invoke-static {p0}, Lcom/zyro/browser/SafeLinkInputHelper;->getPersistentDeviceUuid(Landroid/content/Context;)Ljava/lang/String;

    move-result-object v1

    .line 288
    :cond_a5
    nop

    .line 289
    nop

    .line 291
    new-instance p0, Ljava/lang/StringBuilder;

    invoke-direct {p0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "Starting Link Scanner Token prefetch: alias="

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v0, ", uuid="

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v0, "LinkScanner"

    invoke-static {v0, p0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 293
    new-instance p0, Ljava/lang/Thread;

    new-instance v0, Lcom/zyro/browser/SafeLinkInputHelper$1;

    invoke-direct {v0, p1, v1}, Lcom/zyro/browser/SafeLinkInputHelper$1;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    invoke-direct {p0, v0}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 363
    invoke-virtual {p0}, Ljava/lang/Thread;->start()V

    .line 364
    return-void

    .line 247
    :cond_d7
    :goto_d7
    return-void
.end method

.method public static resolveFullTargetUrl(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .registers 7

    .line 176
    nop

    .line 177
    const-string v0, "http://"

    const-string v1, "https://"

    if-eqz p1, :cond_17

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_17

    .line 178
    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    goto/16 :goto_93

    .line 179
    :cond_17
    if-eqz p0, :cond_32

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p1

    if-nez p1, :cond_2d

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p1

    if-eqz p1, :cond_32

    .line 180
    :cond_2d
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    goto :goto_93

    .line 181
    :cond_32
    const/4 p1, 0x0

    if-eqz p0, :cond_92

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/String;->isEmpty()Z

    move-result v2

    if-nez v2, :cond_92

    .line 182
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    .line 183
    if-eqz p2, :cond_7a

    invoke-virtual {p2, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_51

    invoke-virtual {p2, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_7a

    .line 185
    :cond_51
    const/16 v2, 0x2f

    :try_start_53
    invoke-virtual {p2, v2}, Ljava/lang/String;->lastIndexOf(I)I

    move-result v2

    .line 186
    const/16 v3, 0x8

    if-le v2, v3, :cond_77

    .line 187
    const/4 v3, 0x0

    invoke-virtual {p2, v3, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object p2

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v2, "/"

    invoke-virtual {p2, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1
    :try_end_77
    .catchall {:try_start_53 .. :try_end_77} :catchall_78

    .line 189
    :cond_77
    :goto_77
    goto :goto_7a

    :catchall_78
    move-exception p2

    goto :goto_77

    .line 191
    :cond_7a
    :goto_7a
    if-nez p1, :cond_90

    .line 192
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string p2, "https://ezyshort.xyz/"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    goto :goto_93

    .line 191
    :cond_90
    move-object p0, p1

    goto :goto_93

    .line 196
    :cond_92
    move-object p0, p1

    :goto_93
    if-eqz p0, :cond_be

    invoke-virtual {p0}, Ljava/lang/String;->isEmpty()Z

    move-result p1

    if-eqz p1, :cond_9c

    goto :goto_be

    .line 200
    :cond_9c
    invoke-static {p0}, Lcom/zyro/browser/SafeLinkInputHelper;->cleanTargetUrl(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 202
    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p1

    if-nez p1, :cond_bd

    invoke-virtual {p0, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p1

    if-nez p1, :cond_bd

    .line 203
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    .line 206
    :cond_bd
    return-object p0

    .line 197
    :cond_be
    :goto_be
    const-string p0, "https://zyrobrowser.com"

    return-object p0
.end method

.method private static setupMiddleBannerAd(Landroid/app/Activity;Landroid/view/View;Z)V
    .registers 4

    .line 766
    if-eqz p0, :cond_e

    if-nez p1, :cond_5

    goto :goto_e

    .line 768
    :cond_5
    new-instance v0, Lcom/zyro/browser/SafeLinkInputHelper$6;

    invoke-direct {v0, p1, p2, p0}, Lcom/zyro/browser/SafeLinkInputHelper$6;-><init>(Landroid/view/View;ZLandroid/app/Activity;)V

    invoke-virtual {p0, v0}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 902
    return-void

    .line 766
    :cond_e
    :goto_e
    return-void
.end method

.method public static setupSafeLinkMode(Landroid/app/Activity;Landroid/view/View;Ljava/lang/String;)V
    .registers 16

    .line 367
    if-eqz p0, :cond_1ab

    if-nez p1, :cond_6

    goto/16 :goto_1ab

    .line 370
    :cond_6
    :try_start_6
    const-string v0, "tv_target_url"

    invoke-static {p1, v0}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v0

    .line 371
    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 372
    instance-of v1, v0, Landroid/widget/EditText;

    if-nez v1, :cond_15

    .line 373
    return-void

    .line 375
    :cond_15
    check-cast v0, Landroid/widget/EditText;

    .line 377
    const-string v1, "btn_safelink_continue"

    invoke-static {p1, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 378
    invoke-virtual {p1, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 379
    instance-of v2, v1, Landroid/widget/TextView;

    const/4 v3, 0x0

    if-eqz v2, :cond_29

    check-cast v1, Landroid/widget/TextView;

    goto :goto_2a

    :cond_29
    move-object v1, v3

    .line 381
    :goto_2a
    const-string v2, "tv_ping"

    invoke-static {p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v2

    .line 382
    invoke-virtual {p1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    move-object v6, v2

    check-cast v6, Landroid/widget/TextView;

    .line 384
    const-string v2, "tv_safelink_headline"

    invoke-static {p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v2

    .line 385
    invoke-virtual {p1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    move-object v8, v2

    check-cast v8, Landroid/widget/TextView;

    .line 387
    const-string v2, "pb_safelink_global"

    invoke-static {p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v2

    .line 388
    invoke-virtual {p1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    move-object v9, v2

    check-cast v9, Landroid/widget/ProgressBar;

    .line 390
    const-string v2, "iv_radar_shield"

    invoke-static {p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v2

    .line 391
    invoke-virtual {p1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    move-object v12, v2

    check-cast v12, Landroid/widget/ImageView;

    .line 393
    const-string v2, "safelink_steps_container"

    invoke-static {p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->getId(Landroid/view/View;Ljava/lang/String;)I

    move-result v2

    .line 394
    invoke-virtual {p1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    move-object v11, v2

    check-cast v11, Landroid/widget/LinearLayout;

    .line 397
    nop

    .line 398
    invoke-virtual {v0}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object v2

    instance-of v2, v2, Landroid/view/ViewGroup;

    const/4 v4, 0x1

    if-eqz v2, :cond_91

    .line 399
    invoke-virtual {v0}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object v2

    check-cast v2, Landroid/view/ViewGroup;

    .line 400
    invoke-virtual {v2}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v5

    if-le v5, v4, :cond_91

    invoke-virtual {v2, v4}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v5

    instance-of v5, v5, Landroid/widget/TextView;

    if-eqz v5, :cond_91

    .line 401
    invoke-virtual {v2, v4}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    move-object v7, v2

    goto :goto_92

    .line 404
    :cond_91
    move-object v7, v3

    :goto_92
    nop

    .line 406
    sget-boolean v2, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    .line 407
    if-eqz v2, :cond_9b

    const v5, -0x9f5a06

    goto :goto_9e

    :cond_9b
    const v5, -0xf0e8d6

    .line 408
    :goto_9e
    if-eqz v2, :cond_a4

    const v10, -0x9b8b75

    goto :goto_a7

    :cond_a4
    const v10, -0x6b5c48

    .line 409
    :goto_a7
    invoke-virtual {v0, v5}, Landroid/widget/EditText;->setTextColor(I)V

    .line 410
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setHintTextColor(I)V

    .line 413
    invoke-static {p0, p1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->setupMiddleBannerAd(Landroid/app/Activity;Landroid/view/View;Z)V

    .line 415
    invoke-static {p2}, Lcom/zyro/browser/SafeLinkInputHelper;->isManualMode(Ljava/lang/String;)Z

    move-result v2
    :try_end_b4
    .catchall {:try_start_6 .. :try_end_b4} :catchall_1a6

    .line 417
    const-string v5, "Link Scanner"

    const/4 v10, 0x0

    if-eqz v2, :cond_144

    .line 419
    :try_start_b9
    const-string p2, ""

    invoke-virtual {v0, p2}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 420
    const-string p2, "Enter or paste link to verify..."

    invoke-virtual {v0, p2}, Landroid/widget/EditText;->setHint(Ljava/lang/CharSequence;)V

    .line 421
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setFocusable(Z)V

    .line 422
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setFocusableInTouchMode(Z)V

    .line 423
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setClickable(Z)V

    .line 424
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setLongClickable(Z)V

    .line 425
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setCursorVisible(Z)V

    .line 426
    invoke-virtual {v0, v3}, Landroid/widget/EditText;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 427
    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setSingleLine(Z)V

    .line 428
    invoke-static {}, Landroid/text/method/TextKeyListener;->getInstance()Landroid/text/method/TextKeyListener;

    move-result-object p2

    invoke-virtual {v0, p2}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 430
    new-instance p2, Lcom/zyro/browser/SafeLinkInputHelper$2;

    invoke-direct {p2, v0, p1}, Lcom/zyro/browser/SafeLinkInputHelper$2;-><init>(Landroid/widget/EditText;Landroid/view/View;)V

    invoke-virtual {v0, p2}, Landroid/widget/EditText;->setOnFocusChangeListener(Landroid/view/View$OnFocusChangeListener;)V

    .line 449
    if-eqz v6, :cond_ee

    .line 450
    const-string p2, "\u25cf MANUAL SCAN"

    invoke-virtual {v6, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 452
    :cond_ee
    if-eqz v7, :cond_f5

    .line 453
    const-string p2, "Enter any URL to test threat & security levels"

    invoke-virtual {v7, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 455
    :cond_f5
    if-eqz v8, :cond_fa

    .line 456
    invoke-virtual {v8, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 458
    :cond_fa
    if-eqz v9, :cond_100

    .line 459
    const/4 p2, 0x4

    invoke-virtual {v9, p2}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 463
    :cond_100
    if-eqz v11, :cond_113

    .line 464
    nop

    :goto_103
    invoke-virtual {v11}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result p2

    if-ge v10, p2, :cond_113

    .line 465
    invoke-virtual {v11, v10}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object p2

    .line 466
    invoke-static {p2, v10}, Lcom/zyro/browser/SafeLinkAnimationHelper;->initStepView(Landroid/view/View;I)V

    .line 464
    add-int/lit8 v10, v10, 0x1

    goto :goto_103

    .line 470
    :cond_113
    if-eqz v1, :cond_1a5

    .line 471
    invoke-virtual {v1, v4}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 472
    const/high16 p2, 0x3f800000    # 1.0f

    invoke-virtual {v1, p2}, Landroid/widget/TextView;->setAlpha(F)V

    .line 473
    const-string p2, "Start Security Scan \u2192"

    invoke-virtual {v1, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 474
    invoke-virtual {p1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-static {v1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->applyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V

    .line 476
    new-instance p2, Lcom/zyro/browser/SafeLinkInputHelper$3;

    move-object v2, p2

    move-object v3, v0

    move-object v4, p0

    move-object v5, p1

    move-object v10, v1

    invoke-direct/range {v2 .. v12}, Lcom/zyro/browser/SafeLinkInputHelper$3;-><init>(Landroid/widget/EditText;Landroid/app/Activity;Landroid/view/View;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/ProgressBar;Landroid/widget/TextView;Landroid/widget/LinearLayout;Landroid/widget/ImageView;)V

    .line 561
    new-instance p0, Lcom/zyro/browser/SafeLinkInputHelper$4;

    invoke-direct {p0, p2}, Lcom/zyro/browser/SafeLinkInputHelper$4;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v1, p0}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 568
    new-instance p0, Lcom/zyro/browser/SafeLinkInputHelper$5;

    invoke-direct {p0, p2}, Lcom/zyro/browser/SafeLinkInputHelper$5;-><init>(Ljava/lang/Runnable;)V

    invoke-virtual {v0, p0}, Landroid/widget/EditText;->setOnEditorActionListener(Landroid/widget/TextView$OnEditorActionListener;)V

    .line 583
    goto :goto_1a5

    .line 587
    :cond_144
    nop

    .line 588
    if-eqz p2, :cond_159

    const-string v2, "https://ezyshort.xyz/http"

    invoke-virtual {p2, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_159

    .line 589
    const-string v2, "https://ezyshort.xyz/"

    invoke-virtual {v2}, Ljava/lang/String;->length()I

    move-result v2

    invoke-virtual {p2, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p2

    .line 592
    :cond_159
    invoke-virtual {v0, p2}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 593
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setFocusable(Z)V

    .line 594
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setFocusableInTouchMode(Z)V

    .line 595
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setClickable(Z)V

    .line 596
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setLongClickable(Z)V

    .line 597
    invoke-virtual {v0, v10}, Landroid/widget/EditText;->setCursorVisible(Z)V

    .line 598
    invoke-virtual {v0, v3}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 599
    sget-object v2, Landroid/text/TextUtils$TruncateAt;->END:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 601
    if-eqz v6, :cond_17a

    .line 602
    const-string v0, "\u25cf OFFICIAL & AUTHENTIC"

    invoke-virtual {v6, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 604
    :cond_17a
    if-eqz v7, :cond_181

    .line 605
    const-string v0, "Host: edge.cloudflare.net \u2022 TLS 1.3 Validated"

    invoke-virtual {v7, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 607
    :cond_181
    if-eqz v8, :cond_186

    .line 608
    invoke-virtual {v8, v5}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 610
    :cond_186
    if-eqz v9, :cond_18b

    .line 611
    invoke-virtual {v9, v10}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 613
    :cond_18b
    if-eqz v1, :cond_1a2

    .line 614
    invoke-virtual {v1, v10}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 615
    const v0, 0x3f19999a    # 0.6f

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setAlpha(F)V

    .line 616
    const-string v0, "Validating..."

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 617
    invoke-virtual {p1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-static {v1, p1}, Lcom/zyro/browser/SafeLinkInputHelper;->applyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V

    .line 621
    :cond_1a2
    invoke-static {p0, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->prefetchToken(Landroid/app/Activity;Ljava/lang/String;)V
    :try_end_1a5
    .catchall {:try_start_b9 .. :try_end_1a5} :catchall_1a6

    .line 625
    :cond_1a5
    :goto_1a5
    goto :goto_1aa

    .line 623
    :catchall_1a6
    move-exception p0

    .line 624
    invoke-virtual {p0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 626
    :goto_1aa
    return-void

    .line 367
    :cond_1ab
    :goto_1ab
    return-void
.end method

.method private static showAdForStepOnActivity(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 11

    .line 1254
    :try_start_0
    instance-of v0, p0, Lcom/zyro/browser/MainActivity;

    if-eqz v0, :cond_c

    .line 1255
    move-object v0, p0

    check-cast v0, Lcom/zyro/browser/MainActivity;

    invoke-static {v0, p1, p2}, Lcom/zyro/browser/MainActivity;->access$showStepAdPopup(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V
    :try_end_a
    .catchall {:try_start_0 .. :try_end_a} :catchall_b

    .line 1256
    return-void

    .line 1258
    :catchall_b
    move-exception v0

    :cond_c
    nop

    .line 1261
    const/4 v0, 0x2

    const/4 v1, 0x0

    const/4 v2, 0x1

    :try_start_10
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v3

    const-string v4, "access$showStepAdPopup"

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    const/4 v6, 0x3

    new-array v7, v6, [Ljava/lang/Class;

    aput-object v5, v7, v1

    sget-object v5, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v5, v7, v2

    const-class v5, Ljava/lang/String;

    aput-object v5, v7, v0

    invoke-virtual {v3, v4, v7}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 1262
    invoke-virtual {v3, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1263
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    new-array v5, v6, [Ljava/lang/Object;

    aput-object p0, v5, v1

    aput-object v4, v5, v2

    aput-object p2, v5, v0

    const/4 v4, 0x0

    invoke-virtual {v3, v4, v5}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_3e
    .catchall {:try_start_10 .. :try_end_3e} :catchall_3f

    .line 1264
    return-void

    .line 1265
    :catchall_3f
    move-exception v3

    .line 1268
    :try_start_40
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v3

    const-string v4, "showStepAdPopup"

    new-array v5, v0, [Ljava/lang/Class;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v5, v1

    const-class v6, Ljava/lang/String;

    aput-object v6, v5, v2

    invoke-virtual {v3, v4, v5}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 1269
    invoke-virtual {v3, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1270
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    new-array v0, v0, [Ljava/lang/Object;

    aput-object p1, v0, v1

    aput-object p2, v0, v2

    invoke-virtual {v3, p0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_64
    .catchall {:try_start_40 .. :try_end_64} :catchall_65

    .line 1273
    goto :goto_69

    .line 1271
    :catchall_65
    move-exception p0

    .line 1272
    invoke-virtual {p0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 1274
    :goto_69
    return-void
.end method

.method public static showWatchVideoDialog(Landroid/app/Activity;ILjava/lang/String;Ljava/lang/String;)V
    .registers 20

    .line 1049
    move-object/from16 v1, p0

    move/from16 v2, p1

    move-object/from16 v3, p3

    if-eqz v1, :cond_19c

    invoke-virtual/range {p0 .. p0}, Landroid/app/Activity;->isFinishing()Z

    move-result v0

    if-eqz v0, :cond_10

    goto/16 :goto_19c

    .line 1052
    :cond_10
    :try_start_10
    new-instance v0, Landroid/app/Dialog;

    invoke-direct {v0, v1}, Landroid/app/Dialog;-><init>(Landroid/content/Context;)V

    .line 1053
    const/4 v4, 0x1

    invoke-virtual {v0, v4}, Landroid/app/Dialog;->requestWindowFeature(I)Z

    .line 1056
    new-instance v5, Landroid/widget/LinearLayout;

    invoke-direct {v5, v1}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 1057
    invoke-virtual {v5, v4}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 1058
    const/high16 v6, 0x41b00000    # 22.0f

    invoke-static {v1, v6}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v6

    .line 1059
    invoke-virtual {v5, v6, v6, v6, v6}, Landroid/widget/LinearLayout;->setPadding(IIII)V

    .line 1061
    new-instance v6, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v6}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 1062
    const/4 v7, 0x0

    invoke-virtual {v6, v7}, Landroid/graphics/drawable/GradientDrawable;->setShape(I)V

    .line 1063
    const/high16 v8, 0x41a00000    # 20.0f

    invoke-static {v1, v8}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v8

    int-to-float v8, v8

    invoke-virtual {v6, v8}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 1064
    const v8, -0xe1ddd0

    invoke-virtual {v6, v8}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 1065
    const/high16 v8, 0x3f800000    # 1.0f

    invoke-static {v1, v8}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v8

    const v9, -0xd2c8b8

    invoke-virtual {v6, v8, v9}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 1066
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 1069
    new-instance v6, Landroid/widget/TextView;

    invoke-direct {v6, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 1070
    const-string v8, "Watch a video to continue?"

    invoke-virtual {v6, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1071
    const/high16 v8, 0x41900000    # 18.0f

    invoke-virtual {v6, v8}, Landroid/widget/TextView;->setTextSize(F)V

    .line 1072
    const/4 v8, -0x1

    invoke-virtual {v6, v8}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1073
    sget-object v9, Landroid/graphics/Typeface;->DEFAULT_BOLD:Landroid/graphics/Typeface;

    invoke-virtual {v6, v9}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    .line 1074
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 1077
    new-instance v6, Landroid/widget/TextView;

    invoke-direct {v6, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 1078
    invoke-virtual/range {p2 .. p2}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v9

    new-instance v10, Ljava/lang/StringBuilder;

    invoke-direct {v10}, Ljava/lang/StringBuilder;-><init>()V

    const-string v11, "Watch a short video ad to run "

    invoke-virtual {v10, v11}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    const-string v10, "."

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-virtual {v6, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1079
    const/high16 v9, 0x41600000    # 14.0f

    invoke-virtual {v6, v9}, Landroid/widget/TextView;->setTextSize(F)V

    .line 1080
    const v10, -0x6b5c48

    invoke-virtual {v6, v10}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1081
    new-instance v10, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v11, -0x2

    invoke-direct {v10, v8, v11}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 1084
    invoke-static {v1, v9}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v9

    iput v9, v10, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 1085
    const/high16 v9, 0x41c00000    # 24.0f

    invoke-static {v1, v9}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v9

    iput v9, v10, Landroid/widget/LinearLayout$LayoutParams;->bottomMargin:I

    .line 1086
    invoke-virtual {v6, v10}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 1087
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 1090
    new-instance v6, Landroid/widget/LinearLayout;

    invoke-direct {v6, v1}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;)V

    .line 1091
    invoke-virtual {v6, v7}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 1092
    const v9, 0x800015

    invoke-virtual {v6, v9}, Landroid/widget/LinearLayout;->setGravity(I)V

    .line 1093
    new-instance v9, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v9, v8, v11}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 1096
    invoke-virtual {v6, v9}, Landroid/widget/LinearLayout;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 1099
    new-instance v8, Landroid/widget/TextView;

    invoke-direct {v8, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 1100
    const-string v9, "Not now"

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1101
    const/high16 v9, 0x41680000    # 14.5f

    invoke-virtual {v8, v9}, Landroid/widget/TextView;->setTextSize(F)V

    .line 1102
    const v10, -0x7e7308

    invoke-virtual {v8, v10}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1103
    sget-object v12, Landroid/graphics/Typeface;->DEFAULT_BOLD:Landroid/graphics/Typeface;

    invoke-virtual {v8, v12}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    .line 1104
    const/high16 v12, 0x41400000    # 12.0f

    invoke-static {v1, v12}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v13

    const/high16 v14, 0x41000000    # 8.0f

    invoke-static {v1, v14}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v15

    invoke-static {v1, v12}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v7

    invoke-static {v1, v14}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v11

    invoke-virtual {v8, v13, v15, v7, v11}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 1105
    invoke-virtual {v8, v4}, Landroid/widget/TextView;->setClickable(Z)V

    .line 1106
    invoke-virtual {v8, v4}, Landroid/widget/TextView;->setFocusable(Z)V

    .line 1107
    new-instance v7, Lcom/zyro/browser/SafeLinkInputHelper$9;

    invoke-direct {v7, v0}, Lcom/zyro/browser/SafeLinkInputHelper$9;-><init>(Landroid/app/Dialog;)V

    invoke-virtual {v8, v7}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1113
    invoke-virtual {v6, v8}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 1116
    new-instance v7, Landroid/widget/TextView;

    invoke-direct {v7, v1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;)V

    .line 1117
    const-string v8, "Watch video"

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1118
    invoke-virtual {v7, v9}, Landroid/widget/TextView;->setTextSize(F)V

    .line 1119
    invoke-virtual {v7, v10}, Landroid/widget/TextView;->setTextColor(I)V

    .line 1120
    sget-object v8, Landroid/graphics/Typeface;->DEFAULT_BOLD:Landroid/graphics/Typeface;

    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setTypeface(Landroid/graphics/Typeface;)V

    .line 1121
    invoke-static {v1, v12}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v8

    invoke-static {v1, v14}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v9

    invoke-static {v1, v12}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v10

    invoke-static {v1, v14}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v11

    invoke-virtual {v7, v8, v9, v10, v11}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 1122
    new-instance v8, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v9, -0x2

    invoke-direct {v8, v9, v9}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 1125
    const/high16 v9, 0x41200000    # 10.0f

    invoke-static {v1, v9}, Lcom/zyro/browser/SafeLinkInputHelper;->dp(Landroid/content/Context;F)I

    move-result v9

    iput v9, v8, Landroid/widget/LinearLayout$LayoutParams;->leftMargin:I

    .line 1126
    invoke-virtual {v7, v8}, Landroid/widget/TextView;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 1127
    invoke-virtual {v7, v4}, Landroid/widget/TextView;->setClickable(Z)V

    .line 1128
    invoke-virtual {v7, v4}, Landroid/widget/TextView;->setFocusable(Z)V

    .line 1130
    new-instance v4, Lcom/zyro/browser/SafeLinkInputHelper$10;

    invoke-direct {v4, v0, v1, v2, v3}, Lcom/zyro/browser/SafeLinkInputHelper$10;-><init>(Landroid/app/Dialog;Landroid/app/Activity;ILjava/lang/String;)V

    invoke-virtual {v7, v4}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 1138
    invoke-virtual {v6, v7}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 1140
    invoke-virtual {v5, v6}, Landroid/widget/LinearLayout;->addView(Landroid/view/View;)V

    .line 1142
    invoke-virtual {v0, v5}, Landroid/app/Dialog;->setContentView(Landroid/view/View;)V

    .line 1144
    invoke-virtual {v0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v4

    if-eqz v4, :cond_190

    .line 1145
    invoke-virtual {v0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v4

    new-instance v5, Landroid/graphics/drawable/ColorDrawable;

    const/4 v6, 0x0

    invoke-direct {v5, v6}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    invoke-virtual {v4, v5}, Landroid/view/Window;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 1146
    invoke-virtual/range {p0 .. p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v4

    iget v4, v4, Landroid/util/DisplayMetrics;->widthPixels:I
    :try_end_17c
    .catchall {:try_start_10 .. :try_end_17c} :catchall_194

    int-to-double v4, v4

    const-wide v6, 0x3fec28f5c28f5c29L    # 0.88

    invoke-static {v4, v5}, Ljava/lang/Double;->isNaN(D)Z

    mul-double v4, v4, v6

    double-to-int v4, v4

    .line 1147
    :try_start_188
    invoke-virtual {v0}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v5

    const/4 v6, -0x2

    invoke-virtual {v5, v4, v6}, Landroid/view/Window;->setLayout(II)V

    .line 1150
    :cond_190
    invoke-virtual {v0}, Landroid/app/Dialog;->show()V
    :try_end_193
    .catchall {:try_start_188 .. :try_end_193} :catchall_194

    .line 1156
    goto :goto_19b

    .line 1152
    :catchall_194
    move-exception v0

    .line 1153
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 1155
    invoke-static {v1, v2, v3}, Lcom/zyro/browser/SafeLinkInputHelper;->showAdForStepOnActivity(Landroid/app/Activity;ILjava/lang/String;)V

    .line 1157
    :goto_19b
    return-void

    .line 1049
    :cond_19c
    :goto_19c
    return-void
.end method

.method private static startProgressOnActivity(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 11

    .line 1282
    :try_start_0
    instance-of v0, p0, Lcom/zyro/browser/MainActivity;

    if-eqz v0, :cond_c

    .line 1283
    move-object v0, p0

    check-cast v0, Lcom/zyro/browser/MainActivity;

    invoke-static {v0, p1, p2}, Lcom/zyro/browser/MainActivity;->access$runStepWithProgress(Lcom/zyro/browser/MainActivity;ILjava/lang/String;)V
    :try_end_a
    .catchall {:try_start_0 .. :try_end_a} :catchall_b

    .line 1284
    return-void

    .line 1286
    :catchall_b
    move-exception v0

    :cond_c
    nop

    .line 1289
    const/4 v0, 0x2

    const/4 v1, 0x0

    const/4 v2, 0x1

    :try_start_10
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v3

    const-string v4, "access$runStepWithProgress"

    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v5

    const/4 v6, 0x3

    new-array v7, v6, [Ljava/lang/Class;

    aput-object v5, v7, v1

    sget-object v5, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v5, v7, v2

    const-class v5, Ljava/lang/String;

    aput-object v5, v7, v0

    invoke-virtual {v3, v4, v7}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 1290
    invoke-virtual {v3, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1291
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    new-array v5, v6, [Ljava/lang/Object;

    aput-object p0, v5, v1

    aput-object v4, v5, v2

    aput-object p2, v5, v0

    const/4 v4, 0x0

    invoke-virtual {v3, v4, v5}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_3e
    .catchall {:try_start_10 .. :try_end_3e} :catchall_3f

    .line 1292
    return-void

    .line 1293
    :catchall_3f
    move-exception v3

    .line 1296
    :try_start_40
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v3

    const-string v4, "runStepWithProgress"

    new-array v5, v0, [Ljava/lang/Class;

    sget-object v6, Ljava/lang/Integer;->TYPE:Ljava/lang/Class;

    aput-object v6, v5, v1

    const-class v6, Ljava/lang/String;

    aput-object v6, v5, v2

    invoke-virtual {v3, v4, v5}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v3

    .line 1297
    invoke-virtual {v3, v2}, Ljava/lang/reflect/Method;->setAccessible(Z)V

    .line 1298
    invoke-static {p1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object p1

    new-array v0, v0, [Ljava/lang/Object;

    aput-object p1, v0, v1

    aput-object p2, v0, v2

    invoke-virtual {v3, p0, v0}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
    :try_end_64
    .catchall {:try_start_40 .. :try_end_64} :catchall_65

    .line 1301
    goto :goto_69

    .line 1299
    :catchall_65
    move-exception p0

    .line 1300
    invoke-virtual {p0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 1302
    :goto_69
    return-void
.end method
