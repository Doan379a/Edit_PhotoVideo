package gun0912.tedimagepicker.builder.type;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0011\b\u0002\u0012\b\b\u0001\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\t\u0010\r\u001a\u00020\u0004H\u00d6\u0001J\u0019\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0004H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fj\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015\u00a8\u0006\u0016"}, d2 = {"Lgun0912/tedimagepicker/builder/type/MediaType;", "", "Landroid/os/Parcelable;", "nameResId", "", "(Ljava/lang/String;II)V", "getNameResId", "()I", "permissions", "", "", "getPermissions", "()[Ljava/lang/String;", "describeContents", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "IMAGE", "VIDEO", "IMAGE_AND_VIDEO", "tedimagepicker_debug"})
@kotlinx.parcelize.Parcelize
public enum MediaType implements android.os.Parcelable {
    /*public static final*/ IMAGE /* = new IMAGE(0) */,
    /*public static final*/ VIDEO /* = new VIDEO(0) */,
    /*public static final*/ IMAGE_AND_VIDEO /* = new IMAGE_AND_VIDEO(0) */;
    private final int nameResId = 0;
    
    MediaType(@androidx.annotation.StringRes
    int nameResId) {
    }
    
    public final int getNameResId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getPermissions() {
        return null;
    }
    
    @java.lang.Override
    public int describeContents() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public static kotlin.enums.EnumEntries<gun0912.tedimagepicker.builder.type.MediaType> getEntries() {
        return null;
    }
    
    @java.lang.Override
    public void writeToParcel(@org.jetbrains.annotations.NotNull
    android.os.Parcel parcel, int flags) {
    }
}