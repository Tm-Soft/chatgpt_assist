package live.lafi.presentation.chat_room_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.presentation.base.BaseViewModel
import live.lafi.util.public_model.GptToken
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
            withContext(Dispatchers.Main) {
                success.invoke()
            }
        }
    }
}