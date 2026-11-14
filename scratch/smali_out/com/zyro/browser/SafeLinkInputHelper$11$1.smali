.class Lcom/zyro/browser/SafeLinkInputHelper$11$1;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper$11;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/zyro/browser/SafeLinkInputHelper$11;


# direct methods
.method constructor <init>(Lcom/zyro/browser/SafeLinkInputHelper$11;)V
    .registers 2

    .line 1177
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$11$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$11;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 3

    .line 1180
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$11$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$11;

    iget-object v0, v0, Lcom/zyro/browser/SafeLinkInputHelper$11;->val$activity:Landroid/app/Activity;

    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$11$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$11;

    iget-object v1, v1, Lcom/zyro/browser/SafeLinkInputHelper$11;->val$targetUrl:Ljava/lang/String;

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smdispatchOpenDestination(Landroid/app/Activity;Ljava/lang/String;)V

    .line 1181
    return-void
.end method
