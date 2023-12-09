package live.lafi.chatgpt_assist

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import live.lafi.util.public_model.GptTokenManager
import live.lafi.domain.ApiResult.LoadingStart.onError
import live.lafi.domain.ApiResult.LoadingStart.onException
import live.lafi.domain.ApiResult.LoadingStart.onLoadingEnd
import live.lafi.domain.ApiResult.LoadingStart.onLoadingStart
import live.lafi.domain.ApiResult.LoadingStart.onSuccess
import live.lafi.domain.usecase.chat_gpt.PostChatCompletionsUseCase
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.util.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val postChatCompletionsUseCase: PostChatCompletionsUseCase,
): BaseViewModel() {

    fun postChatGpt() {
        scopeIO.launch {
            postChatCompletionsUseCase("크로스이엔에프에 대해서 자세히 1500자 이상으로 알려줘")
                .collectLatest { response ->
                    response.onLoadingStart {
                        Timber.tag("server flow").e("로딩 VISIBLE")
                    }
                    response.onLoadingEnd {
                        Timber.tag("server flow").e("로딩 GONE")
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