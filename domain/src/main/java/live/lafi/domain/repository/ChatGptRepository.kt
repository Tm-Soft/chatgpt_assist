package live.lafi.domain.repository

import kotlinx.coroutines.flow.Flow
import live.lafi.domain.ApiResult
import live.lafi.domain.model.chat_gpt.CompletionData

interface ChatGptRepository {
    suspend fun postChatCompletions(
        sendMessage: String
    ): Flow<ApiResult<CompletionData>>

    suspend fun postChatListCompletions(
        sendSystemMessage: List<String>,
        sendUserMessage: List<Pair<String, String>>,
    ): Flow<ApiResult<CompletionData>>
}