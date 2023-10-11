package live.lafi.chatgpt_assist.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import live.lafi.chatgpt_assist.base.BaseViewModel
import live.lafi.chatgpt_assist.di.GptToken
import live.lafi.data.model.request.CompletionRequest
import live.lafi.data.network.OpenaiApi
import live.lafi.domain.usecase.local_setting.LoadChatGptTokenUseCase
import live.lafi.domain.usecase.local_setting.SaveChatGptTokenUseCase
import live.lafi.util.chat_gpt.model.ChatGptMessage
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: OpenaiApi,
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
        scopeIO.launch(Dispatchers.IO) {
            val data = service.getCompletion(
                CompletionRequest(
                    model = "gpt-3.5-turbo",
                    temperature = 0.9,
                    messages = listOf(
                        ChatGptMessage(
                            "user",
                            "안녕하세요."
                        )
                    )
                )
            )
        }
    }

//    fun testScope1() {
//        scopeIO.launch {
//            repeat(100) {
//                Timber.tag("test__").d("1번 작업 : ${Thread.currentThread().name} :: $it")
//                delay(100000L)
//            }
//        }
//    }
//
//    fun testScope2() {
//        scopeIO.launch {
//            repeat(100) {
//                Timber.tag("test__").d("2 작업 : ${Thread.currentThread().name} :: $it")
//                delay(1000L)
//                _countText.postValue((countText.value!!.toInt() + 1).toString())
//            }
//        }
//    }
}