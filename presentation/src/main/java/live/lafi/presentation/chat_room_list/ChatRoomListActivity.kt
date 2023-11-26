package live.lafi.presentation.chat_room_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.presentation.base.BaseActivity
import live.lafi.presentation.databinding.ActivityChatRoomListBinding
import live.lafi.presentation.setting.SettingActivity
import live.lafi.presentation.wiget.bottom_sheet.chat_room_setting.ChatRoomSettingBottomSheet

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
            .title(getString(R.string.enter_chat_bot_name))
            .positiveText(getString(R.string.add_text))
            .negativeText(getString(R.string.close_text))
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {
                    ChatRoomSettingBottomSheet.newInstance(
                        0L,
                        inputText
                    ).show(supportFragmentManager, "ChatRoomSettingBottomSheet")
                } else {
                    showToast(getString(R.string.plase_enter_chat_bot_name))
                }
            }
            .showEditTextDialog()
    }
}