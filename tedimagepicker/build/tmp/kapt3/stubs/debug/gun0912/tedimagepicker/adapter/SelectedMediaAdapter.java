package gun0912.tedimagepicker.adapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R(\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u00a8\u0006\u0017"}, d2 = {"Lgun0912/tedimagepicker/adapter/SelectedMediaAdapter;", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter;", "Landroid/net/Uri;", "Lgun0912/tedimagepicker/adapter/SelectedMediaAdapter$MediaViewHolder;", "()V", "layoutManager", "Landroidx/recyclerview/widget/RecyclerView$LayoutManager;", "onClearClickListener", "Lkotlin/Function1;", "", "getOnClearClickListener", "()Lkotlin/jvm/functions/Function1;", "setOnClearClickListener", "(Lkotlin/jvm/functions/Function1;)V", "getViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter$ViewType;", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "MediaViewHolder", "tedimagepicker_debug"})
public final class SelectedMediaAdapter extends gun0912.tedimagepicker.base.BaseRecyclerViewAdapter<android.net.Uri, gun0912.tedimagepicker.adapter.SelectedMediaAdapter.MediaViewHolder> {
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> onClearClickListener;
    @org.jetbrains.annotations.Nullable
    private androidx.recyclerview.widget.RecyclerView.LayoutManager layoutManager;
    
    public SelectedMediaAdapter() {
        super(0);
    }
    
    @org.jetbrains.annotations.Nullable
    public final kotlin.jvm.functions.Function1<android.net.Uri, kotlin.Unit> getOnClearClickListener() {
        return null;
    }
    
    public final void setOnClearClickListener(@org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public gun0912.tedimagepicker.adapter.SelectedMediaAdapter.MediaViewHolder getViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, @org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.base.BaseRecyclerViewAdapter.ViewType viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onAttachedToRecyclerView(@org.jetbrains.annotations.NotNull
    androidx.recyclerview.widget.RecyclerView recyclerView) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\b\u0010\n\u001a\u00020\bH\u0016\u00a8\u0006\u000b"}, d2 = {"Lgun0912/tedimagepicker/adapter/SelectedMediaAdapter$MediaViewHolder;", "Lgun0912/tedimagepicker/base/BaseViewHolder;", "Lgun0912/tedimagepicker/databinding/ItemSelectedMediaBinding;", "Landroid/net/Uri;", "parent", "Landroid/view/ViewGroup;", "(Lgun0912/tedimagepicker/adapter/SelectedMediaAdapter;Landroid/view/ViewGroup;)V", "bind", "", "data", "recycled", "tedimagepicker_debug"})
    public final class MediaViewHolder extends gun0912.tedimagepicker.base.BaseViewHolder<gun0912.tedimagepicker.databinding.ItemSelectedMediaBinding, android.net.Uri> {
        
        public MediaViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent) {
            super(null, 0);
        }
        
        @java.lang.Override
        public void bind(@org.jetbrains.annotations.NotNull
        android.net.Uri data) {
        }
        
        @java.lang.Override
        public void recycled() {
        }
    }
}