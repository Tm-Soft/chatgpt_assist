package live.lafi.library_dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import live.lafi.library_dialog.builder.DialogBaseBuilder
import live.lafi.library_dialog.listener.NegativeListener
import live.lafi.library_dialog.listener.PositiveListener
import live.lafi.library_dialog.listener.StringCallbackListener
import java.lang.ref.WeakReference

class Dialog {
    companion object {
        @JvmStatic
        fun with(context: Context) = Builder(WeakReference(context))
    }
    @SuppressLint("ParcelCreator")
    class Builder(
        private val contextWeakReference: WeakReference<Context>
    ): DialogBaseBuilder<Builder>() {

        private fun positiveListener(positiveListener: PositiveListener): Builder {
            this.positiveListener = positiveListener
            return this
        }

        fun positiveListener(action: () -> Unit): Builder =
            positiveListener(object : PositiveListener {
                override fun onPositive() {
                    action.invoke()
                }
            })

        private fun negativeListener(negativeListener: NegativeListener): Builder {
            this.negativeListener = negativeListener
            return this
        }

        fun negativeListener(action: () -> Unit): Builder =
            negativeListener(object: NegativeListener{
                override fun onNegative() {
                    action.invoke()
                }
            })


        private fun stringCallbackListener(stringCallbackListener: StringCallbackListener) : Builder {
            this.stringCallbackListener = stringCallbackListener
            return this
        }

        fun stringCallbackListener(action: (String) -> Unit) : Builder =
            stringCallbackListener(object : StringCallbackListener {
                override fun onStringCallBack(string: String) {
                    action.invoke(string)
                }
            })


        fun showOneButtonDialog() {
            contextWeakReference.get()?.let {
                startOneButtonDialog(it)
            }
        }

        fun showTwoButtonDialog() {
            contextWeakReference.get()?.let {
                startTwoButtonDialog(it)
            }
        }

        fun showEditTextDialog() {
            contextWeakReference.get()?.let {
                startEditTextDialog(it)
            }
        }

        fun showKakaoLoginDialog() {
            contextWeakReference.get()?.let {
                startKakaoLoginDialog(it)
            }
        }

        fun showSelectDialog() {
            contextWeakReference.get()?.let {
                startRecyclerStringSelectDialog(it)
            }
        }
    }
}