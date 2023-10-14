package live.lafi.domain.usecase.chat_gpt

import kotlinx.coroutines.flow.Flow
import live.lafi.domain.ApiResult
import live.lafi.domain.model.chat_gpt.CompletionData
import live.lafi.domain.repository.ChatGptRepository

class PostChatCompletionsUseCase(private val chatGptRepository: ChatGptRepository) {
    suspend operator fun invoke(
        sendMessage: String
    ): Flow<ApiResult<CompletionData>> = chatGptRepository.postChatCompletions(sendMessage = sendMessage)
}