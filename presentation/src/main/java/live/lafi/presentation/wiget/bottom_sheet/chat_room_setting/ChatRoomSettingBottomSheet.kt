package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.util.base.BaseBottomSheetFragment
import live.lafi.presentation.databinding.FragmentChatRoomSettingBinding
import timber.log.Timber

@AndroidEntryPoint
class ChatRoomSettingBottomSheet : BaseBottomSheetFragment<FragmentChatRoomSettingBinding>(R.layout.fragment_chat_room_setting) {
    private val chatRoomSrl by lazy { arguments?.getLong(CHAT_ROOM_SRL) ?: 0L }

    private val viewModel: ChatRoomSettingViewModel by viewModels()

    private val chatSystemRoleAdapter by lazy { ChatSystemRoleAdapter() }

    private var onChatRoomDeleteListener: (() -> Unit)? = null

    private var roleScrollBottomFlag = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (chatRoomSrl == 0L) {
            dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.changeChatRoomSystemRoleList.value?.let { viewModel.updateChatRoomSystemRoleList(it) }
    }

    override fun setupUi() {
        with(binding) {
            rvSystemRole.apply {
                adapter = chatSystemRoleAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun modifyUiUpdate() {
        binding.tvSettingStatus.visibility = View.VISIBLE
        binding.tvSettingStatus.text = "설정 변경 중..."
    }

    override fun subscribeUi() {
        with(viewModel) {
            chatRoomInfo.observe(viewLifecycleOwner) { chatRoomInfo ->
                binding.tvTitle.text = chatRoomInfo.title
            }

            changeChatRoomSystemRoleList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    modifyUiUpdate()
                }
            }


            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getChatRoomSystemRole(chatRoomSrl = chatRoomSrl).collectLatest { chatRoomSystemRoleList ->
                    chatSystemRoleAdapter.submitList(
                        chatRoomSystemRoleList.map {
                            ChatSystemRoleListItem(
                                viewType = ChatSystemRoleListItem.ViewType.ROLE_CONTENT,
                                chatSystemRoleSrl = it.chatRoomSystemRoleSrl,
                                roleContent = it.roleContent
                            )
                        } + ChatSystemRoleListItem(
                            viewType = ChatSystemRoleListItem.ViewType.PLUS_BUTTON,
                            chatSystemRoleSrl = 0L,
                            roleContent = ""
                        )
                    ) {
                        binding.rvSystemRole.post {
                            lifecycleScope.launch {
                                delay(300L)

                                if (roleScrollBottomFlag) {
                                    roleScrollBottomFlag = false
                                    binding.rvSystemRole.smoothScrollToPosition(chatSystemRoleAdapter.itemCount)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initListener() {
        binding.flCloseButton.setOnClickListener { dismiss() }
        binding.flDeleteButton.setOnClickListener {
            Dialog.with(requireContext())
                .title("채팅방 삭제")
                .content("정말 채팅방을 삭제 하시겠어요?\n등록된 프롬프트와 대화 내역이 삭제 됩니다.")
                .positiveText("그대로 두기")
                .negativeText("삭제")
                .setNegativeTextColor(R.color.price_color_red)
                .negativeListener {
                    onChatRoomDeleteListener?.invoke()
                    dismiss()
                }.showTwoButtonDialog()
        }

        chatSystemRoleAdapter.apply {
            setOnChangeChatSystemRoleContentListener { chatRoomSystemRoleSrl, content ->
                var replaceList: List<ChatRoomSystemRoleInfo>? = null
                val changeModel = ChatRoomSystemRoleInfo(
                    chatRoomSystemRoleSrl = chatRoomSystemRoleSrl,
                    chatRoomSrl = chatRoomSrl,
                    roleContent = content
                )
                replaceList = if (viewModel.changeChatRoomSystemRoleList.value?.firstOrNull { it.chatRoomSystemRoleSrl == chatRoomSystemRoleSrl } == null) {
                    if (viewModel.changeChatRoomSystemRoleList.value == null) {
                        listOf(changeModel)
                    } else {
                        viewModel.changeChatRoomSystemRoleList.value!! + changeModel
                    }
                } else {
                    viewModel.changeChatRoomSystemRoleList.value?.map {
                        if (it.chatRoomSystemRoleSrl == chatRoomSystemRoleSrl) {
                            changeModel
                        } else {
                            it
                        }
                    }
                }

                replaceList?.let { viewModel.setChatRoomSystemRoleList(it) }
            }

            setOnRolePlusListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.changeChatRoomSystemRoleList.value?.let { viewModel.updateChatRoomSystemRoleListCoroutine(it) }

                    viewModel.insertChatRoomSystemRole(
                        chatRoomSrl = chatRoomSrl,
                        roleContent = ""
                    )
                    roleScrollBottomFlag = true
                }
                modifyUiUpdate()
            }

            setOnDeleteListener { chatSystemRoleSrl ->
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.changeChatRoomSystemRoleList.value?.let { viewModel.updateChatRoomSystemRoleListCoroutine(it) }
                    viewModel.deleteChatRoomSystemRole(
                        chatRoomSystemRoleSrl = chatSystemRoleSrl
                    )
                }
                modifyUiUpdate()
            }
        }
    }

    override fun initData() {
        viewModel.getChatRoomInfo(chatRoomSrl = chatRoomSrl)
    }

    fun setOnChatRoomDeleteListener(listener: () -> Unit) {
        this.onChatRoomDeleteListener = listener
    }

    companion object {
        private const val CHAT_ROOM_SRL = "chat_room_srl"

        @JvmStatic
        fun newInstance(
            chatRoomSrl: Long,
        ) = ChatRoomSettingBottomSheet().apply {
            arguments = Bundle().apply {
                putLong(CHAT_ROOM_SRL, chatRoomSrl)
            }
        }
    }
}