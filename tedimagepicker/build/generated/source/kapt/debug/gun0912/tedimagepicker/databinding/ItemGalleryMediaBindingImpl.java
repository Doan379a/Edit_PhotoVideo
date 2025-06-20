package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemGalleryMediaBindingImpl extends ItemGalleryMediaBinding  {

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
    private final android.widget.FrameLayout mboundView0;
    @NonNull
    private final androidx.cardview.widget.CardView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.FrameLayout mboundView5;
    @NonNull
    private final android.view.View mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemGalleryMediaBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private ItemGalleryMediaBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[2]
            , (android.widget.FrameLayout) bindings[3]
            );
        this.ivImage.setTag(null);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (androidx.cardview.widget.CardView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.FrameLayout) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.view.View) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.viewZoomOut.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
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
        else if (BR.selectType == variableId) {
            setSelectType((gun0912.tedimagepicker.builder.type.SelectType) variable);
        }
        else if (BR.showDuration == variableId) {
            setShowDuration((boolean) variable);
        }
        else if (BR.duration == variableId) {
            setDuration((java.lang.String) variable);
        }
        else if (BR.selectedNumber == variableId) {
            setSelectedNumber((int) variable);
        }
        else if (BR.media == variableId) {
            setMedia((gun0912.tedimagepicker.model.Media) variable);
        }
        else if (BR.showZoom == variableId) {
            setShowZoom((boolean) variable);
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
    public void setSelectType(@Nullable gun0912.tedimagepicker.builder.type.SelectType SelectType) {
        this.mSelectType = SelectType;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.selectType);
        super.requestRebind();
    }
    public void setShowDuration(boolean ShowDuration) {
        this.mShowDuration = ShowDuration;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.showDuration);
        super.requestRebind();
    }
    public void setDuration(@Nullable java.lang.String Duration) {
        this.mDuration = Duration;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.duration);
        super.requestRebind();
    }
    public void setSelectedNumber(int SelectedNumber) {
        this.mSelectedNumber = SelectedNumber;
    }
    public void setMedia(@Nullable gun0912.tedimagepicker.model.Media Media) {
        this.mMedia = Media;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.media);
        super.requestRebind();
    }
    public void setShowZoom(boolean ShowZoom) {
        this.mShowZoom = ShowZoom;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.showZoom);
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
        android.graphics.drawable.Drawable isSelectedMboundView7AndroidDrawableBgMultiImageSelectedMboundView7AndroidDrawableBgMultiImageUnselected = null;
        boolean IsSelected1 = false;
        gun0912.tedimagepicker.builder.type.SelectType selectType = mSelectType;
        boolean selectTypeSelectTypeMULTI = false;
        boolean showDuration = mShowDuration;
        java.lang.String duration = mDuration;
        int isSelectedMboundView1AndroidColorColor9ad92cMboundView1AndroidColorTransparent = 0;
        gun0912.tedimagepicker.model.Media media = mMedia;
        boolean showZoom = mShowZoom;
        android.net.Uri mediaUri = null;

        if ((dirtyFlags & 0x81L) != 0) {

            if((dirtyFlags & 0x81L) != 0) {
                if(isSelected) {
                        dirtyFlags |= 0x200L;
                        dirtyFlags |= 0x800L;
                }
                else {
                        dirtyFlags |= 0x100L;
                        dirtyFlags |= 0x400L;
                }
            }


                // read isSelected ? @android:drawable/bg_multi_image_selected : @android:drawable/bg_multi_image_unselected
                isSelectedMboundView7AndroidDrawableBgMultiImageSelectedMboundView7AndroidDrawableBgMultiImageUnselected = ((isSelected) ? (androidx.appcompat.content.res.AppCompatResources.getDrawable(mboundView7.getContext(), R.drawable.bg_multi_image_selected)) : (androidx.appcompat.content.res.AppCompatResources.getDrawable(mboundView7.getContext(), R.drawable.bg_multi_image_unselected)));
                // read !isSelected
                IsSelected1 = !isSelected;
                // read isSelected ? @android:color/color_9ad92c : @android:color/transparent
                isSelectedMboundView1AndroidColorColor9ad92cMboundView1AndroidColorTransparent = ((isSelected) ? (getColorFromResource(mboundView1, R.color.color_9ad92c)) : (getColorFromResource(mboundView1, android.R.color.transparent)));
        }
        if ((dirtyFlags & 0x82L) != 0) {



                // read selectType == SelectType.MULTI
                selectTypeSelectTypeMULTI = (selectType) == (gun0912.tedimagepicker.builder.type.SelectType.MULTI);
        }
        if ((dirtyFlags & 0x84L) != 0) {
        }
        if ((dirtyFlags & 0x88L) != 0) {
        }
        if ((dirtyFlags & 0xa0L) != 0) {



                if (media != null) {
                    // read media.uri
                    mediaUri = media.getUri();
                }
        }
        if ((dirtyFlags & 0xc0L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xa0L) != 0) {
            // api target 1

            gun0912.tedimagepicker.binding.DataBindingAdapter.loadImage(this.ivImage, mediaUri);
        }
        if ((dirtyFlags & 0x81L) != 0) {
            // api target 1

            this.mboundView1.setCardBackgroundColor(isSelectedMboundView1AndroidColorColor9ad92cMboundView1AndroidColorTransparent);
            this.mboundView6.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(isSelected));
            androidx.databinding.adapters.ViewBindingAdapter.setBackground(this.mboundView7, isSelectedMboundView7AndroidDrawableBgMultiImageSelectedMboundView7AndroidDrawableBgMultiImageUnselected);
            this.viewZoomOut.setClickable(IsSelected1);
        }
        if ((dirtyFlags & 0x88L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, duration);
        }
        if ((dirtyFlags & 0x84L) != 0) {
            // api target 1

            this.mboundView4.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(showDuration));
        }
        if ((dirtyFlags & 0x82L) != 0) {
            // api target 1

            this.mboundView5.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(selectTypeSelectTypeMULTI));
        }
        if ((dirtyFlags & 0xc0L) != 0) {
            // api target 1

            this.viewZoomOut.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(showZoom));
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): isSelected
        flag 1 (0x2L): selectType
        flag 2 (0x3L): showDuration
        flag 3 (0x4L): duration
        flag 4 (0x5L): selectedNumber
        flag 5 (0x6L): media
        flag 6 (0x7L): showZoom
        flag 7 (0x8L): null
        flag 8 (0x9L): isSelected ? @android:drawable/bg_multi_image_selected : @android:drawable/bg_multi_image_unselected
        flag 9 (0xaL): isSelected ? @android:drawable/bg_multi_image_selected : @android:drawable/bg_multi_image_unselected
        flag 10 (0xbL): isSelected ? @android:color/color_9ad92c : @android:color/transparent
        flag 11 (0xcL): isSelected ? @android:color/color_9ad92c : @android:color/transparent
    flag mapping end*/
    //end
}