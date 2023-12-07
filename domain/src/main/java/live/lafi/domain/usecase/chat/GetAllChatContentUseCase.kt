package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class GetAllChatContentUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke() = chatRepository.getAllChatContent()
}