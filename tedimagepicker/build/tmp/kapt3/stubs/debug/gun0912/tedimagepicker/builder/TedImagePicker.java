package gun0912.tedimagepicker.builder;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0002\u0003\u0004B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0005"}, d2 = {"Lgun0912/tedimagepicker/builder/TedImagePicker;", "", "()V", "Builder", "Companion", "tedimagepicker_debug"})
public final class TedImagePicker {
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.builder.TedImagePicker.Companion Companion = null;
    
    public TedImagePicker() {
        super();
    }
    
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.builder.TedImagePicker.Builder with(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\u00020\u00002\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bJ\u001a\u0010\f\u001a\u00020\u00002\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t0\rJ\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u001a\u0010\u0011\u001a\u00020\t2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\t0\rJ\u000e\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014J \u0010\u0015\u001a\u00020\t2\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0016\u0012\u0004\u0012\u00020\t0\rJ\u000e\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u0018R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lgun0912/tedimagepicker/builder/TedImagePicker$Builder;", "Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;", "contextWeakReference", "Ljava/lang/ref/WeakReference;", "Landroid/content/Context;", "(Ljava/lang/ref/WeakReference;)V", "cancelListener", "action", "Lkotlin/Function0;", "", "imageSelectCancelListener", "Lgun0912/tedimagepicker/builder/listener/ImageSelectCancelListener;", "errorListener", "Lkotlin/Function1;", "", "onErrorListener", "Lgun0912/tedimagepicker/builder/listener/OnErrorListener;", "start", "Landroid/net/Uri;", "onSelectedListener", "Lgun0912/tedimagepicker/builder/listener/OnSelectedListener;", "startMultiImage", "", "onMultiSelectedListener", "Lgun0912/tedimagepicker/builder/listener/OnMultiSelectedListener;", "tedimagepicker_debug"})
    @android.annotation.SuppressLint(value = {"ParcelCreator"})
    public static final class Builder extends gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<gun0912.tedimagepicker.builder.TedImagePicker.Builder> {
        @org.jetbrains.annotations.NotNull
        private final java.lang.ref.WeakReference<android.content.Context> contextWeakReference = null;
        
        public Builder(@org.jetbrains.annotations.NotNull
        java.lang.ref.WeakReference<android.content.Context> contextWeakReference) {
            super(null, null, 0, 0, false, null, false, null, null, 0, null, null, 0, 0, false, 0, null, 0, 0, null, 0, 0, null, 0, false, null, null, null, null, null, null, 0, false);
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedImagePicker.Builder errorListener(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.listener.OnErrorListener onErrorListener) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedImagePicker.Builder errorListener(@org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> action) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedImagePicker.Builder cancelListener(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.listener.ImageSelectCancelListener imageSelectCancelListener) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedImagePicker.Builder cancelListener(@org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function0<kotlin.Unit> action) {
            return null;
        }
        
        public final void start(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.listener.OnSelectedListener onSelectedListener) {
        }
        
        public final void start(@org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> action) {
        }
        
        public final void startMultiImage(@org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super java.util.List<? extends android.net.Uri>, kotlin.Unit> action) {
        }
        
        public final void startMultiImage(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener onMultiSelectedListener) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lgun0912/tedimagepicker/builder/TedImagePicker$Companion;", "", "()V", "with", "Lgun0912/tedimagepicker/builder/TedImagePicker$Builder;", "context", "Landroid/content/Context;", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @kotlin.jvm.JvmStatic
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedImagePicker.Builder with(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}