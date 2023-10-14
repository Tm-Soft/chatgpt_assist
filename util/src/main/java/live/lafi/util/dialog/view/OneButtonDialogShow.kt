package live.lafi.library_dialog.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import live.lafi.library_dialog.builder.DialogBaseBuilder
import live.lafi.library_dialog.listener.PositiveListener
import live.lafi.util.R
import live.lafi.util.databinding.DialogOneButtonBinding

class OneButtonDialogShow(
    context: Context,
    private val builder: DialogBaseBuilder<*>,
    private val positiveListener: PositiveListener? = null
): Dialog(context) {
    private lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogOneButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOneButtonPositive.setOnClickListener {
            positiveListener?.onPositive()
            this.dismiss()
        }

        initView()
    }

    private fun initView() {
        binding.layoutOneButtonDialogFrame.layoutParams.width = context.resources.displayMetrics.widthPixels

        when (builder.title) {
            null -> { binding.textViewTitle.text = context.getString(R.string.dialog_title_text_default) }
            else -> { binding.textViewTitle.text = builder.title }
        }

        when (builder.content) {
            null -> { binding.textViewContent.text = context.getString(R.string.dialog_content_text_default) }
            else -> { binding.textViewContent.text = builder.content }
        }

        when(builder.positiveText) {
            null -> { binding.btnOneButtonPositive.text = context.getString(R.string.dialog_positive_text_default) }
            else -> { binding.btnOneButtonPositive.text = builder.positiveText }
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
        binding.btnOneButtonPositive.setTextColor(
            ContextCompat.getColor(context, builder.positiveTextColorResId)
        )
    }
}