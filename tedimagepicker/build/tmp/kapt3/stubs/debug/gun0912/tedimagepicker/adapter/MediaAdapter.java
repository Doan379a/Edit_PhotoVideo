package gun0912.tedimagepicker.adapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002$%B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u0014\u0010\u0018\u001a\u00060\u0019R\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0014\u0010\u001c\u001a\u00060\u001dR\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\fH\u0002J\u0010\u0010\"\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u000e\u0010#\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u0013R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\"\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006&"}, d2 = {"Lgun0912/tedimagepicker/adapter/MediaAdapter;", "Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter;", "Lgun0912/tedimagepicker/model/Media;", "activity", "Landroid/app/Activity;", "builder", "Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;", "(Landroid/app/Activity;Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;)V", "executorService", "Ljava/util/concurrent/ExecutorService;", "onMediaAddListener", "Lkotlin/Function0;", "", "getOnMediaAddListener", "()Lkotlin/jvm/functions/Function0;", "setOnMediaAddListener", "(Lkotlin/jvm/functions/Function0;)V", "selectedUriList", "", "Landroid/net/Uri;", "getSelectedUriList$tedimagepicker_debug", "()Ljava/util/List;", "addMedia", "uri", "getHeaderViewHolder", "Lgun0912/tedimagepicker/adapter/MediaAdapter$CameraViewHolder;", "parent", "Landroid/view/ViewGroup;", "getItemViewHolder", "Lgun0912/tedimagepicker/adapter/MediaAdapter$ImageViewHolder;", "getViewPosition", "", "it", "refreshSelectedView", "removeMedia", "toggleMediaSelect", "CameraViewHolder", "ImageViewHolder", "tedimagepicker_debug"})
public final class MediaAdapter extends gun0912.tedimagepicker.base.BaseSimpleHeaderAdapter<gun0912.tedimagepicker.model.Media> {
    @org.jetbrains.annotations.NotNull
    private final android.app.Activity activity = null;
    @org.jetbrains.annotations.NotNull
    private final gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<?> builder = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<android.net.Uri> selectedUriList = null;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onMediaAddListener;
    @org.jetbrains.annotations.NotNull
    private final java.util.concurrent.ExecutorService executorService = null;
    
    public MediaAdapter(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<?> builder) {
        super(0);
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<android.net.Uri> getSelectedUriList$tedimagepicker_debug() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnMediaAddListener() {
        return null;
    }
    
    public final void setOnMediaAddListener(@org.jetbrains.annotations.Nullable
    kotlin.jvm.functions.Function0<kotlin.Unit> p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public gun0912.tedimagepicker.adapter.MediaAdapter.CameraViewHolder getHeaderViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public gun0912.tedimagepicker.adapter.MediaAdapter.ImageViewHolder getItemViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent) {
        return null;
    }
    
    public final void toggleMediaSelect(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
    }
    
    private final void addMedia(android.net.Uri uri) {
    }
    
    private final int getViewPosition(android.net.Uri it) {
        return 0;
    }
    
    private final void removeMedia(android.net.Uri uri) {
    }
    
    private final void refreshSelectedView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001R\b\u0012\u0004\u0012\u00020\u00040\u0003B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lgun0912/tedimagepicker/adapter/MediaAdapter$CameraViewHolder;", "Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter$HeaderViewHolder;", "Lgun0912/tedimagepicker/databinding/ItemGalleryCameraBinding;", "Lgun0912/tedimagepicker/base/BaseSimpleHeaderAdapter;", "Lgun0912/tedimagepicker/model/Media;", "parent", "Landroid/view/ViewGroup;", "(Lgun0912/tedimagepicker/adapter/MediaAdapter;Landroid/view/ViewGroup;)V", "tedimagepicker_debug"})
    public final class CameraViewHolder extends gun0912.tedimagepicker.base.BaseSimpleHeaderAdapter<gun0912.tedimagepicker.model.Media>.HeaderViewHolder<gun0912.tedimagepicker.databinding.ItemGalleryCameraBinding> {
        
        public CameraViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent) {
            super(null, null, 0);
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\b\u0010\n\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0003H\u0002\u00a8\u0006\r"}, d2 = {"Lgun0912/tedimagepicker/adapter/MediaAdapter$ImageViewHolder;", "Lgun0912/tedimagepicker/base/BaseViewHolder;", "Lgun0912/tedimagepicker/databinding/ItemGalleryMediaBinding;", "Lgun0912/tedimagepicker/model/Media;", "parent", "Landroid/view/ViewGroup;", "(Lgun0912/tedimagepicker/adapter/MediaAdapter;Landroid/view/ViewGroup;)V", "bind", "", "data", "recycled", "startZoomActivity", "media", "tedimagepicker_debug"})
    public final class ImageViewHolder extends gun0912.tedimagepicker.base.BaseViewHolder<gun0912.tedimagepicker.databinding.ItemGalleryMediaBinding, gun0912.tedimagepicker.model.Media> {
        
        public ImageViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent) {
            super(null, 0);
        }
        
        @java.lang.Override
        public void bind(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.model.Media data) {
        }
        
        @java.lang.Override
        public void recycled() {
        }
        
        private final void startZoomActivity(gun0912.tedimagepicker.model.Media media) {
        }
    }
}