.class Lcom/zyro/browser/SafeLinkInputHelper$7;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->onStepStarting(Landroid/app/Activity;I)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$activity:Landroid/app/Activity;


# direct methods
.method constructor <init>(Landroid/app/Activity;)V
    .registers 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 917
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$7;->val$activity:Landroid/app/Activity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 3

    .line 921
    :try_start_0
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$7;->val$activity:Landroid/app/Activity;

    const v1, 0x1020002

    invoke-virtual {v0, v1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 922
    const-string v1, "btn_safelink_continue"

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smgetId(Landroid/view/View;Ljava/lang/String;)I

    move-result v1

    .line 923
    if-eqz v0, :cond_16

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    goto :goto_17

    :cond_16
    const/4 v0, 0x0

    .line 924
    :goto_17
    instance-of v1, v0, Landroid/widget/TextView;

    if-eqz v1, :cond_31

    .line 925
    check-cast v0, Landroid/widget/TextView;

    .line 926
    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setEnabled(Z)V

    .line 927
    const v1, 0x3f19999a    # 0.6f

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setAlpha(F)V

    .line 928
    const-string v1, "Validating..."

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 929
    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$7;->val$activity:Landroid/app/Activity;

    invoke-static {v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smapplyOrangeButtonStyle(Landroid/widget/TextView;Landroid/content/Context;)V
    :try_end_31
    .catchall {:try_start_0 .. :try_end_31} :catchall_32

    .line 933
    :cond_31
    goto :goto_36

    .line 931
    :catchall_32
    move-exception v0

    .line 932
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 934
    :goto_36
    return-void
.end method
