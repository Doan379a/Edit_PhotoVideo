package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutTedImagePickerContentBindingImpl extends LayoutTedImagePickerContentBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(6);
        sIncludes.setIncludes(0, 
            new String[] {"layout_ted_image_picker_partial_access_manage"},
            new int[] {2},
            new int[] {gun0912.tedimagepicker.R.layout.layout_ted_image_picker_partial_access_manage});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rv_media, 3);
        sViewsWithIds.put(R.id.fast_scroller, 4);
        sViewsWithIds.put(R.id.view_selected_media, 5);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutTedImagePickerContentBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private LayoutTedImagePickerContentBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (gun0912.tedimagepicker.base.FastScroller) bindings[4]
            , (gun0912.tedimagepicker.databinding.LayoutTedImagePickerPartialAccessManageBinding) bindings[2]
            , (androidx.recyclerview.widget.RecyclerView) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[1]
            , (android.widget.FrameLayout) bindings[5]
            );
        setContainedBinding(this.layoutTedImagePickerPartialAccessManage);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvSelectedMedia.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        layoutTedImagePickerPartialAccessManage.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (layoutTedImagePickerPartialAccessManage.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.selectType == variableId) {
            setSelectType((gun0912.tedimagepicker.builder.type.SelectType) variable);
        }
        else if (BR.items == variableId) {
            setItems((java.util.List<android.net.Uri>) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSelectType(@Nullable gun0912.tedimagepicker.builder.type.SelectType SelectType) {
        this.mSelectType = SelectType;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.selectType);
        super.requestRebind();
    }
    public void setItems(@Nullable java.util.List<android.net.Uri> Items) {
        this.mItems = Items;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.items);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        layoutTedImagePickerPartialAccessManage.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeLayoutTedImagePickerPartialAccessManage((gun0912.tedimagepicker.databinding.LayoutTedImagePickerPartialAccessManageBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeLayoutTedImagePickerPartialAccessManage(gun0912.tedimagepicker.databinding.LayoutTedImagePickerPartialAccessManageBinding LayoutTedImagePickerPartialAccessManage, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
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
        gun0912.tedimagepicker.builder.type.SelectType selectType = mSelectType;
        boolean selectTypeSelectTypeMULTI = false;
        java.util.List<android.net.Uri> items = mItems;

        if ((dirtyFlags & 0xaL) != 0) {



                // read selectType == SelectType.MULTI
                selectTypeSelectTypeMULTI = (selectType) == (gun0912.tedimagepicker.builder.type.SelectType.MULTI);
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            this.rvSelectedMedia.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(selectTypeSelectTypeMULTI));
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            gun0912.tedimagepicker.binding.DataBindingAdapter.replaceAll(this.rvSelectedMedia, items, true);
        }
        executeBindingsOn(layoutTedImagePickerPartialAccessManage);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): layoutTedImagePickerPartialAccessManage
        flag 1 (0x2L): selectType
        flag 2 (0x3L): items
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}