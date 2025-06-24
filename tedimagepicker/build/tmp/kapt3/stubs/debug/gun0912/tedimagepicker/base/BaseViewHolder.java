package gun0912.tedimagepicker.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b \u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\u0004\b\u0001\u0010\u00032\u00020\u0004B\u0017\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0001\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\u0015\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0013H\u0016R\u0016\u0010\n\u001a\u00028\u0000X\u0084\u0004\u00a2\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\u000fX\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0017"}, d2 = {"Lgun0912/tedimagepicker/base/BaseViewHolder;", "B", "Landroidx/databinding/ViewDataBinding;", "D", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "parent", "Landroid/view/ViewGroup;", "layoutRes", "", "(Landroid/view/ViewGroup;I)V", "binding", "getBinding", "()Landroidx/databinding/ViewDataBinding;", "Landroidx/databinding/ViewDataBinding;", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "bind", "", "data", "(Ljava/lang/Object;)V", "recycled", "tedimagepicker_debug"})
public abstract class BaseViewHolder<B extends androidx.databinding.ViewDataBinding, D extends java.lang.Object> extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final B binding = null;
    
    public BaseViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, @androidx.annotation.LayoutRes
    int layoutRes) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    protected final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    protected final B getBinding() {
        return null;
    }
    
    public abstract void bind(D data);
    
    public void recycled() {
    }
}