package gun0912.tedimagepicker.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lgun0912/tedimagepicker/util/GalleryUtil;", "", "()V", "Companion", "tedimagepicker_debug"})
public final class GalleryUtil {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String INDEX_MEDIA_ID = "_id";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String INDEX_MEDIA_URI = "_data";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String INDEX_DATE_ADDED = "date_added";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String INDEX_ALBUM_NAME = "bucket_display_name";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String INDEX_DURATION = "duration";
    @org.jetbrains.annotations.NotNull
    public static final gun0912.tedimagepicker.util.GalleryUtil.Companion Companion = null;
    
    public GalleryUtil() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010&\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u001dB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\t\u001a\u00020\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fH\u0002J\u001e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J)\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r0\u00152\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0000\u00a2\u0006\u0002\b\u0018J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0014\u0010\u001b\u001a\u00020\u001c*\u00020\u001a2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lgun0912/tedimagepicker/util/GalleryUtil$Companion;", "", "()V", "INDEX_ALBUM_NAME", "", "INDEX_DATE_ADDED", "INDEX_DURATION", "INDEX_MEDIA_ID", "INDEX_MEDIA_URI", "getAlbum", "Lgun0912/tedimagepicker/model/Album;", "entry", "", "", "Lgun0912/tedimagepicker/model/Media;", "getAllMediaList", "context", "Landroid/content/Context;", "queryMediaType", "Lgun0912/tedimagepicker/util/GalleryUtil$Companion$QueryMediaType;", "getMedia", "Lio/reactivex/Single;", "mediaType", "Lgun0912/tedimagepicker/builder/type/MediaType;", "getMedia$tedimagepicker_debug", "cursor", "Landroid/database/Cursor;", "getMediaUri", "Landroid/net/Uri;", "QueryMediaType", "tedimagepicker_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final io.reactivex.Single<java.util.List<gun0912.tedimagepicker.model.Album>> getMedia$tedimagepicker_debug(@org.jetbrains.annotations.NotNull
        android.content.Context context, @org.jetbrains.annotations.NotNull
        gun0912.tedimagepicker.builder.type.MediaType mediaType) {
            return null;
        }
        
        private final java.util.List<gun0912.tedimagepicker.model.Media> getAllMediaList(android.content.Context context, gun0912.tedimagepicker.util.GalleryUtil.Companion.QueryMediaType queryMediaType) {
            return null;
        }
        
        private final gun0912.tedimagepicker.model.Album getAlbum(java.util.Map.Entry<java.lang.String, ? extends java.util.List<? extends gun0912.tedimagepicker.model.Media>> entry) {
            return null;
        }
        
        private final gun0912.tedimagepicker.model.Media getMedia(android.database.Cursor cursor, gun0912.tedimagepicker.util.GalleryUtil.Companion.QueryMediaType queryMediaType) {
            return null;
        }
        
        private final android.net.Uri getMediaUri(android.database.Cursor $this$getMediaUri, gun0912.tedimagepicker.util.GalleryUtil.Companion.QueryMediaType queryMediaType) {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lgun0912/tedimagepicker/util/GalleryUtil$Companion$QueryMediaType;", "", "contentUri", "Landroid/net/Uri;", "(Ljava/lang/String;ILandroid/net/Uri;)V", "getContentUri", "()Landroid/net/Uri;", "IMAGE", "VIDEO", "tedimagepicker_debug"})
        static enum QueryMediaType {
            /*public static final*/ IMAGE /* = new IMAGE(null) */,
            /*public static final*/ VIDEO /* = new VIDEO(null) */;
            @org.jetbrains.annotations.NotNull
            private final android.net.Uri contentUri = null;
            
            QueryMediaType(android.net.Uri contentUri) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.net.Uri getContentUri() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public static kotlin.enums.EnumEntries<gun0912.tedimagepicker.util.GalleryUtil.Companion.QueryMediaType> getEntries() {
                return null;
            }
        }
    }
}