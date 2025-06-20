package com.example.editphotovideo.ui.editorimage

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.example.editphotovideo.R
import com.example.editphotovideo.base.base_edit.BaseActivity
import com.example.editphotovideo.data.entity.MediaEntity
import com.example.editphotovideo.data.entity.MediaType
import com.example.editphotovideo.data.viewmodel.MediaViewModel
import com.example.editphotovideo.databinding.ActivityEditImageBinding
import com.example.editphotovideo.ui.editorimage.beauty.BeautyFilterController
import com.example.editphotovideo.ui.editorimage.crop.CropImageIncludeController
import com.example.editphotovideo.ui.editorimage.emoji.EmojiBSFragment
import com.example.editphotovideo.ui.editorimage.filters.FilterListener
import com.example.editphotovideo.ui.editorimage.filters.FilterViewAdapter
import com.example.editphotovideo.ui.editorimage.lighting.LightingFilterController
import com.example.editphotovideo.ui.editorimage.properties.PropertiesBSFragment
import com.example.editphotovideo.ui.editorimage.sealed.CropResult
import com.example.editphotovideo.ui.editorimage.sealed.ImageFilterResult
import com.example.editphotovideo.ui.editorimage.shape.ShapeBSFragment
import com.example.editphotovideo.ui.editorimage.sticker.StickerBSFragment
import com.example.editphotovideo.ui.editorimage.text.TextEditorDialogFragment
import com.example.editphotovideo.ui.editorimage.text.TextViewModel
import com.example.editphotovideo.ui.editorimage.tools.EditingToolsAdapter
import com.example.editphotovideo.ui.editorimage.tools.ToolType
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.save.SaveImageActivity
import com.example.editphotovideo.utils.ImageUtils.DEFAULT_FOLDER
import com.example.editphotovideo.utils.ImageUtils.getCorrectlyOrientedBitmap
import com.example.editphotovideo.utils.ImageUtils.resizeBitmapToView
import com.example.editphotovideo.utils.helper.FileSaveHelper
import com.example.editphotovideo.widget.getTagDebug
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener
import ja.burhanrashid52.photoeditor.PhotoEditor
import ja.burhanrashid52.photoeditor.PhotoFilter
import ja.burhanrashid52.photoeditor.SaveFileResult
import ja.burhanrashid52.photoeditor.SaveSettings
import ja.burhanrashid52.photoeditor.TextStyleBuilder
import ja.burhanrashid52.photoeditor.ViewType
import ja.burhanrashid52.photoeditor.shape.ShapeBuilder
import ja.burhanrashid52.photoeditor.shape.ShapeType
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSmoothToonFilter
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class EditImageActivity : BaseActivity(), OnPhotoEditorListener, View.OnClickListener,
    PropertiesBSFragment.Properties, ShapeBSFragment.Properties, EmojiBSFragment.EmojiListener,
    StickerBSFragment.StickerListener,
    EditingToolsAdapter.OnItemSelected, FilterListener {
    private lateinit var binding: ActivityEditImageBinding
    private val textViewModel: TextViewModel by viewModels()
    private val mediaViewModel: MediaViewModel by viewModels()

    lateinit var mPhotoEditor: PhotoEditor
    private lateinit var mPropertiesBSFragment: PropertiesBSFragment
    private lateinit var mShapeBSFragment: ShapeBSFragment
    private lateinit var mShapeBuilder: ShapeBuilder
    private lateinit var mEmojiBSFragment: EmojiBSFragment
    private lateinit var mStickerBSFragment: StickerBSFragment
    private lateinit var mWonderFont: Typeface
    private val mEditingToolsAdapter = EditingToolsAdapter(this, this)
    private val mFilterViewAdapter = FilterViewAdapter(this)
    private val mConstraintSet = ConstraintSet()
    private var mIsFilterVisible = false
    private var imageUriOrigin: Uri? = null
    private var imageApplyFilter: Bitmap? = null
    private var imgCrop: Bitmap? = null
    private lateinit var gpuImage: GPUImage
    private lateinit var brightnessFilter: GPUImageBrightnessFilter
    private lateinit var saturationFilter: GPUImageSaturationFilter
    private lateinit var lightingFilterController: LightingFilterController
    private lateinit var cropImageIncludeController: CropImageIncludeController
    private lateinit var beautyFilterController: BeautyFilterController


    @VisibleForTesting
    var mSaveImageUri: Uri? = null

    private lateinit var mSaveFileHelper: FileSaveHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeFullScreen()
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageUriOrigin = intent.getParcelableExtra("URI_IMAGE_INPUT")
        Log.d(getTagDebug(), "onCreate: $imageUriOrigin")
        val bitmap = getCorrectlyOrientedBitmap(this, imageUriOrigin ?: Uri.EMPTY)
        imageApplyFilter = bitmap
        imgCrop = bitmap
        gpuImage = GPUImage(this)
        brightnessFilter = GPUImageBrightnessFilter()
        saturationFilter = GPUImageSaturationFilter()
        initViews()

        handleIntentImage(binding.photoEditorView.source)

        mWonderFont = Typeface.createFromAsset(assets, "barlow_medium_500.ttf")

        mPropertiesBSFragment = PropertiesBSFragment()
        mEmojiBSFragment = EmojiBSFragment()
        mStickerBSFragment = StickerBSFragment()
        mShapeBSFragment = ShapeBSFragment()
        mStickerBSFragment.setStickerListener(this)
        mEmojiBSFragment.setEmojiListener(this)
        mPropertiesBSFragment.setPropertiesChangeListener(this)
        mShapeBSFragment.setPropertiesChangeListener(this)
        if (imageApplyFilter !== null) {
            lightingFilterController = LightingFilterController(
                context = this,
                binding = binding,
                gpuImage = gpuImage,
                imageApplyFilter = imageApplyFilter!!,
                brightnessFilter = GPUImageBrightnessFilter(),
                saturationFilter = GPUImageSaturationFilter()
            ) { result ->
                when (result) {
                    is ImageFilterResult.Success -> {
                        imageApplyFilter = result.bitmap
                        result.bitmap?.let { beautyFilterController.updateImage(it) }
                    }

                    is ImageFilterResult.Canceled -> {
//                    Toast.makeText(this, "Người dùng đã huỷ crop", Toast.LENGTH_SHORT).show()
                    }

                    is ImageFilterResult.Error -> {
                        Toast.makeText(this, "Lỗi crop: ${result.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        beautyFilterController = BeautyFilterController(
            context = this,
            binding = binding,
            gpuImage = gpuImage,
            imageApplyFilter = imageApplyFilter ?: Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ),
            saturationFilter = GPUImageSaturationFilter(),
            smoothToonFilter = GPUImageSmoothToonFilter(),
            brightnessFilter = GPUImageBrightnessFilter(),
            contrastFilter = GPUImageContrastFilter(),
            blurFilter = GPUImageGaussianBlurFilter()
        ) { result ->
            when (result) {
                is ImageFilterResult.Success -> {
                    imageApplyFilter = result.bitmap
                    result.bitmap?.let { lightingFilterController.updateImage(it) }
                }

                is ImageFilterResult.Canceled -> {
//                    Toast.makeText(this, "Người dùng đã huỷ crop", Toast.LENGTH_SHORT).show()
                }

                is ImageFilterResult.Error -> {
                    Toast.makeText(this, "Lỗi crop: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        cropImageIncludeController = CropImageIncludeController(
            activity = this,
            imageToCrop = imageApplyFilter,
            binding = binding,
        ) { result ->
            when (result) {
                is CropResult.Success -> {
                    imageApplyFilter = result.bitmap
                    binding.photoEditorView.source.setImageBitmap(
                        resizeBitmapToView(
                            result.bitmap,
                            binding.photoEditorView.source
                        )
                    )
                    lightingFilterController.updateImage(result.bitmap)
                    beautyFilterController.updateImage(result.bitmap)
                    imgCrop?.let { cropImageIncludeController.updateImage(it) }
                    mEditingToolsAdapter.resetSelection()
                }

                is CropResult.Canceled -> {
                    Toast.makeText(this, "Người dùng đã huỷ crop", Toast.LENGTH_SHORT).show()
                }

                is CropResult.Error -> {
                    Toast.makeText(this, "Lỗi crop: ${result.message}", Toast.LENGTH_SHORT).show()
                }
            }
            mEditingToolsAdapter.resetSelection()
        }
        val llmTools = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvConstraintTools.layoutManager = llmTools
        binding.rvConstraintTools.adapter = mEditingToolsAdapter

        val llmFilters = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvFilterView.layoutManager = llmFilters
        binding.rvFilterView.adapter = mFilterViewAdapter

        // NOTE(lucianocheng): Used to set integration testing parameters to PhotoEditor
        val pinchTextScalable = intent.getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true)

        // val mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium)
        // val mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf")

        mPhotoEditor = PhotoEditor.Builder(this, binding.photoEditorView)
            .setPinchTextScalable(pinchTextScalable) // set flag to make text scalable when pinch
            //.setDefaultTextTypeface(mTextRobotoTf)
            //.setDefaultEmojiTypeface(mEmojiTypeFace)
            .build()

        mPhotoEditor.setOnPhotoEditorListener(this)


        Glide.with(this).load(imageApplyFilter).placeholder(R.drawable.img_beach).centerCrop()
            .into(binding.photoEditorView.source)

        mSaveFileHelper = FileSaveHelper(this)
    }

    private fun handleIntentImage(source: ImageView) {
        if (intent == null) {
            return
        }

        when (intent.action) {
            Intent.ACTION_EDIT, ACTION_NEXTGEN_EDIT -> {
                try {
                    val uri = intent.data
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    source.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            else -> {
                val intentType = intent.type
                if (intentType != null && intentType.startsWith("image/")) {
                    val imageUri = intent.data
                    if (imageUri != null) {
                        source.setImageURI(imageUri)
                    }
                }
            }
        }
    }

    private fun initViews() = binding.apply {
        imgUndo.setOnClickListener(this@EditImageActivity)
        imgUndo.isEnabled = false
        imgRedo.setOnClickListener(this@EditImageActivity)
        imgRedo.isEnabled = false
        imgCamera.setOnClickListener(this@EditImageActivity)
        imgGallery.setOnClickListener(this@EditImageActivity)
        imgSave.setOnClickListener(this@EditImageActivity)
        imgClose.setOnClickListener(this@EditImageActivity)
        imgShare.setOnClickListener(this@EditImageActivity)
//        imgDoneFilter.setOnClickListener {
//            mEditingToolsAdapter.resetSelection()
//            showFilter(false)
//        }
    }

    override fun onEditTextChangeListener(
        rootView: View, text: String,
        colorCode: Int,
        typeface: Typeface,
        textSize: Float,
        gravity: Int
    ) {
        Log.d(
            getTagDebug(),
            "onEditTextChangeListener() called with: rootView = [$rootView], text = [$text], colorCode = [$colorCode], typeface = [$typeface], textSize = [$textSize], gravity = [$gravity]"
        )
        val currentSp = textSize / resources.displayMetrics.scaledDensity
        textViewModel.setTextFont(typeface)

        val textEditorDialogFragment =
            TextEditorDialogFragment.show(this, text, colorCode, currentSp, gravity)
        textEditorDialogFragment.setOnTextEditorListener(object :
            TextEditorDialogFragment.TextEditorListener {
            override fun onDone(
                inputText: String,
                colorCode: Int,
                typeface: Typeface,
                mTextSize: Float,
                gravity: Int
            ) {
                val styleBuilder = TextStyleBuilder().apply {
                    withTextColor(colorCode)
                    withTextSize(mTextSize)
                    withTextFont(typeface)
                    withGravity(gravity)
                }
                mPhotoEditor.editText(rootView, inputText, styleBuilder)
            }
        })
    }


    override fun onAddViewListener(viewType: ViewType, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onAddViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )

        binding.imgUndo.isEnabled = mPhotoEditor.isUndoAvailable
        binding.imgRedo.isEnabled = mPhotoEditor.isRedoAvailable
    }

    override fun onRemoveViewListener(viewType: ViewType, numberOfAddedViews: Int) {
        Log.d(
            TAG,
            "onRemoveViewListener() called with: viewType = [$viewType], numberOfAddedViews = [$numberOfAddedViews]"
        )

        binding.imgUndo.isEnabled = mPhotoEditor.isUndoAvailable
        binding.imgRedo.isEnabled = mPhotoEditor.isRedoAvailable
    }

    override fun onStartViewChangeListener(viewType: ViewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [$viewType]")
    }

    override fun onStopViewChangeListener(viewType: ViewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [$viewType]")
    }

    override fun onTouchSourceImage(event: MotionEvent) {
        Log.d(TAG, "onTouchView() called with: event = [$event]")
    }

    @SuppressLint("NonConstantResourceId", "MissingPermission")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.imgUndo -> {
                binding.imgUndo.isEnabled = mPhotoEditor.undo()
                binding.imgRedo.isEnabled = mPhotoEditor.isRedoAvailable
            }

            R.id.imgRedo -> {
                binding.imgUndo.isEnabled = mPhotoEditor.isUndoAvailable
                binding.imgRedo.isEnabled = mPhotoEditor.redo()
            }

            R.id.imgSave -> saveImage()
            R.id.imgClose -> onBackPressed()
            R.id.imgShare -> shareImage()
            R.id.imgCamera -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photoFile = File.createTempFile("IMG_", ".jpg", cacheDir)
                val photoUri =
                    FileProvider.getUriForFile(this, "${this.packageName}.fileprovider", photoFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                imageUriOrigin = photoUri
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }

            R.id.imgGallery -> {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST)
            }
        }
    }


    override fun onColorChanged(colorCode: Int) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode))
    }

    override fun onOpacityChanged(opacity: Int) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity))
    }

    override fun onShapeSizeChanged(shapeSize: Int) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeSize(shapeSize.toFloat()))
    }

    override fun onShapePicked(shapeType: ShapeType) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType))
    }

    override fun onEmojiClick(emojiUnicode: String) {
        mPhotoEditor.addEmoji(emojiUnicode)
    }

    override fun onStickerClick(bitmap: Bitmap) {
        mPhotoEditor.addImage(bitmap)
    }

    @SuppressLint("MissingPermission")
    override fun isPermissionGranted(isGranted: Boolean, permission: String?) {
        if (isGranted) {
            saveImage()
        }
    }

    @SuppressLint("MissingPermission")
    private fun showSaveDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.msg_save_image))
        builder.setPositiveButton("Save") { _: DialogInterface?, _: Int -> saveImage() }
        builder.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.setNeutralButton("Discard") { _: DialogInterface?, _: Int ->
            showActivity(
                MainActivity::class.java
            )
            finishAffinity()
        }
        builder.create().show()
    }

    override fun onFilterSelected(photoFilter: PhotoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter)
    }

    override fun onToolSelected(toolType: ToolType) {
        when (toolType) {
            ToolType.SHAPE -> {
                onToolSelectedShape()
            }

            ToolType.TEXT -> {
                onToolSelectedText()
            }

            ToolType.ERASER -> {
                onToolSelectedEraser()
            }

            ToolType.FILTER -> {
                onToolSelectedFilter()
            }

            ToolType.EMOJI -> {
                onToolSelectedEmoji()
            }

            ToolType.STICKER -> {
                onToolSelectedSticker()
            }

            ToolType.CROP -> {
                onToolSelectedCrop()

            }

            ToolType.BEAUTY -> {
                onToolSelectedBeauty()

            }

            ToolType.LIGHTING -> {
                onToolSelectedLighting()
            }
        }
    }


    private fun onToolSelectedShape() {
        showFilter(false)
        mPhotoEditor.setBrushDrawingMode(true)
        cropImageIncludeController.showCropImageView(false)
        mShapeBuilder = ShapeBuilder()
        mPhotoEditor.setShape(mShapeBuilder)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)
        showBottomSheetDialogFragment(mShapeBSFragment)
    }

    private fun onToolSelectedText() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)

        textViewModel.setTextFont(Typeface.DEFAULT)
        val textEditorDialogFragment = TextEditorDialogFragment.show(this,
            dismissListenerTextEditor = object : DismissListenerTextEditor {
                override fun onDismissTextEditor() {
                    mEditingToolsAdapter.resetSelection()
                }
            })
        textEditorDialogFragment.setOnTextEditorListener(object :
            TextEditorDialogFragment.TextEditorListener {
            override fun onDone(
                inputText: String,
                colorCode: Int,
                typeface: Typeface,
                mTextSize: Float,
                gravity: Int
            ) {
                val styleBuilder = TextStyleBuilder().apply {
                    withTextColor(colorCode)
                    withTextSize(mTextSize)
                    withTextFont(typeface)
                    withGravity(gravity)
                }
                mPhotoEditor.addText(inputText, styleBuilder)
                mEditingToolsAdapter.resetSelection()
            }
        })
