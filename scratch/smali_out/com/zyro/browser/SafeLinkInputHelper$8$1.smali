.class Lcom/zyro/browser/SafeLinkInputHelper$8$1;
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

.field final synthetic val$btn:Landroid/widget/TextView;


# direct methods
.method constructor <init>(Lcom/zyro/browser/SafeLinkInputHelper$8;Landroid/widget/TextView;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 977
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$1;->val$btn:Landroid/widget/TextView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .registers 4

    .line 980
    iget-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iget-object p1, p1, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$activity:Landroid/app/Activity;

    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$1;->this$0:Lcom/zyro/browser/SafeLinkInputHelper$8;

    iget-object v0, v0, Lcom/zyro/browser/SafeLinkInputHelper$8;->val$targetUrl:Ljava/lang/String;

    iget-object v1, p0, Lcom/zyro/browser/SafeLinkInputHelper$8$1;->val$btn:Landroid/widget/TextView;

    invoke-static {p1, v0, v1}, Lcom/zyro/browser/SafeLinkInputHelper;->-$$Nest$smhandleAccessDestination(Landroid/app/Activity;Ljava/lang/String;Landroid/widget/TextView;)V

    .line 981
    return-void
.end method
