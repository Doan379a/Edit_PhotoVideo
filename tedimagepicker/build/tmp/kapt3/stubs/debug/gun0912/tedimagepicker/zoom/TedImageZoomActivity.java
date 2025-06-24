package gun0912.tedimagepicker.zoom;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nH\u0002J\u0012\u0010\u000b\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\u0010\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\rH\u0014J\u0012\u0010\u0010\u001a\u00020\b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lgun0912/tedimagepicker/zoom/TedImageZoomActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lgun0912/tedimagepicker/databinding/ActivityZoomOutBinding;", "uri", "Landroid/net/Uri;", "loadImage", "", "onLoadingFinished", "Lkotlin/Function0;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSaveInstanceState", "outState", "setSavedInstanceState", "Companion", "tedimagepicker_debug"})
public final class TedImageZoomActivity extends androidx.appcompat.app.AppCompatActivity {
    private gun0912.tedimagepicker.databinding.ActivityZoomOutBinding binding;
    private android.net.Uri uri;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String EXTRA_URI = "EXTRA_URI";
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.zoom.TedImageZoomActivity.Companion Companion = null;
    
    public TedImageZoomActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadImage(kotlin.jvm.functions.Function0<kotlin.Unit> onLoadingFinished) {
    }
    
    private final void setSavedInstanceState(android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onSaveInstanceState(@org.jetbrains.annotations.NotNull
    android.os.Bundle outState) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lgun0912/tedimagepicker/zoom/TedImageZoomActivity$Companion;", "", "()V", "EXTRA_URI", "", "getIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.content.Intent getIntent(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        android.net.Uri uri) {
            return null;
        }
    }
}