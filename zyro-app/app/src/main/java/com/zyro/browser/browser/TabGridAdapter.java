package com.zyro.browser.browser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.zyro.browser.R;
import com.zyro.browser.browser.TabGridAdapter;
import com.zyro.browser.databinding.ItemBrowserTabBinding;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: TabGridAdapter.kt */
/* loaded from: classes5.dex */
public final class TabGridAdapter extends RecyclerView.Adapter<TabViewHolder> {
    private final Function0<Integer> getCurrentIndex;
    private final Function1<Integer, Unit> onTabClick;
    private final Function1<Integer, Unit> onTabClose;
    private final List<BrowserTab> tabs;

    /* JADX WARN: Multi-variable type inference failed */
    public TabGridAdapter(List<BrowserTab> tabs, Function0<Integer> getCurrentIndex, Function1<? super Integer, Unit> onTabClick, Function1<? super Integer, Unit> onTabClose) {
        Intrinsics.checkNotNullParameter(tabs, "tabs");
        Intrinsics.checkNotNullParameter(getCurrentIndex, "getCurrentIndex");
        Intrinsics.checkNotNullParameter(onTabClick, "onTabClick");
        Intrinsics.checkNotNullParameter(onTabClose, "onTabClose");
        this.tabs = tabs;
        this.getCurrentIndex = getCurrentIndex;
        this.onTabClick = onTabClick;
        this.onTabClose = onTabClose;
    }

    /* compiled from: TabGridAdapter.kt */
    public final class TabViewHolder extends RecyclerView.ViewHolder {
        private final ItemBrowserTabBinding binding;
        final /* synthetic */ TabGridAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TabViewHolder(TabGridAdapter this$0, ItemBrowserTabBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = this$0;
            this.binding = binding;
        }

