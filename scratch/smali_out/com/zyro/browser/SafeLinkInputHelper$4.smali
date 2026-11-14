.class Lcom/zyro/browser/SafeLinkInputHelper$4;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


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

    .line 561
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$4;->val$startScanRunnable:Ljava/lang/Runnable;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 2

    .line 564
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$4;->val$startScanRunnable:Ljava/lang/Runnable;

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    .line 565
    return-void
.end method
