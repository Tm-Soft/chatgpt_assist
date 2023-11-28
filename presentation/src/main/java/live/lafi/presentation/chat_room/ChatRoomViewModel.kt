package live.lafi.presentation.chat_room

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.lafi.domain.usecase.chat.InsertChatContentUseCase
import live.lafi.util.DateUtil
import live.lafi.util.base.BaseViewModel
import live.lafi.util.chat_gpt.GetGptToken
import live.lafi.util.chat_gpt.model.ChatGptMessage
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val insertChatContentUseCase: InsertChatContentUseCase
) : BaseViewModel() {
    fun sendChatContent(
        chatRoomSrl: Long,
        content: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = GetGptToken(
                GetGptToken.GptModelType.GPT_3_5_TURBO,
                listOf(
                    ChatGptMessage(
                        "user",
                        "안녕하세요. 저는 지금 어떤걸 찾고 있어요. 그런데 아주 작은 아기새가 나타나서 저한테 하는말이 있는거에요!!"
                    )
                )
            )

            insertChatContentUseCase(
                chatRoomSrl = chatRoomSrl,
                role = "user",
                content = content,
                useToken = token,
                status = "wait",
                updateDate = DateUtil.getFullDate(),
                createDate =  DateUtil.getFullDate()
            )
        }
    }
}