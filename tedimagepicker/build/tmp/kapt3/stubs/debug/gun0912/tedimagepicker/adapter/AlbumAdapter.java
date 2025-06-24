package gun0912.tedimagepicker.adapter;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0001\u0011B\u0011\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002R\u0012\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lgun0912/tedimagepicker/adapter/AlbumAdapter;", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter;", "Lgun0912/tedimagepicker/model/Album;", "Lgun0912/tedimagepicker/adapter/AlbumAdapter$AlbumViewHolder;", "builder", "Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;", "(Lgun0912/tedimagepicker/builder/TedImagePickerBaseBuilder;)V", "selectedPosition", "", "getViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "Lgun0912/tedimagepicker/base/BaseRecyclerViewAdapter$ViewType;", "setSelectedAlbum", "", "album", "AlbumViewHolder", "tedimagepicker_debug"})
public final class AlbumAdapter extends gun0912.tedimagepicker.base.BaseRecyclerViewAdapter<gun0912.tedimagepicker.model.Album, gun0912.tedimagepicker.adapter.AlbumAdapter.AlbumViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<?> builder = null;
    private int selectedPosition = 0;
    
    public AlbumAdapter(@org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.builder.TedImagePickerBaseBuilder<?> builder) {
        super(0);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public gun0912.tedimagepicker.adapter.AlbumAdapter.AlbumViewHolder getViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, @org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.base.BaseRecyclerViewAdapter.ViewType viewType) {
        return null;
    }
    
    public final void setSelectedAlbum(@org.jetbrains.annotations.NotNull
    gun0912.tedimagepicker.model.Album album) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0003H\u0016J\b\u0010\n\u001a\u00020\bH\u0016\u00a8\u0006\u000b"}, d2 = {"Lgun0912/tedimagepicker/adapter/AlbumAdapter$AlbumViewHolder;", "Lgun0912/tedimagepicker/base/BaseViewHolder;", "Lgun0912/tedimagepicker/databinding/ItemAlbumBinding;", "Lgun0912/tedimagepicker/model/Album;", "parent", "Landroid/view/ViewGroup;", "(Lgun0912/tedimagepicker/adapter/AlbumAdapter;Landroid/view/ViewGroup;)V", "bind", "", "data", "recycled", "tedimagepicker_debug"})
    public final class AlbumViewHolder extends gun0912.tedimagepicker.base.BaseViewHolder<gun0912.tedimagepicker.databinding.ItemAlbumBinding, gun0912.tedimagepicker.model.Album> {
        
        public AlbumViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent) {
            super(null, 0);
        }
        
        @java.lang.Override
        public void bind(@org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.model.Album data) {
        }
        
        @java.lang.Override
        public void recycled() {
        }
    }
}