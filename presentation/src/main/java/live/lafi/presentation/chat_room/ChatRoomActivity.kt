package live.lafi.presentation.chat_room

import live.lafi.presentation.R
import live.lafi.presentation.databinding.ActivityChatRoomBinding
import live.lafi.util.base.BaseActivity

class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(R.layout.activity_chat_room) {
    companion object {
        const val CHAT_ROOM_SRL = "chat_room_srl"
    }

    private val chatRoomSrl by lazy { intent.getLongExtra(CHAT_ROOM_SRL, 0L) }

    override fun setupUi() {
        if (chatRoomSrl == 0L) {
            showToast("잘못 된 접근입니다.")
            finish()
        }


    }

    override fun subscribeUi() {
    }

    override fun initListener() {
    }

    override fun initData() {
    }


}