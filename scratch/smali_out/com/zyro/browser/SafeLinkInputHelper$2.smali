.class Lcom/zyro/browser/SafeLinkInputHelper$2;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Landroid/view/View$OnFocusChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->setupSafeLinkMode(Landroid/app/Activity;Landroid/view/View;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$et:Landroid/widget/EditText;

.field final synthetic val$root:Landroid/view/View;


# direct methods
.method constructor <init>(Landroid/widget/EditText;Landroid/view/View;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 430
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$et:Landroid/widget/EditText;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$root:Landroid/view/View;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onFocusChange(Landroid/view/View;Z)V
    .registers 5

    .line 434
    :try_start_0
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$et:Landroid/widget/EditText;

    invoke-virtual {p1}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object p1

    instance-of p1, p1, Landroid/view/View;

    if-eqz p1, :cond_6b

    .line 435
    new-instance p1, Landroid/graphics/drawable/GradientDrawable;

    invoke-direct {p1}, Landroid/graphics/drawable/GradientDrawable;-><init>()V

    .line 436
    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/graphics/drawable/GradientDrawable;->setShape(I)V

    .line 437
    sget-boolean v0, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    if-eqz v0, :cond_1b

    const v0, -0xf0e8d6

    goto :goto_1e

    :cond_1b
    const v0, -0x70504

    :goto_1e
    invoke-virtual {p1, v0}, Landroid/graphics/drawable/GradientDrawable;->setColor(I)V

    .line 438
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$root:Landroid/view/View;

    invoke-virtual {v0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    const/high16 v1, 0x41400000    # 12.0f

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result v0

    int-to-float v0, v0

    invoke-virtual {p1, v0}, Landroid/graphics/drawable/GradientDrawable;->setCornerRadius(F)V

    .line 439
    if-eqz p2, :cond_3f

    .line 440
    sget-boolean v0, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    if-eqz v0, :cond_3b

    const v0, -0xc47d0a

    goto :goto_4a

    :cond_3b
    const v0, -0xda9c15

    goto :goto_4a

    .line 441
    :cond_3f
    sget-boolean v0, Lcom/zyro/browser/SafeLinkAnimationHelper;->sIsDarkTheme:Z

    if-eqz v0, :cond_47

    const v0, -0xe1d6c5

    goto :goto_4a

    :cond_47
    const v0, -0x1d1710

    .line 442
    :goto_4a
    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$root:Landroid/view/View;

    invoke-virtual {v1}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v1

    if-eqz p2, :cond_55

    const/high16 p2, 0x40000000    # 2.0f

    goto :goto_57

    :cond_55
    const/high16 p2, 0x3f800000    # 1.0f

    :goto_57
    invoke-static {v1, p2}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdp(Landroid/content/Context;F)I

    move-result p2

    invoke-virtual {p1, p2, v0}, Landroid/graphics/drawable/GradientDrawable;->setStroke(II)V

    .line 443
    iget-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$2;->val$et:Landroid/widget/EditText;

    invoke-virtual {p2}, Landroid/widget/EditText;->getParent()Landroid/view/ViewParent;

    move-result-object p2

    check-cast p2, Landroid/view/View;

    invoke-virtual {p2, p1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V
    :try_end_69
    .catchall {:try_start_0 .. :try_end_69} :catchall_6a

    goto :goto_6b

    .line 445
    :catchall_6a
    move-exception p1

    :cond_6b
    :goto_6b
    nop

    .line 446
    return-void
.end method
