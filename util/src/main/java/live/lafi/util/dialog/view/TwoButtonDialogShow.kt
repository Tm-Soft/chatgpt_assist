package live.lafi.library_dialog.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import live.lafi.library_dialog.builder.DialogBaseBuilder
import live.lafi.library_dialog.listener.NegativeListener
import live.lafi.library_dialog.listener.PositiveListener
import live.lafi.util.R
import live.lafi.util.databinding.DialogTwoButtonBinding

class TwoButtonDialogShow (
    context: Context,
    private val builder: DialogBaseBuilder<*>,
    private val positiveListener: PositiveListener? = null,
    private val negativeListener: NegativeListener? = null
): Dialog(context) {

    private lateinit var binding : DialogTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogTwoButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDialogPositive.setOnClickListener {
            positiveListener?.onPositive()
            this.dismiss()
        }

        binding.btnDialogNegative.setOnClickListener {
            negativeListener?.onNegative()
            this.dismiss()
        }

        initView()
    }

    private fun initView() {
        binding.layoutTwoButtonDialogFrame.layoutParams.width = context.resources.displayMetrics.widthPixels

        when (builder.title) {
            null -> { binding.textViewTitle.text = context.getString(R.string.dialog_title_text_default) }
            else -> { binding.textViewTitle.text = builder.title }
        }

        when (builder.content) {
            null -> { binding.textViewContent.text = context.getString(R.string.dialog_content_text_default) }
            else -> { binding.textViewContent.text = builder.content }
        }

        when(builder.positiveText) {
            null -> { binding.btnDialogPositive.text = context.getString(R.string.dialog_positive_text_default) }
            else -> { binding.btnDialogPositive.text = builder.positiveText }
        }

        when(builder.negativeText) {
            null -> { binding.btnDialogNegative.text = context.getString(R.string.dialog_negative_text_default) }
            else -> { binding.btnDialogNegative.text = builder.negativeText }
        }


        // Title Color Set
        binding.textViewTitle.setTextColor(
            ContextCompat.getColor(context, builder.titleTextColorResId)
        )

        // Content Color Set
        binding.textViewContent.setTextColor(
            ContextCompat.getColor(context, builder.contentTextColorResId)
        )

        // Positive Text Color Set
        binding.btnDialogPositive.setTextColor(
            ContextCompat.getColor(context, builder.positiveTextColorResId)
        )

        // Negative Text Color Set
        binding.btnDialogNegative.setTextColor(
            ContextCompat.getColor(context, builder.negativeTextColorResId)
        )
    }
}