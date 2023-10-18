package live.lafi.domain.usecase.chat_gpt

import live.lafi.domain.repository.ChatGptRepository

class PostChatCompletionsStreamUseCase(private val chatGptRepository: ChatGptRepository) {
    suspend operator fun invoke() {
        chatGptRepository.chatCompletionsStream()
    }
}