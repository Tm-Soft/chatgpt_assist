package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.usecase.chat.GetChatRoomInfoWithSrlUseCase
import live.lafi.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatRoomSettingViewModel @Inject constructor(
    private val getChatRoomInfoWithSrlUseCase: GetChatRoomInfoWithSrlUseCase
) : BaseViewModel() {
    private val _chatRoomInfo = MutableLiveData<ChatRoomInfo>()
    val chatRoomInfo: LiveData<ChatRoomInfo> = _chatRoomInfo

    fun getChatRoomInfo(chatRoomSrl: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatRoomInfoWithSrlUseCase(chatRoomSrl).let {
                _chatRoomInfo.postValue(it)
            }
        }
    }
}