//        textEditorDialogFragment.setDismissListenerTextEditor(object :
//            DismissListenerTextEditor {
//            override fun onDismissTextEditor() {
//                mEditingToolsAdapter.resetSelection()
//            }
//        })
    }

    private fun onToolSelectedEraser() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        mPhotoEditor.brushEraser()
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)
    }

    private fun onToolSelectedFilter() {
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        showFilter(true)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)
    }

    private fun onToolSelectedEmoji() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        showBottomSheetDialogFragment(mEmojiBSFragment)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)
    }

    private fun onToolSelectedSticker() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        showBottomSheetDialogFragment(mStickerBSFragment)
        mPhotoEditor.setBrushDrawingMode(false)
        lightingFilterController.showLighting(false)

        beautyFilterController.showBeauty(false)

    }

    private fun onToolSelectedCrop() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(true)
        mPhotoEditor.setBrushDrawingMode(false)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(false)
    }

    private fun onToolSelectedBeauty() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        lightingFilterController.showLighting(false)
        beautyFilterController.showBeauty(true)
    }

    private fun onToolSelectedLighting() {
        showFilter(false)
        cropImageIncludeController.showCropImageView(false)
        mPhotoEditor.setBrushDrawingMode(false)
        lightingFilterController.showLighting(true)
        beautyFilterController.showBeauty(false)

    }

    private fun showFilter(isVisible: Boolean) {
        mIsFilterVisible = isVisible
        mConstraintSet.clone(binding.rootView)

        val rvFilterId: Int = binding.rvFilterView.id

        if (isVisible) {
            mConstraintSet.clear(rvFilterId, ConstraintSet.START)
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START
            )
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
        } else {
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
            mConstraintSet.clear(rvFilterId, ConstraintSet.END)
        }

        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        TransitionManager.beginDelayedTransition(binding.rootView, changeBounds)

        mConstraintSet.applyTo(binding.rootView)
    }

    private fun shareImage() {
        showLoading("Processing...")
        lifecycleScope.launch {
            val bitmap = mPhotoEditor.saveAsBitmap(
                SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(true)
                    .build()
            )
            hideLoading()
            val file = File(cacheDir, "shared_image_${System.currentTimeMillis()}.jpg")
            file.outputStream().use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            val uri = FileProvider.getUriForFile(
                this@EditImageActivity,
                "${this@EditImageActivity.packageName}.fileprovider",
                file
            )
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)))
        }
    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment?) {
        if (fragment == null || fragment.isAdded) {
            return
        }

        when (fragment) {
            is ShapeBSFragment -> {
                fragment.setDismissListener(object : BottomSheetDismissListener {
                    override fun onBottomSheetDismissed() {
                        mEditingToolsAdapter.resetSelection()
                    }
                })
            }

            is EmojiBSFragment -> {
                fragment.setDismissListener(object : BottomSheetDismissListener {
                    override fun onBottomSheetDismissed() {
                        mEditingToolsAdapter.resetSelection()
                    }
                })
            }

            is StickerBSFragment -> {
                fragment.setDismissListener(object : BottomSheetDismissListener {
                    override fun onBottomSheetDismissed() {
                        mEditingToolsAdapter.resetSelection()
                    }
                })
            }
        }

        fragment.show(supportFragmentManager, fragment.tag)
    }

    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
    private fun saveImage() {
        val fileName = DEFAULT_FOLDER + "_" + System.currentTimeMillis().toString() + ".jpg"
        val hasStoragePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        if (hasStoragePermission || FileSaveHelper.isSdkHigherThan28()) {
            showLoading("Saving...")
            mSaveFileHelper.createFile(
                fileName,
                DEFAULT_FOLDER,
                object : FileSaveHelper.OnFileCreateResult {

                    @RequiresPermission(allOf = [Manifest.permission.WRITE_EXTERNAL_STORAGE])
                    override fun onFileCreateResult(
                        created: Boolean,
                        filePath: String?,
                        error: String?,
                        uri: Uri?
                    ) {
                        lifecycleScope.launch {
                            if (created && filePath != null) {
                                val saveSettings = SaveSettings.Builder()
                                    .setClearViewsEnabled(true)
                                    .setTransparencyEnabled(true)
                                    .build()

                                val result = mPhotoEditor.saveAsFile(filePath, saveSettings)

                                if (result is SaveFileResult.Success) {
                                    mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(
                                        contentResolver
                                    )
                                    hideLoading()
//                                showSnackbar("Image Saved Successfully")
                                    mSaveImageUri = uri
                                    val entity = MediaEntity(
                                        filePath = uri.toString(),
                                        mediaType = MediaType.IMAGE
                                    )
                                    mediaViewModel.insertMedia(entity)
                                    val intent = Intent(
                                        this@EditImageActivity,
                                        SaveImageActivity::class.java
                                    ).apply {
//                                    putExtra("keyActivity","EditImageActivity")
                                        putExtra("output_image_uri", uri.toString())
                                    }
                                    startActivity(intent)
                                } else {
                                    hideLoading()
//                                showSnackbar("Failed to save Image")
                                }
                            } else {
                                hideLoading()
                                error?.let { showSnackbar(error) }
                            }
                        }
                    }
                })
        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST -> {
                    mPhotoEditor.clearAllViews()
                    val photoBitmap = imageUriOrigin?.let { getCorrectlyOrientedBitmap(this, it) }
                    imageApplyFilter = photoBitmap
                    imgCrop = photoBitmap
//                    val imageResize = photoBitmap?.let { resizeBitmapToView(it, binding.photoEditorView.source) }
                    binding.photoEditorView.source.setImageBitmap(imageApplyFilter)
                    imgCrop?.let { cropImageIncludeController.updateImage(it) }
                    photoBitmap?.let {

                        beautyFilterController.updateImage(it)
                        lightingFilterController.updateImage(it)
                    }
                }

                PICK_REQUEST -> try {
                    mPhotoEditor.clearAllViews()
                    val uri = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver, uri
                    )
                    imageUriOrigin = uri
                    imageApplyFilter = bitmap
                    imgCrop = bitmap
                    val imageResize = resizeBitmapToView(bitmap, binding.photoEditorView.source)
                    binding.photoEditorView.source.setImageBitmap(imageResize)
                    imgCrop?.let { cropImageIncludeController.updateImage(it) }
                    beautyFilterController.updateImage(bitmap)
                    lightingFilterController.updateImage(bitmap)
//                    mPhotoEditor.addImage(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (mIsFilterVisible) {
            showFilter(false)
        } else if (!mPhotoEditor.isCacheEmpty) {
            showSaveDialog()
        } else {
            super.onBackPressed()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    companion object {

        private const val TAG = "EditImageActivity"
        private const val CAMERA_REQUEST = 52
        private const val PICK_REQUEST = 53
        const val ACTION_NEXTGEN_EDIT = "action_nextgen_edit"
        const val PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE"
    }
}