package gun0912.tedimagepicker.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b \u0018\u0000 \u0012*\u0004\b\u0000\u0010\u00012\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002H\u00010\u00030\u0002:\u0002\u0012\u0013B\u000f\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J \u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u000bR\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\f\u001a\u00020\rH&J\u001c\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\f\u001a\u00020\rH&J\"\u0010\u000f\u001a\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0014"}, d2 = {"Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter;", "D", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter;", "Lgun0912/tedimagepicker/base/BaseViewHolder;", "Landroidx/databinding/ViewDataBinding;", "headerCount", "", "(I)V", "getHeaderCount", "()I", "getHeaderViewHolder", "Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter$HeaderViewHolder;", "parent", "Landroid/view/ViewGroup;", "getItemViewHolder", "getViewHolder", "viewType", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter$ViewType;", "Companion", "HeaderViewHolder", "tedimagepicker_debug"})
public abstract class BaseSimpleHeaderAdapter<D extends java.lang.Object> extends gun0912.tedimagepicker.base.BaseRecyclerViewAdapter<D, gun0912.tedimagepicker.base.BaseViewHolder<? extends androidx.databinding.ViewDataBinding, D>> {
    private final int headerCount = 0;
    private static final int HEADER_COUNT = 1;
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.base.BaseSimpleHeaderAdapter.Companion Companion = null;
    
    public BaseSimpleHeaderAdapter(int headerCount) {
        super(0);
    }
    
    protected final int getHeaderCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract gun0912.tedimagepicker.base.BaseViewHolder<androidx.databinding.ViewDataBinding, D> getItemViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent);
    
    @org.jetbrains.annotations.NotNull
    public abstract gun0912.tedimagepicker.base.BaseSimpleHeaderAdapter<D>.HeaderViewHolder<androidx.databinding.ViewDataBinding> getHeaderViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent);
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public gun0912.tedimagepicker.base.BaseViewHolder<?, D> getViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, @org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.base.BaseRecyclerViewAdapter.ViewType viewType) {
        return null;
    }
    
    public BaseSimpleHeaderAdapter() {
        super(0);
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter$Companion;", "", "()V", "HEADER_COUNT", "", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0096\u0004\u0018\u0000*\n\b\u0001\u0010\u0001 \u0001*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00028\u00000\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter$HeaderViewHolder;", "B", "Landroidx/databinding/ViewDataBinding;", "Lgun0912/tedimagepicker/base/BaseViewHolder;", "parent", "Landroid/view/ViewGroup;", "layoutRes", "", "(Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter;Landroid/view/ViewGroup;I)V", "bind", "", "data", "(Ljava/lang/Object;)V", "tedimagepicker_debug"})
    public class HeaderViewHolder<B extends androidx.databinding.ViewDataBinding> extends gun0912.tedimagepicker.base.BaseViewHolder<B, D> {
        
        public HeaderViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, @androidx.annotation.LayoutRes
        int layoutRes) {
            super(null, 0);
        }
        
        @java.lang.Override
        public void bind(D data) {
        }
    }
}