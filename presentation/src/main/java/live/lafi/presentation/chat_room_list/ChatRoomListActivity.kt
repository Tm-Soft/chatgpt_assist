package live.lafi.presentation.chat_room_list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.util.base.BaseActivity
import live.lafi.presentation.databinding.ActivityChatRoomListBinding
import live.lafi.presentation.setting.SettingActivity
import live.lafi.presentation.wiget.bottom_sheet.chat_room_setting.ChatRoomSettingBottomSheet

@AndroidEntryPoint
class ChatRoomListActivity : BaseActivity<ActivityChatRoomListBinding>(R.layout.activity_chat_room_list) {
    private val viewModel: ChatRoomListViewModel by viewModels()

    private val chatRoomListAdapter by lazy { ChatRoomListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupUi() {
        with(binding) {
            rvChatRoomList.apply {
                adapter = chatRoomListAdapter
                layoutManager = LinearLayoutManager(this@ChatRoomListActivity, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun subscribeUi() {
        with(viewModel) {
            lifecycleScope.launch(Dispatchers.IO) {
                getAllChatRoomInfo().collectLatest { setOnChatRoomList(it) }
            }
        }
    }

    override fun initListener() {
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

    override fun initData() {}

    private fun setOnChatRoomList(chatRoomInfoList: List<ChatRoomInfo>) {
        chatRoomListAdapter.submitList(
            chatRoomInfoList.map {
                ChatRoomItem(
                    chatRoomSrl = it.chatRoomSrl,
                    title = it.title,
                    question = "",
                    content = "",
                    profileUri = it.profileUri,
                    lastReadTimestamp = it.lastReadTimestamp,
                    lastUpdateTimestamp = it.lastUpdateTimestamp
                )
            }.sortedByDescending { it.lastUpdateTimestamp }
        )
    }

    private fun showAddChatRoomDialog() {
        Dialog.with(this@ChatRoomListActivity)
            .title(getString(R.string.enter_chat_bot_name))
            .positiveText(getString(R.string.add_text))
            .negativeText(getString(R.string.close_text))
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {
                    viewModel.insertChatRoom(title = inputText)
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