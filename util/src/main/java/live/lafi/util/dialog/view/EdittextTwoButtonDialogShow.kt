package live.lafi.library_dialog.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import live.lafi.library_dialog.builder.DialogBaseBuilder
import live.lafi.library_dialog.listener.NegativeListener
import live.lafi.library_dialog.listener.StringCallbackListener
import live.lafi.util.R
import live.lafi.util.databinding.DialogEdittextWithTwoButtonBinding

class EdittextTwoButtonDialogShow (
    context: Context,
    private val builder: DialogBaseBuilder<*>,
    private val positiveListener: StringCallbackListener? = null,
    private val negativeListener: NegativeListener? = null
): Dialog(context) {
    private lateinit var binding : DialogEdittextWithTwoButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogEdittextWithTwoButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(char: CharSequence?, start: Int, end: Int, p3: Int) {
                if(!char.isNullOrBlank()) {
                    binding.btnDeleteAllText.visibility = View.VISIBLE
                } else {
                    binding.btnDeleteAllText.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnDeleteAllText.setOnClickListener {
            binding.editText.text.clear()
        }

        binding.btnDialogModify.setOnClickListener {
            positiveListener?.onStringCallBack(binding.editText.text.toString().trim())
            this.dismiss()
        }

        binding.btnDialogNegative.setOnClickListener {
            negativeListener?.onNegative()
            this.dismiss()
        }

        initView()
    }

    private fun initView() {
        binding.layoutEdittextDialogFrame.layoutParams.width = context.resources.displayMetrics.widthPixels

        when (builder.title) {
            null -> { binding.textViewTitle.text = context.getString(R.string.dialog_title_text_default) }
            else -> { binding.textViewTitle.text = builder.title }
        }

        when (builder.content) {
            null -> binding.textViewContent.visibility = View.GONE
            else -> {
                binding.textViewContent.visibility = View.VISIBLE
                binding.textViewContent.text = builder.content
            }
        }

        when(builder.positiveText) {
            null -> { binding.btnDialogModify.text = context.getString(R.string.dialog_positive_text_default) }
            else -> { binding.btnDialogModify.text = builder.positiveText }
        }

        when(builder.negativeText) {
            null -> { binding.btnDialogNegative.text = context.getString(R.string.dialog_negative_text_default) }
            else -> { binding.btnDialogNegative.text = builder.negativeText }
        }

        when (builder.editTextHint) {
            null -> binding.editText.hint = "내용을 입력해 주세요"
            else -> binding.editText.hint = builder.editTextHint
        }


        // Title Color Set
        binding.textViewTitle.setTextColor(
            ContextCompat.getColor(context, builder.titleTextColorResId)
        )

        // Positive Text Color Set
        binding.btnDialogModify.setTextColor(
            ContextCompat.getColor(context, builder.positiveTextColorResId)
        )

        // Negative Text Color Set
        binding.btnDialogNegative.setTextColor(
            ContextCompat.getColor(context, R.color.price_color_red)
        )
    }
}