        public final ItemBrowserTabBinding getBinding() {
            return this.binding;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemBrowserTabBinding binding = ItemBrowserTabBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(binding, "inflate(LayoutInflater.f….context), parent, false)");
        return new TabViewHolder(this, binding);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final TabViewHolder holder, int position) {
        String displayTitle;
        Intrinsics.checkNotNullParameter(holder, "holder");
        BrowserTab tab = this.tabs.get(position);
        Context context = holder.itemView.getContext();
        boolean isActive = position == this.getCurrentIndex.invoke().intValue();
        float density = context.getResources().getDisplayMetrics().density;
        ItemBrowserTabBinding $this$onBindViewHolder_u24lambda_u242 = holder.getBinding();
        if (isActive) {
            $this$onBindViewHolder_u24lambda_u242.cardTab.setStrokeWidth((int) (2.5f * density));
            $this$onBindViewHolder_u24lambda_u242.cardTab.setStrokeColor(ContextCompat.getColor(context, R.color.cyber_cyan));
            $this$onBindViewHolder_u24lambda_u242.tvActiveTabTag.setVisibility(0);
            $this$onBindViewHolder_u24lambda_u242.layoutTabHeader.setBackgroundColor(ContextCompat.getColor(context, R.color.cyber_card_bg));
        } else {
            $this$onBindViewHolder_u24lambda_u242.cardTab.setStrokeWidth((int) (1.0f * density));
            $this$onBindViewHolder_u24lambda_u242.cardTab.setStrokeColor(ContextCompat.getColor(context, R.color.cyber_card_border));
            $this$onBindViewHolder_u24lambda_u242.tvActiveTabTag.setVisibility(8);
            $this$onBindViewHolder_u24lambda_u242.layoutTabHeader.setBackgroundColor(-15000287);
        }
        if ((!StringsKt.isBlank(tab.getTitle())) && !Intrinsics.areEqual(tab.getTitle(), "Zyro Browser")) {
            displayTitle = tab.getTitle();
        } else if (Intrinsics.areEqual(tab.getUrl(), "zyro://home")) {
            displayTitle = "New Tab";
        } else {
            displayTitle = true ^ StringsKt.isBlank(tab.getUrl()) ? tab.getUrl() : "New Tab";
        }
        $this$onBindViewHolder_u24lambda_u242.tvTabTitle.setText(displayTitle);
        if (tab.getFavicon() != null) {
            $this$onBindViewHolder_u24lambda_u242.ivTabFavicon.setImageBitmap(tab.getFavicon());
            $this$onBindViewHolder_u24lambda_u242.ivTabFavicon.setImageTintList(null);
        } else {
            $this$onBindViewHolder_u24lambda_u242.ivTabFavicon.setImageResource(R.drawable.ic_globe);
            $this$onBindViewHolder_u24lambda_u242.ivTabFavicon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.zyro_teal)));
        }
        if (tab.getThumbnail() != null) {
            Bitmap thumbnail = tab.getThumbnail();
            Intrinsics.checkNotNull(thumbnail);
            if (!thumbnail.isRecycled()) {
                $this$onBindViewHolder_u24lambda_u242.ivTabThumbnail.setVisibility(0);
                $this$onBindViewHolder_u24lambda_u242.ivTabThumbnail.setImageBitmap(tab.getThumbnail());
                $this$onBindViewHolder_u24lambda_u242.layoutTabEmptyPreview.setVisibility(8);
                $this$onBindViewHolder_u24lambda_u242.cardTab.setOnClickListener(new View.OnClickListener() { // from class: com.zyro.browser.browser.TabGridAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TabGridAdapter.onBindViewHolder$lambda$2$lambda$0(TabGridAdapter.TabViewHolder.this, this, view);
                    }
                });
                $this$onBindViewHolder_u24lambda_u242.btnTabClose.setOnClickListener(new View.OnClickListener() { // from class: com.zyro.browser.browser.TabGridAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        TabGridAdapter.onBindViewHolder$lambda$2$lambda$1(TabGridAdapter.TabViewHolder.this, this, view);
                    }
                });
            }
        }
        $this$onBindViewHolder_u24lambda_u242.ivTabThumbnail.setVisibility(8);
        $this$onBindViewHolder_u24lambda_u242.layoutTabEmptyPreview.setVisibility(0);
        if (Intrinsics.areEqual(tab.getUrl(), "zyro://home")) {
            $this$onBindViewHolder_u24lambda_u242.tvTabPreviewLabel.setText("Zyro Home");
            $this$onBindViewHolder_u24lambda_u242.ivPreviewLogo.setImageResource(R.drawable.ic_shield);
            $this$onBindViewHolder_u24lambda_u242.ivPreviewLogo.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.zyro_orange)));
        } else {
            $this$onBindViewHolder_u24lambda_u242.tvTabPreviewLabel.setText(displayTitle);
            $this$onBindViewHolder_u24lambda_u242.ivPreviewLogo.setImageResource(R.drawable.ic_globe);
            $this$onBindViewHolder_u24lambda_u242.ivPreviewLogo.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.cyber_cyan)));
        }
        $this$onBindViewHolder_u24lambda_u242.cardTab.setOnClickListener(new View.OnClickListener() { // from class: com.zyro.browser.browser.TabGridAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TabGridAdapter.onBindViewHolder$lambda$2$lambda$0(TabGridAdapter.TabViewHolder.this, this, view);
            }
        });
        $this$onBindViewHolder_u24lambda_u242.btnTabClose.setOnClickListener(new View.OnClickListener() { // from class: com.zyro.browser.browser.TabGridAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TabGridAdapter.onBindViewHolder$lambda$2$lambda$1(TabGridAdapter.TabViewHolder.this, this, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2$lambda$0(TabViewHolder holder, TabGridAdapter this$0, View it) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int pos = holder.getAdapterPosition();
        if (pos != -1) {
            this$0.onTabClick.invoke(Integer.valueOf(pos));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2$lambda$1(TabViewHolder holder, TabGridAdapter this$0, View it) {
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int pos = holder.getAdapterPosition();
        if (pos != -1) {
            this$0.onTabClose.invoke(Integer.valueOf(pos));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.tabs.size();
    }
}
