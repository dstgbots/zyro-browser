package com.zyro.browser.browser;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.zyro.browser.browser.SpeedDialAdapter;
import com.zyro.browser.databinding.ItemSpeedDialTileBinding;
import com.zyro.browser.model.SpeedDialItem;
import java.util.List;
import java.util.Locale;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: SpeedDialAdapter.kt */
/* loaded from: classes5.dex */
public final class SpeedDialAdapter extends RecyclerView.Adapter<ViewHolder> {
    private boolean isDarkTheme;
    private List<SpeedDialItem> items;
    private final Function1<SpeedDialItem, Unit> onItemClick;
    private final Function1<SpeedDialItem, Unit> onItemLongClick;

    public /* synthetic */ SpeedDialAdapter(List list, boolean z, Function1 function1, Function1 function12, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? true : z, function1, (i & 8) != 0 ? null : function12);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpeedDialAdapter(List<SpeedDialItem> items, boolean isDarkTheme, Function1<? super SpeedDialItem, Unit> onItemClick, Function1<? super SpeedDialItem, Unit> function1) {
        Intrinsics.checkNotNullParameter(items, "items");
        Intrinsics.checkNotNullParameter(onItemClick, "onItemClick");
        this.items = items;
        this.isDarkTheme = isDarkTheme;
        this.onItemClick = onItemClick;
        this.onItemLongClick = function1;
    }

    public static /* synthetic */ void updateData$default(SpeedDialAdapter speedDialAdapter, List list, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = speedDialAdapter.isDarkTheme;
        }
        speedDialAdapter.updateData(list, z);
    }

    public final void updateData(List<SpeedDialItem> newItems, boolean dark) {
        Intrinsics.checkNotNullParameter(newItems, "newItems");
        this.items = newItems;
        this.isDarkTheme = dark;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        ItemSpeedDialTileBinding binding = ItemSpeedDialTileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        Intrinsics.checkNotNullExpressionValue(binding, "inflate(\n            Lay…          false\n        )");
        return new ViewHolder(this, binding);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        SpeedDialItem item = this.items.get(position);
        holder.bind(item, this.isDarkTheme);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    /* compiled from: SpeedDialAdapter.kt */
    public final class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSpeedDialTileBinding binding;
        final /* synthetic */ SpeedDialAdapter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ViewHolder(SpeedDialAdapter this$0, ItemSpeedDialTileBinding binding) {
            super(binding.getRoot());
            Intrinsics.checkNotNullParameter(binding, "binding");
            this.this$0 = this$0;
            this.binding = binding;
        }

        public final void bind(final SpeedDialItem item, boolean dark) {
            String badge;
            int itemColor;
            Intrinsics.checkNotNullParameter(item, "item");
            this.binding.tvTileTitle.setText(item.getTitle());
            if (!StringsKt.isBlank(item.getBadgeText())) {
                badge = item.getBadgeText();
            } else if (!StringsKt.isBlank(item.getTitle())) {
                badge = StringsKt.take(item.getTitle(), 2).toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(badge, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            } else {
                badge = "•";
            }
            this.binding.tvTileBadge.setText(badge);
            try {
                itemColor = Color.parseColor(item.getBgColor());
            } catch (Exception e) {
                itemColor = Color.parseColor("#FF6D00");
            }
            this.binding.tvTileBadge.setTextColor(-1);
            this.binding.tileFrame.setBackgroundTintList(ColorStateList.valueOf(itemColor));
            int textPrimary = Color.parseColor(dark ? "#F4F5F6" : "#1F2937");
            this.binding.tvTileTitle.setTextColor(textPrimary);
            LinearLayout root = this.binding.getRoot();
            final SpeedDialAdapter speedDialAdapter = this.this$0;
            root.setOnClickListener(new View.OnClickListener() { // from class: com.zyro.browser.browser.SpeedDialAdapter$ViewHolder$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SpeedDialAdapter.ViewHolder.bind$lambda$0(SpeedDialAdapter.this, item, view);
                }
            });
            LinearLayout root2 = this.binding.getRoot();
            final SpeedDialAdapter speedDialAdapter2 = this.this$0;
            root2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.zyro.browser.browser.SpeedDialAdapter$ViewHolder$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    boolean bind$lambda$1;
                    bind$lambda$1 = SpeedDialAdapter.ViewHolder.bind$lambda$1(SpeedDialAdapter.this, item, view);
                    return bind$lambda$1;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void bind$lambda$0(SpeedDialAdapter this$0, SpeedDialItem item, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            this$0.onItemClick.invoke(item);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean bind$lambda$1(SpeedDialAdapter this$0, SpeedDialItem item, View it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(item, "$item");
            Function1 function1 = this$0.onItemLongClick;
            if (function1 != null) {
                function1.invoke(item);
                return true;
            }
            return true;
        }
    }
}
