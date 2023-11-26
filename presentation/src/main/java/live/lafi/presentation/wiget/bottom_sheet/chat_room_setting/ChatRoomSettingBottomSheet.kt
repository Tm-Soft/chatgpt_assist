package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import live.lafi.presentation.R
import live.lafi.util.base.BaseBottomSheetFragment
import live.lafi.presentation.databinding.FragmentChatRoomSettingBinding

class ChatRoomSettingBottomSheet : BaseBottomSheetFragment<FragmentChatRoomSettingBinding>(R.layout.fragment_chat_room_setting) {
    private val chatRoomSrl by lazy { arguments?.getLong(CHAT_ROOM_SRL) ?: 0L }
    private val chatRoomTitle by lazy { arguments?.getString(CHAT_ROOM_TITLE) ?: "" }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupUi() {
        with(binding) {
            tvTitle.text = chatRoomTitle
        }
    }

    override fun subscribeUi() {}

    override fun initListener() {
        binding.flCloseButton.setOnClickListener { dismiss() }
    }

    override fun initData() {}

    companion object {
        private const val CHAT_ROOM_SRL = "chat_room_srl"
        private const val CHAT_ROOM_TITLE = "chat_room_title"
        @JvmStatic
        fun newInstance(
            chatRoomSrl: Long,
            chatRoomTitle: String,
        ) = ChatRoomSettingBottomSheet().apply {
            arguments = Bundle().apply {
                putLong(CHAT_ROOM_SRL, chatRoomSrl)
                putString(CHAT_ROOM_TITLE, chatRoomTitle)
            }
        }
    }
}