package live.lafi.domain.usecase.chat

import live.lafi.domain.repository.ChatRepository

class UpdateChatRoomTitleUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        chatRoomSrl: Long,
        title: String
    ) {
        chatRepository.updateChatRoomTitle(
            chatRoomSrl = chatRoomSrl,
            title = title
        )
    }
}