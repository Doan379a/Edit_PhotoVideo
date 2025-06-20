package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutSelectedAlbumDropDownBindingImpl extends LayoutSelectedAlbumDropDownBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.ImageView mboundView3;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutSelectedAlbumDropDownBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private LayoutSelectedAlbumDropDownBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.ImageView) bindings[3];
        this.mboundView3.setTag(null);
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
        if (BR.imageCountFormat == variableId) {
            setImageCountFormat((java.lang.String) variable);
        }
        else if (BR.isOpened == variableId) {
            setIsOpened((boolean) variable);
        }
        else if (BR.selectedAlbum == variableId) {
            setSelectedAlbum((gun0912.tedimagepicker.model.Album) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setImageCountFormat(@Nullable java.lang.String ImageCountFormat) {
        this.mImageCountFormat = ImageCountFormat;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.imageCountFormat);
        super.requestRebind();
    }
    public void setIsOpened(boolean IsOpened) {
        this.mIsOpened = IsOpened;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.isOpened);
        super.requestRebind();
    }
    public void setSelectedAlbum(@Nullable gun0912.tedimagepicker.model.Album SelectedAlbum) {
        this.mSelectedAlbum = SelectedAlbum;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.selectedAlbum);
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
        java.util.List<gun0912.tedimagepicker.model.Media> selectedAlbumMediaUris = null;
        java.lang.String selectedAlbumName = null;
        java.lang.String imageCountFormat = mImageCountFormat;
        int selectedAlbumMediaUrisSize = 0;
        boolean isOpened = mIsOpened;
        java.lang.String textFormatUtilGetMediaCountTextImageCountFormatSelectedAlbumMediaUrisSize = null;
        android.graphics.drawable.Drawable isOpenedMboundView3AndroidDrawableIcArrowDropUpBlack24dpMboundView3AndroidDrawableIcArrowDropDownBlack24dp = null;
        gun0912.tedimagepicker.model.Album selectedAlbum = mSelectedAlbum;
        boolean selectedAlbumJavaLangObjectNull = false;

        if ((dirtyFlags & 0xdL) != 0) {



                if (selectedAlbum != null) {
                    // read selectedAlbum.mediaUris
                    selectedAlbumMediaUris = selectedAlbum.getMediaUris();
                }


                if (selectedAlbumMediaUris != null) {
                    // read selectedAlbum.mediaUris.size()
                    selectedAlbumMediaUrisSize = selectedAlbumMediaUris.size();
                }


                // read TextFormatUtil.getMediaCountText(imageCountFormat, selectedAlbum.mediaUris.size())
                textFormatUtilGetMediaCountTextImageCountFormatSelectedAlbumMediaUrisSize = gun0912.tedimagepicker.util.TextFormatUtil.getMediaCountText(imageCountFormat, selectedAlbumMediaUrisSize);
            if ((dirtyFlags & 0xcL) != 0) {

                    if (selectedAlbum != null) {
                        // read selectedAlbum.name
                        selectedAlbumName = selectedAlbum.getName();
                    }
                    // read selectedAlbum != null
                    selectedAlbumJavaLangObjectNull = (selectedAlbum) != (null);
            }
        }
        if ((dirtyFlags & 0xaL) != 0) {

            if((dirtyFlags & 0xaL) != 0) {
                if(isOpened) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }


                // read isOpened ? @android:drawable/ic_arrow_drop_up_black_24dp : @android:drawable/ic_arrow_drop_down_black_24dp
                isOpenedMboundView3AndroidDrawableIcArrowDropUpBlack24dpMboundView3AndroidDrawableIcArrowDropDownBlack24dp = ((isOpened) ? (androidx.appcompat.content.res.AppCompatResources.getDrawable(mboundView3.getContext(), R.drawable.ic_arrow_drop_up_black_24dp)) : (androidx.appcompat.content.res.AppCompatResources.getDrawable(mboundView3.getContext(), R.drawable.ic_arrow_drop_down_black_24dp)));
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.mboundView0.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(selectedAlbumJavaLangObjectNull));
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, selectedAlbumName);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, textFormatUtilGetMediaCountTextImageCountFormatSelectedAlbumMediaUrisSize);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.mboundView3, isOpenedMboundView3AndroidDrawableIcArrowDropUpBlack24dpMboundView3AndroidDrawableIcArrowDropDownBlack24dp);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): imageCountFormat
        flag 1 (0x2L): isOpened
        flag 2 (0x3L): selectedAlbum
        flag 3 (0x4L): null
        flag 4 (0x5L): isOpened ? @android:drawable/ic_arrow_drop_up_black_24dp : @android:drawable/ic_arrow_drop_down_black_24dp
        flag 5 (0x6L): isOpened ? @android:drawable/ic_arrow_drop_up_black_24dp : @android:drawable/ic_arrow_drop_down_black_24dp
    flag mapping end*/
    //end
}