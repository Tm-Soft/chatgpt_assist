package live.lafi.presentation.wiget.bottom_sheet.chat_room_setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import live.lafi.domain.model.chat.ChatRoomInfo
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.usecase.chat.DeleteChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.DeleteChatRoomSystemRoleWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.GetChatRoomInfoWithSrlUseCase
import live.lafi.domain.usecase.chat.GetChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.UpdateChatRoomSystemRoleListUseCase
import live.lafi.domain.usecase.chat.UpdateChatRoomSystemRoleUseCase
import live.lafi.util.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ChatRoomSettingViewModel @Inject constructor(
    private val getChatRoomInfoWithSrlUseCase: GetChatRoomInfoWithSrlUseCase,
    private val getChatRoomSystemRoleUseCase: GetChatRoomSystemRoleUseCase,
    private val updateChatRoomSystemRoleListUseCase: UpdateChatRoomSystemRoleListUseCase,
    private val insertChatRoomSystemRoleUseCase: InsertChatRoomSystemRoleUseCase,
    private val deleteChatRoomSystemRoleUseCase: DeleteChatRoomSystemRoleUseCase
) : BaseViewModel() {
    private val _chatRoomInfo = MutableLiveData<ChatRoomInfo>()
    val chatRoomInfo: LiveData<ChatRoomInfo> get() = _chatRoomInfo

    private val _changeChatRoomSystemRoleList = MutableLiveData<List<ChatRoomSystemRoleInfo>>()
    val changeChatRoomSystemRoleList: LiveData<List<ChatRoomSystemRoleInfo>> get() =_changeChatRoomSystemRoleList

    fun getChatRoomInfo(chatRoomSrl: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getChatRoomInfoWithSrlUseCase(chatRoomSrl).let {
                _chatRoomInfo.postValue(it)
            }
        }
    }

    suspend fun getChatRoomSystemRole(chatRoomSrl: Long) = getChatRoomSystemRoleUseCase(chatRoomSrl = chatRoomSrl)

    fun updateChatRoomSystemRoleList(
        chatRoomSystemRoleInfoList: List<ChatRoomSystemRoleInfo>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            updateChatRoomSystemRoleListUseCase(
                chatRoomSystemRoleInfoList = chatRoomSystemRoleInfoList
            )
        }
    }

    suspend fun updateChatRoomSystemRoleListCoroutine(
        chatRoomSystemRoleInfoList: List<ChatRoomSystemRoleInfo>
    ) {
        updateChatRoomSystemRoleListUseCase(
            chatRoomSystemRoleInfoList = chatRoomSystemRoleInfoList
        )
    }

    suspend fun insertChatRoomSystemRole(
        chatRoomSrl: Long,
        roleContent: String,
    ) {
        insertChatRoomSystemRoleUseCase(
            chatRoomSrl = chatRoomSrl,
            roleContent = roleContent
        )
    }

    fun setChatRoomSystemRoleList(list: List<ChatRoomSystemRoleInfo>) {
        _changeChatRoomSystemRoleList.postValue(list)
    }

    suspend fun deleteChatRoomSystemRole(
        chatRoomSystemRoleSrl: Long
    ) {
        deleteChatRoomSystemRoleUseCase(
            chatRoomSystemRoleSrl = chatRoomSystemRoleSrl
        )
    }
}