package gun0912.tedimagepicker.builder

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import gun0912.tedimagepicker.TedImagePickerDialogFragment
import gun0912.tedimagepicker.builder.listener.*
import gun0912.tedimagepicker.builder.type.SelectType
import java.lang.ref.WeakReference

class TedImagePickerDialog {

    companion object {
        @JvmStatic
        fun with(context: Context) = Builder(WeakReference(context))
    }

    @SuppressLint("ParcelCreator")
    class Builder(private val contextWeakReference: WeakReference<Context>) :
        TedImagePickerBaseBuilder<Builder>() {

        fun errorListener(listener: OnErrorListener): Builder {
            this.onErrorListener = listener
            return this
        }

        fun errorListener(action: (Throwable) -> Unit): Builder {
            this.onErrorListener = object : OnErrorListener {
                override fun onError(throwable: Throwable) {
                    action(throwable)
                }
            }
            return this
        }
        fun cancelListener(listener: ImageSelectCancelListener): Builder {
            this.imageSelectCancelListener = listener
            return this
        }

        fun cancelListener(action: () -> Unit): Builder =
            cancelListener(object : ImageSelectCancelListener {
                override fun onImageSelectCancel() {
                    action.invoke()
                }
            })


        fun start(action: (Uri) -> Unit) {
            this.onSelectedListener = object : OnSelectedListener {
                override fun onSelected(uri: Uri) {
                    action(uri)
                }
            }

            selectType = SelectType.SINGLE
            contextWeakReference.get()?.let {
                startDialog(it)
            }
        }

        fun startMultiImage(action: (List<Uri>) -> Unit) {
            this.onMultiSelectedListener = object : OnMultiSelectedListener {
                override fun onSelected(uriList: List<Uri>) {
                    action(uriList)
                }
            }

            selectType = SelectType.MULTI
            contextWeakReference.get()?.let {
                startDialog(it)
            }
        }

        private fun startDialog(context: Context) {

            val fragmentManager =
                (context as AppCompatActivity).supportFragmentManager

            TedImagePickerDialogFragment
                .newInstance(this)
                .show(fragmentManager, "TedImagePickerDialog")
        }
    }
}