.class Lcom/zyro/browser/SafeLinkInputHelper$3;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->setupSafeLinkMode(Landroid/app/Activity;Landroid/view/View;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$btn:Landroid/widget/TextView;

.field final synthetic val$et:Landroid/widget/EditText;

.field final synthetic val$ivShield:Landroid/widget/ImageView;

.field final synthetic val$pb:Landroid/widget/ProgressBar;

.field final synthetic val$root:Landroid/view/View;

.field final synthetic val$stepsContainer:Landroid/widget/LinearLayout;

.field final synthetic val$subtitle:Landroid/widget/TextView;

.field final synthetic val$tvHeadline:Landroid/widget/TextView;

.field final synthetic val$tvPing:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Landroid/widget/EditText;Landroid/app/Activity;Landroid/view/View;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/TextView;Landroid/widget/ProgressBar;Landroid/widget/TextView;Landroid/widget/LinearLayout;Landroid/widget/ImageView;)V
    .registers 11
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 476
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    iput-object p3, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$root:Landroid/view/View;

    iput-object p4, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvPing:Landroid/widget/TextView;

    iput-object p5, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$subtitle:Landroid/widget/TextView;

    iput-object p6, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvHeadline:Landroid/widget/TextView;

    iput-object p7, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$pb:Landroid/widget/ProgressBar;

    iput-object p8, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$btn:Landroid/widget/TextView;

    iput-object p9, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$stepsContainer:Landroid/widget/LinearLayout;

    iput-object p10, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$ivShield:Landroid/widget/ImageView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 7

    .line 480
    const-string v0, "https://"

    :try_start_2
    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    if-eqz v1, :cond_19

    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    goto :goto_1b

    :cond_19
    const-string v1, ""

    .line 481
    :goto_1b
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2
    :try_end_1f
    .catchall {:try_start_2 .. :try_end_1f} :catchall_163

    const-string v3, "input_method"

    const/4 v4, 0x0

    if-eqz v2, :cond_45

    .line 482
    :try_start_24
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    const-string v1, "Please enter a URL to verify"

    invoke-static {v0, v1, v4}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 483
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->requestFocus()Z

    .line 484
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    invoke-virtual {v0, v3}, Landroid/app/Activity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/inputmethod/InputMethodManager;

    .line 485
    if-eqz v0, :cond_44

    .line 486
    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    const/4 v2, 0x1

    invoke-virtual {v0, v1, v2}, Landroid/view/inputmethod/InputMethodManager;->showSoftInput(Landroid/view/View;I)Z

    .line 488
    :cond_44
    return-void

    .line 491
    :cond_45
    const-string v2, "http://"

    invoke-virtual {v1, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_69

    invoke-virtual {v1, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-nez v2, :cond_69

    .line 492
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    .line 493
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v1}, Landroid/widget/EditText;->setText(Ljava/lang/CharSequence;)V

    .line 497
    :cond_69
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    invoke-virtual {v0, v3}, Landroid/app/Activity;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/inputmethod/InputMethodManager;

    .line 498
    if-eqz v0, :cond_7c

    .line 499
    iget-object v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getWindowToken()Landroid/os/IBinder;

    move-result-object v2

    invoke-virtual {v0, v2, v4}, Landroid/view/inputmethod/InputMethodManager;->hideSoftInputFromWindow(Landroid/os/IBinder;I)Z

    .line 503
    :cond_7c
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->clearFocus()V

    .line 504
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setFocusable(Z)V

    .line 505
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setFocusableInTouchMode(Z)V

    .line 506
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setClickable(Z)V

    .line 507
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setLongClickable(Z)V

    .line 508
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0, v4}, Landroid/widget/EditText;->setCursorVisible(Z)V

    .line 509
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setKeyListener(Landroid/text/method/KeyListener;)V

    .line 510
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    sget-object v2, Landroid/text/TextUtils$TruncateAt;->END:Landroid/text/TextUtils$TruncateAt;

    invoke-virtual {v0, v2}, Landroid/widget/EditText;->setEllipsize(Landroid/text/TextUtils$TruncateAt;)V

    .line 512
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v0}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    instance-of v0, v0, Landroid/view/View;

    if-eqz v0, :cond_fc

    .line 513
    new-instance v0, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {v0}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 514
    invoke-virtual {v0, v4}, Landroid/graphics/drawable/GradientDrawable;->setShape(I)V

    .line 515
    sget-boolean v2, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    if-eqz v2, :cond_c1

    const v2, -0xf0e8d6

    goto :goto_c4

    :cond_c1
    const v2, -0x70504

    :goto_c4
    invoke-virtual {v0, v2}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 516
    iget-object v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$root:Landroid/view/View;

    invoke-virtual {v2}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v2

    const/high16 v3, 0x41400000    # 12.0f

    invoke-static {v2, v3}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v0, v2}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 517
    sget-boolean v2, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    if-eqz v2, :cond_df

    const v2, -0xe1d6c5

    goto :goto_e2

    :cond_df
    const v2, -0x1d1710

    .line 518
    :goto_e2
    iget-object v3, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$root:Landroid/view/View;

    invoke-virtual {v3}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v3

    const/high16 v5, 0x3f800000    # 1.0f

    invoke-static {v3, v5}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v3

    invoke-virtual {v0, v3, v2}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 519
    iget-object v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$et:Landroid/widget/EditText;

    invoke-virtual {v2}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object v2

    check-cast v2, Landroid/view/View;

    invoke-virtual {v2, v0}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 522
    :cond_fc
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvPing:Landroid/widget/TextView;

    if-eqz v0, :cond_107

    .line 523
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvPing:Landroid/widget/TextView;

    const-string v2, "\u25cf SCANNING TARGET"

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 525
    :cond_107
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$subtitle:Landroid/widget/TextView;

    if-eqz v0, :cond_112

    .line 526
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$subtitle:Landroid/widget/TextView;

    const-string v2, "Host: Validating \u2022 TLS Handshake"

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 528
    :cond_112
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvHeadline:Landroid/widget/TextView;

    if-eqz v0, :cond_11d

    .line 529
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$tvHeadline:Landroid/widget/TextView;

    const-string v2, "Link Scanner"

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 531
    :cond_11d
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$pb:Landroid/widget/ProgressBar;

    if-eqz v0, :cond_126

    .line 532
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$pb:Landroid/widget/ProgressBar;

    invoke-virtual {v0, v4}, Landroid/widget/ProgressBar;->setVisibility(I)V

    .line 535
    :cond_126
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$btn:Landroid/widget/TextView;

    invoke-virtual {v0, v4}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 536
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$btn:Landroid/widget/TextView;

    const v2, 0x3f19999a    # 0.6f

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setAlpha(F)V

    .line 537
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$btn:Landroid/widget/TextView;

    const-string v2, "Validating..."

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 540
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$stepsContainer:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_14f

    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$stepsContainer:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v0

    if-lez v0, :cond_14f

    .line 541
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$stepsContainer:Landroid/widget/LinearLayout;

    invoke-virtual {v0, v4}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    .line 542
    invoke-static {v0, v4}, Lcom/zyro/browser/SafeLinkAnimationHelper;->setStepActive(Landroid/view/View;I)V

    .line 546
    :cond_14f
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$ivShield:Landroid/widget/ImageView;

    if-eqz v0, :cond_158

    .line 547
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$ivShield:Landroid/widget/ImageView;

    invoke-static {v0}, Lcom/zyro/browser/SafeLinkAnimationHelper;->startShieldPulse(Landroid/view/View;)V

    .line 551
    :cond_158
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->prefetchToken(Landroid/app/Activity;Ljava/lang/String;)V

    .line 554
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$3;->val$activity:Landroid/app/Activity;

    invoke-static {v0, v4, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smstartProgressOnActivity(Landroid/app/Activity;ILjava/lang/String;)V
    :try_end_162
    .catchall {:try_start_24 .. :try_end_162} :catchall_163

    .line 557
    goto :goto_167

    .line 555
    :catchall_163
    move-exception v0

    .line 556
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 558
    :goto_167
    return-void
.end method
