.class Lcom/zyro/browser/SafeLinkInputHelper$10;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->showWatchVideoDialog(Landroid/app/Activity;ILjava/lang/String;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;

.field final synthetic val$currentStepIndex:I

.field final synthetic val$dialog:Landroid/app/Dialog;

.field final synthetic val$targetUrl:Ljava/lang/String;


# direct methods
.method constructor <init>(Landroid/app/Dialog;Landroid/app/Activity;ILjava/lang/String;)V
    .registers 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1130
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$dialog:Landroid/app/Dialog;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$activity:Landroid/app/Activity;

    iput p3, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$currentStepIndex:I

    iput-object p4, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$targetUrl:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 4

    .line 1133
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$dialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->dismiss()V

    .line 1135
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$activity:Landroid/app/Activity;

    iget v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$currentStepIndex:I

    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$10;->val$targetUrl:Ljava/lang/String;

    invoke-static {p1, v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smshowAdForStepOnActivity(Landroid/app/Activity;ILjava/lang/String;)V

    .line 1136
    return-void
.end method
