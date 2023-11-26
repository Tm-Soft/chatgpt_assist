package live.lafi.presentation.chat_room_list

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.util.base.BaseActivity
import live.lafi.presentation.databinding.ActivityChatRoomListBinding
import live.lafi.presentation.setting.SettingActivity
import live.lafi.presentation.wiget.bottom_sheet.chat_room_setting.ChatRoomSettingBottomSheet
import live.lafi.util.VibratorUtil

@AndroidEntryPoint
class ChatRoomListActivity : BaseActivity<ActivityChatRoomListBinding>(R.layout.activity_chat_room_list) {
    private val viewModel: ChatRoomListViewModel by viewModels()
    private val chatRoomListAdapter by lazy { ChatRoomListAdapter() }
    private val vibratorUtil by lazy { VibratorUtil(this@ChatRoomListActivity) }

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
                getAllChatRoomInfo().collectLatest {
                    if (it.isEmpty()) {
                        // 리스트가 비어있다면...
                        viewModel.insertChatRoom("GPT 비서")
                    } else {
                        setOnChatRoomList(it)
                    }
                }
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

        chatRoomListAdapter.apply {
            setOnClickListener {
                showToast("채팅방 클릭 srl = $it")
            }

            setOnLongClickListener {
                vibratorUtil.vibrateOneShot(50, 100)
                showChatRoomSettingBottomSheet(chatRoomSrl = it)
            }
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

    private fun showChatRoomSettingBottomSheet(chatRoomSrl: Long) {
        ChatRoomSettingBottomSheet.newInstance(
            chatRoomSrl = chatRoomSrl,
        ).apply {
            setOnChatRoomDeleteListener {
                viewModel.deleteChatRoom(chatRoomSrl = chatRoomSrl)
            }
        }.show(supportFragmentManager, "ChatRoomSettingBottomSheet")
    }

    private fun showAddChatRoomDialog() {
        Dialog.with(this@ChatRoomListActivity)
            .title(getString(R.string.enter_chat_bot_name))
            .content("챗봇의 이름을 지어 주세요.\n생성 이후 나만의 프롬프트를 설정할 수 있는 창이 나타나요!")
            .positiveText(getString(R.string.chat_bot_create_text))
            .negativeText(getString(R.string.close_text))
            .stringCallbackListener { inputText ->
                if (inputText.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val createChatRoomSrl = viewModel.insertChatRoom(title = inputText)
                        viewModel.initChatRoomSystemRole(createChatRoomSrl)
                        withContext(Dispatchers.Main) {
                            showChatRoomSettingBottomSheet(chatRoomSrl = createChatRoomSrl)
                        }
                    }
                } else {
                    showToast(getString(R.string.plase_enter_chat_bot_name))
                }
            }
            .showEditTextDialog()
    }
}