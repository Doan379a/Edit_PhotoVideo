package gun0912.tedimagepicker.util;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016JO\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0014\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0015\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0002\u0010\u0016J;\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"Lgun0912/tedimagepicker/util/TedImagePickerContentProvider;", "Landroid/content/ContentProvider;", "()V", "delete", "", "uri", "Landroid/net/Uri;", "selection", "", "selectionArgs", "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "getType", "insert", "values", "Landroid/content/ContentValues;", "onCreate", "", "query", "Landroid/database/Cursor;", "projection", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "tedimagepicker_debug"})
public final class TedImagePickerContentProvider extends android.content.ContentProvider {
    
    public TedImagePickerContentProvider() {
        super();
    }
    
    @java.lang.Override
    public boolean onCreate() {
        return false;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.net.Uri insert(@org.jetbrains.annotations.NotNull
    android.net.Uri uri, @org.jetbrains.annotations.Nullable
    android.content.ContentValues values) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.database.Cursor query(@org.jetbrains.annotations.NotNull
    android.net.Uri uri, @org.jetbrains.annotations.Nullable
    java.lang.String[] projection, @org.jetbrains.annotations.Nullable
    java.lang.String selection, @org.jetbrains.annotations.Nullable
    java.lang.String[] selectionArgs, @org.jetbrains.annotations.Nullable
    java.lang.String sortOrder) {
        return null;
    }
    
    @java.lang.Override
    public int update(@org.jetbrains.annotations.NotNull
    android.net.Uri uri, @org.jetbrains.annotations.Nullable
    android.content.ContentValues values, @org.jetbrains.annotations.Nullable
    java.lang.String selection, @org.jetbrains.annotations.Nullable
    java.lang.String[] selectionArgs) {
        return 0;
    }
    
    @java.lang.Override
    public int delete(@org.jetbrains.annotations.NotNull
    android.net.Uri uri, @org.jetbrains.annotations.Nullable
    java.lang.String selection, @org.jetbrains.annotations.Nullable
    java.lang.String[] selectionArgs) {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String getType(@org.jetbrains.annotations.NotNull
    android.net.Uri uri) {
        return null;
    }
}