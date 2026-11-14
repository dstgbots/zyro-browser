.class Lcom/zyro/browser/SafeLinkInputHelper$11;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->handleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$targetUrl:Ljava/lang/String;


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1170
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$11;->val$activity:Landroid/app/Activity;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$11;->val$targetUrl:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 8

    .line 1173
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    .line 1174
    :goto_4
    sget-boolean v2, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    if-eqz v2, :cond_1b

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v2

    sub-long/2addr v2, v0

    const-wide/16 v4, 0xfa0

    cmp-long v6, v2, v4

    if-gez v6, :cond_1b

    .line 1175
    const-wide/16 v2, 0x64

    :try_start_15
    invoke-static {v2, v3}, Ljava/lang/Thread;->sleep(J)V
    :try_end_18
    .catch Ljava/lang/InterruptedException; {:try_start_15 .. :try_end_18} :catch_19

    goto :goto_4

    :catch_19
    move-exception v2

    goto :goto_4

    .line 1177
    :cond_1b
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$11;->val$activity:Landroid/app/Activity;

    new-instance v1, Lcom/zyro/browser/SafeLinkInputHelper$11$1;

    invoke-direct {v1, p0}, Lcom/zyro/browser/SafeLinkInputHelper$11$1;-><init>(Lcom/zyro/browser/SafeLinkInputHelper$11;)V

    invoke-virtual {v0, v1}, Landroid/app/Activity;->runOnUiThread(Ljava/lang/Runnable;)V

    .line 1183
    return-void
.end method
