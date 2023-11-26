package live.lafi.presentation.chat_room_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import live.lafi.domain.ApiResult.LoadingStart.onError
import live.lafi.domain.ApiResult.LoadingStart.onException
import live.lafi.domain.ApiResult.LoadingStart.onLoadingEnd
import live.lafi.domain.ApiResult.LoadingStart.onLoadingStart
import live.lafi.domain.ApiResult.LoadingStart.onSuccess
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.util.base.BaseViewModel
import live.lafi.util.ext.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChatRoomListViewModel @Inject constructor(
    private val postChatCompletionsUseCase: PostChatCompletionsUseCase
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
}