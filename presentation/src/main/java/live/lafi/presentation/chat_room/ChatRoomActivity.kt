package live.lafi.presentation.chat_room

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import live.lafi.presentation.R
import live.lafi.presentation.databinding.ActivityChatRoomBinding
import live.lafi.util.base.BaseActivity

@AndroidEntryPoint
class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {
    companion object {
        const val CHAT_ROOM_SRL = "chat_room_srl"
        const val CHAT_ROOM_TITLE = "chat_room_title"
    }

    private val viewModel: ChatRoomViewModel by viewModels()

    private val chatRoomSrl by lazy { intent.getLongExtra(CHAT_ROOM_SRL, 0L) }
    private val chatRoomTitle by lazy { intent.getStringExtra(CHAT_ROOM_TITLE) ?: "" }

    override fun setupUi() {
        if (chatRoomSrl == 0L) {
            showToast("잘못 된 접근입니다.")
            finish()
        }

        with(binding) {
            binding.tvChatRoomTitle.text = chatRoomTitle
        }
    }

    override fun subscribeUi() {
    }

    override fun initListener() {
        with(binding) {
            flBackButton.setOnClickListener { finish() }
            flSendButton.setOnClickListener { sendMessage(etTextMessage.text.toString()) }

            etTextMessage.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun afterTextChanged(s: Editable?) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s.isNullOrBlank()) {
                        flSendButton.visibility = View.GONE
                    } else {
                        flSendButton.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    override fun initData() {
    }

    private fun sendMessage(message: String) {
        if (message.isNotBlank()) {
            viewModel.sendChatContent(
                chatRoomSrl = chatRoomSrl,
                content = message
            )
            binding.etTextMessage.text.clear()
        }
    }
}