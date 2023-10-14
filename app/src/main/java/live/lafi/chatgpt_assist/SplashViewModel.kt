package live.lafi.chatgpt_assist

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.startWith
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import live.lafi.chatgpt_assist.base.BaseViewModel
import live.lafi.util.public_model.GptToken
import live.lafi.data.model.request.CompletionRequest
import live.lafi.data.network.OpenaiApi
import live.lafi.domain.ApiResult
import live.lafi.domain.ApiResult.Loading.onError
import live.lafi.domain.ApiResult.Loading.onException
import live.lafi.domain.ApiResult.Loading.onLoading
import live.lafi.domain.ApiResult.Loading.onSuccess
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.util.chat_gpt.model.ChatGptMessage
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val service: OpenaiApi,
    private val postChatCompletionsUseCase: PostChatCompletionsUseCase,
    private val saveChatGptTokenUseCase: SaveChatGptTokenUseCase,
    private val loadChatGptTokenUseCase: LoadChatGptTokenUseCase
): BaseViewModel() {
    suspend fun setupChatGptToken(): String {
        val token = loadChatGptTokenUseCase()
        if (token.isNotEmpty()) {
            GptToken.editToken(token)
        }
        return token
    }

    fun updateChatGptToken(token: String) {
        scopeIO.launch {
            GptToken.editToken(token)
            saveChatGptTokenUseCase(token)
        }
    }

    fun postChatGpt() {
        scopeIO.launch {
            postChatCompletionsUseCase("크로스이엔에프에 대해서 자세히 1500자 이상으로 알려줘").onStart {}
                .collect { response ->
                    Timber.tag("server flow").e("로딩 GONE")
                    response.onLoading {
                        Timber.tag("server flow").e("로딩 VISIBLE")
                    }

                    response.onSuccess {
                        Timber.tag("server flow").e("성공 데이터 : $it")
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