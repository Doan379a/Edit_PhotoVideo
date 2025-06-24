package gun0912.tedimagepicker.builder;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0002\u0003\u0004B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0005"}, d2 = {"Lgun0912/tedimagepicker/builder/TedRxImagePicker;", "", "()V", "Builder", "Companion", "tedimagepicker_debug"})
public final class TedRxImagePicker {
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.builder.TedRxImagePicker.Companion Companion = null;
    
    public TedRxImagePicker() {
        super();
    }
    
    @kotlin.jvm.JvmStatic
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.builder.TedRxImagePicker.Builder with(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007J\u001c\u0010\u0006\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0002J\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000f0\u0007R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lgun0912/tedimagepicker/builder/TedRxImagePicker$Builder;", "Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;", "contextWeakReference", "Ljava/lang/ref/WeakReference;", "Landroid/content/Context;", "(Ljava/lang/ref/WeakReference;)V", "start", "Lio/reactivex/Single;", "Landroid/net/Uri;", "", "selectType", "Lgun0912/tedimagepicker/builder/type/SelectType;", "emitter", "Lio/reactivex/SingleEmitter;", "startMultiImage", "", "tedimagepicker_debug"})
    @android.annotation.SuppressLint(value = {"ParcelCreator"})
    public static final class Builder extends gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<gun0912.tedimagepicker.builder.TedRxImagePicker.Builder> {
        @org.jetbrains.annotations.NotNull
        private final java.lang.ref.WeakReference<android.content.Context> contextWeakReference = null;
        
        public Builder(@org.jetbrains.annotations.NotNull
        java.lang.ref.WeakReference<android.content.Context> contextWeakReference) {
            super(null, null, 0, 0, false, null, false, null, null, 0, null, null, 0, 0, false, 0, null, 0, 0, null, 0, 0, null, 0, false, null, null, null, null, null, null, 0, false);
        }
        
        @org.jetbrains.annotations.NotNull
        public final io.reactivex.Single<android.net.Uri> start() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final io.reactivex.Single<java.util.List<android.net.Uri>> startMultiImage() {
            return null;
        }
        
        private final void start(gun0912.tedimagepicker.builder.type.SelectType selectType, io.reactivex.SingleEmitter<?> emitter) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lgun0912/tedimagepicker/builder/TedRxImagePicker$Companion;", "", "()V", "with", "Lgun0912/tedimagepicker/builder/TedRxImagePicker$Builder;", "context", "Landroid/content/Context;", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @kotlin.jvm.JvmStatic
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.builder.TedRxImagePicker.Builder with(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}