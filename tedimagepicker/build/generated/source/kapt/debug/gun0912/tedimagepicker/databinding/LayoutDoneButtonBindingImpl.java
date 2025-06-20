package gun0912.tedimagepicker.databinding;
import gun0912.tedimagepicker.R;
import gun0912.tedimagepicker.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutDoneButtonBindingImpl extends LayoutDoneButtonBinding  {

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
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.ImageView mboundView2;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutDoneButtonBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private LayoutDoneButtonBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            );
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.ImageView) bindings[2];
        this.mboundView2.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
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
        if (BR.textColor == variableId) {
            setTextColor((java.lang.Integer) variable);
        }
        else if (BR.text == variableId) {
            setText((java.lang.String) variable);
        }
        else if (BR.background == variableId) {
            setBackground((java.lang.Integer) variable);
        }
        else if (BR.buttonDrawableOnly == variableId) {
            setButtonDrawableOnly((boolean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTextColor(@Nullable java.lang.Integer TextColor) {
        this.mTextColor = TextColor;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.textColor);
        super.requestRebind();
    }
    public void setText(@Nullable java.lang.String Text) {
        this.mText = Text;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.text);
        super.requestRebind();
    }
    public void setBackground(@Nullable java.lang.Integer Background) {
        this.mBackground = Background;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.background);
        super.requestRebind();
    }
    public void setButtonDrawableOnly(boolean ButtonDrawableOnly) {
        this.mButtonDrawableOnly = ButtonDrawableOnly;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.buttonDrawableOnly);
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
        int androidxDatabindingViewDataBindingSafeUnboxTextColor = 0;
        boolean buttonDrawableOnly = false;
        java.lang.Integer textColor = mTextColor;
        java.lang.String text = mText;
        java.lang.Integer background = mBackground;
        boolean ButtonDrawableOnly1 = mButtonDrawableOnly;

        if ((dirtyFlags & 0x11L) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(textColor)
                androidxDatabindingViewDataBindingSafeUnboxTextColor = androidx.databinding.ViewDataBinding.safeUnbox(textColor);
        }
        if ((dirtyFlags & 0x12L) != 0) {
        }
        if ((dirtyFlags & 0x14L) != 0) {
        }
        if ((dirtyFlags & 0x18L) != 0) {



                // read !buttonDrawableOnly
                buttonDrawableOnly = !ButtonDrawableOnly1;
        }
        // batch finished
        if ((dirtyFlags & 0x12L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, text);
        }
        if ((dirtyFlags & 0x11L) != 0) {
            // api target 1

            this.mboundView1.setTextColor(androidxDatabindingViewDataBindingSafeUnboxTextColor);
        }
        if ((dirtyFlags & 0x18L) != 0) {
            // api target 1

            this.mboundView1.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(buttonDrawableOnly));
            this.mboundView2.setVisibility(gun0912.tedimagepicker.binding.DataBindingconversionKt.convertBooleanToVisibility(ButtonDrawableOnly1));
        }
        if ((dirtyFlags & 0x14L) != 0) {
            // api target 1

            gun0912.tedimagepicker.binding.DataBindingAdapter.setBackgroundResource(this.mboundView1, background);
            gun0912.tedimagepicker.binding.DataBindingAdapter.setImageViewResource(this.mboundView2, background);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): textColor
        flag 1 (0x2L): text
        flag 2 (0x3L): background
        flag 3 (0x4L): buttonDrawableOnly
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}