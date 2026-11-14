package com.zyro.browser.security;

import android.content.Context;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: AdBlockDetector.kt */
@DebugMetadata(c = "com.zyro.browser.security.AdBlockDetector$detect$2", f = "AdBlockDetector.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
/* loaded from: classes9.dex */
final class AdBlockDetector$detect$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super AdBlockDetectionResult>, Object> {
    final /* synthetic */ Context $context;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AdBlockDetector$detect$2(Context context, Continuation<? super AdBlockDetector$detect$2> continuation) {
        super(2, continuation);
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new AdBlockDetector$detect$2(this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super AdBlockDetectionResult> continuation) {
        return ((AdBlockDetector$detect$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AdBlockDetectionResult privateDnsCheck;
        AdBlockDetectionResult probeCheck;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                privateDnsCheck = AdBlockDetector.INSTANCE.checkPrivateDns(this.$context);
                if (!privateDnsCheck.isBlocked()) {
                    probeCheck = AdBlockDetector.INSTANCE.checkDnsProbe();
                    if (probeCheck.isBlocked()) {
                        return probeCheck;
                    }
                    return new AdBlockDetectionResult(false, null, null, 6, null);
                }
                return privateDnsCheck;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
