package live.lafi.library_dialog.builder


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Parcelable
import android.view.WindowManager
import androidx.annotation.ColorRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import live.lafi.library_dialog.listener.NegativeListener
import live.lafi.library_dialog.listener.PositiveListener
import live.lafi.library_dialog.listener.StringCallbackListener
import live.lafi.library_dialog.view.*
import live.lafi.util.R

@Suppress("UNCHECKED_CAST")
@Parcelize
open class DialogBaseBuilder<out B : DialogBaseBuilder<B>>(
    internal var title: String? = null,
    internal var content: String? = null,
    internal var positiveText: String? = null,
    internal var negativeText: String? = null,
    internal var selectTitleList: List<String>? = null,
    internal var selectContentList: List<String>? = null,
    @ColorRes
    internal var titleTextColorResId: Int = R.color.gray_60,
    @ColorRes
    internal var contentTextColorResId: Int = R.color.gray_40,
    @ColorRes
    internal var positiveTextColorResId: Int = R.color.detail_button_blue,
    @ColorRes
    internal var negativeTextColorResId: Int = R.color.gray_30

): Parcelable {
    @IgnoredOnParcel
    protected var positiveListener: PositiveListener? = null

    @IgnoredOnParcel
    protected var negativeListener: NegativeListener? = null

    @IgnoredOnParcel
    protected var stringCallbackListener: StringCallbackListener? = null

    fun title(title: String): B {
        this.title = title
        return this as B
    }

    fun content(content: String): B {
        this.content = content
        return this as B
    }

    fun selectTitleList(list: List<String>): B {
        this.selectTitleList = list
        return this as B
    }

    fun selectContentList(list: List<String>): B {
        this.selectContentList = list
        return this as B
    }

    fun positiveText(positiveText: String): B {
        this.positiveText = positiveText
        return this as B
    }

    fun negativeText(negativeText: String): B {
        this.negativeText = negativeText
        return this as B
    }

    fun setTitleColor(@ColorRes titleTextColorResId: Int): B {
        this.titleTextColorResId = titleTextColorResId
        return this as B
    }

    fun setContentColor(@ColorRes contentTextColorResId: Int): B {
        this.contentTextColorResId = contentTextColorResId
        return this as B
    }

    fun setPositiveTextColor(@ColorRes positiveTextColorResId: Int): B {
        this.positiveTextColorResId = positiveTextColorResId
        return this as B
    }

    fun setNegativeTextColor(@ColorRes negativeTextColorResId: Int): B {
        this.negativeTextColorResId = negativeTextColorResId
        return this as B
    }

    protected fun startOneButtonDialog(context: Context) {
        val dialogObj = OneButtonDialogShow(
            context,
            this,
            positiveListener
        )

        dialogObj.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogObj.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialogObj.setOnCancelListener{
            positiveListener?.onPositive()
            negativeListener?.onNegative()
        }
        dialogObj.show()
    }

    protected fun startTwoButtonDialog(context: Context) {
        val dialogObj = TwoButtonDialogShow(
            context,
            this,
            positiveListener,
            negativeListener
        )

        dialogObj.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogObj.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        //dialogObj.setOnCancelListener{ negativeListener?.onNegative() }
        dialogObj.show()
    }

    protected fun startEditTextDialog(context: Context) {
        val dialogObj = EdittextTwoButtonDialogShow(
            context,
            this,
            stringCallbackListener,
            negativeListener
        )

        dialogObj.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogObj.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialogObj.show()
    }

    protected fun startKakaoLoginDialog(context: Context) {
        val dialogObj = KakaoLoginDialogShow(
            context,
            positiveListener,
            negativeListener
        )

        dialogObj.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogObj.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialogObj.show()
    }

    protected fun startRecyclerStringSelectDialog(context: Context) {
        val dialogObj = RecyclerStringSelectDialogShow(
            context,
            this,
            stringCallbackListener,
        )

        dialogObj.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogObj.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialogObj.show()
    }
}