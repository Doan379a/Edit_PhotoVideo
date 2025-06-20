package gun0912.tedimagepicker.partialaccess;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 \u00192\u00020\u0001:\u0002\u0019\u001aB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\bH\u0003J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J$\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\u001a\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u000e2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\u0015\u001a\u00020\bH\u0003J\b\u0010\u0016\u001a\u00020\bH\u0002J\b\u0010\u0017\u001a\u00020\bH\u0002J\b\u0010\u0018\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lgun0912/tedimagepicker/partialaccess/PartialAccessManageBottomSheet;", "Lcom/google/android/material/bottomsheet/BottomSheetDialogFragment;", "()V", "binding", "Lgun0912/tedimagepicker/databinding/BottomsheetPartialAccessManageBinding;", "mediaType", "Lgun0912/tedimagepicker/builder/type/MediaType;", "actionComplete", "", "grantFullAccess", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "requestPermission", "selectMoreImageVideo", "setupListener", "setupText", "Companion", "Listener", "tedimagepicker_debug"})
@androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public final class PartialAccessManageBottomSheet extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
    private gun0912.tedimagepicker.databinding.BottomsheetPartialAccessManageBinding binding;
    private gun0912.tedimagepicker.builder.type.MediaType mediaType;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String ARGUMENT_MEDIA_TYPE = "ARGUMENT_MEDIA_TYPE";
    private static final java.lang.String TAG = null;
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.partialaccess.PartialAccessManageBottomSheet.Companion Companion = null;
    
    public PartialAccessManageBottomSheet() {
        super();
    }
    
    @java.lang.Override
    public void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupText() {
    }
    
    private final void setupListener() {
    }
    
    private final void selectMoreImageVideo() {
    }
    
    @android.annotation.SuppressLint(value = {"CheckResult"})
    private final void grantFullAccess() {
    }
    
    @android.annotation.SuppressLint(value = {"CheckResult"})
    private final void requestPermission() {
    }
    
    private final void actionComplete() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lgun0912/tedimagepicker/partialaccess/PartialAccessManageBottomSheet$Companion;", "", "()V", "ARGUMENT_MEDIA_TYPE", "", "TAG", "kotlin.jvm.PlatformType", "show", "", "activity", "Landroidx/fragment/app/FragmentActivity;", "mediaType", "Lgun0912/tedimagepicker/builder/type/MediaType;", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void show(@org.jetbrains.annotations.NotNull
        androidx.fragment.app.FragmentActivity activity, @org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.type.MediaType mediaType) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2 = {"Lgun0912/tedimagepicker/partialaccess/PartialAccessManageBottomSheet$Listener;", "", "onRefreshMedia", "", "tedimagepicker_debug"})
    public static abstract interface Listener {
        
        public abstract void onRefreshMedia();
    }
}