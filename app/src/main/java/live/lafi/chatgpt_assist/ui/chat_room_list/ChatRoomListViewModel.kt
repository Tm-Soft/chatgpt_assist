package live.lafi.chatgpt_assist.ui.chat_room_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import live.lafi.chatgpt_assist.base.BaseViewModel
import live.lafi.chatgpt_assist.di.GptToken
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import javax.inject.Inject

@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val saveChatGptTokenUseCase: SaveChatGptTokenUseCase
) : BaseViewModel() {
    fun updateChatGptToken(
        token: String,
        success: () -> Unit
    ) {
        scopeIO.launch {
            GptToken.editToken(token)
            saveChatGptTokenUseCase(token)
            success.invoke()
        }
    }
}