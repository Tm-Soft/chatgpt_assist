package live.lafi.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import live.lafi.data.handleFlowApi
import live.lafi.data.mapper.ChatGptMapper
import live.lafi.data.model.request.CompletionRequest
import live.lafi.data.network.OpenaiApi
import live.lafi.domain.ApiResult
import live.lafi.domain.model.chat_gpt.CompletionData
import live.lafi.domain.repository.ChatGptRepository
import live.lafi.util.chat_gpt.model.ChatGptMessage
import timber.log.Timber
import javax.inject.Inject

class ChatGptRepositoryImpl @Inject constructor(
    private val service: OpenaiApi
): ChatGptRepository {
    override fun postChatCompletions(
        sendMessage: String
    ): Flow<ApiResult<CompletionData>> =
        handleFlowApi {
            service.getCompletion(
                CompletionRequest(
                        model = "gpt-3.5-turbo",
                        temperature = 0.9,
                        messages = listOf(
                            ChatGptMessage(
                                "user",
                                sendMessage
                            )
                        )
                    )
            )
        }.map { apiResult ->
            when(apiResult) {
                is ApiResult.Success -> ApiResult.Success(ChatGptMapper.mapperToCompletionData(apiResult.data))
                is ApiResult.Fail -> apiResult
                is ApiResult.Loading -> ApiResult.Loading
            }
        }
}