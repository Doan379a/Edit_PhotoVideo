package com.example.editphotovideo.library.freehandcropper

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.editphotovideo.ui.main.MainActivity
import com.example.editphotovideo.ui.removebackgr.FreeHandCropperActivity

class SomeView : View, View.OnTouchListener {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var points: MutableList<Point> = mutableListOf()
    private val DIST = 2
    private var flgPathDraw = true
    private var mfirstpoint: Point? = null
    private var bfirstpoint = false
    private var mlastpoint: Point? = null
    private var bitmap: Bitmap? = null
    private var mContext: Context
    var onCropReady: (() -> Unit)? = null

    constructor(context: Context, bitmap: Bitmap) : super(context) {
        this.mContext = context
        this.bitmap = bitmap
        initPaint()
        this.setOnTouchListener(this)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        initPaint()
        this.setOnTouchListener(this)
    }

    private fun initPaint() {
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(10f, 20f), 0f)
        paint.strokeWidth = 5f
        paint.color = Color.RED
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        val path = Path()
        var first = true
        var i = 0
        while (i < points.size) {
            val point = points[i]
            if (first) {
                first = false
                path.moveTo(point.x.toFloat(), point.y.toFloat())
            } else if (i < points.size - 1) {
                val next = points[i + 1]
                path.quadTo(point.x.toFloat(), point.y.toFloat(), next.x.toFloat(), next.y.toFloat())
            } else {
                mlastpoint = point
                path.lineTo(point.x.toFloat(), point.y.toFloat())
            }
            i += 2
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        val point = Point(event.x.toInt(), event.y.toInt())

        if (flgPathDraw) {
            if (bfirstpoint) {
                if (comparePoint(mfirstpoint, point)) {
                    points.add(mfirstpoint!!)
                    flgPathDraw = false
//                    showCropDialog()
                    onCropReady?.invoke()
                } else {
                    points.add(point)
                }
            } else {
                points.add(point)
                mfirstpoint = point
                bfirstpoint = true
            }
        }

        invalidate()
        Log.e("Hi  ==>", "Size: ${point.x} ${point.y}")

        if (event.action == MotionEvent.ACTION_UP) {
            Log.d("Action up*****~~>>>>", "called")
            mlastpoint = point
            if (flgPathDraw && points.size > 12 && !comparePoint(mfirstpoint, mlastpoint)) {
                flgPathDraw = false
                points.add(mfirstpoint!!)
//                showCropDialog()
                onCropReady?.invoke()
            }
        }
        return true
    }

    private fun comparePoint(first: Point?, current: Point?): Boolean {
        if (first == null || current == null) return false

        val leftRangeX = current.x - 3
        val leftRangeY = current.y - 3
        val rightRangeX = current.x + 3
        val rightRangeY = current.y + 3

        return (leftRangeX < first.x && first.x < rightRangeX
                && leftRangeY < first.y && first.y < rightRangeY
                && points.size >= 10)
    }

    fun fillInPartOfPath() {
        points.add(Point(points[0].x, points[0].y))
        invalidate()
    }

    fun resetView() {
        points.clear()
        initPaint()
        bfirstpoint = false
        flgPathDraw = true
        invalidate()
    }
    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        Log.d("SomeView", "Bitmap set with size ${bitmap.width}x${bitmap.height}")
        invalidate()
    }
    private fun showCropDialog() {
        var onCropReady: (() -> Unit)? = null
//        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
//            when (which) {
//                DialogInterface.BUTTON_POSITIVE -> (mContext as? FreeHandCropperActivity)?.cropImage()
//                DialogInterface.BUTTON_NEGATIVE -> resetView()
//            }
//        }
//
//        AlertDialog.Builder(mContext)
//            .setMessage("Do you Want to save Crop or Non-crop image?")
//            .setPositiveButton("Crop", dialogClickListener)
//            .setNegativeButton("Non-crop", dialogClickListener)
//            .setCancelable(false)
//            .show()
    }

    fun getPoints(): List<Point> = points
}
