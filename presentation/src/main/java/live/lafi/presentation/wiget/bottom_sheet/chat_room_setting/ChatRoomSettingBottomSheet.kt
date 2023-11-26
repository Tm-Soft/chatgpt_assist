package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import live.lafi.library_dialog.Dialog
import live.lafi.presentation.R
import live.lafi.util.base.BaseBottomSheetFragment
import live.lafi.presentation.databinding.FragmentChatRoomSettingBinding

@AndroidEntryPoint
class ChatRoomSettingBottomSheet : BaseBottomSheetFragment<FragmentChatRoomSettingBinding>(R.layout.fragment_chat_room_setting) {
    private val chatRoomSrl by lazy { arguments?.getLong(CHAT_ROOM_SRL) ?: 0L }

    private val viewModel: ChatRoomSettingViewModel by viewModels()

    private var onChatRoomDeleteListener: (() -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (chatRoomSrl == 0L) {
            dismiss()
        }
    }

    override fun setupUi() {
        with(binding) {}
    }

    override fun subscribeUi() {
        with(viewModel) {
            chatRoomInfo.observe(viewLifecycleOwner) { chatRoomInfo ->
                binding.tvTitle.text = chatRoomInfo.title
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