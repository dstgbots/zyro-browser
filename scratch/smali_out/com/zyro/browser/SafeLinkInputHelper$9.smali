.class Lcom/zyro/browser/SafeLinkInputHelper$9;
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
.field final synthetic val$dialog:Landroid/app/Dialog;


# direct methods
.method constructor <init>(Landroid/app/Dialog;)V
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 1107
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$9;->val$dialog:Landroid/app/Dialog;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 2

    .line 1110
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$9;->val$dialog:Landroid/app/Dialog;

    invoke-virtual {p1}, Landroid/app/Dialog;->dismiss()V

    .line 1111
    return-void
.end method
