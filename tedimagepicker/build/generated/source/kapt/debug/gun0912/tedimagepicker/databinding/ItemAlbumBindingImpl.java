package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemAlbumBindingImpl extends ItemAlbumBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.view_image, 5);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    @NonNull
    private final android.widget.ImageView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemAlbumBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ItemAlbumBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.FrameLayout) bindings[5]
            );
        this.ivImage.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (android.widget.ImageView) bindings[2];
        this.mboundView2.setTag(null);
        this.tvCount.setTag(null);
        this.tvName.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.isSelected == variableId) {
            setIsSelected((boolean) variable);
        }
        else if (BR.album == variableId) {
            setAlbum((gun0912.tedimagepicker.model.Album) variable);
        }
        else if (BR.mediaCountText == variableId) {
            setMediaCountText((java.lang.String) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsSelected(boolean IsSelected) {
        this.mIsSelected = IsSelected;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.isSelected);
        super.requestRebind();
    }
    public void setAlbum(@Nullable gun0912.tedimagepicker.model.Album Album) {
        this.mAlbum = Album;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.album);
        super.requestRebind();
    }
    public void setMediaCountText(@Nullable java.lang.String MediaCountText) {
        this.mMediaCountText = MediaCountText;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.mediaCountText);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        boolean isSelected = mIsSelected;
        java.lang.String albumName = null;
        gun0912.tedimagepicker.model.Album album = mAlbum;
        android.net.Uri albumThumbnailUri = null;
        java.lang.String mediaCountText = mMediaCountText;

        if ((dirtyFlags & 0x9L) != 0) {
        }
        if ((dirtyFlags & 0xaL) != 0) {



                if (album != null) {
                    // read album.name
                    albumName = album.getName();
                    // read album.thumbnailUri
                    albumThumbnailUri = album.getThumbnailUri();
                }
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            gun0912.tedimagepicker.binding.DataBindingAdapter.loadImage(this.ivImage, albumThumbnailUri);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvName, albumName);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            this.mboundView2.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(isSelected));
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvCount, mediaCountText);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isSelected
        flag 1 (0x2L): album
        flag 2 (0x3L): mediaCountText
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}