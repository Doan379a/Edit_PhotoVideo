package com.example.editphotovideo.ui.editorimage.text

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.editphotovideo.R
import com.example.editphotovideo.databinding.AddTextDialogBinding
import com.example.editphotovideo.ui.editorimage.ColorPickerAdapter

import com.example.editphotovideo.ui.editorimage.DismissListenerTextEditor
import com.example.editphotovideo.utils.showColorPicker
import com.example.editphotovideo.widget.gone
import com.example.editphotovideo.widget.tap
import com.example.editphotovideo.widget.visible
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.android.material.internal.ViewUtils.showKeyboard
import androidx.fragment.app.activityViewModels
import com.example.editphotovideo.utils.ViewUtils.setupSeekBarWithProgressLabel
import com.example.editphotovideo.widget.getTagDebug
import kotlin.math.roundToInt

class TextEditorDialogFragment : DialogFragment() {
    private val textViewModel: TextViewModel by activityViewModels()
    private val fontList = listOf(
        "fonts/allura.ttf",
        "fonts/eduauvicwant.ttf",
        "fonts/eduauvicwantpre.ttf",
        "fonts/pacifico.ttf",
        "fonts/sacramento.ttf",
        "fonts/satisfy.ttf",
        "fonts/tangerine.ttf",
        "fonts/tangerine_regular.ttf",
        "fonts/zeyada.ttf",
        "fonts/parisienne.ttf",
        "fonts/mrdehaviland.ttf",
        "fonts/kaushanscript.ttf",
        "fonts/playball.ttf",
        "fonts/alexbrush.ttf",
        "fonts/cinzel.ttf",
    )

    private var _binding: AddTextDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var mInputMethodManager: InputMethodManager
    private var mColorCode: Int = 0
    private var mTypeface: Typeface = Typeface.DEFAULT
    private var mTextSizeSp: Float = 16f
    private var mTextGravity = Gravity.CENTER
    private var mTextBackgroundColor: Int = Color.TRANSPARENT
    private lateinit var colorPickerAdapter: ColorPickerAdapter
    private var mTextEditorListener: TextEditorListener? = null
    private var dismissListenerTextEditor: DismissListenerTextEditor? = null
    private lateinit var fontAdapter: TextFontAdapter

    fun setDismissListenerTextEditor(listener: DismissListenerTextEditor) {
        dismissListenerTextEditor = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListenerTextEditor?.onDismissTextEditor()
    }

    interface TextEditorListener {
        fun onDone(
            inputText: String,
            colorCode: Int,
            typeface: Typeface,
            mTextSize: Float,
            gravity: Int
        )
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddTextDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.addTextEditText.requestFocus()
        showKeyboard(binding.addTextEditText)
        binding.addTextEditText.post {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.addTextEditText, InputMethodManager.SHOW_IMPLICIT)
        }

