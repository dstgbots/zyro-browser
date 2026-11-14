.class Lcom/zyro/browser/SafeLinkInputHelper$8;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->onStepProgressFinished(Landroid/app/Activity;ILjava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$stepIndex:I

.field final synthetic val$targetUrl:Ljava/lang/String;


# direct methods
.method constructor <init>(Landroid/app/Activity;ILjava/lang/String;)V
    .registers 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 940
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    iput p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$stepIndex:I

    iput-object p3, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$targetUrl:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 8

    .line 944
    :try_start_0
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    const v1, 0x1020002

    invoke-virtual {v0, v1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 945
    if-nez v0, :cond_c

    return-void

    .line 948
    :cond_c
    const-string v1, "safelink_steps_container"

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 949
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/LinearLayout;

    .line 950
    if-eqz v1, :cond_2b

    iget v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$stepIndex:I

    invoke-virtual {v1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v3

    if-ge v2, v3, :cond_2b

    .line 951
    iget v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$stepIndex:I

    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v2

    .line 952
    invoke-static {v2}, Lcom/zyro/browser/SafeLinkAnimationHelper;->setStepPassed(Landroid/view/View;)V

    .line 955
    :cond_2b
    nop

    .line 956
    iget v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$stepIndex:I

    const/4 v3, 0x1

    add-int/2addr v2, v3

    .line 958
    const-string v4, "btn_safelink_continue"

    invoke-static {v0, v4}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v4

    .line 959
    invoke-virtual {v0, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v4

    check-cast v4, Landroid/widget/TextView;

    .line 961
    const/high16 v5, 0x3f800000    # 1.0f

    const/4 v6, 0x4

    if-lt v2, v6, :cond_b2

    .line 963
    if-eqz v4, :cond_7a

    .line 964
    invoke-virtual {v4, v3}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 965
    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setAlpha(F)V

    .line 966
    const-string v1, "Access Verified Destination \u2192"

    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 969
    new-instance v1, Landroid/graphics/drawable/GradientDrawable;

    sget-object v2, Landroid/graphics/drawable/GradientDrawable$Orientation;->TL_BR:Landroid/graphics/drawable/GradientDrawable$Orientation;

    const v3, -0xfb87a9

    const v5, -0xfa6997

    filled-new-array {v5, v3}, [I

    move-result-object v3

    invoke-direct {v1, v2, v3}, Landroid/graphics/drawable/GradientDrawable;-><init>(Landroid/graphics/drawable/GradientDrawable$Orientation;[I)V

    .line 973
    iget-object v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    const/high16 v3, 0x41600000    # 14.0f

    invoke-static {v2, v3}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v2

    int-to-float v2, v2

    invoke-virtual {v1, v2}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 974
    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setBackground(Landroid/graphics/drawable/Drawable;)V

    .line 975
    const/4 v1, -0x1

    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setTextColor(I)V

    .line 977
    new-instance v1, Lcom/zyro/browser/SafeLinkInputHelper$8$1;

    invoke-direct {v1, p0, v4}, Lcom/zyro/browser/SafeLinkInputHelper$8$1;-><init>(Lcom/zyro/browser/SafeLinkInputHelper$8;Landroid/widget/TextView;)V

    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 985
    :cond_7a
    const-string v1, "tv_safelink_headline"

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 986
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 987
    if-eqz v1, :cond_8d

    .line 988
    const-string v2, "Safety Verified \u2022 Target Clean"

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 991
    :cond_8d
    const-string v1, "tv_threat_level"

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 992
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 993
    if-eqz v1, :cond_a0

    .line 994
    const-string v2, "\u25cf 100% SECURE & VERIFIED"

    invoke-virtual {v1, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 997
    :cond_a0
    const-string v1, "pb_safelink_global"

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 998
    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 999
    if-eqz v0, :cond_b1

    .line 1000
    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 1003
    :cond_b1
    goto :goto_f0

    .line 1005
    :cond_b2
    invoke-static {v2}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetStepName(I)Ljava/lang/String;

    move-result-object v0

    .line 1008
    if-eqz v1, :cond_c5

    invoke-virtual {v1}, Landroid/widget/LinearLayout;->getChildCount()I

    move-result v6

    if-ge v2, v6, :cond_c5

    .line 1009
    invoke-virtual {v1, v2}, Landroid/widget/LinearLayout;->getChildAt(I)Landroid/view/View;

    move-result-object v1

    .line 1010
    invoke-static {v1, v2}, Lcom/zyro/browser/SafeLinkAnimationHelper;->setStepActive(Landroid/view/View;I)V

    .line 1013
    :cond_c5
    if-eqz v4, :cond_f0

    .line 1014
    invoke-virtual {v4, v3}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 1015
    invoke-virtual {v4, v5}, Landroid/widget/TextView;->setAlpha(F)V

    .line 1016
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "Start: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 1017
    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    invoke-static {v4, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smapplyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V

    .line 1019
    new-instance v1, Lcom/zyro/browser/SafeLinkInputHelper$8$2;

    invoke-direct {v1, p0, v0}, Lcom/zyro/browser/SafeLinkInputHelper$8$2;-><init>(Lcom/zyro/browser/SafeLinkInputHelper$8;Ljava/lang/String;)V

    invoke-virtual {v4, v1}, Landroid/widget/TextView;->setOnClickListener(Landroid/view/View$OnClickListener;)V
    :try_end_f0
    .catchall {:try_start_0 .. :try_end_f0} :catchall_f1

    .line 1030
    :cond_f0
    :goto_f0
    goto :goto_f5

    .line 1028
    :catchall_f1
    move-exception v0

    .line 1029
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 1031
    :goto_f5
    return-void
.end method
