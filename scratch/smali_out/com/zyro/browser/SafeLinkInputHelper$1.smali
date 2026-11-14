.class Lcom/zyro/browser/SafeLinkInputHelper$1;
.super Ljava/lang/Object;
.source "SafeLinkInputHelper.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/zyro/browser/SafeLinkInputHelper;->prefetchToken(Landroid/app/Activity;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$alias:Ljava/lang/String;

.field final synthetic val$uuid:Ljava/lang/String;


# direct methods
.method constructor <init>(Ljava/lang/String;Ljava/lang/String;)V
    .registers 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 293
    iput-object p1, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    iput-object p2, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$uuid:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .registers 12

    .line 296
    const-string v0, "url"

    const-string v1, "destination"

    const-string v2, "LinkScanner"

    const-string v3, "UTF-8"

    .line 298
    const/4 v4, 0x0

    const/4 v5, 0x0

    :try_start_a
    iget-object v6, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    invoke-static {v6, v3}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    iget-object v7, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    .line 299
    invoke-static {v7, v3}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    iget-object v8, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$uuid:Ljava/lang/String;

    .line 300
    invoke-static {v8, v3}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "?alias="

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v9

    invoke-virtual {v9, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v9, "&url="

    invoke-virtual {v6, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    const-string v7, "&uuid="

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    .line 301
    new-instance v7, Ljava/net/URL;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "https://zyrobrowser.com/api.php"

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v8

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v7, v6}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 302
    invoke-virtual {v7}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object v6

    check-cast v6, Ljava/net/HttpURLConnection;
    :try_end_61
    .catchall {:try_start_a .. :try_end_61} :catchall_1ab

    .line 303
    :try_start_61
    const-string v5, "GET"

    invoke-virtual {v6, v5}, Ljava/net/HttpURLConnection;->setRequestMethod(Ljava/lang/String;)V

    .line 304
    const-string v5, "X-App-Key"

    const-string v7, "b558d61f7b3177576bc28723697897179d2d20fd88d45d65fc0fa55417dd730f"

    invoke-virtual {v6, v5, v7}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 305
    const-string v5, "Accept"

    const-string v7, "application/json"

    invoke-virtual {v6, v5, v7}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 306
    const-string v5, "User-Agent"

    const-string v7, "Mozilla/5.0 ZyroBrowser/1.0"

    invoke-virtual {v6, v5, v7}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 307
    const/16 v5, 0x3a98

    invoke-virtual {v6, v5}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 308
    invoke-virtual {v6, v5}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 310
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v5

    .line 311
    const/16 v7, 0xc8

    if-lt v5, v7, :cond_94

    const/16 v7, 0x12c

    if-ge v5, v7, :cond_94

    .line 312
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v5

    goto :goto_98

    :cond_94
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->getErrorStream()Ljava/io/InputStream;

    move-result-object v5

    .line 314
    :goto_98
    new-instance v7, Ljava/io/BufferedReader;

    new-instance v8, Ljava/io/InputStreamReader;

    invoke-direct {v8, v5, v3}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;Ljava/lang/String;)V

    invoke-direct {v7, v8}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V

    .line 315
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    .line 317
    :goto_a7
    invoke-virtual {v7}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;

    move-result-object v8

    if-eqz v8, :cond_b1

    .line 318
    invoke-virtual {v5, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_a7

    .line 320
    :cond_b1
    invoke-virtual {v7}, Ljava/io/BufferedReader;->close()V

    .line 322
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    .line 323
    new-instance v7, Ljava/lang/StringBuilder;

    invoke-direct {v7}, Ljava/lang/StringBuilder;-><init>()V

    const-string v8, "Link Scanner API Response: "

    invoke-virtual {v7, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v7

    invoke-virtual {v7}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-static {v2, v7}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 325
    new-instance v7, Lorg/json/JSONObject;

    invoke-direct {v7, v5}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    .line 326
    const-string v5, "success"

    invoke-virtual {v7, v5}, Lorg/json/JSONObject;->optBoolean(Ljava/lang/String;)Z

    move-result v5

    if-eqz v5, :cond_17c

    .line 327
    const-string v5, "token"

    const-string v8, ""

    invoke-virtual {v7, v5, v8}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 328
    nop

    .line 329
    invoke-virtual {v7, v1}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v8

    if-eqz v8, :cond_f5

    invoke-virtual {v7, v1}, Lorg/json/JSONObject;->isNull(Ljava/lang/String;)Z

    move-result v8

    if-nez v8, :cond_f5

    .line 330
    invoke-virtual {v7, v1}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_154

    .line 331
    :cond_f5
    invoke-virtual {v7, v0}, Lorg/json/JSONObject;->has(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_106

    invoke-virtual {v7, v0}, Lorg/json/JSONObject;->isNull(Ljava/lang/String;)Z

    move-result v1

    if-nez v1, :cond_106

    .line 332
    invoke-virtual {v7, v0}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    goto :goto_154

    .line 333
    :cond_106
    invoke-virtual {v5}, Ljava/lang/String;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_152

    .line 334
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    const-string v1, "?"

    invoke-virtual {v0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_134

    .line 335
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    invoke-static {v5, v3}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, "&token="

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_154

    .line 337
    :cond_134
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    invoke-static {v5, v3}, Ljava/net/URLEncoder;->encode(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v3, "?token="

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    goto :goto_154

    .line 340
    :cond_152
    iget-object v0, p0, Lcom/zyro/browser/SafeLinkInputHelper$1;->val$alias:Ljava/lang/String;

    .line 342
    :goto_154
    sput-object v5, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedToken:Ljava/lang/String;

    .line 343
    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchedDestinationUrl:Ljava/lang/String;

    .line 344
    const/4 v1, 0x1

    sput-boolean v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    .line 345
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Link Scanner Token obtained: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v3, " -> "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 346
    goto :goto_1a0

    .line 347
    :cond_17c
    const-string v0, "error"

    const-string v1, "Gate verification error"

    invoke-virtual {v7, v0, v1}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchError:Ljava/lang/String;

    .line 348
    sput-boolean v4, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    .line 349
    sget-object v0, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchError:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Link Scanner API returned non-success: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1a0
    .catchall {:try_start_61 .. :try_end_1a0} :catchall_1a8

    .line 357
    :goto_1a0
    if-eqz v6, :cond_1d8

    .line 358
    :try_start_1a2
    invoke-virtual {v6}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_1a5
    .catchall {:try_start_1a2 .. :try_end_1a5} :catchall_1a6

    goto :goto_1d8

    :catchall_1a6
    move-exception v0

    goto :goto_1d8

    .line 351
    :catchall_1a8
    move-exception v0

    move-object v5, v6

    goto :goto_1ac

    :catchall_1ab
    move-exception v0

    .line 352
    :goto_1ac
    :try_start_1ac
    invoke-virtual {v0}, Ljava/lang/Throwable;->printStackTrace()V

    .line 353
    invoke-virtual {v0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object v1

    sput-object v1, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchError:Ljava/lang/String;

    .line 354
    sput-boolean v4, Lcom/zyro/browser/SafeLinkInputHelper;->sPrefetchSuccess:Z

    .line 355
    invoke-virtual {v0}, Ljava/lang/Throwable;->getMessage()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "Link Scanner API fetch exception: "

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_1d1
    .catchall {:try_start_1ac .. :try_end_1d1} :catchall_1dc

    .line 357
    if-eqz v5, :cond_1d8

    .line 358
    :try_start_1d3
    invoke-virtual {v5}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_1d6
    .catchall {:try_start_1d3 .. :try_end_1d6} :catchall_1d7

    goto :goto_1d8

    :catchall_1d7
    move-exception v0

    .line 360
    :cond_1d8
    :goto_1d8
    sput-boolean v4, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    .line 361
    nop

    .line 362
    return-void

    .line 357
    :catchall_1dc
    move-exception v0

    if-eqz v5, :cond_1e4

    .line 358
    :try_start_1df
    invoke-virtual {v5}, Ljava/net/HttpURLConnection;->disconnect()V
    :try_end_1e2
    .catchall {:try_start_1df .. :try_end_1e2} :catchall_1e3

    goto :goto_1e4

    :catchall_1e3
    move-exception v1

    .line 360
    :cond_1e4
    :goto_1e4
    sput-boolean v4, Lcom/zyro/browser/SafeLinkInputHelper;->sIsPrefetching:Z

    .line 361
    goto :goto_1e8

    :goto_1e7
    throw v0

    :goto_1e8
    goto :goto_1e7
.end method
