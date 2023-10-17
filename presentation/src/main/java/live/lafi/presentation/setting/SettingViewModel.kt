package live.lafi.presentation.setting

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import live.lafi.domain.ApiResult.LoadingEnd.onError
import live.lafi.domain.ApiResult.LoadingEnd.onException
import live.lafi.domain.ApiResult.LoadingEnd.onLoadingEnd
import live.lafi.domain.ApiResult.LoadingEnd.onLoadingStart
import live.lafi.domain.ApiResult.LoadingEnd.onSuccess
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.LoadMaxUseTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveMaxUseTokenUseCase
import live.lafi.presentation.base.BaseViewModel
import live.lafi.util.ext.SingleLiveEvent
import live.lafi.util.public_model.GptToken
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loadChatGptTokenUseCase: LoadChatGptTokenUseCase,
    private val saveChatGptTokenUseCase: SaveChatGptTokenUseCase,
    private val loadMaxUseTokenUseCase: LoadMaxUseTokenUseCase,
    private val saveMaxUseTokenUseCase: SaveMaxUseTokenUseCase,
    private val postChatCompletionsUseCase: PostChatCompletionsUseCase
): BaseViewModel() {
    private val _onLoading = SingleLiveEvent<Boolean>()
    val onLoading: SingleLiveEvent<Boolean> get() = _onLoading

    suspend fun getChatGptToken() = loadChatGptTokenUseCase()

    suspend fun getMaxUseToken() = loadMaxUseTokenUseCase()

    fun updateChatGptToken(
        token: String,
        success: () -> Unit,
        fail: () -> Unit
    ) {
        scopeIO.launch {
            GptToken.editToken(token)
            // 먼저 토큰 값으로 서버에 요청을 보내서 토큰이 유효한지 확인한다.
            postChatCompletionsUseCase("hi").collectLatest { result ->
                result.onLoadingStart {
                    _onLoading.postValue(true)
                }
                result.onLoadingEnd {
                    _onLoading.postValue(false)
                }
                result.onSuccess { data ->
                    if (data.data[0].message.content.isNotEmpty()) {
                        viewModelScope.launch {
                            success.invoke()
                            saveChatGptTokenUseCase(token)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            fail.invoke()
                        }
                    }
                }
                result.onError { _, _ ->
                    withContext(Dispatchers.Main) {
                        fail.invoke()
                    }
                }
                result.onException {
                    withContext(Dispatchers.Main) {
                        fail.invoke()
                    }
                }
            }
        }
    }

    fun updateMaxUseToken(number: Int) {
        scopeIO.launch { saveMaxUseTokenUseCase(number) }
    }
}