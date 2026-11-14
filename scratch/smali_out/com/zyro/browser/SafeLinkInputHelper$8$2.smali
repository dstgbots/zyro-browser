.class Lcom/zyro/browser/SafeLinkInputHelper$8$2;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper$8;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

.field final synthetic val$nextStepName:Ljava/lang/String;


# direct methods
.method constructor <init>(Lcom/zyro/browser/SafeLinkInputHelper$8;Ljava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1019
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->val$nextStepName:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 5

    .line 1022
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iget-object p1, p1, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iget v0, v0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$stepIndex:I

    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->val$nextStepName:Ljava/lang/String;

    iget-object v2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$2;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iget-object v2, v2, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$targetUrl:Ljava/lang/String;

    invoke-static {p1, v0, v1, v2}, Lcom/zyro/browser/SafeLinkInputHelper;->showWatchVideoDialog(Landroid/app/Activity;ILjava/lang/String;Ljava/lang/String;)V

    .line 1023
    return-void
.end method
