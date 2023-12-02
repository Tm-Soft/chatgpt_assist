package live.lafi.presentation.chat_room_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import live.lafi.domain.ApiResult.LoadingStart.onError
import live.lafi.domain.ApiResult.LoadingStart.onException
import live.lafi.domain.ApiResult.LoadingStart.onLoadingEnd
import live.lafi.domain.ApiResult.LoadingStart.onLoadingStart
import live.lafi.domain.ApiResult.LoadingStart.onSuccess
import live.lafi.domain.model.chat.ChatRoomSystemRoleInfo
import live.lafi.domain.usecase.chat.DeleteChatRoomSystemRoleWithChatRoomSrlUseCase
import live.lafi.domain.usecase.chat.DeleteChatRoomWithSrlUseCase
import live.lafi.domain.usecase.chat.GetAllChatRoomInfoUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomSystemRoleListUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomSystemRoleUseCase
import live.lafi.domain.usecase.chat.InsertChatRoomUseCase
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.util.base.BaseViewModel
import live.lafi.util.ext.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val postChatCompletionsUseCase: PostChatCompletionsUseCase,
    private val insertChatRoomUseCase: InsertChatRoomUseCase,
    private val getAllChatRoomInfoUseCase: GetAllChatRoomInfoUseCase,
    private val deleteChatRoomWithSrlUseCase: DeleteChatRoomWithSrlUseCase,
    private val insertChatRoomSystemRoleListUseCase: InsertChatRoomSystemRoleListUseCase,
    private val deleteChatRoomSystemRoleWithChatRoomSrlUseCase: DeleteChatRoomSystemRoleWithChatRoomSrlUseCase
) : BaseViewModel() {
    private val _onLoading = SingleLiveEvent<Boolean>()
    val onLoading: LiveData<Boolean> get() = _onLoading

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> get() = _responseMessage

    fun postChatGptMessage(message: String) {
        scopeIO.launch {
            postChatCompletionsUseCase(sendMessage = message).collectLatest { response ->
                response.onLoadingStart {
                    Timber.tag("server flow").e("로딩 VISIBLE")
                    _onLoading.postValue(true)
                }
                response.onLoadingEnd {
                    Timber.tag("server flow").e("로딩 GONE")
                    _onLoading.postValue(false)

                }
                response.onSuccess {
                    Timber.tag("server flow").e("성공 데이터 : $it")
                    _responseMessage.postValue(it.data[0].message.content)
                }
                response.onError { code, message ->
                    Timber.tag("server flow").e("에러 code : $code / message : $message")
                }
                response.onException {
                    Timber.tag("server flow").e("익셉션 : $it")
                }
            }
        }
    }

    suspend fun getAllChatRoomInfo() = getAllChatRoomInfoUseCase()

    suspend fun insertChatRoom(title: String): Long {
        return insertChatRoomUseCase(
            title = title,
            profileUri = null
        )
    }

    fun deleteChatRoom(chatRoomSrl: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteChatRoomWithSrlUseCase(chatRoomSrl)
            deleteChatRoomSystemRoleWithChatRoomSrlUseCase(chatRoomSrl)
        }
    }

    suspend fun initChatRoomSystemRole(chatRoomSrl: Long) {
        insertChatRoomSystemRoleListUseCase(
            listOf(
                ChatRoomSystemRoleInfo(
                    chatRoomSystemRoleSrl = 0L,
                    chatRoomSrl = chatRoomSrl,
                    roleContent = "너는 나의 비서로 내가 하는 질문에 모두 대답 해야돼"
                ),
                ChatRoomSystemRoleInfo(
                    chatRoomSystemRoleSrl = 0L,
                    chatRoomSrl = chatRoomSrl,
                    roleContent = "어떤 질문이든 반드시 정답을 찾아서 알려줘"
                )
            )
        )
    }
}