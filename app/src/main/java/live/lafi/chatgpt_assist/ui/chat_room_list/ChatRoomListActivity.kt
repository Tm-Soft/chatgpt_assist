package live.lafi.chatgpt_assist.ui.chat_room_list

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import live.lafi.chatgpt_assist.R
import live.lafi.chatgpt_assist.base.BaseActivity
import live.lafi.chatgpt_assist.databinding.ActivityChatRoomListBinding
import live.lafi.library_dialog.Dialog

@AndroidEntryPoint
class ChatRoomListActivity : BaseActivity<ActivityChatRoomListBinding>(R.layout.activity_chat_room_list) {
    private val viewModel: ChatRoomListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        subscribeUi()
        initListener()
    }

    private fun setupUi() {

    }

    private fun subscribeUi() {

    }

    private fun initListener() {
        with(binding) {
            flChatAddButton.setOnClickListener { showEditGptToken() }
        }
    }

    private fun showEditGptToken() {
        Dialog.with(this@ChatRoomListActivity)
            .title("ChatGpt API Token 입력")
            .content("")
            .positiveText("변경")
            .negativeText("닫기")
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {
                    viewModel.updateChatGptToken(
                        token = inputText,
                        success = {
                            showToast("ChatGPT Token 변경 완료")
                        }
                    )
                } else {
                    showToast("AI 이름을 입력 해주세요.")
                }
            }
            .showEditTextDialog()
    }
}