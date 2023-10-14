package live.lafi.presentation.chat_room_list

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.presentation.base.BaseActivity
import live.lafi.presentation.databinding.ActivityChatRoomListBinding
import live.lafi.presentation.setting.SettingActivity

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
        with(binding) {
            tvResponseMessage.movementMethod = ScrollingMovementMethod()
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            onLoading.observe(this@ChatRoomListActivity) { isLoading ->
                binding.etSendMessage.isEnabled = !isLoading
                binding.btSend.isEnabled = !isLoading
            }
            responseMessage.observe(this@ChatRoomListActivity) { message ->
                binding.tvResponseMessage.text = message
            }
        }
    }

    private fun initListener() {
        with(binding) {
            flChatAddButton.setOnClickListener { showAddChatRoom() }
            flSettingButton.setOnClickListener {
                startActivity(
                    Intent(this@ChatRoomListActivity, SettingActivity::class.java)
                )
            }
            btSend.setOnClickListener {
                if (!binding.etSendMessage.text.isNullOrEmpty()) {
                    viewModel.postChatGptMessage(
                        binding.etSendMessage.text.toString()
                    )

                    binding.etSendMessage.text.clear()
                }
            }
        }
    }

    private fun showAddChatRoom() {
        Dialog.with(this@ChatRoomListActivity)
            .title("챗봇 이름 입력")
            .content("")
            .positiveText("변경")
            .negativeText("닫기")
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {

                } else {
                    showToast("AI 이름을 입력 해주세요.")
                }
            }
            .showEditTextDialog()
    }
}