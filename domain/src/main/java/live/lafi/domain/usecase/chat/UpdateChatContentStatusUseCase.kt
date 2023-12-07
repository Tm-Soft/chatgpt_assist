package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class UpdateChatContentStatusUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatContentSrl: Long,
        status: String,
        updateDate: Long
    ) {
        chatRepository.updateChatContentStatus(
            chatContentSrl = chatContentSrl,
            status = status,
            updateDate = updateDate
        )
    }
}