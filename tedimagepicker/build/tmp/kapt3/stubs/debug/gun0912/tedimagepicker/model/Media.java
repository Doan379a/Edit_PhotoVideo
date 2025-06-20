package gun0912.tedimagepicker.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0002\u0011\u0012\u00a8\u0006\u0013"}, d2 = {"Lgun0912/tedimagepicker/model/Media;", "", "albumName", "", "uri", "Landroid/net/Uri;", "dateAddedSecond", "", "(Ljava/lang/String;Landroid/net/Uri;J)V", "getAlbumName", "()Ljava/lang/String;", "getDateAddedSecond", "()J", "getUri", "()Landroid/net/Uri;", "Image", "Video", "Lgun0912/tedimagepicker/model/Media$Image;", "Lgun0912/tedimagepicker/model/Media$Video;", "tedimagepicker_debug"})
public abstract class Media {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String albumName = null;
    @org.jetbrains.annotations.NotNull
    private final android.net.Uri uri = null;
    private final long dateAddedSecond = 0L;
    
    private Media(java.lang.String albumName, android.net.Uri uri, long dateAddedSecond) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public java.lang.String getAlbumName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public android.net.Uri getUri() {
        return null;
    }
    
    public long getDateAddedSecond() {
        return 0L;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lgun0912/tedimagepicker/model/Media$Image;", "Lgun0912/tedimagepicker/model/Media;", "albumName", "", "uri", "Landroid/net/Uri;", "dateAddedSecond", "", "(Ljava/lang/String;Landroid/net/Uri;J)V", "getAlbumName", "()Ljava/lang/String;", "getDateAddedSecond", "()J", "getUri", "()Landroid/net/Uri;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "tedimagepicker_debug"})
    public static final class Image extends gun0912.tedimagepicker.model.Media {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String albumName = null;
        @org.jetbrains.annotations.NotNull
        private final android.net.Uri uri = null;
        private final long dateAddedSecond = 0L;
        
        public Image(@org.jetbrains.annotations.NotNull
        java.lang.String albumName, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, long dateAddedSecond) {
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String getAlbumName() {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public android.net.Uri getUri() {
            return null;
        }
        
        @java.lang.Override
        public long getDateAddedSecond() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.net.Uri component2() {
            return null;
        }
        
        public final long component3() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.model.Media.Image copy(@org.jetbrains.annotations.NotNull
        java.lang.String albumName, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, long dateAddedSecond) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J1\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lgun0912/tedimagepicker/model/Media$Video;", "Lgun0912/tedimagepicker/model/Media;", "albumName", "", "uri", "Landroid/net/Uri;", "dateAddedSecond", "", "duration", "(Ljava/lang/String;Landroid/net/Uri;JJ)V", "getAlbumName", "()Ljava/lang/String;", "getDateAddedSecond", "()J", "getDuration", "durationText", "getDurationText", "getUri", "()Landroid/net/Uri;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "", "toString", "tedimagepicker_debug"})
    public static final class Video extends gun0912.tedimagepicker.model.Media {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String albumName = null;
        @org.jetbrains.annotations.NotNull
        private final android.net.Uri uri = null;
        private final long dateAddedSecond = 0L;
        private final long duration = 0L;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String durationText = null;
        
        public Video(@org.jetbrains.annotations.NotNull
        java.lang.String albumName, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, long dateAddedSecond, long duration) {
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String getAlbumName() {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public android.net.Uri getUri() {
            return null;
        }
        
        @java.lang.Override
        public long getDateAddedSecond() {
            return 0L;
        }
        
        public final long getDuration() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDurationText() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.net.Uri component2() {
            return null;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final long component4() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final gun0912.tedimagepicker.model.Media.Video copy(@org.jetbrains.annotations.NotNull
        java.lang.String albumName, @org.jetbrains.annotations.NotNull
        android.net.Uri uri, long dateAddedSecond, long duration) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}