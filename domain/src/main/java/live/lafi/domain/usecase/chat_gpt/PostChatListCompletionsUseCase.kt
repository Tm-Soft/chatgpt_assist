package live.lafi.domain.usecase.chat_gpt

import live.lafi.domain.repository.ChatGptRepository

class PostChatListCompletionsUseCase(private val chatGptRepository: ChatGptRepository) {
    suspend operator fun invoke(
        sendSystemMessage: List<String>,
        sendUserMessage: List<Pair<String, String>>,
    ) = chatGptRepository.postChatListCompletions(
        sendSystemMessage = sendSystemMessage,
        sendUserMessage = sendUserMessage
    )
}