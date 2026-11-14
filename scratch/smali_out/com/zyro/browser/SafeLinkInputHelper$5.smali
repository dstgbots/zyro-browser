.class Lcom/zyro/browser/SafeLinkInputHelper$5;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Landroid/widget/TextView$OnEditorActionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->setupSafeLinkMode(Landroid/app/Activity;Landroid/view/View;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$startScanRunnable:Ljava/lang/Runnable;


# direct methods
.method constructor <init>(Ljava/lang/Runnable;)V
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 568
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$5;->val$startScanRunnable:Ljava/lang/Runnable;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onEditorAction(Landroid/widget/TextView;ILandroid/view/KeyEvent;)Z
    .registers 4

    .line 571
    const/4 p1, 0x2

    if-eq p2, p1, :cond_23

    const/4 p1, 0x3

    if-eq p2, p1, :cond_23

    const/4 p1, 0x4

    if-eq p2, p1, :cond_23

    const/4 p1, 0x6

    if-eq p2, p1, :cond_23

    if-eqz p2, :cond_23

    if-eqz p3, :cond_21

    .line 576
    invoke-virtual {p3}, Landroid/view/KeyEvent;->getKeyCode()I

    move-result p1

    const/16 p2, 0x42

    if-eq p1, p2, :cond_23

    invoke-virtual {p3}, Landroid/view/KeyEvent;->getKeyCode()I

    move-result p1

    const/16 p2, 0xa0

    if-ne p1, p2, :cond_21

    goto :goto_23

    .line 580
    :cond_21
    const/4 p1, 0x0

    return p1

    .line 577
    :cond_23
    :goto_23
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$5;->val$startScanRunnable:Ljava/lang/Runnable;

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    .line 578
    const/4 p1, 0x1

    return p1
.end method
