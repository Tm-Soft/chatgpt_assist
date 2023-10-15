package live.lafi.presentation.chat_room_list

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.presentation.base.BaseActivity
import live.lafi.presentation.databinding.ActivityChatRoomListBinding
import live.lafi.presentation.setting.SettingActivity

@AndroidEntryPoint
class ChatRoomListActivity : BaseActivity<ActivityChatRoomListBinding>(R.layout.activity_chat_room_list) {
    private val viewModel: ChatRoomListViewModel by viewModels()

    private val chatRoomListAdapter by lazy { ChatRoomListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupUi()
        subscribeUi()
        initListener()

        chatRoomListAdapter.submitList(
            listOf(
                ChatRoomInfo(
                    chatRoomSrl = 1L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 2L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 3L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 4L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 5L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 6L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 7L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 8L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                ),
                ChatRoomInfo(
                    chatRoomSrl = 9L,
                    title = "챗봇이냐옹",
                    question = "물어보고 싶네요",
                    content = "무엇을용?",
                    profileUri = "",
                    lastViewDate = 0L,
                    lastUpdate = 0L
                )
            )
        )
    }

    private fun setupUi() {
        with(binding) {
            rvChatRoomList.apply {
                adapter = chatRoomListAdapter
                layoutManager = LinearLayoutManager(this@ChatRoomListActivity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
        }
    }

    private fun initListener() {
        with(binding) {
            flChatAddButton.setOnClickListener { showAddChatRoomDialog() }
            flSettingButton.setOnClickListener {
                startActivity(
                    Intent(this@ChatRoomListActivity, SettingActivity::class.java)
                )
            }
        }

        chatRoomListAdapter.setOnClickListener {
            showToast("안녕 클릭 : $it")
        }
    }

    private fun showAddChatRoomDialog() {
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