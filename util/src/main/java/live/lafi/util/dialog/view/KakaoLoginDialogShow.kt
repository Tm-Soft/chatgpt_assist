package live.lafi.library_dialog.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import live.lafi.library_dialog.listener.NegativeListener
import live.lafi.library_dialog.listener.PositiveListener
import live.lafi.util.databinding.DialogKakoLoginBinding

class KakaoLoginDialogShow(
    context: Context,
    private val positiveListener: PositiveListener? = null,
    private val negativeListener: NegativeListener? = null
): Dialog(context) {
    private lateinit var binding: DialogKakoLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogKakoLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutDialogKakaoLoginSelectId.layoutParams.apply {
            val displayMetrics = context.resources.displayMetrics
            width = displayMetrics.widthPixels
            height = displayMetrics.heightPixels
        }

        binding.layoutKakaoLogin.setOnClickListener {
            positiveListener?.onPositive()
            this.dismiss()
        }

        binding.layoutKakaoLoginAnotherAccount.setOnClickListener {
            negativeListener?.onNegative()
            this.dismiss()
        }

        binding.textViewCancel.setOnClickListener {
            this.dismiss()
        }

    }
}