        initView()
        viewListener()
        ////
        setupSeekBarWithProgressLabel(
            binding.seekbarTextSize,
            binding.tvProgress,
            minProgress = 16,
            maxProgress = 50,
            isCheckVisibility = true
        ) { value ->
            mTextSizeSp = value.coerceAtLeast(10).toFloat()
            Log.d("TextEditor", "onViewCreateddoan22222: $mTextSizeSp")
            binding.addTextEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizeSp)
        }
        arguments?.let { args ->
            binding.addTextEditText.setText(args.getString(EXTRA_INPUT_TEXT))
            mColorCode = args.getInt(EXTRA_COLOR_CODE)
            mTextSizeSp = args.getFloat(EXTRA_SIZE, 16f)
            mTextGravity = args.getInt(EXTRA_GRAVITY, Gravity.CENTER)
        }
        binding.addTextEditText.gravity = mTextGravity
        setActiveGravityTab(
            when (mTextGravity) {
                Gravity.START or Gravity.TOP -> TabGravity.LEFT
                Gravity.CENTER  or Gravity.CENTER_HORIZONTAL-> TabGravity.CENTER
                Gravity.TOP or Gravity.END -> TabGravity.RIGHT
                else -> TabGravity.CENTER
            }
        )
        binding.addTextEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizeSp)
        binding.addTextEditText.setTextColor(mColorCode)
        colorPickerAdapter.selectColor(mColorCode)

        binding.seekbarTextSize.progress = mTextSizeSp.roundToInt()
        Log.d(getTagDebug(), "onViewCreateddoan11111: --$mColorCode,---$mTextGravity,---$mTextSizeSp, ---$mTypeface")
        textViewModel.textFont.observe(viewLifecycleOwner) { typeface ->
            if (typeface != null) {
                binding.addTextEditText.typeface = typeface
                mTypeface = typeface

            } else {
                binding.addTextEditText.typeface = Typeface.DEFAULT
                mTypeface = Typeface.DEFAULT
            }
            fontAdapter.updateSelectedFont(mTypeface)
        }


    }

    private fun initView() = binding.apply {
        val activity = requireActivity()
        root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            root.getWindowVisibleDisplayFrame(rect)
            val screenHeight = root.rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                Log.d("Keyboard", "Visible")
                hideFontAndColorPickers()
            } else {
                // Bàn phím ĐÃ ẩn
                Log.d("Keyboard", "Hidden")
            }
        }
        ///
        colorPickerAdapter = ColorPickerAdapter(activity)
        addTextColorPickerRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = colorPickerAdapter
        }
        colorPickerAdapter.setOnColorPickerClickListener(object :
            ColorPickerAdapter.OnColorPickerClickListener {
            override fun onColorPickerClickListener(colorCode: Int) {
                if (colorPickerAdapter.getSelectedPosition() == 0) {
                    val defaultColor =
                        ContextCompat.getColor(requireActivity(), R.color.default_color)
                    requireActivity().showColorPicker(defaultColor, onColorPicked = {
                        mColorCode = Color.parseColor(it)
                        addTextEditText.setTextColor(mColorCode)
                    })
                } else {
                    mColorCode = colorCode
                    addTextEditText.setTextColor(colorCode)
                }
            }
        })


        ///
        rcvFont.layoutManager = GridLayoutManager(requireActivity(), 2)
        fontAdapter = TextFontAdapter(requireActivity(), fontList) { typeface ->
            Log.d("font", "initView: $typeface id $id")
            addTextEditText.typeface = typeface
            mTypeface = typeface
        }
        rcvFont.adapter = fontAdapter
    }

    private fun viewListener() = binding.apply {
        addTextDoneTv.setOnClickListener {
            mInputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            dismissListenerTextEditor?.onDismissTextEditor()
            val inputText = addTextEditText.text.toString()
            if (inputText.isNotEmpty()) {
                mTextEditorListener?.onDone(
                    inputText,
                    mColorCode,
                    mTypeface,
                    mTextSizeSp,
                    mTextGravity
                )
            }
            dismiss()
        }

        imgClose.tap {
            dismiss()
        }

        imgKeyboard.setOnClickListener { setActiveTab(Tab.KEYBOARD) }
        imgFont.setOnClickListener { setActiveTab(Tab.FONT) }
        imgColor.setOnClickListener { setActiveTab(Tab.COLOR) }
        imgGravity.setOnClickListener { setActiveTab(Tab.GRAVITY) }
        imgRight.setOnClickListener { setActiveGravityTab(TabGravity.RIGHT) }
        imgLeft.setOnClickListener { setActiveGravityTab(TabGravity.LEFT) }
        imgCenter.setOnClickListener { setActiveGravityTab(TabGravity.CENTER) }
    }

    private fun hideFontAndColorPickers() {
        binding.rcvFont.gone()
        binding.addTextColorPickerRecyclerView.gone()
        binding.llGravity.gone()
        binding.imgKeyboard.setColorFilter(Color.parseColor("#519FFF"))
        binding.imgFont.setColorFilter(Color.parseColor("#A4A4A4"))
        binding.imgColor.setColorFilter(Color.parseColor("#A4A4A4"))
        binding.imgGravity.setColorFilter(Color.parseColor("#A4A4A4"))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun setOnTextEditorListener(textEditorListener: TextEditorListener) {
        mTextEditorListener = textEditorListener
    }

    @SuppressLint("RestrictedApi")
    private fun setActiveTab(tab: Tab) = binding.apply {
        val colorTextSelect = ContextCompat.getColor(
            requireContext(),
            R.color.color_selector_tab
        )
        val colorTextUnSelect =ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
        imgKeyboard.setColorFilter(if (tab == Tab.KEYBOARD) colorTextSelect else colorTextUnSelect)
        imgFont.setColorFilter(if (tab == Tab.FONT) colorTextSelect else colorTextUnSelect)
        imgColor.setColorFilter(if (tab == Tab.COLOR) colorTextSelect else colorTextUnSelect)
        imgGravity.setColorFilter(if (tab == Tab.GRAVITY) colorTextSelect else colorTextUnSelect)
        addTextColorPickerRecyclerView.gone()
        rcvFont.gone()
        llGravity.gone()
        when (tab) {
            Tab.KEYBOARD -> {
                showKeyboard(addTextEditText)
            }

            Tab.COLOR -> {
                addTextColorPickerRecyclerView.visible()
                hideKeyboard(addTextEditText)
            }

            Tab.FONT -> {
                rcvFont.visible()
                hideKeyboard(addTextEditText)
            }

            Tab.GRAVITY -> {
                llGravity.visible()
                hideKeyboard(addTextEditText)
            }
        }
    }

    private fun setActiveGravityTab(tab: TabGravity) = binding.apply {
        val colorTextSelect = ContextCompat.getColor(
            requireContext(),
            R.color.color_selector_tab
        )
        val colorTextUnSelect = ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
        imgLeft.setColorFilter(if (tab == TabGravity.LEFT) colorTextSelect else colorTextUnSelect)
        imgCenter.setColorFilter(if (tab == TabGravity.CENTER) colorTextSelect else colorTextUnSelect)
        imgRight.setColorFilter(if (tab == TabGravity.RIGHT) colorTextSelect else colorTextUnSelect)
        when (tab) {
            TabGravity.LEFT -> {
                mTextGravity = Gravity.TOP or Gravity.START
                addTextEditText.gravity = mTextGravity
                Log.d(getTagDebug(), "setActiveGravityTab1: $mTextGravity")
            }

            TabGravity.CENTER -> {
                mTextGravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
                addTextEditText.gravity = mTextGravity
                Log.d(getTagDebug(), "setActiveGravityTab2: $mTextGravity")

            }

            TabGravity.RIGHT -> {
                mTextGravity = Gravity.TOP or Gravity.END
                addTextEditText.gravity = mTextGravity
                Log.d(getTagDebug(), "setActiveGravityTab3: $mTextGravity")

            }
        }

    }

    companion object {
        private val TAG: String = TextEditorDialogFragment::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR_CODE = "extra_color_code"
        const val EXTRA_SIZE = "extra_size"
        const val EXTRA_GRAVITY="extra_gravity"
        const val EXTRA_BACKGROUND_COLOR = "extra_background_color"

        @JvmOverloads
        fun show(
            appCompatActivity: AppCompatActivity,
            inputText: String = "",
            @ColorInt colorCode: Int = ContextCompat.getColor(appCompatActivity, R.color.white),
            textSizeSp: Float = 16f,
            gravity: Int = Gravity.CENTER,
            backgroundColor: Int = Color.TRANSPARENT,
            dismissListenerTextEditor: DismissListenerTextEditor? = null
        ): TextEditorDialogFragment {
            val args = Bundle().apply {
                putString(EXTRA_INPUT_TEXT, inputText)
                putInt(EXTRA_COLOR_CODE, colorCode)
                putFloat(EXTRA_SIZE, textSizeSp)
                putInt(EXTRA_GRAVITY, gravity)
                putInt(EXTRA_BACKGROUND_COLOR, backgroundColor)
            }
            return TextEditorDialogFragment().apply {
                arguments = args
                dismissListenerTextEditor?.let { setDismissListenerTextEditor(it) }
                show(appCompatActivity.supportFragmentManager, TAG)
            }
        }
    }

}
