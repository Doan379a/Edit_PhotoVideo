package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityTedImagePickerBindingImpl extends ActivityTedImagePickerBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(14);
        sIncludes.setIncludes(1, 
            new String[] {"layout_done_button"},
            new int[] {10},
            new int[] {gun0912.tedimagepicker.R.layout.layout_done_button});
        sIncludes.setIncludes(2, 
            new String[] {"layout_selected_album_drop_down"},
            new int[] {9},
            new int[] {gun0912.tedimagepicker.R.layout.layout_selected_album_drop_down});
        sIncludes.setIncludes(3, 
            new String[] {"layout_done_button"},
            new int[] {11},
            new int[] {gun0912.tedimagepicker.R.layout.layout_done_button});
        sIncludes.setIncludes(7, 
            new String[] {"layout_ted_image_picker_content"},
            new int[] {12},
            new int[] {gun0912.tedimagepicker.R.layout.layout_ted_image_picker_content});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rv_album, 13);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityTedImagePickerBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityTedImagePickerBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (androidx.drawerlayout.widget.DrawerLayout) bindings[7]
            , (gun0912.tedimagepicker.databinding.LayoutTedImagePickerContentBinding) bindings[12]
            , (gun0912.tedimagepicker.databinding.LayoutSelectedAlbumDropDownBinding) bindings[9]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (androidx.recyclerview.widget.RecyclerView) bindings[8]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[3]
            , (gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding) bindings[11]
            , (gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding) bindings[10]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.FrameLayout) bindings[2]
            );
        this.drawerLayout.setTag(null);
        setContainedBinding(this.layoutContent);
        setContainedBinding(this.layoutSelectedAlbumDropDown);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.rvAlbumDropDown.setTag(null);
        this.toolbar.setTag(null);
        this.viewBottom.setTag(null);
        setContainedBinding(this.viewDoneBottom);
        setContainedBinding(this.viewDoneTop);
        this.viewSelectedAlbum.setTag(null);
        this.viewSelectedAlbumDropDown.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2000L;
        }
        layoutSelectedAlbumDropDown.invalidateAll();
        viewDoneTop.invalidateAll();
        viewDoneBottom.invalidateAll();
        layoutContent.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layoutSelectedAlbumDropDown.hasPendingBindings()) {
            return true;
        }
        if (viewDoneTop.hasPendingBindings()) {
            return true;
        }
        if (viewDoneBottom.hasPendingBindings()) {
            return true;
        }
        if (layoutContent.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.isAlbumOpened == variableId) {
            setIsAlbumOpened((boolean) variable);
        }
        else if (BR.buttonGravity == variableId) {
            setButtonGravity((gun0912.tedimagepicker.builder.type.ButtonGravity) variable);
        }
        else if (BR.showButton == variableId) {
            setShowButton((boolean) variable);
        }
        else if (BR.selectedAlbum == variableId) {
            setSelectedAlbum((gun0912.tedimagepicker.model.Album) variable);
        }
        else if (BR.buttonDrawableOnly == variableId) {
            setButtonDrawableOnly((boolean) variable);
        }
        else if (BR.buttonTextColor == variableId) {
            setButtonTextColor((java.lang.Integer) variable);
        }
        else if (BR.imageCountFormat == variableId) {
            setImageCountFormat((java.lang.String) variable);
        }
        else if (BR.buttonText == variableId) {
            setButtonText((java.lang.String) variable);
        }
        else if (BR.buttonBackground == variableId) {
            setButtonBackground((java.lang.Integer) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setIsAlbumOpened(boolean IsAlbumOpened) {
        this.mIsAlbumOpened = IsAlbumOpened;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.isAlbumOpened);
        super.requestRebind();
    }
    public void setButtonGravity(@Nullable gun0912.tedimagepicker.builder.type.ButtonGravity ButtonGravity) {
        this.mButtonGravity = ButtonGravity;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.buttonGravity);
        super.requestRebind();
    }
    public void setShowButton(boolean ShowButton) {
        this.mShowButton = ShowButton;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.showButton);
        super.requestRebind();
    }
    public void setSelectedAlbum(@Nullable gun0912.tedimagepicker.model.Album SelectedAlbum) {
        this.mSelectedAlbum = SelectedAlbum;
        synchronized(this) {
            mDirtyFlags |= 0x80L;
        }
        notifyPropertyChanged(BR.selectedAlbum);
        super.requestRebind();
    }
    public void setButtonDrawableOnly(boolean ButtonDrawableOnly) {
        this.mButtonDrawableOnly = ButtonDrawableOnly;
        synchronized(this) {
            mDirtyFlags |= 0x100L;
        }
        notifyPropertyChanged(BR.buttonDrawableOnly);
        super.requestRebind();
    }
    public void setButtonTextColor(@Nullable java.lang.Integer ButtonTextColor) {
        this.mButtonTextColor = ButtonTextColor;
        synchronized(this) {
            mDirtyFlags |= 0x200L;
        }
        notifyPropertyChanged(BR.buttonTextColor);
        super.requestRebind();
    }
    public void setImageCountFormat(@Nullable java.lang.String ImageCountFormat) {
        this.mImageCountFormat = ImageCountFormat;
        synchronized(this) {
            mDirtyFlags |= 0x400L;
        }
        notifyPropertyChanged(BR.imageCountFormat);
        super.requestRebind();
    }
    public void setButtonText(@Nullable java.lang.String ButtonText) {
        this.mButtonText = ButtonText;
        synchronized(this) {
            mDirtyFlags |= 0x800L;
        }
        notifyPropertyChanged(BR.buttonText);
        super.requestRebind();
    }
    public void setButtonBackground(@Nullable java.lang.Integer ButtonBackground) {
        this.mButtonBackground = ButtonBackground;
        synchronized(this) {
            mDirtyFlags |= 0x1000L;
        }
        notifyPropertyChanged(BR.buttonBackground);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layoutSelectedAlbumDropDown.setLifecycleOwner(lifecycleOwner);
        viewDoneTop.setLifecycleOwner(lifecycleOwner);
        viewDoneBottom.setLifecycleOwner(lifecycleOwner);
        layoutContent.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewDoneBottom((gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding) object, fieldId);
            case 1 :
                return onChangeLayoutContent((gun0912.tedimagepicker.databinding.LayoutTedImagePickerContentBinding) object, fieldId);
            case 2 :
                return onChangeViewDoneTop((gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding) object, fieldId);
            case 3 :
                return onChangeLayoutSelectedAlbumDropDown((gun0912.tedimagepicker.databinding.LayoutSelectedAlbumDropDownBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewDoneBottom(gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding ViewDoneBottom, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutContent(gun0912.tedimagepicker.databinding.LayoutTedImagePickerContentBinding LayoutContent, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewDoneTop(gun0912.tedimagepicker.databinding.LayoutDoneButtonBinding ViewDoneTop, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeLayoutSelectedAlbumDropDown(gun0912.tedimagepicker.databinding.LayoutSelectedAlbumDropDownBinding LayoutSelectedAlbumDropDown, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
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
        boolean isAlbumOpened = mIsAlbumOpened;
        boolean buttonGravityButtonGravityTOP = false;
        gun0912.tedimagepicker.builder.type.ButtonGravity buttonGravity = mButtonGravity;
        boolean showButton = mShowButton;
        gun0912.tedimagepicker.model.Album selectedAlbum = mSelectedAlbum;
        boolean buttonGravityButtonGravityBOTTOM = false;
        boolean buttonDrawableOnly = mButtonDrawableOnly;
        java.lang.Integer buttonTextColor = mButtonTextColor;
        boolean showButtonButtonGravityButtonGravityTOPBooleanFalse = false;
        java.lang.String imageCountFormat = mImageCountFormat;
        int selectedAlbumMediaUrisSize = 0;
        int showButtonButtonGravityButtonGravityBOTTOMBooleanFalseViewVISIBLEViewGONE = 0;
        java.lang.String buttonText = mButtonText;
        java.lang.String textFormatUtilGetMediaCountTextImageCountFormatSelectedAlbumMediaUrisSize = null;
        java.lang.Integer buttonBackground = mButtonBackground;
        int selectedAlbumJavaLangObjectNullViewVISIBLEViewGONE = 0;
        boolean selectedAlbumJavaLangObjectNull = false;
        int showButtonButtonGravityButtonGravityTOPBooleanFalseViewVISIBLEViewGONE = 0;
        boolean showButtonButtonGravityButtonGravityBOTTOMBooleanFalse = false;

        if ((dirtyFlags & 0x2010L) != 0) {
        }
        if ((dirtyFlags & 0x2060L) != 0) {

            if((dirtyFlags & 0x2060L) != 0) {
                if(showButton) {
                        dirtyFlags |= 0x8000L;
                        dirtyFlags |= 0x800000L;
                }
                else {
                        dirtyFlags |= 0x4000L;
                        dirtyFlags |= 0x400000L;
                }
            }
        }
        if ((dirtyFlags & 0x2480L) != 0) {



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
            if ((dirtyFlags & 0x2080L) != 0) {

                    if (selectedAlbum != null) {
                        // read selectedAlbum.name
                        selectedAlbumName = selectedAlbum.getName();
                    }
                    // read selectedAlbum != null
                    selectedAlbumJavaLangObjectNull = (selectedAlbum) != (null);
                if((dirtyFlags & 0x2080L) != 0) {
                    if(selectedAlbumJavaLangObjectNull) {
                            dirtyFlags |= 0x80000L;
                    }
                    else {
                            dirtyFlags |= 0x40000L;
                    }
                }


                    // read selectedAlbum != null ? View.VISIBLE : View.GONE
                    selectedAlbumJavaLangObjectNullViewVISIBLEViewGONE = ((selectedAlbumJavaLangObjectNull) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
            }
        }
        if ((dirtyFlags & 0x2100L) != 0) {
        }
        if ((dirtyFlags & 0x2200L) != 0) {
        }
        if ((dirtyFlags & 0x2800L) != 0) {
        }
        if ((dirtyFlags & 0x3000L) != 0) {
        }
        // batch finished

        if ((dirtyFlags & 0x808000L) != 0) {


            if ((dirtyFlags & 0x8000L) != 0) {

                    // read buttonGravity == ButtonGravity.TOP
                    buttonGravityButtonGravityTOP = (buttonGravity) == (gun0912.tedimagepicker.builder.type.ButtonGravity.TOP);
            }
            if ((dirtyFlags & 0x800000L) != 0) {

                    // read buttonGravity == ButtonGravity.BOTTOM
                    buttonGravityButtonGravityBOTTOM = (buttonGravity) == (gun0912.tedimagepicker.builder.type.ButtonGravity.BOTTOM);
            }
        }

        if ((dirtyFlags & 0x2060L) != 0) {

                // read showButton ? buttonGravity == ButtonGravity.TOP : false
                showButtonButtonGravityButtonGravityTOPBooleanFalse = ((showButton) ? (buttonGravityButtonGravityTOP) : (false));
                // read showButton ? buttonGravity == ButtonGravity.BOTTOM : false
                showButtonButtonGravityButtonGravityBOTTOMBooleanFalse = ((showButton) ? (buttonGravityButtonGravityBOTTOM) : (false));
            if((dirtyFlags & 0x2060L) != 0) {
                if(showButtonButtonGravityButtonGravityTOPBooleanFalse) {
                        dirtyFlags |= 0x200000L;
                }
                else {
                        dirtyFlags |= 0x100000L;
                }
            }
            if((dirtyFlags & 0x2060L) != 0) {
                if(showButtonButtonGravityButtonGravityBOTTOMBooleanFalse) {
                        dirtyFlags |= 0x20000L;
                }
                else {
                        dirtyFlags |= 0x10000L;
                }
            }


                // read showButton ? buttonGravity == ButtonGravity.TOP : false ? View.VISIBLE : View.GONE
                showButtonButtonGravityButtonGravityTOPBooleanFalseViewVISIBLEViewGONE = ((showButtonButtonGravityButtonGravityTOPBooleanFalse) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
                // read showButton ? buttonGravity == ButtonGravity.BOTTOM : false ? View.VISIBLE : View.GONE
                showButtonButtonGravityButtonGravityBOTTOMBooleanFalseViewVISIBLEViewGONE = ((showButtonButtonGravityButtonGravityBOTTOMBooleanFalse) ? (android.view.View.VISIBLE) : (android.view.View.GONE));
        }
        // batch finished
        if ((dirtyFlags & 0x2080L) != 0) {
            // api target 1

            this.layoutSelectedAlbumDropDown.getRoot().setVisibility(selectedAlbumJavaLangObjectNullViewVISIBLEViewGONE);
            this.layoutSelectedAlbumDropDown.setSelectedAlbum(selectedAlbum);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, selectedAlbumName);
            this.viewSelectedAlbum.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(selectedAlbumJavaLangObjectNull));
        }
        if ((dirtyFlags & 0x2400L) != 0) {
            // api target 1

            this.layoutSelectedAlbumDropDown.setImageCountFormat(imageCountFormat);
        }
        if ((dirtyFlags & 0x2010L) != 0) {
            // api target 1

            this.layoutSelectedAlbumDropDown.setIsOpened(isAlbumOpened);
            this.rvAlbumDropDown.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(isAlbumOpened));
        }
        if ((dirtyFlags & 0x2480L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, textFormatUtilGetMediaCountTextImageCountFormatSelectedAlbumMediaUrisSize);
        }
        if ((dirtyFlags & 0x2060L) != 0) {
            // api target 1

            this.viewDoneBottom.getRoot().setVisibility(showButtonButtonGravityButtonGravityBOTTOMBooleanFalseViewVISIBLEViewGONE);
            this.viewDoneTop.getRoot().setVisibility(showButtonButtonGravityButtonGravityTOPBooleanFalseViewVISIBLEViewGONE);
        }
        if ((dirtyFlags & 0x3000L) != 0) {
            // api target 1

            this.viewDoneBottom.setBackground(buttonBackground);
            this.viewDoneTop.setBackground(buttonBackground);
        }
        if ((dirtyFlags & 0x2100L) != 0) {
            // api target 1

            this.viewDoneBottom.setButtonDrawableOnly(buttonDrawableOnly);
            this.viewDoneTop.setButtonDrawableOnly(buttonDrawableOnly);
        }
        if ((dirtyFlags & 0x2800L) != 0) {
            // api target 1

            this.viewDoneBottom.setText(buttonText);
            this.viewDoneTop.setText(buttonText);
        }
        if ((dirtyFlags & 0x2200L) != 0) {
            // api target 1

            this.viewDoneBottom.setTextColor(buttonTextColor);
            this.viewDoneTop.setTextColor(buttonTextColor);
        }
        executeBindingsOn(layoutSelectedAlbumDropDown);
        executeBindingsOn(viewDoneTop);
        executeBindingsOn(viewDoneBottom);
        executeBindingsOn(layoutContent);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewDoneBottom
        flag 1 (0x2L): layoutContent
        flag 2 (0x3L): viewDoneTop
        flag 3 (0x4L): layoutSelectedAlbumDropDown
        flag 4 (0x5L): isAlbumOpened
        flag 5 (0x6L): buttonGravity
        flag 6 (0x7L): showButton
        flag 7 (0x8L): selectedAlbum
        flag 8 (0x9L): buttonDrawableOnly
        flag 9 (0xaL): buttonTextColor
        flag 10 (0xbL): imageCountFormat
        flag 11 (0xcL): buttonText
        flag 12 (0xdL): buttonBackground
        flag 13 (0xeL): null
        flag 14 (0xfL): showButton ? buttonGravity == ButtonGravity.TOP : false
        flag 15 (0x10L): showButton ? buttonGravity == ButtonGravity.TOP : false
        flag 16 (0x11L): showButton ? buttonGravity == ButtonGravity.BOTTOM : false ? View.VISIBLE : View.GONE
        flag 17 (0x12L): showButton ? buttonGravity == ButtonGravity.BOTTOM : false ? View.VISIBLE : View.GONE
        flag 18 (0x13L): selectedAlbum != null ? View.VISIBLE : View.GONE
        flag 19 (0x14L): selectedAlbum != null ? View.VISIBLE : View.GONE
        flag 20 (0x15L): showButton ? buttonGravity == ButtonGravity.TOP : false ? View.VISIBLE : View.GONE
        flag 21 (0x16L): showButton ? buttonGravity == ButtonGravity.TOP : false ? View.VISIBLE : View.GONE
        flag 22 (0x17L): showButton ? buttonGravity == ButtonGravity.BOTTOM : false
        flag 23 (0x18L): showButton ? buttonGravity == ButtonGravity.BOTTOM : false
    flag mapping end*/
    //end
}