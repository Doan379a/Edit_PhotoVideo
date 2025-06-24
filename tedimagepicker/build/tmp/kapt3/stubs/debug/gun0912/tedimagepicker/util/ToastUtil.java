package gun0912.tedimagepicker.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u000bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR(\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2 = {"Lgun0912/tedimagepicker/util/ToastUtil;", "", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "toastAction", "Lkotlin/Function1;", "", "", "getToastAction", "()Lkotlin/jvm/functions/Function1;", "setToastAction", "(Lkotlin/jvm/functions/Function1;)V", "showToast", "text", "tedimagepicker_debug"})
public final class ToastUtil {
    public static android.content.Context context;
    @org.jetbrains.annotations.Nullable
    private static kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> toastAction;
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.util.ToastUtil INSTANCE = null;
    
    private ToastUtil() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.Context getContext() {
        return null;
    }
    
    public final void setContext(@org.jetbrains.annotations.NotNull
    android.content.Context p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> getToastAction() {
        return null;
    }
    
    public final void setToastAction(@org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> p0) {
    }
    
    public final void showToast(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
    }
}